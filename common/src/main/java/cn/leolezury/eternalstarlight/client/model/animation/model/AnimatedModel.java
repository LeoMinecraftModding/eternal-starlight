package cn.leolezury.eternalstarlight.client.model.animation.model;

import net.minecraft.client.model.geom.ModelPart;

import java.util.Optional;

public interface AnimatedModel {
    ModelPart root();
    default Optional<ModelPart> getAnyDescendantWithName(String name) {
        return name.equals("root") ? Optional.of(root()) : root().getAllParts().filter((part) -> part.hasChild(name)).findFirst().map((part) -> part.getChild(name));
    }
}
