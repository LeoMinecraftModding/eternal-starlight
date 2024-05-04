package cn.leolezury.eternalstarlight.common.item.misc;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.network.OpenStarlightStoryPacket;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import net.minecraft.Util;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class ESBookItem extends Item {
    private static final List<ResourceLocation> bossAdvancements = Util.make(() -> {
        List<ResourceLocation> locations = new ArrayList<>();
        locations.add(new ResourceLocation(EternalStarlight.MOD_ID, "kill_golem"));
        locations.add(new ResourceLocation(EternalStarlight.MOD_ID, "kill_lunar_monstrosity"));
        return locations;
    });

    public ESBookItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!player.level().isClientSide && player.level() instanceof ServerLevel serverLevel && player instanceof ServerPlayer serverPlayer) {
            int bossProgression = 0;
            for (int i = 0; i < bossAdvancements.size(); i++) {
                AdvancementHolder holder = serverLevel.getServer().getAdvancements().get(bossAdvancements.get(i));
                if (holder != null && serverPlayer.getAdvancements().getOrStartProgress(holder).isDone()) {
                    bossProgression = i + 1;
                } else {
                    break;
                }
            }
            ESPlatform.INSTANCE.sendToClient(serverPlayer, new OpenStarlightStoryPacket(bossProgression));
        }
        return super.use(level, player, hand);
    }
}
