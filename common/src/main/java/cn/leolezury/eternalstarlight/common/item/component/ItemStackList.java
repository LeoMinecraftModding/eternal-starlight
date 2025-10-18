package cn.leolezury.eternalstarlight.common.item.component;

import net.minecraft.world.item.ItemStack;

import java.util.AbstractList;
import java.util.List;

public class ItemStackList extends AbstractList<ItemStack> {
	private final List<ItemStack> list;

	public ItemStackList(List<ItemStack> list) {
		this.list = list;
	}

	@Override
	public ItemStack get(int index) {
		return this.list.get(index);
	}

	@Override
	public ItemStack set(int index, ItemStack value) {
		return this.list.set(index, value);
	}

	@Override
	public void add(int index, ItemStack value) {
		this.list.add(index, value);
	}

	@Override
	public ItemStack remove(int index) {
		return this.list.remove(index);
	}

	@Override
	public int size() {
		return this.list.size();
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		} else {
			return other instanceof ItemStackList stacks && ItemStack.listMatches(this.list, stacks.list);
		}
	}

	@Override
	public int hashCode() {
		return ItemStack.hashStackList(this.list);
	}
}
