package cn.leolezury.eternalstarlight.common.entity.living.boss.creeper;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.entity.living.boss.ESBoss;
import cn.leolezury.eternalstarlight.common.entity.living.goal.LookAtTargetGoal;
import cn.leolezury.eternalstarlight.common.entity.living.goal.MoveToTargetGoal;
import cn.leolezury.eternalstarlight.common.entity.living.phase.BehaviorManager;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
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

import java.util.List;

public class SolarCreeper extends ESBoss {
	private static final String TAG_INTRO_COMPLETED = "intro_completed";

	private int oldAnimationTicks, animationTicks;
	private boolean introCompleted = false;

	public float getAnimationTicks(float partialTicks) {
		return Mth.lerp(partialTicks, oldAnimationTicks, animationTicks);
	}

	public void finishIntro() {
		this.introCompleted = true;
	}

	public SolarCreeper(EntityType<? extends SolarCreeper> entityType, Level level) {
		super(entityType, level);
	}

	private final BehaviorManager<SolarCreeper> behaviorManager = new BehaviorManager<>(this, List.of(
		new SolarCreeperIntroPhase()
	));

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

	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
		if (accessor.equals(BEHAVIOR_STATE)) {
			stopAllAnimStates();
			switch (getBehaviorState()) {

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
			setNoGravity(!introCompleted);
		}
		super.aiStep();
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
		}
	}

	@Override
	public boolean canBossMove() {
		return introCompleted;
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
}
