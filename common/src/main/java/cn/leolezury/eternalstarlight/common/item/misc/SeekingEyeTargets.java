package cn.leolezury.eternalstarlight.common.item.misc;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.data.ESBiomes;
import cn.leolezury.eternalstarlight.common.data.ESStructures;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.structure.Structure;

import java.util.List;

public class SeekingEyeTargets {
	public record Entry(ResourceKey<?> key, Component label, int color) {
	}

	public enum TargetType {
		BOSS_STRUCTURE("boss_structure", 0xFF6B6B, List.of(
			structure(ESStructures.GOLEM_FORGE, 0xFFA44D),
			structure(ESStructures.CURSED_GARDEN, 0xB16CEA)
		)),
		NORMAL_STRUCTURE("normal_structure", 0x7FC4FF, List.of(
			structure(ESStructures.STRANGHOUL_DEN, 0xE06060)
		)),
		BIOME("biome", 0x8FE8B8, List.of(
			biome(ESBiomes.STARLIGHT_FOREST, 0x7FC4FF),
			biome(ESBiomes.STARLIGHT_DENSE_FOREST, 0x4A8FD4),
			biome(ESBiomes.UMBRAL_PLAINS, 0x6B5B95),
			biome(ESBiomes.GLIMMER_SCRUBLAND, 0xFFD97A),
			biome(ESBiomes.STARLIGHT_PERMAFROST_FOREST, 0xE1F7FF),
			biome(ESBiomes.PERMAFROST_PEAKS, 0xCAEFFF),
			biome(ESBiomes.STARLIGHT_TAIGA, 0x9FCFEE),
			biome(ESBiomes.DARK_SWAMP, 0x5B8C52),
			biome(ESBiomes.SCARLET_FOREST, 0xE05B5B),
			biome(ESBiomes.TORREYA_FOREST, 0x8B7BE8),
			biome(ESBiomes.CRYSTALLIZED_DESERT, 0xE36BD6),
			biome(ESBiomes.LUCENT_MYCELIUM_ISLE, 0x9B7BD6),
			biome(ESBiomes.SOLARIS_ISLES, 0xF7C948),
			biome(ESBiomes.STARLIT_SKY, 0x8FE0FF),
			biome(ESBiomes.SHIMMER_RIVER, 0xD8E8F0),
			biome(ESBiomes.ETHER_RIVER, 0xDCC8F0),
			biome(ESBiomes.STARLIT_SEA, 0x2E5FA3),
			biome(ESBiomes.ICY_SEA, 0xA8D8E8),
			biome(ESBiomes.SPIRAL_KELP_FOREST, 0x54B86B),
			biome(ESBiomes.LUSH_SHALLOW_SEA, 0x3FBF9F),
			biome(ESBiomes.THE_ABYSS, 0x4A4A6A),
			biome(ESBiomes.WARM_SHORE, 0xE8C878),
			biome(ESBiomes.GRIM_SHORE, 0x8E8E9E)
		));

		public static final StreamCodec<RegistryFriendlyByteBuf, TargetType> STREAM_CODEC = StreamCodec.of(
			(buf, type) -> buf.writeUtf(type.name),
			buf -> byName(buf.readUtf())
		);

		private final String name;
		private final Component label;
		private final int color;
		private final List<Entry> entries;

		TargetType(String name, int color, List<Entry> entries) {
			this.name = name;
			this.label = Component.translatable("gui." + EternalStarlight.ID + ".seeking_eye." + name);
			this.color = color;
			this.entries = entries;
		}

		public String getName() {
			return name;
		}

		public Component getLabel() {
			return label;
		}

		public int getColor() {
			return color;
		}

		public List<Entry> getEntries() {
			return entries;
		}

		private static TargetType byName(String name) {
			for (TargetType type : values()) {
				if (type.name.equals(name)) {
					return type;
				}
			}
			return BOSS_STRUCTURE;
		}

		public static Entry get(TargetType type, ResourceLocation id) {
			for (Entry entry : type.getEntries()) {
				if (entry.key().location().equals(id)) {
					return entry;
				}
			}
			return null;
		}
	}

	private static Entry structure(ResourceKey<Structure> key, int color) {
		return new Entry(key, Component.translatable("structure." + key.location().toLanguageKey()), color);
	}

	private static Entry biome(ResourceKey<Biome> key, int color) {
		return new Entry(key, Component.translatable("biome." + key.location().toLanguageKey()), color);
	}
}
