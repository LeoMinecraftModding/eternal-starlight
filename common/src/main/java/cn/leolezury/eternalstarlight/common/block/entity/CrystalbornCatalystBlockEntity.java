package cn.leolezury.eternalstarlight.common.block.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.CrystalbornCatalystBlock;
import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.item.menu.CrystalbornCatalystMenu;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.particle.ExplosionShockParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class CrystalbornCatalystBlockEntity extends BaseContainerBlockEntity {
	private NonNullList<ItemStack> items = NonNullList.withSize(15, ItemStack.EMPTY);

	private static final String TAG_CURSOR_COUNT = "cursor_count";
	private static final String TAG_CURSOR = "cursor";
	private static final String TAG_ENERGY_LEFT = "energy_left";

	private int energyLeft = 0;
	private int charge = 0;
	private final List<Cursor> cursors = new ArrayList<>();
	private final RandomSource random;

	protected final ContainerData dataAccess = new ContainerData() {
		@Override
		public int get(int index) {
			return charge;
		}

		@Override
		public void set(int index, int value) {
			charge = value;
		}

		@Override
		public int getCount() {
			return 1;
		}
	};

	public CrystalbornCatalystBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(ESBlockEntities.CRYSTALBORN_CATALYST.get(), blockPos, blockState);
		this.random = new LegacyRandomSource(blockPos.asLong());
	}

	public static void tick(Level level, BlockPos pos, BlockState state, CrystalbornCatalystBlockEntity entity) {
		if (level instanceof ServerLevel serverLevel) {
			if (state.getValue(CrystalbornCatalystBlock.LIT)) {
				entity.cursors.removeIf(cursor -> cursor.energyLeft <= 0 || cursor.pos.distSqr(pos) > ESConfig.INSTANCE.itemsConfig.crystalbornCatalyst.maxRange() * ESConfig.INSTANCE.itemsConfig.crystalbornCatalyst.maxRange());
				if (entity.energyLeft > 0 && entity.cursors.size() < 8) {
					int energy = Math.min(entity.random.nextInt(10, 20), entity.energyLeft);
					entity.cursors.add(new Cursor(pos, Direction.getRandom(entity.random), energy));
					entity.energyLeft -= energy;
				}
				for (Cursor cursor : entity.cursors) {
					cursor.tick(serverLevel, stack -> {
						int size = entity.getContainerSize();
						ItemStack remains = stack;
						for (int i = 1; i < size && !stack.isEmpty(); i++) {
							remains = tryMoveInItem(entity, remains, i);
						}
						if (!remains.isEmpty()) {
							Block.popResource(level, pos, remains);
						}
					});
				}
				if (isFuel(entity.items.getFirst())) {
					entity.charge++;
				} else {
					entity.charge--;
				}
				entity.charge = Math.clamp(entity.charge, 0, 30);
				if (entity.charge == 30) {
					entity.items.getFirst().shrink(1);
					entity.energyLeft += ESConfig.INSTANCE.itemsConfig.crystalbornCatalyst.energyPerShard();
					for (int i = 0; i <= 10; i++) {
						ESPlatform.INSTANCE.sendToAllClients(serverLevel, new ParticlePacket(ExplosionShockParticleOptions.CRYSTAL, pos.getCenter().x + (entity.random.nextFloat() - 0.5f) * 4, pos.getY(), pos.getCenter().z + (entity.random.nextFloat() - 0.5f) * 4, 0, 1, 0));
					}
					entity.charge = 0;
					entity.setChanged();
				}
			} else {
				entity.charge = 0;
			}
		}
	}

	private static ItemStack tryMoveInItem(Container destination, ItemStack stack, int slot) {
		ItemStack destItem = destination.getItem(slot);
		if (destination.canPlaceItem(slot, stack)) {
			boolean changed = false;
			if (destItem.isEmpty()) {
				destination.setItem(slot, stack);
				stack = ItemStack.EMPTY;
				changed = true;
			} else if (canMergeItems(destItem, stack)) {
				int freeCount = stack.getMaxStackSize() - destItem.getCount();
				int diff = Math.min(stack.getCount(), freeCount);
				stack.shrink(diff);
				destItem.grow(diff);
				changed = diff > 0;
			}
			if (changed) {
				destination.setChanged();
			}
		}

		return stack;
	}

	private static boolean canMergeItems(ItemStack stack1, ItemStack stack2) {
		return stack1.getCount() <= stack1.getMaxStackSize() && ItemStack.isSameItemSameComponents(stack1, stack2);
	}

	public static boolean isFuel(ItemStack stack) {
		return stack.is(ESTags.Items.CRYSTALBORN_CATALYST_FUELS);
	}

	@Override
	public boolean canPlaceItem(int index, ItemStack stack) {
		if (index == 0 && !isFuel(stack)) {
			return false;
		}
		return super.canPlaceItem(index, stack);
	}

	@Override
	public int getContainerSize() {
		return 15;
	}

	private static class Cursor {
		private static final String TAG_POS_X = "pos_x";
		private static final String TAG_POS_Y = "pos_y";
		private static final String TAG_POS_Z = "pos_z";
		private static final String TAG_DIRECTION = "direction";
		private static final String TAG_ENERGY_LEFT = "energy_left";

		public BlockPos pos;
		public Direction direction;
		public int energyLeft;

		public Cursor(BlockPos pos, Direction direction, int energy) {
			this.pos = pos;
			this.direction = direction;
			this.energyLeft = energy;
		}

		public void tick(ServerLevel level, Consumer<ItemStack> dropsConsumer) {
			if (energyLeft <= 0) {
				return;
			}
			BlockPos targetPos = pos.relative(direction);
			BlockState targetState = level.getBlockState(targetPos);
			if (targetState.is(ESBlocks.CRYSTALLIZED_SAND.get()) || targetState.is(ESBlocks.RED_CRYSTAL_MOSS_BLOCK.get()) || targetState.is(ESBlocks.BLUE_CRYSTAL_MOSS_BLOCK.get())) {
				pos = targetPos;
				if (level.getRandom().nextInt(3) == 0) {
					direction = Direction.getRandom(level.getRandom());
				}
			} else if (targetState.is(ESTags.Blocks.CRYSTALBORN_CATALYST_REPLACEABLES)) {
				LootParams.Builder builder = new LootParams.Builder(level).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(targetPos)).withParameter(LootContextParams.TOOL, Items.DIAMOND_PICKAXE.getDefaultInstance()).withOptionalParameter(LootContextParams.BLOCK_ENTITY, targetState.hasBlockEntity() ? level.getBlockEntity(targetPos) : null);
				List<ItemStack> drops = targetState.getDrops(builder);
				if (level.destroyBlock(targetPos, false)) {
					level.setBlockAndUpdate(targetPos, targetState.is(ESTags.Blocks.CRYSTALBORN_CATALYST_MOSS_REPLACEABLES) ? ESBlocks.RED_CRYSTAL_MOSS_BLOCK.get().defaultBlockState() : ESBlocks.CRYSTALLIZED_SAND.get().defaultBlockState());
					drops.forEach(dropsConsumer);
					pos = targetPos;
					energyLeft -= 1;
					List<Direction> preferredDirs = new ArrayList<>(Arrays.stream(Direction.values()).toList());
					preferredDirs.removeIf(dir -> !level.getBlockState(pos.relative(dir)).is(ESTags.Blocks.CRYSTALBORN_CATALYST_PREFERENCES));
					List<Direction> availableDirs = new ArrayList<>(Arrays.stream(Direction.values()).toList());
					availableDirs.removeIf(dir -> !level.getBlockState(pos.relative(dir)).is(ESTags.Blocks.CRYSTALBORN_CATALYST_REPLACEABLES));
					if (!preferredDirs.isEmpty()) {
						direction = preferredDirs.get(level.getRandom().nextInt(preferredDirs.size()));
					} else if (!availableDirs.isEmpty()) {
						direction = availableDirs.get(level.getRandom().nextInt(availableDirs.size()));
					}
				} else {
					direction = Direction.getRandom(level.getRandom());
				}
			} else {
				direction = Direction.getRandom(level.getRandom());
			}
		}

		public static Cursor fromTag(CompoundTag tag) {
			return new Cursor(new BlockPos(tag.getInt(TAG_POS_X), tag.getInt(TAG_POS_Y), tag.getInt(TAG_POS_Z)), Direction.from3DDataValue(tag.getInt(TAG_DIRECTION)), tag.getInt(TAG_ENERGY_LEFT));
		}

		public CompoundTag toTag() {
			CompoundTag tag = new CompoundTag();
			tag.putInt(TAG_POS_X, pos.getX());
			tag.putInt(TAG_POS_Y, pos.getY());
			tag.putInt(TAG_POS_Z, pos.getZ());
			tag.putInt(TAG_DIRECTION, direction.get3DDataValue());
			tag.putInt(TAG_ENERGY_LEFT, energyLeft);
			return tag;
		}
	}

	@Override
	public void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
		super.loadAdditional(compoundTag, provider);
		int count = compoundTag.getInt(TAG_CURSOR_COUNT);
		for (int i = 0; i < count; i++) {
			this.cursors.add(Cursor.fromTag(compoundTag.getCompound(TAG_CURSOR + i)));
		}
		this.energyLeft = compoundTag.getInt(TAG_ENERGY_LEFT);
		ContainerHelper.loadAllItems(compoundTag, this.items, provider);
	}

	@Override
	protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
		super.saveAdditional(compoundTag, provider);
		compoundTag.putInt(TAG_CURSOR_COUNT, cursors.size());
		for (int i = 0; i < cursors.size(); i++) {
			compoundTag.put(TAG_CURSOR + i, cursors.get(i).toTag());
		}
		compoundTag.putInt(TAG_ENERGY_LEFT, this.energyLeft);
		ContainerHelper.saveAllItems(compoundTag, this.items, provider);
	}

	@Override
	protected Component getDefaultName() {
		return Component.translatable("container." + EternalStarlight.ID + ".crystalborn_catalyst");
	}

	@Override
	protected NonNullList<ItemStack> getItems() {
		return items;
	}

	@Override
	protected void setItems(NonNullList<ItemStack> items) {
		this.items = items;
	}

	@Override
	protected AbstractContainerMenu createMenu(int id, Inventory player) {
		return new CrystalbornCatalystMenu(id, player.player.getInventory(), this, dataAccess);
	}
}
