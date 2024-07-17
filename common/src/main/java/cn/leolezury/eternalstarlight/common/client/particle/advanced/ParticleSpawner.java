package cn.leolezury.eternalstarlight.common.client.particle.advanced;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteSet;

@Environment(EnvType.CLIENT)
public interface ParticleSpawner {
	Particle spawn(ClientLevel clientLevel, double x, double y, double z, SpriteSet spriteSet, AdvancedParticleOptions options);
}
