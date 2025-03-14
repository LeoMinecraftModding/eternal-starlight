package cn.leolezury.eternalstarlight.fabric.compatibility.fabric;

import java.util.Optional;

import cn.leolezury.eternalstarlight.common.compatibility.ESModCompatibility;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform.Loader;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.SemanticVersion;
import net.fabricmc.loader.api.Version;

public interface FabricModCompatibility extends ESModCompatibility {

	@Override
	public default boolean isModLoaded() {
		return FabricLoader.getInstance().isModLoaded(this.getModId());
	}

	@Override
	public default Optional<String> getModVersion() {
		if(!isModLoaded()) {
			return Optional.empty();
		} else {
			return FabricLoader.getInstance()
					.getModContainer(this.getModId())
					.map(container -> {
						Version version = container.getMetadata().getVersion();
						// Strip to just the components if possible
						if(version instanceof SemanticVersion semanticVersion) {
							final int componentCount = semanticVersion.getVersionComponentCount();
							StringBuilder componentOnlyBuilder = new StringBuilder(componentCount * 2 - 1);
							for(int i = 0; i < componentCount - 1; ++i) {
								componentOnlyBuilder.append(semanticVersion.getVersionComponent(i));
								componentOnlyBuilder.append('.');
							}
							componentOnlyBuilder.append(semanticVersion.getVersionComponent(componentCount - 1));
							return componentOnlyBuilder.toString();
						} else {
							return version.getFriendlyString();
						}
					}
				);
		}
	}

	@Override
	public default Optional<Loader> getLoader() {
		return Optional.of(Loader.FABRIC);
	}
}
