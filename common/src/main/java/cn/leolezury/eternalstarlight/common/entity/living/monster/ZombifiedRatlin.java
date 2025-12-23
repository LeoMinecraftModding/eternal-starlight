package cn.leolezury.eternalstarlight.common.entity.living.monster;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.entity.living.animal.Ratlin;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import org.jetbrains.annotations.Nullable;

public class ZombifiedRatlin extends Ratlin {
	public ZombifiedRatlin(EntityType<? extends ZombifiedRatlin> type, Level level) {
		super(type, level);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new RestrictSunGoal(this));
		this.goalSelector.addGoal(1, new FleeSunGoal(this, 1));
		this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0, false));
		this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Player.class, 8f, 1.25D, 1.5D) {
			@Override
			public boolean canUse() {
				return super.canUse() && ZombifiedRatlin.this.getTarget() == null;
			}

			@Override
			public boolean canContinueToUse() {
				return super.canContinueToUse() && ZombifiedRatlin.this.getTarget() == null;
			}
		});
		this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(0, new HurtByTargetGoal(this).setAlertOthers());
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes()
			.add(Attributes.MAX_HEALTH, ESConfig.INSTANCE.mobsConfig.zombifiedRatlin.maxHealth())
			.add(Attributes.ARMOR, ESConfig.INSTANCE.mobsConfig.zombifiedRatlin.armor())
			.add(Attributes.ATTACK_DAMAGE, ESConfig.INSTANCE.mobsConfig.zombifiedRatlin.attackDamage())
			.add(Attributes.FOLLOW_RANGE, ESConfig.INSTANCE.mobsConfig.zombifiedRatlin.followRange())
			.add(Attributes.MOVEMENT_SPEED, 0.25D);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (this.isAlive() && this.isSunBurnTick()) {
			this.igniteForSeconds(8.0F);
		}
	}

	@Override
	public boolean isFood(ItemStack stack) {
		return false;
	}

	@Override
	public boolean canMate(Animal animal) {
		return false;
	}

	@Nullable
	@Override
	public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob mob) {
		return null;
	}

	public static boolean checkZombifiedRatlinSpawnRules(EntityType<? extends Ratlin> type, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
		return level.getBlockState(pos.below()).is(BlockTags.ANIMALS_SPAWNABLE_ON) && ESConfig.INSTANCE.mobsConfig.zombifiedRatlin.canSpawn();
	}
}
