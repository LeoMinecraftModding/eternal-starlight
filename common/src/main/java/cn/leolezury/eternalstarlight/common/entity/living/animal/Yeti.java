package cn.leolezury.eternalstarlight.common.entity.living.animal;

import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import com.mojang.serialization.Dynamic;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class Yeti extends Animal {
	protected static final EntityDataAccessor<Integer> ROLL_STATE = SynchedEntityData.defineId(Yeti.class, EntityDataSerializers.INT);

	public int getRollState() {
		return this.getEntityData().get(ROLL_STATE);
	}

	public void setRollState(int rollState) {
		this.getEntityData().set(ROLL_STATE, rollState);
	}

	public AnimationState idleAnimationState = new AnimationState();
	public AnimationState rollStartAnimationState = new AnimationState();
	public AnimationState rollAnimationState = new AnimationState();
	public AnimationState rollEndAnimationState = new AnimationState();
	private int rollTicks = 0;

	public void setRollTicks(int rollTicks) {
		this.rollTicks = rollTicks;
	}

	public int getRollTicks() {
		return rollTicks;
	}

	private int rollCooldown = 200;

	public void setRollCooldown(int rollCooldown) {
		this.rollCooldown = rollCooldown;
	}

	public int getRollCooldown() {
		return rollCooldown;
	}

	public float rollAngle, prevRollAngle;

	public Yeti(EntityType<? extends Animal> entityType, Level level) {
		super(entityType, level);
		this.getNavigation().setCanFloat(true);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0D).add(Attributes.MOVEMENT_SPEED, 0.25D);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(ROLL_STATE, 0);
	}

	protected Brain<?> makeBrain(Dynamic<?> dynamic) {
		return YetiAi.makeBrain(this.brainProvider().makeBrain(dynamic));
	}

	public Brain<Yeti> getBrain() {
		return (Brain<Yeti>) super.getBrain();
	}

	protected Brain.Provider<Yeti> brainProvider() {
		return Brain.provider(YetiAi.MEMORY_TYPES, YetiAi.SENSOR_TYPES);
	}

	protected void customServerAiStep() {
		this.level().getProfiler().push("YetiBrain");
		this.getBrain().tick((ServerLevel) this.level(), this);
		this.level().getProfiler().popPush("YetiActivityUpdate");
		YetiAi.updateActivity(this);
		this.level().getProfiler().pop();
		super.customServerAiStep();
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (rollTicks > 0) {
			rollTicks--;
		}
		if (rollCooldown > 0) {
			rollCooldown--;
		}
		if (level().isClientSide) {
			idleAnimationState.startIfStopped(tickCount);
			prevRollAngle = rollAngle;
			rollAngle += (float) (position().subtract(new Vec3(xOld, yOld, zOld)).length() / (3f / 260f));
			rollAngle = rollAngle % 360;
		}
	}

	public void stopAllAnimStates() {
		rollStartAnimationState.stop();
		rollAnimationState.stop();
		rollEndAnimationState.stop();
	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
		super.onSyncedDataUpdated(accessor);
		if (accessor.equals(ROLL_STATE) && getRollState() != 0) {
			stopAllAnimStates();
			switch (getRollState()) {
				case 1 -> {
					rollAngle = 0;
					prevRollAngle = 0;
					rollStartAnimationState.start(tickCount);
				}
				case 2 -> rollAnimationState.start(tickCount);
				case 3 -> rollEndAnimationState.start(tickCount);
			}
		}
	}

	@Override
	public boolean isFood(ItemStack itemStack) {
		return YetiAi.getTemptations().test(itemStack);
	}

	@Nullable
	@Override
	public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
		return new Yeti(ESEntities.YETI.get(), serverLevel);
	}

	public static boolean checkYetiSpawnRules(EntityType<? extends Yeti> type, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
		return level.getBlockState(pos.below()).is(BlockTags.DIRT);
	}
}
