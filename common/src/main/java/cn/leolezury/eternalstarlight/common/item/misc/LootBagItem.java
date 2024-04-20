package cn.leolezury.eternalstarlight.common.item.misc;

import cn.leolezury.eternalstarlight.common.registry.ESDataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class LootBagItem extends Item {
    public LootBagItem(Properties properties) {
        super(properties);
    }
    
    protected void dropFromLootTable(Level level, Vec3 pos, ResourceKey<LootTable> lootTable) {
        MinecraftServer server;
        if ((server = level.getServer()) == null) {
            return;
        }
        LootTable loottable = server.reloadableRegistries().getLootTable(lootTable);
        LootParams.Builder paramBuilder = new LootParams.Builder((ServerLevel)level);
        LootParams params = paramBuilder.create(LootContextParamSets.EMPTY);
        loottable.getRandomItems(params).forEach((stack) -> spawnAtLocation(stack, level, pos));
    }

    @Nullable
    public ItemEntity spawnAtLocation(ItemStack stack, Level level, Vec3 pos) {
        if (stack.isEmpty()) {
            return null;
        } else if (level.isClientSide) {
            return null;
        } else {
            ItemEntity itementity = new ItemEntity(level, pos.x, pos.y, pos.z, stack);
            itementity.setDefaultPickUpDelay();
            level.addFreshEntity(itementity);
            return itementity;
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide) {
            dropFromLootTable(level, player.position(), stack.getOrDefault(ESDataComponents.LOOT_TABLE.get(), ResourceKey.create(Registries.LOOT_TABLE, new ResourceLocation(""))));
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
        }
        return InteractionResultHolder.consume(stack);
    }
}
