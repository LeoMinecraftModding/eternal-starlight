package cn.leolezury.eternalstarlight.neoforge.compatibility.neoforge;

import java.util.Optional;
import java.util.StringTokenizer;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.maven.artifact.versioning.ArtifactVersion;

import cn.leolezury.eternalstarlight.common.compatibility.ESModCompatibility;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform.Loader;
import net.neoforged.fml.ModList;

public interface NeoForgeModCompatibility extends ESModCompatibility {

	@Override
	public default boolean isModLoaded() {
		return ModList.get().isLoaded(this.getModId());
	}

	@Override
	public default Optional<String> getModVersion() {
		if(!isModLoaded()) {
			return Optional.empty();
		} else {
			return ModList.get()
					.getModContainerById(this.getModId())
					.map(container -> {
						ArtifactVersion artifactVersion = container.getModInfo().getVersion();
						// Strip to just the components if possible
						final String qualifier = artifactVersion.getQualifier();
						final int buildDelimPos = qualifier.indexOf('+');
						final int dashDelimPos = qualifier.indexOf('-');
						if(buildDelimPos < 0 && dashDelimPos <= 0) {
							return qualifier;
						} else {
							int index = qualifier.length();
							if(buildDelimPos != -1) {
								index = buildDelimPos;
							}
							if(dashDelimPos != -1 && dashDelimPos < index) {
								index = dashDelimPos;
							}
							String version = qualifier.substring(0, index);
							StringTokenizer tokenizer = new StringTokenizer(version, ".");
							StringBuilder builder = new StringBuilder();
							boolean prev = false;
							while(tokenizer.hasMoreTokens()) {
								String token = tokenizer.nextToken();
								if(NumberUtils.isDigits(token)) {
									if(prev) {
										builder.append('.');
									}
									builder.append(token);
									prev = true;
								} else {
									break;
								}
							}
							return builder.toString();
						}
					}
				);
		}
	}

	@Override
	public default Optional<Loader> getLoader() {
		return Optional.of(Loader.NEOFORGE);
	}
}
