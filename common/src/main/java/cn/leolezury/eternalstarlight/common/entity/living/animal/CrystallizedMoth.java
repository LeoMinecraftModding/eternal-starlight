package cn.leolezury.eternalstarlight.common.entity.living.animal;

import cn.leolezury.eternalstarlight.common.entity.living.goal.LookAtTargetGoal;
import cn.leolezury.eternalstarlight.common.entity.living.goal.RandomFlyGoal;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import cn.leolezury.eternalstarlight.common.vfx.ScreenShakeVfx;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class CrystallizedMoth extends Animal implements FlyingAnimal {
	protected static final EntityDataAccessor<Integer> ATTACK_TICKS = SynchedEntityData.defineId(CrystallizedMoth.class, EntityDataSerializers.INT);

	public int getAttackTicks() {
		return entityData.get(ATTACK_TICKS);
	}

	public void setAttackTicks(int attackTicks) {
		entityData.set(ATTACK_TICKS, attackTicks);
	}

	public CrystallizedMoth(EntityType<? extends Animal> entityType, Level level) {
		super(entityType, level);
		this.moveControl = new MothMoveControl();
		this.setPathfindingMalus(PathType.DANGER_FIRE, -1.0F);
		this.setPathfindingMalus(PathType.WATER, -1.0F);
		this.setPathfindingMalus(PathType.WATER_BORDER, -1.0F);
		this.setNoGravity(true);
	}

	public AnimationState idleAnimationState = new AnimationState();

	@Override
	protected PathNavigation createNavigation(Level level) {
		FlyingPathNavigation pathNavigation = new FlyingPathNavigation(this, level) {
			@Override
			public boolean isStableDestination(BlockPos blockPos) {
				return this.level.getBlockState(blockPos).isAir();
			}
		};
		pathNavigation.setCanOpenDoors(false);
		pathNavigation.setCanFloat(true);
		pathNavigation.setCanPassDoors(true);
		return pathNavigation;
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(ATTACK_TICKS, 0);
	}

	@Override
	protected void registerGoals() {
		goalSelector.addGoal(0, new FloatGoal(this));
		goalSelector.addGoal(1, new LookAtTargetGoal(this));
		goalSelector.addGoal(2, new CrystallizedMothAttackGoal());
		goalSelector.addGoal(3, new RandomFlyGoal(this) {
			@Override
			public boolean canUse() {
				return super.canUse() && CrystallizedMoth.this.getAttackTicks() <= 0;
			}

			@Override
			public boolean canContinueToUse() {
				return super.canContinueToUse() && CrystallizedMoth.this.getAttackTicks() <= 0;
			}
		});
		goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 6.0F));

		targetSelector.addGoal(0, new HurtByTargetGoal(this));
		targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Bat.class, false));
	}

	private class MothMoveControl extends MoveControl {
		public MothMoveControl() {
			super(CrystallizedMoth.this);
		}

		public void tick() {
			if (this.operation == MoveControl.Operation.MOVE_TO) {
				Vec3 vec3 = new Vec3(this.wantedX - mob.getX(), this.wantedY - mob.getY(), this.wantedZ - mob.getZ());
				double length = vec3.length();
				double size = mob.getBoundingBox().getSize();
				Vec3 delta = vec3.scale(this.speedModifier * 0.025D / length);
				mob.setDeltaMovement(mob.getDeltaMovement().add(delta));
				if (length < size * 0.5F) {
					this.operation = Operation.WAIT;
					mob.setDeltaMovement(mob.getDeltaMovement().scale(0.2));
				} else if (length >= size && CrystallizedMoth.this.getAttackTicks() <= 0) {
					mob.setYRot(-((float) Mth.atan2(delta.x, delta.z)) * Mth.RAD_TO_DEG);
				}
			}
			mob.setNoGravity(true);
		}
	}

	private class CrystallizedMothAttackGoal extends Goal {
		public CrystallizedMothAttackGoal() {
			this.setFlags(EnumSet.of(Flag.MOVE));
		}

		public boolean canUse() {
			return CrystallizedMoth.this.getTarget() != null && CrystallizedMoth.this.getAttackTicks() == 0 && CrystallizedMoth.this.random.nextInt(100) == 0;
		}

		public boolean canContinueToUse() {
			return CrystallizedMoth.this.getAttackTicks() < 100;
		}

		@Override
		public boolean requiresUpdateEveryTick() {
			return true;
		}

		@Override
		public void tick() {
			CrystallizedMoth.this.setAttackTicks(CrystallizedMoth.this.getAttackTicks() + 1);
			if (CrystallizedMoth.this.getTarget() != null) {
				LivingEntity target = CrystallizedMoth.this.getTarget();
				Vec3 selfPos = CrystallizedMoth.this.position().add(0, CrystallizedMoth.this.getBbHeight() / 2f, 0);
				Vec3 pos = target.position().add(0, target.getBbHeight() / 2f, 0);
				Vec3 subtract = pos.subtract(selfPos);
				CrystallizedMoth.this.setYRot(-((float) Mth.atan2(subtract.x, subtract.z)) * Mth.RAD_TO_DEG);
				if (CrystallizedMoth.this.hasLineOfSight(target) && CrystallizedMoth.this.distanceTo(target) < 30) {
					CrystallizedMoth.this.doHurtTarget(target);
				}
				if (CrystallizedMoth.this.level() instanceof ServerLevel serverLevel && CrystallizedMoth.this.getAttackTicks() % 5 == 0) {
					Vec3 delta = pos.subtract(selfPos).normalize().scale(0.8);
					ESPlatform.INSTANCE.sendToAllClients(serverLevel, new ParticlePacket(ESParticles.CRYSTALLIZED_MOTH_SONAR.get(), selfPos.x, selfPos.y, selfPos.z, delta.x, delta.y, delta.z));
					ScreenShakeVfx.createInstance(CrystallizedMoth.this.level().dimension(), CrystallizedMoth.this.position(), 30, 15, 0.15f, 0.24f, 4, 5).send(serverLevel);
				}
				Vec3 wanted = ESMathUtil.rotationToPosition(pos, 10, 0, ESMathUtil.positionToYaw(pos, selfPos) + 5);
				CrystallizedMoth.this.getMoveControl().setWantedPosition(wanted.x, wanted.y, wanted.z, 1);
			}
		}

		@Override
		public void stop() {
			CrystallizedMoth.this.setAttackTicks(0);
		}
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0D).add(Attributes.ATTACK_DAMAGE, 1.5).add(Attributes.FOLLOW_RANGE, 50).add(Attributes.MOVEMENT_SPEED, 0.3).add(Attributes.FLYING_SPEED, 0.6);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (level().isClientSide) {
			idleAnimationState.startIfStopped(tickCount);
		}
	}

	@Override
	public boolean isFood(ItemStack itemStack) {
		return false;
	}

	@Override
	protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {

	}

	@Nullable
	@Override
	public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
		return null;
	}

	@Override
	public boolean isFlying() {
		return !this.onGround();
	}

	public static boolean checkMothSpawnRules(EntityType<? extends CrystallizedMoth> type, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
		return !level.canSeeSky(pos) && pos.getY() < level.getHeight(Heightmap.Types.WORLD_SURFACE, pos.getX(), pos.getZ()) - 20;
	}
}
