package cn.leolezury.eternalstarlight.forge.client.renderer;

import cn.leolezury.eternalstarlight.common.client.renderer.ESItemStackRenderer;
import com.google.common.base.Suppliers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Supplier;

public class ForgeItemStackRenderer extends BlockEntityWithoutLevelRenderer {
	public static final Supplier<ForgeItemStackRenderer> INSTANCE = Suppliers.memoize(ForgeItemStackRenderer::new);
	public static final IClientItemExtensions CLIENT_ITEM_EXTENSION = Util.make(() -> new IClientItemExtensions() {
		@Override
		public BlockEntityWithoutLevelRenderer getCustomRenderer() {
			return INSTANCE.get();
		}
	});

	public ForgeItemStackRenderer() {
		super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
	}

	@Override
	public void renderByItem(ItemStack stack, ItemDisplayContext context, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, int overlay) {
		ESItemStackRenderer.render(stack, context, poseStack, multiBufferSource, light, overlay);
	}
}
