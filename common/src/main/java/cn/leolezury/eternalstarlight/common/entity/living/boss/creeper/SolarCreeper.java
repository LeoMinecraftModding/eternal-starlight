package cn.leolezury.eternalstarlight.common.entity.living.boss.creeper;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.entity.interfaces.TrailOwner;
import cn.leolezury.eternalstarlight.common.entity.living.boss.ESBoss;
import cn.leolezury.eternalstarlight.common.entity.living.boss.ESServerBossEvent;
import cn.leolezury.eternalstarlight.common.entity.living.goal.LookAtTargetGoal;
import cn.leolezury.eternalstarlight.common.entity.living.goal.MoveToTargetGoal;
import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorManager;
import cn.leolezury.eternalstarlight.common.util.ModelSnapshot;
import cn.leolezury.eternalstarlight.common.util.TrailEffect;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SolarCreeper extends ESBoss implements TrailOwner {
	private static final String TAG_INTRO_COMPLETED = "intro_completed";

	private static final EntityDataAccessor<Float> HEALTH_PERCENTAGE = SynchedEntityData.defineId(SolarCreeper.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Vector3f> SOLAR_RAY_NORMAL = SynchedEntityData.defineId(SolarCreeper.class, EntityDataSerializers.VECTOR3);
	private static final EntityDataAccessor<Float> SOLAR_RAY_ANGLE = SynchedEntityData.defineId(SolarCreeper.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Float> SOLAR_RAY_LENGTH_0 = SynchedEntityData.defineId(SolarCreeper.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Float> SOLAR_RAY_LENGTH_1 = SynchedEntityData.defineId(SolarCreeper.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Float> SOLAR_RAY_LENGTH_2 = SynchedEntityData.defineId(SolarCreeper.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Float> SOLAR_RAY_LENGTH_3 = SynchedEntityData.defineId(SolarCreeper.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Float> SOLAR_RAY_LENGTH_4 = SynchedEntityData.defineId(SolarCreeper.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Float> SOLAR_RAY_LENGTH_5 = SynchedEntityData.defineId(SolarCreeper.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Float> SOLAR_RAY_WIDTH_0 = SynchedEntityData.defineId(SolarCreeper.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Float> SOLAR_RAY_WIDTH_1 = SynchedEntityData.defineId(SolarCreeper.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Float> SOLAR_RAY_WIDTH_2 = SynchedEntityData.defineId(SolarCreeper.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Float> SOLAR_RAY_WIDTH_3 = SynchedEntityData.defineId(SolarCreeper.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Float> SOLAR_RAY_WIDTH_4 = SynchedEntityData.defineId(SolarCreeper.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Float> SOLAR_RAY_WIDTH_5 = SynchedEntityData.defineId(SolarCreeper.class, EntityDataSerializers.FLOAT);

	private int oldAnimationTicks, animationTicks;
	private boolean introCompleted = false;
	private boolean generatedPowerUpPartData = false;
	public final Map<String, Vector3f> clientPartOffsets = new HashMap<>();
	public final Map<String, Vector3f> clientPartRotations = new HashMap<>();

	private final Vector3f oldSolarRayNormal = new Vector3f();
	private final Vector3f renderSolarRayNormal = new Vector3f();
	private float oldSolarRayAngle, renderSolarRayAngle;
	private final float[] oldSolarRayLengths = new float[6];
	private final float[] renderSolarRayLengths = new float[6];
	private final float[] oldSolarRayWidths = new float[6];
	private final float[] renderSolarRayWidths = new float[6];

	public float getAnimationTicks(float partialTicks) {
		return Mth.lerp(partialTicks, oldAnimationTicks, animationTicks);
	}

	public void finishIntro() {
		this.introCompleted = true;
	}

	public float getHealthPercentage() {
		return this.getEntityData().get(HEALTH_PERCENTAGE);
	}

	public void setHealthPercentage(float healthPercentage) {
		this.getEntityData().set(HEALTH_PERCENTAGE, healthPercentage);
	}

	public Vector3f getSolarRayNormal() {
		return this.getEntityData().get(SOLAR_RAY_NORMAL);
	}

	public void setSolarRayNormal(Vector3f normal) {
		this.getEntityData().set(SOLAR_RAY_NORMAL, normal);
	}

	public float getSolarRayAngle() {
		return this.getEntityData().get(SOLAR_RAY_ANGLE);
	}

	public void setSolarRayAngle(float angle) {
		this.getEntityData().set(SOLAR_RAY_ANGLE, angle);
	}

	public float getSolarRayLength(int index) {
		return switch (index) {
			case 0 -> this.getEntityData().get(SOLAR_RAY_LENGTH_0);
			case 1 -> this.getEntityData().get(SOLAR_RAY_LENGTH_1);
			case 2 -> this.getEntityData().get(SOLAR_RAY_LENGTH_2);
			case 3 -> this.getEntityData().get(SOLAR_RAY_LENGTH_3);
			case 4 -> this.getEntityData().get(SOLAR_RAY_LENGTH_4);
			case 5 -> this.getEntityData().get(SOLAR_RAY_LENGTH_5);
			default -> 0;
		};
	}

	public void setSolarRayLength(int index, float length) {
		switch (index) {
			case 0 -> this.getEntityData().set(SOLAR_RAY_LENGTH_0, length);
			case 1 -> this.getEntityData().set(SOLAR_RAY_LENGTH_1, length);
			case 2 -> this.getEntityData().set(SOLAR_RAY_LENGTH_2, length);
			case 3 -> this.getEntityData().set(SOLAR_RAY_LENGTH_3, length);
			case 4 -> this.getEntityData().set(SOLAR_RAY_LENGTH_4, length);
			case 5 -> this.getEntityData().set(SOLAR_RAY_LENGTH_5, length);
		}
	}

	public float getSolarRayWidth(int index) {
		return switch (index) {
			case 0 -> this.getEntityData().get(SOLAR_RAY_WIDTH_0);
			case 1 -> this.getEntityData().get(SOLAR_RAY_WIDTH_1);
			case 2 -> this.getEntityData().get(SOLAR_RAY_WIDTH_2);
			case 3 -> this.getEntityData().get(SOLAR_RAY_WIDTH_3);
			case 4 -> this.getEntityData().get(SOLAR_RAY_WIDTH_4);
			case 5 -> this.getEntityData().get(SOLAR_RAY_WIDTH_5);
			default -> 0;
		};
	}

	public void setSolarRayWidth(int index, float width) {
		switch (index) {
			case 0 -> this.getEntityData().set(SOLAR_RAY_WIDTH_0, width);
			case 1 -> this.getEntityData().set(SOLAR_RAY_WIDTH_1, width);
			case 2 -> this.getEntityData().set(SOLAR_RAY_WIDTH_2, width);
			case 3 -> this.getEntityData().set(SOLAR_RAY_WIDTH_3, width);
			case 4 -> this.getEntityData().set(SOLAR_RAY_WIDTH_4, width);
			case 5 -> this.getEntityData().set(SOLAR_RAY_WIDTH_5, width);
		}
	}

	public Vector3f getRenderSolarRayNormal(float partialTicks) {
		return new Vector3f(
			Mth.lerp(partialTicks, oldSolarRayNormal.x(), renderSolarRayNormal.x()),
			Mth.lerp(partialTicks, oldSolarRayNormal.y(), renderSolarRayNormal.y()),
			Mth.lerp(partialTicks, oldSolarRayNormal.z(), renderSolarRayNormal.z())
		);
	}

	public float getRenderSolarRayAngle(float partialTicks) {
		return Mth.lerp(partialTicks, oldSolarRayAngle, renderSolarRayAngle);
	}

	public float getRenderSolarRayLength(int index, float partialTicks) {
		return Mth.lerp(partialTicks, oldSolarRayLengths[index], renderSolarRayLengths[index]);
	}

	public float getRenderSolarRayWidth(int index, float partialTicks) {
		return Mth.lerp(partialTicks, oldSolarRayWidths[index], renderSolarRayWidths[index]);
	}

	public Vec3 getSunAbovePos(float partialTicks) {
		return getPosition(partialTicks).add(0, getBbHeight() + (getBehaviorState() == SolarCreeperBlackHolePhase.ID ? 5 : 3), 0);
	}

	public void generatePowerUpPartData() {
		if (generatedPowerUpPartData) return;
		generatedPowerUpPartData = true;
		clientPartOffsets.clear();
		clientPartRotations.clear();
		for (String name : SolarCreeperPowerUpPhase.PART_NAMES) {
			clientPartOffsets.put(name, new Vector3f(
				(random.nextFloat() - 0.5f) * 120,
				(random.nextFloat() - 0.5f) * 120,
				(random.nextFloat() - 0.5f) * 120
			));
			clientPartRotations.put(name, new Vector3f(
				(random.nextFloat() - 0.5f) * Mth.TWO_PI,
				(random.nextFloat() - 0.5f) * Mth.TWO_PI,
				(random.nextFloat() - 0.5f) * Mth.TWO_PI
			));
		}
	}

	public SolarCreeper(EntityType<? extends SolarCreeper> entityType, Level level) {
		super(entityType, level);
		this.noCulling = true;
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(HEALTH_PERCENTAGE, 1f)
			.define(SOLAR_RAY_NORMAL, new Vector3f())
			.define(SOLAR_RAY_ANGLE, 0f)
			.define(SOLAR_RAY_LENGTH_0, 0f)
			.define(SOLAR_RAY_LENGTH_1, 0f)
			.define(SOLAR_RAY_LENGTH_2, 0f)
			.define(SOLAR_RAY_LENGTH_3, 0f)
			.define(SOLAR_RAY_LENGTH_4, 0f)
			.define(SOLAR_RAY_LENGTH_5, 0f)
			.define(SOLAR_RAY_WIDTH_0, 0f)
			.define(SOLAR_RAY_WIDTH_1, 0f)
			.define(SOLAR_RAY_WIDTH_2, 0f)
			.define(SOLAR_RAY_WIDTH_3, 0f)
			.define(SOLAR_RAY_WIDTH_4, 0f)
			.define(SOLAR_RAY_WIDTH_5, 0f);
	}

	private final ESServerBossEvent bossEvent = new ESServerBossEvent(this, ESServerBossEvent.SOLAR_CREEPER, BossEvent.BossBarColor.YELLOW, false);

	private final BehaviorManager<SolarCreeper> behaviorManager = new BehaviorManager<>(this, List.of(
		new SolarCreeperIntroPhase(),
		//new SolarCreeperMeleePhase(),
		//new SolarCreeperStompShootPhase(),
		//new SolarCreeperJumpStartPhase(),
		//new SolarCreeperJumpTransitionPhase(),
		//new SolarCreeperJumpEndPhase(),
		//new SolarCreeperStarPhase(),
		//new SolarCreeperSolarWindPhase(),
		//new SolarCreeperDashPhase(),
		new SolarCreeperSupernovaPhase(),
		new SolarCreeperSolarRayPhase(),
		new SolarCreeperBlackHolePhase(),
		new SolarCreeperGalaxyPhase(),
		new SolarCreeperPowerUpPhase()
	));

	@Override
	public BehaviorManager<SolarCreeper> getBehaviorManager() {
		return behaviorManager;
	}

	public AnimationState introAnimationState = new AnimationState();
	public AnimationState meleeAnimationState = new AnimationState();
	public AnimationState stompShootAnimationState = new AnimationState();
	public AnimationState jumpStartAnimationState = new AnimationState();
	public AnimationState jumpTransitionAnimationState = new AnimationState();
	public AnimationState jumpEndAnimationState = new AnimationState();
	public AnimationState starAnimationState = new AnimationState();
	public AnimationState solarWindAnimationState = new AnimationState();
	public AnimationState dashAnimationState = new AnimationState();
	public AnimationState supernovaAnimationState = new AnimationState();
	public AnimationState solarRayAnimationState = new AnimationState();
	public AnimationState blackHoleAnimationState = new AnimationState();
	public AnimationState galaxyAnimationState = new AnimationState();
	public AnimationState powerUpAnimationState = new AnimationState();
	public AnimationState deathAnimationState = new AnimationState();

	public final List<Pair<Vec3, ModelSnapshot>> trailSnapshots = new ArrayList<>(50);
	public float lastTrailTick = 0;

	public boolean shouldAddTrailSnapshot() {
		return Mth.degreesDifferenceAbs(getYRot(), yBodyRot) < 30
			&& Mth.degreesDifferenceAbs(getYRot(), yBodyRotO) < 30
			&& Mth.degreesDifferenceAbs(yBodyRot, yBodyRotO) < 30
			&& (getBehaviorState() == SolarCreeperJumpTransitionPhase.ID
			|| getBehaviorState() == SolarCreeperDashPhase.ID);
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
		goalSelector.addGoal(1, new MoveToTargetGoal(this, 1));
		goalSelector.addGoal(2, new LookAtTargetGoal(this));
		goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
		goalSelector.addGoal(4, new LookAtPlayerGoal(this, Mob.class, 8.0F));

		targetSelector.addGoal(0, new HurtByTargetGoal(this, SolarCreeper.class).setAlertOthers());
		targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
		targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
		targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes()
			.add(Attributes.MAX_HEALTH, ESConfig.INSTANCE.mobsConfig.solarCreeper.maxHealth())
			.add(Attributes.ARMOR, ESConfig.INSTANCE.mobsConfig.solarCreeper.armor())
			.add(Attributes.FOLLOW_RANGE, ESConfig.INSTANCE.mobsConfig.solarCreeper.followRange())
			.add(Attributes.MOVEMENT_SPEED, 0.3)
			.add(Attributes.ATTACK_DAMAGE, 12)
			.add(Attributes.KNOCKBACK_RESISTANCE, 0.9);
	}

	public void stopAllAnimStates() {
		introAnimationState.stop();
		meleeAnimationState.stop();
		stompShootAnimationState.stop();
		jumpStartAnimationState.stop();
		jumpTransitionAnimationState.stop();
		jumpEndAnimationState.stop();
		starAnimationState.stop();
		solarWindAnimationState.stop();
		dashAnimationState.stop();
		supernovaAnimationState.stop();
		solarRayAnimationState.stop();
		blackHoleAnimationState.stop();
		galaxyAnimationState.stop();
		powerUpAnimationState.stop();
	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
		if (accessor.equals(BEHAVIOR_STATE)) {
			stopAllAnimStates();
			switch (getBehaviorState()) {
				case SolarCreeperIntroPhase.ID -> introAnimationState.start(tickCount);
				case SolarCreeperMeleePhase.ID -> meleeAnimationState.start(tickCount);
				case SolarCreeperStompShootPhase.ID -> stompShootAnimationState.start(tickCount);
				case SolarCreeperJumpStartPhase.ID -> jumpStartAnimationState.start(tickCount);
				case SolarCreeperJumpTransitionPhase.ID -> jumpTransitionAnimationState.start(tickCount);
				case SolarCreeperJumpEndPhase.ID -> jumpEndAnimationState.start(tickCount);
				case SolarCreeperStarPhase.ID -> starAnimationState.start(tickCount);
				case SolarCreeperSolarWindPhase.ID -> solarWindAnimationState.start(tickCount);
				case SolarCreeperDashPhase.ID -> dashAnimationState.start(tickCount);
				case SolarCreeperSupernovaPhase.ID -> supernovaAnimationState.start(tickCount);
				case SolarCreeperSolarRayPhase.ID -> solarRayAnimationState.start(tickCount);
				case SolarCreeperBlackHolePhase.ID -> blackHoleAnimationState.start(tickCount);
				case SolarCreeperGalaxyPhase.ID -> galaxyAnimationState.start(tickCount);
				case SolarCreeperPowerUpPhase.ID -> powerUpAnimationState.start(tickCount);
			}
		}
		super.onSyncedDataUpdated(accessor);
	}

	@Override
	public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
		return false;
	}

	@Override
	protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {

	}

	@Override
	public void aiStep() {
		if (!level().isClientSide) {
			int state = getBehaviorState();
			setNoGravity(!introCompleted || state == SolarCreeperIntroPhase.ID || state == SolarCreeperPowerUpPhase.ID);
		}
		super.aiStep();
		bossEvent.update();
		if (!level().isClientSide) {
			if (getTarget() != null && !getTarget().isAlive()) {
				setTarget(null);
			}
			if (!isNoAi() && isAlive()) {
				behaviorManager.tick();
				if (!introCompleted && getBehaviorState() != SolarCreeperIntroPhase.ID) {
					setBehaviorState(SolarCreeperIntroPhase.ID);
					setBehaviorTicks(0);
				}
			}
		} else {
			oldAnimationTicks = animationTicks;
			animationTicks = getBehaviorTicks();
			if (getBehaviorState() == SolarCreeperPowerUpPhase.ID) {
				generatePowerUpPartData();
			} else if (generatedPowerUpPartData) {
				generatedPowerUpPartData = false;
				clientPartOffsets.clear();
				clientPartRotations.clear();
			}
			setHealthPercentage(getHealth() / getMaxHealth());
			oldSolarRayNormal.set(renderSolarRayNormal);
			renderSolarRayNormal.set(getSolarRayNormal());
			oldSolarRayAngle = renderSolarRayAngle;
			renderSolarRayAngle = getSolarRayAngle();
			for (int i = 0; i < 6; i++) {
				oldSolarRayLengths[i] = renderSolarRayLengths[i];
				renderSolarRayLengths[i] = getSolarRayLength(i);
				oldSolarRayWidths[i] = renderSolarRayWidths[i];
				renderSolarRayWidths[i] = getSolarRayWidth(i);
			}
		}
	}

	@Override
	protected void tickDeath() {
		if (deathTime == 0) {
			stopAllAnimStates();
			deathAnimationState.start(tickCount);
			setBehaviorState(0);
		}
		++deathTime;
		if (deathTime == 100 && !level().isClientSide()) {
			remove(RemovalReason.KILLED);
		}
	}

	@Override
	public boolean canBossMove() {
		return introCompleted;
	}

	@Override
	public boolean isPushable() {
		return false;
	}

	@Override
	public boolean isPushedByFluid() {
		return false;
	}

	@Override
	protected boolean isAffectedByFluids() {
		return false;
	}

	@Override
	public SoundEvent getBossMusic() {
		return null;
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		introCompleted = compoundTag.getBoolean(TAG_INTRO_COMPLETED);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putBoolean(TAG_INTRO_COMPLETED, introCompleted);
	}

	@Override
	public TrailEffect createNewTrail() {
		return new TrailEffect(0.15f, 0);
	}

	@Override
	public void updateTrail(TrailEffect effect) {
		Vec3 oldPos = new Vec3(xOld, yOld, zOld);
		effect.update(getTrailPosition(oldPos));
		int state = getBehaviorState();
		if (isRemoved() || getDeltaMovement().length() < 0.01
			|| !(state == SolarCreeperJumpStartPhase.ID
			|| state == SolarCreeperJumpTransitionPhase.ID
			|| state == SolarCreeperDashPhase.ID)) {
			effect.setLength(Math.max(effect.getLength() - 0.5f, 0));
		} else {
			effect.setLength(Math.min(effect.getLength() + 2.5f, 8));
		}
	}

	@Override
	public Vector4f getTrailColor() {
		return new Vector4f(1f, 1f, 1f, 1f);
	}

	@Override
	public boolean isTrailFullBright() {
		return true;
	}

	@Override
	public boolean isTrailSolid() {
		return true;
	}
}
