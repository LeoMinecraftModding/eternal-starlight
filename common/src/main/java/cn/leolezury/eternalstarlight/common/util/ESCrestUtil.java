package cn.leolezury.eternalstarlight.common.util;

import cn.leolezury.eternalstarlight.common.crest.Crest;
import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import cn.leolezury.eternalstarlight.common.item.component.CurrentCrestComponent;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.particle.OrbitalTrailParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESDataComponents;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
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
import java.util.Optional;

public class ESCrestUtil {
	public static final String TAG_CRESTS = "crests";
	public static final String TAG_OWNED_CRESTS = "owned_crests";

	public static Crest.Set getCrests(Player player) {
		return getCrests(player, TAG_CRESTS);
	}

	public static Crest.Set getOwnedCrests(Player player) {
		return getCrests(player, TAG_OWNED_CRESTS);
	}

	public static Crest.Set getCrests(Player player, String tagId) {
		CompoundTag tag = ESEntityUtil.getPersistentData(player).getCompound(tagId);
		return getCrests(player.registryAccess(), tag);
	}

	public static Crest.Set getCrests(HolderLookup.Provider provider, CompoundTag tag) {
		return Crest.Set.CODEC.parse(provider.createSerializationContext(NbtOps.INSTANCE), tag).resultOrPartial().orElse(new Crest.Set(new ArrayList<>()));
	}

	public static boolean setCrests(Player player, List<Crest.Instance> crests) {
		return setCrests(player, crests, TAG_CRESTS);
	}

	public static boolean setCrests(Player player, List<Crest.Instance> crests, String tagId) {
		Optional<Tag> optionalTag = setCrests(player.registryAccess(), crests);
		if (optionalTag.isEmpty()) {
			return false;
		}
		ESEntityUtil.getPersistentData(player).put(tagId, optionalTag.get());
		return true;
	}

	public static Optional<Tag> setCrests(HolderLookup.Provider provider, List<Crest.Instance> crests) {
		return Crest.Set.CODEC.encodeStart(provider.createSerializationContext(NbtOps.INSTANCE), new Crest.Set(crests)).resultOrPartial();
	}

	public static boolean giveCrest(Player player, Crest.Instance crest) {
		Crest.Set set = getCrests(player, TAG_OWNED_CRESTS);
		List<Crest.Instance> crests = new ArrayList<>(set.crests());
		for (Crest.Instance instance : crests) {
			if (instance.crest().is(crest.crest()) && instance.level() >= crest.level()) {
				return false;
			}
		}
		crests.removeIf(c -> c.crest().is(crest.crest()));
		crests.add(crest);
		setCrests(player, crests, TAG_OWNED_CRESTS);
		return true;
	}

	public static boolean upgradeCrest(Player player, ResourceKey<Crest> key) {
		Optional<Crest.Instance> instance = Crest.Instance.of(player.registryAccess(), key, getCrestLevel(player, key) + 1);
		return instance.isPresent() && giveCrest(player, instance.get());
	}

	public static boolean removeCrest(Player player, Holder<Crest> crest) {
		return removeCrest(player, crest, TAG_OWNED_CRESTS) || removeCrest(player, crest, TAG_CRESTS);
	}

	public static boolean removeCrest(Player player, Holder<Crest> crest, String tagId) {
		Crest.Set set = getCrests(player, tagId);
		List<Crest.Instance> crests = new ArrayList<>(set.crests());
		if (crests.stream().noneMatch(c -> c.crest().is(crest))) {
			return false;
		}
		crests.removeIf(c -> c.crest().is(crest));
		setCrests(player, crests, tagId);
		return true;
	}

	public static List<Crest.Instance> mergeCrests(List<Crest.Instance> first, List<Crest.Instance> second) {
		List<Crest.Instance> result = new ArrayList<>(first);
		for (Crest.Instance instance : second) {
			boolean hasSame = false;
			int level = instance.level();
			for (int i = 0; i < result.size(); i++) {
				if (result.get(i).crest().is(instance.crest())) {
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
		Optional<Holder.Reference<Crest>> crest = player.registryAccess().registryOrThrow(ESRegistries.CREST).getHolder(key);
		return crest.map(ref -> getCrestLevel(player, ref)).orElse(0);
	}

	public static int getCrestLevel(Player player, Holder<Crest> crest) {
		Crest.Set set = getCrests(player, TAG_OWNED_CRESTS);
		for (Crest.Instance instance : set.crests()) {
			if (instance.crest().is(crest)) {
				return instance.level();
			}
		}
		return 0;
	}

	public static void tickCrests(Player player) {
		Crest.Set ownedSet = getCrests(player, TAG_OWNED_CRESTS);
		Crest.Set set = getCrests(player);
		List<Crest.Instance> ownedCrestInstances = ownedSet.crests();
		List<Crest.Instance> crestInstances = set.crests();
		ItemStack mainHand = player.getMainHandItem();
		ItemStack offHand = player.getOffhandItem();
		if (mainHand.has(ESDataComponents.CURRENT_CREST.get())) {
			CurrentCrestComponent component = mainHand.get(ESDataComponents.CURRENT_CREST.get());
			if (component != null && component.crest().isBound() && ownedCrestInstances.stream().noneMatch(c -> c.crest().is(component.crest()))) {
				mainHand.remove(ESDataComponents.CURRENT_CREST.get());
			}
		} else if (offHand.has(ESDataComponents.CURRENT_CREST.get())) {
			CurrentCrestComponent component = offHand.get(ESDataComponents.CURRENT_CREST.get());
			if (component != null && component.crest().isBound() && ownedCrestInstances.stream().noneMatch(c -> c.crest().is(component.crest()))) {
				offHand.remove(ESDataComponents.CURRENT_CREST.get());
			}
		}
		crestInstances.forEach(crest -> {
			if (player.hasInfiniteMaterials()) {
				applyCrestEffects(player, crest);
			} else {
				Inventory inventory = player.getInventory();
				for (int i = 0; i < inventory.getContainerSize(); i++) {
					ItemStack stack = inventory.getItem(i);
					if (stack.is(crest.crest().value().type().getCrystalsTag())) {
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
								&& crestInstances.stream().noneMatch(c1 -> c1.crest().value().attributeModifiers().isPresent() && c1.crest().value().attributeModifiers().get().stream().anyMatch(mod -> mod.getModifierId(c1.level()).equals(m.id())))) {
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
		crest.crest().value().effects().ifPresent(effects ->
			effects.forEach(mobEffect -> player.addEffect(new MobEffectInstance(mobEffect.effect(), 20, mobEffect.level() + (crest.level() - 1) * mobEffect.levelAddition())))
		);
		crest.crest().value().attributeModifiers().ifPresent(modifiers ->
			modifiers.forEach(modifier -> {
				AttributeInstance instance = player.getAttributes().getInstance(modifier.attribute());
				if (instance != null && !instance.hasModifier(modifier.getModifierId(crest.level()))) {
					instance.addPermanentModifier(modifier.getModifier(crest.level()));
				}
			})
		);
	}
}
