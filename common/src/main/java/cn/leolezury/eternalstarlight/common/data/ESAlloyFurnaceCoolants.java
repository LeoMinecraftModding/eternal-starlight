package cn.leolezury.eternalstarlight.common.data;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.AlloyFurnaceCoolant;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import net.minecraft.core.HolderSet;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;

public class ESAlloyFurnaceCoolants {
	public static final ResourceKey<AlloyFurnaceCoolant> SNOW = create("snow");
	public static final ResourceKey<AlloyFurnaceCoolant> SNOWBALL = create("snowball");
	public static final ResourceKey<AlloyFurnaceCoolant> SNOW_BLOCK = create("snow_block");
	public static final ResourceKey<AlloyFurnaceCoolant> ICE = create("ice");
	public static final ResourceKey<AlloyFurnaceCoolant> PACKED_ICE = create("packed_ice");
	public static final ResourceKey<AlloyFurnaceCoolant> BLUE_ICE = create("blue_ice");
	public static final ResourceKey<AlloyFurnaceCoolant> ICICLE = create("icicle");
	public static final ResourceKey<AlloyFurnaceCoolant> ASHEN_SNOW = create("ashen_snow");
	public static final ResourceKey<AlloyFurnaceCoolant> ASHEN_SNOWBALL = create("ashen_snowball");
	public static final ResourceKey<AlloyFurnaceCoolant> GLACITE = create("glacite");
	public static final ResourceKey<AlloyFurnaceCoolant> GLACITE_SHARD = create("glacite_shard");
	public static final ResourceKey<AlloyFurnaceCoolant> GLACITE_BLOCK = create("glacite_block");
	public static final ResourceKey<AlloyFurnaceCoolant> GLACITE_ARROW = create("glacite_arrow");
	public static final ResourceKey<AlloyFurnaceCoolant> GLACITE_SWORD = create("glacite_sword");
	public static final ResourceKey<AlloyFurnaceCoolant> GLACITE_PICKAXE = create("glacite_pickaxe");
	public static final ResourceKey<AlloyFurnaceCoolant> GLACITE_AXE = create("glacite_axe");
	public static final ResourceKey<AlloyFurnaceCoolant> GLACITE_HOE = create("glacite_hoe");
	public static final ResourceKey<AlloyFurnaceCoolant> GLACITE_SHOVEL = create("glacite_shovel");
	public static final ResourceKey<AlloyFurnaceCoolant> GLACITE_SCYTHE = create("glacite_scythe");
	public static final ResourceKey<AlloyFurnaceCoolant> GLACITE_HELMET = create("glacite_helmet");
	public static final ResourceKey<AlloyFurnaceCoolant> GLACITE_CHESTPLATE = create("glacite_chestplate");
	public static final ResourceKey<AlloyFurnaceCoolant> GLACITE_LEGGINGS = create("glacite_leggings");
	public static final ResourceKey<AlloyFurnaceCoolant> GLACITE_BOOTS = create("glacite_boots");
	public static final ResourceKey<AlloyFurnaceCoolant> GLACITE_SHIELD = create("glacite_shield");
	public static final ResourceKey<AlloyFurnaceCoolant> FROZEN_TUBE = create("frozen_tube");

	public static void bootstrap(BootstrapContext<AlloyFurnaceCoolant> context) {
		context.register(SNOW, new AlloyFurnaceCoolant(HolderSet.direct(Items.SNOW.builtInRegistryHolder()), 800, 3));
		context.register(SNOWBALL, new AlloyFurnaceCoolant(HolderSet.direct(Items.SNOWBALL.builtInRegistryHolder()), 800, 3));
		context.register(SNOW_BLOCK, new AlloyFurnaceCoolant(HolderSet.direct(Items.SNOW_BLOCK.builtInRegistryHolder()), 3200, 3));
		context.register(ICE, new AlloyFurnaceCoolant(HolderSet.direct(Items.ICE.builtInRegistryHolder()), 800, 3));
		context.register(PACKED_ICE, new AlloyFurnaceCoolant(HolderSet.direct(Items.PACKED_ICE.builtInRegistryHolder()), 8000, 3));
		context.register(BLUE_ICE, new AlloyFurnaceCoolant(HolderSet.direct(Items.BLUE_ICE.builtInRegistryHolder()), 32000, 5));
		context.register(ICICLE, new AlloyFurnaceCoolant(HolderSet.direct(ESItems.ICICLE.get().builtInRegistryHolder()), 800, 3));
		context.register(ASHEN_SNOW, new AlloyFurnaceCoolant(HolderSet.direct(ESItems.ASHEN_SNOW.get().builtInRegistryHolder()), 800, 3));
		context.register(ASHEN_SNOWBALL, new AlloyFurnaceCoolant(HolderSet.direct(ESItems.ASHEN_SNOWBALL.get().builtInRegistryHolder()), 800, 3));
		context.register(GLACITE, new AlloyFurnaceCoolant(HolderSet.direct(ESItems.GLACITE.get().builtInRegistryHolder()), 7200, 5));
		context.register(GLACITE_SHARD, new AlloyFurnaceCoolant(HolderSet.direct(ESItems.GLACITE_SHARD.get().builtInRegistryHolder()), 1200, 5));
		context.register(GLACITE_BLOCK, new AlloyFurnaceCoolant(HolderSet.direct(ESItems.GLACITE_BLOCK.get().builtInRegistryHolder()), 12000, 5));
		context.register(GLACITE_ARROW, new AlloyFurnaceCoolant(HolderSet.direct(ESItems.GLACITE_ARROW.get().builtInRegistryHolder()), 300, 4));
		context.register(GLACITE_SWORD, new AlloyFurnaceCoolant(HolderSet.direct(ESItems.GLACITE_SWORD.get().builtInRegistryHolder()), 300, 4));
		context.register(GLACITE_PICKAXE, new AlloyFurnaceCoolant(HolderSet.direct(ESItems.GLACITE_PICKAXE.get().builtInRegistryHolder()), 300, 4));
		context.register(GLACITE_AXE, new AlloyFurnaceCoolant(HolderSet.direct(ESItems.GLACITE_AXE.get().builtInRegistryHolder()), 300, 4));
		context.register(GLACITE_HOE, new AlloyFurnaceCoolant(HolderSet.direct(ESItems.GLACITE_HOE.get().builtInRegistryHolder()), 300, 4));
		context.register(GLACITE_SHOVEL, new AlloyFurnaceCoolant(HolderSet.direct(ESItems.GLACITE_SHOVEL.get().builtInRegistryHolder()), 300, 4));
		context.register(GLACITE_SCYTHE, new AlloyFurnaceCoolant(HolderSet.direct(ESItems.GLACITE_SCYTHE.get().builtInRegistryHolder()), 300, 4));
		context.register(GLACITE_HELMET, new AlloyFurnaceCoolant(HolderSet.direct(ESItems.GLACITE_HELMET.get().builtInRegistryHolder()), 300, 4));
		context.register(GLACITE_CHESTPLATE, new AlloyFurnaceCoolant(HolderSet.direct(ESItems.GLACITE_CHESTPLATE.get().builtInRegistryHolder()), 300, 4));
		context.register(GLACITE_LEGGINGS, new AlloyFurnaceCoolant(HolderSet.direct(ESItems.GLACITE_LEGGINGS.get().builtInRegistryHolder()), 300, 4));
		context.register(GLACITE_BOOTS, new AlloyFurnaceCoolant(HolderSet.direct(ESItems.GLACITE_BOOTS.get().builtInRegistryHolder()), 300, 4));
		context.register(GLACITE_SHIELD, new AlloyFurnaceCoolant(HolderSet.direct(ESItems.GLACITE_SHIELD.get().builtInRegistryHolder()), 300, 4));
		context.register(FROZEN_TUBE, new AlloyFurnaceCoolant(HolderSet.direct(ESItems.FROZEN_TUBE.get().builtInRegistryHolder()), 1600, 4));
	}

	public static ResourceKey<AlloyFurnaceCoolant> create(String name) {
		return ResourceKey.create(ESRegistries.ALLOY_FURNACE_COOLANT, EternalStarlight.id(name));
	}
}
