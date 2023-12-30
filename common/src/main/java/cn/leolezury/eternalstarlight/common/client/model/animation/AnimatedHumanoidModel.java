package cn.leolezury.eternalstarlight.common.client.model.animation;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;


@Environment(EnvType.CLIENT)
public abstract class AnimatedHumanoidModel<E extends LivingEntity> extends HumanoidModel<E> implements AnimatedModel {
    public AnimatedHumanoidModel(ModelPart part) {
        super(part);
    }
}
