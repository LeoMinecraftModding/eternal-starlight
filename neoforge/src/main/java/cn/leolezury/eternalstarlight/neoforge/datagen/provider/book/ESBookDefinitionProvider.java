package cn.leolezury.eternalstarlight.neoforge.datagen.provider.book;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.book.BookDefinition;
import cn.leolezury.eternalstarlight.common.client.book.component.*;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.joml.Quaternionf;

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ESBookDefinitionProvider extends BookDefinitionProvider {
	public ESBookDefinitionProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, EternalStarlight.ID, existingFileHelper);
	}

	@Override
	protected void gather(HolderLookup.Provider provider) {
		BookDefinition main = new BookDefinition(List.of(
			new ConfiguredBookComponent<>(BookComponentRegistry.INDEX, new IndexBookComponent.Config(EternalStarlight.id("test_index"), new HashSet<>(), List.of(
				new IndexBookComponent.Entry(Component.literal("Test One").withColor(0xacfffc), EternalStarlight.id("test1"), new HashSet<>(), 24, 24, EternalStarlight.id("textures/gui/screen/book/chapter_frame.png"), Util.make(() -> {
					ItemStack stack = ESItems.SEEKING_EYE.get().getDefaultInstance();
					return (CompoundTag) stack.save(provider);
				})),
				new IndexBookComponent.Entry(Component.literal("Test Two").withColor(0xacfffc), EternalStarlight.id("test2"), new HashSet<>(), 24, 24, EternalStarlight.id("textures/gui/screen/book/chapter_frame.png"), Util.make(() -> {
					ItemStack stack = ESItems.FLOWGLAZE_AXE.get().getDefaultInstance();
					return (CompoundTag) stack.save(provider);
				})),
				new IndexBookComponent.Entry(Component.literal("Test Three Long Text Long Text Long Text Long Text Long Text Long Text Long Text Long Text Long Text").withColor(0xacfffc), EternalStarlight.id("test3"), new HashSet<>(), 24, 24, EternalStarlight.id("textures/gui/screen/book/chapter_frame.png"), Util.make(() -> {
					ItemStack stack = ESItems.ABYSSAL_MAGMA_BLOCK.get().getDefaultInstance();
					return (CompoundTag) stack.save(provider);
				}))
			), 130, 12)),
			new ConfiguredBookComponent<>(BookComponentRegistry.TEXT, new TextBookComponent.Config(EternalStarlight.id("test1"), new HashSet<>(), Component.translatable("book." + EternalStarlight.ID + ".main_story").withColor(0xacfffc), 130, 12)),
			new ConfiguredBookComponent<>(BookComponentRegistry.TEXT, new TextBookComponent.Config(EternalStarlight.id("test2"), new HashSet<>(), Component.translatable("book." + EternalStarlight.ID + ".freeze").withColor(0xacfffc), 130, 12)),
			new ConfiguredBookComponent<>(BookComponentRegistry.DISPLAY, new DisplayBookComponent.Config(EternalStarlight.id("test3"), new HashSet<>(), 200)
				.textDisplay(Component.literal("SampleText").withStyle(ChatFormatting.STRIKETHROUGH), 65, 80, 2)
				.imageDisplay(EternalStarlight.id("textures/gui/screen/book/slot.png"), 0, 0, 20, 20)
				.imageDisplay(EternalStarlight.id("textures/gui/screen/book/slot.png"), 20, 20, 20, 20)
				.itemDisplay(Util.make(() -> {
					ItemStack stack = ESItems.STARFIRE.get().getDefaultInstance();
					return (CompoundTag) stack.save(provider);
				}), 22, 22)
				.itemTagDisplay(ESTags.Items.GOLEM_FORGE_LOCATORS, 2, 2)
				.entityDisplay(Util.make(() -> {
					CompoundTag compoundTag = new CompoundTag();
					compoundTag.putString(Entity.ID_TAG, ESEntities.FREEZE.getId().toString());
					return compoundTag;
				}), 65, 65, -25, 210, 30, new Quaternionf().rotationXYZ(0.43633232F, 0.0F, 3.1415927F)))
		), 150, 187, 10,
			4, 4, 2, 174, 6,
			4, 170, 140, 5, 2, FastColor.ARGB32.color(172, 255, 252),
			new BookDefinition.Textures(EternalStarlight.id("textures/gui/screen/book/book.png"),
				EternalStarlight.id("textures/gui/screen/book/book_overlay.png"),
				EternalStarlight.id("textures/gui/screen/book/up.png"),
				EternalStarlight.id("textures/gui/screen/book/down.png")));
		add(EternalStarlight.id("main"), main);
	}
}
