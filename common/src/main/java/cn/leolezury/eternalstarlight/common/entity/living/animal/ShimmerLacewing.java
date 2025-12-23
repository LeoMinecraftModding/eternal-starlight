package cn.leolezury.eternalstarlight.common.entity.living.animal;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import cn.leolezury.eternalstarlight.common.data.ESShimmerLacewingVariants;
import cn.leolezury.eternalstarlight.common.entity.living.goal.RandomFlyGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ShimmerLacewing extends Animal implements VariantHolder<Holder<ShimmerLacewingVariant>>, FlyingAnimal {
	private static final String TAG_VARIANT = "variant";

	protected static final EntityDataAccessor<String> VARIANT = SynchedEntityData.defineId(ShimmerLacewing.class, EntityDataSerializers.STRING);

	public ResourceLocation getVariantId() {
		return ResourceLocation.parse(this.getEntityData().get(VARIANT));
	}

	public void setVariantId(ResourceLocation variant) {
		this.getEntityData().set(VARIANT, variant.toString());
	}

	@Override
	public void setVariant(Holder<ShimmerLacewingVariant> variant) {
		if (variant.isBound()) {
			ResourceLocation key = level().registryAccess().registryOrThrow(ESRegistries.SHIMMER_LACEWING_VARIANT).getKey(variant.value());
			if (key != null) {
				setVariantId(key);
			}
		}
	}

	@Override
	public Holder<ShimmerLacewingVariant> getVariant() {
		ResourceLocation key = getVariantId();
		Registry<ShimmerLacewingVariant> variants = level().registryAccess().registryOrThrow(ESRegistries.SHIMMER_LACEWING_VARIANT);
		Optional<Holder.Reference<ShimmerLacewingVariant>> optional = variants.getHolder(key);
		return optional.orElse(variants.getHolder(ESShimmerLacewingVariants.RIVER).orElseThrow());
	}

	public ShimmerLacewing(EntityType<? extends ShimmerLacewing> entityType, Level level) {
		super(entityType, level);
		this.moveControl = new LacewingMoveControl();
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
		builder.define(VARIANT, ESShimmerLacewingVariants.RIVER.location().toString());
	}

	@Override
	protected void registerGoals() {
		goalSelector.addGoal(0, new FloatGoal(this));
		goalSelector.addGoal(1, new RandomFlyGoal(this));
		goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 6.0F));
	}

	private class LacewingMoveControl extends MoveControl {
		public LacewingMoveControl() {
			super(ShimmerLacewing.this);
		}

		@Override
		public void tick() {
			if (this.operation == Operation.MOVE_TO) {
				Vec3 vec3 = new Vec3(this.wantedX - mob.getX(), this.wantedY - mob.getY(), this.wantedZ - mob.getZ());
				double length = vec3.length();
				double size = mob.getBoundingBox().getSize();
				Vec3 delta = vec3.scale(this.speedModifier * 0.025D / length);
				mob.setDeltaMovement(mob.getDeltaMovement().add(delta));
				if (length < size * 0.5F) {
					this.operation = Operation.WAIT;
					mob.setDeltaMovement(mob.getDeltaMovement().scale(0.2));
				} else if (length >= size) {
					mob.setYRot(-((float) Mth.atan2(delta.x, delta.z)) * Mth.RAD_TO_DEG);
				}
			}
			mob.setNoGravity(true);
		}
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes()
			.add(Attributes.MAX_HEALTH, ESConfig.INSTANCE.mobsConfig.shimmerLacewing.maxHealth())
			.add(Attributes.ARMOR, ESConfig.INSTANCE.mobsConfig.shimmerLacewing.armor())
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
	protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {

	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance instance, MobSpawnType spawnType, @Nullable SpawnGroupData data) {
		setVariant(ShimmerLacewingVariant.getSpawnVariant(level.registryAccess(), level.getBiome(blockPosition())));
		return super.finalizeSpawn(level, instance, spawnType, data);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		setVariantId(ResourceLocation.read(compoundTag.getString(TAG_VARIANT)).getOrThrow());
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putString(TAG_VARIANT, getVariantId().toString());
	}

	@Override
	public boolean isFood(ItemStack itemStack) {
		return false;
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

	public static boolean checkLacewingSpawnRules(EntityType<? extends ShimmerLacewing> type, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
		return pos.getY() >= level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, pos).getY() && ESConfig.INSTANCE.mobsConfig.shimmerLacewing.canSpawn();
	}
}
