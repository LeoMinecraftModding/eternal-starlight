package cn.leolezury.eternalstarlight.common.vfx;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.spell.ManaType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.phys.Vec3;

import java.util.Arrays;

public class ManaCrystalParticleVfx implements SyncedVfxType {
	private static final String TAG_TYPE = "type";
	private static final String TAG_X = "x";
	private static final String TAG_Y = "y";
	private static final String TAG_Z = "z";

	@Override
	public void spawnOnClient(CompoundTag tag) {
		String type = tag.getString(TAG_TYPE);
		Vec3 pos = new Vec3(tag.getDouble(TAG_X), tag.getDouble(TAG_Y), tag.getDouble(TAG_Z));
		Arrays.stream(ManaType.values()).forEach(t -> {
			if (t.getSerializedName().equals(type)) {
				EternalStarlight.getClientHelper().spawnManaCrystalItemParticles(t, pos);
			}
		});
	}

	public static VfxInstance createInstance(ManaType type, Vec3 pos) {
		CompoundTag tag = new CompoundTag();
		tag.putString(TAG_TYPE, type.getSerializedName());
		tag.putDouble(TAG_X, pos.x());
		tag.putDouble(TAG_Y, pos.y());
		tag.putDouble(TAG_Z, pos.z());
		return new VfxInstance(VfxRegistry.MANA_CRYSTAL_PARTICLE, tag);
	}
}
