package cn.leolezury.eternalstarlight.block.modifier;

import com.google.common.collect.Maps;
import net.minecraft.world.level.block.Block;
import java.util.Map;
import java.util.function.Consumer;

public record ESFlammableBlockModifier(int catchOdds, int burnOdds) implements Consumer<Block> {
    private static final Map<Block, Entry> entries = Maps.newHashMap();
    public static final ESFlammableBlockModifier WOOD = create(5, 20);
    public static final ESFlammableBlockModifier LEAVES = create(30, 60);
    public static final ESFlammableBlockModifier TALL_FLOWER = create(60, 100);
    public static final ESFlammableBlockModifier IVY = create(60, 50);

    public record Entry(int catchOdds, int burnOdds) {

    }

    public static Entry getEntry(Block block) {
        return entries.get(block);
    }


    public static ESFlammableBlockModifier create(int catchOdds, int burnOdds) {
        return new ESFlammableBlockModifier(catchOdds, burnOdds);
    }

    public static void setBlockFlammable(Block block, int catchOdds, int burnOdds) {
        entries.put(block, new Entry(catchOdds, burnOdds));
    }

    @Override
    public void accept(Block block) {
        setBlockFlammable(block, catchOdds, burnOdds);
    }
}
