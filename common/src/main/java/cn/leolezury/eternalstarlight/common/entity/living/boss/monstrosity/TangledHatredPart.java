package cn.leolezury.eternalstarlight.common.entity.living.boss.monstrosity;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class TangledHatredPart extends LivingEntity {
    private TangledHatred parent;

    public void setParent(TangledHatred parent) {
        this.parent = parent;
    }

    public TangledHatred getParent() {
        return parent;
    }

    public TangledHatredPart(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void push(Entity entity) {

    }

    @Override
    protected void pushEntities() {

    }

    @Override
    public void travel(Vec3 vec3) {

    }

    @Override
    public void setDeltaMovement(Vec3 vec3) {

    }

    @Override
    public boolean hurt(DamageSource damageSource, float f) {
        if (parent != null) {
            if (damageSource.getDirectEntity() != null && (damageSource.getEntity() == null || !damageSource.getEntity().getUUID().equals(parent.getUUID()))) {
                parent.hurt(damageSource, f);
            }
        }
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide && tickCount > 10) {
            if (parent == null || parent.isDeadOrDying() || parent.isRemoved()) {
                discard();
            } else {
                boolean hasPart = false;
                for (TangledHatredPart part : parent.parts) {
                    if (part.getUUID().equals(getUUID())) {
                        hasPart = true;
                    }
                }
                if (!hasPart) {
                    discard();
                }
            }
        }
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public boolean shouldBeSaved() {
        return false;
    }

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return List.of();
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot equipmentSlot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemSlot(EquipmentSlot equipmentSlot, ItemStack itemStack) {

    }

    @Override
    public HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }
}
