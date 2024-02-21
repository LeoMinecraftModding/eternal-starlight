package cn.leolezury.eternalstarlight.common.item.weapon;

import cn.leolezury.eternalstarlight.common.entity.projectile.FrozenTube;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class FrozenTubeItem extends Item {
    public FrozenTubeItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack stack = player.getItemInHand(interactionHand);
        if (!level.isClientSide) {
            Vec3 targetPos = ESMathUtil.rotationToPosition(player.getEyePosition(), 1, -player.getXRot(), player.getYHeadRot() + 90);
            Vec3 launchPos = player.getEyePosition();
            Vec3 delta = targetPos.subtract(launchPos).normalize();
            FrozenTube tube = new FrozenTube(level, player, delta.x, delta.y, delta.z);
            tube.setPos(launchPos);
            tube.setOwner(player);
            level.addFreshEntity(tube);
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
        }
        return InteractionResultHolder.consume(stack);
    }
}
