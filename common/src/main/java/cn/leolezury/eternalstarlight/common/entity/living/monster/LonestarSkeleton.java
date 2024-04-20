package cn.leolezury.eternalstarlight.common.entity.living.monster;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.entity.living.goal.LonestarSkeletonShootBladeGoal;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class LonestarSkeleton extends Skeleton {
    private final LonestarSkeletonShootBladeGoal bladeGoal = new LonestarSkeletonShootBladeGoal(this, 1.0, 20, 15.0F);
    private final MeleeAttackGoal meleeGoal = new MeleeAttackGoal(this, 1.2, false) {
        public void stop() {
            super.stop();
            LonestarSkeleton.this.setAggressive(false);
        }

        public void start() {
            super.start();
            LonestarSkeleton.this.setAggressive(true);
        }
    };
    private static final AttributeModifier DAMAGE_NERF = new AttributeModifier(UUID.fromString("012CFA7C-D624-695F-9C9F-7020A8718B6B"), "Lonestar Skeleton Nerf", -4, AttributeModifier.Operation.ADD_VALUE);
    private static final AttributeModifier DAMAGE_ADDITION = new AttributeModifier(UUID.fromString("210CFA7C-D624-695F-9C9F-7020A8718B6B"), "Lonestar Skeleton Addition", 4, AttributeModifier.Operation.ADD_VALUE);

    public LonestarSkeleton(EntityType<? extends LonestarSkeleton> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.25).add(Attributes.FOLLOW_RANGE, 80);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData) {
        SpawnGroupData data = super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData);
        Arrays.fill(this.handDropChances, 0.2F);
        return data;
    }

    @Override
    public void reassessWeaponGoal() {

    }

    public void onSwitchWeapon() {
        AttributeInstance instance = getAttribute(Attributes.ATTACK_DAMAGE);
        if (instance != null) {
            if (getMainHandItem().is(ESItems.ENERGY_SWORD.get())) {
                instance.addPermanentModifier(DAMAGE_NERF);
            } else {
                instance.removeModifier(DAMAGE_NERF.id());
            }

            if (getMainHandItem().is(ESItems.WAND_OF_TELEPORTATION.get())) {
                instance.addPermanentModifier(DAMAGE_ADDITION);
            } else {
                instance.removeModifier(DAMAGE_ADDITION.id());
            }
        }
        this.goalSelector.removeGoal(this.meleeGoal);
        this.goalSelector.removeGoal(this.bladeGoal);
        if (getMainHandItem().is(ESItems.SHATTERED_SWORD.get())) {
            this.goalSelector.addGoal(4, this.bladeGoal);
        } else {
            this.goalSelector.addGoal(4, this.meleeGoal);
        }
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        if (level() instanceof ServerLevel serverLevel) {
            if (getMainHandItem().is(ESItems.ENERGY_SWORD.get())) {
                Vec3 initialStartPos = getEyePosition();
                float lookYaw = getYHeadRot() + 90.0f;
                float lookPitch = -getXRot();
                Vec3 initialEndPos = ESMathUtil.rotationToPosition(initialStartPos, 0.3f, lookPitch, lookYaw);
                hurtMarked = true;
                setDeltaMovement(initialEndPos.subtract(initialStartPos).scale(5));
                playSound(SoundEvents.PLAYER_ATTACK_SWEEP);
                for (int i = 0; i < 15; i++) {
                    Vec3 startPos = initialStartPos.offsetRandom(getRandom(), 0.4f);
                    Vec3 endPos = initialEndPos.offsetRandom(getRandom(), 0.8f);
                    ESPlatform.INSTANCE.sendToAllClients(serverLevel, new ParticlePacket(ESParticles.BLADE_SHOCKWAVE.get(), startPos.x, startPos.y, startPos.z, endPos.x - startPos.x, endPos.y - startPos.y, endPos.z - startPos.z));
                }
            } else if (getMainHandItem().is(ESItems.WAND_OF_TELEPORTATION.get())) {
                for (int i = 0; i < 360; i += 5) {
                    Vec3 vec3 = ESMathUtil.rotationToPosition(entity.position().add(0, entity.getBbHeight() / 2f, 0), 4 * entity.getBbWidth(), 0, i);
                    serverLevel.sendParticles(ESParticles.STARLIGHT.get(), vec3.x, vec3.y, vec3.z, 1, 0, 0, 0, 0);
                }
                teleportTowards(entity);
            }
        }
        return super.doHurtTarget(entity);
    }

    private boolean teleportTowards(Entity entity) {
        Vec3 vec3 = new Vec3(this.getX() - entity.getX(), this.getY(0.5) - entity.getEyeY(), this.getZ() - entity.getZ());
        vec3 = vec3.normalize();
        double e = this.getX() + (this.random.nextDouble() - 0.5) * 8.0 - vec3.x * 16.0;
        double f = this.getY() + (double)(this.random.nextInt(16) - 8) - vec3.y * 16.0;
        double g = this.getZ() + (this.random.nextDouble() - 0.5) * 8.0 - vec3.z * 16.0;
        return this.teleport(e, f, g);
    }

    private boolean teleport(double d, double e, double f) {
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos(d, e, f);

        while (mutableBlockPos.getY() > this.level().getMinBuildHeight() && !this.level().getBlockState(mutableBlockPos).blocksMotion()) {
            mutableBlockPos.move(Direction.DOWN);
        }

        if (level().getBlockState(mutableBlockPos).blocksMotion()) {
            Vec3 vec3 = this.position();
            boolean bl3 = this.randomTeleport(d, e, f, true);
            if (bl3) {
                this.level().gameEvent(GameEvent.TELEPORT, vec3, GameEvent.Context.of(this));
            }
            return bl3;
        } else {
            return false;
        }
    }

    private boolean isAdvancementDone(ServerPlayer player, ResourceLocation advancement) {
        if (player.getServer() != null) {
            AdvancementHolder holder = player.getServer().getAdvancements().get(advancement);
            if (holder != null) {
                return player.getAdvancements().getOrStartProgress(holder).isDone();
            }
        }
        return false;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (getTarget() != null && !level().isClientSide) {
            List<Item> possibleWeapons = new ArrayList<>();
            possibleWeapons.add(ESItems.SHATTERED_SWORD.get());
            if (getTarget() instanceof ServerPlayer serverPlayer) {
                if (isAdvancementDone(serverPlayer, new ResourceLocation(EternalStarlight.MOD_ID, "kill_golem"))) {
                    possibleWeapons.add(ESItems.ENERGY_SWORD.get());
                }
                if (isAdvancementDone(serverPlayer, new ResourceLocation(EternalStarlight.MOD_ID, "kill_lunar_monstrosity"))) {
                    possibleWeapons.add(ESItems.WAND_OF_TELEPORTATION.get());
                }
            }
            boolean correctWeapon = false;
            for (Item item : possibleWeapons) {
                if (getMainHandItem().is(item)) {
                    correctWeapon = true;
                }
            }
            if (!correctWeapon) {
                setItemInHand(InteractionHand.MAIN_HAND, possibleWeapons.get(getRandom().nextInt(possibleWeapons.size())).getDefaultInstance());
            }
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.onSwitchWeapon();
    }

    public void setItemSlot(EquipmentSlot equipmentSlot, ItemStack itemStack) {
        super.setItemSlot(equipmentSlot, itemStack);
        if (!this.level().isClientSide) {
            this.onSwitchWeapon();
        }
    }
}
