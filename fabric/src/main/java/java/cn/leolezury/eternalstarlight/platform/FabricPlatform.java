package java.cn.leolezury.eternalstarlight.platform;

import cn.leolezury.eternalstarlight.platform.ESPlatform;
import com.google.auto.service.AutoService;
import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.mixin.content.registry.HoeItemAccessor;
import net.minecraft.world.item.context.UseOnContext;

import java.util.function.Consumer;
import java.util.function.Predicate;

@AutoService(ESPlatform.class)
public class FabricPlatform implements ESPlatform {
    @Override
    public Loader getLoader() {
        return Loader.FABRIC;
    }

    @Override
    public Pair<Predicate<UseOnContext>, Consumer<UseOnContext>> getToolTillAction(UseOnContext context) {
        return HoeItemAccessor.getTillingActions().get(context.getLevel().getBlockState(context.getClickedPos()).getBlock());
    }
}
