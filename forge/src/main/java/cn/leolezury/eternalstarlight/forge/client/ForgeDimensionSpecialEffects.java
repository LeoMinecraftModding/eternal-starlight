package cn.leolezury.eternalstarlight.forge.client;

import cn.leolezury.eternalstarlight.common.client.ESDimensionSpecialEffects;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import org.joml.Matrix4f;

public class ForgeDimensionSpecialEffects extends ESDimensionSpecialEffects {
    public ForgeDimensionSpecialEffects(float cloudHeight, boolean placebo, SkyType fogType, boolean brightenLightMap, boolean entityLightingBottomsLit) {
        super(cloudHeight, placebo, fogType, brightenLightMap, entityLightingBottomsLit);
    }

    @Override
    public boolean renderSky(ClientLevel level, int ticks, float partialTick, PoseStack poseStack, Camera camera, Matrix4f projectionMatrix, boolean isFoggy, Runnable setupFog) {
        return doRenderSky(level, partialTick, poseStack, camera, projectionMatrix, isFoggy, setupFog);
    }
}
