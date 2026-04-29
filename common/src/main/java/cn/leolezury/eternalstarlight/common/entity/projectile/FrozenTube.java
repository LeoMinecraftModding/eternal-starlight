package cn.leolezury.eternalstarlight.common.entity.projectile;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.entity.attack.EnergizedFlame;
import cn.leolezury.eternalstarlight.common.entity.interfaces.TrailOwner;
import cn.leolezury.eternalstarlight.common.entity.living.boss.golem.Permafrost;
import cn.leolezury.eternalstarlight.common.entity.living.boss.golem.StarlightGolem;
import cn.leolezury.eternalstarlight.common.entity.living.boss.golem.StarlightGolemChargeStartPhase;
import cn.leolezury.eternalstarlight.common.entity.living.monster.Freeze;
import cn.leolezury.eternalstarlight.common.registry.ESCriteriaTriggers;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import cn.leolezury.eternalstarlight.common.util.TrailEffect;
import it.unimi.dsi.fastutil.ints.Int2IntArrayMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector4f;

public class FrozenTube extends ThrowableProjectile implements TrailOwner {
	public FrozenTube(EntityType<? extends FrozenTube> entityType, Level level) {
		super(entityType, level);
	}

	public FrozenTube(Level level, LivingEntity livingEntity) {
		super(ESEntities.FROZEN_TUBE.get(), livingEntity, level);
	}

	public FrozenTube(Level level, double x, double y, double z) {
		super(ESEntities.FROZEN_TUBE.get(), x, y, z, level);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {

	}

	@Override
	protected void onHit(HitResult hitResult) {
		super.onHit(hitResult);
		if (hitResult.getType() != HitResult.Type.MISS) {
			this.playSound(ESSoundEvents.FROZEN_TUBE_BREAK.get(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
			if (level() instanceof ServerLevel serverLevel) {
				serverLevel.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, ESItems.FROZEN_TUBE.get().getDefaultInstance()), this.getX() + (this.random.nextFloat() - 0.5) * getBbWidth(), this.getY() + random.nextFloat() * getBbHeight(), this.getZ() + (this.random.nextFloat() - 0.5) * getBbWidth(), 5, 0.2, 0.2, 0.2, 0.0);
			}
			for (LivingEntity entity : level().getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(2))) {
				if (!level().isClientSide && ESEntityUtil.shouldHarm(getOwner(), entity)) {
					if (entity.canFreeze()) {
						entity.setTicksFrozen(Math.min(entity.getTicksFrozen() + 100, 300));
					}
					if (getOwner() instanceof ServerPlayer serverPlayer && entity instanceof StarlightGolem golem && golem.getAttackEnergy() > 0) {
						golem.setAttackEnergy(Math.max(golem.getAttackEnergy() - 5, 0));
						Int2IntArrayMap cooldowns = golem.getBehaviorManager().getCooldowns();
						cooldowns.put(StarlightGolemChargeStartPhase.ID, Math.max(cooldowns.getOrDefault(StarlightGolemChargeStartPhase.ID, 0) - 60, 0));
						ESCriteriaTriggers.FREEZE_STARLIGHT_GOLEM.get().trigger(serverPlayer);
					}
				}
			}
			for (int x = -4; x <= 4; x++) {
				for (int y = -4; y <= 4; y++) {
					for (int z = -4; z <= 4; z++) {
						BlockPos pos = blockPosition().offset(x, y, z);
						if (level().getBlockState(pos).is(Blocks.FIRE) && blockPosition().distSqr(pos) <= 4 * 4) {
							level().setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
						}
						if (level().getBlockState(pos).is(Blocks.WATER) && level().getFluidState(pos).isSource() && blockPosition().distSqr(pos) <= 4 * 4) {
							level().setBlockAndUpdate(pos, Blocks.ICE.defaultBlockState());
						}
						if (level().getBlockState(pos).is(Blocks.LAVA) && level().getFluidState(pos).isSource() && blockPosition().distSqr(pos) <= 2 * 2) {
							level().setBlockAndUpdate(pos, Blocks.OBSIDIAN.defaultBlockState());
						}
					}
				}
			}
			level().getEntitiesOfClass(EnergizedFlame.class, getBoundingBox().inflate(5)).forEach(Entity::discard);
			discard();
		}
	}

	@Override
	protected void onHitEntity(EntityHitResult hitResult) {
		if (hitResult.getType() != HitResult.Type.MISS && getOwner() instanceof LivingEntity owner && ESEntityUtil.shouldHarm(owner, hitResult.getEntity())) {
			hitResult.getEntity().hurt(ESDamageTypes.getIndirectEntityDamageSource(level(), ESDamageTypes.FREEZE, this, owner), (float) switch (getOwner()) {
				case Player ignored -> 6;
				case Freeze ignored -> ESConfig.INSTANCE.mobsConfig.freeze.attackDamage();
				case Permafrost permafrost -> (permafrost.getAttribute(Attributes.ATTACK_DAMAGE) != null ? permafrost.getAttributeValue(Attributes.ATTACK_DAMAGE) : 12) * 0.4;
				default -> 3;
			});
		}
	}

	@Override
	public TrailEffect createNewTrail() {
		return new TrailEffect(0.3f, 8);
	}

	@Override
	public void updateTrail(TrailEffect effect) {
		Vec3 oldPos = new Vec3(xOld, yOld, zOld);
		effect.update(oldPos.add(0, getBbHeight() / 2, 0));
		if (isRemoved()) {
			effect.setLength(Math.max(effect.getLength() - 0.9f, 0));
		}
	}

	@Override
	public Vector4f getTrailColor() {
		return new Vector4f(104 / 255f, 204 / 255f, 255 / 255f, 1f);
	}
}