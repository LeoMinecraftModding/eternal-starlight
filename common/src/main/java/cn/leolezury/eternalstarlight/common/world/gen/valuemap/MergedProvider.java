package cn.leolezury.eternalstarlight.common.world.gen.valuemap;

import net.minecraft.world.phys.Vec3;

import java.util.List;

public record MergedProvider(List<Entry> providers) implements ValueMapProvider {
    @Override
    public float getValue(float x, float y, float z) {
        float sum = 0;
        int count = 0;
        for (Entry entry : providers()) {
            float value = entry.provider().getValue((float) (x - entry.offset().x()), (float) (y - entry.offset().y()), (float) (z - entry.offset().z()));
            if (value < 1 && value >= 0) {
                sum += value;
                count++;
            }
        }
        return count == 0 ? 1 : sum / count;
    }

    public record Entry(ValueMapProvider provider, Vec3 offset) {

    }
}
