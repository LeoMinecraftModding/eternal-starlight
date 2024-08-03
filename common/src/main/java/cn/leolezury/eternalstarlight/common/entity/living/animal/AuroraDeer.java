package cn.leolezury.eternalstarlight.common.entity.living.animal;

import cn.leolezury.eternalstarlight.common.entity.interfaces.Charger;
import cn.leolezury.eternalstarlight.common.entity.living.goal.ChargeAttackGoal;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class AuroraDeer extends Animal implements Charger {
	private static final String TAG_LEFT_HORN = "left_horn";
	private static final String TAG_RIGHT_HORN = "right_horn";

	private static final Ingredient FOOD_ITEMS = Ingredient.of(ESTags.Items.AURORA_DEER_FOOD);
	protected static final EntityDataAccessor<Boolean> LEFT_HORN = SynchedEntityData.defineId(AuroraDeer.class, EntityDataSerializers.BOOLEAN);

	public boolean hasLeftHorn() {
		return this.getEntityData().get(LEFT_HORN);
	}

	protected static final EntityDataAccessor<Boolean> RIGHT_HORN = SynchedEntityData.defineId(AuroraDeer.class, EntityDataSerializers.BOOLEAN);

	public boolean hasRightHorn() {
		return this.getEntityData().get(RIGHT_HORN);
	}

	public AuroraDeer(EntityType<? extends Animal> entityType, Level level) {
		super(entityType, level);
	}

	public AnimationState idleAnimationState = new AnimationState();
	private boolean charging = false;
	private int notChargingTicks = 200;

	@Override
	public void setCharging(boolean charging) {
		this.charging = charging;
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(LEFT_HORN, true)
			.define(RIGHT_HORN, true);
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData) {
		this.getEntityData().set(LEFT_HORN, true);
		this.getEntityData().set(RIGHT_HORN, true);
		return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData);
	}

	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new ChargeAttackGoal(this, false, 2f, 80, 80, 0.6f) {
			private boolean hornBroken = false;

			@Override
			public boolean canUse() {
				return super.canUse() && AuroraDeer.this.getHealth() / AuroraDeer.this.getMaxHealth() >= 0.5f && (hasLeftHorn() || hasRightHorn());
			}

			@Override
			public void start() {
				super.start();
				hornBroken = false;
			}

			@Override
			public void tick() {
				super.tick();
				if (AuroraDeer.this.charging && !hornBroken) {
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
									AuroraDeer.this.randomlyBreakHorn();
									hornBroken = true;
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
				return super.canUse() && (AuroraDeer.this.getHealth() / AuroraDeer.this.getMaxHealth() < 0.5f || (!hasLeftHorn() && !hasRightHorn()));
			}
		});
		this.goalSelector.addGoal(3, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, FOOD_ITEMS, false));
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
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0D).add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.ATTACK_DAMAGE, 3D);
	}

	public boolean isFood(ItemStack stack) {
		return FOOD_ITEMS.test(stack);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (charging) {
			notChargingTicks = 0;
		} else {
			notChargingTicks++;
		}
		if (level().isClientSide) {
			idleAnimationState.startIfStopped(tickCount);
		}
	}

	public void randomlyBreakHorn() {
		if (getRandom().nextInt(8) == 0) {
			EntityDataAccessor<Boolean> accessor = getRandom().nextBoolean() ? LEFT_HORN : RIGHT_HORN;
			if (!hasLeftHorn()) {
				accessor = RIGHT_HORN;
			}
			if (!hasRightHorn()) {
				accessor = LEFT_HORN;
			}
			if (!hasLeftHorn() && !hasRightHorn()) {
				return;
			}
			this.getEntityData().set(accessor, false);
			if (level() instanceof ServerLevel serverLevel) {
				serverLevel.sendParticles(ParticleTypes.EXPLOSION, this.getX(), this.getY() + getBbHeight() / 2f, this.getZ(), 2, 0.2, 0.2, 0.2, 0.0);
			}
			spawnAtLocation(ESItems.AURORA_DEER_ANTLER.get());
		}
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.getEntityData().set(LEFT_HORN, compoundTag.getBoolean(TAG_LEFT_HORN));
		this.getEntityData().set(RIGHT_HORN, compoundTag.getBoolean(TAG_RIGHT_HORN));
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putBoolean(TAG_LEFT_HORN, hasLeftHorn());
		compoundTag.putBoolean(TAG_RIGHT_HORN, hasRightHorn());
	}

	public static boolean checkAuroraDeerSpawnRules(EntityType<? extends AuroraDeer> type, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
		return level.getBlockState(pos.below()).is(BlockTags.DIRT);
	}

	@Nullable
	@Override
	public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
		return ESEntities.AURORA_DEER.get().create(serverLevel);
	}
}
