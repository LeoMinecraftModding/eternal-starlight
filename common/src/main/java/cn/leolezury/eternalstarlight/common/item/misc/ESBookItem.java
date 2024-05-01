package cn.leolezury.eternalstarlight.common.item.misc;

import cn.leolezury.eternalstarlight.common.network.TestPacket;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import com.mojang.logging.LogUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ESBookItem extends Item {
    public ESBookItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        var logger = LogUtils.getLogger();
        if (level.isClientSide) {
            logger.info("Sending a test packet to the server...");
            // ESPlatform.INSTANCE.sendToServer(new TestPacket(player.getName().getString()));
        } else {
            logger.info("Sending a test packet to the client...");
            ESPlatform.INSTANCE.sendToClient((ServerPlayer) player, new TestPacket(player.getName().getString()));
        }
        /*if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            BookManager.openBook(new ResourceLocation(serverPlayer.getItemInHand(hand).getOrCreateTag().getString("book")), serverPlayer);
        }*/
        return super.use(level, player, hand);
    }
}
