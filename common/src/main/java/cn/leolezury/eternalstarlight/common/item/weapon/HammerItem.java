package cn.leolezury.eternalstarlight.common.item.weapon;

import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class HammerItem extends TieredItem {
    public HammerItem(Tier tier, Properties properties) {
        super(tier, properties.component(DataComponents.TOOL, tier.createToolProperties(BlockTags.MINEABLE_WITH_PICKAXE)));
    }

    protected void spawnBlockParticle(Level level, BlockPos pos, Vec3 particlePos) {
        BlockState state = level.getBlockState(pos.below());
        if (state.getRenderShape() != RenderShape.INVISIBLE) {
            level.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, state), particlePos.x, particlePos.y, particlePos.z, 0, 1.5D, 0);
        }
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        BlockPos pos = context.getClickedPos();
        if (level.isClientSide) {
            for (int i = 0; i < 360; i += 10) {
                Vec3 vec3 = ESMathUtil.rotationToPosition(pos.getCenter().add(0, -0.1, 0), 2, 0, i);
                BlockPos particlePos = new BlockPos((int) vec3.x, (int) vec3.y, (int) vec3.z);
                spawnBlockParticle(level, particlePos, vec3.add(0, 0.6, 0));
            }
        } else if (player != null) {
            for (LivingEntity entity : level.getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, player, new AABB(pos).inflate(2))) {
                if (entity.hurt(level.damageSources().playerAttack(player), 8)) {
                    player.doEnchantDamageEffects(player, entity);
                    double knockbackParam = Math.max(0.0D, 1.0D - entity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                    entity.setDeltaMovement(entity.getDeltaMovement().add(0.0D, (double)0.4F * knockbackParam, 0.0D));
                }
            }
            player.playSound(SoundEvents.GENERIC_EXPLODE.value());
            player.getCooldowns().addCooldown(this, 60);
            context.getItemInHand().hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
        }
        return InteractionResult.SUCCESS;
    }

    public boolean hurtEnemy(ItemStack stack, LivingEntity entity, LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, EquipmentSlot.MAINHAND);
        return true;
    }

    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entity) {
        if (!level.isClientSide && state.getDestroySpeed(level, pos) != 0.0F) {
            stack.hurtAndBreak(1, entity, EquipmentSlot.MAINHAND);
        }

        return true;
    }
}
