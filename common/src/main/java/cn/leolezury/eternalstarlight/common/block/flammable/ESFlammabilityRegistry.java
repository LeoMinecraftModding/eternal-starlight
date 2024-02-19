package cn.leolezury.eternalstarlight.common.block.flammable;

import cn.leolezury.eternalstarlight.common.init.ESBlocks;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class ESFlammabilityRegistry {
    public static final Map<Supplier<? extends Block>, Flammability> BLOCK_TO_FLAMMABILITY = new HashMap<>();

    public static final Flammability WOOD = new Flammability(5, 30);
    public static final Flammability LEAVES = new Flammability(30, 60);

    public static Optional<Flammability> getBlockFlammability(Block block) {
        for (Supplier<? extends Block> blockSupplier : BLOCK_TO_FLAMMABILITY.keySet()) {
            if (blockSupplier.get().defaultBlockState().is(block)) {
                return Optional.ofNullable(BLOCK_TO_FLAMMABILITY.get(blockSupplier));
            }
        }
        return Optional.empty();
    }

    public static void registerDefaults() {
        BLOCK_TO_FLAMMABILITY.put(ESBlocks.LUNAR_LEAVES, LEAVES);
        BLOCK_TO_FLAMMABILITY.put(ESBlocks.NORTHLAND_LEAVES, LEAVES);
        BLOCK_TO_FLAMMABILITY.put(ESBlocks.STARLIGHT_MANGROVE_LEAVES, LEAVES);
        BLOCK_TO_FLAMMABILITY.put(ESBlocks.LUNAR_LOG, WOOD);
        BLOCK_TO_FLAMMABILITY.put(ESBlocks.LUNAR_WOOD, WOOD);
        BLOCK_TO_FLAMMABILITY.put(ESBlocks.STRIPPED_LUNAR_LOG, WOOD);
        BLOCK_TO_FLAMMABILITY.put(ESBlocks.STRIPPED_LUNAR_WOOD, WOOD);
        BLOCK_TO_FLAMMABILITY.put(ESBlocks.LUNAR_PLANKS, WOOD);
        BLOCK_TO_FLAMMABILITY.put(ESBlocks.NORTHLAND_LOG, WOOD);
        BLOCK_TO_FLAMMABILITY.put(ESBlocks.NORTHLAND_WOOD, WOOD);
        BLOCK_TO_FLAMMABILITY.put(ESBlocks.STRIPPED_NORTHLAND_LOG, WOOD);
        BLOCK_TO_FLAMMABILITY.put(ESBlocks.STRIPPED_NORTHLAND_WOOD, WOOD);
        BLOCK_TO_FLAMMABILITY.put(ESBlocks.NORTHLAND_PLANKS, WOOD);
        BLOCK_TO_FLAMMABILITY.put(ESBlocks.STARLIGHT_MANGROVE_LOG, WOOD);
        BLOCK_TO_FLAMMABILITY.put(ESBlocks.STARLIGHT_MANGROVE_WOOD, WOOD);
        BLOCK_TO_FLAMMABILITY.put(ESBlocks.STRIPPED_STARLIGHT_MANGROVE_LOG, WOOD);
        BLOCK_TO_FLAMMABILITY.put(ESBlocks.STRIPPED_STARLIGHT_MANGROVE_WOOD, WOOD);
        BLOCK_TO_FLAMMABILITY.put(ESBlocks.STARLIGHT_MANGROVE_PLANKS, WOOD);
    }

    public record Flammability (int catchOdds, int burnOdds) {

    }
}
