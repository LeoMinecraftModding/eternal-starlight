package cn.leolezury.eternalstarlight.common.entity.animal;

import cn.leolezury.eternalstarlight.common.entity.ai.goal.ChargeAttackGoal;
import cn.leolezury.eternalstarlight.common.entity.interfaces.Charger;
import cn.leolezury.eternalstarlight.common.init.EntityInit;
import cn.leolezury.eternalstarlight.common.init.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
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
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class AuroraDeer extends Animal implements Charger {
    private static final Ingredient FOOD_ITEMS = Ingredient.of(ItemInit.LUNAR_BERRIES.get());
    protected static final EntityDataAccessor<Boolean> LEFT_HORN = SynchedEntityData.defineId(AuroraDeer.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Boolean> RIGHT_HORN = SynchedEntityData.defineId(AuroraDeer.class, EntityDataSerializers.BOOLEAN);

    // todo: fix navigation problem (keeps turning around)
    public AuroraDeer(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    public AnimationState idleAnimationState = new AnimationState();
    private boolean charging = false;

    @Override
    public void setCharging(boolean charging) {
        this.charging = charging;
        if (!charging) {
            setTarget(null);
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(LEFT_HORN, true);
        entityData.define(RIGHT_HORN, true);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new ChargeAttackGoal(this, false, 1.5f, 3, 30, 0.5f) {
            @Override
            public boolean canUse() {
                return super.canUse() && AuroraDeer.this.getHealth() / AuroraDeer.this.getMaxHealth() >= 0.5f;
            }

            @Override
            public void tick() {
                super.tick();
                if (charging) {
                    Vec3 vec3 = AuroraDeer.this.getDeltaMovement().multiply(1.0, 0.0, 1.0).normalize();
                    BlockPos blockPos = BlockPos.containing(AuroraDeer.this.position().add(vec3));
                    if (AuroraDeer.this.level().getBlockState(blockPos).is(BlockTags.SNAPS_GOAT_HORN) || AuroraDeer.this.level().getBlockState(blockPos.above()).is(BlockTags.SNAPS_GOAT_HORN)) {
                        AuroraDeer.this.randomlyBreakHorn();
                    }
                }
            }
        });
        this.goalSelector.addGoal(2, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(3, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, FOOD_ITEMS, false));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
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
        if (level().isClientSide) {
            idleAnimationState.startIfStopped(tickCount);
        }
    }

    public void randomlyBreakHorn() {
        EntityDataAccessor<Boolean> accessor = getRandom().nextBoolean() ? LEFT_HORN : RIGHT_HORN;
        if (!entityData.get(LEFT_HORN)) {
            accessor = RIGHT_HORN;
        }
        if (!entityData.get(RIGHT_HORN)) {
            accessor = LEFT_HORN;
        }
        entityData.set(accessor, false);
        if (level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.EXPLOSION, this.getX(), this.getY() + getBbHeight() / 2f, this.getZ(), 2, 0.2, 0.2, 0.2, 0.0);
        }
        // todo: horn break effect
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        entityData.set(LEFT_HORN, compoundTag.getBoolean("LeftHorn"));
        entityData.set(RIGHT_HORN, compoundTag.getBoolean("RightHorn"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("LeftHorn", entityData.get(LEFT_HORN));
        compoundTag.putBoolean("RightHorn", entityData.get(RIGHT_HORN));
    }

    public static boolean checkAuroraDeerSpawnRules(EntityType<? extends AuroraDeer> type, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).is(BlockTags.DIRT);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return EntityInit.AURORA_DEER.get().create(serverLevel);
    }
}
