package cn.leolezury.eternalstarlight.common.world.gen.structure.pool;

import cn.leolezury.eternalstarlight.common.registry.ESStructurePoolElementTypes;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElementType;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import java.util.Optional;
import java.util.function.Function;

public class ESSinglePoolElement extends SinglePoolElement {
	private final int groundLevelDelta;

	public static final MapCodec<ESSinglePoolElement> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
		templateCodec(),
		processorsCodec(),
		projectionCodec(),
		overrideLiquidSettingsCodec(),
		Codec.INT.fieldOf("ground_level_delta").forGetter(o -> o.groundLevelDelta)
	).apply(instance, ESSinglePoolElement::new));

	protected ESSinglePoolElement(Either<ResourceLocation, StructureTemplate> either, Holder<StructureProcessorList> holder, StructureTemplatePool.Projection projection, Optional<LiquidSettings> overrideLiquidSettings, int groundLevelDelta) {
		super(either, holder, projection, overrideLiquidSettings);
		this.groundLevelDelta = groundLevelDelta;
	}

	@Override
	public int getGroundLevelDelta() {
		return groundLevelDelta;
	}

	public static Function<StructureTemplatePool.Projection, SinglePoolElement> make(String string, Holder<StructureProcessorList> holder, int groundLevelDelta) {
		return (projection) -> new ESSinglePoolElement(Either.left(ResourceLocation.parse(string)), holder, projection, Optional.empty(), groundLevelDelta);
	}

	@Override
	public StructurePoolElementType<?> getType() {
		return ESStructurePoolElementTypes.SINGLE_POOL.get();
	}

	@Override
	public String toString() {
		return "ESSingle[" + this.template + "]";
	}
}