package cn.leolezury.eternalstarlight.common.critereon;

import cn.leolezury.eternalstarlight.common.data.ESDimensions;
import cn.leolezury.eternalstarlight.common.handler.CommonHandlers;
import cn.leolezury.eternalstarlight.common.registry.ESWeathers;
import cn.leolezury.eternalstarlight.common.weather.AbstractWeather;
import cn.leolezury.eternalstarlight.common.weather.WeatherInstance;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

public class WitnessWeatherTrigger extends SimpleCriterionTrigger<WitnessWeatherTrigger.TriggerInstance> {
	@Override
	public Codec<TriggerInstance> codec() {
		return WitnessWeatherTrigger.TriggerInstance.CODEC;
	}

	public void trigger(ServerPlayer serverPlayer) {
		this.trigger(serverPlayer, (triggerInstance) -> triggerInstance.matches(serverPlayer.serverLevel()));
	}

	public record TriggerInstance(Optional<ContextAwarePredicate> player, Holder<AbstractWeather> weather) implements SimpleCriterionTrigger.SimpleInstance {
		public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create((instance) -> {
			return instance.group(EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player), ESWeathers.WEATHERS.registry().holderByNameCodec().fieldOf("weather").forGetter(TriggerInstance::weather)).apply(instance, TriggerInstance::new);
		});

		public boolean matches(ServerLevel serverLevel) {
			Optional<WeatherInstance> active = CommonHandlers.getActiveWeather();
			return serverLevel.dimension().location().equals(ESDimensions.STARLIGHT_KEY.location())
				&& active.isPresent() && weather().isBound() && active.get().getWeather() == weather().value();
		}
	}
}
