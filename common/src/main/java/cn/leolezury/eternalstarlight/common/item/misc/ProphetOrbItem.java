package cn.leolezury.eternalstarlight.common.item.misc;

import cn.leolezury.eternalstarlight.common.client.gui.screens.CrestSelectScreen;
import cn.leolezury.eternalstarlight.common.client.model.animation.PlayerAnimator;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import cn.leolezury.eternalstarlight.common.util.ESUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.level.Level;

public class ProphetOrbItem extends Item implements Vanishable {
    public ProphetOrbItem(Properties properties) {
        super(properties);
    }
    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack itemStack, int i) {
        if (livingEntity.getPose() != Pose.STANDING) {
            livingEntity.stopUsingItem();
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        ESUtil.getPersistentData(player).putBoolean("UsingProphetOrb", true);
        int usingTicks = ESUtil.getPersistentData(player).getInt("UsingProphetOrbTicks");
        player.startUsingItem(interactionHand);
        if (player.getPose() == Pose.STANDING && level.isClientSide && usingTicks >= 280) {
            player.stopUsingItem();
            return InteractionResultHolder.consume(itemStack);
        }
        return InteractionResultHolder.pass(itemStack);
    }
}
