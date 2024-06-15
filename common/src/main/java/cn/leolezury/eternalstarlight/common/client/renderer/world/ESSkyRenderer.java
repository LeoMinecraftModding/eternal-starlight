package cn.leolezury.eternalstarlight.common.client.renderer.world;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.ClientWeatherInfo;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

@Environment(EnvType.CLIENT)
public class ESSkyRenderer {
    private static final ResourceLocation DEAD_STAR_LOCATION = EternalStarlight.id("textures/environment/dead_star.png");
    private static VertexBuffer starBuffer;

    public static boolean renderSky(ClientLevel level, Matrix4f modelViewMatrix, Matrix4f matrix, float partialTicks, Camera camera, Runnable setupFog) {
        Minecraft minecraft = Minecraft.getInstance();
        LevelRenderer levelRenderer = minecraft.levelRenderer;
        setupFog.run();

        if (starBuffer == null) {
            createStars();
        }

        FogType fogType = camera.getFluidInCamera();
        if (fogType != FogType.POWDER_SNOW && fogType != FogType.LAVA && !levelRenderer.doesMobEffectBlockSky(camera)) {
            PoseStack poseStack = new PoseStack();
            poseStack.mulPose(modelViewMatrix);

            Vec3 vec3 = level.getSkyColor(minecraft.gameRenderer.getMainCamera().getPosition(), partialTicks);
            float g = (float)vec3.x;
            float h = (float)vec3.y;
            float i = (float)vec3.z;
            FogRenderer.levelFogColor();
            Tesselator tesselator = Tesselator.getInstance();
            RenderSystem.depthMask(false);
            RenderSystem.setShaderColor(g, h, i, 1.0F);
            ShaderInstance shaderInstance = RenderSystem.getShader();
            levelRenderer.skyBuffer.bind();
            levelRenderer.skyBuffer.drawWithShader(poseStack.last().pose(), matrix, shaderInstance);
            VertexBuffer.unbind();
            RenderSystem.enableBlend();
            float[] fs = level.effects().getSunriseColor(level.getTimeOfDay(partialTicks), partialTicks);
            float j;
            float l;
            float p;
            float q;
            float r;
            if (fs != null) {
                RenderSystem.setShader(GameRenderer::getPositionColorShader);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                poseStack.pushPose();
                poseStack.mulPose(com.mojang.math.Axis.XP.rotationDegrees(90.0F));
                j = Mth.sin(level.getSunAngle(partialTicks)) < 0.0F ? 180.0F : 0.0F;
                poseStack.mulPose(com.mojang.math.Axis.ZP.rotationDegrees(j));
                poseStack.mulPose(com.mojang.math.Axis.ZP.rotationDegrees(90.0F));
                float k = fs[0];
                l = fs[1];
                float m = fs[2];
                Matrix4f matrix4f3 = poseStack.last().pose();
                BufferBuilder bufferBuilder = tesselator.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);
                bufferBuilder.addVertex(matrix4f3, 0.0F, 100.0F, 0.0F).setColor(k, l, m, fs[3]);

                for(int o = 0; o <= 16; ++o) {
                    p = (float)o * 6.2831855F / 16.0F;
                    q = Mth.sin(p);
                    r = Mth.cos(p);
                    bufferBuilder.addVertex(matrix4f3, q * 120.0F, r * 120.0F, -r * 40.0F * fs[3]).setColor(fs[0], fs[1], fs[2], 0.0F);
                }

                BufferUploader.drawWithShader(bufferBuilder.buildOrThrow());
                poseStack.popPose();
            }

            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            poseStack.pushPose();

            float rainLevel = level.getRainLevel(partialTicks);
            if (ClientWeatherInfo.WEATHER != null) {
                rainLevel = ClientWeatherInfo.WEATHER.modifyRainLevel(rainLevel);
            } else {
                ClientWeatherInfo.LEVEL_TARGET = rainLevel;
                rainLevel = ClientWeatherInfo.getRainLevel(partialTicks);
            }

            j = 1.0F - rainLevel;
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, j);
            poseStack.mulPose(com.mojang.math.Axis.YP.rotationDegrees(-90.0F));
            poseStack.mulPose(com.mojang.math.Axis.XP.rotationDegrees(level.getTimeOfDay(partialTicks) * 360.0F));
            Matrix4f matrix4f4 = poseStack.last().pose();
            l = 60.0F;
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, DEAD_STAR_LOCATION);
            BufferBuilder bufferBuilder2 = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
            bufferBuilder2.addVertex(matrix4f4, -l, 100.0F, -l).setUv(0.0F, 0.0F);
            bufferBuilder2.addVertex(matrix4f4, l, 100.0F, -l).setUv(1.0F, 0.0F);
            bufferBuilder2.addVertex(matrix4f4, l, 100.0F, l).setUv(1.0F, 1.0F);
            bufferBuilder2.addVertex(matrix4f4, -l, 100.0F, l).setUv(0.0F, 1.0F);
            BufferUploader.drawWithShader(bufferBuilder2.buildOrThrow());

            float v = 1.0f/*level.getStarBrightness(partialTicks) * j*/;
            RenderSystem.setShaderColor(v, v, v, v);
            FogRenderer.setupNoFog();
            starBuffer.bind();
            starBuffer.drawWithShader(poseStack.last().pose(), matrix, GameRenderer.getPositionShader());
            VertexBuffer.unbind();
            setupFog.run();

            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.disableBlend();
            RenderSystem.defaultBlendFunc();
            poseStack.popPose();
            RenderSystem.setShaderColor(0.0F, 0.0F, 0.0F, 1.0F);
            double d = minecraft.player.getEyePosition(partialTicks).y - level.getLevelData().getHorizonHeight(level);
            if (d < 0.0) {
                poseStack.pushPose();
                poseStack.translate(0.0F, 12.0F, 0.0F);
                levelRenderer.darkBuffer.bind();
                levelRenderer.darkBuffer.drawWithShader(poseStack.last().pose(), matrix, shaderInstance);
                VertexBuffer.unbind();
                poseStack.popPose();
            }

            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.depthMask(true);
        }

        return true;
    }

    private static void createStars() {
        if (starBuffer != null) {
            starBuffer.close();
        }

        starBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
        starBuffer.bind();
        starBuffer.upload(drawStars(Tesselator.getInstance()));
        VertexBuffer.unbind();
    }

    private static MeshData drawStars(Tesselator tesselator) {
        RandomSource randomSource = RandomSource.create(10842L);
        float f = 100.0F;
        BufferBuilder bufferBuilder = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);

        for(int j = 0; j < 3000; ++j) {
            float g = randomSource.nextFloat() * 2.0F - 1.0F;
            float h = randomSource.nextFloat() * 2.0F - 1.0F;
            float k = randomSource.nextFloat() * 2.0F - 1.0F;
            float l = 0.15F + randomSource.nextFloat() * 0.1F;
            float m = Mth.lengthSquared(g, h, k);
            if (!(m <= 0.010000001F) && !(m >= 1.0F)) {
                Vector3f vector3f = (new Vector3f(g, h, k)).normalize(100.0F);
                float n = (float)(randomSource.nextDouble() * 3.1415927410125732 * 2.0);
                Quaternionf quaternionf = (new Quaternionf()).rotateTo(new Vector3f(0.0F, 0.0F, -1.0F), vector3f).rotateZ(n);
                bufferBuilder.addVertex(vector3f.add((new Vector3f(l, -l, 0.0F)).rotate(quaternionf)));
                bufferBuilder.addVertex(vector3f.add((new Vector3f(l, l, 0.0F)).rotate(quaternionf)));
                bufferBuilder.addVertex(vector3f.add((new Vector3f(-l, l, 0.0F)).rotate(quaternionf)));
                bufferBuilder.addVertex(vector3f.add((new Vector3f(-l, -l, 0.0F)).rotate(quaternionf)));
            }
        }

        return bufferBuilder.buildOrThrow();
    }
}
