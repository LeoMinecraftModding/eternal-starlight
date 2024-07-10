package cn.leolezury.eternalstarlight.common.util;

import cn.leolezury.eternalstarlight.common.crest.Crest;
import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import cn.leolezury.eternalstarlight.common.item.component.CurrentCrestComponent;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.particle.OrbitalTrailParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESDataComponents;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ESCrestUtil {
	public static List<Crest.Instance> getCrests(Player player) {
		return getCrests(player, "Crests");
	}

	public static List<Crest.Instance> getCrests(Player player, String tagId) {
		if (!ESEntityUtil.getPersistentData(player).contains(tagId, Tag.TAG_COMPOUND)) {
			ESEntityUtil.getPersistentData(player).put(tagId, new CompoundTag());
		}
		CompoundTag crests = ESEntityUtil.getPersistentData(player).getCompound(tagId);
		return getCrests(player.level().registryAccess(), crests);
	}

	public static List<Crest.Instance> getCrests(RegistryAccess access, CompoundTag tag) {
		List<Crest.Instance> result = new ArrayList<>();
		Registry<Crest> registry = access.registryOrThrow(ESRegistries.CREST);
		registry.forEach((crest -> {
			String id = Objects.requireNonNull(registry.getKey(crest)).toString();
			if (tag.getBoolean(id)) {
				result.add(new Crest.Instance(crest, tag.getCompound("levels").getInt(id)));
			}
		}));
		return result;
	}

	public static void setCrests(Player player, List<Crest.Instance> crests) {
		setCrests(player, crests, "Crests");
	}

	public static void setCrests(Player player, List<Crest.Instance> crests, String tagId) {
		CompoundTag crestsTag = new CompoundTag();
		setCrests(player.level().registryAccess(), crestsTag, crests);
		ESEntityUtil.getPersistentData(player).put(tagId, crestsTag);
	}

	public static void setCrests(RegistryAccess access, CompoundTag tag, List<Crest.Instance> crests) {
		Registry<Crest> registry = access.registryOrThrow(ESRegistries.CREST);
		CompoundTag levels = new CompoundTag();
		crests.forEach((crest -> {
			String id = Objects.requireNonNull(registry.getKey(crest.crest())).toString();
			tag.putBoolean(id, true);
			levels.putInt(id, crest.level());
		}));
		tag.put("levels", levels);
	}

	public static boolean giveCrest(Player player, Crest.Instance crest) {
		List<Crest.Instance> crests = getCrests(player, "OwnedCrests");
		for (Crest.Instance instance : crests) {
			if (instance.crest() == crest.crest() && instance.level() >= crest.level()) {
				return false;
			}
		}
		crests.removeIf(c -> c.crest() == crest.crest());
		crests.add(crest);
		setCrests(player, crests, "OwnedCrests");
		return true;
	}

	public static boolean upgradeCrest(Player player, ResourceKey<Crest> key) {
		Optional<Crest.Instance> instance = Crest.Instance.of(player.registryAccess(), key, getCrestLevel(player, key) + 1);
		return instance.isPresent() && giveCrest(player, instance.get());
	}

	public static boolean removeCrest(Player player, Crest crest) {
		return removeCrest(player, crest, "OwnedCrests") || removeCrest(player, crest, "Crests");
	}

	public static boolean removeCrest(Player player, Crest crest, String tagId) {
		List<Crest.Instance> crests = getCrests(player, tagId);
		if (crests.stream().noneMatch(c -> c.crest() == crest)) {
			return false;
		}
		crests.removeIf(c -> c.crest() == crest);
		setCrests(player, crests, tagId);
		return true;
	}

	public static List<Crest.Instance> mergeCrests(List<Crest.Instance> first, List<Crest.Instance> second) {
		List<Crest.Instance> result = new ArrayList<>(first);
		for (Crest.Instance instance : second) {
			boolean hasSame = false;
			int level = instance.level();
			for (int i = 0; i < result.size(); i++) {
				if (result.get(i).crest() == instance.crest()) {
					level = Math.max(level, result.get(i).level());
					result.set(i, new Crest.Instance(instance.crest(), level));
					hasSame = true;
				}
			}
			if (!hasSame) {
				result.add(instance);
			}
		}
		return result;
	}

	public static int getCrestLevel(Player player, ResourceKey<Crest> key) {
		Crest crest = player.registryAccess().registryOrThrow(ESRegistries.CREST).get(key);
		return getCrestLevel(player, crest);
	}

	public static int getCrestLevel(Player player, Crest crest) {
		for (Crest.Instance instance : getCrests(player, "OwnedCrests")) {
			if (instance.crest() == crest) {
				return instance.level();
			}
		}
		return 0;
	}

	public static void tickCrests(Player player) {
		List<Crest.Instance> ownedCrestInstances = getCrests(player, "OwnedCrests");
		List<Crest.Instance> crestInstances = getCrests(player);
		ItemStack mainHand = player.getMainHandItem();
		ItemStack offHand = player.getOffhandItem();
		if (mainHand.has(ESDataComponents.CURRENT_CREST.get())) {
			CurrentCrestComponent component = mainHand.get(ESDataComponents.CURRENT_CREST.get());
			if (component != null && component.crest().isBound() && ownedCrestInstances.stream().noneMatch(c -> c.crest() == component.crest().value())) {
				mainHand.remove(ESDataComponents.CURRENT_CREST.get());
			}
		} else if (offHand.has(ESDataComponents.CURRENT_CREST.get())) {
			CurrentCrestComponent component = offHand.get(ESDataComponents.CURRENT_CREST.get());
			if (component != null && component.crest().isBound() && ownedCrestInstances.stream().noneMatch(c -> c.crest() == component.crest().value())) {
				offHand.remove(ESDataComponents.CURRENT_CREST.get());
			}
		}
		crestInstances.forEach(crest -> {
			if (player.getAbilities().instabuild) {
				applyCrestEffects(player, crest);
			} else {
				Inventory inventory = player.getInventory();
				for (int i = 0; i < inventory.getContainerSize(); i++) {
					ItemStack stack = inventory.getItem(i);
					if (stack.is(crest.crest().type().getCrystalsTag())) {
						applyCrestEffects(player, crest);
						if (player.tickCount % 60 == 0) {
							stack.hurtAndBreak(crest.level(), player, EquipmentSlot.MAINHAND);
						}
						break;
					}
				}
			}
		});
		player.level().registryAccess().registryOrThrow(ESRegistries.CREST).forEach(c ->
			c.attributeModifiers().ifPresent(modifiers ->
				modifiers.forEach(modifier -> {
					AttributeInstance instance = player.getAttributes().getInstance(modifier.attribute());
					if (instance != null) {
						instance.getModifiers().forEach(m -> {
							if (m.id().toString().startsWith(modifier.id().toString())
								&& crestInstances.stream().noneMatch(c1 -> c1.crest().attributeModifiers().isPresent() && c1.crest().attributeModifiers().get().stream().anyMatch(mod -> mod.getModifierId(c1.level()).equals(m.id())))) {
								instance.removeModifier(m.id());
							}
						});
					}
				})
			)
		);
		if (!crestInstances.isEmpty() && player.level() instanceof ServerLevel serverLevel && player.tickCount % 100 == 0) {
			ESPlatform.INSTANCE.sendToAllClients(serverLevel, new ParticlePacket(OrbitalTrailParticleOptions.magic(player), player.getX(), player.getY() - 1, player.getZ(), 0, 0.04, 0));
		}
	}

	public static void applyCrestEffects(Player player, Crest.Instance crest) {
		crest.crest().effects().ifPresent(effects ->
			effects.forEach(mobEffect -> player.addEffect(new MobEffectInstance(mobEffect.effect(), 20, mobEffect.level() + (crest.level() - 1) * mobEffect.levelAddition())))
		);
		crest.crest().attributeModifiers().ifPresent(modifiers ->
			modifiers.forEach(modifier -> {
				AttributeInstance instance = player.getAttributes().getInstance(modifier.attribute());
				if (instance != null && !instance.hasModifier(modifier.getModifierId(crest.level()))) {
					instance.addPermanentModifier(modifier.getModifier(crest.level()));
				}
			})
		);
	}
}
