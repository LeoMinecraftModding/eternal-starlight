package cn.leolezury.eternalstarlight.common.item.misc;

import cn.leolezury.eternalstarlight.common.network.OpenStarlightStoryPacket;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.util.ESBookUtil;
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
		if (!player.level().isClientSide && player instanceof ServerPlayer serverPlayer) {
			ESPlatform.INSTANCE.sendToClient(serverPlayer, new OpenStarlightStoryPacket(ESBookUtil.getUnlockedPartsFor(serverPlayer)));
		}
		return super.use(level, player, hand);
	}
}
