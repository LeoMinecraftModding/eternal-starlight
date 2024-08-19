package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.particle.ESSmokeParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class GolemSteelJetBlock extends WeatheringGolemSteelFullBlock {
	public GolemSteelJetBlock(Properties properties) {
		super(properties);
	}

	@Override
	public void stepOn(Level level, BlockPos pos, BlockState blockState, Entity entity) {
		if (!level.isClientSide && level instanceof ServerLevel serverLevel) {
			Vec3 particlePos = pos.getCenter().add(0, 0.3, 0);
			var random = RandomSource.create();
			for (int i = 0; i < 3; i++) {
				ParticlePacket packet = new ParticlePacket(ESSmokeParticleOptions.ENERGIZED_FLAME, particlePos.x, particlePos.y, particlePos.z, (random.nextFloat() - 0.5) / 5, 0.2 + random.nextFloat() / 1.5, (random.nextFloat() - 0.5) / 5);
				ESPlatform.INSTANCE.sendToAllClients(serverLevel, packet);
			}
		}
		entity.setDeltaMovement(new Vec3(0, 1.21, 0));
		entity.move(MoverType.SELF, entity.getDeltaMovement());
	}
}
