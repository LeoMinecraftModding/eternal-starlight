package cn.leolezury.eternalstarlight.platform;

import cn.leolezury.eternalstarlight.client.model.item.ForgeGlowingBakedModel;
import com.google.auto.service.AutoService;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Optional;
import java.util.function.Supplier;

@AutoService(ESPlatform.class)
public class ForgePlatform implements ESPlatform {
    @Override
    public Loader getLoader() {
        return Loader.FORGE;
    }

    @Override
    public FlowerPotBlock createFlowerPot(Supplier<FlowerPotBlock> pot, Supplier<? extends Block> flower, BlockBehaviour.Properties properties) {
        return new FlowerPotBlock(pot, flower, properties);
    }

    @Override
    public EntityType<?> getEntityType(ResourceLocation location) {
        Optional<Holder<EntityType<?>>> entityTypeHolder = ForgeRegistries.ENTITY_TYPES.getHolder(location);
        return entityTypeHolder.<EntityType<?>>map(Holder::get).orElse(null);
    }
}
