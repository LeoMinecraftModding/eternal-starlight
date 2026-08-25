package cn.leolezury.eternalstarlight.common.command;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.posteffect.PostEffectData;
import cn.leolezury.eternalstarlight.common.posteffect.PostEffectType;
import cn.leolezury.eternalstarlight.common.registry.ESPostEffects;
import com.google.gson.JsonObject;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class PostEffectArgument implements ArgumentType<PostEffectData> {
	private static final Collection<String> EXAMPLES = Arrays.asList("shockwave", "shockwave{frequency:2.0}");
	public static final DynamicCommandExceptionType ERROR_UNKNOWN_EFFECT = new DynamicCommandExceptionType(id -> Component.translatableEscape("commands." + EternalStarlight.ID + ".posteffect.not_found", id));
	public static final DynamicCommandExceptionType ERROR_INVALID_DATA = new DynamicCommandExceptionType(data -> Component.translatableEscape("commands." + EternalStarlight.ID + ".posteffect.invalid_data", data));

	public static PostEffectArgument postEffect() {
		return new PostEffectArgument();
	}

	public static PostEffectData getPostEffect(CommandContext<?> context, String name) {
		return context.getArgument(name, PostEffectData.class);
	}

	@Override
	public PostEffectData parse(StringReader reader) throws CommandSyntaxException {
		return readPostEffect(reader);
	}

	public static PostEffectData readPostEffect(StringReader reader) throws CommandSyntaxException {
		ResourceLocation id = ResourceLocation.read(reader);
		PostEffectType<?> type = ESPostEffects.get(id);
		if (type == null && id.getNamespace().equals(ResourceLocation.DEFAULT_NAMESPACE)) {
			type = ESPostEffects.get(EternalStarlight.id(id.getPath()));
		}
		if (type == null) {
			throw ERROR_UNKNOWN_EFFECT.createWithContext(reader, id);
		}
		CompoundTag tag = reader.canRead() && reader.peek() == '{' ? new TagParser(reader).readStruct() : new CompoundTag();
		return readPostEffect(tag, type, reader);
	}

	private static <T extends PostEffectData> T readPostEffect(CompoundTag tag, PostEffectType<T> type, StringReader reader) throws CommandSyntaxException {
		return type.codec().codec().parse(NbtOps.INSTANCE, tag).getOrThrow(ERROR_INVALID_DATA::create);
	}

	@Override
	public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
		return SharedSuggestionProvider.suggestResource(ESPostEffects.keys(), builder);
	}

	@Override
	public Collection<String> getExamples() {
		return EXAMPLES;
	}

	public static class Info implements ArgumentTypeInfo<PostEffectArgument, Info.Template> {
		@Override
		public void serializeToNetwork(Info.Template template, FriendlyByteBuf buffer) {
		}

		@Override
		public Info.Template deserializeFromNetwork(FriendlyByteBuf buffer) {
			return new Template();
		}

		@Override
		public void serializeToJson(Info.Template template, JsonObject json) {
		}

		@Override
		public Info.Template unpack(PostEffectArgument argument) {
			return new Template();
		}

		public final class Template implements ArgumentTypeInfo.Template<PostEffectArgument> {
			@Override
			public PostEffectArgument instantiate(CommandBuildContext context) {
				return postEffect();
			}

			@Override
			public ArgumentTypeInfo<PostEffectArgument, ?> type() {
				return Info.this;
			}
		}
	}
}
