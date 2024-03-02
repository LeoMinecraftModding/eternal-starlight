package cn.leolezury.eternalstarlight.common.entity.boss.gatekeeper;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.entity.ai.goal.GatekeeperTargetGoal;
import cn.leolezury.eternalstarlight.common.entity.ai.goal.LookAtTargetGoal;
import cn.leolezury.eternalstarlight.common.entity.boss.AttackManager;
import cn.leolezury.eternalstarlight.common.entity.boss.ESBoss;
import cn.leolezury.eternalstarlight.common.entity.boss.ESServerBossEvent;
import cn.leolezury.eternalstarlight.common.handler.CommonHandlers;
import cn.leolezury.eternalstarlight.common.network.ESParticlePacket;
import cn.leolezury.eternalstarlight.common.network.OpenGatekeeperGuiPacket;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESCriteriaTriggers;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.Npc;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.compress.utils.Sets;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class TheGatekeeper extends ESBoss implements Npc, Merchant {
    private static final Music BOSS_MUSIC = new Music(ESSoundEvents.MUSIC_BOSS_GATEKEEPER.asHolder(), 0, 0, true);

    public TheGatekeeper(EntityType<? extends ESBoss> entityType, Level level) {
        super(entityType, level);
    }
    private final ESServerBossEvent bossEvent = new ESServerBossEvent(this, getUUID(), BossEvent.BossBarColor.PURPLE, false);

    private final AttackManager<TheGatekeeper> attackManager = new AttackManager<>(this, List.of(
            new GatekeeperMeleePhase(),
            new GatekeeperDodgePhase(),
            new GatekeeperDashPhase(),
            new GatekeeperCastFireballPhase()
    ));

    public AnimationState meleeAnimationStateA = new AnimationState();
    public AnimationState meleeAnimationStateB = new AnimationState();
    public AnimationState meleeAnimationStateC = new AnimationState();
    public AnimationState dodgeAnimationState = new AnimationState();
    public AnimationState dashAnimationState = new AnimationState();
    public AnimationState castFireballAnimationState = new AnimationState();
    private String gatekeeperName = "TheGatekeeper";
    @Nullable
    private Player customer;
    @Nullable
    protected MerchantOffers offers;
    private int restockCoolDown;
    @Nullable
    private ServerPlayer conversationTarget;
    @Nullable
    private String fightTarget;
    private boolean fightPlayerOnly = true;

    public void setFightPlayerOnly(boolean fightPlayerOnly) {
        this.fightPlayerOnly = fightPlayerOnly;
    }

    public void setFightTargetName(String fightTarget) {
        this.fightTarget = fightTarget;
    }

    public String getFightTargetName() {
        return fightTarget;
    }

    public Optional<? extends Player> getFightTarget() {
        return level().players().stream().filter(p -> p.getName().getString().equals(fightTarget)).findFirst();
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        gatekeeperName = compoundTag.getString("GatekeeperName");
        fightTarget = compoundTag.getString("FightTarget");
        fightPlayerOnly = compoundTag.getBoolean("FightPlayerOnly");
        restockCoolDown = compoundTag.getInt("RestockCoolDown");
        bossEvent.setId(getUUID());
        if (this.offers == null) {
            this.offers = new MerchantOffers();
            this.addTrades();
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putString("GatekeeperName", gatekeeperName);
        compoundTag.putString("FightTarget", fightTarget);
        compoundTag.putBoolean("FightPlayerOnly", fightPlayerOnly);
        compoundTag.putInt("RestockCoolDown", restockCoolDown);
    }

    public void startSeenByPlayer(ServerPlayer serverPlayer) {
        super.startSeenByPlayer(serverPlayer);
        bossEvent.addPlayer(serverPlayer);
    }

    public void stopSeenByPlayer(ServerPlayer serverPlayer) {
        super.stopSeenByPlayer(serverPlayer);
        bossEvent.removePlayer(serverPlayer);
    }

    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(1, new MeleeAttackGoal(this, 0.5D, false) {
            @Override
            protected void checkAndPerformAttack(LivingEntity livingEntity) {

            }
        });
        goalSelector.addGoal(2, new LookAtTargetGoal(this));
        goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        goalSelector.addGoal(4, new LookAtPlayerGoal(this, Mob.class, 8.0F));

        targetSelector.addGoal(0, new GatekeeperTargetGoal(this) {
            @Override
            public boolean canUse() {
                return super.canUse() && fightPlayerOnly && isActivated();
            }
        });
        targetSelector.addGoal(1, new HurtByTargetGoal(this, TheGatekeeper.class) {
            @Override
            public boolean canUse() {
                return super.canUse() && !fightPlayerOnly && isActivated();
            }
        }.setAlertOthers());
        targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true) {
            @Override
            public boolean canUse() {
                return super.canUse() && !fightPlayerOnly && isActivated();
            }
        });
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.5F)
                .add(Attributes.FOLLOW_RANGE, 200.0D)
                .add(Attributes.MAX_HEALTH, 175.0D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D)
                .add(Attributes.ARMOR, 30.0D)
                .add(Attributes.ARMOR_TOUGHNESS, 5.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.8D);
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
        spawnGroupData = super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
        RandomSource randomSource = serverLevelAccessor.getRandom();
        this.populateDefaultEquipmentSlots(randomSource, difficultyInstance);
        return spawnGroupData;
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
        super.populateDefaultEquipmentSlots(randomSource, difficultyInstance);
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.DIAMOND_SWORD));
    }

    public void stopAllAnimStates() {
        meleeAnimationStateA.stop();
        meleeAnimationStateB.stop();
        meleeAnimationStateC.stop();
        dodgeAnimationState.stop();
        dashAnimationState.stop();
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
        if (accessor.equals(ATTACK_STATE) && getAttackState() != 0) {
            stopAllAnimStates();
            switch (getAttackState()) {
                case GatekeeperMeleePhase.ID -> {
                    switch (getRandom().nextInt(3)) {
                        case 0 -> meleeAnimationStateA.start(tickCount);
                        case 1 -> meleeAnimationStateB.start(tickCount);
                        case 2 -> meleeAnimationStateC.start(tickCount);
                    }
                }
                case GatekeeperDodgePhase.ID -> dodgeAnimationState.start(tickCount);
                case GatekeeperDashPhase.ID -> dashAnimationState.start(tickCount);
                case GatekeeperCastFireballPhase.ID -> castFireballAnimationState.start(tickCount);
            }
        }
        super.onSyncedDataUpdated(accessor);
    }

    @Override
    public boolean isActivated() {
        return super.isActivated() || !fightPlayerOnly;
    }

    @Override
    public boolean canAttack(LivingEntity livingEntity) {
        return super.canAttack(livingEntity) && isActivated();
    }

    @Override
    public void initializeBoss() {
        super.initializeBoss();
        setActivated(false);
        gatekeeperName = CommonHandlers.getGatekeeperName();
    }

    @Override
    public boolean shouldPlayBossMusic() {
        return super.shouldPlayBossMusic() && isActivated();
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
        if (!level().isClientSide && player instanceof ServerPlayer serverPlayer && serverPlayer.getServer() != null && conversationTarget == null && getTradingPlayer() == null && getTarget() == null && getFightTarget().isEmpty()) {
            AdvancementHolder killDragon = serverPlayer.getServer().getAdvancements().get(new ResourceLocation("end/kill_dragon"));
            boolean killed = killDragon != null && serverPlayer.getAdvancements().getOrStartProgress(killDragon).isDone();
            AdvancementHolder challenge = serverPlayer.getServer().getAdvancements().get(new ResourceLocation(EternalStarlight.MOD_ID, "challenge_gatekeeper"));
            boolean challenged = challenge != null && serverPlayer.getAdvancements().getOrStartProgress(challenge).isDone();
            boolean hasOrb = false;
            for (int i = 0; i < serverPlayer.getInventory().getContainerSize(); i++) {
                if (serverPlayer.getInventory().getItem(i).is(ESItems.ORB_OF_PROPHECY.get())) hasOrb = true;
            }
            if (!challenged || !hasOrb) {
                conversationTarget = serverPlayer;
                ESPlatform.INSTANCE.sendToClient(serverPlayer, new OpenGatekeeperGuiPacket(getId(), killed, challenged));
            } else if (!getOffers().isEmpty()) {
                this.setTradingPlayer(serverPlayer);
                this.openTradingScreen(serverPlayer, this.getDisplayName(), 1);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        return InteractionResult.PASS;
    }

    public void handleDialogueClose(int operation) {
        if (operation == 1 && conversationTarget != null) {
            fightPlayerOnly = true;
            setFightTargetName(conversationTarget.getName().getString());
            setActivated(true);
            setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.DIAMOND_SWORD));
        }
        if (operation == 2 && conversationTarget != null) {
            fightPlayerOnly = true;
            setFightTargetName("");
            setActivated(false);
            ItemStack stack = ESItems.ORB_OF_PROPHECY.get().getDefaultInstance();
            ESCriteriaTriggers.CHALLENGED_GATEKEEPER.get().trigger(conversationTarget);
            if (!conversationTarget.getInventory().add(stack)) {
                ItemEntity entity = new ItemEntity(level(), conversationTarget.getX(), conversationTarget.getY(), conversationTarget.getZ(), stack);
                level().addFreshEntity(entity);
            }
        }
        conversationTarget = null;
    }

    public boolean canReachTarget(double range) {
        LivingEntity target = getTarget();
        if (target == null) {
            return false;
        }
        for (LivingEntity livingEntity : level().getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, this, getBoundingBox().inflate(range))) {
            if (livingEntity.getUUID().equals(target.getUUID())) {
                return true;
            }
        }
        return false;
    }

    public void performMeleeAttack(double range) {
        LivingEntity target = getTarget();
        if (target == null) {
            return;
        }
        for (LivingEntity livingEntity : level().getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, this, getBoundingBox().inflate(range))) {
            if (livingEntity.getUUID().equals(target.getUUID())) {
                livingEntity.invulnerableTime = 0;
                doHurtTarget(livingEntity);
                spawnMeleeAttackParticles();
            }
        }
    }

    public void spawnMeleeAttackParticles() {
        if (level() instanceof ServerLevel serverLevel) {
            float lookYaw = getYHeadRot() + 90.0f;
            float lookPitch = -getXRot();
            Vec3 initialEndPos = ESMathUtil.rotationToPosition(getEyePosition(), 1f, lookPitch, lookYaw);
            for (int i = 0; i < 15; i++) {
                Vec3 endPos = initialEndPos.offsetRandom(getRandom(), 0.8f);
                ESPlatform.INSTANCE.sendToAllClients(serverLevel, new ESParticlePacket(ESParticles.BLADE_SHOCKWAVE.get(), getEyePosition().x, getEyePosition().y, getEyePosition().z, endPos.x - getEyePosition().x, endPos.y - getEyePosition().y, endPos.z - getEyePosition().z));
            }
        }
    }

    @Override
    public void die(DamageSource damageSource) {
        if (damageSource.is(DamageTypes.GENERIC_KILL)) {
            super.die(damageSource);
        } else {
            getFightTarget().ifPresent(p -> {
                if (p instanceof ServerPlayer serverPlayer) {
                    ESCriteriaTriggers.CHALLENGED_GATEKEEPER.get().trigger(serverPlayer);
                }
            });
            setHealth(getMaxHealth());
            fightPlayerOnly = true;
            setFightTargetName("");
            setActivated(false);
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (!source.is(DamageTypes.GENERIC_KILL) && (source.getEntity() == null || getTarget() == null || source.getEntity().getUUID() != getTarget().getUUID())) {
            return false;
        }
        return super.hurt(source, amount);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        bossEvent.update();
        if (!level().isClientSide) {
            if (restockCoolDown > 0) {
                restockCoolDown--;
            } else {
                restockCoolDown = 12000;
                restockAll();
            }
            if (conversationTarget != null && conversationTarget.distanceTo(this) > 20) {
                conversationTarget = null;
            }
            if (getTradingPlayer() != null && getTradingPlayer().distanceTo(this) > 16) {
                setTradingPlayer(null);
            }
            setCustomName(Component.literal(gatekeeperName));
            if (isLeftHanded()) {
                setLeftHanded(false);
            }
            if (isActivated()) {
                attackManager.tick();
            }
        } else {
            level().addParticle(ESParticles.STARLIGHT.get(), getX() + (getRandom().nextDouble() - 0.5) * 2, getY() + 1 + (getRandom().nextDouble() - 0.5) * 2, getZ() + (getRandom().nextDouble() - 0.5) * 2, 0, 0, 0);
        }
    }

    @Override
    public Music getBossMusic() {
        return BOSS_MUSIC;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.PLAYER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.PLAYER_DEATH;
    }

    @Override
    public void setTradingPlayer(@Nullable Player player) {
        this.customer = player;
    }

    @Nullable
    @Override
    public Player getTradingPlayer() {
        return this.customer;
    }

    @Override
    public MerchantOffers getOffers() {
        if (this.offers == null) {
            this.offers = new MerchantOffers();
            this.addTrades();
        }

        return this.offers;
    }

    protected void addTrades() {
        MerchantOffers merchantoffers = this.getOffers();
        this.addTrades(merchantoffers, GatekeeperTrades.TRADES, 50);
    }

    protected void addTrades(MerchantOffers original, VillagerTrades.ItemListing[] newTrades, int maxNumbers) {
        Set<Integer> set = Sets.newHashSet();
        if (newTrades.length > maxNumbers) {
            while (set.size() < maxNumbers) {
                set.add(this.random.nextInt(newTrades.length));
            }
        } else {
            for (int i = 0; i < newTrades.length; ++i) {
                set.add(i);
            }
        }

        for (Integer integer : set) {
            VillagerTrades.ItemListing villagertrades$itrade = newTrades[integer];
            MerchantOffer merchantoffer = villagertrades$itrade.getOffer(this, this.random);
            if (merchantoffer != null) {
                original.add(merchantoffer);
            }
        }
    }

    protected void restockAll() {
        for (MerchantOffer merchantoffer : this.getOffers()) {
            merchantoffer.resetUses();
        }
    }

    @Override
    public void overrideOffers(@Nullable MerchantOffers offers) {

    }

    @Override
    public void notifyTrade(MerchantOffer offer) {
        offer.increaseUses();
        this.ambientSoundTime = -this.getAmbientSoundInterval();
        if (offer.shouldRewardExp()) {
            int i = 3 + this.random.nextInt(4);
            this.level().addFreshEntity(new ExperienceOrb(this.level(), this.getX(), this.getY() + 0.5D, this.getZ(), i));
        }
    }

    @Override
    public void notifyTradeUpdated(ItemStack stack) {

    }

    @Override
    public int getVillagerXp() {
        return 0;
    }

    @Override
    public void overrideXp(int xp) {

    }

    @Override
    public boolean showProgressBar() {
        return false;
    }

    @Override
    public SoundEvent getNotifyTradeSound() {
        return null;
    }

    @Override
    public boolean isClientSide() {
        return this.level().isClientSide;
    }
}
