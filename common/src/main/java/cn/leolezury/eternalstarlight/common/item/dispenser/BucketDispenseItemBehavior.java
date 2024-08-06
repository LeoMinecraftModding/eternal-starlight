package cn.leolezury.eternalstarlight.common.item.dispenser;

import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.world.item.DispensibleContainerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;

public class BucketDispenseItemBehavior extends DefaultDispenseItemBehavior {
	private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

	@Override
	public ItemStack execute(BlockSource blockSource, ItemStack item) {
		DispensibleContainerItem dispensibleContainerItem = (DispensibleContainerItem) item.getItem();
		BlockPos blockpos = blockSource.pos().relative(blockSource.state().getValue(DispenserBlock.FACING));
		Level level = blockSource.level();
		if (dispensibleContainerItem.emptyContents(null, level, blockpos, null)) {
			dispensibleContainerItem.checkExtraContent(null, level, item, blockpos);
			return this.consumeWithRemainder(blockSource, item, new ItemStack(Items.BUCKET));
		} else {
			return this.defaultDispenseItemBehavior.dispense(blockSource, item);
		}
	}
}
