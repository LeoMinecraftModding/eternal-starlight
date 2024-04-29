package cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper;

import cn.leolezury.eternalstarlight.common.entity.living.boss.AttackPhase;
import cn.leolezury.eternalstarlight.common.entity.misc.ESFallingBlock;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class GatekeeperSwingSwordPhase extends AttackPhase<TheGatekeeper> {
    public static final int ID = 6;

    public GatekeeperSwingSwordPhase() {
        super(ID, 1, 61, 100);
    }

    @Override
    public boolean canStart(TheGatekeeper entity, boolean coolDownOver) {
        return coolDownOver && entity.canReachTarget(3);
    }

    @Override
    public void onStart(TheGatekeeper entity) {

    }

    @Override
    public void tick(TheGatekeeper entity) {
        if (entity.getAttackTicks() < 40 && entity.level() instanceof ServerLevel serverLevel) {
            Vec3 pos = entity.position().add(0, entity.getBbHeight() * 0.5, 0).offsetRandom(entity.getRandom(), 6);
            Vec3 delta = entity.position().add(0, entity.getBbHeight() * 0.5, 0).subtract(pos).normalize();
            ESPlatform.INSTANCE.sendToAllClients(serverLevel, new ParticlePacket(ParticleTypes.CRIT, pos.x, pos.y, pos.z, delta.x, delta.y, delta.z));
        }
        if (entity.getTarget() != null) {
            LivingEntity target = entity.getTarget();
            if (entity.getAttackTicks() == 40) {
                for (int x = -4; x <= 4; x++) {
                    for (int y = -2; y <= 2; y++) {
                        for (int z = -4; z <= 4; z++) {
                            BlockPos pos = entity.blockPosition().offset(x, y, z);
                            if (entity.level().getBlockState(pos.above()).isAir() && pos.getCenter().distanceTo(entity.position()) <= 4) {
                                ESFallingBlock fallingBlock = new ESFallingBlock(entity.level(), pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, entity.level().getBlockState(pos), 100, false);
                                fallingBlock.push(0, entity.getRandom().nextDouble() / 12 + 0.2, 0);
                                entity.level().addFreshEntity(fallingBlock);
                                if (fallingBlock.getBoundingBox().intersects(target.getBoundingBox()) && !entity.canReachTarget(2)) {
                                    target.hurtMarked = true;
                                    target.setDeltaMovement(target.getDeltaMovement().add(entity.position().subtract(target.position()).normalize().scale(0.5)));
                                }
                            }
                        }
                    }
                }
            }
            if (entity.getAttackTicks() >= 47 && entity.getAttackTicks() <= 51 && entity.performMeleeAttack(3, false)) {
                target.hurtMarked = true;
                target.setDeltaMovement(target.getDeltaMovement().add(entity.position().subtract(target.position()).normalize().scale(-3).add(0, 0.2, 0)));
            }
        }
    }

    @Override
    public boolean canContinue(TheGatekeeper entity) {
        return true;
    }

    @Override
    public void onStop(TheGatekeeper entity) {

    }
}