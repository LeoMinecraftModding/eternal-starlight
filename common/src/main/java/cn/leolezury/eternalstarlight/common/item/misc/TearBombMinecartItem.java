package cn.leolezury.eternalstarlight.common.item.misc;

import cn.leolezury.eternalstarlight.common.entity.misc.TearBombMinecart;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RailShape;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;
import net.minecraft.world.phys.Vec3;

public class TearBombMinecartItem extends Item {
	private static final DispenseItemBehavior DISPENSE_ITEM_BEHAVIOR = new DefaultDispenseItemBehavior() {
		private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

		@Override
		public ItemStack execute(BlockSource blockSource, ItemStack itemStack) {
			Direction direction = blockSource.state().getValue(DispenserBlock.FACING);
			ServerLevel serverLevel = blockSource.level();
			Vec3 vec3 = blockSource.center();
			double d = vec3.x() + direction.getStepX() * 1.125F;
			double e = Math.floor(vec3.y()) + direction.getStepY();
			double f = vec3.z() + direction.getStepZ() * 1.125F;
			BlockPos blockPos = blockSource.pos().relative(direction);
			BlockState blockState = serverLevel.getBlockState(blockPos);
			RailShape railShape = blockState.getBlock() instanceof BaseRailBlock ? blockState.getValue(((BaseRailBlock) blockState.getBlock()).getShapeProperty()) : RailShape.NORTH_SOUTH;
			double g;
			if (blockState.is(BlockTags.RAILS)) {
				if (railShape.isAscending()) {
					g = 0.6;
				} else {
					g = 0.1;
				}
			} else {
				if (!blockState.isAir() || !serverLevel.getBlockState(blockPos.below()).is(BlockTags.RAILS)) {
					return this.defaultDispenseItemBehavior.dispense(blockSource, itemStack);
				}

				BlockState blockState2 = serverLevel.getBlockState(blockPos.below());
				RailShape railShape2 = blockState2.getBlock() instanceof BaseRailBlock ? blockState2.getValue(((BaseRailBlock) blockState2.getBlock()).getShapeProperty()) : RailShape.NORTH_SOUTH;
				if (direction != Direction.DOWN && railShape2.isAscending()) {
					g = -0.4;
				} else {
					g = -0.9;
				}
			}

			AbstractMinecart minecart = new TearBombMinecart(serverLevel, d, e + g, f);
			serverLevel.addFreshEntity(minecart);
			itemStack.shrink(1);
			return itemStack;
		}

		@Override
		protected void playSound(BlockSource blockSource) {
			blockSource.level().levelEvent(LevelEvent.SOUND_DISPENSER_DISPENSE, blockSource.pos(), 0);
		}
	};

	public TearBombMinecartItem(Item.Properties properties) {
		super(properties);
		DispenserBlock.registerBehavior(this, DISPENSE_ITEM_BEHAVIOR);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		Level level = context.getLevel();
		BlockPos blockPos = context.getClickedPos();
		BlockState blockState = level.getBlockState(blockPos);
		if (!blockState.is(BlockTags.RAILS)) {
			return InteractionResult.FAIL;
		} else {
			ItemStack itemStack = context.getItemInHand();
			if (level instanceof ServerLevel serverLevel) {
				RailShape railShape = blockState.getBlock() instanceof BaseRailBlock ? blockState.getValue(((BaseRailBlock) blockState.getBlock()).getShapeProperty()) : RailShape.NORTH_SOUTH;
				double d = 0.0F;
				if (railShape.isAscending()) {
					d = 0.5F;
				}

				TearBombMinecart minecart = new TearBombMinecart(serverLevel, blockPos.getX() + 0.5F, blockPos.getY() + 0.0625F + d, blockPos.getZ() + 0.5F);
				serverLevel.addFreshEntity(minecart);
				serverLevel.gameEvent(GameEvent.ENTITY_PLACE, blockPos, Context.of(context.getPlayer(), serverLevel.getBlockState(blockPos.below())));
			}

			itemStack.shrink(1);
			return InteractionResult.sidedSuccess(level.isClientSide);
		}
	}
}
