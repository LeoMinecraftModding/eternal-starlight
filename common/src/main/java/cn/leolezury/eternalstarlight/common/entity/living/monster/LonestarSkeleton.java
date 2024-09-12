package cn.leolezury.eternalstarlight.common.entity.living.monster;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.entity.living.goal.LonestarSkeletonShootBladeGoal;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

// todo: fix this mess wtf is this
public class LonestarSkeleton extends AbstractSkeleton {
	private final LonestarSkeletonShootBladeGoal shootBladeGoal = new LonestarSkeletonShootBladeGoal(this, 1.0, 20, 15.0F);

	public LonestarSkeleton(EntityType<? extends LonestarSkeleton> type, Level level) {
		super(type, level);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes()
			.add(Attributes.MAX_HEALTH, ESConfig.INSTANCE.mobsConfig.lonestarSkeleton.maxHealth())
			.add(Attributes.ARMOR, ESConfig.INSTANCE.mobsConfig.lonestarSkeleton.armor())
			.add(Attributes.ATTACK_DAMAGE, ESConfig.INSTANCE.mobsConfig.lonestarSkeleton.attackDamage())
			.add(Attributes.FOLLOW_RANGE, ESConfig.INSTANCE.mobsConfig.lonestarSkeleton.followRange())
			.add(Attributes.MOVEMENT_SPEED, 0.25);
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData) {
		SpawnGroupData data = super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData);
		Arrays.fill(this.handDropChances, 0.2F);
		return data;
	}

	@Override
	protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
		super.populateDefaultEquipmentSlots(randomSource, difficultyInstance);
		this.setItemSlot(EquipmentSlot.MAINHAND, ESItems.SHATTERED_SWORD.get().getDefaultInstance());
	}

	@Override
	public void reassessWeaponGoal() {
		if (!this.level().isClientSide()) {
			this.goalSelector.removeGoal(this.shootBladeGoal);
			if (getMainHandItem().is(ESItems.SHATTERED_SWORD.get())) {
				this.goalSelector.addGoal(4, this.shootBladeGoal);
			} else {
				super.reassessWeaponGoal();
			}
		} else {
			super.reassessWeaponGoal();
		}
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.SKELETON_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return SoundEvents.SKELETON_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.SKELETON_DEATH;
	}

	@Override
	public SoundEvent getStepSound() {
		return SoundEvents.SKELETON_STEP;
	}

	public static boolean checkLonestarSkeletonSpawnRules(EntityType<? extends LonestarSkeleton> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
		return checkMonsterSpawnRules(type, level, spawnType, pos, random) && ESConfig.INSTANCE.mobsConfig.lonestarSkeleton.canSpawn();
	}
}
