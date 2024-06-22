package cn.leolezury.eternalstarlight.common.item.weapon;

import cn.leolezury.eternalstarlight.common.entity.living.monster.TangledSkull;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import net.minecraft.core.Direction;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class TangledSkullItem extends StandingAndWallBlockItem {
    public TangledSkullItem(Block block, Block wallBlock, Properties properties, Direction direction) {
        super(block, wallBlock, properties, direction);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        if (!level.isClientSide) {
            TangledSkull skull = new TangledSkull(ESEntities.TANGLED_SKULL.get(), level);
            skull.setPos(player.getEyePosition());
            skull.setShotMovement(ESMathUtil.rotationToPosition(1, -player.getXRot(), player.getYHeadRot() + 90.0f));
            level.addFreshEntity(skull);
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        if (!player.getAbilities().instabuild) {
            itemStack.shrink(1);
        }

        player.getCooldowns().addCooldown(this, 60);

        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }
}
