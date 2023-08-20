package cn.leolezury.eternalstarlight.client.particle.lightning;

import cn.leolezury.eternalstarlight.client.renderer.effect.BoltEffect;
import cn.leolezury.eternalstarlight.client.renderer.effect.BoltRenderer;
import cn.leolezury.eternalstarlight.util.Color;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.joml.Vector3f;

import java.util.Random;


public class LightningParticle extends Particle {
    private final Vec3 endPos;
    private final Vector3f color;
    private final BoltRenderer boltRenderer = new BoltRenderer();

    protected LightningParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, Vector3f color) {
        super(level, x, y, z);
        endPos = new Vec3(xSpeed, ySpeed, zSpeed);
        this.color = color;
        this.gravity = 0.0F;
        this.lifetime = 5 + (new Random()).nextInt(3);
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
        }
    }

    public void render(VertexConsumer consumer, Camera camera, float partialTick) {
        Vec3 vec3 = camera.getPosition();
        PoseStack stack = new PoseStack();
        MultiBufferSource.BufferSource multibuffersource$buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
        float x = (float)(Mth.lerp(partialTick, this.xo, this.x) - vec3.x);
        float y = (float)(Mth.lerp(partialTick, this.yo, this.y) - vec3.y);
        float z = (float)(Mth.lerp(partialTick, this.zo, this.z) - vec3.z);
        stack.pushPose();
        stack.translate(x, y, z);
        stack.scale(1.5f, 1.5f, 1.5f);

        float progress = (age + partialTick) / lifetime;
        BoltEffect.BoltRenderInfo lightningBoltData = new BoltEffect.BoltRenderInfo().color(Color.rgbad(color.x, color.y, color.z, 1F - progress)).noise(0.2F, 0.2F).branching(0.3F, 0.6F).spreader(BoltEffect.SegmentSpreader.memory(0.9F));
        BoltEffect bolt = (new BoltEffect(lightningBoltData, Vec3.ZERO, endPos, 4)).size(0.05F).lifespan(lifetime).spawn(BoltEffect.SpawnFunction.CONSECUTIVE);
        boltRenderer.update(this, bolt, partialTick);
        boltRenderer.render(partialTick, stack, multibuffersource$buffersource);

        multibuffersource$buffersource.endBatch();
        stack.popPose();
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.CUSTOM;
    }

    
    public static class Provider implements ParticleProvider<LightningParticleOptions> {
        public Provider(SpriteSet spriteSet) {}

        public Particle createParticle(LightningParticleOptions options, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {

            return new LightningParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, options.getColor());
        }
    }
}
