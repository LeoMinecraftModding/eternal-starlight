package cn.leolezury.eternalstarlight.neoforge.client.model.item;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.BakedModelWrapper;
import net.neoforged.neoforge.client.model.IQuadTransformer;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class NeoGlowingBakedModel extends BakedModelWrapper<BakedModel> {
	public NeoGlowingBakedModel(BakedModel originalModel) {
		super(originalModel);
	}

	@Override
	public List<BakedQuad> getQuads(@Nullable BlockState blockState, @Nullable Direction direction, RandomSource randomSource) {
		return transformQuads(super.getQuads(blockState, direction, randomSource));
	}

	private static List<BakedQuad> transformQuads(List<BakedQuad> oldQuads) {
		List<BakedQuad> quads = new ArrayList<>();
		for (BakedQuad quad : oldQuads) {
			quads.add(applyGlowEffect(quad));
		}
		return quads;
	}

	private static BakedQuad applyGlowEffect(BakedQuad quad) {
		int[] vertexData = quad.getVertices().clone();
		vertexData[IQuadTransformer.UV2] = LightTexture.FULL_BRIGHT;
		vertexData[IQuadTransformer.UV2 + IQuadTransformer.STRIDE] = LightTexture.FULL_BRIGHT;
		vertexData[IQuadTransformer.UV2 + 2 * IQuadTransformer.STRIDE] = LightTexture.FULL_BRIGHT;
		vertexData[IQuadTransformer.UV2 + 3 * IQuadTransformer.STRIDE] = LightTexture.FULL_BRIGHT;
		return new BakedQuad(vertexData, quad.getTintIndex(), quad.getDirection(), quad.getSprite(), quad.isShade());
	}

	@Override
	public BakedModel applyTransform(ItemDisplayContext cameraTransformType, PoseStack poseStack, boolean applyLeftHandTransform) {
		this.getTransforms().getTransform(cameraTransformType).apply(applyLeftHandTransform, poseStack);
		return this;
	}

	@Override
	public List<BakedModel> getRenderPasses(ItemStack itemStack, boolean fabulous) {
		return List.of(this);
	}
}
