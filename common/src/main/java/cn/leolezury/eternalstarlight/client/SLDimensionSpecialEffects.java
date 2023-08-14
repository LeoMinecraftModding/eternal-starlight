package cn.leolezury.eternalstarlight.client;

import cn.leolezury.eternalstarlight.client.renderer.world.SLSkyRenderer;
import cn.leolezury.eternalstarlight.util.SLTags;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public class SLDimensionSpecialEffects extends DimensionSpecialEffects {
    public SLDimensionSpecialEffects(float cloudHeight, boolean placebo, SkyType fogType, boolean brightenLightMap, boolean entityLightingBottomsLit) {
        super(cloudHeight, placebo, fogType, brightenLightMap, entityLightingBottomsLit);
    }

    @Override
    public Vec3 getBrightnessDependentFogColor(Vec3 biomeFogColor, float daylight) {
        return biomeFogColor.multiply(daylight * 0.94F + 0.06F, (daylight * 0.94F + 0.06F), (daylight * 0.91F + 0.09F));
    }

    @Override
    public boolean isFoggyAt(int x, int y) {
        Player player = Minecraft.getInstance().player;

        if (player != null) {
            Holder<Biome> biomeHolder = player.level().getBiome(player.blockPosition());
            return biomeHolder.is(SLTags.Biomes.PERMAFROST_FOREST_VARIANT) || biomeHolder.is(SLTags.Biomes.DARK_SWAMP_VARIANT);
        }

        return false;
    }

    @Override
    public boolean renderSky(ClientLevel level, int ticks, float partialTick, PoseStack poseStack, Camera camera, Matrix4f projectionMatrix, boolean isFoggy, Runnable setupFog) {
        return SLSkyRenderer.renderSky(level, poseStack, projectionMatrix, partialTick, camera, setupFog);
    }
}
