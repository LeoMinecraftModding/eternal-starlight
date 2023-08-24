package cn.leolezury.eternalstarlight.item.weapon;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.util.List;

public class CommonScytheItem extends ScytheItem {
    public CommonScytheItem(Tier tier, float damage, float attackSpeed, Properties properties) {
        super(tier, damage, attackSpeed, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity entity, LivingEntity attacker) {
        // Copied from vanilla Player.attack
        if (attacker instanceof Player player) {
            float l = (float) (1.0f + EnchantmentHelper.getSweepingDamageRatio(player) * player.getAttributeValue(Attributes.ATTACK_DAMAGE));
            List<LivingEntity> list = player.level().getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(1.0, 0.25, 1.0));
            for (LivingEntity livingEntity : list) {
                if (livingEntity == player || livingEntity == entity || player.isAlliedTo(livingEntity) || livingEntity instanceof ArmorStand && ((ArmorStand)livingEntity).isMarker() || !(player.distanceToSqr(livingEntity) < 9.0)) continue;
                livingEntity.knockback(0.4f, Mth.sin(player.getYRot() * ((float)Math.PI / 180)), -Mth.cos(player.getYRot() * ((float)Math.PI / 180)));
                livingEntity.hurt(player.damageSources().playerAttack(player), l);
            }
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, player.getSoundSource(), 1.0f, 1.0f);
            player.sweepAttack();
        }
        return super.hurtEnemy(stack, entity, attacker);
    }
}
