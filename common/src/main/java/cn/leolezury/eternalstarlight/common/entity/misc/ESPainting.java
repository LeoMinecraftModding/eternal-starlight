package cn.leolezury.eternalstarlight.common.entity.misc;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.data.ESPaintingVariant;
import cn.leolezury.eternalstarlight.common.data.ESPaintingVariants;
import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ESPainting extends HangingEntity {
	public static final String TAG_VARIANT = "variant";
	private static final String TAG_ITEM = "item";
	private static final String TAG_FACING = "facing";

	private static final EntityDataAccessor<String> DATA_VARIANT = SynchedEntityData.defineId(ESPainting.class, EntityDataSerializers.STRING);
	private static final EntityDataAccessor<ItemStack> DATA_ITEM = SynchedEntityData.defineId(ESPainting.class, EntityDataSerializers.ITEM_STACK);

	public ESPainting(Level level, BlockPos blockPos) {
		super(ESEntities.PAINTING.get(), level, blockPos);
	}

	public ESPainting(EntityType<? extends ESPainting> type, Level level) {
		super(type, level);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		builder.define(DATA_VARIANT, ESPaintingVariants.GUARDIAN.location().toString());
		builder.define(DATA_ITEM, ESItems.STARLIT_PAINTING.get().getDefaultInstance());
	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
		// [Vanilla copy] net.minecraft.world.entity.decoration.Painting#onSyncedDataUpdated
		if (DATA_VARIANT.equals(key)) {
			this.recalculateBoundingBox();
		}
		super.onSyncedDataUpdated(key);
	}

	public ResourceLocation getVariantId() {
		return ResourceLocation.parse(this.getEntityData().get(DATA_VARIANT));
	}

	public void setVariantId(ResourceLocation variantId) {
		this.getEntityData().set(DATA_VARIANT, variantId.toString());
	}

	public void setVariant(Holder<ESPaintingVariant> variant) {
		variant.unwrapKey().ifPresent(key -> setVariantId(key.location()));
	}

	public Holder<ESPaintingVariant> getVariant() {
		Registry<ESPaintingVariant> registry = level().registryAccess().registryOrThrow(ESRegistries.PAINTING_VARIANT);
		return registry.getHolder(getVariantId())
			.or(() -> registry.getHolder(ESPaintingVariants.GUARDIAN))
			.orElseGet(() -> registry.getAny().orElseThrow());
	}

	public static Optional<ESPainting> createPainting(Level level, ItemStack item, BlockPos blockPos, Direction direction) {
		ESPainting painting = new ESPainting(level, blockPos);
		List<Holder<ESPaintingVariant>> candidates = new ArrayList<>();
		level.registryAccess().registryOrThrow(ESRegistries.PAINTING_VARIANT).getTagOrEmpty(ESTags.PaintingVariants.PLACEABLE).forEach(candidates::add);
		if (candidates.isEmpty()) {
			return Optional.empty();
		}
		painting.setDirection(direction);
		candidates.removeIf(holder -> {
			painting.setVariant(holder);
			return !painting.survives();
		});
		if (candidates.isEmpty()) {
			return Optional.empty();
		}
		int maxArea = candidates.stream().mapToInt(holder -> holder.value().area()).max().orElse(0);
		candidates.removeIf(holder -> holder.value().area() < maxArea);
		Optional<Holder<ESPaintingVariant>> chosen = Util.getRandomSafe(candidates, painting.getRandom());
		if (chosen.isEmpty()) {
			return Optional.empty();
		}
		painting.setVariant(chosen.get());
		painting.setDirection(direction);
		painting.setItem(item);
		return Optional.of(painting);
	}

	public void setItem(ItemStack item) {
		this.getEntityData().set(DATA_ITEM, item.copy());
	}

	public ItemStack getItem() {
		return this.getEntityData().get(DATA_ITEM);
	}

	public static CompoundTag paintingData(ResourceKey<ESPaintingVariant> variant) {
		CompoundTag tag = new CompoundTag();
		tag.putString("id", EternalStarlight.ID + ":painting");
		tag.putString(TAG_VARIANT, variant.location().toString());
		return tag;
	}

	// [Vanilla copy] net.minecraft.world.entity.decoration.Painting#calculateBoundingBox
	@Override
	protected AABB calculateBoundingBox(BlockPos pos, Direction direction) {
		float f = 0.46875F;
		Vec3 vec3 = Vec3.atCenterOf(pos).relative(direction, -0.46875);
		ESPaintingVariant variant = this.getVariant().value();
		double d0 = this.offsetForPaintingSize(variant.width());
		double d1 = this.offsetForPaintingSize(variant.height());
		Direction counterClockWise = direction.getCounterClockWise();
		Vec3 vec31 = vec3.relative(counterClockWise, d0).relative(Direction.UP, d1);
		Direction.Axis axis = direction.getAxis();
		double d2 = axis == Direction.Axis.X ? 0.0625 : (double) variant.width();
		double d3 = (double) variant.height();
		double d4 = axis == Direction.Axis.Z ? 0.0625 : (double) variant.width();
		return AABB.ofSize(vec31, d2, d3, d4);
	}

	// [Vanilla copy] net.minecraft.world.entity.decoration.Painting#offsetForPaintingSize
	private double offsetForPaintingSize(int size) {
		return size % 2 == 0 ? 0.5 : 0.0;
	}

	// [Vanilla copy] net.minecraft.world.entity.decoration.Painting#getAddEntityPacket
	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket(ServerEntity entity) {
		return new ClientboundAddEntityPacket(this, this.direction.get3DDataValue(), this.getPos());
	}

	// [Vanilla copy] net.minecraft.world.entity.decoration.Painting#recreateFromPacket
	@Override
	public void recreateFromPacket(ClientboundAddEntityPacket packet) {
		super.recreateFromPacket(packet);
		this.setDirection(Direction.from3DDataValue(packet.getData()));
	}

	// [Vanilla copy] net.minecraft.world.entity.decoration.Painting#moveTo
	@Override
	public void moveTo(double x, double y, double z, float yaw, float pitch) {
		this.setPos(x, y, z);
	}

	// [Vanilla copy] net.minecraft.world.entity.decoration.Painting#lerpTo
	@Override
	public void lerpTo(double x, double y, double z, float yRot, float xRot, int steps) {
		this.setPos(x, y, z);
	}

	// [Vanilla copy] net.minecraft.world.entity.decoration.Painting#trackingPosition
	@Override
	public Vec3 trackingPosition() {
		return Vec3.atLowerCornerOf(this.pos);
	}

	// [Vanilla copy] net.minecraft.world.entity.decoration.Painting#playPlacementSound
	@Override
	public void playPlacementSound() {
		this.playSound(SoundEvents.PAINTING_PLACE, 1.0F, 1.0F);
	}

	@Override
	public void dropItem(@Nullable Entity entity) {
		if (this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
			this.playSound(SoundEvents.PAINTING_BREAK, 1.0F, 1.0F);
			if (entity instanceof Player player && player.hasInfiniteMaterials()) {
				return;
			}
			this.spawnAtLocation(getItem());
		}
	}

	@Override
	public ItemStack getPickResult() {
		return getItem().copy();
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putByte(TAG_FACING, (byte) this.direction.get2DDataValue());
		compoundTag.putString(TAG_VARIANT, getVariantId().toString());
		compoundTag.put(TAG_ITEM, this.getItem().save(this.registryAccess()));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.direction = Direction.from2DDataValue(compoundTag.getByte(TAG_FACING));
		ResourceLocation variantId = ResourceLocation.tryParse(compoundTag.getString(TAG_VARIANT));
		if (variantId != null) {
			setVariantId(variantId);
		}
		this.setDirection(this.direction);
		if (compoundTag.contains(TAG_ITEM, CompoundTag.TAG_COMPOUND)) {
			this.setItem(ItemStack.parse(this.registryAccess(), compoundTag.getCompound(TAG_ITEM)).orElse(ESItems.STARLIT_PAINTING.get().getDefaultInstance()));
		} else {
			this.setItem(ESItems.STARLIT_PAINTING.get().getDefaultInstance());
		}
	}
}
