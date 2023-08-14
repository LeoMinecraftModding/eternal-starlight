package cn.leolezury.eternalstarlight.mixins.access;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(PickaxeItem.class)
public interface PickaxeItemAccess {
    @Invoker("<init>")
    static PickaxeItem create(Tier $$0, int $$1, float $$2, Item.Properties $$3) {
        throw new UnsupportedOperationException();
    }
}
