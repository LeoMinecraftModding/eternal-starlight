package cn.leolezury.eternalstarlight.common.entity.living.animal;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class ShadowSnail extends Animal {
	private static final Ingredient FOOD_ITEMS = Ingredient.of(ESTags.Items.SHADOW_SNAIL_FOOD);

	protected static final EntityDataAccessor<Integer> HIDE_STATE = SynchedEntityData.defineId(ShadowSnail.class, EntityDataSerializers.INT);

	public int getHideState() {
		return this.getEntityData().get(HIDE_STATE);
	}

	public void setHideState(int hideState) {
		this.getEntityData().set(HIDE_STATE, hideState);
	}

	public AnimationState hideStartAnimationState = new AnimationState();
	public AnimationState hideAnimationState = new AnimationState();
	public AnimationState hideEndAnimationState = new AnimationState();

	private int transitionTicks;

	public ShadowSnail(EntityType<? extends ShadowSnail> type, Level level) {
		super(type, level);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(HIDE_STATE, 0);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new ShadowSnailDoNothingGoal());
		this.goalSelector.addGoal(1, new FloatGoal(this));
		this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.1D, FOOD_ITEMS, false));
		this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
	}

	class ShadowSnailDoNothingGoal extends Goal {
		public ShadowSnailDoNothingGoal() {
			this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
		}

		public boolean canUse() {
			return ShadowSnail.this.getHideState() != 0;
		}
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes()
			.add(Attributes.MAX_HEALTH, ESConfig.INSTANCE.mobsConfig.shadowSnail.maxHealth())
			.add(Attributes.ARMOR, ESConfig.INSTANCE.mobsConfig.shadowSnail.armor())
			.add(Attributes.MOVEMENT_SPEED, 0.08D);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (getHideState() != 0) {
			transitionTicks++;
		}
		if (getHideState() == 1 && transitionTicks > 20) {
			setHideState(2);
			transitionTicks = 0;
		}
		if (getHideState() == 2 && transitionTicks > 400) {
			setHideState(3);
			transitionTicks = 0;
		}
		if (getHideState() == 3 && transitionTicks > 20) {
			setHideState(0);
			transitionTicks = 0;
		}
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (source.is(DamageTypeTags.PANIC_CAUSES) && getHideState() == 0) {
			setHideState(1);
			transitionTicks = 0;
			getNavigation().stop();
		}
		return super.hurt(source, (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY) || getHideState() != 2) ? amount : amount / 8);
	}

	public void stopAllAnimStates() {
		hideStartAnimationState.stop();
		hideAnimationState.stop();
		hideEndAnimationState.stop();
	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
		super.onSyncedDataUpdated(accessor);
		if (accessor.equals(HIDE_STATE) && getHideState() != 0) {
			stopAllAnimStates();
			switch (getHideState()) {
				case 1 -> hideStartAnimationState.start(tickCount);
				case 2 -> hideAnimationState.start(tickCount);
				case 3 -> hideEndAnimationState.start(tickCount);
			}
		}
	}

	@Override
	public boolean isFood(ItemStack stack) {
		return FOOD_ITEMS.test(stack);
	}

	@Nullable
	@Override
	public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob mob) {
		return ESEntities.SHADOW_SNAIL.get().create(level);
	}

	@Override
	protected void ageBoundaryReached() {
		super.ageBoundaryReached();
		if (!this.isBaby()) {
			spawnAtLocation(ESItems.SHADOW_SNAIL_SHELL.get());
		}
	}

	public static boolean checkShadowSnailSpawnRules(EntityType<? extends ShadowSnail> type, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
		return level.getBlockState(pos.below()).is(BlockTags.ANIMALS_SPAWNABLE_ON) && ESConfig.INSTANCE.mobsConfig.shadowSnail.canSpawn();
	}
}
