package cn.leolezury.eternalstarlight.item.weapon;

import cn.leolezury.eternalstarlight.util.ESUtil;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class HammerItem extends DiggerItem implements Vanishable {
    private final float attackDamage;
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;

    public HammerItem(Tier tier, float damage, float attackSpeed, Item.Properties properties) {
        super(damage, attackSpeed, tier, BlockTags.MINEABLE_WITH_PICKAXE, properties);
        this.attackDamage = damage + tier.getAttackDamageBonus();
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (double)this.attackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", (double)attackSpeed, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }

    protected void spawnBlockParticle(Level level, BlockPos pos, Vec3 particlePos) {
        BlockState state = level.getBlockState(pos);
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
                Vec3 vec3 = ESUtil.rotationToPosition(pos.getCenter().add(0, -0.1, 0), 2, 0, i);
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
            level.playSound(null, pos, SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 2.0F, 1.0F);
            player.getCooldowns().addCooldown(this, 60);
            context.getItemInHand().hurtAndBreak(1, player, (livingEntity) -> {
                livingEntity.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
        }
        return InteractionResult.SUCCESS;
    }

    public boolean hurtEnemy(ItemStack stack, LivingEntity entity, LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, (livingEntity) -> {
            livingEntity.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        });
        return true;
    }

    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entity) {
        if (!level.isClientSide && state.getDestroySpeed(level, pos) != 0.0F) {
            stack.hurtAndBreak(1, entity, (livingEntity) -> {
                livingEntity.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
        }

        return true;
    }

    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(slot);
    }
}
