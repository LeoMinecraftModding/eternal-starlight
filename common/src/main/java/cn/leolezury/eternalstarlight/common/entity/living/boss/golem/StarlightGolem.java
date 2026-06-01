package cn.leolezury.eternalstarlight.common.entity.living.boss.golem;

import cn.leolezury.eternalstarlight.common.block.EnergyBlock;
import cn.leolezury.eternalstarlight.common.block.WeatheringGolemSteel;
import cn.leolezury.eternalstarlight.common.block.entity.LootChestBlockEntity;
import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.data.ESCrests;
import cn.leolezury.eternalstarlight.common.entity.attack.EnergizedFlame;
import cn.leolezury.eternalstarlight.common.entity.interfaces.RayAttackUser;
import cn.leolezury.eternalstarlight.common.entity.living.boss.ESBoss;
import cn.leolezury.eternalstarlight.common.entity.living.boss.ESServerBossEvent;
import cn.leolezury.eternalstarlight.common.entity.living.goal.LookAtTargetGoal;
import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorManager;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.particle.ESExplosionParticleOptions;
import cn.leolezury.eternalstarlight.common.particle.ExplosionShockParticleOptions;
import cn.leolezury.eternalstarlight.common.particle.GatheringTrailParticleOptions;
import cn.leolezury.eternalstarlight.common.particle.RingExplosionParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.*;
import cn.leolezury.eternalstarlight.common.util.*;
import cn.leolezury.eternalstarlight.common.vfx.ScreenShakeVfx;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StarlightGolem extends ESBoss implements RayAttackUser {
	public StarlightGolem(EntityType<? extends StarlightGolem> entityType, Level level) {
		super(entityType, level);
		this.noCulling = true;
	}

	private final ESServerBossEvent bossEvent = new ESServerBossEvent(this, ESServerBossEvent.STARLIGHT_GOLEM, BossEvent.BossBarColor.BLUE, false);

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

	public int oldDeathAnimationTime;
	public int deathAnimationTime;
	private final Vec3[] deathParticlePos = new Vec3[4];

	public final List<Pair<Vec3, ModelSnapshot>> trailSnapshots = new ArrayList<>(50);
	public float lastTrailTick = 0;

	public boolean shouldAddTrailSnapshot() {
		return Mth.degreesDifferenceAbs(getYRot(), yBodyRot) < 45
			&& Mth.degreesDifferenceAbs(getYRot(), yBodyRotO) < 45
			&& Mth.degreesDifferenceAbs(yBodyRot, yBodyRotO) < 45
			&& getPhase() == 1
			&& getBehaviorState() == StarlightGolemSmashPhase.ID
			&& getBehaviorTicks() >= 37;
	}

	private int attackEnergy;
	private int lastHurtCount;
	private int chargeHurtCount;
	private float chargeHurtAmount;
	private int lastHurtSound;
	private boolean hasProtection;

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

	public boolean hasProtection() {
		return hasProtection;
	}

	public BehaviorManager<StarlightGolem> getBehaviorManager() {
		return behaviorManager;
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
		return getTarget() == null ? position().add(getBbWidth() * (getRandom().nextFloat() - 0.5f), getBbHeight() * getRandom().nextFloat(), getBbWidth() * (getRandom().nextFloat() - 0.5f)) : getTarget().position().add(0, getTarget().getBbHeight() / 2, 0);
	}

	@Override
	public void updateRayEnd(Vec3 endPos) {
		ESEntityUtil.instantLook(this, endPos);
	}

	private class GolemLookAtTargetGoal extends LookAtTargetGoal {
		public GolemLookAtTargetGoal() {
			super(StarlightGolem.this);
		}

		@Override
		public void tick() {
			boolean affectsLook =
				StarlightGolem.this.getBehaviorState() == StarlightGolemLaserBeamPhase.ID
					|| (StarlightGolem.this.getBehaviorState() == StarlightGolemSmashPhase.ID && StarlightGolem.this.getBehaviorTicks() > 30);
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
	public boolean isInvulnerableTo(DamageSource source) {
		return super.isInvulnerableTo(source) || source.getEntity() == this || source.is(DamageTypes.FALLING_BLOCK);
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (!source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
			if (!hasProtection()) {
				if (source.getEntity() != null && source.getEntity() != this) {
					if (tickCount - lastHurtCount > 10) {
						chargeHurtCount++;
						lastHurtCount = tickCount;
					}
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
		boolean success = super.hurt(source, amount);
		if (getPhase() == 0 && getHealth() / getMaxHealth() < 0.2) {
			setPhase(1);
			ESBlockUtil.getBlocksInBoundingBox(getBoundingBox().inflate(2, 0, 2)).forEach(pos -> {
				if (level().getBlockState(pos).getBlock() instanceof WeatheringGolemSteel && ESPlatform.INSTANCE.postEntityDestroyBlockEvent(level(), pos, this)) {
					level().destroyBlock(pos, true);
				}
			});
		}
		return success;
	}

	@Override
	protected void tickDeath() {
		if (deathAnimationTime == 0) {
			stopAllAnimStates();
			deathAnimationState.start(tickCount);
			setBehaviorState(0);
			for (int i = 0; i < 4; i++) {
				deathParticlePos[i] = position().add(0, getBbHeight() / 4, 0);
			}
		}
		Optional<BlockPos> chestPos = getLootChestPos();
		if (chestPos.isPresent()) {
			for (int i = 0; i < 4; i++) {
				if (deathAnimationTime < 90) {
					deathParticlePos[i] = ESMathUtil.lerpVec(1 / (90f - deathAnimationTime), deathParticlePos[i], ESMathUtil.rotationToPosition(chestPos.get().getCenter(), 5, 0, 30 + i * 90));
				} else {
					float currentYaw = ESMathUtil.positionToYaw(chestPos.get().getCenter(), deathParticlePos[i]);
					deathParticlePos[i] = ESMathUtil.rotationToPosition(chestPos.get().getCenter(), ((110 - deathAnimationTime) / 20f) * 5, 0, currentYaw + 2);
				}
				if (level() instanceof ServerLevel serverLevel) {
					serverLevel.sendParticles(ESParticles.ENERGY.get(), deathParticlePos[i].x(), deathParticlePos[i].y(), deathParticlePos[i].z(), 3, 0.1, 0.1, 0.1, 0);
				}
			}
		}
		oldDeathAnimationTime = deathAnimationTime;
		++deathAnimationTime;
		if (deathAnimationTime == 110 && !level().isClientSide()) {
			level().broadcastEntityEvent(this, (byte) 60);
			playSound(SoundEvents.GENERIC_EXPLODE.value());
			if (level() instanceof ServerLevel serverLevel) {
				serverLevel.sendParticles(ESExplosionParticleOptions.ENERGY, getX(), getY() + getBbHeight() / 2, getZ(), 20, getBbWidth() / 2, getBbHeight() / 2, getBbWidth() / 2, 0);
				serverLevel.sendParticles(ESExplosionParticleOptions.ENERGY_BLAST, getX(), getY() + getBbHeight() / 2, getZ(), 5, getBbWidth() / 2, getBbHeight() / 2, getBbWidth() / 2, 0);
				for (int i = 0; i < 25; i++) {
					Vec3 speed = new Vec3((this.random.nextFloat() - this.random.nextFloat()) * 0.1F, this.random.nextFloat() * 0.05F, (this.random.nextFloat() - this.random.nextFloat()) * 0.1F).normalize();
					ESPlatform.INSTANCE.sendToAllClients(serverLevel, new ParticlePacket(ExplosionShockParticleOptions.ENERGY, position().x + speed.x * 1.2, position().y + speed.y * 1.2, position().z + speed.z * 1.2, speed.x, speed.y, speed.z));
				}
				ScreenShakeVfx.createInstance(level().dimension(), position(), 40, 50, 0.5f, 0.5f, 3, 5.5f).send(serverLevel);
			}
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
		if (accessor.equals(BEHAVIOR_STATE)) {
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
		return super.isAlliedTo(entity) || entity.getType().is(ESTags.EntityTypes.STARLIGHT_GOLEM_ALLIES);
	}

	@Override
	public boolean canStandOnFluid(FluidState fluidState) {
		return super.canStandOnFluid(fluidState) || fluidState.is(FluidTags.LAVA);
	}

	@Override
	public boolean canBossMove() {
		return getPhase() != 0;
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
		LivingEntity target = getTarget();
		if (trackTarget && target != null) {
			EnergizedFlame energizedFlame = ESEntities.ENERGIZED_FLAME.get().create(level());
			if (energizedFlame != null) {
				energizedFlame.setPos(target.position().add(ESDataAttachments.MOVEMENT.getData(target).scale(20)));
				energizedFlame.setOwner(this);
				level().addFreshEntity(energizedFlame);
				left--;
			}
		}
		List<BlockPos> possiblePositions = new ArrayList<>();
		for (int x = -scanRadius; x <= scanRadius; x++) {
			for (int z = -scanRadius; z <= scanRadius; z++) {
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
				energizedFlame.setPos(firePos.getBottomCenter());
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
			List<BlockPos> nearbyEnergyBlocks = getNearbyEnergyBlocks(true);
			if (level() instanceof ServerLevel serverLevel && tickCount % 10 == 0 && getBehaviorState() == StarlightGolemChargePhase.ID && !nearbyEnergyBlocks.isEmpty()) {
				for (BlockPos pos : nearbyEnergyBlocks) {
					Vec3 energySource = pos.getCenter();
					Vec3 energyMotion = position().add(0, getBbHeight() / 4, 0).subtract(energySource);
					ESPlatform.INSTANCE.sendToTrackingClients(serverLevel, this, new ParticlePacket(GatheringTrailParticleOptions.ENERGY, energySource.x, energySource.y, energySource.z, energyMotion.x, energyMotion.y, energyMotion.z));
				}
			}
			if (getBehaviorState() == StarlightGolemChargePhase.ID) {
				hasProtection = !nearbyEnergyBlocks.isEmpty();
			} else {
				hasProtection = true;
			}
			if (getPhase() == 1) {
				hasProtection = false;
				if (tickCount % 60 == 0) {
					hurt(damageSources().generic(), 0);
					this.invulnerableTime = 0;
				}
				for (BlockPos pos : ESBlockUtil.getBlocksInBoundingBox(getBoundingBox().inflate(3))) {
					if (pos.distToCenterSqr(position()) < (getBbWidth() * getBbWidth()) * 2 && level().getBlockState(pos).is(Blocks.LAVA) && ESPlatform.INSTANCE.postEntityDestroyBlockEvent(level(), pos, this)) {
						level().setBlockAndUpdate(pos, Blocks.MAGMA_BLOCK.defaultBlockState());
						if (level() instanceof ServerLevel serverLevel) {
							serverLevel.sendParticles(ESExplosionParticleOptions.LAVA, pos.getCenter().x, pos.getCenter().y + 0.6, pos.getCenter().z, 1, 0, 0, 0, 0);
						}
					}
				}
			}
		} else {
			if (getRandom().nextInt(Mth.clamp(Math.round((getHealth() / getMaxHealth()) * 30), 1, 30)) == 0) {
				Vec3 smokePos = position().add(getBbWidth() * (getRandom().nextFloat() - 0.5f), getBbHeight() * getRandom().nextFloat(), getBbWidth() * (getRandom().nextFloat() - 0.5f));
				level().addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, smokePos.x, smokePos.y, smokePos.z, 0, getRandom().nextFloat() * 0.15, 0);
			}
			if (getRandom().nextInt(3) == 0 && getPhase() == 1) {
				Vec3 sparkPos = position().add(getBbWidth() * (getRandom().nextFloat() - 0.5f), getBbHeight() * getRandom().nextFloat(), getBbWidth() * (getRandom().nextFloat() - 0.5f));
				for (int i = 0; i < 25; i++) {
					Vec3 speed = new Vec3((this.random.nextFloat() - this.random.nextFloat()) * 0.1F, this.random.nextFloat() * 0.05F, (this.random.nextFloat() - this.random.nextFloat()) * 0.1F).normalize();
					level().addParticle(ExplosionShockParticleOptions.ENERGY_SMALL, sparkPos.x, sparkPos.y, sparkPos.z, speed.x, speed.y, speed.z);
				}
			}
		}
	}

	@Override
	protected BlockState getBossSpawner() {
		return ESBlocks.STARLIGHT_GOLEM_SPAWNER.get().defaultBlockState();
	}

	@Override
	protected void grantSpecialLoot(ServerPlayer player) {
		ESCrestUtil.upgradeCrest(player, ESCrests.BLAZING_BEAM);
	}

	@Override
	protected void modifyBossLootChest(LootChestBlockEntity blockEntity) {
		blockEntity.setColor(0x767573);
		blockEntity.setOutlineColor(0x00fff4);
		blockEntity.setFlashColor(0xc8fff4);
		blockEntity.setRareFlashColor(0x00fff4);
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
