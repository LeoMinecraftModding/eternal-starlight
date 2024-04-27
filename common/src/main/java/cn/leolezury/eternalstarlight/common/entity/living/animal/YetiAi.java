package cn.leolezury.eternalstarlight.common.entity.living.animal;

import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESSensorTypes;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class YetiAi {
    public static final List<SensorType<? extends Sensor<? super Yeti>>> SENSOR_TYPES = ImmutableList.of(
            SensorType.NEAREST_LIVING_ENTITIES,
            SensorType.HURT_BY,
            SensorType.NEAREST_PLAYERS,
            ESSensorTypes.YETI_TEMPTATIONS.get()
    );
    public static final List<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.WALK_TARGET,
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
            MemoryModuleType.PATH,
            MemoryModuleType.IS_PANICKING,
            MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
            MemoryModuleType.BREED_TARGET,
            MemoryModuleType.TEMPTING_PLAYER,
            MemoryModuleType.TEMPTATION_COOLDOWN_TICKS,
            MemoryModuleType.IS_TEMPTED
    );

    public static Ingredient getTemptations() {
        return Ingredient.of(ESTags.Items.YETI_FOOD);
    }

    protected static Brain<?> makeBrain(Brain<Yeti> brain) {
        initCoreActivity(brain);
        initIdleActivity(brain);
        brain.setCoreActivities(Set.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();
        return brain;
    }

    private static void initCoreActivity(Brain<Yeti> brain) {
        brain.addActivity(Activity.CORE, 0, ImmutableList.of(
                new Swim(0.8F),
                new AnimalPanic<>(1.0F),
                new MoveToTargetSink(),
                new CountDownCooldownTicks(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS))
        );
    }

    private static void initIdleActivity(Brain<Yeti> brain) {
        brain.addActivity(Activity.IDLE, ImmutableList.of(
                Pair.of(0, new AnimalMakeLove(ESEntities.YETI.get())),
                Pair.of(1, new FollowTemptation((livingEntity) -> 1.25F, (livingEntity) -> 3.5)),
                Pair.of(2, new LookAtTargetSink(45, 90)),
                Pair.of(3, new StartRolling()),
                Pair.of(3, new StopRolling()),
                Pair.of(3, new RunOne<>(
                        ImmutableList.of(
                                Pair.of(SetWalkTargetFromLookTarget.create(1.0F, 3), 2),
                                Pair.of(SetEntityLookTarget.create(EntityType.PLAYER, 6.0F), 1),
                                Pair.of(RandomStroll.stroll(1.0F), 1),
                                Pair.of(new DoNothing(5, 20), 2)
                        )
                ))
        ));
    }

    static void updateActivity(Yeti yeti) {
        yeti.getBrain().setActiveActivityToFirstValid(ImmutableList.of(Activity.IDLE));
    }

    private static class StartRolling extends Behavior<Yeti> {
        private StartRolling() {
            super(Map.of(
                    MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT,
                    MemoryModuleType.IS_PANICKING, MemoryStatus.VALUE_ABSENT
            ), 10);
        }

        protected boolean checkExtraStartConditions(ServerLevel serverLevel, Yeti yeti) {
            return yeti.getRollState() == 0 && !yeti.getMoveControl().hasWanted() && yeti.getRollCoolDown() <= 0 && yeti.getRandom().nextInt(10) == 0;
        }

        @Override
        protected boolean canStillUse(ServerLevel serverLevel, Yeti livingEntity, long l) {
            return true;
        }

        protected void start(ServerLevel serverLevel, Yeti yeti, long l) {
            yeti.setRollState(1);
            yeti.setRollTicks(400);
        }

        protected void stop(ServerLevel serverLevel, Yeti yeti, long l) {
            yeti.setRollState(2);
        }
    }

    private static class StopRolling extends Behavior<Yeti> {
        private StopRolling() {
            super(Map.of(
                    MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT,
                    MemoryModuleType.IS_PANICKING, MemoryStatus.VALUE_ABSENT
            ), 10);
        }

        protected boolean checkExtraStartConditions(ServerLevel serverLevel, Yeti yeti) {
            return yeti.getRollState() == 2 && !yeti.getMoveControl().hasWanted() && yeti.getRollTicks() == 0;
        }

        @Override
        protected boolean canStillUse(ServerLevel serverLevel, Yeti livingEntity, long l) {
            return true;
        }

        protected void start(ServerLevel serverLevel, Yeti yeti, long l) {
            yeti.setRollState(3);
        }

        protected void stop(ServerLevel serverLevel, Yeti yeti, long l) {
            yeti.setRollState(0);
            yeti.setRollTicks(400);
            yeti.setRollCoolDown(600);
        }
    }
}
