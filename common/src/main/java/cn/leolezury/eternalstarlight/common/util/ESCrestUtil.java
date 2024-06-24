package cn.leolezury.eternalstarlight.common.util;

import cn.leolezury.eternalstarlight.common.crest.Crest;
import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ESCrestUtil {
	public static List<Crest> getCrests(Player player) {
		return getCrests(player, "Crests");
	}

	public static List<Crest> getCrests(Player player, String tagId) {
		if (!ESEntityUtil.getPersistentData(player).contains(tagId, Tag.TAG_COMPOUND)) {
			ESEntityUtil.getPersistentData(player).put(tagId, new CompoundTag());
		}
		CompoundTag crests = ESEntityUtil.getPersistentData(player).getCompound(tagId);
		return getCrests(player.level().registryAccess(), crests);
	}

	public static List<Crest> getCrests(RegistryAccess access, CompoundTag tag) {
		List<Crest> result = new ArrayList<>();
		Registry<Crest> registry = access.registryOrThrow(ESRegistries.CREST);
		registry.forEach((crest -> {
			String id = Objects.requireNonNull(registry.getKey(crest)).toString();
			if (tag.getBoolean(id)) {
				result.add(crest);
			}
		}));
		return result;
	}

	public static void setCrests(Player player, List<Crest> crests) {
		setCrests(player, crests, "Crests");
	}

	public static void setCrests(Player player, List<Crest> crests, String tagId) {
		CompoundTag crestsTag = new CompoundTag();
		setCrests(player.level().registryAccess(), crestsTag, crests);
		ESEntityUtil.getPersistentData(player).put(tagId, crestsTag);
	}

	public static void setCrests(RegistryAccess access, CompoundTag tag, List<Crest> crests) {
		Registry<Crest> registry = access.registryOrThrow(ESRegistries.CREST);
		crests.forEach((crest -> {
			String id = Objects.requireNonNull(registry.getKey(crest)).toString();
			tag.putBoolean(id, true);
		}));
	}

	public static void giveCrest(Player player, Crest crest) {
		List<Crest> crests = getCrests(player, "OwnedCrests");
		crests.add(crest);
		setCrests(player, crests, "OwnedCrests");
	}

	public static boolean removeCrest(Player player, Crest crest) {
		List<Crest> crests = getCrests(player, "OwnedCrests");
		if (!crests.contains(crest)) {
			return false;
		}
		crests.remove(crest);
		setCrests(player, crests, "OwnedCrests");
		return true;
	}

	public static void tickCrests(Player player) {
		getCrests(player).forEach(crest -> {
			if (player.getAbilities().instabuild || crest.spell().isPresent()) {
				applyCrestEffects(player, crest);
			} else {
				Inventory inventory = player.getInventory();
				for (int i = 0; i < inventory.getContainerSize(); i++) {
					ItemStack stack = inventory.getItem(i);
					if (stack.is(crest.type().getCrystalsTag())) {
						applyCrestEffects(player, crest);
						if (player.tickCount % 60 == 0) {
							stack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
						}
						return;
					}
				}
			}
		});
	}

	public static void applyCrestEffects(Player player, Crest crest) {
		crest.effects().ifPresent(effects ->
			effects.forEach(mobEffect -> player.addEffect(new MobEffectInstance(mobEffect.effect(), 20, mobEffect.level())))
		);
	}
}
