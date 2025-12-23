package cn.leolezury.eternalstarlight.common.entity.living.animal;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.data.ESEntVariants;
import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class Ent extends Animal implements VariantHolder<Holder<EntVariant>> {
	private static final String TAG_HAS_LEAVES = "has_leaves";
	private static final String TAG_VARIANT = "variant";

	private static final Ingredient FOOD_ITEMS = Ingredient.of(ESTags.Items.ENT_FOOD);

	public Ent(EntityType<? extends Ent> type, Level level) {
		super(type, level);
	}

	protected static final EntityDataAccessor<Boolean> HAS_LEAVES = SynchedEntityData.defineId(Ent.class, EntityDataSerializers.BOOLEAN);

	public boolean hasLeaves() {
		return this.getEntityData().get(HAS_LEAVES);
	}

	public void setHasLeaves(boolean hasLeaves) {
		this.getEntityData().set(HAS_LEAVES, hasLeaves);
	}

	protected static final EntityDataAccessor<String> VARIANT = SynchedEntityData.defineId(Ent.class, EntityDataSerializers.STRING);

	public ResourceLocation getVariantId() {
		return ResourceLocation.parse(this.getEntityData().get(VARIANT));
	}

	public void setVariantId(ResourceLocation variant) {
		this.getEntityData().set(VARIANT, variant.toString());
	}

	@Override
	public void setVariant(Holder<EntVariant> variant) {
		if (variant.isBound()) {
			ResourceLocation key = level().registryAccess().registryOrThrow(ESRegistries.ENT_VARIANT).getKey(variant.value());
			if (key != null) {
				setVariantId(key);
			}
		}
	}

	@Override
	public Holder<EntVariant> getVariant() {
		ResourceLocation key = getVariantId();
		Registry<EntVariant> variants = level().registryAccess().registryOrThrow(ESRegistries.ENT_VARIANT);
		Optional<Holder.Reference<EntVariant>> optional = variants.getHolder(key);
		return optional.orElse(variants.getHolder(ESEntVariants.LUNAR).orElseThrow());
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(HAS_LEAVES, true)
			.define(VARIANT, ESEntVariants.LUNAR.location().toString());
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
		this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, FOOD_ITEMS, false));
		this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1D));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {
		super.readAdditionalSaveData(compoundTag);
		if (compoundTag.contains(TAG_HAS_LEAVES, CompoundTag.TAG_BYTE)) {
			setHasLeaves(compoundTag.getBoolean(TAG_HAS_LEAVES));
		}
		setVariantId(ResourceLocation.read(compoundTag.getString(TAG_VARIANT)).getOrThrow());
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putBoolean(TAG_HAS_LEAVES, hasLeaves());
		compoundTag.putString(TAG_VARIANT, getVariantId().toString());
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes()
			.add(Attributes.MAX_HEALTH, ESConfig.INSTANCE.mobsConfig.ent.maxHealth())
			.add(Attributes.ARMOR, ESConfig.INSTANCE.mobsConfig.ent.armor())
			.add(Attributes.MOVEMENT_SPEED, 0.25D);
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance instance, MobSpawnType spawnType, @Nullable SpawnGroupData data) {
		setVariant(EntVariant.getSpawnVariant(level.registryAccess(), level.getBiome(blockPosition())));
		return super.finalizeSpawn(level, instance, spawnType, data);
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		boolean flag = this.isFood(stack);
		if (!flag) {
			if (ESPlatform.INSTANCE.isShears(stack) && hasLeaves()) {
				setHasLeaves(false);
				spawnAtLocation(getVariant().value().leaves().value());
				stack.hurtAndBreak(1, player, player.getEquipmentSlotForItem(stack));
				playSound(SoundEvents.SHEEP_SHEAR);
				return InteractionResult.sidedSuccess(level().isClientSide);
			}
			if (stack.is(ESTags.Items.ENT_FERTILIZERS) && !hasLeaves()) {
				setHasLeaves(true);
				usePlayerItem(player, hand, stack);
				playSound(SoundEvents.BONE_MEAL_USE);
				player.swing(hand);
				return InteractionResult.sidedSuccess(level().isClientSide);
			}
		}
		return super.mobInteract(player, hand);
	}

	@Override
	protected void dropCustomDeathLoot(ServerLevel level, DamageSource source, boolean recentlyHit) {
		super.dropCustomDeathLoot(level, source, recentlyHit);
		spawnAtLocation(getVariant().value().leaves().value());
	}

	@Override
	public boolean isFood(ItemStack stack) {
		return FOOD_ITEMS.test(stack);
	}

	@Nullable
	@Override
	public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob mob) {
		Ent ent = ESEntities.ENT.get().create(level);
		if (ent != null && mob instanceof Ent partner) {
			if (this.random.nextBoolean()) {
				ent.setVariant(this.getVariant());
			} else {
				ent.setVariant(partner.getVariant());
			}
		}
		return ent;
	}

	@Nullable
	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return ESSoundEvents.ENT_HURT.get();
	}

	public static boolean checkEntSpawnRules(EntityType<? extends Ent> type, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
		return level.getBlockState(pos.below()).is(BlockTags.ANIMALS_SPAWNABLE_ON) && ESConfig.INSTANCE.mobsConfig.ent.canSpawn();
	}
}
