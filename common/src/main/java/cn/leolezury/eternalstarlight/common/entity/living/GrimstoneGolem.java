package cn.leolezury.eternalstarlight.common.entity.living;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;

public class GrimstoneGolem extends PathfinderMob {
    public final AnimationState raiseArmsAnimationState = new AnimationState();
    public final AnimationState displayAnimationState = new AnimationState();
    public final AnimationState lowerArmsAnimationState = new AnimationState();

    public GrimstoneGolem(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0D).add(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1D));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
    }

    public void stopAllAnimStates() {
        raiseArmsAnimationState.stop();
        displayAnimationState.stop();
        lowerArmsAnimationState.stop();
    }

    @Override
    public void handleEntityEvent(byte b) {
        switch (b) {
            case 100: {
                stopAllAnimStates();
                raiseArmsAnimationState.start(tickCount);
                break;
            }
            case 101: {
                stopAllAnimStates();
                lowerArmsAnimationState.start(tickCount);
                break;
            }
            default: super.handleEntityEvent(b);
        }
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
        if (getItemInHand(InteractionHand.MAIN_HAND).isEmpty() && !player.getItemInHand(interactionHand).isEmpty()) {
            setItemInHand(InteractionHand.MAIN_HAND, player.getItemInHand(interactionHand).copy());
            player.setItemInHand(interactionHand, ItemStack.EMPTY);
            if (!player.level().isClientSide) {
                player.level().broadcastEntityEvent(this, (byte) 100);
            }
            return InteractionResult.sidedSuccess(player.level().isClientSide);
        } else if (!getItemInHand(InteractionHand.MAIN_HAND).isEmpty() && player.getItemInHand(interactionHand).isEmpty()) {
            player.setItemInHand(interactionHand, getItemInHand(InteractionHand.MAIN_HAND).copy());
            setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
            if (!player.level().isClientSide) {
                player.level().broadcastEntityEvent(this, (byte) 101);
            }
            return InteractionResult.sidedSuccess(player.level().isClientSide);
        }
        return super.mobInteract(player, interactionHand);
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide) {
            if (raiseArmsAnimationState.isStarted() && (raiseArmsAnimationState.getAccumulatedTime() / 1000f) * 20f > 20) {
                raiseArmsAnimationState.stop();
                displayAnimationState.start(tickCount);
            }
            if (!getMainHandItem().isEmpty() && !raiseArmsAnimationState.isStarted()) {
                displayAnimationState.startIfStopped(tickCount);
            }
        }
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource damageSource, int i, boolean bl) {
        spawnAtLocation(getMainHandItem());
    }

    public static boolean checkGolemSpawnRules(EntityType<? extends GrimstoneGolem> type, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return !level.canSeeSky(pos) && pos.getY() < level.getHeight(Heightmap.Types.WORLD_SURFACE, pos.getX(), pos.getZ()) - 20;
    }
}
