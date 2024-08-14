package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.whisper.Whisper;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;

import java.util.Optional;

public class ESWhispers {
	public static final ResourceKey<Whisper> GATEKEEPER = create("gatekeeper");

	public static void bootstrap(BootstrapContext<Whisper> context) {
		context.register(GATEKEEPER, new Whisper(Component.translatable("whisper." + EternalStarlight.ID + ".gatekeeper"), Optional.empty()));
	}

	public static ResourceKey<Whisper> create(String name) {
		return ResourceKey.create(ESRegistries.WHISPER, EternalStarlight.id(name));
	}
}
