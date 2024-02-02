package cn.leolezury.eternalstarlight.common.client.renderer.world;

import cn.leolezury.eternalstarlight.common.client.ClientWeatherInfo;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap;

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
                    float f = (float)(j - 16);
                    float g = (float)(i - 16);
                    float h = Mth.sqrt(f * f + g * g);
                    rainSizeX[i << 5 | j] = -g / h;
                    rainSizeZ[i << 5 | j] = f / h;
                }
            }
            initialized = true;
        }
    }

    public static boolean renderCustomWeather(ClientLevel level, int ticks, float partialTick, LightTexture lightTexture, double camX, double camY, double camZ) {
        return ClientWeatherInfo.weather != null && ClientWeatherInfo.weather.renderWeather(level, ticks, partialTick, lightTexture, camX, camY, camZ);
    }

    public static void renderWeather(LightTexture lightTexture, Biome.Precipitation weatherType, ResourceLocation rainLocation, ResourceLocation snowLocation, float rainLevel, int ticks, boolean fullBright, float partialTicks, double camX, double camY, double camZ) {
        initialize();
        Minecraft minecraft = Minecraft.getInstance();
        if (!(rainLevel <= 0.0F)) {
            lightTexture.turnOnLightLayer();
            Level level = minecraft.level;
            int i = Mth.floor(camX);
            int j = Mth.floor(camY);
            int k = Mth.floor(camZ);
            Tesselator tesselator = Tesselator.getInstance();
            BufferBuilder bufferBuilder = tesselator.getBuilder();
            RenderSystem.disableCull();
            RenderSystem.enableBlend();
            RenderSystem.enableDepthTest();
            int l = 5;
            if (Minecraft.useFancyGraphics()) {
                l = 10;
            }

            RenderSystem.depthMask(Minecraft.useShaderTransparency());
            int m = -1;
            float n = (float)ticks + partialTicks;
            RenderSystem.setShader(GameRenderer::getParticleShader);
            BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

            for(int o = k - l; o <= k + l; ++o) {
                for(int p = i - l; p <= i + l; ++p) {
                    int q = (o - k + 16) * 32 + p - i + 16;
                    double r = (double)rainSizeX[q] * 0.5;
                    double s = (double)rainSizeZ[q] * 0.5;
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
                        if (t < j) {
                            w = j;
                        }

                        if (u != v) {
                            RandomSource randomSource = RandomSource.create((long) p * p * 3121 + p * 45238971L ^ (long) o * o * 418711 + o * 13761L);
                            mutableBlockPos.set(p, u, o);
                            Biome.Precipitation precipitation = weatherType == null ? biome.getPrecipitationAt(mutableBlockPos) : weatherType;
                            float z;
                            double ac;
                            int ag;
                            if (precipitation == Biome.Precipitation.RAIN) {
                                if (m != 0) {
                                    if (m >= 0) {
                                        tesselator.end();
                                    }

                                    m = 0;
                                    RenderSystem.setShaderTexture(0, rainLocation);
                                    bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
                                }

                                int x = ticks & 131071;
                                int y = p * p * 3121 + p * 45238971 + o * o * 418711 + o * 13761 & 255;
                                z = 3.0F + randomSource.nextFloat();
                                float aa = -((float)(x + y) + partialTicks) / 32.0F * z;
                                float ab = aa % 32.0F;
                                ac = (double)p + 0.5 - camX;
                                double ad = (double)o + 0.5 - camZ;
                                float ae = (float)Math.sqrt(ac * ac + ad * ad) / (float)l;
                                float af = ((1.0F - ae * ae) * 0.5F + 0.5F) * rainLevel;
                                mutableBlockPos.set(p, w, o);
                                ag = fullBright ? 0xF000F0 : LevelRenderer.getLightColor(level, mutableBlockPos);
                                bufferBuilder.vertex((double)p - camX - r + 0.5, (double)v - camY, (double)o - camZ - s + 0.5).uv(0.0F, (float)u * 0.25F + ab).color(1.0F, 1.0F, 1.0F, af).uv2(ag).endVertex();
                                bufferBuilder.vertex((double)p - camX + r + 0.5, (double)v - camY, (double)o - camZ + s + 0.5).uv(1.0F, (float)u * 0.25F + ab).color(1.0F, 1.0F, 1.0F, af).uv2(ag).endVertex();
                                bufferBuilder.vertex((double)p - camX + r + 0.5, (double)u - camY, (double)o - camZ + s + 0.5).uv(1.0F, (float)v * 0.25F + ab).color(1.0F, 1.0F, 1.0F, af).uv2(ag).endVertex();
                                bufferBuilder.vertex((double)p - camX - r + 0.5, (double)u - camY, (double)o - camZ - s + 0.5).uv(0.0F, (float)v * 0.25F + ab).color(1.0F, 1.0F, 1.0F, af).uv2(ag).endVertex();
                            } else if (precipitation == Biome.Precipitation.SNOW) {
                                if (m != 1) {
                                    if (m >= 0) {
                                        tesselator.end();
                                    }

                                    m = 1;
                                    RenderSystem.setShaderTexture(0, snowLocation);
                                    bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
                                }

                                float ah = -((float)(ticks & 511) + partialTicks) / 512.0F;
                                float ai = (float)(randomSource.nextDouble() + (double)n * 0.01 * (double)((float)randomSource.nextGaussian()));
                                z = (float)(randomSource.nextDouble() + (double)(n * (float)randomSource.nextGaussian()) * 0.001);
                                double aj = (double)p + 0.5 - camX;
                                ac = (double)o + 0.5 - camZ;
                                float ak = (float)Math.sqrt(aj * aj + ac * ac) / (float)l;
                                float al = ((1.0F - ak * ak) * 0.3F + 0.5F) * rainLevel;
                                mutableBlockPos.set(p, w, o);
                                int am = fullBright ? 0xF000F0 : LevelRenderer.getLightColor(level, mutableBlockPos);
                                int an = am >> 16 & '\uffff';
                                ag = am & '\uffff';
                                int ao = (an * 3 + 240) / 4;
                                int ap = (ag * 3 + 240) / 4;
                                bufferBuilder.vertex((double)p - camX - r + 0.5, (double)v - camY, (double)o - camZ - s + 0.5).uv(0.0F + ai, (float)u * 0.25F + ah + z).color(1.0F, 1.0F, 1.0F, al).uv2(ap, ao).endVertex();
                                bufferBuilder.vertex((double)p - camX + r + 0.5, (double)v - camY, (double)o - camZ + s + 0.5).uv(1.0F + ai, (float)u * 0.25F + ah + z).color(1.0F, 1.0F, 1.0F, al).uv2(ap, ao).endVertex();
                                bufferBuilder.vertex((double)p - camX + r + 0.5, (double)u - camY, (double)o - camZ + s + 0.5).uv(1.0F + ai, (float)v * 0.25F + ah + z).color(1.0F, 1.0F, 1.0F, al).uv2(ap, ao).endVertex();
                                bufferBuilder.vertex((double)p - camX - r + 0.5, (double)u - camY, (double)o - camZ - s + 0.5).uv(0.0F + ai, (float)v * 0.25F + ah + z).color(1.0F, 1.0F, 1.0F, al).uv2(ap, ao).endVertex();
                            }
                        }
                    }
                }
            }

            if (m >= 0) {
                tesselator.end();
            }

            RenderSystem.enableCull();
            RenderSystem.disableBlend();
            lightTexture.turnOffLightLayer();
        }
    }
}
