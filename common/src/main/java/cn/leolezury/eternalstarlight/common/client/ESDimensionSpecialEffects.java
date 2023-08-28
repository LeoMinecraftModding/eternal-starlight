package cn.leolezury.eternalstarlight.common.client;

import cn.leolezury.eternalstarlight.common.client.renderer.world.ESSkyRenderer;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

@Environment(EnvType.CLIENT)

public class ESDimensionSpecialEffects extends DimensionSpecialEffects {
    public ESDimensionSpecialEffects(float cloudHeight, boolean placebo, SkyType fogType, boolean brightenLightMap, boolean entityLightingBottomsLit) {
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
            return biomeHolder.is(ESTags.Biomes.PERMAFROST_FOREST_VARIANT) || biomeHolder.is(ESTags.Biomes.DARK_SWAMP_VARIANT);
        }

        return false;
    }

    public static boolean doRenderSky(ClientLevel level, float partialTick, PoseStack poseStack, Camera camera, Matrix4f projectionMatrix, boolean isFoggy, Runnable setupFog) {
        return ESSkyRenderer.renderSky(level, poseStack, projectionMatrix, partialTick, camera, setupFog);
    }
}
