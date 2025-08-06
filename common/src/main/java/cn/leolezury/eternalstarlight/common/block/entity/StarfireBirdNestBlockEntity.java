package cn.leolezury.eternalstarlight.common.block.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.StarfireBirdNestBlock;
import cn.leolezury.eternalstarlight.common.entity.living.animal.StarfireBird;
import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import cn.leolezury.eternalstarlight.common.registry.ESDataComponents;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class StarfireBirdNestBlockEntity extends SimpleContainerBlockEntity {
	private static final String TAG_BIRDS = "birds";
	private static final String TAG_LAST_SEED_PLAYER = "last_seed_player";
	private static final String TAG_HATCH_TICKS = "hatch_ticks";
	private static final List<String> IGNORED_BIRD_TAGS = Arrays.asList("Air", "FallDistance", "FallFlying", "Fire", "HurtByTimestamp", "HurtTime", "Motion", "PortalCooldown", "Pos", "leash", "UUID");

	private final List<BirdData> stored = Lists.newArrayList();
	private NonNullList<ItemStack> items = NonNullList.withSize(5, ItemStack.EMPTY);
	private UUID lastSeedPlayer = null;
	private int hatchTicks;

	public StarfireBirdNestBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(ESBlockEntities.STARFIRE_BIRD_NEST.get(), blockPos, blockState);
	}

	@Override
	public void setChanged() {
		super.setChanged();
		if (getLevel() != null) {
			this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
		}
	}

	public boolean addSeeds(ItemStack stack) {
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).isEmpty()) {
				items.set(i, stack);
				setChanged();
				return true;
			}
		}
		return false;
	}

	public boolean removeSeeds() {
		for (int i = 0; i < items.size(); i++) {
			if (!items.get(i).isEmpty()) {
				items.set(i, ItemStack.EMPTY);
				setChanged();
				return true;
			}
		}
		return false;
	}

	public void setLastSeedPlayer(Player player) {
		this.lastSeedPlayer = player.getUUID();
	}

	public void releaseAllOccupants(BlockState state, boolean emergency) {
		List<Entity> list = Lists.newArrayList();
		if (level != null) {
			this.stored.removeIf((birdData) -> releaseOccupant(this.level, this.worldPosition, state, this, birdData.toOccupant(), list, emergency, 2400));
		}
		if (!list.isEmpty()) {
			setChanged();
		}
	}

	public int getOccupantCount() {
		return this.stored.size();
	}

	@Override
	public int getContainerSize() {
		return 5;
	}

	@Override
	public int getMaxStackSize() {
		return 1;
	}

	@Override
	public boolean canPlaceItem(int index, ItemStack stack) {
		return stack.is(ESTags.Items.STARFIRE_BIRD_FOOD) && this.getItem(index).isEmpty() && stack.getCount() <= this.getMaxStackSize();
	}

	public boolean isEmpty() {
		return this.stored.isEmpty();
	}

	public boolean isFullForAdults() {
		return this.getAdults().size() >= 2;
	}

	public boolean isFullForBabies() {
		return this.getBabies().size() >= 3;
	}

	public List<BirdData> getAdults() {
		return this.stored.stream().filter(d -> d.getOrCreateEntityInstance(level, getBlockPos()) instanceof AgeableMob mob && !mob.isBaby()).toList();
	}

	public List<BirdData> getBabies() {
		return this.stored.stream().filter(d -> d.getOrCreateEntityInstance(level, getBlockPos()) instanceof AgeableMob mob && mob.isBaby()).toList();
	}

	public void addAdultOccupant(Entity entity) {
		addOccupant(entity, 600, false);
	}

	public void addBabyOccupant(Entity entity) {
		addOccupant(entity, 24000 - 20, true);
	}

	public void addOccupant(Entity entity, int minTicksInNest, boolean baby) {
		if ((!baby && this.getAdults().size() < 2) || (baby && this.getBabies().size() < 3)) {
			entity.stopRiding();
			entity.ejectPassengers();
			this.storeBird(Occupant.of(entity, minTicksInNest));
			if (this.level != null) {
				BlockPos blockPos = this.getBlockPos();
				this.level.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Context.of(entity, this.getBlockState()));
			}

			entity.discard();
			setChanged();
		}
	}

	public void storeBird(Occupant occupant) {
		this.stored.add(new BirdData(occupant));
		if (level != null) {
			this.stored.sort(Comparator.comparingInt(d -> d.getOrCreateEntityInstance(level, getBlockPos()) instanceof AgeableMob mob ? -mob.getAge() : 0));
		}
		setChanged();
	}

	private static boolean releaseOccupant(Level level, BlockPos blockPos, BlockState blockState, StarfireBirdNestBlockEntity blockEntity, Occupant occupant, @Nullable List<Entity> list, boolean emergency, int renterCooldown) {
		if (level.isRaining() && !emergency) {
			return false;
		} else {
			Direction facing = blockState.getValue(StarfireBirdNestBlock.FACING);
			BlockPos neighborBlock = blockPos.relative(facing);
			boolean hasCollisionShape = !level.getBlockState(neighborBlock).getCollisionShape(level, neighborBlock).isEmpty();
			if (hasCollisionShape && !emergency) {
				return false;
			} else {
				Entity entity = occupant.createEntity(level, blockPos, true);
				if (entity != null) {
					if (entity instanceof StarfireBird bird) {
						if (list != null) {
							list.add(bird);
						}
						float bbWidth = entity.getBbWidth();
						double xzOffset = hasCollisionShape ? 0.0 : 0.55 + (bbWidth / 2.0);
						double x = blockPos.getX() + 0.5 + xzOffset * facing.getStepX();
						double y = blockPos.getY() + 0.5 - (entity.getBbHeight() / 2.0);
						double z = blockPos.getZ() + 0.5 + xzOffset * facing.getStepZ();
						entity.moveTo(x, y, z, entity.getYRot(), entity.getXRot());
						if (level.getBlockState(blockPos).is(ESTags.Blocks.STARFIRE_BIRD_NESTS) && !emergency
							&& !bird.isBaby() && bird.canFallInLove() && bird.getAge() == 0
							&& blockEntity.removeSeeds()) {
							Player loveCause = null;
							if (blockEntity.lastSeedPlayer != null) {
								loveCause = level.getPlayerByUUID(blockEntity.lastSeedPlayer);
							}
							bird.setInLove(loveCause);
						}
						bird.addTrustedPlayer(blockEntity.lastSeedPlayer);
						bird.addGiftCount();
						bird.setStayOutOfNestTicks(renterCooldown);
					}

					level.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Context.of(entity, level.getBlockState(blockPos)));
					return level.addFreshEntity(entity);
				} else {
					return false;
				}
			}
		}
	}

	private static void tickOccupants(Level level, BlockPos pos, BlockState state, StarfireBirdNestBlockEntity blockEntity, List<BirdData> list) {
		boolean change = false;
		Iterator<BirdData> iterator = list.iterator();

		while (iterator.hasNext()) {
			BirdData data = iterator.next();
			if (data.tick()) {
				if (releaseOccupant(level, pos, state, blockEntity, data.toOccupant(), null, false, 1200)) {
					change = true;
					iterator.remove();
				}
			}
		}

		int eggs = state.getValue(StarfireBirdNestBlock.EGGS);
		if (eggs > 0) {
			blockEntity.hatchTicks++;
			if (!blockEntity.isFullForBabies() && blockEntity.hatchTicks > 6000) {
				StarfireBird baby = new StarfireBird(ESEntities.STARFIRE_BIRD.get(), level);
				baby.setBaby(true);
				baby.moveTo(pos.getCenter());
				if (level instanceof ServerLevel serverLevel) {
					baby.finalizeSpawn(serverLevel, level.getCurrentDifficultyAt(pos), MobSpawnType.BREEDING, null);
				}
				blockEntity.addBabyOccupant(baby);
				change = true;
				level.setBlockAndUpdate(pos, state.setValue(StarfireBirdNestBlock.EGGS, eggs - 1));
				blockEntity.hatchTicks = 0;
			}
		} else {
			blockEntity.hatchTicks = 0;
		}

		if (change) {
			blockEntity.setChanged();
		}
	}

	public static void clientTick(Level level, BlockPos pos, BlockState state, StarfireBirdNestBlockEntity blockEntity) {
		for (BirdData data : blockEntity.stored) {
			data.clientTick(level);
		}
	}

	public static void serverTick(Level level, BlockPos pos, BlockState state, StarfireBirdNestBlockEntity blockEntity) {
		tickOccupants(level, pos, state, blockEntity, blockEntity.stored);
		if (!blockEntity.stored.isEmpty() && level.getRandom().nextDouble() < 0.005) {
			level.playSound(null, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, ESSoundEvents.STARFIRE_BIRD_AMBIENT.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
		}
	}

	@Override
	protected void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
		super.loadAdditional(compoundTag, provider);
		this.stored.clear();
		if (compoundTag.contains(TAG_BIRDS)) {
			Occupant.LIST_CODEC.parse(NbtOps.INSTANCE, compoundTag.get(TAG_BIRDS)).resultOrPartial((string) -> EternalStarlight.LOGGER.error("Failed to parse Starfire Birds: '{}'", string)).ifPresent((list) -> list.forEach(this::storeBird));
		}
		this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
		ContainerHelper.loadAllItems(compoundTag, this.items, provider);
		if (compoundTag.hasUUID(TAG_LAST_SEED_PLAYER)) {
			lastSeedPlayer = compoundTag.getUUID(TAG_LAST_SEED_PLAYER);
		}
		hatchTicks = compoundTag.getInt(TAG_HATCH_TICKS);
		setChanged();
	}

	@Override
	protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
		super.saveAdditional(compoundTag, provider);
		compoundTag.put(TAG_BIRDS, Occupant.LIST_CODEC.encodeStart(NbtOps.INSTANCE, this.getBirds()).getOrThrow());
		ContainerHelper.saveAllItems(compoundTag, this.items, provider);
		if (lastSeedPlayer != null) {
			compoundTag.putUUID(TAG_LAST_SEED_PLAYER, lastSeedPlayer);
		}
		compoundTag.putInt(TAG_HATCH_TICKS, hatchTicks);
	}

	@Override
	public NonNullList<ItemStack> getItems() {
		return items;
	}

	@Nullable
	@Override
	public Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
		return saveWithFullMetadata(provider);
	}

	@Override
	protected void applyImplicitComponents(BlockEntity.DataComponentInput input) {
		super.applyImplicitComponents(input);
		this.stored.clear();
		List<Occupant> occupants = input.getOrDefault(ESDataComponents.BIRDS.get(), List.of());
		occupants.forEach(this::storeBird);
		setChanged();
	}

	@Override
	protected void collectImplicitComponents(DataComponentMap.Builder builder) {
		super.collectImplicitComponents(builder);
		builder.set(ESDataComponents.BIRDS.get(), this.getBirds());
	}

	@Override
	public void removeComponentsFromTag(CompoundTag compoundTag) {
		super.removeComponentsFromTag(compoundTag);
		compoundTag.remove(TAG_BIRDS);
	}

	private List<Occupant> getBirds() {
		return this.stored.stream().map(BirdData::toOccupant).toList();
	}

	public static class BirdData {
		private final Occupant occupant;
		private int ticksInNest;
		private Entity entityInstance;
		private int clientTickOffset = -1;

		private BirdData(Occupant occupant) {
			this.occupant = occupant;
			this.ticksInNest = occupant.ticksInNest();
		}

		public boolean tick() {
			return this.ticksInNest++ > this.occupant.minTicksInNest;
		}

		public void clientTick(Level level) {
			if (clientTickOffset == -1) {
				clientTickOffset = level.getRandom().nextInt(1000);
			}
			if (entityInstance != null) {
				entityInstance.tickCount++;
				if (entityInstance instanceof StarfireBird bird) {
					bird.idleAnimationState.startIfStopped(bird.tickCount - clientTickOffset);
					bird.nestIdleAnimationState.startIfStopped(bird.tickCount - clientTickOffset);
				}
			}
		}

		public Occupant toOccupant() {
			return new Occupant(this.occupant.entityData, this.ticksInNest, this.occupant.minTicksInNest);
		}

		public Entity getOrCreateEntityInstance(Level level, BlockPos pos) {
			if (entityInstance == null) {
				entityInstance = occupant.createEntity(level, pos, false);
				if (entityInstance != null) {
					entityInstance.setXRot(0);
					entityInstance.xRotO = 0;
					entityInstance.setYRot(0);
					entityInstance.yRotO = 0;
					entityInstance.setYBodyRot(0);
					if (entityInstance instanceof LivingEntity living) {
						living.yBodyRotO = 0;
					}
					entityInstance.setYHeadRot(0);
					if (entityInstance instanceof LivingEntity living) {
						living.yHeadRotO = 0;
					}
				}
			}
			return entityInstance;
		}
	}

	public record Occupant(CustomData entityData, int ticksInNest, int minTicksInNest) {
		public static final Codec<Occupant> CODEC = RecordCodecBuilder.create((instance) -> instance.group(CustomData.CODEC.optionalFieldOf("entity_data", CustomData.EMPTY).forGetter(Occupant::entityData), Codec.INT.fieldOf("ticks_in_nest").forGetter(Occupant::ticksInNest), Codec.INT.fieldOf("min_ticks_in_nest").forGetter(Occupant::minTicksInNest)).apply(instance, Occupant::new));
		public static final Codec<List<Occupant>> LIST_CODEC = CODEC.listOf();
		public static final StreamCodec<ByteBuf, Occupant> STREAM_CODEC = StreamCodec.composite(CustomData.STREAM_CODEC, Occupant::entityData, ByteBufCodecs.VAR_INT, Occupant::ticksInNest, ByteBufCodecs.VAR_INT, Occupant::minTicksInNest, Occupant::new);

		public static Occupant of(Entity entity, int minTicksInNest) {
			CompoundTag compoundTag = new CompoundTag();
			entity.save(compoundTag);
			IGNORED_BIRD_TAGS.forEach(compoundTag::remove);
			return new Occupant(CustomData.of(compoundTag), 0, minTicksInNest);
		}

		public static Occupant create(RandomSource random, int ticksInNest) {
			CompoundTag compoundTag = new CompoundTag();
			compoundTag.putString("id", BuiltInRegistries.ENTITY_TYPE.getKey(ESEntities.STARFIRE_BIRD.get()).toString());
			if (random.nextInt(20) == 0) {
				compoundTag.putBoolean(StarfireBird.TAG_SPECIAL_VARIANT, true);
			}
			return new Occupant(CustomData.of(compoundTag), ticksInNest, 600);
		}

		@Nullable
		public Entity createEntity(Level level, BlockPos blockPos, boolean setData) {
			CompoundTag compoundTag = this.entityData.copyTag();
			IGNORED_BIRD_TAGS.forEach(compoundTag::remove);
			Entity entity = EntityType.loadEntityRecursive(compoundTag, level, e -> e);
			if (entity != null) {
				entity.setNoGravity(true);
				if (entity instanceof StarfireBird bird) {
					bird.setNestPos(blockPos);
					if (setData) {
						setBirdReleaseData(this.ticksInNest, bird);
					}
				}

				return entity;
			} else {
				return null;
			}
		}

		private static void setBirdReleaseData(int ticksInNest, StarfireBird bird) {
			int age = bird.getAge();
			if (age < 0) {
				bird.setAge(Math.min(0, age + ticksInNest));
			} else if (age > 0) {
				bird.setAge(Math.max(0, age - ticksInNest));
			}
			bird.setInLoveTime(Math.max(0, bird.getInLoveTime() - ticksInNest));
		}
	}
}
