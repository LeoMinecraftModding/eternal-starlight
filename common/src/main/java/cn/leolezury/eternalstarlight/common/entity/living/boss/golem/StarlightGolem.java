package cn.leolezury.eternalstarlight.common.entity.living.boss.golem;

import cn.leolezury.eternalstarlight.common.block.EnergyBlock;
import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.data.ESCrests;
import cn.leolezury.eternalstarlight.common.entity.attack.EnergizedFlame;
import cn.leolezury.eternalstarlight.common.entity.interfaces.RayAttackUser;
import cn.leolezury.eternalstarlight.common.entity.living.boss.ESBoss;
import cn.leolezury.eternalstarlight.common.entity.living.boss.ESServerBossEvent;
import cn.leolezury.eternalstarlight.common.entity.living.goal.LookAtTargetGoal;
import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorManager;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.particle.RingExplosionParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.*;
import cn.leolezury.eternalstarlight.common.util.ESCrestUtil;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.mojang.datafixers.util.Pair;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class StarlightGolem extends ESBoss implements RayAttackUser {
	public StarlightGolem(EntityType<? extends StarlightGolem> entityType, Level level) {
		super(entityType, level);
	}

	private final ESServerBossEvent bossEvent = new ESServerBossEvent(this, getUUID(), BossEvent.BossBarColor.BLUE, false);

	private final BehaviorManager<StarlightGolem> behaviorManager = new BehaviorManager<>(this, List.of(
		new StarlightGolemLaserBeamPhase(),
		new StarlightGolemSummonFlamePhase(),
		new StarlightGolemSmashPhase(),
		new StarlightGolemChargeStartPhase(),
		new StarlightGolemChargePhase(),
		new StarlightGolemChargeEndPhase()
	));

	public AnimationState laserBeamAnimationState = new AnimationState();
	public AnimationState summonFlameAnimationState = new AnimationState();
	public AnimationState smashAnimationState = new AnimationState();
	public AnimationState chargeStartAnimationState = new AnimationState();
	public AnimationState chargeAnimationState = new AnimationState();
	public AnimationState chargeEndAnimationState = new AnimationState();
	public AnimationState deathAnimationState = new AnimationState();

	private int attackEnergy;
	private int chargeHurtCount;
	private float chargeHurtAmount;
	private int lastHurtSound;

	public void clearChargeHurtCountAndAmount() {
		this.chargeHurtCount = 0;
		this.chargeHurtAmount = 0;
	}

	public int getChargeHurtCount() {
		return chargeHurtCount;
	}

	public float getChargeHurtAmount() {
		return chargeHurtAmount;
	}

	public int getAttackEnergy() {
		return attackEnergy;
	}

	public void setAttackEnergy(int energy) {
		this.attackEnergy = energy;
	}

	public boolean canHurt() {
		return getNearbyEnergyBlocks(true).isEmpty();
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		bossEvent.setId(getUUID());
	}

	@Override
	public void startSeenByPlayer(ServerPlayer serverPlayer) {
		super.startSeenByPlayer(serverPlayer);
		bossEvent.addPlayer(serverPlayer);
	}

	@Override
	public void stopSeenByPlayer(ServerPlayer serverPlayer) {
		super.stopSeenByPlayer(serverPlayer);
		bossEvent.removePlayer(serverPlayer);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		goalSelector.addGoal(0, new FloatGoal(this));
		goalSelector.addGoal(1, new GolemLookAtTargetGoal());
		goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
		goalSelector.addGoal(3, new LookAtPlayerGoal(this, Mob.class, 8.0F));

		targetSelector.addGoal(0, new HurtByTargetGoal(this, StarlightGolem.class).setAlertOthers());
		targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
		targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
		targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
	}

	@Override
	public boolean isRayFollowingHeadRotation() {
		return false;
	}

	@Override
	public Vec3 getRayRotationTarget() {
		return getTarget() == null ? position().add(getBbWidth() * (getRandom().nextFloat() - 0.5f), getBbHeight() * getRandom().nextFloat(), getBbWidth() * (getRandom().nextFloat() - 0.5f)) : getTarget().position().add(getTarget().getBbWidth() * (getRandom().nextFloat() - 0.5f), getTarget().getBbHeight() * getRandom().nextFloat(), getTarget().getBbWidth() * (getRandom().nextFloat() - 0.5f));
	}

	@Override
	public void updateRayEnd(Vec3 endPos) {
		lookAt(EntityAnchorArgument.Anchor.EYES, endPos);
	}

	private class GolemLookAtTargetGoal extends LookAtTargetGoal {
		public GolemLookAtTargetGoal() {
			super(StarlightGolem.this);
		}

		@Override
		public void tick() {
			boolean affectsLook =
				StarlightGolem.this.getBehaviorState() == StarlightGolemLaserBeamPhase.ID;
			if (!affectsLook) {
				super.tick();
			}
		}
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes()
			.add(Attributes.MAX_HEALTH, ESConfig.INSTANCE.mobsConfig.starlightGolem.maxHealth())
			.add(Attributes.ARMOR, ESConfig.INSTANCE.mobsConfig.starlightGolem.armor())
			.add(Attributes.FOLLOW_RANGE, ESConfig.INSTANCE.mobsConfig.starlightGolem.followRange())
			.add(Attributes.MOVEMENT_SPEED, 0)
			.add(Attributes.ATTACK_DAMAGE, 0)
			.add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (!source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
			if (canHurt() && getBehaviorState() == StarlightGolemChargePhase.ID && !source.is(DamageTypes.FALL) && !source.is(DamageTypes.FREEZE) && source.getEntity() != this) {
				if (source.getEntity() != null) {
					chargeHurtCount++;
					chargeHurtAmount += amount;
				}
			} else {
				if (source.getDirectEntity() instanceof LivingEntity && tickCount - lastHurtSound > 20) {
					playSound(ESSoundEvents.STARLIGHT_GOLEM_BLOCK.get(), getSoundVolume(), getVoicePitch());
					lastHurtSound = tickCount;
				}
				return false;
			}
		}
		return super.hurt(source, amount);
	}

	@Override
	protected void tickDeath() {
		if (deathTime == 0) {
			stopAllAnimStates();
			deathAnimationState.start(tickCount);
			setBehaviorState(0);
		}
		++deathTime;
		if (deathTime == 110 && !level().isClientSide()) {
			level().broadcastEntityEvent(this, (byte) 60);
			remove(Entity.RemovalReason.KILLED);
		}
	}

	public void stopAllAnimStates() {
		laserBeamAnimationState.stop();
		summonFlameAnimationState.stop();
		smashAnimationState.stop();
		chargeStartAnimationState.stop();
		chargeAnimationState.stop();
		chargeEndAnimationState.stop();
	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
		if (accessor.equals(BEHAVIOR_STATE) && getBehaviorState() != 0) {
			stopAllAnimStates();
			switch (getBehaviorState()) {
				case StarlightGolemLaserBeamPhase.ID -> laserBeamAnimationState.start(tickCount);
				case StarlightGolemSummonFlamePhase.ID -> summonFlameAnimationState.start(tickCount);
				case StarlightGolemSmashPhase.ID -> smashAnimationState.start(tickCount);
				case StarlightGolemChargeStartPhase.ID -> chargeStartAnimationState.start(tickCount);
				case StarlightGolemChargePhase.ID -> chargeAnimationState.start(tickCount);
				case StarlightGolemChargeEndPhase.ID -> chargeEndAnimationState.start(tickCount);
			}
		}
		super.onSyncedDataUpdated(accessor);
	}

	@Override
	public boolean isPushable() {
		return false;
	}

	@Override
	public boolean isAlliedTo(Entity entity) {
		return super.isAlliedTo(entity) || entity.getType().is(ESTags.EntityTypes.STARLIGHT_GOLEM_ALLYS);
	}

	@Override
	public boolean canBossMove() {
		return false;
	}

	private List<BlockPos> getNearbyEnergyBlocks(boolean lit) {
		if (level() instanceof ServerLevel serverLevel) {
			PoiManager poiManager = serverLevel.getPoiManager();
			return poiManager
				.findAllClosestFirstWithType(poi -> poi.is(ESPoiTypes.ENERGY_BLOCK.getResourceKey()), pos -> {
					BlockState state = serverLevel.getBlockState(pos);
					return !lit || (state.hasProperty(EnergyBlock.LIT) && state.getValue(EnergyBlock.LIT));
				}, blockPosition(), 48, PoiManager.Occupancy.ANY)
				.limit(5L)
				.map(Pair::getSecond)
				.toList();
		}
		return List.of();
	}

	public void turnOnEnergyBlocks() {
		List<BlockPos> list = getNearbyEnergyBlocks(false);
		for (BlockPos pos : list) {
			BlockState state = level().getBlockState(pos);
			if (state.is(ESBlocks.ENERGY_BLOCK.get()) && !state.getValue(BlockStateProperties.LIT)) {
				level().setBlockAndUpdate(pos, state.setValue(BlockStateProperties.LIT, true));
				if (level() instanceof ServerLevel serverLevel) {
					Vec3 center = pos.getCenter();
					ESPlatform.INSTANCE.sendToAllClients(serverLevel, new ParticlePacket(RingExplosionParticleOptions.ENERGY, center.x(), center.y(), center.z(), 0, 0.2, 0));
				}
			}
		}
	}

	public void spawnEnergizedFlame(int maxNum, int scanRadius, boolean trackTarget) {
		int left = maxNum;
		if (trackTarget) {
			EnergizedFlame energizedFlame = ESEntities.ENERGIZED_FLAME.get().create(level());
			energizedFlame.setPos(getTarget() != null ? getTarget().position() : position());
			energizedFlame.setOwner(this);
			level().addFreshEntity(energizedFlame);
			left--;
		}
		List<BlockPos> possiblePositions = new ArrayList<>();
		for (int x = -scanRadius; x <= scanRadius; x += 1) {
			for (int z = -scanRadius; z <= scanRadius; z += 1) {
				for (int y = -5; y <= 5; y++) {
					BlockPos firePos = blockPosition().offset(x, y, z);
					if (level().isEmptyBlock(firePos) && level().getBlockState(firePos.below()).isFaceSturdy(level(), firePos.below(), Direction.UP)) {
						possiblePositions.add(firePos);
					}
				}
			}
		}
		for (int i = 0; i < left; i++) {
			if (!possiblePositions.isEmpty()) {
				BlockPos firePos = possiblePositions.get(getRandom().nextInt(possiblePositions.size()));
				EnergizedFlame energizedFlame = ESEntities.ENERGIZED_FLAME.get().create(level());
				energizedFlame.setPos(firePos.getCenter().add(0, -0.5, 0));
				energizedFlame.setOwner(this);
				level().addFreshEntity(energizedFlame);
				possiblePositions.remove(firePos);
			}
		}
	}

	@Override
	public void aiStep() {
		super.aiStep();
		bossEvent.update();
		if (!level().isClientSide) {
			if (getTarget() != null && !getTarget().isAlive()) {
				setTarget(null);
			}
			if (!isNoAi() && isAlive()) {
				behaviorManager.tick();
			}
			List<BlockPos> list = getNearbyEnergyBlocks(true);
			if (level() instanceof ServerLevel serverLevel && getBehaviorState() == StarlightGolemChargePhase.ID && !list.isEmpty()) {
				for (BlockPos pos : list) {
					Vec3 angle = position().add(-pos.getX() - 0.5, -pos.getY() - 1.0, -pos.getZ() - 0.5);
					double px = pos.getX() + 0.5;
					double py = pos.getY() + 1.0;
					double pz = pos.getZ() + 0.5;

					for (int i = 0; i < 10; i++) {
						double dx = angle.x();
						double dy = angle.y();
						double dz = angle.z();

						double spread = 5.0D + getRandom().nextFloat() * 2.5D;
						double velocity = (3.0D + getRandom().nextFloat() * 0.15D) / 45.0D;

						dx += getRandom().nextGaussian() * 0.0075D * spread;
						dy += getRandom().nextGaussian() * 0.0075D * spread;
						dz += getRandom().nextGaussian() * 0.0075D * spread;
						dx *= velocity;
						dy *= velocity;
						dz *= velocity;

						ESPlatform.INSTANCE.sendToTrackingClients(serverLevel, this, new ParticlePacket(ESParticles.ENERGY.get(), px, py, pz, dx, dy, dz));
					}
				}
			}
		} else {
			if (getRandom().nextInt(15) == 0) {
				Vec3 smokePos = position().add(getBbWidth() * (getRandom().nextFloat() - 0.5f), getBbHeight() * getRandom().nextFloat(), getBbWidth() * (getRandom().nextFloat() - 0.5f));
				level().addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, smokePos.x, smokePos.y, smokePos.z, (getRandom().nextFloat() - 0.5f) * 0.15, getRandom().nextFloat() * 0.15, (getRandom().nextFloat() - 0.5f) * 0.15);
			}
		}
	}

	@Override
	public void dropExtraLoot(ServerPlayer player) {
		ESCrestUtil.upgradeCrest(player, ESCrests.BLAZING_BEAM);
	}

	@Override
	public SoundEvent getBossMusic() {
		return ESSoundEvents.MUSIC_BOSS_STARLIGHT_GOLEM.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return ESSoundEvents.STARLIGHT_GOLEM_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ESSoundEvents.STARLIGHT_GOLEM_DEATH.get();
	}
}
