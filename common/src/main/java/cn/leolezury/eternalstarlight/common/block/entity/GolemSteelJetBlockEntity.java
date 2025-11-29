package cn.leolezury.eternalstarlight.common.block.entity;

import cn.leolezury.eternalstarlight.common.block.WeatheringGolemSteel;
import cn.leolezury.eternalstarlight.common.block.WeatheringGolemSteelJetBlock;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class GolemSteelJetBlockEntity extends BlockEntity {
	private int pushCooldown = 0;

	public GolemSteelJetBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(ESBlockEntities.GOLEM_STEEL_JET.get(), blockPos, blockState);
	}

	public static void tick(Level level, BlockPos pos, BlockState state, GolemSteelJetBlockEntity blockEntity) {
		if (!level.isClientSide) {
			if (blockEntity.pushCooldown > 0) {
				blockEntity.pushCooldown--;
			} else {
				Direction direction = state.getValue(WeatheringGolemSteelJetBlock.FACING);
				AABB box = new AABB(pos).expandTowards(direction.getStepX() * 0.05, direction.getStepY() * 0.05, direction.getStepZ() * 0.05);
				boolean success = false;
				if (state.getValue(WeatheringGolemSteelJetBlock.POWER) > 0) {
					for (Entity entity : level.getEntitiesOfClass(Entity.class, box)) {
						if (level instanceof ServerLevel serverLevel) {
							Vec3 particlePos = pos.getCenter().add(direction.getStepX() * 0.5, direction.getStepY() * 0.5, direction.getStepZ() * 0.5);
							RandomSource random = entity.getRandom();
							for (int i = 0; i < 15; i++) {
								double speed = 0.2 + random.nextFloat() / 1.5;
								ParticlePacket packet = new ParticlePacket(ParticleTypes.WHITE_SMOKE, particlePos.x, particlePos.y, particlePos.z, direction.getStepX() * speed, direction.getStepY() * speed, direction.getStepZ() * speed);
								ESPlatform.INSTANCE.sendToAllClients(serverLevel, packet);
							}
						}
						entity.hurtMarked = true;
						double push = Math.sqrt(state.getValue(WeatheringGolemSteelJetBlock.POWER) * 0.21) * (state.getBlock() instanceof WeatheringGolemSteel weathering && weathering.isOxidized() ? 0.9 : 1);
						entity.addDeltaMovement(new Vec3(direction.step()).scale(push));
						if (entity instanceof Player player) {
							player.currentImpulseImpactPos = pos.getCenter().add(direction.getStepX() * 0.5, direction.getStepY() * 0.5, direction.getStepZ() * 0.5);
							player.setIgnoreFallDamageFromCurrentImpulse(true);
						}
						success = true;
					}
				}
				if (success) {
					blockEntity.pushCooldown = 5;
				}
			}
		}
	}
}
