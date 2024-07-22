package cn.leolezury.eternalstarlight.common.item.weapon;

import cn.leolezury.eternalstarlight.common.entity.projectile.FrozenTube;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.level.Level;

public class FrozenTubeItem extends Item implements ProjectileItem {
	public FrozenTubeItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
		if (!level.isClientSide) {
			FrozenTube tube = new FrozenTube(level, player, null);
			tube.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
			tube.setPos(tube.position().offsetRandom(player.getRandom(), 0.2f)); // so we can see the trail
			level.addFreshEntity(tube);
		}

		player.awardStat(Stats.ITEM_USED.get(this));
		itemStack.consume(1, player);

		player.getCooldowns().addCooldown(this, 60);

		return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
	}

	@Override
	public Projectile asProjectile(Level level, Position position, ItemStack itemStack, Direction direction) {
		FrozenTube tube = new FrozenTube(ESEntities.FROZEN_TUBE.get(), level);
		tube.setPos(position.x(), position.y(), position.z());
		return tube;
	}
}
