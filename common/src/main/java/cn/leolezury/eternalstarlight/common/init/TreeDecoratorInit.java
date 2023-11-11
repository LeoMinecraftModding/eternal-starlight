package cn.leolezury.eternalstarlight.common.init;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.util.register.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.util.register.RegistryObject;
import cn.leolezury.eternalstarlight.common.world.gen.feature.tree.TrunkBerriesDecorator;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class TreeDecoratorInit {
    public static final RegistrationProvider<TreeDecoratorType<?>> TREE_DECORATORS = RegistrationProvider.get(Registries.TREE_DECORATOR_TYPE, EternalStarlight.MOD_ID);
    public static final RegistryObject<TreeDecoratorType<TrunkBerriesDecorator>> TRUNK_BERRIES = TREE_DECORATORS.register("trunk_berries", () -> new TreeDecoratorType<>(TrunkBerriesDecorator.CODEC));
    public static void loadClass() {}
}
