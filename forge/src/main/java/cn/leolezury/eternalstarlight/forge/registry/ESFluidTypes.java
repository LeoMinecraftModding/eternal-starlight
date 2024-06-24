package cn.leolezury.eternalstarlight.forge.registry;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistryObject;
import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.function.Consumer;

public class ESFluidTypes {
	public static final RegistrationProvider<FluidType> FLUID_TYPES = RegistrationProvider.get(NeoForgeRegistries.FLUID_TYPES.key(), EternalStarlight.ID);
	public static final RegistryObject<FluidType, FluidType> ETHER = FLUID_TYPES.register("ether", () -> new FluidType(FluidType.Properties.create()) {
		@Override
		public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
			consumer.accept(new IClientFluidTypeExtensions() {
				@Override
				public ResourceLocation getStillTexture() {
					return EternalStarlight.id("block/ether");
				}

				@Override
				public ResourceLocation getFlowingTexture() {
					return EternalStarlight.id("block/ether_flow");
				}

				@Override
				public @NotNull Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor) {
					return new Vector3f(232 / 255F, 255 / 255F, 222 / 255F);
				}

				@Override
				public void modifyFogRender(Camera camera, FogRenderer.FogMode mode, float renderDistance, float partialTick, float nearDistance, float farDistance, FogShape shape) {
					RenderSystem.setShaderFogStart(0.0F);
					RenderSystem.setShaderFogEnd(3.0F);
				}
			});
		}
	});

	public static void loadClass() {
	}
}
