package cn.leolezury.eternalstarlight.item.misc;

import net.minecraft.resources.ResourceLocation;
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
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

import org.jetbrains.annotations.Nullable;

public class LootBagItem extends Item {
    public LootBagItem(Properties p_41383_) {
        super(p_41383_);
    }
    
    protected void dropFromLootTable(Level level, Vec3 pos, ResourceLocation lootTable) {
        if (level.getServer() == null) {
            return;
        }
        LootTable loottable = level.getServer().getLootData().getLootTable(lootTable);
        LootParams.Builder paramBuilder = (new LootParams.Builder((ServerLevel)level)).withParameter(LootContextParams.ORIGIN, pos);
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
        player.swing(hand);
        if (!level.isClientSide) {
            String lootTable = stack.getOrCreateTag().getString("LootTable");
            dropFromLootTable(level, player.position(), new ResourceLocation(lootTable));
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
        }
        return InteractionResultHolder.consume(stack);
    }
}
