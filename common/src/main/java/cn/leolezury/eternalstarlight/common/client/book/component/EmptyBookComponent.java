package cn.leolezury.eternalstarlight.common.client.book.component;

import cn.leolezury.eternalstarlight.common.client.book.BookContext;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

import java.util.HashSet;

@Environment(EnvType.CLIENT)
public class EmptyBookComponent extends BookComponent<EmptyBookComponent.Config> {
	public EmptyBookComponent() {
		super(Config.CODEC);
	}

	@Override
	public int getTotalHeight(Config config, BookContext context) {
		return config.totalHeight();
	}

	@Override
	public void render(Config config, BookContext context, GuiGraphics graphics, int x, int y) {

	}

	public record Config(ResourceLocation id, HashSet<HashSet<ResourceLocation>> unlockConditions, int totalHeight) implements BookComponentConfig {
		public static final Codec<Config> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			ResourceLocation.CODEC.fieldOf("id").forGetter(Config::id),
			ResourceLocation.CODEC.listOf().xmap(Sets::newHashSet, Lists::newArrayList).listOf().xmap(Sets::newHashSet, Lists::newArrayList).fieldOf("unlock_conditions").forGetter(Config::unlockConditions),
			Codec.INT.fieldOf("total_height").forGetter(Config::totalHeight)
		).apply(instance, Config::new));
	}
}
