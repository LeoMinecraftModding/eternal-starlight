package cn.leolezury.eternalstarlight.common.entity.animal;

import cn.leolezury.eternalstarlight.common.init.EntityInit;
import cn.leolezury.eternalstarlight.common.init.ItemInit;
import cn.leolezury.eternalstarlight.common.init.SoundEventInit;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
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
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
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
    private static final Ingredient FOOD_ITEMS = Ingredient.of(ItemInit.LUNAR_BERRIES.get());
    public Ent(EntityType<? extends Animal> type, Level level) {
        super(type, level);
    }

    protected static final EntityDataAccessor<Boolean> HAS_LEAVES = SynchedEntityData.defineId(Ent.class, EntityDataSerializers.BOOLEAN);
    public boolean hasLeaves() {
        return entityData.get(HAS_LEAVES);
    }
    public void setHasLeaves(boolean hasLeaves) {
        entityData.set(HAS_LEAVES, hasLeaves);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(HAS_LEAVES, true);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(3, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, FOOD_ITEMS, false));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        setHasLeaves(compoundTag.getBoolean("HasLeaves"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("HasLeaves", hasLeaves());
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
                spawnAtLocation(ItemInit.LUNAR_LEAVES.get());
                stack.hurtAndBreak(1, player, (p) -> {
                    p.broadcastBreakEvent(hand);
                });
                playSound(SoundEvents.SHEEP_SHEAR);
                player.swing(hand);
                if (this.level().isClientSide) {
                    return InteractionResult.CONSUME;
                }
            }
            if (stack.is(Items.BONE_MEAL) && !hasLeaves()) {
                setHasLeaves(true);
                usePlayerItem(player, hand, stack);
                playSound(SoundEvents.BONE_MEAL_USE);
                player.swing(hand);
                if (this.level().isClientSide) {
                    return InteractionResult.CONSUME;
                }
            }
        }
        return super.mobInteract(player, hand);
    }

    public boolean isFood(ItemStack stack_) {
        return FOOD_ITEMS.test(stack_);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob mob) {
        return EntityInit.ENT.get().create(level);
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEventInit.ENT_HURT.get();
    }

    public static boolean checkEntSpawnRules(EntityType<? extends Ent> type, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).is(BlockTags.DIRT);
    }
}
