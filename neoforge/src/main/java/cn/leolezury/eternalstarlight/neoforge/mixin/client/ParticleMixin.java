package cn.leolezury.eternalstarlight.neoforge.mixin.client;

import cn.leolezury.eternalstarlight.common.client.particle.effect.*;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.particle.Particle;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Particle.class)
public abstract class ParticleMixin {
	@ModifyReturnValue(method = "getRenderBoundingBox", at = @At("RETURN"), remap = false)
	public AABB getRenderBoundingBox(AABB original) {
		Particle particle = (Particle) (Object) this;
		if (particle instanceof ElectricSparkParticle
			|| particle instanceof ExplosionShockParticle
			|| particle instanceof GatheringTrailParticle
			|| particle instanceof LunarSlashParticle
			|| particle instanceof OrbitalTrailParticle
			|| particle instanceof RingExplosionParticle
			|| particle instanceof RingParticle
			|| particle instanceof TrailParticle) {
			return AABB.INFINITE;
		}
		return original;
	}
}
