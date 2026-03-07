package cn.leolezury.eternalstarlight.common.item.interfaces;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface SwingAttackWeapon {
	void performSwingAttack(ItemStack stack, Player player);
}
