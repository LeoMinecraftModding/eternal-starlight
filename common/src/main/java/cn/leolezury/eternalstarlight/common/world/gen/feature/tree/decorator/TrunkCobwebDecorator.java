package cn.leolezury.eternalstarlight.common.world.gen.feature.tree.decorator;

import cn.leolezury.eternalstarlight.common.registry.ESTreeDecorators;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class TrunkCobwebDecorator extends TreeDecorator {
    public static final MapCodec<TrunkCobwebDecorator> CODEC = MapCodec.unit(() -> TrunkCobwebDecorator.INSTANCE);
    public static final TrunkCobwebDecorator INSTANCE = new TrunkCobwebDecorator();
    
    @Override
    protected TreeDecoratorType<?> type() {
        return ESTreeDecorators.TRUNK_COBWEB.get();
    }

    @Override
    public void place(Context context) {
        RandomSource random = context.random();
        context.leaves().forEach((pos) -> {
            boolean nearLog = false;
            for (Direction direction : Direction.values()) {
                if (context.logs().contains(pos.relative(direction))) {
                    nearLog = true;
                    break;
                }
            }
            if (nearLog) {
                if (context.isAir(pos.relative(Direction.DOWN)) && random.nextInt(5) == 0) {
                    context.setBlock(pos.relative(Direction.DOWN), Blocks.COBWEB.defaultBlockState());
                }
            }
        });
    }
}
