package cn.leolezury.eternalstarlight.common.entity.living.monster;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class Seeker extends Monster {
	private static final int MOVE_INTERVAL = 50;
	private static final byte EVENT_MOVE = 100;

	protected static final EntityDataAccessor<Float> SEEKER_Y_ROT = SynchedEntityData.defineId(Seeker.class, EntityDataSerializers.FLOAT);

	public float getSeekerYRot() {
		return this.getEntityData().get(SEEKER_Y_ROT);
	}

	public void setSeekerYRot(float yRot) {
		this.getEntityData().set(SEEKER_Y_ROT, yRot);
	}

	protected static final EntityDataAccessor<Float> SEEKER_X_ROT = SynchedEntityData.defineId(Seeker.class, EntityDataSerializers.FLOAT);

	public float getSeekerXRot() {
		return this.getEntityData().get(SEEKER_X_ROT);
	}

	public void setSeekerXRot(float xRot) {
		this.getEntityData().set(SEEKER_X_ROT, xRot);
	}

	private float oldSeekerXRot, seekerXRot, oldSeekerYRot, seekerYRot;
	public AnimationState moveAnimationState = new AnimationState();

	private Vec3 nextMovement = Vec3.ZERO;

	public Seeker(EntityType<? extends Seeker> entityType, Level level) {
		super(entityType, level);
		this.setNoGravity(true);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(SEEKER_X_ROT, 0f)
			.define(SEEKER_Y_ROT, 0f);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.targetSelector.addGoal(0, new HurtByTargetGoal(this).setAlertOthers());
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes()
			.add(Attributes.MAX_HEALTH, ESConfig.INSTANCE.mobsConfig.seeker.maxHealth())
			.add(Attributes.ARMOR, ESConfig.INSTANCE.mobsConfig.seeker.armor())
			.add(Attributes.ATTACK_DAMAGE, ESConfig.INSTANCE.mobsConfig.seeker.attackDamage())
			.add(Attributes.FOLLOW_RANGE, ESConfig.INSTANCE.mobsConfig.seeker.followRange())
			.add(Attributes.MOVEMENT_SPEED, 0)
			.add(Attributes.FLYING_SPEED, 1);
	}

	public float getSeekerXRot(float f) {
		return Mth.rotLerp(f, oldSeekerXRot, seekerXRot);
	}

	public float getSeekerYRot(float f) {
		return Mth.rotLerp(f, oldSeekerYRot, seekerYRot);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		this.setNoGravity(true);
		if (!level().isClientSide) {
			if (isAlive() && !isNoAi()) {
				if (tickCount % MOVE_INTERVAL == MOVE_INTERVAL - 5) {
					level().broadcastEntityEvent(this, EVENT_MOVE);
					BlockHitResult heightResult = level().clip(new ClipContext(position(), position().subtract(0, 10, 0), ClipContext.Block.COLLIDER, ClipContext.Fluid.ANY, this));
					boolean tooHigh = heightResult.getType() == HitResult.Type.MISS;
					for (int i = 0; i < 32; i++) {
						nextMovement = new Vec3(getRandom().nextFloat() - 0.5F, tooHigh ? -Math.abs(getRandom().nextFloat() - 0.5F) : getRandom().nextFloat() - 0.5F, getRandom().nextFloat() - 0.5F).normalize();
						BlockHitResult result = level().clip(new ClipContext(position(), position().add(nextMovement.scale(getBoundingBox().getSize() + 2)), ClipContext.Block.COLLIDER, ClipContext.Fluid.ANY, this));
						if (result.getType() == HitResult.Type.MISS && Mth.degreesDifferenceAbs(getSeekerXRot(), ESMathUtil.positionToPitch(nextMovement)) <= 90 && Mth.degreesDifferenceAbs(getSeekerYRot(), ESMathUtil.positionToYaw(nextMovement)) <= 90) {
							break;
						}
					}
					setSeekerXRot(ESMathUtil.positionToPitch(nextMovement));
					setSeekerYRot(ESMathUtil.positionToYaw(nextMovement));
					setYRot(getSeekerYRot() - 90);
				}
				if (tickCount % MOVE_INTERVAL == 0) {
					this.hurtMarked = true;
					setDeltaMovement(nextMovement.scale(getAttributeValue(Attributes.FLYING_SPEED) * 0.3));
				}
			}
		} else {
			oldSeekerXRot = seekerXRot;
			oldSeekerYRot = seekerYRot;
			seekerXRot = getSeekerXRot();
			seekerYRot = getSeekerYRot();
		}
	}

	@Override
	public void handleEntityEvent(byte b) {
		if (b == EVENT_MOVE) {
			moveAnimationState.start(tickCount);
		} else {
			super.handleEntityEvent(b);
		}
	}

	@Override
	public boolean causeFallDamage(float f, float g, DamageSource damageSource) {
		return false;
	}

	@Override
	protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {

	}

	public static boolean checkSeekerSpawnRules(EntityType<? extends Seeker> type, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
		return checkAnyLightMonsterSpawnRules(type, level, spawnType, pos, random) && ESConfig.INSTANCE.mobsConfig.seeker.canSpawn();
	}
}
