package cn.leolezury.eternalstarlight.client.model.animation.model;

import cn.leolezury.eternalstarlight.client.model.animation.SLKeyframeAnimations;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.LivingEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.joml.Vector3f;


public abstract class AnimatedHumanoidModel<E extends LivingEntity> extends HumanoidModel<E> implements AnimatedModel {
    public AnimatedHumanoidModel(ModelPart part) {
        super(part);
    }
}
