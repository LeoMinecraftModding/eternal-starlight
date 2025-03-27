package cn.leolezury.eternalstarlight.common.entity.projectile;

import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

public class MalariteArrow extends AbstractArrow {
	private static final String TAG_DURATION = "duration";
	private int duration = 200;

	public MalariteArrow(EntityType<? extends MalariteArrow> entityType, Level level) {
		super(entityType, level);
	}

	public MalariteArrow(Level level, LivingEntity livingEntity, ItemStack itemStack, @Nullable ItemStack itemStack2) {
		super(ESEntities.MALARITE_ARROW.get(), livingEntity, level, itemStack, itemStack2);
	}

	public MalariteArrow(Level level, double d, double e, double f, ItemStack itemStack, @Nullable ItemStack itemStack2) {
		super(ESEntities.MALARITE_ARROW.get(), d, e, f, level, itemStack, itemStack2);
	}

	@Override
	public void tick() {
		super.tick();
		if (this.level().isClientSide && !this.inGround) {
			this.level().addParticle(new DustColorTransitionOptions(new Vector3f(170 / 255f, 81 / 255f, 158 / 255f), new Vector3f(42 / 255f, 61 / 255f, 56 / 255f), 1f), this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
		}
	}

	@Override
	protected void doPostHurtEffects(LivingEntity livingEntity) {
		livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON, this.duration, 0), this.getEffectSource());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		if (compoundTag.contains(TAG_DURATION)) {
			this.duration = compoundTag.getInt(TAG_DURATION);
		}
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putInt(TAG_DURATION, this.duration);
	}

	@Override
	protected ItemStack getDefaultPickupItem() {
		return ESItems.MALARITE_ARROW.get().getDefaultInstance();
	}
}
