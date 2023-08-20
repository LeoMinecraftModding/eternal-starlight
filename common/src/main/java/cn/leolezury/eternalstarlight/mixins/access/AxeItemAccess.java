package cn.leolezury.eternalstarlight.mixins.access;

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AxeItem.class)
public interface AxeItemAccess {
    @Invoker("<init>")
    static AxeItem create(Tier $$0, float $$1, float $$2, Item.Properties $$3) {
        throw new UnsupportedOperationException();
    }
}
