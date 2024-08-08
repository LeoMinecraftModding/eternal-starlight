package cn.leolezury.eternalstarlight.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Equipable;

public class EquipableCarvedLunarisCactusFruitBlock extends CarvedLunarisCactusFruitBlock implements Equipable {
	public static final MapCodec<EquipableCarvedLunarisCactusFruitBlock> CODEC = simpleCodec(EquipableCarvedLunarisCactusFruitBlock::new);

	@Override
	public MapCodec<EquipableCarvedLunarisCactusFruitBlock> codec() {
		return CODEC;
	}

	public EquipableCarvedLunarisCactusFruitBlock(Properties properties) {
		super(properties);
	}

	@Override
	public EquipmentSlot getEquipmentSlot() {
		return EquipmentSlot.HEAD;
	}
}
