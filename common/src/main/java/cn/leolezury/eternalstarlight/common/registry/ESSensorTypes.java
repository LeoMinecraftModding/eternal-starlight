package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.entity.living.animal.YetiAi;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.ai.sensing.TemptingSensor;

public class ESSensorTypes {
	public static final RegistrationProvider<SensorType<?>> SENSOR_TYPES = RegistrationProvider.get(Registries.SENSOR_TYPE, EternalStarlight.ID);
	public static final RegistryObject<SensorType<?>, SensorType<TemptingSensor>> YETI_TEMPTATIONS = SENSOR_TYPES.register("yeti_temptations", () -> new SensorType<>(() -> new TemptingSensor(YetiAi.getTemptations())));

	public static void loadClass() {
	}
}
