package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.registry.ESAttributes;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;

public class EtherLiquidBlock extends LiquidBlock {
	public static final String TAG_IN_ETHER_TICKS = "in_ether_ticks";
	public static final String TAG_CLIENT_IN_ETHER_TICKS = "client_in_ether_ticks";

	public EtherLiquidBlock(FlowingFluid flowingFluid, Properties properties) {
		super(flowingFluid, properties);
	}

	@Override
	protected void entityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity) {
		if (entity instanceof LivingEntity livingEntity) {
			float factor = 0;
			AttributeInstance resistance = livingEntity.getAttribute(ESAttributes.ETHER_RESISTANCE.asHolder());
			if (resistance != null) {
				factor = 1 - (float) resistance.getValue();
			}
			AttributeInstance armorInstance = livingEntity.getAttributes().getInstance(Attributes.ARMOR);
			if (armorInstance != null && armorInstance.getValue() <= 0) {
				if (entity.hurt(ESDamageTypes.getDamageSource(level, ESDamageTypes.ETHER), 0.3f + 0.6f * factor) && level instanceof ServerLevel serverLevel) {
					for (int i = 0; i < 5; i++) {
						serverLevel.sendParticles(ESParticles.STARLIGHT.get(), entity.getX() + (livingEntity.getRandom().nextDouble() - 0.5) * entity.getBbWidth(), entity.getY() + entity.getBbHeight() / 2d + (livingEntity.getRandom().nextDouble() - 0.5) * entity.getBbHeight(), entity.getZ() + (livingEntity.getRandom().nextDouble() - 0.5) * entity.getBbWidth(), 20, 0.1, 0.1, 0.1, 0);
					}
				}
			}
			CompoundTag compoundTag = ESEntityUtil.getPersistentData(livingEntity);
			if (armorInstance == null || armorInstance.getValue() > 0) {
				int inEtherTicks = compoundTag.getInt(TAG_IN_ETHER_TICKS);
				if (livingEntity.getRandom().nextFloat() <= factor) {
					compoundTag.putInt(TAG_IN_ETHER_TICKS, inEtherTicks + 1);
				}
			}
			if (level.isClientSide) {
				int clientEtherTicks = compoundTag.getInt(TAG_CLIENT_IN_ETHER_TICKS);
				if (clientEtherTicks < 140) {
					compoundTag.putInt(TAG_CLIENT_IN_ETHER_TICKS, clientEtherTicks + 1);
				}
			}
		} else {
			entity.hurt(ESDamageTypes.getDamageSource(level, ESDamageTypes.ETHER), 1);
		}
		super.entityInside(blockState, level, blockPos, entity);
	}
}
