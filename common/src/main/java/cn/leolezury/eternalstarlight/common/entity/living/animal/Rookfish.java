package cn.leolezury.eternalstarlight.common.entity.living.animal;

import cn.leolezury.eternalstarlight.common.data.ESDimensions;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.FastColor;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;

public class Rookfish extends Squid implements Bucketable {
	private static final String TAG_FROM_BUCKET = "from_bucket";
	private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(Rookfish.class, EntityDataSerializers.BOOLEAN);

	public Rookfish(EntityType<? extends Rookfish> type, Level level) {
		super(type, level);
	}

	@Override
	protected ParticleOptions getInkParticle() {
		return ColorParticleOption.create(ESParticles.COLORED_INK.get(), FastColor.ARGB32.color(255, 51, 61, 58));
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(FROM_BUCKET, false);
	}

	@Override
	protected InteractionResult mobInteract(Player player, InteractionHand hand) {
		return Bucketable.bucketMobPickup(player, hand, this).orElse(super.mobInteract(player, hand));
	}

	@Override
	public boolean fromBucket() {
		return this.entityData.get(FROM_BUCKET);
	}

	@Override
	public void setFromBucket(boolean fromBucket) {
		this.entityData.set(FROM_BUCKET, fromBucket);
	}

	@Override
	public void saveToBucketTag(ItemStack itemStack) {
		Bucketable.saveDefaultDataToBucketTag(this, itemStack);
	}

	@Override
	public void loadFromBucketTag(CompoundTag compoundTag) {
		Bucketable.loadDefaultDataFromBucketTag(this, compoundTag);
	}

	@Override
	public ItemStack getBucketItemStack() {
		return ESItems.ROOKFISH_BUCKET.get().getDefaultInstance();
	}

	@Override
	public SoundEvent getPickupSound() {
		return SoundEvents.BUCKET_FILL_FISH;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putBoolean(TAG_FROM_BUCKET, this.fromBucket());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		this.setFromBucket(compoundTag.getBoolean(TAG_FROM_BUCKET));
	}

	@Override
	protected void dropCustomDeathLoot(ServerLevel serverLevel, DamageSource damageSource, boolean bl) {
		super.dropCustomDeathLoot(serverLevel, damageSource, bl);
		if (damageSource.is(DamageTypes.DROWN) && serverLevel.getRandom().nextInt(10) == 0) {
			spawnAtLocation(ESItems.MUSIC_DISC_BRISK.get());
		}
	}

	public static boolean checkRookfishSpawnRules(EntityType<? extends Rookfish> type, LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource randomSource) {
		int seaLevel = ESDimensions.SEA_LEVEL;
		int minY = seaLevel - 13;
		return blockPos.getY() >= minY && blockPos.getY() <= seaLevel && levelAccessor.getFluidState(blockPos.below()).is(FluidTags.WATER) && levelAccessor.getBlockState(blockPos.above()).is(Blocks.WATER);
	}
}
