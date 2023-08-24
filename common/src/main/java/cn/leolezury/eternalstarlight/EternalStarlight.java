package cn.leolezury.eternalstarlight;

import cn.leolezury.eternalstarlight.init.BlockInit;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

//@Mod(EternalStarlight.MOD_ID)
public class EternalStarlight {
    public static final String MOD_ID = "eternal_starlight";
    public static final SimpleChannel NETWORK_WRAPPER;
    private static final String PROTOCOL_VERSION = Integer.toString(1);

    static {
        NetworkRegistry.ChannelBuilder channel = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(MOD_ID, "main"));
        channel = channel.clientAcceptedVersions(Integer.toString(1)::equals);
        NETWORK_WRAPPER = channel.serverAcceptedVersions(Integer.toString(1)::equals).networkProtocolVersion(() -> PROTOCOL_VERSION).simpleChannel();
    }

    public void init() {
        BlockInit.postRegistry();
    }

//    public EternalStarlight() {
//        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
//
//        BlockInit.BLOCKS.register(modEventBus);
//
//        ItemInit.ITEMS.register(modEventBus);
//        CreativeModeTabInit.TABS.register(modEventBus);
//
//        EntityInit.ENTITIES.register(modEventBus);
//
//        BlockEntityInit.BLOCK_ENTITIES.register(modEventBus);
//
//        EnchantmentInit.ENCHANTMENTS.register(modEventBus);
//
//        FeatureInit.FEATURES.register(modEventBus);
//        PlacerInit.TRUNK_PLACERS.register(modEventBus);
//        PlacerInit.FLOIAGE_PLACERS.register(modEventBus);
//        TreeDecoratorInit.TREE_DECORATORS.register(modEventBus);
//        StructurePlacementTypeInit.STRUCTURE_PLACEMENT_TYPES.register(modEventBus);
//        StructureInit.STRUCTURES.register(modEventBus);
//
//        POIInit.POIS.register(modEventBus);
//
//        ParticleInit.PARTICLE_TYPES.register(modEventBus);
//
//        SoundEventInit.SOUND_EVENTS.register(modEventBus);
//    }

    public static <MSG> void sendMessageToServer(MSG message) {
        NETWORK_WRAPPER.sendToServer(message);
    }

    public static <MSG> void sendMessageToClient(MSG message, ServerPlayer serverPlayer) {
        NETWORK_WRAPPER.sendTo(message, serverPlayer.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }
}
