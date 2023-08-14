//package cn.leolezury.eternalstarlight.block;
//
//import cn.leolezury.eternalstarlight.init.BlockInit;
//import net.minecraft.core.BlockPos;
//import net.minecraft.core.Direction;
//import net.minecraft.world.item.AxeItem;
//import net.minecraft.world.item.context.UseOnContext;
//import net.minecraft.world.level.BlockGetter;
//import net.minecraft.world.level.block.RotatedPillarBlock;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraftforge.common.ToolAction;
//import org.jetbrains.annotations.Nullable;
//
//public class SLFlammableRotatedPillarBlock extends RotatedPillarBlock {
//    public SLFlammableRotatedPillarBlock(Properties properties) {
//        super(properties);
//    }
//
//    @Override
//    public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
//        return true;
//    }
//
//    @Override
//    public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
//        return 5;
//    }
//
//    @Override
//    public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
//        return 5;
//    }
//
//    @Nullable
//    @Override
//    public BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
//        if (context.getItemInHand().getItem() instanceof AxeItem) {
//            if (state.is(BlockInit.LUNAR_LOG.get())) {
//                return BlockInit.STRIPPED_LUNAR_LOG.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
//            }
//            if (state.is(BlockInit.LUNAR_WOOD.get())) {
//                return BlockInit.STRIPPED_LUNAR_WOOD.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
//            }
//            if (state.is(BlockInit.NORTHLAND_LOG.get())) {
//                return BlockInit.STRIPPED_NORTHLAND_LOG.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
//            }
//            if (state.is(BlockInit.NORTHLAND_WOOD.get())) {
//                return BlockInit.STRIPPED_NORTHLAND_WOOD.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
//            }
//            if (state.is(BlockInit.STARLIGHT_MANGROVE_LOG.get())) {
//                return BlockInit.STRIPPED_STARLIGHT_MANGROVE_LOG.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
//            }
//            if (state.is(BlockInit.STARLIGHT_MANGROVE_WOOD.get())) {
//                return BlockInit.STRIPPED_STARLIGHT_MANGROVE_WOOD.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
//            }
//        }
//        return super.getToolModifiedState(state, context, toolAction, simulate);
//    }
//}
