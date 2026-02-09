package cn.leolezury.eternalstarlight.fabric.item.armor;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.armor.StarlitDiamondArmorModel;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.util.ESMiscUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

public class FabricStarlitDiamondArmorItem extends ArmorItem {
	public FabricStarlitDiamondArmorItem(Holder<ArmorMaterial> material, Type type, Properties properties) {
		super(material, type, properties);
		ESMiscUtil.runWhenOnClient(() -> this::initializeClient);
	}

	@Environment(EnvType.CLIENT)
	public void initializeClient() {
		ArmorRenderer.register(ArmorRender.INSTANCE, this);
	}

	@Environment(EnvType.CLIENT)
	private static final class ArmorRender implements ArmorRenderer {
		private static final ArmorRender INSTANCE = new ArmorRender();
		// private TextureAtlas armorTrimAtlas;

		private StarlitDiamondArmorModel<LivingEntity> innerModel;
		private StarlitDiamondArmorModel<LivingEntity> outerModel;

		public static void setPartVisibility(StarlitDiamondArmorModel<LivingEntity> armorModel, EquipmentSlot slot) {
			armorModel.setAllVisible(false);
			switch (slot) {
				case HEAD -> {
					armorModel.head.visible = true;
					armorModel.hat.visible = true;
				}
				case CHEST -> {
					armorModel.body.visible = true;
					armorModel.rightArm.visible = true;
					armorModel.leftArm.visible = true;
				}
				case LEGS -> {
					armorModel.body.visible = true;
					armorModel.rightLeg.visible = true;
					armorModel.leftLeg.visible = true;
				}
				case FEET -> {
					armorModel.rightLeg.visible = true;
					armorModel.leftLeg.visible = true;
				}
			}
		}

		@Override
		public void render(PoseStack stack, MultiBufferSource multiBufferSource, ItemStack itemStack, LivingEntity livingEntity, EquipmentSlot armorSlot, int light, HumanoidModel<LivingEntity> parentModel) {
			if (innerModel == null || outerModel == null) {
				innerModel = new StarlitDiamondArmorModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(StarlitDiamondArmorModel.INNER_LOCATION));
				outerModel = new StarlitDiamondArmorModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(StarlitDiamondArmorModel.OUTER_LOCATION));
			}

			/*if (armorTrimAtlas == null) {
				armorTrimAtlas = Minecraft.getInstance().getModelManager().getAtlas(Sheets.ARMOR_TRIMS_SHEET);
			}*/

			StarlitDiamondArmorModel<LivingEntity> armorModel;

			if (itemStack.is(ESItems.STARLIT_DIAMOND_HELMET.get()) || itemStack.is(ESItems.STARLIT_DIAMOND_CHESTPLATE.get()) || itemStack.is(ESItems.STARLIT_DIAMOND_BOOTS.get())) {
				armorModel = outerModel;
			} else if (itemStack.is(ESItems.STARLIT_DIAMOND_LEGGINGS.get())) {
				armorModel = innerModel;
			} else return;

			parentModel.copyPropertiesTo(armorModel);
			setPartVisibility(armorModel, armorSlot);
			ArmorRenderer.renderPart(stack, multiBufferSource, light, itemStack, armorModel, EternalStarlight.id("textures/armor/starlit_diamond_layer_" + ((armorSlot == EquipmentSlot.LEGS) ? "2.png" : "1.png")));
            /*if (itemStack.getItem() instanceof ArmorItem armorItem) {
				ArmorTrim trim = itemStack.get(DataComponents.TRIM);
				if (trim != null) {
					this.renderTrim(armorItem.getMaterial(), stack, multiBufferSource, light, trim, armorModel, armorItem.getType() == Type.LEGGINGS);
				}
            }*/
		}

        /*private void renderTrim(Holder<ArmorMaterial> armorMaterial, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, ArmorTrim armorTrim, HumanoidModel<?> humanoidModel, boolean inner) {
            TextureAtlasSprite textureAtlasSprite = this.armorTrimAtlas.getSprite(inner ? armorTrim.innerTexture(armorMaterial) : armorTrim.outerTexture(armorMaterial));
            VertexConsumer vertexConsumer = textureAtlasSprite.wrap(multiBufferSource.getBuffer(Sheets.armorTrimsSheet(armorTrim.pattern().value().decal())));
            humanoidModel.renderToBuffer(poseStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY, -1);
        }*/
	}
}
