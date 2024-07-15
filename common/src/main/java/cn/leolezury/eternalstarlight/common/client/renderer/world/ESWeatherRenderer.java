package cn.leolezury.eternalstarlight.common.client.renderer.world;

import cn.leolezury.eternalstarlight.common.client.ClientWeatherInfo;
import cn.leolezury.eternalstarlight.common.client.handler.ClientHandlers;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class ESWeatherRenderer {
	private static float[] rainSizeX;
	private static float[] rainSizeZ;
	private static boolean initialized = false;

	private static void initialize() {
		if (!initialized) {
			rainSizeX = new float[1024];
			rainSizeZ = new float[1024];
			for (int i = 0; i < 32; ++i) {
				for (int j = 0; j < 32; ++j) {
					float f = (float) (j - 16);
					float g = (float) (i - 16);
					float h = Mth.sqrt(f * f + g * g);
					rainSizeX[i << 5 | j] = -g / h;
					rainSizeZ[i << 5 | j] = f / h;
				}
			}
			initialized = true;
		}
	}

	public static boolean renderCustomWeather(ClientLevel level, int ticks, float partialTick, LightTexture lightTexture, double camX, double camY, double camZ) {
		return ClientWeatherInfo.WEATHER != null && ClientWeatherInfo.WEATHER.renderWeather(level, ticks, partialTick, lightTexture, camX, camY, camZ);
	}

	public static void renderWeather(@Nullable ShaderInstance shader, LightTexture lightTexture, @Nullable Biome.Precipitation weatherType, ResourceLocation rainLocation, ResourceLocation snowLocation, float rainLevel, int ticks, boolean fullBright, float partialTicks, double camX, double camY, double camZ) {
		initialize();
		Minecraft minecraft = Minecraft.getInstance();
		float h = rainLevel;
		if (!(h <= 0.0F)) {
			lightTexture.turnOnLightLayer();
			Level level = minecraft.level;
			int i = Mth.floor(camX);
			int j = Mth.floor(camY);
			int k = Mth.floor(camZ);
			Tesselator tesselator = Tesselator.getInstance();
			BufferBuilder bufferBuilder = null;
			RenderSystem.disableCull();
			RenderSystem.enableBlend();
			RenderSystem.enableDepthTest();
			int l = 5;
			if (Minecraft.useFancyGraphics()) {
				l = 10;
			}

			RenderSystem.depthMask(Minecraft.useShaderTransparency());
			int m = -1;
			float n = (float) ticks + partialTicks;
			RenderSystem.setShader(GameRenderer::getParticleShader);
			if (shader != null) {
				RenderSystem.setShader(() -> shader);
			}
			BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

			for (int o = k - l; o <= k + l; ++o) {
				for (int p = i - l; p <= i + l; ++p) {
					int q = (o - k + 16) * 32 + p - i + 16;
					double r = (double) rainSizeX[q] * 0.5;
					double s = (double) rainSizeZ[q] * 0.5;
					mutableBlockPos.set(p, camY, o);
					Biome biome = level.getBiome(mutableBlockPos).value();
					if (biome.hasPrecipitation()) {
						int t = level.getHeight(Heightmap.Types.MOTION_BLOCKING, p, o);
						int u = j - l;
						int v = j + l;
						if (u < t) {
							u = t;
						}

						if (v < t) {
							v = t;
						}

						int w = t;
						if (w < j) {
							w = j;
						}

						if (u != v) {
							RandomSource randomSource = RandomSource.create((long) (p * p * 3121 + p * 45238971 ^ o * o * 418711 + o * 13761));
							mutableBlockPos.set(p, u, o);
							Biome.Precipitation precipitation = biome.getPrecipitationAt(mutableBlockPos);
							if (weatherType != null) {
								precipitation = weatherType;
							}
							float z;
							double ac;
							int ag;
							if (precipitation == Biome.Precipitation.RAIN) {
								if (m != 0) {
									if (m >= 0) {
										BufferUploader.drawWithShader(bufferBuilder.buildOrThrow());
									}

									m = 0;
									RenderSystem.setShaderTexture(0, rainLocation);
									bufferBuilder = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
								}

								int x = ticks & 131071;
								int y = p * p * 3121 + p * 45238971 + o * o * 418711 + o * 13761 & 255;
								z = 3.0F + randomSource.nextFloat();
								float aa = -((float) (x + y) + partialTicks) / 32.0F * z;
								float ab = aa % 32.0F;
								ac = (double) p + 0.5 - camX;
								double ad = (double) o + 0.5 - camZ;
								float ae = (float) Math.sqrt(ac * ac + ad * ad) / (float) l;
								float af = ((1.0F - ae * ae) * 0.5F + 0.5F) * h;
								mutableBlockPos.set(p, w, o);
								ag = fullBright ? ClientHandlers.FULL_BRIGHT : LevelRenderer.getLightColor(level, mutableBlockPos);
								bufferBuilder.addVertex((float) ((double) p - camX - r + 0.5), (float) ((double) v - camY), (float) ((double) o - camZ - s + 0.5)).setUv(0.0F, (float) u * 0.25F + ab).setColor(1.0F, 1.0F, 1.0F, af).setLight(ag);
								bufferBuilder.addVertex((float) ((double) p - camX + r + 0.5), (float) ((double) v - camY), (float) ((double) o - camZ + s + 0.5)).setUv(1.0F, (float) u * 0.25F + ab).setColor(1.0F, 1.0F, 1.0F, af).setLight(ag);
								bufferBuilder.addVertex((float) ((double) p - camX + r + 0.5), (float) ((double) u - camY), (float) ((double) o - camZ + s + 0.5)).setUv(1.0F, (float) v * 0.25F + ab).setColor(1.0F, 1.0F, 1.0F, af).setLight(ag);
								bufferBuilder.addVertex((float) ((double) p - camX - r + 0.5), (float) ((double) u - camY), (float) ((double) o - camZ - s + 0.5)).setUv(0.0F, (float) v * 0.25F + ab).setColor(1.0F, 1.0F, 1.0F, af).setLight(ag);
							} else if (precipitation == Biome.Precipitation.SNOW) {
								if (m != 1) {
									if (m >= 0) {
										BufferUploader.drawWithShader(bufferBuilder.buildOrThrow());
									}

									m = 1;
									RenderSystem.setShaderTexture(0, snowLocation);
									bufferBuilder = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
								}

								float ah = -((float) (ticks & 511) + partialTicks) / 512.0F;
								float ai = (float) (randomSource.nextDouble() + (double) n * 0.01 * (double) ((float) randomSource.nextGaussian()));
								z = (float) (randomSource.nextDouble() + (double) (n * (float) randomSource.nextGaussian()) * 0.001);
								double aj = (double) p + 0.5 - camX;
								ac = (double) o + 0.5 - camZ;
								float ak = (float) Math.sqrt(aj * aj + ac * ac) / (float) l;
								float al = ((1.0F - ak * ak) * 0.3F + 0.5F) * h;
								mutableBlockPos.set(p, w, o);
								int am = fullBright ? ClientHandlers.FULL_BRIGHT : LevelRenderer.getLightColor(level, mutableBlockPos);
								int an = am >> 16 & '\uffff';
								ag = am & '\uffff';
								int ao = (an * 3 + 240) / 4;
								int ap = (ag * 3 + 240) / 4;
								bufferBuilder.addVertex((float) ((double) p - camX - r + 0.5), (float) ((double) v - camY), (float) ((double) o - camZ - s + 0.5)).setUv(0.0F + ai, (float) u * 0.25F + ah + z).setColor(1.0F, 1.0F, 1.0F, al).setUv2(ap, ao);
								bufferBuilder.addVertex((float) ((double) p - camX + r + 0.5), (float) ((double) v - camY), (float) ((double) o - camZ + s + 0.5)).setUv(1.0F + ai, (float) u * 0.25F + ah + z).setColor(1.0F, 1.0F, 1.0F, al).setUv2(ap, ao);
								bufferBuilder.addVertex((float) ((double) p - camX + r + 0.5), (float) ((double) u - camY), (float) ((double) o - camZ + s + 0.5)).setUv(1.0F + ai, (float) v * 0.25F + ah + z).setColor(1.0F, 1.0F, 1.0F, al).setUv2(ap, ao);
								bufferBuilder.addVertex((float) ((double) p - camX - r + 0.5), (float) ((double) u - camY), (float) ((double) o - camZ - s + 0.5)).setUv(0.0F + ai, (float) v * 0.25F + ah + z).setColor(1.0F, 1.0F, 1.0F, al).setUv2(ap, ao);
							}
						}
					}
				}
			}

			if (m >= 0) {
				BufferUploader.drawWithShader(bufferBuilder.buildOrThrow());
			}

			RenderSystem.enableCull();
			RenderSystem.disableBlend();
			lightTexture.turnOffLightLayer();
		}
	}
}
