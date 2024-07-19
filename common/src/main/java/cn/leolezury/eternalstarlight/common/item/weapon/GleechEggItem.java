package cn.leolezury.eternalstarlight.common.item.weapon;

import cn.leolezury.eternalstarlight.common.entity.projectile.GleechEgg;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class GleechEggItem extends Item {
	public GleechEggItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
		if (!level.isClientSide) {
			GleechEgg egg = new GleechEgg(level, player);
			egg.setItem(itemStack);
			egg.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
			level.addFreshEntity(egg);
		}

		player.awardStat(Stats.ITEM_USED.get(this));
		itemStack.consume(1, player);

		return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
	}
}
