package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.JukeboxSong;

public class ESJukeboxSongs {
	public static final ResourceKey<JukeboxSong> WHISPER_OF_THE_STARS = create("whisper_of_the_stars");
	public static final ResourceKey<JukeboxSong> DUSK_O_EREYESTERDAY = create("dusk_o_ereyesterday");
	public static final ResourceKey<JukeboxSong> TRANQUILITY = create("tranquility");
	public static final ResourceKey<JukeboxSong> POSTERITY = create("posterity");
	public static final ResourceKey<JukeboxSong> ATLANTIS = create("atlantis");
	public static final ResourceKey<JukeboxSong> SACRED_DESERT = create("sacred_desert");

	public static void bootstrap(BootstrapContext<JukeboxSong> context) {
		register(context, WHISPER_OF_THE_STARS, ESSoundEvents.MUSIC_DISC_WHISPER_OF_THE_STARS.asHolder(), 163, 14);
		register(context, DUSK_O_EREYESTERDAY, ESSoundEvents.MUSIC_DISC_DUSK_O_EREYESTERDAY.asHolder(), 362, 3);
		register(context, TRANQUILITY, ESSoundEvents.MUSIC_DISC_TRANQUILITY.asHolder(), 129, 12);
		register(context, POSTERITY, ESSoundEvents.MUSIC_DISC_POSTERITY.asHolder(), 238, 4);
		register(context, ATLANTIS, ESSoundEvents.MUSIC_DISC_ATLANTIS.asHolder(), 94, 5);
		register(context, SACRED_DESERT, ESSoundEvents.MUSIC_DISC_SACRED_DESERT.asHolder(), 105, 6);
	}

	private static void register(BootstrapContext<JukeboxSong> context, ResourceKey<JukeboxSong> key, Holder<SoundEvent> sound, int lengthInSeconds, int comparatorOutput) {
		context.register(key, new JukeboxSong(sound, Component.translatable(Util.makeDescriptionId("jukebox_song", key.location())), (float) lengthInSeconds, comparatorOutput));
	}

	public static ResourceKey<JukeboxSong> create(String name) {
		return ResourceKey.create(Registries.JUKEBOX_SONG, EternalStarlight.id(name));
	}
}
