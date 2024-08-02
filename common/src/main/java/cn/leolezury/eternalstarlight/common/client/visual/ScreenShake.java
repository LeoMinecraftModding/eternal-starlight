package cn.leolezury.eternalstarlight.common.client.visual;

import cn.leolezury.eternalstarlight.common.util.Easing;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

@Environment(EnvType.CLIENT)
public class ScreenShake {
	private final ResourceLocation dimension;
	private final Vec3 pos;
	private final float radius;
	private final int duration;
	private final float horizontalPower;
	private final float verticalPower;
	private final float horizontalFreq;
	private final float verticalFreq;

	private Easing fadeEasing = Easing.IN_QUART;

	private int age;

	public ScreenShake(ResourceLocation dimension, Vec3 pos, float radius, int duration, float horizontalPower, float verticalPower, float horizontalFreq, float verticalFreq) {
		this.dimension = dimension;
		this.pos = pos;
		this.radius = radius;
		this.duration = duration;
		this.horizontalPower = horizontalPower;
		this.verticalPower = verticalPower;
		this.horizontalFreq = horizontalFreq;
		this.verticalFreq = verticalFreq;
	}

	public void setFadeEasing(Easing fadeEasing) {
		this.fadeEasing = fadeEasing;
	}

	public void tick() {
		age++;
	}

	public boolean shouldRemove() {
		return age > duration;
	}

	private float getPowerScale(float partialTicks) {
		float progress = Math.min((age + partialTicks) / duration, 1);
		return fadeEasing.interpolate(progress, 1, 0);
	}

	public float getYawOffset() {
		Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
		LocalPlayer player = Minecraft.getInstance().player;
		float partialTicks = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(Minecraft.getInstance().level != null && Minecraft.getInstance().level.tickRateManager().runsNormally());
		float time = player != null ? player.tickCount + partialTicks : 0;
		float distance = (float) pos.distanceTo(camera.getPosition());
		if (distance > radius || (player != null && !player.level().dimension().location().equals(dimension))) {
			return 0;
		}
		float powerScale = getPowerScale(partialTicks) * (1 - distance / radius);
		float power = horizontalPower * powerScale;
		return (float) (Math.sin(time * horizontalFreq) * power);
	}

	public float getPitchOffset() {
		Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
		LocalPlayer player = Minecraft.getInstance().player;
		float partialTicks = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(Minecraft.getInstance().level != null && Minecraft.getInstance().level.tickRateManager().runsNormally());
		float time = player != null ? player.tickCount + partialTicks : 0;
		float distance = (float) pos.distanceTo(camera.getPosition());
		if (distance > radius || (player != null && !player.level().dimension().location().equals(dimension))) {
			return 0;
		}
		float powerScale = getPowerScale(partialTicks) * (1 - distance / radius);
		float power = verticalPower * powerScale;
		return (float) (Math.sin(time * verticalFreq) * power);
	}
}
