package cn.leolezury.eternalstarlight.common.registry;

import cn.leolezury.eternalstarlight.common.crest.Crest;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.platform.EntityDataAttachment;
import cn.leolezury.eternalstarlight.common.spell.SpellCastData;
import cn.leolezury.eternalstarlight.common.spell.SpellCooldown;
import cn.leolezury.eternalstarlight.common.util.SpecialItemCooldown;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ESDataAttachments {
	private static final List<EntityDataAttachment<?>> ATTACHMENTS = new ArrayList<>();

	public static final EntityDataAttachment<Vec3> MOVEMENT = register(ESPlatform.INSTANCE.registerDataAttachment("movement", () -> Vec3.ZERO, null, null, false));
	public static final EntityDataAttachment<Integer> LAST_MOVEMENT_UPDATE = register(ESPlatform.INSTANCE.registerDataAttachment("last_movement_update", () -> 0, null, null, false));
	public static final EntityDataAttachment<LivingEntity> CONCENTRATED_TARGET = register(ESPlatform.INSTANCE.registerDataAttachment("concentrated_target", () -> null, null, null, false));
	public static final EntityDataAttachment<ItemStack> CONCENTRATED_WEAPON = register(ESPlatform.INSTANCE.registerDataAttachment("concentrated_weapon", () -> null, null, null, false));
	public static final EntityDataAttachment<Integer> LAST_CONCENTRATED_ATTACK_TIME = register(ESPlatform.INSTANCE.registerDataAttachment("last_concentrated_attack_time", () -> Integer.MIN_VALUE, null, null, false));
	public static final EntityDataAttachment<Integer> CONCENTRATION_LEVEL = register(ESPlatform.INSTANCE.registerDataAttachment("concentration_level", () -> 0, null, null, false));
	public static final EntityDataAttachment<BlockPos> FLOWGLAZE_DESTROY_BLOCK_TARGET = register(ESPlatform.INSTANCE.registerDataAttachment("flowglaze_destroy_block_target", () -> null, null, null, false));
	public static final EntityDataAttachment<Integer> FLOWGLAZE_DESTROY_BLOCK_TICKS = register(ESPlatform.INSTANCE.registerDataAttachment("flowglaze_destroy_block_ticks", () -> 0, null, null, false));
	public static final EntityDataAttachment<SpellCastData> SPELL_CAST_DATA = register(ESPlatform.INSTANCE.registerDataAttachment("spell_cast_data", SpellCastData::getDefault, null, SpellCastData.STREAM_CODEC, false));
	public static final EntityDataAttachment<List<SpellCooldown>> SPELL_COOLDOWNS = register(ESPlatform.INSTANCE.registerDataAttachment("spell_cooldowns", List::of, SpellCooldown.LIST_CODEC, null, false));
	public static final EntityDataAttachment<SpellCastData.SpellSource> SPELL_SOURCE = register(ESPlatform.INSTANCE.registerDataAttachment("spell_source", () -> e -> false, null, null, false));
	public static final EntityDataAttachment<Boolean> IN_ETHER = register(ESPlatform.INSTANCE.registerDataAttachment("in_ether", () -> false, Codec.BOOL, ByteBufCodecs.BOOL, false));
	public static final EntityDataAttachment<Integer> IN_ETHER_TICKS = register(ESPlatform.INSTANCE.registerDataAttachment("in_ether_ticks", () -> 0, Codec.INT, ByteBufCodecs.INT, false));
	public static final EntityDataAttachment<Boolean> OBTAINED_BLOSSOM_OF_STARS = register(ESPlatform.INSTANCE.registerDataAttachment("obtained_blossom_of_stars", () -> false, Codec.BOOL, null, true));
	public static final EntityDataAttachment<Float> NUMBNESS_DAMAGE = register(ESPlatform.INSTANCE.registerDataAttachment("numbness_damage", () -> 0f, Codec.FLOAT, null, false));
	public static final EntityDataAttachment<Integer> TEARY_TICKS = register(ESPlatform.INSTANCE.registerDataAttachment("teary_ticks", () -> 0, Codec.INT, null, false));
	public static final EntityDataAttachment<Integer> ABYSSAL_FIRE_TICKS = register(ESPlatform.INSTANCE.registerDataAttachment("abyssal_fire_ticks", () -> 0, Codec.INT, ByteBufCodecs.INT, false));
	public static final EntityDataAttachment<String> ARROW_TYPE = register(ESPlatform.INSTANCE.registerDataAttachment("arrow_type", () -> "", Codec.STRING, null, false));
	public static final EntityDataAttachment<Float> FLOWGLAZE_ARROW_EXTRA_BASE_DAMAGE = register(ESPlatform.INSTANCE.registerDataAttachment("flowglaze_arrow_extra_base_damage", () -> 0f, Codec.FLOAT, null, false));
	public static final EntityDataAttachment<Integer> METEOR_COOLDOWN = register(ESPlatform.INSTANCE.registerDataAttachment("meteor_cooldown", () -> 0, Codec.INT, null, false));
	public static final EntityDataAttachment<List<Crest.Instance>> OLD_ACTIVE_CRESTS = register(ESPlatform.INSTANCE.registerDataAttachment("old_active_crests", List::of, Crest.Instance.LIST_CODEC, null, true));
	public static final EntityDataAttachment<List<Crest.Instance>> CRESTS = register(ESPlatform.INSTANCE.registerDataAttachment("crests", List::of, Crest.Instance.LIST_CODEC, null, true));
	public static final EntityDataAttachment<List<Crest.Instance>> OWNED_CRESTS = register(ESPlatform.INSTANCE.registerDataAttachment("owned_crests", List::of, Crest.Instance.LIST_CODEC, null, true));
	public static final EntityDataAttachment<List<SpecialItemCooldown>> SPECIAL_ITEM_COOLDOWNS = register(ESPlatform.INSTANCE.registerDataAttachment("special_item_cooldowns", List::of, SpecialItemCooldown.LIST_CODEC, null, false));
	public static final EntityDataAttachment<Integer> HUSK_OWNER_ID = register(ESPlatform.INSTANCE.registerDataAttachment("husk_owner_id", () -> -1, null, null, false));
	public static final EntityDataAttachment<Integer> GATEKEEPER_CHALLENGE_COUNT = register(ESPlatform.INSTANCE.registerDataAttachment("gatekeeper_challenge_count", () -> 0, Codec.INT, null, true));
	public static final EntityDataAttachment<Integer> STRANGHOUL_HIRING_COOLDOWN = register(ESPlatform.INSTANCE.registerDataAttachment("stranghoul_hiring_cooldown", () -> 0, Codec.INT, ByteBufCodecs.INT, true));
	public static final EntityDataAttachment<Integer> BOARWARF_CREDIT = register(ESPlatform.INSTANCE.registerDataAttachment("boarwarf_credit", () -> 0, Codec.INT, null, true));
	public static final EntityDataAttachment<Boolean> CRESCENT_SPEAR_DASH = register(ESPlatform.INSTANCE.registerDataAttachment("crescent_spear_dash", () -> false, null, ByteBufCodecs.BOOL, false));
	public static final EntityDataAttachment<GlobalPos> ENERGY_TRANSMITTER_SOURCE = register(ESPlatform.INSTANCE.registerDataAttachment("energy_transmitter_source", () -> null, null, null, false));
	public static final EntityDataAttachment<Boolean> OFFHAND_ATTACK = register(ESPlatform.INSTANCE.registerDataAttachment("offhand_attack", () -> false, null, null, false));
	public static final EntityDataAttachment<ItemStack> LAST_OFFHAND_ITEM = register(ESPlatform.INSTANCE.registerDataAttachment("last_offhand_item", () -> ItemStack.EMPTY, null, null, false));
	public static final EntityDataAttachment<Integer> OFFHAND_ATTACK_STRENGTH_TIMER = register(ESPlatform.INSTANCE.registerDataAttachment("offhand_attack_strength_timer", () -> 0, null, ByteBufCodecs.INT, false));
	public static final EntityDataAttachment<Integer> GRAPPLING = register(ESPlatform.INSTANCE.registerDataAttachment("grappling", () -> -1, null, ByteBufCodecs.INT, false));
	public static final EntityDataAttachment<Integer> WHIP = register(ESPlatform.INSTANCE.registerDataAttachment("whip", () -> -1, null, ByteBufCodecs.INT, false));
	public static final EntityDataAttachment<List<String>> GUIDEBOOK_LISTENING_NAMESPACES = register(ESPlatform.INSTANCE.registerDataAttachment("guidebook_listening_namespaces", List::of, Codec.STRING.listOf(), null, true));
	public static final EntityDataAttachment<Boolean> IMPORTANT_ITEM = register(ESPlatform.INSTANCE.registerDataAttachment("important_item", () -> false, Codec.BOOL, ByteBufCodecs.BOOL, false));
	public static final EntityDataAttachment<Integer> CRYSTAL_GREATSWORD_MUSIC_INDEX = register(ESPlatform.INSTANCE.registerDataAttachment("crystal_greatsword_music_index", () -> 0, null, null, false));
	public static final EntityDataAttachment<Boolean> RECEIVED_GUIDEBOOK = register(ESPlatform.INSTANCE.registerDataAttachment("received_guidebook", () -> false, Codec.BOOL, null, true));

	private static <T> EntityDataAttachment<T> register(EntityDataAttachment<T> attachment) {
		ATTACHMENTS.add(attachment);
		return attachment;
	}

	public static List<EntityDataAttachment<?>> getAttachments() {
		return Collections.unmodifiableList(ATTACHMENTS);
	}

	public static EntityDataAttachment<?> byId(ResourceLocation id) {
		return ATTACHMENTS.stream().filter(a -> a.id().equals(id)).findFirst().orElse(null);
	}

	public static void loadClass() {
	}
}
