package cn.leolezury.eternalstarlight.common.block;


import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class JetBlock extends Block {
	public final List<ParticleOptions> particles;

	public JetBlock(List<ParticleOptions> particles, Properties properties) {
		super(properties);
		this.particles = particles;
	}

	@Override
	public void stepOn(Level level, BlockPos pos, BlockState blockState, Entity entity) {
		if (!level.isClientSide && level instanceof ServerLevel serverLevel) {
			Vec3 particlePos = pos.getCenter().add(0, 0.4, 0);
			var random = RandomSource.create();
			for (var particle : this.particles) {
				ParticlePacket packet = new ParticlePacket(particle, particlePos.x, particlePos.y, particlePos.z, (random.nextFloat() - 0.5) / 5, 0.2 + random.nextFloat() / 1.5, (random.nextFloat() - 0.5) / 5);
				ESPlatform.INSTANCE.sendToAllClients(serverLevel, packet);
			}
		}
	}
}
