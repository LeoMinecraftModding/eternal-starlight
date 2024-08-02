package cn.leolezury.eternalstarlight.common.vfx;

import cn.leolezury.eternalstarlight.common.client.handler.ClientHandlers;
import cn.leolezury.eternalstarlight.common.client.visual.ScreenShake;
import cn.leolezury.eternalstarlight.common.util.ESMiscUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ScreenShakeVfx implements SyncedVfxType {
	private static final String TAG_DIMENSION = "dimension";
	private static final String TAG_X = "x";
	private static final String TAG_Y = "y";
	private static final String TAG_Z = "z";
	private static final String TAG_RADIUS = "radius";
	private static final String TAG_DURATION = "duration";
	private static final String TAG_HORIZONTAL_POWER = "horizontal_power";
	private static final String TAG_VERTICAL_POWER = "vertical_power";
	private static final String TAG_HORIZONTAL_FREQUENCY = "horizontal_frequency";
	private static final String TAG_VERTICAL_FREQUENCY = "vertical_frequency";

	@Override
	public void spawnOnClient(CompoundTag tag) {
		ESMiscUtil.runWhenOnClient(() -> () -> ClientHandlers.SCREEN_SHAKES.add(new ScreenShake(
			ResourceLocation.parse(tag.getString(TAG_DIMENSION)),
			new Vec3(tag.getDouble(TAG_X), tag.getDouble(TAG_Y), tag.getDouble(TAG_Z)),
			tag.getFloat(TAG_RADIUS),
			tag.getInt(TAG_DURATION),
			tag.getFloat(TAG_HORIZONTAL_POWER),
			tag.getFloat(TAG_VERTICAL_POWER),
			tag.getFloat(TAG_HORIZONTAL_FREQUENCY),
			tag.getFloat(TAG_VERTICAL_FREQUENCY)
		)));
	}

	public static VfxInstance createInstance(ResourceKey<Level> dimension, Vec3 pos, float radius, int duration, float horizontalPower, float verticalPower, float horizontalFreq, float verticalFreq) {
		CompoundTag tag = new CompoundTag();
		tag.putString(TAG_DIMENSION, dimension.location().toString());
		tag.putDouble(TAG_X, pos.x());
		tag.putDouble(TAG_Y, pos.y());
		tag.putDouble(TAG_Z, pos.z());
		tag.putFloat(TAG_RADIUS, radius);
		tag.putInt(TAG_DURATION, duration);
		tag.putFloat(TAG_HORIZONTAL_POWER, horizontalPower);
		tag.putFloat(TAG_VERTICAL_POWER, verticalPower);
		tag.putFloat(TAG_HORIZONTAL_FREQUENCY, horizontalFreq);
		tag.putFloat(TAG_VERTICAL_FREQUENCY, verticalFreq);
		return new VfxInstance(VfxRegistry.SCREEN_SHAKE, tag);
	}
}
