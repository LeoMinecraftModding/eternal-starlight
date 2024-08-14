package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.whisper.Whisper;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;

import java.util.Optional;

public class ESWhispers {
	public static final ResourceKey<Whisper> GATE_KEEPER = create("gate_keeper");

	public static void bootstrap(BootstrapContext<Whisper> context) {
		context.register(GATE_KEEPER, new Whisper(Component.translatable(from("gate_keeper")), Component.translatable(ctx("gate_keeper")), Optional.empty()));
	}

	public static ResourceKey<Whisper> create(String name) {
		return ResourceKey.create(ESRegistries.WHISPER, EternalStarlight.id(name));
	}

	private static String from(String name) {
		return "whisper.eternal_starlight.from." + name;
	}

	private static String ctx(String name) {
		return "whisper.eternal_starlight.ctx." + name;
	}
}
