package cn.leolezury.eternalstarlight.common.entity.living.animal;

import cn.leolezury.eternalstarlight.common.block.StarfireBirdNestBlock;
import cn.leolezury.eternalstarlight.common.block.entity.StarfireBirdNestBlockEntity;
import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.data.ESLootTables;
import cn.leolezury.eternalstarlight.common.entity.living.goal.RandomFlyGoal;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESPoiTypes;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.*;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;
import java.util.UUID;

public class StarfireBird extends Animal implements FlyingAnimal {
	public static final String TAG_SPECIAL_VARIANT = "special_variant";
	private static final String TAG_NEST_POS = "nest_pos";
	private static final String TAG_STAY_OUT_OF_NEST_TICKS = "stay_out_of_nest_ticks";
	private static final String TAG_HAS_EGG = "has_egg";
	private static final String TAG_TRUSTED_PLAYERS = "trusted_players";
	private static final String TAG_GIFT_COUNT = "gift_count";
	private static final String TAG_GIFT_COOLDOWN = "gift_cooldown";
	protected static final EntityDataAccessor<Boolean> SPECIAL_VARIANT = SynchedEntityData.defineId(StarfireBird.class, EntityDataSerializers.BOOLEAN);

	public boolean isSpecialVariant() {
		return this.getEntityData().get(SPECIAL_VARIANT);
	}

	public void setSpecialVariant(boolean specialVariant) {
		this.getEntityData().set(SPECIAL_VARIANT, specialVariant);
	}

	private BlockPos nestPos = null;
	private int nestDestroyedTicks;

	public void setNestPos(BlockPos nestPos) {
		this.nestPos = nestPos;
	}

	public boolean hasNest() {
		return nestPos != null;
	}

	private int stayOutOfNestTicks;

	public void setStayOutOfNestTicks(int stayOutOfNestTicks) {
		this.stayOutOfNestTicks = stayOutOfNestTicks;
	}

	private boolean hasEgg = false;

	public void setHasEgg(boolean hasEgg) {
		this.hasEgg = hasEgg;
	}

	private final List<UUID> trustedPlayers = Lists.newArrayList();

	public void addTrustedPlayer(UUID uuid) {
		if (uuid != null) {
			trustedPlayers.add(uuid);
		}
	}

	private int giftCount;
	private int giftCooldown;

	public void addGiftCount() {
		giftCount++;
	}

	private float oldFlapScale, flapScale;

	public float getFlapScale(float partialTick) {
		return Mth.lerp(partialTick, oldFlapScale, flapScale);
	}

	private boolean flying = true;
	private int flyingTicks, walkingTicks;

	public StarfireBird(EntityType<? extends StarfireBird> entityType, Level level) {
		super(entityType, level);
		this.setPathfindingMalus(PathType.DANGER_FIRE, -1.0F);
		this.setPathfindingMalus(PathType.WATER, -1.0F);
		this.setPathfindingMalus(PathType.WATER_BORDER, -1.0F);
		this.switchMoveType(true);
	}

	public AnimationState idleAnimationState = new AnimationState();
	public AnimationState nestIdleAnimationState = new AnimationState();
	public AnimationState flapAnimationState = new AnimationState();

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

	private void switchMoveType(boolean fly) {
		flyingTicks = 0;
		walkingTicks = 0;
		flying = fly;
		this.setNoGravity(fly);
		this.stopInPlace();
		if (fly) {
			this.moveControl = new FlyingMoveControl(this, 20, true);
			this.navigation = createNavigation(level());
		} else {
			this.moveControl = new MoveControl(this);
			this.navigation = super.createNavigation(level());
		}
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(SPECIAL_VARIANT, false);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new StarfireBirdBreedGoal());
		this.goalSelector.addGoal(2, new TemptGoal(this, 1, stack -> stack.is(ESTags.Items.STARFIRE_BIRD_FOOD), false));
		this.goalSelector.addGoal(3, new StarfireBirdEnterNestGoal());
		this.goalSelector.addGoal(4, new StarfireBirdGoToNestGoal());
		this.goalSelector.addGoal(5, new StarfireBirdLandGoal());
		this.goalSelector.addGoal(6, new StarfireBirdTakeOffGoal());
		this.goalSelector.addGoal(7, new StarfireBirdGiftGoal());
		this.goalSelector.addGoal(8, new PanicGoal(this, 1.25D));
		this.goalSelector.addGoal(9, new RandomFlyGoal(this) {
			@Override
			public boolean canUse() {
				return super.canUse() && StarfireBird.this.flying;
			}

			@Override
			public boolean canContinueToUse() {
				return super.canContinueToUse() && StarfireBird.this.flying;
			}
		});
		this.goalSelector.addGoal(10, new WaterAvoidingRandomStrollGoal(this, 1.0, 0.0F) {
			@Override
			public boolean canUse() {
				return super.canUse() && !StarfireBird.this.flying;
			}

			@Override
			public boolean canContinueToUse() {
				return super.canContinueToUse() && !StarfireBird.this.flying;
			}
		});
		this.goalSelector.addGoal(11, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(12, new RandomLookAroundGoal(this));
	}

	private class StarfireBirdBreedGoal extends BreedGoal {
		public StarfireBirdBreedGoal() {
			super(StarfireBird.this, 1);
		}

		@Override
		protected void breed() {
			ServerPlayer loveCause = this.animal.getLoveCause();
			if (loveCause == null && this.partner != null && this.partner.getLoveCause() != null) {
				loveCause = this.partner.getLoveCause();
			}

			if (loveCause != null && this.partner != null) {
				loveCause.awardStat(Stats.ANIMALS_BRED);
				CriteriaTriggers.BRED_ANIMALS.trigger(loveCause, this.animal, this.partner, null);
				StarfireBird.this.addTrustedPlayer(loveCause.getUUID());
				if (this.partner instanceof StarfireBird bird) {
					bird.addTrustedPlayer(loveCause.getUUID());
				}
			}

			StarfireBird.this.setHasEgg(true);
			this.animal.setAge(6000);
			this.partner.setAge(6000);
			this.animal.resetLove();
			this.partner.resetLove();
			RandomSource random = this.animal.getRandom();
			if (this.level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
				this.level.addFreshEntity(new ExperienceOrb(this.level, this.animal.getX(), this.animal.getY(), this.animal.getZ(), random.nextInt(7) + 1));
			}
			StarfireBird.this.stayOutOfNestTicks = 0;
		}
	}

	private class StarfireBirdEnterNestGoal extends Goal {
		@Override
		public boolean canUse() {
			if (StarfireBird.this.hasNest() && StarfireBird.this.wantsToEnterNest() && StarfireBird.this.nestPos.closerToCenterThan(StarfireBird.this.position(), 2)) {
				BlockEntity blockEntity = StarfireBird.this.level().getBlockEntity(StarfireBird.this.nestPos);
				if (blockEntity instanceof StarfireBirdNestBlockEntity nest) {
					return !nest.isFullForAdults();
				}
			}
			return false;
		}

		@Override
		public boolean canContinueToUse() {
			return false;
		}

		@Override
		public void start() {
			if (StarfireBird.this.hasNest()) {
				BlockEntity blockEntity = StarfireBird.this.level().getBlockEntity(StarfireBird.this.nestPos);
				if (blockEntity instanceof StarfireBirdNestBlockEntity nest) {
					if (StarfireBird.this.hasEgg) {
						BlockState nestState = StarfireBird.this.level().getBlockState(StarfireBird.this.nestPos);
						int eggs = nestState.getValue(StarfireBirdNestBlock.EGGS);
						if (eggs < 3) {
							StarfireBird.this.level().setBlockAndUpdate(StarfireBird.this.nestPos, nestState.setValue(StarfireBirdNestBlock.EGGS, eggs + 1));
							StarfireBird.this.setHasEgg(false);
						}
					}
					nest.addAdultOccupant(StarfireBird.this);
				}
			}
		}
	}

	private class StarfireBirdGoToNestGoal extends Goal {
		private int tryTicks = 0;

		public StarfireBirdGoToNestGoal() {
			this.setFlags(EnumSet.of(Flag.MOVE));
		}

		@Override
		public boolean requiresUpdateEveryTick() {
			return true;
		}

		@Override
		public boolean canUse() {
			return StarfireBird.this.getRandom().nextInt(reducedTickDelay(120)) == 0 && canUseGoal();
		}

		@Override
		public boolean canContinueToUse() {
			return canUseGoal();
		}

		private boolean canUseGoal() {
			return StarfireBird.this.nestPos != null
				&& StarfireBird.this.wantsToEnterNest()
				&& tryTicks < 300
				&& !StarfireBird.this.nestPos.closerToCenterThan(StarfireBird.this.position(), 2)
				&& StarfireBird.this.isNestValid()
				&& StarfireBird.this.level().getBlockEntity(StarfireBird.this.nestPos) instanceof StarfireBirdNestBlockEntity nest
				&& !nest.isFullForAdults();
		}

		@Override
		public void start() {
			this.tryTicks = 0;
		}

		@Override
		public void stop() {
			this.tryTicks = 0;
			StarfireBird.this.navigation.stop();
		}

		@Override
		public void tick() {
			BlockPos target = StarfireBird.this.nestPos;
			if (target != null) {
				StarfireBird.this.getLookControl().setLookAt(target.getX() + 0.5, target.getY() + 0.5, target.getZ() + 0.5, 10.0F, StarfireBird.this.getMaxHeadXRot());
				StarfireBird.this.getNavigation().moveTo(target.getX() + 0.5, target.getY() + 0.5, target.getZ() + 0.5, 1);
			}
			this.tryTicks++;
		}
	}

	private class StarfireBirdLandGoal extends Goal {
		private int tryTicks = 0;
		private Vec3 target;

		public StarfireBirdLandGoal() {
			this.setFlags(EnumSet.of(Flag.MOVE));
		}

		@Override
		public boolean requiresUpdateEveryTick() {
			return true;
		}

		@Override
		public boolean canUse() {
			if (canUseGoal()) {
				target = LandRandomPos.getPos(StarfireBird.this, 15, 20);
				return validTarget();
			}
			return false;
		}

		@Override
		public boolean canContinueToUse() {
			return canUseGoal() && validTarget();
		}

		private boolean canUseGoal() {
			return StarfireBird.this.flying
				&& StarfireBird.this.flyingTicks >= 300
				&& tryTicks < 300;
		}

		private boolean validTarget() {
			return target != null
				&& StarfireBird.this.level().getBlockState(BlockPos.containing(target)).isAir()
				&& StarfireBird.this.level().getBlockState(BlockPos.containing(target).below()).entityCanStandOnFace(StarfireBird.this.level(), BlockPos.containing(target).below(), StarfireBird.this, Direction.UP)
				&& StarfireBird.this.getPathfindingMalus(WalkNodeEvaluator.getPathTypeStatic(StarfireBird.this, BlockPos.containing(target))) == 0.0F;
		}

		@Override
		public boolean isInterruptable() {
			return false;
		}

		@Override
		public void start() {
			this.tryTicks = 0;
		}

		@Override
		public void stop() {
			this.tryTicks = 0;
			StarfireBird.this.navigation.stop();
		}

		@Override
		public void tick() {
			if (target != null) {
				StarfireBird.this.getLookControl().setLookAt(target.x, target.y, target.z, 10.0F, StarfireBird.this.getMaxHeadXRot());
				StarfireBird.this.getNavigation().moveTo(target.x, target.y, target.z, 1);
				if (StarfireBird.this.onGround()
					|| BlockPos.containing(StarfireBird.this.position()).equals(BlockPos.containing(target))
					|| BlockPos.containing(StarfireBird.this.position()).below().equals(BlockPos.containing(target))) {
					StarfireBird.this.switchMoveType(false);
					StarfireBird.this.navigation.stop();
					target = null;
				}
			}
			this.tryTicks++;
		}
	}

	private class StarfireBirdTakeOffGoal extends Goal {
		@Override
		public boolean canUse() {
			if (!StarfireBird.this.flying && !StarfireBird.this.isBaby()) {
				List<LivingEntity> entities = StarfireBird.this.level().getNearbyEntities(LivingEntity.class, TargetingConditions.forCombat(), StarfireBird.this, StarfireBird.this.getBoundingBox().inflate(5));
				return StarfireBird.this.walkingTicks > 300
					|| StarfireBird.this.getLastHurtByMob() != null
					|| (StarfireBird.this.wantsToEnterNest() && StarfireBird.this.isNestValid() && StarfireBird.this.level().getBlockEntity(StarfireBird.this.nestPos) instanceof StarfireBirdNestBlockEntity nest && !nest.isFullForAdults())
					|| entities.stream().anyMatch(e -> e.getType().is(ESTags.EntityTypes.STARFIRE_BIRD_AFRAID_OF) && !trustedPlayers.contains(e.getUUID()));
			}
			return false;
		}

		@Override
		public boolean canContinueToUse() {
			return false;
		}

		@Override
		public boolean isInterruptable() {
			return false;
		}

		@Override
		public void start() {
			StarfireBird.this.switchMoveType(true);
			Vec3 pos = StarfireBird.this.position();
			StarfireBird.this.getNavigation().moveTo(pos.x, pos.y + 3, pos.z, 1);
		}
	}

	private class StarfireBirdGiftGoal extends Goal {
		private Player giftTarget = null;
		private int tryTicks = 0;

		public StarfireBirdGiftGoal() {
			this.setFlags(EnumSet.of(Flag.MOVE));
		}

		@Override
		public boolean requiresUpdateEveryTick() {
			return true;
		}

		@Override
		public boolean canUse() {
			return StarfireBird.this.getRandom().nextInt(reducedTickDelay(120)) == 0 && canUseGoal();
		}

		@Override
		public boolean canContinueToUse() {
			return canUseGoal();
		}

		private boolean canUseGoal() {
			if (StarfireBird.this.giftCooldown > 0 || StarfireBird.this.giftCount <= 0) {
				return false;
			}
			if ((giftTarget == null || !giftTarget.isAlive()) && !StarfireBird.this.trustedPlayers.isEmpty()) {
				UUID playerId = StarfireBird.this.trustedPlayers.get(StarfireBird.this.getRandom().nextInt(StarfireBird.this.trustedPlayers.size()));
				giftTarget = StarfireBird.this.level().getPlayerByUUID(playerId);
			}
			return tryTicks < 300 && giftTarget != null && giftTarget.isAlive() && !StarfireBird.this.isBaby();
		}

		@Override
		public void start() {
			this.tryTicks = 0;
		}

		@Override
		public void stop() {
			this.tryTicks = 0;
			StarfireBird.this.navigation.stop();
		}

		@Override
		public void tick() {
			if (giftTarget != null) {
				StarfireBird.this.getLookControl().setLookAt(giftTarget, 10.0F, StarfireBird.this.getMaxHeadXRot());
				StarfireBird.this.getNavigation().moveTo(giftTarget, 1);
				if (StarfireBird.this.distanceTo(giftTarget) < 3 && StarfireBird.this.level() instanceof ServerLevel serverLevel) {
					LootTable lootTable = serverLevel.getServer().reloadableRegistries().getLootTable(ESLootTables.GAMEPLAY_STARFIRE_BIRD_GIFT);
					List<ItemStack> items = lootTable.getRandomItems(
						new LootParams.Builder(serverLevel)
							.withParameter(LootContextParams.ORIGIN, StarfireBird.this.position())
							.withParameter(LootContextParams.THIS_ENTITY, StarfireBird.this)
							.create(LootContextParamSets.GIFT)
					);
					for (ItemStack item : items) {
						BehaviorUtils.throwItem(StarfireBird.this, item, giftTarget.position().add(0, giftTarget.getBbHeight() / 2, 0));
					}
					giftTarget = null;
					StarfireBird.this.giftCooldown = 600;
					StarfireBird.this.giftCount--;
				}
			}
			this.tryTicks++;
		}
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes()
			.add(Attributes.MAX_HEALTH, ESConfig.INSTANCE.mobsConfig.starfireBird.maxHealth())
			.add(Attributes.ARMOR, ESConfig.INSTANCE.mobsConfig.starfireBird.armor())
			.add(Attributes.MOVEMENT_SPEED, 0.3)
			.add(Attributes.FLYING_SPEED, 0.75);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (!level().isClientSide) {
			if (flying) {
				flyingTicks++;
				walkingTicks = 0;
			} else {
				walkingTicks++;
				flyingTicks = 0;
			}
			giftCooldown--;
			if (giftCooldown < 0) {
				giftCooldown = 0;
			}
			if (!isNestValid()) {
				nestDestroyedTicks++;
			} else {
				nestDestroyedTicks = 0;
			}
			if (nestDestroyedTicks > 600) {
				nestPos = null;
			}
			stayOutOfNestTicks--;
			if (stayOutOfNestTicks < 0) {
				stayOutOfNestTicks = 0;
			}
			if (nestPos == null && tickCount % 20 == 0 && !isBaby() && level() instanceof ServerLevel serverLevel) {
				PoiManager poiManager = serverLevel.getPoiManager();
				List<BlockPos> availableNests = poiManager
					.findAllClosestFirstWithType(poi -> poi.is(ESPoiTypes.STARFIRE_BIRD_NEST.getResourceKey()), this::canBeSeenAsNewNest, blockPosition(), 48, PoiManager.Occupancy.ANY)
					.limit(5L)
					.map(Pair::getSecond)
					.toList();
				if (!availableNests.isEmpty()) {
					nestPos = availableNests.get(getRandom().nextInt(availableNests.size()));
					nestDestroyedTicks = 0;
					for (int i = 0; i < 5; ++i) {
						double d = getRandom().nextGaussian() * 0.02;
						double e = getRandom().nextGaussian() * 0.02;
						double f = getRandom().nextGaussian() * 0.02;
						ESPlatform.INSTANCE.sendToAllClients(serverLevel, new ParticlePacket(ParticleTypes.HAPPY_VILLAGER, getRandomX(1.0F), getRandomY() + 1.0F, getRandomZ(1.0F), d, e, f));
					}
				}
			}
		} else {
			idleAnimationState.startIfStopped(tickCount);
			flapAnimationState.startIfStopped(tickCount);
			oldFlapScale = flapScale;
			if (onGround()) {
				flapScale -= 0.1f;
			} else {
				flapScale += 0.3f;
			}
			flapScale = Mth.clamp(flapScale, 0, 1);
		}
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		boolean success = super.hurt(source, amount);
		if (success && source.getEntity() instanceof Player player) {
			trustedPlayers.remove(player.getUUID());
		}
		return success;
	}

	private boolean wantsToEnterNest() {
		return this.stayOutOfNestTicks <= 0 && !isInLove();
	}

	private boolean isNestValid() {
		return this.nestPos != null
			&& this.level().getBlockState(this.nestPos).is(ESTags.Blocks.STARFIRE_BIRD_NESTS)
			&& this.level().getBlockEntity(this.nestPos) instanceof StarfireBirdNestBlockEntity;
	}

	private boolean canBeSeenAsNewNest(BlockPos pos) {
		BlockState state = this.level().getBlockState(pos);
		return state.is(ESTags.Blocks.STARFIRE_BIRD_NESTS)
			&& this.level().getBlockEntity(pos) instanceof StarfireBirdNestBlockEntity entity
			&& !entity.isFullForAdults()
			&& entity.getItems().stream().anyMatch(stack -> !stack.isEmpty());
	}

	@Override
	protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {

	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance instance, MobSpawnType spawnType, @Nullable SpawnGroupData data) {
		if (random.nextInt(20) == 0) {
			setSpecialVariant(true);
		}
		return super.finalizeSpawn(level, instance, spawnType, data);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		setSpecialVariant(compoundTag.getBoolean(TAG_SPECIAL_VARIANT));
		NbtUtils.readBlockPos(compoundTag, TAG_NEST_POS).ifPresent(pos -> nestPos = pos);
		stayOutOfNestTicks = compoundTag.getInt(TAG_STAY_OUT_OF_NEST_TICKS);
		hasEgg = compoundTag.getBoolean(TAG_HAS_EGG);
		this.trustedPlayers.clear();
		if (compoundTag.contains(TAG_TRUSTED_PLAYERS, CompoundTag.TAG_LIST)) {
			ListTag listTag = compoundTag.getList(TAG_TRUSTED_PLAYERS, CompoundTag.TAG_INT_ARRAY);
			for (Tag tag : listTag) {
				if (tag != null && tag.getType() == IntArrayTag.TYPE && ((IntArrayTag) tag).getAsIntArray().length == 4) {
					this.trustedPlayers.add(NbtUtils.loadUUID(tag));
				}
			}
		}
		giftCount = compoundTag.getInt(TAG_GIFT_COUNT);
		giftCooldown = compoundTag.getInt(TAG_GIFT_COOLDOWN);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putBoolean(TAG_SPECIAL_VARIANT, isSpecialVariant());
		if (hasNest()) {
			compoundTag.put(TAG_NEST_POS, NbtUtils.writeBlockPos(nestPos));
		}
		compoundTag.putInt(TAG_STAY_OUT_OF_NEST_TICKS, stayOutOfNestTicks);
		compoundTag.putBoolean(TAG_HAS_EGG, hasEgg);
		ListTag listTag = new ListTag();
		for (UUID uuid : this.trustedPlayers) {
			if (uuid != null) {
				listTag.add(NbtUtils.createUUID(uuid));
			}
		}
		compoundTag.put(TAG_TRUSTED_PLAYERS, listTag);
		compoundTag.putInt(TAG_GIFT_COUNT, giftCount);
		compoundTag.putInt(TAG_GIFT_COOLDOWN, giftCooldown);
	}

	@Override
	public boolean isFood(ItemStack stack) {
		return stack.is(ESTags.Items.STARFIRE_BIRD_FOOD);
	}

	@Nullable
	@Override
	public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob mob) {
		return ESEntities.STARFIRE_BIRD.get().create(level);
	}

	@Override
	public boolean isFlying() {
		return !this.onGround();
	}

	@Override
	protected void ageBoundaryReached() {
		super.ageBoundaryReached();
		refreshDimensions();
	}

	@Override
	public void setAge(int age) {
		super.setAge(age);
		if (isBaby()) {
			switchMoveType(false);
		}
	}

	@Override
	public EntityDimensions getDefaultDimensions(Pose pose) {
		return isBaby() ? super.getDefaultDimensions(pose).scale(0.75f) : super.getDefaultDimensions(pose);
	}

	@Nullable
	@Override
	protected SoundEvent getAmbientSound() {
		return ESSoundEvents.STARFIRE_BIRD_AMBIENT.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return ESSoundEvents.STARFIRE_BIRD_HURT.get();
	}

	@Override
	protected @Nullable SoundEvent getDeathSound() {
		return ESSoundEvents.STARFIRE_BIRD_DEATH.get();
	}

	public static boolean checkStarfireBirdSpawnRules(EntityType<? extends StarfireBird> type, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
		return level.getBlockState(pos.below()).is(BlockTags.DIRT) && ESConfig.INSTANCE.mobsConfig.starfireBird.canSpawn();
	}
}
