package cn.leolezury.eternalstarlight.common.entity.living.boss.golem;

import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.entity.living.phase.AttackPhase;
import cn.leolezury.eternalstarlight.common.entity.misc.CameraShake;
import cn.leolezury.eternalstarlight.common.entity.misc.ESFallingBlock;
import cn.leolezury.eternalstarlight.common.particle.ESExplosionParticleOptions;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;

public class StarlightGolemSmashPhase extends AttackPhase<StarlightGolem> {
    public static final int ID = 4;

    private float pitch, yaw;
    private final List<BlockPos> visited = new ArrayList<>();
    private final List<BlockPos> lavaVisited = new ArrayList<>();

    public StarlightGolemSmashPhase() {
        super(ID, 2, 100, 250);
    }

    @Override
    public boolean canStart(StarlightGolem entity, boolean cooldownOver) {
        return cooldownOver && entity.getTarget() != null;
    }

    @Override
    public void onStart(StarlightGolem entity) {
        visited.clear();
        lavaVisited.clear();
    }

    @Override
    public void tick(StarlightGolem entity) {
        if (entity.getAttackTicks() == 30) {
            pitch = entity.getTarget() != null ? ESMathUtil.positionToPitch(entity.position(), entity.getTarget().position()) : 0;
            yaw = entity.getYHeadRot() + 90f;
        }
        if (entity.getAttackTicks() == 40) {
            CameraShake.createCameraShake(entity.level(), entity.position(), 45, 0.02f, 40, 20);
        }
        if (entity.getAttackTicks() >= 30) {
            int radius = (int) ((entity.getAttackTicks() - 30f) / 3.5f);
            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    for (int y = -radius; y <= radius; y++) {
                        BlockPos pos = entity.blockPosition().offset(x, y, z);
                        if (entity.level().getBlockState(pos).getFluidState().is(Fluids.LAVA)) {
                            if (!lavaVisited.contains(pos)) {
                                lavaVisited.add(pos);
                                for (Direction direction : Direction.values()) {
                                    if (entity.level().getBlockState(pos.relative(direction)).isAir() && entity.getRandom().nextInt(100) == 0) {
                                        entity.level().setBlockAndUpdate(pos.relative(direction), Blocks.LAVA.defaultBlockState());
                                        if (!entity.level().isClientSide) {
                                            ((ServerLevel) entity.level()).sendParticles(ESExplosionParticleOptions.LAVA, pos.relative(direction).getCenter().x, pos.relative(direction).getCenter().y, pos.relative(direction).getCenter().z, 1, 0, 0, 0, 0);
                                        }
                                    }
                                }
                            }
                        } else {
                            float blockPitch = ESMathUtil.positionToPitch(entity.position(), pos.getCenter());
                            float blockYaw = ESMathUtil.positionToYaw(entity.position(), pos.getCenter());
                            if (!visited.contains(pos) && !entity.level().getBlockState(pos).isAir() && Math.abs(Mth.wrapDegrees(pitch - blockPitch)) < 75 && Math.abs(Mth.wrapDegrees(yaw - blockYaw)) < 30 && pos.getCenter().distanceTo(entity.position()) <= radius && pos.getCenter().distanceTo(entity.position()) >= radius - 1) {
                                boolean above = entity.level().getBlockState(pos.above()).isAir();
                                boolean below = entity.level().getBlockState(pos.below()).isAir();
                                if (above || below) {
                                    visited.add(pos);
                                    boolean flag = true;
                                    if (!above) {
                                        flag = entity.getRandom().nextInt(6) == 0;
                                    }
                                    if (flag) {
                                        ESFallingBlock fallingBlock = new ESFallingBlock(entity.level(), pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, entity.level().getBlockState(pos), 100);
                                        fallingBlock.push(0, (above ? 1 : -1) * entity.getRandom().nextDouble() / 6 + 0.25, 0);
                                        entity.level().addFreshEntity(fallingBlock);
                                        for (LivingEntity living : entity.level().getEntitiesOfClass(LivingEntity.class, new AABB(pos).inflate(1))) {
                                            if (!living.getUUID().equals(entity.getUUID())) {
                                                living.hurt(ESDamageTypes.getDamageSource(entity.level(), ESDamageTypes.GROUND_SMASH), 4);
                                            }
                                        }
                                        if (!entity.level().isClientSide) {
                                            ((ServerLevel) entity.level()).sendParticles(ESExplosionParticleOptions.ENERGY, pos.getCenter().x, pos.getCenter().y, pos.getCenter().z, 1, 0, 0, 0, 0);
                                            if (entity.getRandom().nextInt(5) == 0) {
                                                ((ServerLevel) entity.level()).sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, pos.getCenter().x, pos.getCenter().y + 0.5, pos.getCenter().z, 1, 0, 0, 0, 0);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean canContinue(StarlightGolem entity) {
        return true;
    }

    @Override
    public void onStop(StarlightGolem entity) {

    }
}
