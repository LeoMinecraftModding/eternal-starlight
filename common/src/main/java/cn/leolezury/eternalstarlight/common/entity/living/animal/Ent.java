package cn.leolezury.eternalstarlight.common.entity.living.animal;

import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
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
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import org.jetbrains.annotations.Nullable;

public class Ent extends Animal {
	private static final String TAG_HAS_LEAVES = "has_leaves";

	private static final Ingredient FOOD_ITEMS = Ingredient.of(ESTags.Items.ENT_FOOD);

	public Ent(EntityType<? extends Animal> type, Level level) {
		super(type, level);
	}

	protected static final EntityDataAccessor<Boolean> HAS_LEAVES = SynchedEntityData.defineId(Ent.class, EntityDataSerializers.BOOLEAN);

	public boolean hasLeaves() {
		return this.getEntityData().get(HAS_LEAVES);
	}

	public void setHasLeaves(boolean hasLeaves) {
		this.getEntityData().set(HAS_LEAVES, hasLeaves);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(HAS_LEAVES, true);
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
		setHasLeaves(compoundTag.getBoolean(TAG_HAS_LEAVES));
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {
		super.addAdditionalSaveData(compoundTag);
		compoundTag.putBoolean(TAG_HAS_LEAVES, hasLeaves());
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0D).add(Attributes.MOVEMENT_SPEED, 0.25D);
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		boolean flag = this.isFood(stack);
		if (!flag) {
			if (ESPlatform.INSTANCE.isShears(stack) && hasLeaves()) {
				setHasLeaves(false);
				spawnAtLocation(ESItems.LUNAR_LEAVES.get());
				stack.hurtAndBreak(1, player, hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
				playSound(SoundEvents.SHEEP_SHEAR);
				return InteractionResult.sidedSuccess(level().isClientSide);
			}
			if (stack.is(Items.BONE_MEAL) && !hasLeaves()) {
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
	public boolean isFood(ItemStack stack) {
		return FOOD_ITEMS.test(stack);
	}

	@Nullable
	@Override
	public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob mob) {
		return ESEntities.ENT.get().create(level);
	}

	@Nullable
	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return ESSoundEvents.ENT_HURT.get();
	}

	public static boolean checkEntSpawnRules(EntityType<? extends Ent> type, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
		return level.getBlockState(pos.below()).is(BlockTags.DIRT);
	}
}
