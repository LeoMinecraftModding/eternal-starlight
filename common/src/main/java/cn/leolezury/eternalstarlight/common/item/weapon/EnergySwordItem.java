package cn.leolezury.eternalstarlight.common.item.weapon;

import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class EnergySwordItem extends SwordItem {
    public EnergySwordItem(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public void releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int ticksLeft) {
        if (getUseDuration(itemStack) - ticksLeft >= 10 && livingEntity.level() instanceof ServerLevel serverLevel) {
            Vec3 initialStartPos = livingEntity.getEyePosition();
            float lookYaw = livingEntity.getYHeadRot() + 90.0f;
            float lookPitch = -livingEntity.getXRot();
            Vec3 initialEndPos = ESMathUtil.rotationToPosition(initialStartPos, 0.3f, lookPitch, lookYaw);
            livingEntity.hurtMarked = true;
            livingEntity.setDeltaMovement(initialEndPos.subtract(initialStartPos).scale(5));
            livingEntity.invulnerableTime += 20;
            for (LivingEntity entity : serverLevel.getEntitiesOfClass(LivingEntity.class, livingEntity.getBoundingBox().inflate(livingEntity.getBbWidth() * 4f))) {
                if (!entity.getUUID().equals(livingEntity.getUUID())) {
                    entity.hurt(livingEntity instanceof Player player ? serverLevel.damageSources().playerAttack(player) : serverLevel.damageSources().mobAttack(livingEntity), 4);
                }
            }
            for (LivingEntity entity : serverLevel.getEntitiesOfClass(LivingEntity.class, livingEntity.getBoundingBox().inflate(livingEntity.getBbWidth() * 4f).move(initialEndPos.subtract(initialStartPos).scale(5)))) {
                if (!entity.getUUID().equals(livingEntity.getUUID())) {
                    entity.hurt(livingEntity instanceof Player player ? serverLevel.damageSources().playerAttack(player) : serverLevel.damageSources().mobAttack(livingEntity), 4);
                }
            }
            level.playSound(null, livingEntity, SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 1.0F, 1.0F);
            for (int i = 0; i < 15; i++) {
                Vec3 startPos = initialStartPos.offsetRandom(livingEntity.getRandom(), 0.4f);
                Vec3 endPos = initialEndPos.offsetRandom(livingEntity.getRandom(), 0.8f);
                ESPlatform.INSTANCE.sendToAllClients(serverLevel, new ParticlePacket(ESParticles.BLADE_SHOCKWAVE.get(), startPos.x, startPos.y, startPos.z, endPos.x - startPos.x, endPos.y - startPos.y, endPos.z - startPos.z));
            }
            if (livingEntity instanceof Player player) {
                player.getCooldowns().addCooldown(this, 40);
            }
        }
    }

    public int getUseDuration(ItemStack itemStack) {
        return 72000;
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        if (itemStack.getDamageValue() >= itemStack.getMaxDamage() - 1) {
            return InteractionResultHolder.fail(itemStack);
        } else {
            player.startUsingItem(interactionHand);
            return InteractionResultHolder.consume(itemStack);
        }
    }
}
