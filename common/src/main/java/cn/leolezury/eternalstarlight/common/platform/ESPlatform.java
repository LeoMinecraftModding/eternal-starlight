package cn.leolezury.eternalstarlight.common.platform;

import cn.leolezury.eternalstarlight.common.block.TearBombBlock;
import cn.leolezury.eternalstarlight.common.block.fluid.EtherFluid;
import cn.leolezury.eternalstarlight.common.item.armor.AlchemistArmorItem;
import cn.leolezury.eternalstarlight.common.item.armor.ThermalSpringstoneArmorItem;
import cn.leolezury.eternalstarlight.common.item.armor.UnrealiumArmorItem;
import cn.leolezury.eternalstarlight.common.item.combat.CrescentSpearItem;
import cn.leolezury.eternalstarlight.common.item.combat.HammerItem;
import cn.leolezury.eternalstarlight.common.item.combat.PetalScytheItem;
import cn.leolezury.eternalstarlight.common.item.combat.ScytheItem;
import cn.leolezury.eternalstarlight.common.platform.registry.RegistrationProvider;
import cn.leolezury.eternalstarlight.common.resource.gatekeeper.TheGatekeeperNameManager;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;

import java.nio.file.Path;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface ESPlatform {
	ESPlatform INSTANCE = Util.make(() -> {
		final ServiceLoader<ESPlatform> loader = ServiceLoader.load(ESPlatform.class);
		final Iterator<ESPlatform> iterator = loader.iterator();
		if (!iterator.hasNext()) {
			throw new RuntimeException("Platform instance not found!");
		} else {
			final ESPlatform platform = iterator.next();
			if (iterator.hasNext()) {
				throw new RuntimeException("More than one platform instance was found!");
			}
			return platform;
		}
	});

	enum Loader {
		NEOFORGE,
		FABRIC
	}

	// some loader-related stuff
	Loader getLoader();

	boolean isPhysicalClient();

	Path getConfigDir();

	// registry utils
	<T> RegistrationProvider<T> createRegistrationProvider(ResourceKey<? extends Registry<T>> key, String namespace);

	<T> RegistrationProvider<T> createNewRegistryProvider(ResourceKey<? extends Registry<T>> key, String namespace);

	<T> void registerDatapackRegistry(ResourceKey<Registry<T>> key, Codec<T> codec, Codec<T> networkCodec);

	// for initialization
	// items
	default SpawnEggItem createSpawnEgg(Supplier<EntityType<? extends Mob>> defaultType, int backgroundColor, int highlightColor, Item.Properties properties) {
		return new SpawnEggItem(defaultType.get(), backgroundColor, highlightColor, properties);
	}

	default ScytheItem createScythe(Tier tier, boolean canTill, Item.Properties properties) {
		return new ScytheItem(tier, canTill, properties);
	}

	default ScytheItem createPetalScythe(Tier tier, boolean canTill, Item.Properties properties) {
		return new PetalScytheItem(tier, canTill, properties);
	}

	default HammerItem createHammer(Tier tier, Supplier<ParticleOptions> smashParticle, Holder<SoundEvent> smashSound, Item.Properties properties) {
		return new HammerItem(tier, smashParticle, smashSound, properties);
	}

	default CrescentSpearItem createCrescentSpear(Item.Properties properties) {
		return new CrescentSpearItem(properties);
	}

	default ThermalSpringstoneArmorItem createThermalSpringstoneArmor(Holder<ArmorMaterial> material, ArmorItem.Type type, Item.Properties properties) {
		return new ThermalSpringstoneArmorItem(material, type, properties);
	}

	default AlchemistArmorItem createAlchemistArmor(Holder<ArmorMaterial> material, ArmorItem.Type type, Item.Properties properties) {
		return new AlchemistArmorItem(material, type, properties);
	}

	default ArmorItem createStarlitDiamondArmor(Holder<ArmorMaterial> material, ArmorItem.Type type, Item.Properties properties) {
		return new ArmorItem(material, type, properties);
	}

	default UnrealiumArmorItem createUnrealiumArmor(Holder<ArmorMaterial> material, ArmorItem.Type type, Item.Properties properties) {
		return new UnrealiumArmorItem(material, type, properties);
	}

	CreativeModeTab getESTab();

	// blocks
	default FlowerPotBlock createFlowerPot(Supplier<FlowerPotBlock> pot, Supplier<? extends Block> flower, BlockBehaviour.Properties properties) {
		return new FlowerPotBlock(flower.get(), properties);
	}

	default EtherFluid.Still createEtherFluid() {
		return new EtherFluid.Still();
	}

	default EtherFluid.Flowing createFlowingEtherFluid() {
		return new EtherFluid.Flowing();
	}

	default TearBombBlock createTearBombBlock(BlockBehaviour.Properties properties) {
		return new TearBombBlock(properties);
	}

	// attachment
	default <T> EntityDataAttachment<T> registerDataAttachment(String id, Supplier<T> defaultValue, Codec<T> codec, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec, boolean copyOnDeath) {
		return registerDataAttachment(id, defaultValue, codec, streamCodec, (v1, v2) -> !Objects.equals(v1, v2), copyOnDeath);
	}

	<T> EntityDataAttachment<T> registerDataAttachment(String id, Supplier<T> defaultValue, Codec<T> codec, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec, BiPredicate<T, T> shouldSync, boolean copyOnDeath);

	// reload listeners
	default TheGatekeeperNameManager createGatekeeperNameManager() {
		return new TheGatekeeperNameManager();
	}

	// event-related
	default boolean canEntityGrief(Level level, Entity entity) {
		return level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
	}

	default boolean postEntityDestroyBlockEvent(Level level, BlockPos pos, Entity entity) {
		if (entity instanceof ServerPlayer player && (!level.mayInteract(player, pos) || player.blockActionRestricted(level, pos, player.gameMode.getGameModeForPlayer()))) {
			return false;
		}
		return level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
	}

	default boolean postTravelToDimensionEvent(Entity entity, ResourceKey<Level> dimension) {
		return true;
	}

	default boolean postTeleportEvent(Entity entity, Vec3 destPos) {
		return true;
	}

	// item stuff
	boolean isShears(ItemStack stack);

	boolean isShield(ItemStack stack);

	default boolean canScrape(ItemStack stack) {
		return stack.is(ItemTags.AXES);
	}

	Pair<Predicate<UseOnContext>, Consumer<UseOnContext>> getToolTillAction(UseOnContext context);

	default int getBurnTime(ItemStack stack, RecipeType<?> type) {
		return AbstractFurnaceBlockEntity.getFuel().get(stack.getItem());
	}

	default boolean hasCraftingRemainingItem(ItemStack stack) {
		return stack.getItem().hasCraftingRemainingItem();
	}

	default Optional<ItemStack> getCraftingRemainingItem(ItemStack stack) {
		return Optional.ofNullable(stack.getItem().getCraftingRemainingItem()).map(ItemStack::new);
	}

	// dispenser
	default boolean canBoatInFluid(Boat boat, FluidState state) {
		return state.is(FluidTags.WATER);
	}

	// part entity
	default Entity getPartEntityParent(Entity entity) {
		if (entity instanceof EnderDragonPart part) {
			return part.parentMob;
		}
		return null;
	}

	// networking
	void sendToClient(ServerPlayer player, CustomPacketPayload packet);

	default void sendToAllClients(ServerLevel level, CustomPacketPayload packet) {
		for (ServerPlayer player : level.players()) {
			sendToClient(player, packet);
		}
	}

	default void sendToTrackingClients(ServerLevel level, Entity entity, CustomPacketPayload packet) {
		level.getChunkSource().broadcast(entity, new ClientboundCustomPayloadPacket(packet));
		if (entity instanceof ServerPlayer player) {
			sendToClient(player, packet);
		}
	}
}
