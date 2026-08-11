package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

public class DesertFlowerBlock extends ESFlowerBlock {
	public static final MapCodec<DesertFlowerBlock> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(EFFECTS_FIELD.forGetter(FlowerBlock::getSuspiciousEffects), propertiesCodec()).apply(instance, DesertFlowerBlock::new));

	public DesertFlowerBlock(Holder<MobEffect> holder, float duration, Properties properties, Optional<ResourceKey<Item>> seed) {
		super(holder, duration, properties, seed);
	}

	public DesertFlowerBlock(SuspiciousStewEffects effects, BlockBehaviour.Properties properties, ResourceKey<Item> seed) {
		super(effects, properties);
	}

	public DesertFlowerBlock(SuspiciousStewEffects effects, BlockBehaviour.Properties properties) {
		this(effects, properties, null);
	}

	@Override
	public MapCodec<DesertFlowerBlock> codec() {
		return CODEC;
	}

	@Override
	protected boolean mayPlaceOn(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
		return super.mayPlaceOn(blockState, blockGetter, blockPos) || blockState.is(BlockTags.SAND) || blockState.is(ESTags.Blocks.BASE_STONE_STARLIGHT) || blockState.is(ESBlocks.RED_STARLIGHT_CRYSTAL_BLOCK.get()) || blockState.is(ESBlocks.BLUE_STARLIGHT_CRYSTAL_BLOCK.get());
	}
}
