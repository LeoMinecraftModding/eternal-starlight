package cn.leolezury.eternalstarlight.common.util;

import java.util.Map;

public record ModelSnapshot(float xRot, float yRot, float timestamp, Map<String, ModelPartPose> poses) {
}
