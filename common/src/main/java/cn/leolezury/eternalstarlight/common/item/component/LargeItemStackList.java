package cn.leolezury.eternalstarlight.common.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

import java.util.AbstractList;
import java.util.List;

public class LargeItemStackList extends AbstractList<LargeItemStackList.LargeItemStack> {
	private final List<LargeItemStack> list;

	public LargeItemStackList(List<LargeItemStack> list) {
		this.list = list;
	}

	@Override
	public LargeItemStack get(int index) {
		return this.list.get(index);
	}

	@Override
	public LargeItemStack set(int index, LargeItemStack value) {
		return this.list.set(index, value);
	}

	@Override
	public void add(int index, LargeItemStack value) {
		this.list.add(index, value);
	}

	@Override
	public LargeItemStack remove(int index) {
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
			if (other instanceof LargeItemStackList stacks) {
				if (stacks.size() != size()) {
					return false;
				} else {
					for (int i = 0; i < stacks.size(); ++i) {
						if (!ItemStack.matches(stacks.get(i).getItem(), get(i).getItem()) || stacks.get(i).getCount() != get(i).getCount()) {
							return false;
						}
					}
					return true;
				}
			} else {
				return false;
			}
		}
	}

	public static class LargeItemStack {
		private final ItemStack item;

		public ItemStack getItem() {
			return item;
		}

		private int count;

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}

		public static final Codec<LargeItemStack> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			ItemStack.SINGLE_ITEM_CODEC.fieldOf("item").forGetter(LargeItemStack::getItem),
			Codec.INT.fieldOf("count").forGetter(LargeItemStack::getCount)
		).apply(instance, LargeItemStack::new));

		public static final StreamCodec<RegistryFriendlyByteBuf, LargeItemStack> STREAM_CODEC = StreamCodec.composite(
			ItemStack.STREAM_CODEC, LargeItemStack::getItem,
			ByteBufCodecs.INT, LargeItemStack::getCount,
			LargeItemStack::new
		);

		public LargeItemStack(ItemStack stack) {
			this(stack.copyWithCount(1), stack.getCount());
		}

		public LargeItemStack(ItemStack item, int count) {
			this.item = item;
			this.count = count;
		}

		public boolean isEmpty() {
			return item.isEmpty() || count == 0;
		}

		public void grow(int amount) {
			setCount(getCount() + amount);
		}

		public void shrink(int amount) {
			grow(-amount);
		}

		public ItemStack split(int splitAmount) {
			int splitCount = Math.min(splitAmount, count);
			ItemStack stack = item.copyWithCount(splitCount);
			count -= splitCount;
			return stack;
		}

		public ItemStack splitMaxStack() {
			return split(item.getMaxStackSize());
		}

		public ItemStack asItemStack() {
			return item.copyWithCount(count);
		}
	}
}
