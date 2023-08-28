package cn.leolezury.eternalstarlight.common.client.model.item;

import cn.leolezury.eternalstarlight.common.client.model.item.wrapper.ESBakedModelWrapper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class GlowingBakedModel extends ESBakedModelWrapper<BakedModel> {
    public GlowingBakedModel(BakedModel origin) {
        super(origin);
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState blockState, @Nullable Direction direction, RandomSource randomSource) {
        return transformQuads(super.getQuads(blockState, direction, randomSource));
    }

    private static List<BakedQuad> transformQuads(List<BakedQuad> oldQuads) {
        List<BakedQuad> quads = new ArrayList<>();
        for(BakedQuad quad : oldQuads){
            quads.add(applyGlowEffect(quad));
        }
        return quads;
    }

    private static BakedQuad applyGlowEffect(BakedQuad quad) {
        int[] vertexData = quad.getVertices().clone();
        int step = vertexData.length / 4;

        vertexData[6] = 0xF000F0;
        vertexData[6 + step] = 0xF000F0;
        vertexData[6 + 2 * step] = 0xF000F0;
        vertexData[6 + 3 * step] = 0xF000F0;
        return new BakedQuad(vertexData, quad.getTintIndex(), quad.getDirection(), quad.getSprite(), quad.isShade());
    }
}
