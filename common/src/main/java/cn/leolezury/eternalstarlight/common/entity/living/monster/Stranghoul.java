package cn.leolezury.eternalstarlight.common.entity.living.monster;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.PungencyFruitVinesBlock;
import cn.leolezury.eternalstarlight.common.block.entity.DryingRackBlockEntity;
import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.data.ESLootTables;
import cn.leolezury.eternalstarlight.common.entity.projectile.ThrownSpear;
import cn.leolezury.eternalstarlight.common.item.combat.SeedsLauncherItem;
import cn.leolezury.eternalstarlight.common.item.combat.SpearItem;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.*;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.FastColor;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public class Stranghoul extends Monster implements NeutralMob, OwnableEntity, RangedAttackMob {
	private static final String TAG_HOME_POS = "home_pos";
	private static final String TAG_BABY = "baby";
	private static final String TAG_GROWTH_TICKS = "growth_ticks";
	private static final String TAG_BREED_COOLDOWN = "breed_cooldown";
	private static final String TAG_ADMIRATION_TICKS = "admiration_ticks";
	private static final String TAG_HIRER = "hirer";
	private static final String TAG_HIRED_TICKS_LEFT = "hired_ticks_left";
	private static final ResourceLocation SPEED_MODIFIER_BABY_ID = ResourceLocation.withDefaultNamespace("baby");
	private static final AttributeModifier SPEED_MODIFIER_BABY = new AttributeModifier(SPEED_MODIFIER_BABY_ID, 0.2, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
	private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
	private int remainingPersistentAngerTime;
	@Nullable
	private UUID persistentAngerTarget;
	public GlobalPos homePos = GlobalPos.of(Level.OVERWORLD, BlockPos.ZERO);
	private int growthTicks;
	private int breedCooldown = 24000;
	private int admirationTicks;
	private boolean makingLove = false;

	@Nullable
	private LivingEntity hirer;
	@Nullable
	private UUID hirerId;
	private int hiredTicksLeft;
	private boolean hiredEatAnim = false;

	public @Nullable LivingEntity getHirer() {
		if (level().isClientSide) {
			int id = getHirerId();
			return level().getEntity(id) instanceof LivingEntity living ? living : null;
		}
		return hirer;
	}

	public void setHirer(@Nullable LivingEntity hirer) {
		if (hirer != null) {
			this.hirerId = hirer.getUUID();
			if (!level().isClientSide) {
				setHirerId(hirer.getId());
			}
		} else if (!level().isClientSide) {
			setHirerId(-1);
		}
		this.hirer = hirer;
	}

	public boolean isHired() {
		return getHirer() != null;
	}

	protected static final EntityDataAccessor<Boolean> EATING = SynchedEntityData.defineId(Stranghoul.class, EntityDataSerializers.BOOLEAN);

	public boolean isEating() {
		return this.getEntityData().get(EATING);
	}

	public void setEating(boolean eating) {
		this.getEntityData().set(EATING, eating);
	}

	protected static final EntityDataAccessor<Boolean> BARTERING = SynchedEntityData.defineId(Stranghoul.class, EntityDataSerializers.BOOLEAN);

	public boolean isBartering() {
		return this.getEntityData().get(BARTERING);
	}

	public void setBartering(boolean bartering) {
		this.getEntityData().set(BARTERING, bartering);
	}

	protected static final EntityDataAccessor<Integer> HIRER_ID = SynchedEntityData.defineId(Stranghoul.class, EntityDataSerializers.INT);

	public int getHirerId() {
		return this.getEntityData().get(HIRER_ID);
	}

	public void setHirerId(int hirerId) {
		this.getEntityData().set(HIRER_ID, hirerId);
	}

	protected static final EntityDataAccessor<Boolean> BABY = SynchedEntityData.defineId(Stranghoul.class, EntityDataSerializers.BOOLEAN);

	@Override
	public boolean isBaby() {
		return this.getEntityData().get(BABY);
	}

	@Override
	public void setBaby(boolean baby) {
		this.getEntityData().set(BABY, baby);
		if (!this.level().isClientSide) {
			AttributeInstance instance = this.getAttribute(Attributes.MOVEMENT_SPEED);
			if (instance != null) {
				instance.removeModifier(SPEED_MODIFIER_BABY.id());
				if (baby) {
					instance.addTransientModifier(SPEED_MODIFIER_BABY);
				}
			}
		}
	}

	public void setEquipmentsOnGrownUp() {
		if (level() instanceof ServerLevelAccessor serverLevel) {
			DifficultyInstance difficulty = level().getCurrentDifficultyAt(blockPosition());
			populateDefaultEquipmentSlots(getRandom(), difficulty);
			populateDefaultEquipmentEnchantments(serverLevel, getRandom(), difficulty);
		}
	}

	public Stranghoul(EntityType<? extends Stranghoul> entityType, Level level) {
		super(entityType, level);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(EATING, false)
			.define(BARTERING, false)
			.define(HIRER_ID, -1)
			.define(BABY, false);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new EatGoal());
		this.goalSelector.addGoal(2, new SeedsLauncherAttackGoal());
		this.goalSelector.addGoal(3, new SpearAttackGoal());
		this.goalSelector.addGoal(4, new RangedBowAttackGoal<>(this, 1.0, 20, 15.0F));
		this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0, false));
		this.goalSelector.addGoal(6, new FollowHirerGoal());
		this.goalSelector.addGoal(7, new BreedGoal());
		this.goalSelector.addGoal(8, new GoToWantedItemGoal());
		this.goalSelector.addGoal(9, new GoHomeGoal());
		this.goalSelector.addGoal(10, new PlantCropGoal());
		this.goalSelector.addGoal(12, new CropGoal());
		this.goalSelector.addGoal(12, new DryRottenFleshGoal());
		this.goalSelector.addGoal(13, new WaterAvoidingRandomStrollGoal(this, 1.0, 0.0F) {
			@Override
			public boolean canUse() {
				return super.canUse() && !Stranghoul.this.isBartering();
			}
		});
		this.goalSelector.addGoal(14, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(15, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(0, new HirerHurtByTargetGoal());
		this.targetSelector.addGoal(1, new HirerHurtTargetGoal());
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this).setAlertOthers());
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, LivingEntity.class, true, living -> living.getType().is(ESTags.EntityTypes.STRANGHOUL_PREYS) && !living.getType().is(ESTags.EntityTypes.STRANGHOUL_CANNOT_HUNT)));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Player.class, true, living -> !Stranghoul.this.isHired() && living.getHealth() / living.getMaxHealth() < 0.15));
		this.targetSelector.addGoal(5, new ResetUniversalAngerTargetGoal<>(this, false));
	}

	@Override
	public void performRangedAttack(LivingEntity target, float distanceFactor) {
		if (getMainHandItem().getItem() instanceof SeedsLauncherItem launcherItem) {
			if (getBoundingBox().inflate(6).intersects(target.getBoundingBox())) {
				ItemStack projectile = this.getProjectile(getMainHandItem());
				launcherItem.performShooting(level(), this, projectile, InteractionHand.MAIN_HAND);
			}
		} else if (getMainHandItem().getItem() instanceof SpearItem spearItem) {
			ThrownSpear spear = spearItem.createSpear(level(), this, getX(), getEyeY() - 0.1, getZ(), getMainHandItem());
			double x = target.getX() - this.getX();
			double y = target.getY(0.3333333333333333) - spear.getY();
			double z = target.getZ() - this.getZ();
			double dist = Math.sqrt(x * x + z * z);
			spear.shoot(x, y + dist * 0.2F, z, 1.6F, (14 - this.level().getDifficulty().getId() * 4));
			this.level().addFreshEntity(spear);
		} else {
			ItemStack bow = this.getItemInHand(ProjectileUtil.getWeaponHoldingHand(this, Items.BOW));
			// I HATE YOU
			if (ESPlatform.INSTANCE.getLoader() == ESPlatform.Loader.NEOFORGE) {
				bow = this.getItemInHand(getMainHandItem().getItem() instanceof BowItem ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND);
			}
			ItemStack projectile = this.getProjectile(bow);
			AbstractArrow arrow = ProjectileUtil.getMobArrow(this, projectile, distanceFactor, bow);
			double x = target.getX() - this.getX();
			double y = target.getY(0.3333333333333333) - arrow.getY();
			double z = target.getZ() - this.getZ();
			double dist = Math.sqrt(x * x + z * z);
			arrow.shoot(x, y + dist * 0.2F, z, 1.6F, (14 - this.level().getDifficulty().getId() * 4));
			this.playSound(ESSoundEvents.STRANGHOUL_SHOOT.get(), 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
			this.level().addFreshEntity(arrow);
		}
	}

	@Override
	public ItemStack getProjectile(ItemStack stack) {
		if (stack.getItem() instanceof ProjectileWeaponItem weaponItem) {
			Predicate<ItemStack> predicate = weaponItem.getSupportedHeldProjectiles();
			ItemStack heldProjectile = ProjectileWeaponItem.getHeldProjectile(this, predicate);
			return heldProjectile.isEmpty() ? (weaponItem instanceof SeedsLauncherItem ? ESItems.PUNGENCY_FRUIT_SEEDS.get().getDefaultInstance() : ESItems.MALARITE_ARROW.get().getDefaultInstance()) : heldProjectile;
		} else {
			return ItemStack.EMPTY;
		}
	}

	private class SeedsLauncherAttackGoal extends RangedAttackGoal {
		public SeedsLauncherAttackGoal() {
			super(Stranghoul.this, 1.0, 30, 5.0F);
		}

		@Override
		public boolean canUse() {
			return super.canUse() && Stranghoul.this.getMainHandItem().getItem() instanceof SeedsLauncherItem;
		}

		@Override
		public void start() {
			super.start();
			Stranghoul.this.setAggressive(true);
		}

		@Override
		public void stop() {
			super.stop();
			Stranghoul.this.setAggressive(false);
		}
	}

	private class SpearAttackGoal extends RangedAttackGoal {
		public SpearAttackGoal() {
			super(Stranghoul.this, 1.0, 40, 10.0F);
		}

		@Override
		public boolean canUse() {
			return super.canUse() && Stranghoul.this.getMainHandItem().getItem() instanceof SpearItem;
		}

		@Override
		public void start() {
			super.start();
			Stranghoul.this.setAggressive(true);
			Stranghoul.this.startUsingItem(InteractionHand.MAIN_HAND);
		}

		@Override
		public void stop() {
			super.stop();
			Stranghoul.this.stopUsingItem();
			Stranghoul.this.setAggressive(false);
		}
	}

	private class EatGoal extends Goal {
		private int eatTicks = 0;

		@Override
		public void start() {
			eatTicks = 0;
			if (!Stranghoul.this.getOffhandItem().has(DataComponents.FOOD)) {
				BuiltInRegistries.ITEM.getRandomElementOf(ESTags.Items.STRANGHOUL_FOOD, random).ifPresent(item -> {
					Stranghoul.this.spawnAtLocation(Stranghoul.this.getOffhandItem());
					Stranghoul.this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(item));
				});
			}
			if (Stranghoul.this.getOffhandItem().has(DataComponents.FOOD)) {
				Stranghoul.this.setEating(true);
			}
		}

		@Override
		public void tick() {
			eatTicks++;
			ItemStack food = Stranghoul.this.getOffhandItem();
			if (eatTicks == 32) {
				FoodProperties foodProperties = food.get(DataComponents.FOOD);
				if (foodProperties != null) {
					Stranghoul.this.heal(foodProperties.nutrition() + foodProperties.saturation() * 3);
				}
				Stranghoul.this.eat(Stranghoul.this.level(), food);
			}
			// copied from LivingEntity#triggerItemUseEffects
			if (eatTicks % 4 == 0 && Stranghoul.this.level() instanceof ServerLevel serverLevel) {
				if (!food.isEmpty()) {
					for (int i = 0; i < (eatTicks == 32 ? 16 : 5); ++i) {
						Vec3 vec3 = new Vec3((Stranghoul.this.random.nextFloat() - 0.5F) * 0.1, Math.random() * 0.1 + 0.1, 0.0F);
						vec3 = vec3.xRot((float) (-Stranghoul.this.getXRot() * (Math.PI / 180F)));
						vec3 = vec3.yRot((float) (-Stranghoul.this.getYHeadRot() * (Math.PI / 180F)));
						double d0 = (-Stranghoul.this.random.nextFloat()) * 0.6 - 0.3;
						Vec3 vec31 = new Vec3((Stranghoul.this.random.nextFloat() - 0.5F) * 0.3, d0, 0.6);
						vec31 = vec31.xRot((float) (-Stranghoul.this.getXRot() * (Math.PI / 180F)));
						vec31 = vec31.yRot((float) (-Stranghoul.this.getYHeadRot() * (Math.PI / 180F)));
						vec31 = vec31.add(Stranghoul.this.getX(), Stranghoul.this.getEyeY(), Stranghoul.this.getZ());
						ESPlatform.INSTANCE.sendToAllClients(serverLevel, new ParticlePacket(new ItemParticleOption(ParticleTypes.ITEM, food), vec31.x, vec31.y, vec31.z, vec3.x, vec3.y + 0.05, vec3.z));
					}
				}
				Stranghoul.this.playSound(Stranghoul.this.getEatingSound(food), 0.5F + 0.5F * Stranghoul.this.random.nextInt(2), (Stranghoul.this.random.nextFloat() - Stranghoul.this.random.nextFloat()) * 0.2F + 1.0F);
			}
		}

		@Override
		public void stop() {
			Stranghoul.this.setEating(false);
			if (Stranghoul.this.hiredEatAnim) {
				Stranghoul.this.hiredEatAnim = false;
				Stranghoul.this.addAlliedParticles();
			}
		}

		@Override
		public boolean requiresUpdateEveryTick() {
			return true;
		}

		@Override
		public boolean canUse() {
			if (Stranghoul.this.hiredEatAnim) {
				return true;
			}
			return Stranghoul.this.random.nextInt(reducedTickDelay(80)) == 0 && (Stranghoul.this.getHealth() / Stranghoul.this.getMaxHealth() < 0.4f || (Stranghoul.this.getOffhandItem().has(DataComponents.FOOD) && Stranghoul.this.getHealth() < Stranghoul.this.getMaxHealth()));
		}

		@Override
		public boolean canContinueToUse() {
			return Stranghoul.this.getOffhandItem().has(DataComponents.FOOD) && eatTicks <= 32;
		}
	}

	private class FollowHirerGoal extends Goal {
		@Nullable
		private LivingEntity hirer;
		private final PathNavigation navigation;
		private int timeToRecalcPath;
		private float oldWaterCost;

		public FollowHirerGoal() {
			this.navigation = Stranghoul.this.getNavigation();
			this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
		}

		@Override
		public boolean canUse() {
			LivingEntity livingEntity = Stranghoul.this.getHirer();
			if (livingEntity == null) {
				return false;
			} else if (Stranghoul.this.unableToMoveToHirer()) {
				return false;
			} else if (Stranghoul.this.distanceToSqr(livingEntity) < 100) {
				return false;
			} else {
				this.hirer = livingEntity;
				return true;
			}
		}

		@Override
		public boolean canContinueToUse() {
			if (this.navigation.isDone()) {
				return false;
			} else if (this.hirer == null) {
				return false;
			} else if (Stranghoul.this.unableToMoveToHirer()) {
				return false;
			} else {
				return !(Stranghoul.this.distanceToSqr(this.hirer) <= 4);
			}
		}

		@Override
		public void start() {
			this.timeToRecalcPath = 0;
			this.oldWaterCost = Stranghoul.this.getPathfindingMalus(PathType.WATER);
			Stranghoul.this.setPathfindingMalus(PathType.WATER, 0.0F);
		}

		@Override
		public void stop() {
			this.hirer = null;
			this.navigation.stop();
			Stranghoul.this.setPathfindingMalus(PathType.WATER, this.oldWaterCost);
		}

		@Override
		public void tick() {
			boolean shouldTeleport = this.hirer != null && Stranghoul.this.distanceToSqr(this.hirer) >= 144.0;
			if (!shouldTeleport && this.hirer != null) {
				Stranghoul.this.getLookControl().setLookAt(this.hirer, 10.0F, Stranghoul.this.getMaxHeadXRot());
			}
			if (--this.timeToRecalcPath <= 0) {
				this.timeToRecalcPath = this.adjustedTickDelay(10);
				if (shouldTeleport) {
					Stranghoul.this.tryToTeleportToHirer();
				} else if (this.hirer != null) {
					this.navigation.moveTo(this.hirer, 1);
				}
			}
		}
	}

	private class BreedGoal extends Goal {
		private static final TargetingConditions PARTNER_TARGETING = TargetingConditions.forNonCombat().range(8.0F).ignoreLineOfSight();
		protected final Stranghoul stranghoul;
		protected final Level level;
		@Nullable
		protected Stranghoul partner;
		private int loveTime;

		public BreedGoal() {
			this.stranghoul = Stranghoul.this;
			this.level = Stranghoul.this.level();
			this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
		}

		@Override
		public boolean canUse() {
			if (this.stranghoul.getRandom().nextInt(reducedTickDelay(80)) != 0 || !this.stranghoul.canBreed()) {
				return false;
			} else {
				this.partner = this.getFreePartner();
				return this.partner != null;
			}
		}

		@Override
		public boolean canContinueToUse() {
			return this.partner != null && this.partner.isAlive() && this.loveTime < 60;
		}

		@Override
		public void start() {
			this.stranghoul.makingLove = true;
			if (this.partner != null) {
				this.partner.makingLove = true;
			}
		}

		@Override
		public void stop() {
			this.stranghoul.makingLove = false;
			if (this.partner != null) {
				this.partner.makingLove = false;
			}
			this.partner = null;
			this.loveTime = 0;
		}

		@Override
		public void tick() {
			if (this.partner != null) {
				this.stranghoul.getLookControl().setLookAt(this.partner, 10.0F, this.stranghoul.getMaxHeadXRot());
				this.stranghoul.getNavigation().moveTo(this.partner, 1);
				++this.loveTime;
				if (this.loveTime >= this.adjustedTickDelay(60) && this.stranghoul.distanceToSqr(this.partner) < 9) {
					Stranghoul baby = new Stranghoul(ESEntities.STRANGHOUL.get(), level);
					baby.setBaby(true);
					baby.moveTo(stranghoul.getX(), stranghoul.getY(), stranghoul.getZ(), 0.0F, 0.0F);
					if (level instanceof ServerLevel serverLevel) {
						baby.finalizeSpawn(serverLevel, level.getCurrentDifficultyAt(baby.blockPosition()), MobSpawnType.BREEDING, null);
					}
					level.addFreshEntity(baby);
					baby.addHappyParticles();
					stranghoul.breedCooldown = 24000;
					partner.breedCooldown = 24000;
					loveTime = 60;
				}
			}
		}

		@Nullable
		private Stranghoul getFreePartner() {
			List<Stranghoul> list = this.level.getNearbyEntities(Stranghoul.class, PARTNER_TARGETING, this.stranghoul, this.stranghoul.getBoundingBox().inflate(8.0F)).stream().filter(Stranghoul::canBreed).sorted(Comparator.comparingDouble(s -> s.distanceTo(stranghoul))).toList();
			return list.isEmpty() ? null : list.getFirst();
		}

		@Override
		public boolean requiresUpdateEveryTick() {
			return true;
		}
	}

	private class GoToWantedItemGoal extends Goal {
		private ItemEntity wantedItem;

		public GoToWantedItemGoal() {
			this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
		}

		@Override
		public boolean canUse() {
			if (Stranghoul.this.isBaby()) {
				return false;
			}
			if (Stranghoul.this.getRandom().nextInt(reducedTickDelay(80)) == 0 && !Stranghoul.this.getOffhandItem().is(ESTags.Items.STRANGHOUL_CURRENCIES)) {
				List<ItemEntity> items = Stranghoul.this.level().getEntitiesOfClass(ItemEntity.class, Stranghoul.this.getBoundingBox().inflate(15)).stream().filter(i -> i.getItem().is(ESTags.Items.STRANGHOUL_CURRENCIES) && Stranghoul.this.hasLineOfSight(i)).sorted(Comparator.comparingDouble(i -> i.distanceTo(Stranghoul.this))).toList();
				if (!items.isEmpty()) {
					wantedItem = items.getFirst();
					return true;
				}
			}
			return false;
		}

		@Override
		public boolean canContinueToUse() {
			return wantedItem != null && wantedItem.isAlive();
		}

		@Override
		public void stop() {
			wantedItem = null;
		}

		@Override
		public void tick() {
			if (wantedItem != null) {
				Stranghoul.this.getLookControl().setLookAt(this.wantedItem, 10.0F, Stranghoul.this.getMaxHeadXRot());
				Stranghoul.this.getNavigation().moveTo(this.wantedItem, 1);
			}
		}

		@Override
		public boolean requiresUpdateEveryTick() {
			return true;
		}
	}

	private class GoHomeGoal extends Goal {
		private int tryTicks = 0;

		public GoHomeGoal() {
			this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
		}

		@Override
		public boolean canUse() {
			return Stranghoul.this.getRandom().nextInt(reducedTickDelay(120)) == 0 && Stranghoul.this.homePos.dimension() == Stranghoul.this.level().dimension() && !Stranghoul.this.blockPosition().closerThan(Stranghoul.this.homePos.pos(), 20);
		}

		@Override
		public boolean canContinueToUse() {
			return Stranghoul.this.homePos.dimension() == Stranghoul.this.level().dimension() && !Stranghoul.this.blockPosition().closerThan(Stranghoul.this.homePos.pos(), 10) && tryTicks < 600;
		}

		@Override
		public void stop() {
			tryTicks = 0;
		}

		@Override
		public void tick() {
			tryTicks++;
			Stranghoul.this.getNavigation().moveTo(Stranghoul.this.homePos.pos().getX() + 0.5, Stranghoul.this.homePos.pos().getY() + 0.5, Stranghoul.this.homePos.pos().getZ() + 0.5, 1);
		}

		@Override
		public boolean requiresUpdateEveryTick() {
			return true;
		}
	}

	private class PlantCropGoal extends MoveToBlockGoal {
		public PlantCropGoal() {
			super(Stranghoul.this, 1.0, 20, 15);
		}

		@Override
		public boolean canUse() {
			if (Stranghoul.this.isHired() || !ESPlatform.INSTANCE.canEntityGrief(Stranghoul.this.level(), Stranghoul.this)) {
				return false;
			}
			return Stranghoul.this.getMainHandItem().is(ItemTags.HOES) && !Stranghoul.this.isBartering() && super.canUse();
		}

		@Override
		public void tick() {
			super.tick();
			if (this.isReachedTarget()) {
				Level level = Stranghoul.this.level();
				BlockPos cropPos = this.blockPos.above();
				BlockState blockState = level.getBlockState(cropPos);
				if (blockState.isAir()) {
					level.setBlockAndUpdate(cropPos, ESBlocks.PUNGENCY_FRUIT_VINES.get().defaultBlockState());
					level.gameEvent(GameEvent.BLOCK_CHANGE, cropPos, GameEvent.Context.of(Stranghoul.this));
					Stranghoul.this.swing(InteractionHand.MAIN_HAND);
					Stranghoul.this.addHappyParticles();
				}
			}
		}

		@Override
		public double acceptedDistance() {
			return 2.5;
		}

		@Override
		protected boolean isValidTarget(LevelReader level, BlockPos pos) {
			BlockState state = level.getBlockState(pos);
			return ESBlocks.PUNGENCY_FRUIT_VINES.get().canPlaceSeeds(state, level, pos) && level.getBlockState(pos.above()).isAir();
		}
	}

	private class CropGoal extends MoveToBlockGoal {
		public CropGoal() {
			super(Stranghoul.this, 1.0, 20, 15);
		}

		@Override
		public boolean canUse() {
			if (Stranghoul.this.isHired() || !ESPlatform.INSTANCE.canEntityGrief(Stranghoul.this.level(), Stranghoul.this)) {
				return false;
			}
			return Stranghoul.this.getMainHandItem().is(ItemTags.HOES) && !Stranghoul.this.isBartering() && super.canUse();
		}

		@Override
		public void tick() {
			super.tick();
			if (this.isReachedTarget()) {
				Level level = Stranghoul.this.level();
				BlockState blockState = level.getBlockState(blockPos);
				if (blockState.is(ESBlocks.PUNGENCY_FRUIT_VINES.get()) && blockState.getValue(PungencyFruitVinesBlock.AGE) == PungencyFruitVinesBlock.MAX_AGE && ESPlatform.INSTANCE.postEntityDestroyBlockEvent(level, blockPos, Stranghoul.this)) {
					level.destroyBlock(blockPos, true, Stranghoul.this);
					Stranghoul.this.swing(InteractionHand.MAIN_HAND);
					Stranghoul.this.addHappyParticles();
				}
			}
		}

		@Override
		public double acceptedDistance() {
			return 2.5;
		}

		@Override
		protected boolean isValidTarget(LevelReader level, BlockPos pos) {
			BlockState state = level.getBlockState(pos);
			return state.is(ESBlocks.PUNGENCY_FRUIT_VINES.get()) && state.getValue(PungencyFruitVinesBlock.AGE) == PungencyFruitVinesBlock.MAX_AGE;
		}
	}

	private class DryRottenFleshGoal extends MoveToBlockGoal {
		public DryRottenFleshGoal() {
			super(Stranghoul.this, 1.0, 20, 15);
		}

		@Override
		public boolean canUse() {
			if (Stranghoul.this.isHired() || !ESPlatform.INSTANCE.canEntityGrief(Stranghoul.this.level(), Stranghoul.this)) {
				return false;
			}
			return Stranghoul.this.getOffhandItem().is(Items.ROTTEN_FLESH) && !Stranghoul.this.isBartering() && super.canUse();
		}

		@Override
		public boolean canContinueToUse() {
			return Stranghoul.this.getOffhandItem().is(Items.ROTTEN_FLESH) && super.canContinueToUse();
		}

		@Override
		public void tick() {
			super.tick();
			if (this.isReachedTarget()) {
				Level level = Stranghoul.this.level();
				if (isValidTarget(level, blockPos)) {
					if (level.getBlockEntity(blockPos) instanceof DryingRackBlockEntity entity) {
						entity.setItem(Stranghoul.this.getOffhandItem().copyWithCount(1));
						Stranghoul.this.getOffhandItem().consume(1, Stranghoul.this);
					}
					Stranghoul.this.swing(InteractionHand.OFF_HAND);
					Stranghoul.this.addHappyParticles();
				}
			}
		}

		@Override
		public double acceptedDistance() {
			return 2.5;
		}

		@Override
		protected boolean isValidTarget(LevelReader level, BlockPos pos) {
			return level.getBlockState(pos).is(ESBlocks.DRYING_RACK.get()) && level.getBlockEntity(pos) instanceof DryingRackBlockEntity entity && (entity.getItem().isEmpty() || entity.getItem().is(ESItems.ROTTEN_FLESH_JERKY.get()) || entity.getItem().is(Items.LEATHER));
		}
	}

	private class HirerHurtByTargetGoal extends TargetGoal {
		private LivingEntity hirerLastHurtBy;
		private int timestamp;

		public HirerHurtByTargetGoal() {
			super(Stranghoul.this, false);
			this.setFlags(EnumSet.of(Flag.TARGET));
		}

		@Override
		public boolean canUse() {
			if (Stranghoul.this.isHired()) {
				LivingEntity living = Stranghoul.this.getHirer();
				if (living == null) {
					return false;
				} else {
					this.hirerLastHurtBy = living.getLastHurtByMob();
					int i = living.getLastHurtByMobTimestamp();
					return i != this.timestamp && this.canAttack(this.hirerLastHurtBy, TargetingConditions.DEFAULT);
				}
			} else {
				return false;
			}
		}

		@Override
		public void start() {
			this.mob.setTarget(this.hirerLastHurtBy);
			LivingEntity living = Stranghoul.this.getHirer();
			if (living != null) {
				this.timestamp = living.getLastHurtByMobTimestamp();
			}
			super.start();
		}
	}

	private class HirerHurtTargetGoal extends TargetGoal {
		private LivingEntity hirerLastHurt;
		private int timestamp;

		public HirerHurtTargetGoal() {
			super(Stranghoul.this, false);
			this.setFlags(EnumSet.of(Flag.TARGET));
		}

		@Override
		public boolean canUse() {
			if (Stranghoul.this.isHired()) {
				LivingEntity livingEntity = Stranghoul.this.getHirer();
				if (livingEntity == null) {
					return false;
				} else {
					this.hirerLastHurt = livingEntity.getLastHurtMob();
					int i = livingEntity.getLastHurtMobTimestamp();
					return i != this.timestamp && this.canAttack(this.hirerLastHurt, TargetingConditions.DEFAULT);
				}
			} else {
				return false;
			}
		}

		@Override
		public void start() {
			this.mob.setTarget(this.hirerLastHurt);
			LivingEntity livingEntity = Stranghoul.this.getHirer();
			if (livingEntity != null) {
				this.timestamp = livingEntity.getLastHurtMobTimestamp();
			}
			super.start();
		}
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes()
			.add(Attributes.MAX_HEALTH, ESConfig.INSTANCE.mobsConfig.stranghoul.maxHealth())
			.add(Attributes.ARMOR, ESConfig.INSTANCE.mobsConfig.stranghoul.armor())
			.add(Attributes.ATTACK_DAMAGE, ESConfig.INSTANCE.mobsConfig.stranghoul.attackDamage())
			.add(Attributes.FOLLOW_RANGE, ESConfig.INSTANCE.mobsConfig.stranghoul.followRange())
			.add(Attributes.MOVEMENT_SPEED, 0.3);
	}

	@Override
	protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
		if (random.nextFloat() < 0.3F && getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
			this.setItemSlot(EquipmentSlot.HEAD, dyedLeatherArmor(Items.LEATHER_HELMET, random));
		}
		if (random.nextFloat() < 0.3F && getItemBySlot(EquipmentSlot.CHEST).isEmpty()) {
			this.setItemSlot(EquipmentSlot.CHEST, dyedLeatherArmor(Items.LEATHER_CHESTPLATE, random));
		}
		if (random.nextFloat() < 0.3F && getItemBySlot(EquipmentSlot.LEGS).isEmpty()) {
			this.setItemSlot(EquipmentSlot.LEGS, dyedLeatherArmor(Items.LEATHER_LEGGINGS, random));
		}
		if (random.nextFloat() < 0.3F && getItemBySlot(EquipmentSlot.FEET).isEmpty()) {
			this.setItemSlot(EquipmentSlot.FEET, dyedLeatherArmor(Items.LEATHER_BOOTS, random));
		}
		if (getMainHandItem().isEmpty()) {
			BuiltInRegistries.ITEM.getRandomElementOf(ESTags.Items.STRANGHOUL_CAN_USE, random).ifPresent(item -> this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(item)));
		}
	}

	private ItemStack dyedLeatherArmor(Item item, RandomSource random) {
		ItemStack stack = item.getDefaultInstance();
		stack.set(DataComponents.DYED_COLOR, new DyedItemColor(FastColor.ARGB32.lerp(random.nextFloat(), FastColor.ARGB32.color(117, 135, 137), FastColor.ARGB32.color(48, 55, 56)), true));
		return stack;
	}

	@Nullable
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
		homePos = GlobalPos.of(level.getLevel().dimension(), blockPosition());
		if (random.nextFloat() < 0.1F) {
			this.setBaby(true);
		}
		if (!isBaby()) {
			this.populateDefaultEquipmentSlots(getRandom(), difficulty);
			this.populateDefaultEquipmentEnchantments(level, getRandom(), difficulty);
		}
		this.breedCooldown = random.nextInt(24000, 36000);
		return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (!this.level().isClientSide) {
			if (getTarget() != null && !getTarget().isAlive()) {
				setTarget(null);
			}
			if (hirer == null && hirerId != null && level() instanceof ServerLevel serverLevel) {
				if (serverLevel.getEntity(hirerId) instanceof LivingEntity livingEntity) {
					hirer = livingEntity;
				}
				if (hirer == null) {
					hirerId = null;
				}
			}
			if (this.isAlive() && !this.dead && !this.hiredEatAnim && ESPlatform.INSTANCE.canEntityGrief(level(), this)) {
				for (ItemEntity itemEntity : this.level().getEntitiesOfClass(ItemEntity.class, this.getBoundingBox().inflate(2, 1, 2))) {
					if (!itemEntity.isRemoved() && !itemEntity.getItem().isEmpty() && !itemEntity.hasPickUpDelay() && itemEntity.getOwner() != this
						&& ((itemEntity.getItem().is(ESTags.Items.STRANGHOUL_FOOD) && !getOffhandItem().has(DataComponents.FOOD) && !getOffhandItem().is(ESTags.Items.STRANGHOUL_CURRENCIES))
						|| (getTarget() == null && !isBaby() && !isEating() && itemEntity.getItem().is(ESTags.Items.STRANGHOUL_CURRENCIES) && !getOffhandItem().is(ESTags.Items.STRANGHOUL_CURRENCIES)))) {
						spawnAtLocation(getOffhandItem());
						setItemSlot(EquipmentSlot.OFFHAND, itemEntity.getItem());
						itemEntity.discard();
					}
				}
			}
			if (isBaby()) {
				growthTicks++;
				if (growthTicks > 24000) {
					growthTicks = 0;
					setBaby(false);
					addHappyParticles();
					setEquipmentsOnGrownUp();
				}
			} else {
				breedCooldown--;
				breedCooldown = Math.max(breedCooldown, 0);
			}
			hiredTicksLeft--;
			hiredTicksLeft = Math.max(hiredTicksLeft, 0);
			if (hiredTicksLeft == 0) {
				setHirer(null);
			}
			if (getOffhandItem().is(ESTags.Items.STRANGHOUL_CURRENCIES) && getTarget() == null && level() instanceof ServerLevel serverLevel) {
				admirationTicks++;
				if (admirationTicks >= 60) {
					LootTable lootTable = serverLevel.getServer().reloadableRegistries().getLootTable(ESLootTables.GAMEPLAY_STRANGHOUL_BARTERING);
					List<ItemStack> items = lootTable.getRandomItems(
						new LootParams.Builder(serverLevel)
							.withParameter(LootContextParams.THIS_ENTITY, this)
							.create(LootContextParamSets.PIGLIN_BARTER)
					);
					Vec3 pos = LandRandomPos.getPos(this, 4, 2);
					List<Player> players = level().getNearbyPlayers(TargetingConditions.forNonCombat(), this, getBoundingBox().inflate(4));
					players.sort(Comparator.comparingDouble(p -> p.distanceTo(this)));
					if (!players.isEmpty()) {
						pos = players.getFirst().position();
					}
					if (pos == null) {
						pos = position();
					}
					for (ItemStack item : items) {
						BehaviorUtils.throwItem(this, item, pos.add(0.0, 1.0, 0.0));
					}
					admirationTicks = 0;
					getOffhandItem().consume(1, this);
					swing(InteractionHand.OFF_HAND);
				}
				setBartering(true);
			} else {
				admirationTicks = 0;
				setBartering(false);
			}
		}
	}

	@Override
	public boolean isAlliedTo(Entity entity) {
		LivingEntity hirer = getHirer();
		return super.isAlliedTo(entity) || entity instanceof Stranghoul || (hirer != null
			&& (hirer.isAlliedTo(entity)
			|| hirer == entity
			|| (entity instanceof OwnableEntity ownable
			&& ownable.getOwnerUUID() != null
			&& ownable.getOwnerUUID().equals(getOwnerUUID()))));
	}

	@Override
	protected InteractionResult mobInteract(Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (ESDataAttachments.STRANGHOUL_HIRING_COOLDOWN.getData(player) <= 0 && stack.is(ESTags.Items.STRANGHOUL_HIRING_FOOD) && !isHired() && !isBaby() && getTarget() == null) {
			setHirer(player);
			ESDataAttachments.STRANGHOUL_HIRING_COOLDOWN.setData(player, ESConfig.INSTANCE.mobsConfig.stranghoul.hiringCooldown());
			hiredTicksLeft = 24000;
			hiredEatAnim = true;
			spawnAtLocation(getOffhandItem());
			setItemSlot(EquipmentSlot.OFFHAND, stack.copyWithCount(1));
			setPersistenceRequired();
			stack.consume(1, player);
			if (player instanceof ServerPlayer serverPlayer) {
				ESCriteriaTriggers.HIRE_STRANGHOUL.get().trigger(serverPlayer);
			}
			return InteractionResult.sidedSuccess(level().isClientSide);
		}
		if (stack.is(ESTags.Items.STRANGHOUL_CURRENCIES) && !isBaby() && getOffhandItem().isEmpty() && getTarget() == null) {
			setItemSlot(EquipmentSlot.OFFHAND, stack.copyWithCount(1));
			stack.consume(1, player);
			return InteractionResult.sidedSuccess(level().isClientSide);
		}
		if (isHired() && player == getHirer()) {
			boolean infiniteMaterials = player.hasInfiniteMaterials();
			if (stack.is(ItemTags.HEAD_ARMOR)) {
				if (!level().isClientSide) {
					if (!infiniteMaterials || !getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
						player.setItemInHand(hand, getItemBySlot(EquipmentSlot.HEAD).copy());
					}
					setItemSlot(EquipmentSlot.HEAD, stack.copy());
					setGuaranteedDrop(EquipmentSlot.HEAD);
				}
				return InteractionResult.sidedSuccess(level().isClientSide);
			} else if (stack.is(ItemTags.CHEST_ARMOR)) {
				if (!level().isClientSide) {
					if (!infiniteMaterials || !getItemBySlot(EquipmentSlot.CHEST).isEmpty()) {
						player.setItemInHand(hand, getItemBySlot(EquipmentSlot.CHEST).copy());
					}
					setItemSlot(EquipmentSlot.CHEST, stack.copy());
					setGuaranteedDrop(EquipmentSlot.CHEST);
				}
				return InteractionResult.sidedSuccess(level().isClientSide);
			} else if (stack.is(ItemTags.LEG_ARMOR)) {
				if (!level().isClientSide) {
					if (!infiniteMaterials || !getItemBySlot(EquipmentSlot.LEGS).isEmpty()) {
						player.setItemInHand(hand, getItemBySlot(EquipmentSlot.LEGS).copy());
					}
					setItemSlot(EquipmentSlot.LEGS, stack.copy());
					setGuaranteedDrop(EquipmentSlot.LEGS);
				}
				return InteractionResult.sidedSuccess(level().isClientSide);
			} else if (stack.is(ItemTags.FOOT_ARMOR)) {
				if (!level().isClientSide) {
					if (!infiniteMaterials || !getItemBySlot(EquipmentSlot.FEET).isEmpty()) {
						player.setItemInHand(hand, getItemBySlot(EquipmentSlot.FEET).copy());
					}
					setItemSlot(EquipmentSlot.FEET, stack.copy());
					setGuaranteedDrop(EquipmentSlot.FEET);
				}
				return InteractionResult.sidedSuccess(level().isClientSide);
			} else if (stack.has(DataComponents.FOOD)) {
				if (!level().isClientSide) {
					if (!infiniteMaterials || !getOffhandItem().isEmpty()) {
						player.setItemInHand(hand, getOffhandItem().copy());
					}
					setItemSlot(EquipmentSlot.OFFHAND, stack.copy());
					setGuaranteedDrop(EquipmentSlot.OFFHAND);
				}
				return InteractionResult.sidedSuccess(level().isClientSide);
			} else if (!stack.isEmpty()) {
				if (!level().isClientSide) {
					if (!infiniteMaterials || !getMainHandItem().isEmpty()) {
						player.setItemInHand(hand, getMainHandItem().copy());
					}
					setItemSlot(EquipmentSlot.MAINHAND, stack.copy());
					setGuaranteedDrop(EquipmentSlot.MAINHAND);
				}
				return InteractionResult.sidedSuccess(level().isClientSide);
			} else {
				for (EquipmentSlot slot : EquipmentSlot.values()) {
					if (!getItemBySlot(slot).isEmpty()) {
						if (!level().isClientSide) {
							player.setItemInHand(hand, getItemBySlot(slot).copy());
							setItemSlot(slot, ItemStack.EMPTY);
						}
						return InteractionResult.sidedSuccess(level().isClientSide);
					}
				}
			}
		}
		return super.mobInteract(player, hand);
	}

	public boolean canBreed() {
		return !makingLove && breedCooldown <= 0 && !isHired() && !isBaby() && level().getRawBrightness(blockPosition(), 0) < 12 && level().getEntitiesOfClass(Stranghoul.class, getBoundingBox().inflate(10)).stream().filter(Stranghoul::isBaby).count() < 5;
	}

	// copied from TamableAnimal
	public final boolean unableToMoveToHirer() {
		return this.isPassenger() || this.mayBeLeashed() || this.getOwner() != null && this.getOwner().isSpectator();
	}

	public void tryToTeleportToHirer() {
		LivingEntity livingEntity = this.getHirer();
		if (livingEntity != null) {
			this.teleportToAroundBlockPos(livingEntity.blockPosition());
		}
	}

	private void teleportToAroundBlockPos(BlockPos blockPos) {
		for (int i = 0; i < 10; ++i) {
			int j = this.random.nextIntBetweenInclusive(-3, 3);
			int k = this.random.nextIntBetweenInclusive(-3, 3);
			if (Math.abs(j) >= 2 || Math.abs(k) >= 2) {
				int l = this.random.nextIntBetweenInclusive(-1, 1);
				if (this.maybeTeleportTo(blockPos.getX() + j, blockPos.getY() + l, blockPos.getZ() + k)) {
					return;
				}
			}
		}
	}

	private boolean maybeTeleportTo(int i, int j, int k) {
		if (!this.canTeleportTo(new BlockPos(i, j, k))) {
			return false;
		} else {
			this.moveTo(i + 0.5F, j, k + 0.5F, this.getYRot(), this.getXRot());
			this.navigation.stop();
			return true;
		}
	}

	private boolean canTeleportTo(BlockPos blockPos) {
		PathType pathType = WalkNodeEvaluator.getPathTypeStatic(this, blockPos);
		if (pathType != PathType.WALKABLE) {
			return false;
		} else {
			BlockState blockState = this.level().getBlockState(blockPos.below());
			if (blockState.getBlock() instanceof LeavesBlock) {
				return false;
			} else {
				BlockPos blockPos2 = blockPos.subtract(this.blockPosition());
				return this.level().noCollision(this, this.getBoundingBox().move(blockPos2));
			}
		}
	}

	public void addHappyParticles() {
		if (level() instanceof ServerLevel serverLevel) {
			for (int i = 0; i < 5; ++i) {
				double d = getRandom().nextGaussian() * 0.02;
				double e = getRandom().nextGaussian() * 0.02;
				double f = getRandom().nextGaussian() * 0.02;
				ESPlatform.INSTANCE.sendToAllClients(serverLevel, new ParticlePacket(ParticleTypes.HAPPY_VILLAGER, getRandomX(1.0F), getRandomY() + 1.0F, getRandomZ(1.0F), d, e, f));
			}
		}
	}

	public void addAlliedParticles() {
		if (level() instanceof ServerLevel serverLevel) {
			for (int i = 0; i < 5; ++i) {
				double d = getRandom().nextGaussian() * 0.02;
				double e = getRandom().nextGaussian() * 0.02;
				double f = getRandom().nextGaussian() * 0.02;
				ESPlatform.INSTANCE.sendToAllClients(serverLevel, new ParticlePacket(ESParticles.ALLIED.get(), getRandomX(1.0F), getRandomY() + 1.0F, getRandomZ(1.0F), d, e, f));
			}
		}
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		return super.hurt(source, (source.getDirectEntity() instanceof LivingEntity living && living.getWeaponItem().is(ESTags.Items.STRANGHOUL_VULNERABLE_TO) ? 2 : 1) * amount);
	}

	@Override
	public boolean canBeAffected(MobEffectInstance instance) {
		return !instance.is(MobEffects.HUNGER) && !instance.is(ESMobEffects.TEARY.asHolder()) && super.canBeAffected(instance);
	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
		super.onSyncedDataUpdated(accessor);
		if (BABY.equals(accessor)) {
			this.refreshDimensions();
		}
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		this.addPersistentAngerSaveData(compoundTag);
		compoundTag.put(TAG_HOME_POS, GlobalPos.CODEC.encodeStart(NbtOps.INSTANCE, homePos).getOrThrow());
		compoundTag.putBoolean(TAG_BABY, isBaby());
		compoundTag.putInt(TAG_GROWTH_TICKS, growthTicks);
		compoundTag.putInt(TAG_BREED_COOLDOWN, breedCooldown);
		compoundTag.putInt(TAG_ADMIRATION_TICKS, admirationTicks);
		if (hirer != null) {
			compoundTag.putUUID(TAG_HIRER, hirer.getUUID());
		}
		compoundTag.putInt(TAG_HIRED_TICKS_LEFT, hiredTicksLeft);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.readPersistentAngerSaveData(this.level(), compoundTag);
		if (compoundTag.contains(TAG_HOME_POS)) {
			GlobalPos.CODEC.parse(NbtOps.INSTANCE, compoundTag.get(TAG_HOME_POS)).resultOrPartial(s -> EternalStarlight.LOGGER.warn("Failed to parse Stranghoul home pos: {}", s)).ifPresent(pos -> this.homePos = pos);
		}
		if (compoundTag.contains(TAG_BABY, CompoundTag.TAG_BYTE)) {
			setBaby(compoundTag.getBoolean(TAG_BABY));
		}
		growthTicks = compoundTag.getInt(TAG_GROWTH_TICKS);
		breedCooldown = compoundTag.getInt(TAG_BREED_COOLDOWN);
		admirationTicks = compoundTag.getInt(TAG_ADMIRATION_TICKS);
		if (compoundTag.hasUUID(TAG_HIRER)) {
			hirerId = compoundTag.getUUID(TAG_HIRER);
		}
		hiredTicksLeft = compoundTag.getInt(TAG_HIRED_TICKS_LEFT);
	}

	@Override
	protected @Nullable SoundEvent getAmbientSound() {
		return ESSoundEvents.STRANGHOUL_AMBIENT.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return ESSoundEvents.STRANGHOUL_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ESSoundEvents.STRANGHOUL_DEATH.get();
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

	@Override
	public @Nullable UUID getOwnerUUID() {
		return hirerId;
	}

	public static boolean checkStranghoulSpawnRules(EntityType<? extends Stranghoul> type, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
		return checkAnyLightMonsterSpawnRules(type, level, spawnType, pos, random) && ESConfig.INSTANCE.mobsConfig.stranghoul.canSpawn();
	}
}
