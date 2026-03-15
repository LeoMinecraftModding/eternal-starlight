package cn.leolezury.eternalstarlight.common.enchantment.effect;

import cn.leolezury.eternalstarlight.common.registry.ESDataAttachments;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

public record IgniteAbyssalFire(LevelBasedValue duration) implements EnchantmentEntityEffect {
	public static final MapCodec<IgniteAbyssalFire> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(LevelBasedValue.CODEC.fieldOf("duration").forGetter((ignite) -> ignite.duration)).apply(instance, IgniteAbyssalFire::new));

	public void apply(ServerLevel level, int enchantmentLevel, EnchantedItemInUse item, Entity entity, Vec3 origin) {
		int abyssalFireTicks = ESDataAttachments.ABYSSAL_FIRE_TICKS.getData(entity);
		ESDataAttachments.ABYSSAL_FIRE_TICKS.setData(entity, Math.max(abyssalFireTicks, (int) this.duration.calculate(enchantmentLevel) * 20));
	}

	public MapCodec<IgniteAbyssalFire> codec() {
		return CODEC;
	}
}