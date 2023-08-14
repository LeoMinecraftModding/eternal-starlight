package cn.leolezury.eternalstarlight.item.misc;

import cn.leolezury.eternalstarlight.manager.book.BookManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SLBookItem extends Item {
    public SLBookItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            BookManager.openBook(new ResourceLocation(serverPlayer.getItemInHand(hand).getOrCreateTag().getString("book")), serverPlayer);
        }
        return super.use(level, player, hand);
    }
}
