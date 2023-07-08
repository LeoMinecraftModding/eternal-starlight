package cn.leolezury.eternalstarlight.entity.animal;

import cn.leolezury.eternalstarlight.init.EntityInit;
import cn.leolezury.eternalstarlight.init.ItemInit;
import cn.leolezury.eternalstarlight.init.SoundEventInit;
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
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.Nullable;

public class Dryad extends Animal {
    private static final Ingredient FOOD_ITEMS = Ingredient.of(ItemInit.LUNAR_BERRIES.get());
    public Dryad(EntityType<? extends Animal> p_27557_, Level p_27558_) {
        super(p_27557_, p_27558_);
    }

    protected static final EntityDataAccessor<Boolean> HAS_LEAVE = SynchedEntityData.defineId(Dryad.class, EntityDataSerializers.BOOLEAN);
    public boolean hasLeaves() {
        return entityData.get(HAS_LEAVE);
    }
    public void setHasLeaves(boolean hasLeaves) {
        entityData.set(HAS_LEAVE, hasLeaves);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(HAS_LEAVE, true);
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
        setHasLeaves(compoundTag.getBoolean("HasLeaves"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        compoundTag.putBoolean("HasLeaves", hasLeaves());
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0D).add(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    public static boolean checkDryadSpawnRules(EntityType<? extends Dryad> p_218105_, LevelAccessor p_218106_, MobSpawnType p_218107_, BlockPos p_218108_, RandomSource p_218109_) {
        return p_218106_.getBlockState(p_218108_.below()).is(BlockTags.DIRT) || p_218106_.getBlockState(p_218108_.below()).is(Blocks.SNOW_BLOCK);
    }

    @Override
    public InteractionResult mobInteract(Player p_27584_, InteractionHand p_27585_) {
        ItemStack stack = p_27584_.getItemInHand(p_27585_);
        boolean flag = this.isFood(stack);
        if (!flag) {
            if (stack.is(Tags.Items.SHEARS) && hasLeaves()) {
                setHasLeaves(false);
                spawnAtLocation(ItemInit.LUNAR_LEAVES.get());
                stack.hurtAndBreak(1, p_27584_, (p_32290_) -> {
                    p_32290_.broadcastBreakEvent(p_27585_);
                });
                playSound(SoundEvents.SHEEP_SHEAR);
                p_27584_.swing(p_27585_);
                if (this.level().isClientSide) {
                    return InteractionResult.CONSUME;
                }
            }
            if (stack.is(Items.BONE_MEAL) && !hasLeaves()) {
                setHasLeaves(true);
                usePlayerItem(p_27584_, p_27585_, stack);
                playSound(SoundEvents.BONE_MEAL_USE);
                p_27584_.swing(p_27585_);
                if (this.level().isClientSide) {
                    return InteractionResult.CONSUME;
                }
            }
        }
        return super.mobInteract(p_27584_, p_27585_);
    }

    public boolean isFood(ItemStack p_29508_) {
        return FOOD_ITEMS.test(p_29508_);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return EntityInit.DRYAD.get().create(p_146743_);
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource p_21239_) {
        return SoundEventInit.DRYAD_HURT.get();
    }
}
