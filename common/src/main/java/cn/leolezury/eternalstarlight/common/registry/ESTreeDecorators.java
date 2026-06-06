package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import cn.leolezury.eternalstarlight.common.world.gen.feature.tree.decorator.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class ESTreeDecorators {
	public static final RegistrationProvider<TreeDecoratorType<?>> TREE_DECORATORS = RegistrationProvider.get(Registries.TREE_DECORATOR_TYPE, EternalStarlight.ID);
	public static final RegistryObject<TreeDecoratorType<?>, TreeDecoratorType<TrunkBerriesDecorator>> TRUNK_BERRIES = TREE_DECORATORS.register("trunk_berries", () -> new TreeDecoratorType<>(TrunkBerriesDecorator.CODEC));
	public static final RegistryObject<TreeDecoratorType<?>, TreeDecoratorType<TrunkCobwebDecorator>> TRUNK_COBWEB = TREE_DECORATORS.register("trunk_cobweb", () -> new TreeDecoratorType<>(TrunkCobwebDecorator.CODEC));
	public static final RegistryObject<TreeDecoratorType<?>, TreeDecoratorType<TorreyaVinesDecorator>> TORREYA_VINES = TREE_DECORATORS.register("torreya_vines", () -> new TreeDecoratorType<>(TorreyaVinesDecorator.CODEC));
	public static final RegistryObject<TreeDecoratorType<?>, TreeDecoratorType<BanyinRootsDecorator>> BANYIN_ROOTS = TREE_DECORATORS.register("banyin_roots", () -> new TreeDecoratorType<>(BanyinRootsDecorator.CODEC));
	public static final RegistryObject<TreeDecoratorType<?>, TreeDecoratorType<HangingPlantDecorator>> HANGING_PLANT = TREE_DECORATORS.register("hanging_plant", () -> new TreeDecoratorType<>(HangingPlantDecorator.CODEC));
	public static final RegistryObject<TreeDecoratorType<?>, TreeDecoratorType<StarfireBirdNestDecorator>> STARFIRE_BIRD_NEST = TREE_DECORATORS.register("starfire_bird_nest", () -> new TreeDecoratorType<>(StarfireBirdNestDecorator.CODEC));
	public static final RegistryObject<TreeDecoratorType<?>, TreeDecoratorType<AttachedToLogsDecorator>> ATTACHED_TO_LOGS = TREE_DECORATORS.register("log_top", () -> new TreeDecoratorType<>(AttachedToLogsDecorator.CODEC));

	public static void loadClass() {
	}
}
