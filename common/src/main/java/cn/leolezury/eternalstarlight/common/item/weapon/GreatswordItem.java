package cn.leolezury.eternalstarlight.common.item.weapon;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class GreatswordItem extends SwordItem {
    private final float attackDamage;
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;

    public GreatswordItem(Tier tier, int baseDamage, float attackSpeed, Item.Properties properties) {
        super(tier, baseDamage, attackSpeed, properties);
        this.attackDamage = (float)baseDamage + tier.getAttackDamageBonus();
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (double)this.attackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", (double)attackSpeed, AttributeModifier.Operation.ADDITION));
        // TODO: reimplement this when updated to 1.20.5
        /*Attribute attribute = ESPlatform.INSTANCE.getEntityReachAttribute();
        // Implementation of ENTITY_REACH on fabric is not that easy
        if (attribute != null) {
            builder.put(ESPlatform.INSTANCE.getEntityReachAttribute(), new AttributeModifier(UUID.fromString("A9867629-19D6-F529-862E-21979863B5CF"), "Weapon modifier", 2, AttributeModifier.Operation.ADDITION));
        }*/
        this.defaultModifiers = builder.build();
    }

    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(slot);
    }
}
