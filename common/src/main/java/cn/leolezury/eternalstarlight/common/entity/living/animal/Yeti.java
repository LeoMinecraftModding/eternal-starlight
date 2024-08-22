package cn.leolezury.eternalstarlight.common.entity.living.animal;

import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import com.mojang.serialization.Dynamic;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class Yeti extends Animal {
	protected static final EntityDataAccessor<Integer> ROLL_STATE = SynchedEntityData.defineId(Yeti.class, EntityDataSerializers.INT);
	protected static final EntityDataAccessor<Boolean> HAS_FUR = SynchedEntityData.defineId(Yeti.class, EntityDataSerializers.BOOLEAN);

	public int getRollState() {
		return this.getEntityData().get(ROLL_STATE);
	}

	public boolean hasFur() {
		return this.getEntityData().get(HAS_FUR);
	}

	public void setRollState(int rollState) {
		this.getEntityData().set(ROLL_STATE, rollState);
	}

	public AnimationState idleAnimationState = new AnimationState();
	public AnimationState rollStartAnimationState = new AnimationState();
	public AnimationState rollAnimationState = new AnimationState();
	public AnimationState rollEndAnimationState = new AnimationState();
	private int rollTicks = 0;

	public void setRollTicks(int rollTicks) {
		this.rollTicks = rollTicks;
	}

	public int getRollTicks() {
		return rollTicks;
	}

	private int rollCooldown = 200;

	public void setRollCooldown(int rollCooldown) {
		this.rollCooldown = rollCooldown;
	}

	public int getRollCooldown() {
		return rollCooldown;
	}

	public float rollAngle, prevRollAngle;

	public Yeti(EntityType<? extends Animal> entityType, Level level) {
		super(entityType, level);
		this.getNavigation().setCanFloat(true);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0D).add(Attributes.MOVEMENT_SPEED, 0.25D);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(ROLL_STATE, 0)
			.define(HAS_FUR, true);
	}

	@Override
	protected Brain<?> makeBrain(Dynamic<?> dynamic) {
		return YetiAi.makeBrain(this.brainProvider().makeBrain(dynamic));
	}

	@Override
	public Brain<Yeti> getBrain() {
		return (Brain<Yeti>) super.getBrain();
	}

	@Override
	protected Brain.Provider<Yeti> brainProvider() {
		return Brain.provider(YetiAi.MEMORY_TYPES, YetiAi.SENSOR_TYPES);
	}

	@Override
	protected void customServerAiStep() {
		this.level().getProfiler().push("YetiBrain");
		this.getBrain().tick((ServerLevel) this.level(), this);
		this.level().getProfiler().popPush("YetiActivityUpdate");
		YetiAi.updateActivity(this);
		this.level().getProfiler().pop();
		super.customServerAiStep();
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (rollTicks > 0) {
			rollTicks--;
		}
		if (rollCooldown > 0) {
			rollCooldown--;
		}
		if (level().isClientSide) {
			idleAnimationState.startIfStopped(tickCount);
			prevRollAngle = rollAngle;
			rollAngle += (float) (position().subtract(new Vec3(xOld, yOld, zOld)).length() / (3f / 260f));
			rollAngle = rollAngle % 360;
		}
	}

	public void stopAllAnimStates() {
		rollStartAnimationState.stop();
		rollAnimationState.stop();
		rollEndAnimationState.stop();
	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
		super.onSyncedDataUpdated(accessor);
		if (accessor.equals(ROLL_STATE) && getRollState() != 0) {
			stopAllAnimStates();
			switch (getRollState()) {
				case 1 -> {
					rollAngle = 0;
					prevRollAngle = 0;
					rollStartAnimationState.start(tickCount);
				}
				case 2 -> rollAnimationState.start(tickCount);
				case 3 -> rollEndAnimationState.start(tickCount);
			}
		}
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		ItemStack itemStack = player.getItemInHand(hand);
		if (ESPlatform.INSTANCE.isShears(itemStack) && hasFur()) {
			if (!level().isClientSide) {
				this.level().playSound(null, this, SoundEvents.SHEEP_SHEAR, SoundSource.PLAYERS, 1.0F, 1.0F);
				this.gameEvent(GameEvent.SHEAR, player);
				itemStack.hurtAndBreak(1, player, getSlotForHand(hand));

				int i = 1 + this.random.nextInt(3);
				for (int j = 0; j < i; ++j) {
					ItemEntity itemEntity = this.spawnAtLocation(ESItems.WHITE_YETI_FUR.get(), 1);
					if (itemEntity != null) {
						itemEntity.setDeltaMovement(itemEntity.getDeltaMovement().add((this.random.nextFloat() - this.random.nextFloat()) * 0.1F, this.random.nextFloat() * 0.05F, (this.random.nextFloat() - this.random.nextFloat()) * 0.1F));
					}
				}

				this.getEntityData().set(HAS_FUR, false);
			}
			return InteractionResult.sidedSuccess(level().isClientSide);
		}
		return super.mobInteract(player, hand);
	}

	@Override
	public void tick() {
		super.tick();
		if (!this.getEntityData().get(HAS_FUR) && RandomSource.create().nextInt(0, 12000) < 12 && !this.level().isClientSide) {
			this.getEntityData().set(HAS_FUR, true);
		}
	}

	@Override
	public boolean isFood(ItemStack itemStack) {
		return YetiAi.getTemptations().test(itemStack);
	}

	@Nullable
	@Override
	public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
		return new Yeti(ESEntities.YETI.get(), serverLevel);
	}

	public static boolean checkYetiSpawnRules(EntityType<? extends Yeti> type, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
		return level.getBlockState(pos.below()).is(BlockTags.DIRT);
	}
}
