package cn.leolezury.eternalstarlight.common.codecs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;

import java.util.List;

public record SwampSilverRemovableEffects(List<Holder<MobEffect>> effects) {
	public static final Codec<SwampSilverRemovableEffects> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		BuiltInRegistries.MOB_EFFECT.holderByNameCodec().listOf().fieldOf("effects").forGetter(SwampSilverRemovableEffects::effects)
	).apply(instance, SwampSilverRemovableEffects::new));
}
