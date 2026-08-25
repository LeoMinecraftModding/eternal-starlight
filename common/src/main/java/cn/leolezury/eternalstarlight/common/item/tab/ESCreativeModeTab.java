package cn.leolezury.eternalstarlight.common.item.tab;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackLinkedSet;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class ESCreativeModeTab {
	private static final RegistrationProvider<CreativeModeTab> TABS = RegistrationProvider.get(Registries.CREATIVE_MODE_TAB, EternalStarlight.ID);
	private static final Map<CreativeModeTab, ESCreativeModeTab> BY_TAB = new IdentityHashMap<>();

	public record Section(Supplier<ItemStack> icon, Component name, ContentSupplier content) {
		@FunctionalInterface
		public interface ContentSupplier {
			void addItems(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output output);
		}
	}

	private final CreativeModeTab tab;
	private final List<Section> sections = new ArrayList<>();
	private final Set<Integer> activeSections = new TreeSet<>();

	private ESCreativeModeTab(ResourceLocation id, Supplier<ItemStack> icon, Consumer<ESCreativeModeTab> initializer) {
		this.tab = ESPlatform.INSTANCE.getTabBuilder()
			.icon(icon)
			.title(Component.translatable("tab." + id.getNamespace() + "." + id.getPath()))
			.displayItems(this::generateDisplayItems)
			.build();
		BY_TAB.put(this.tab, this);
		initializer.accept(this);
		TABS.register(id.getPath(), () -> this.tab);
	}

	public static ESCreativeModeTab create(ResourceLocation id, Supplier<ItemStack> icon, Consumer<ESCreativeModeTab> modifier) {
		return new ESCreativeModeTab(id, icon, modifier);
	}

	public static ESCreativeModeTab byTab(CreativeModeTab tab) {
		return BY_TAB.get(tab);
	}

	public CreativeModeTab tab() {
		return this.tab;
	}

	public void addSection(Section section) {
		this.sections.add(section);
		this.activeSections.add(this.sections.size() - 1);
	}

	public Section getSection(int index) {
		return this.sections.get(index);
	}

	public int getSectionCount() {
		return this.sections.size();
	}

	public boolean isSectionActive(int index) {
		return this.activeSections.contains(index);
	}

	public Set<Integer> activeSections() {
		return this.activeSections;
	}

	public void selectSection(int index) {
		this.activeSections.clear();
		this.activeSections.add(index);
	}

	public void toggleSection(int index) {
		if (this.activeSections.contains(index)) {
			if (this.activeSections.size() > 1) {
				this.activeSections.remove(index);
			}
		} else {
			this.activeSections.add(index);
		}
	}

	private void generateDisplayItems(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output output) {
		Set<ItemStack> accepted = ItemStackLinkedSet.createTypeAndComponentsSet();
		CreativeModeTab.Output dedupedOutput = (stack, visibility) -> {
			if (accepted.add(stack)) {
				output.accept(stack, visibility);
			}
		};
		for (int index = 0; index < this.sections.size(); index++) {
			if (this.activeSections.contains(index)) {
				this.sections.get(index).content().addItems(parameters, dedupedOutput);
			} else {
				this.sections.get(index).content().addItems(parameters, (stack, visibility) -> dedupedOutput.accept(stack, CreativeModeTab.TabVisibility.SEARCH_TAB_ONLY));
			}
		}
	}
}
