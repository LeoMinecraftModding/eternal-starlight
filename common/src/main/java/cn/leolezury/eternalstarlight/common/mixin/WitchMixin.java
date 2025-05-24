package cn.leolezury.eternalstarlight.common.mixin;

import cn.leolezury.eternalstarlight.common.entity.interfaces.StarlightWitch;
import net.minecraft.world.entity.monster.Witch;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.Objects;

@Mixin(Witch.class)
public abstract class WitchMixin implements StarlightWitch {
	@Unique
	private String starlightWitchType = "";
	@Unique
	private boolean starlightWitchTypeDirty = false;

	@Override
	public String getWitchType() {
		return starlightWitchType;
	}

	@Override
	public void setWitchType(String witchType) {
		if (!Objects.equals(witchType, starlightWitchType)) {
			starlightWitchTypeDirty = true;
		}
		this.starlightWitchType = witchType;
	}

	@Override
	public boolean isWitchTypeDirty() {
		return starlightWitchTypeDirty;
	}

	@Override
	public void setWitchTypeDirty(boolean dirty) {
		starlightWitchTypeDirty = dirty;
	}
}
