package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.platform.EntityDataAttachment;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class ESDataAttachments {
	public static final EntityDataAttachment<LivingEntity> CONCENTRATED_TARGET = ESPlatform.INSTANCE.registerDataAttachment("concentrated_target", () -> null, null, false);
	public static final EntityDataAttachment<ItemStack> CONCENTRATED_WEAPON = ESPlatform.INSTANCE.registerDataAttachment("concentrated_weapon", () -> null, null, false);
	public static final EntityDataAttachment<Integer> LAST_CONCENTRATED_ATTACK_TIME = ESPlatform.INSTANCE.registerDataAttachment("last_concentrated_attack_time", () -> Integer.MIN_VALUE, null, false);
	public static final EntityDataAttachment<Integer> CONCENTRATION_LEVEL = ESPlatform.INSTANCE.registerDataAttachment("concentration_level", () -> 0, null, false);
	public static final EntityDataAttachment<Integer> FLOWGLAZE_DESTROY_BLOCK_TICKS = ESPlatform.INSTANCE.registerDataAttachment("flowglaze_destroy_block_ticks", () -> 0, null, false);

	public static void loadClass() {
	}
}
