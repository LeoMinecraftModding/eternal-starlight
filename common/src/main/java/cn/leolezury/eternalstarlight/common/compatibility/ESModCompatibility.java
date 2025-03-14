package cn.leolezury.eternalstarlight.common.compatibility;

import java.util.Optional;

import cn.leolezury.eternalstarlight.common.platform.ESPlatform;

public interface ESModCompatibility {

	public String getModId();
	
	public boolean isModLoaded();
	
	public Optional<String> getModVersion();
	
	public Optional<ESPlatform.Loader> getLoader();
	
}
