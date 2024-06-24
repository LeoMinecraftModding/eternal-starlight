package cn.leolezury.eternalstarlight.common.enchantment.effects;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

public record PushTowardsEntity(LevelBasedValue minSpeed, LevelBasedValue maxSpeed) implements EnchantmentEntityEffect {
	public static final MapCodec<PushTowardsEntity> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(LevelBasedValue.CODEC.fieldOf("min_speed").forGetter(PushTowardsEntity::minSpeed), LevelBasedValue.CODEC.fieldOf("max_speed").forGetter(PushTowardsEntity::maxSpeed)).apply(instance, PushTowardsEntity::new));

	public void apply(ServerLevel serverLevel, int i, EnchantedItemInUse enchantedItemInUse, Entity entity, Vec3 vec3) {
		if (enchantedItemInUse.owner() != null) {
			float speed = Math.max(0, Mth.randomBetween(entity.getRandom(), this.minSpeed.calculate(i), this.maxSpeed.calculate(i)));
			Vec3 velocity = entity.position().subtract(enchantedItemInUse.owner().position()).normalize().scale(speed);
			enchantedItemInUse.owner().hurtMarked = true;
			enchantedItemInUse.owner().setDeltaMovement(enchantedItemInUse.owner().getDeltaMovement().add(velocity));
		}
	}

	public MapCodec<PushTowardsEntity> codec() {
		return CODEC;
	}
}