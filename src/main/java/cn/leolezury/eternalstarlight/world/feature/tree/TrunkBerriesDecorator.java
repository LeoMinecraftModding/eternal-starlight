package cn.leolezury.eternalstarlight.world.feature.tree;

import cn.leolezury.eternalstarlight.block.BerriesVineBlock;
import cn.leolezury.eternalstarlight.block.BerriesVinePlantBlock;
import cn.leolezury.eternalstarlight.init.BlockInit;
import cn.leolezury.eternalstarlight.init.TreeDecoratorInit;
import com.mojang.serialization.Codec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class TrunkBerriesDecorator extends TreeDecorator {
    public static final Codec<TrunkBerriesDecorator> CODEC = Codec.unit(() -> TrunkBerriesDecorator.INSTANCE);
    public static final TrunkBerriesDecorator INSTANCE = new TrunkBerriesDecorator();

    protected TreeDecoratorType<?> type() {
        return TreeDecoratorInit.TRUNK_BERRIES.get();
    }

    public void place(TreeDecorator.Context context) {
        RandomSource randomsource = context.random();
        context.logs().forEach((pos) -> {
            int l = randomsource.nextInt(6) + 5;
            for (int i = 1; i <= l; i++) {
                if (context.isAir(pos.below(i))) {
                    context.setBlock(pos.below(i), BlockInit.BERRIES_VINES_PLANT.get().defaultBlockState().setValue(BerriesVinePlantBlock.BERRIES, randomsource.nextInt(4) == 0));
                    if (i == l) {
                        context.setBlock(pos.below(i), BlockInit.BERRIES_VINES.get().defaultBlockState().setValue(BerriesVineBlock.BERRIES, randomsource.nextInt(4) == 0));
                    }
                } else {
                    if (i != 1) {
                        context.setBlock(pos.below(i - 1), BlockInit.BERRIES_VINES.get().defaultBlockState().setValue(BerriesVineBlock.BERRIES, randomsource.nextInt(4) == 0));
                    }
                    break;
                }
            }
        });
    }
}
