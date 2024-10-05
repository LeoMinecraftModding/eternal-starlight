package cn.leolezury.eternalstarlight.common.entity.living.animal;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.entity.living.goal.RandomFlyGoal;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import cn.leolezury.eternalstarlight.common.vfx.ScreenShakeVfx;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.UUID;

public class CrystallizedMoth extends TamableAnimal implements FlyingAnimal, NeutralMob {
	private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
	private int remainingPersistentAngerTime;
	@Nullable
	private UUID persistentAngerTarget;

	private static final Ingredient FOOD_ITEMS = Ingredient.of(ESTags.Items.CRYSTALLIZED_MOTH_FOOD);

	protected static final EntityDataAccessor<Integer> ATTACK_TICKS = SynchedEntityData.defineId(CrystallizedMoth.class, EntityDataSerializers.INT);

	public int getAttackTicks() {
		return this.getEntityData().get(ATTACK_TICKS);
	}

	public void setAttackTicks(int attackTicks) {
		this.getEntityData().set(ATTACK_TICKS, attackTicks);
	}

	public CrystallizedMoth(EntityType<? extends TamableAnimal> entityType, Level level) {
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
		goalSelector.addGoal(1, new CrystallizedMothAttackGoal());
		goalSelector.addGoal(2, new FollowOwnerGoal(this, 1.0, 10.0F, 2.0F));
		goalSelector.addGoal(3, new BreedGoal(this, 1.0));
		goalSelector.addGoal(4, new TemptGoal(this, 1.2D, FOOD_ITEMS, false));
		goalSelector.addGoal(5, new RandomFlyGoal(this) {
			@Override
			public boolean canUse() {
				return super.canUse() && CrystallizedMoth.this.getAttackTicks() <= 0;
			}

			@Override
			public boolean canContinueToUse() {
				return super.canContinueToUse() && CrystallizedMoth.this.getAttackTicks() <= 0;
			}
		});
		goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));

		targetSelector.addGoal(0, new OwnerHurtByTargetGoal(this));
		targetSelector.addGoal(1, new OwnerHurtTargetGoal(this));
		targetSelector.addGoal(2, new HurtByTargetGoal(this));
		targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Bat.class, false));
		targetSelector.addGoal(4, new ResetUniversalAngerTargetGoal<>(this, true));
	}

	@Override
	public int getRemainingPersistentAngerTime() {
		return remainingPersistentAngerTime;
	}

	@Override
	public void setRemainingPersistentAngerTime(int i) {
		this.remainingPersistentAngerTime = i;
	}

	@Nullable
	@Override
	public UUID getPersistentAngerTarget() {
		return persistentAngerTarget;
	}

	@Override
	public void setPersistentAngerTarget(@Nullable UUID uuid) {
		this.persistentAngerTarget = uuid;
	}

	@Override
	public void startPersistentAngerTimer() {
		this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(getRandom()));
	}

	private class MothMoveControl extends MoveControl {
		public MothMoveControl() {
			super(CrystallizedMoth.this);
		}

		@Override
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

		@Override
		public boolean canUse() {
			return CrystallizedMoth.this.getTarget() != null && CrystallizedMoth.this.getAttackTicks() == 0 && CrystallizedMoth.this.random.nextInt(50) == 0;
		}

		@Override
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
				CrystallizedMoth.this.getLookControl().setLookAt(target, 360, 360);
				Vec3 selfPos = CrystallizedMoth.this.position().add(0, CrystallizedMoth.this.getBbHeight() / 2f, 0);
				Vec3 pos = target.position().add(0, target.getBbHeight() / 2f, 0);
				Vec3 subtract = pos.subtract(selfPos);
				CrystallizedMoth.this.setYRot(-((float) Mth.atan2(subtract.x, subtract.z)) * Mth.RAD_TO_DEG);
				if (CrystallizedMoth.this.getAttackTicks() > 15 && CrystallizedMoth.this.hasLineOfSight(target) && CrystallizedMoth.this.distanceTo(target) < 30) {
					target.hurt(ESDamageTypes.getEntityDamageSource(level(), ESDamageTypes.SONAR, CrystallizedMoth.this), (float) getAttributeValue(Attributes.ATTACK_DAMAGE) * (target.getType().is(ESTags.EntityTypes.VULNERABLE_TO_SONAR_BOMB) ? 4 : 1));
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
		return Mob.createMobAttributes()
			.add(Attributes.MAX_HEALTH, ESConfig.INSTANCE.mobsConfig.crystallizedMoth.maxHealth())
			.add(Attributes.ARMOR, ESConfig.INSTANCE.mobsConfig.crystallizedMoth.armor())
			.add(Attributes.ATTACK_DAMAGE, ESConfig.INSTANCE.mobsConfig.crystallizedMoth.attackDamage())
			.add(Attributes.FOLLOW_RANGE, ESConfig.INSTANCE.mobsConfig.crystallizedMoth.followRange())
			.add(Attributes.MOVEMENT_SPEED, 0.3)
			.add(Attributes.FLYING_SPEED, 0.6);
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
		return FOOD_ITEMS.test(itemStack);
	}

	// copied from vanilla Wolf
	@Override
	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		ItemStack itemStack = player.getItemInHand(hand);
		if (this.level().isClientSide && (!this.isBaby() || !this.isFood(itemStack))) {
			boolean bl = this.isOwnedBy(player) || this.isTame() || this.isFood(itemStack) && !this.isTame() && !isAngry();
			return bl ? InteractionResult.CONSUME : InteractionResult.PASS;
		} else if (this.isTame()) {
			if (this.isFood(itemStack) && this.getHealth() < this.getMaxHealth()) {
				itemStack.consume(1, player);
				FoodProperties foodProperties = itemStack.get(DataComponents.FOOD);
				float f = foodProperties != null ? (float) foodProperties.nutrition() : 1.0F;
				this.heal(2.0F * f);
				return InteractionResult.sidedSuccess(this.level().isClientSide());
			}
		} else if (this.isFood(itemStack) && !this.isAngry()) {
			itemStack.consume(1, player);
			this.tryToTame(player);
			return InteractionResult.SUCCESS;
		}
		return super.mobInteract(player, hand);
	}

	private void tryToTame(Player player) {
		if (this.random.nextInt(3) == 0) {
			this.tame(player);
			this.getNavigation().stop();
			this.setTarget(null);
			this.level().broadcastEntityEvent(this, (byte) 7);
		} else {
			this.level().broadcastEntityEvent(this, (byte) 6);
		}
	}

	// copied from vanilla Wolf
	@Override
	public boolean wantsToAttack(LivingEntity target, LivingEntity owner) {
		if (!(target instanceof Creeper) && !(target instanceof Ghast)) {
			return switch (target) {
				case CrystallizedMoth zombie -> !zombie.isTame() || zombie.getOwner() != owner;
				case Player playerTarget when owner instanceof Player playerOwner && !playerOwner.canHarmPlayer(playerTarget) -> false;
				case AbstractHorse horse when horse.isTamed() -> false;
				default -> !(target instanceof TamableAnimal animal) || !animal.isTame();
			};
		} else {
			return false;
		}
	}

	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return !this.isTame();
	}

	@Override
	public boolean shouldTryTeleportToOwner() {
		LivingEntity livingEntity = this.getOwner();
		return livingEntity != null && this.distanceToSqr(this.getOwner()) >= 20 * 20;
	}

	@Override
	protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {

	}

	@Nullable
	@Override
	public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob ageableMob) {
		CrystallizedMoth moth = ESEntities.CRYSTALLIZED_MOTH.get().create(level);
		if (this.isTame() && moth != null) {
			moth.setOwnerUUID(this.getOwnerUUID());
			moth.setTame(true, true);
		}
		return moth;
	}

	@Override
	public boolean isFlying() {
		return !this.onGround();
	}

	public static boolean checkMothSpawnRules(EntityType<? extends CrystallizedMoth> type, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
		return !level.canSeeSky(pos) && pos.getY() < level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, pos.getX(), pos.getZ()) - 20 && ESConfig.INSTANCE.mobsConfig.crystallizedMoth.canSpawn();
	}
}
