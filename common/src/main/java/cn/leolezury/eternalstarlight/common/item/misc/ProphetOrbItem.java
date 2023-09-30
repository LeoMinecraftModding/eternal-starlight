package cn.leolezury.eternalstarlight.common.item.misc;

import cn.leolezury.eternalstarlight.common.util.ESTags;
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
        if (player.getOffhandItem().is(ESTags.Items.RIESTURUS_LOCATE_ITEM)) {

        } else if (player.getOffhandItem().is(ESTags.Items.RIESTURUS_LOCATE_ITEM)) {

        } else if (player.getOffhandItem().is(ESTags.Items.RINEMIN_LOCATE_ITEM)) {

        } else if (player.getOffhandItem().is(ESTags.Items.ANRLO_LOCATE_ITEM)) {

        } else if (player.getOffhandItem().is(ESTags.Items.CRATEVIRGO_LOCATE_ITEM)) {

        } else if (player.getOffhandItem().is(ESTags.Items.LIBRASCRP_LOCATE_ITEM)) {

        } else if (player.getOffhandItem().is(ESTags.Items.OPHUCHAGAR_LOCATE_ITEM)) {

        } else if (player.getOffhandItem().is(ESTags.Items.APORNIGAS_LOCATE_ITEM)) {

        } else if (player.getOffhandItem().is(ESTags.Items.EGASUSICE_LOCATE_ITEM)) {

        } else {

        }
        if (player.getPose() == Pose.STANDING && player.getPose() == Pose.STANDING) {
            player.startUsingItem(interactionHand);
            return InteractionResultHolder.consume(itemStack);
        }
        return InteractionResultHolder.pass(itemStack);
    }
}
