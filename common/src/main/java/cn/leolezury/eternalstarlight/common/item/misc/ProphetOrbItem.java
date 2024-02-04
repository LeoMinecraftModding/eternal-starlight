package cn.leolezury.eternalstarlight.common.item.misc;

import cn.leolezury.eternalstarlight.common.crest.Crest;
import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import cn.leolezury.eternalstarlight.common.network.OpenCrestGuiPacket;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.util.CrestUtil;
import net.minecraft.core.Registry;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Objects;

public class ProphetOrbItem extends Item implements Vanishable {
    public ProphetOrbItem(Properties properties) {
        super(properties);
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack itemStack, int i) {
        if (livingEntity.getPose() != Pose.STANDING) {
            livingEntity.stopUsingItem();
        }
        if (livingEntity.getTicksUsingItem() >= 140 && livingEntity instanceof ServerPlayer player) {
            Registry<Crest> registry = player.level().registryAccess().registryOrThrow(ESRegistries.CREST);
            List<String> crests = CrestUtil.getCrests(player).stream().map(c -> Objects.requireNonNull(registry.getKey(c)).toString()).toList();
            ESPlatform.INSTANCE.sendToClient(player, new OpenCrestGuiPacket(crests));
            player.stopUsingItem();
            player.getCooldowns().addCooldown(this, 20);
        }
    }

    public int getUseDuration(ItemStack itemStack) {
        return 72000;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        if (player.getPose() == Pose.STANDING) {
            player.startUsingItem(interactionHand);
            return InteractionResultHolder.consume(itemStack);
        }
        return InteractionResultHolder.pass(itemStack);
    }
}
