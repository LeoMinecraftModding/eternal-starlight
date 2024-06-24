package cn.leolezury.eternalstarlight.common.block;

import net.minecraft.world.level.block.SkullBlock;

public enum ESSkullType implements SkullBlock.Type {
	TANGLED("tangled");

	private final String name;

	ESSkullType(String name) {
		this.name = name;
	}

	@Override
	public String getSerializedName() {
		return name;
	}
}
