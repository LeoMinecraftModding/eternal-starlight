package cn.leolezury.eternalstarlight.common.init;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import cn.leolezury.eternalstarlight.common.world.gen.feature.tree.decorator.TorreyaVinesDecorator;
import cn.leolezury.eternalstarlight.common.world.gen.feature.tree.decorator.TrunkBerriesDecorator;
import cn.leolezury.eternalstarlight.common.world.gen.feature.tree.decorator.TrunkCobwebDecorator;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class ESTreeDecorators {
    public static final RegistrationProvider<TreeDecoratorType<?>> TREE_DECORATORS = RegistrationProvider.get(Registries.TREE_DECORATOR_TYPE, EternalStarlight.MOD_ID);
    public static final RegistryObject<TreeDecoratorType<?>, TreeDecoratorType<TrunkBerriesDecorator>> TRUNK_BERRIES = TREE_DECORATORS.register("trunk_berries", () -> new TreeDecoratorType<>(TrunkBerriesDecorator.CODEC));
    public static final RegistryObject<TreeDecoratorType<?>, TreeDecoratorType<TrunkCobwebDecorator>> TRUNK_COBWEB = TREE_DECORATORS.register("trunk_cobweb", () -> new TreeDecoratorType<>(TrunkCobwebDecorator.CODEC));
    public static final RegistryObject<TreeDecoratorType<?>, TreeDecoratorType<TorreyaVinesDecorator>> TORRETA_VINES = TREE_DECORATORS.register("torreya_vines", () -> new TreeDecoratorType<>(TorreyaVinesDecorator.CODEC));
    public static void loadClass() {}
}
