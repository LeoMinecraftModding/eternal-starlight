package cn.leolezury.eternalstarlight.common.block;

import net.minecraft.util.StringRepresentable;

public enum SpeleothemThickness implements StringRepresentable {
	TIP("tip"),
	FRUSTUM("frustum"),
	MIDDLE("middle"),
	BASE("base"),
	TIP_MERGE("tip_merge");

	private final String name;

	SpeleothemThickness(final String name) {
		this.name = name;
	}

	@Override
	public String getSerializedName() {
		return this.name;
	}
}
