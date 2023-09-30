package cn.leolezury.eternalstarlight.common.world.gen.areasystem.datatransformer.biome;

import cn.leolezury.eternalstarlight.common.world.gen.areasystem.area.BiomesContainer;
import cn.leolezury.eternalstarlight.common.world.gen.areasystem.datatransformer.biome.interfaces.NeighborsRelatedTransformer;

import java.util.Random;

public class AssimilateBiomesTransformer implements NeighborsRelatedTransformer {
    private final boolean onlyLonely;

    public AssimilateBiomesTransformer(boolean onlyLonely) {
        this.onlyLonely = onlyLonely;
    }

    @Override
    public int transform(BiomesContainer container, Random random, int original, int up, int down, int left, int right) {
        if (!onlyLonely || (original != up && original != down && original != left && original != right)) {
            if (up == left && up == right) {
                return up;
            }
            if (down == left && down == right) {
                return down;
            }
            if (left == up && left == down) {
                return left;
            }
            if (right == up && right == down) {
                return right;
            }
            if (up == left || up == right) {
                return up;
            }
            if (down == left || down == right) {
                return down;
            }
            if (up == down) {
                return up;
            }
            if (left == right) {
                return left;
            }
        }
        return onlyLonely ? original : chooseRandomly(random, up, down, left, right);
    }
}
