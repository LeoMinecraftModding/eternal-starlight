package cn.leolezury.eternalstarlight.common.compatibility;

import java.util.Optional;

import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import net.minecraft.resources.ResourceLocation;

public interface ESModCompatibility {

	public String getModId();
	
	public default ResourceLocation location(String path) {
		return ResourceLocation.fromNamespaceAndPath(this.getModId(), path);
	}
	
	public boolean isModLoaded();
	
	public Optional<String> getModVersion();
	
	public Optional<ESPlatform.Loader> getLoader();
	
}
