package cn.leolezury.eternalstarlight.common.entity.living.animal;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.entity.interfaces.Charger;
import cn.leolezury.eternalstarlight.common.entity.living.goal.ChargeAttackGoal;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class AuroraDeer extends Animal implements Charger {
	private static final String TAG_LEFT_ANTLER = "left_antler";
	private static final String TAG_RIGHT_ANTLER = "right_antler";
	private static final String TAG_SNOW = "snow";
	private static final String TAG_SNOW_PROGRESS = "snow_progress";

	protected static final EntityDataAccessor<Boolean> LEFT_ANTLER = SynchedEntityData.defineId(AuroraDeer.class, EntityDataSerializers.BOOLEAN);

	public boolean hasLeftAntler() {
		return this.getEntityData().get(LEFT_ANTLER);
	}

	protected static final EntityDataAccessor<Boolean> RIGHT_ANTLER = SynchedEntityData.defineId(AuroraDeer.class, EntityDataSerializers.BOOLEAN);

	public boolean hasRightAntler() {
		return this.getEntityData().get(RIGHT_ANTLER);
	}

	protected static final EntityDataAccessor<Boolean> SNOW = SynchedEntityData.defineId(AuroraDeer.class, EntityDataSerializers.BOOLEAN);

	public boolean hasSnow() {
		return this.getEntityData().get(SNOW);
	}

	public void setHasSnow(boolean hasSnow) {
		this.getEntityData().set(SNOW, hasSnow);
	}

	public AuroraDeer(EntityType<? extends Animal> entityType, Level level) {
		super(entityType, level);
	}

	public AnimationState idleAnimationState = new AnimationState();
	private boolean charging = false;
	private int notChargingTicks = 200;
	private int snowProgress = 2000;

	@Override
	public void setCharging(boolean charging) {
		this.charging = charging;
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(LEFT_ANTLER, true)
			.define(RIGHT_ANTLER, true)
			.define(SNOW, true);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new ChargeAttackGoal(this, false, 2f, 80, 80, 0.6f) {
			private boolean antlerBroken = false;

			@Override
			public boolean canUse() {
				return super.canUse() && AuroraDeer.this.getHealth() / AuroraDeer.this.getMaxHealth() >= 0.5f && (hasLeftAntler() || hasRightAntler());
			}

			@Override
			public void start() {
				super.start();
				antlerBroken = false;
			}

			@Override
			public void tick() {
				super.tick();
				if (AuroraDeer.this.charging && !antlerBroken) {
					Vec3 vec3 = AuroraDeer.this.getDeltaMovement().multiply(1.0, 0.0, 1.0).normalize();
					AABB box = AuroraDeer.this.getBoundingBox().move(vec3);
					BlockPos fromPos = BlockPos.containing(box.minX + 1.0E-7, box.minY + 1.0E-7, box.minZ + 1.0E-7);
					BlockPos toPos = BlockPos.containing(box.maxX - 1.0E-7, box.maxY - 1.0E-7, box.maxZ - 1.0E-7);
					BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
					for (int i = fromPos.getX(); i <= toPos.getX(); ++i) {
						for (int j = fromPos.getY(); j <= toPos.getY(); ++j) {
							for (int k = fromPos.getZ(); k <= toPos.getZ(); ++k) {
								mutableBlockPos.set(i, j, k);
								BlockState blockState = AuroraDeer.this.level().getBlockState(mutableBlockPos);
								if (blockState.is(BlockTags.SNAPS_GOAT_HORN)) {
									AuroraDeer.this.randomlyBreakAntler();
									antlerBroken = true;
									stop();
									return;
								}
							}
						}
					}
				}
			}
		});
		this.goalSelector.addGoal(2, new PanicGoal(this, 1.25D) {
			@Override
			public boolean canUse() {
				return super.canUse() && (AuroraDeer.this.getHealth() / AuroraDeer.this.getMaxHealth() < 0.5f || (!hasLeftAntler() && !hasRightAntler()));
			}
		});
		this.goalSelector.addGoal(3, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, this::isFood, false));
		this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
		this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D) {
			@Override
			public boolean canUse() {
				return super.canUse() && AuroraDeer.this.notChargingTicks >= 200;
			}
		});
		this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));

		this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes()
			.add(Attributes.MAX_HEALTH, ESConfig.INSTANCE.mobsConfig.auroraDeer.maxHealth())
			.add(Attributes.ARMOR, ESConfig.INSTANCE.mobsConfig.auroraDeer.armor())
			.add(Attributes.ATTACK_DAMAGE, ESConfig.INSTANCE.mobsConfig.auroraDeer.attackDamage())
			.add(Attributes.FOLLOW_RANGE, ESConfig.INSTANCE.mobsConfig.auroraDeer.followRange())
			.add(Attributes.MOVEMENT_SPEED, 0.25D);
	}

	@Override
	public boolean isFood(ItemStack stack) {
		return stack.is(ESTags.Items.AURORA_DEER_FOOD);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (charging) {
			notChargingTicks = 0;
		} else {
			notChargingTicks++;
		}
		if (!level().isClientSide) {
			boolean snow = hasSnow();
			if (level().getBiome(blockPosition()).value().getBaseTemperature() < 0.15) {
				snowProgress = Math.min(snowProgress + 1, 2000);
			} else {
				snowProgress = Math.max(snowProgress - 1, 0);
			}
			if (snow && snowProgress < 1000) {
				setHasSnow(false);
			}
			if (!snow && snowProgress >= 1000) {
				setHasSnow(true);
			}
		} else {
			idleAnimationState.startIfStopped(tickCount);
		}
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (hasSnow() && stack.is(ItemTags.SHOVELS)) {
			setHasSnow(false);
			snowProgress = 0;
			int i = 1 + getRandom().nextInt(3);
			for (int j = 0; j < i; ++j) {
				ItemEntity itemEntity = this.spawnAtLocation(Items.SNOWBALL, 1);
				if (itemEntity != null) {
					itemEntity.setDeltaMovement(itemEntity.getDeltaMovement().add((getRandom().nextFloat() - getRandom().nextFloat()) * 0.1F, getRandom().nextFloat() * 0.05F, (getRandom().nextFloat() - getRandom().nextFloat()) * 0.1F));
				}
			}
			stack.hurtAndBreak(1, player, player.getEquipmentSlotForItem(stack));
			return InteractionResult.sidedSuccess(player.level().isClientSide);
		}
		return super.mobInteract(player, hand);
	}

	public void randomlyBreakAntler() {
		if (getRandom().nextInt(8) == 0) {
			EntityDataAccessor<Boolean> accessor = getRandom().nextBoolean() ? LEFT_ANTLER : RIGHT_ANTLER;
			if (!hasLeftAntler()) {
				accessor = RIGHT_ANTLER;
			}
			if (!hasRightAntler()) {
				accessor = LEFT_ANTLER;
			}
			if (!hasLeftAntler() && !hasRightAntler()) {
				return;
			}
			this.getEntityData().set(accessor, false);
			if (level() instanceof ServerLevel serverLevel) {
				serverLevel.sendParticles(ParticleTypes.EXPLOSION, this.getX(), this.getY() + getBbHeight() / 2f, this.getZ(), 2, 0.2, 0.2, 0.2, 0.0);
				spawnAtLocation(serverLevel, ESItems.AURORA_DEER_ANTLER.get());
			}
		}
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		if (compoundTag.contains(TAG_LEFT_ANTLER, CompoundTag.TAG_BYTE)) {
			this.getEntityData().set(LEFT_ANTLER, compoundTag.getBoolean(TAG_LEFT_ANTLER));
		}
		if (compoundTag.contains(TAG_RIGHT_ANTLER, CompoundTag.TAG_BYTE)) {
			this.getEntityData().set(RIGHT_ANTLER, compoundTag.getBoolean(TAG_RIGHT_ANTLER));
		}
		if (compoundTag.contains(TAG_SNOW, CompoundTag.TAG_BYTE)) {
			setHasSnow(compoundTag.getBoolean(TAG_SNOW));
		}
		if (compoundTag.contains(TAG_SNOW_PROGRESS, CompoundTag.TAG_INT)) {
			snowProgress = compoundTag.getInt(TAG_SNOW_PROGRESS);
		}
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putBoolean(TAG_LEFT_ANTLER, hasLeftAntler());
		compoundTag.putBoolean(TAG_RIGHT_ANTLER, hasRightAntler());
		compoundTag.putBoolean(TAG_SNOW, hasSnow());
		compoundTag.putInt(TAG_SNOW_PROGRESS, snowProgress);
	}

	@Nullable
	@Override
	protected SoundEvent getAmbientSound() {
		return ESSoundEvents.AURORA_DEER_AMBIENT.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return ESSoundEvents.AURORA_DEER_HURT.get();
	}

	public static boolean checkAuroraDeerSpawnRules(EntityType<? extends AuroraDeer> type, LevelAccessor level, EntitySpawnReason spawnReason, BlockPos pos, RandomSource random) {
		return level.getBlockState(pos.below()).is(BlockTags.DIRT) && ESConfig.INSTANCE.mobsConfig.auroraDeer.canSpawn();
	}

	@Nullable
	@Override
	public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
		return ESEntities.AURORA_DEER.get().create(serverLevel, EntitySpawnReason.BREEDING);
	}
}
