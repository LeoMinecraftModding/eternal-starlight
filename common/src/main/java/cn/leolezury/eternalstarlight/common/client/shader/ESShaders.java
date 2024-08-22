package cn.leolezury.eternalstarlight.common.client.shader;

import net.minecraft.client.renderer.ShaderInstance;

public class ESShaders {
	private static ShaderInstance crestSelectionGui;
	private static ShaderInstance renderTypeLaserBeam;
	private static ShaderInstance renderTypeStarlightPortal;
	private static ShaderInstance renderTypeEclipse;

	public static ShaderInstance getCrestSelectionGui() {
		return crestSelectionGui;
	}

	public static void setCrestSelectionGui(ShaderInstance crestSelectionGui) {
		ESShaders.crestSelectionGui = crestSelectionGui;
	}

	public static ShaderInstance getRenderTypeLaserBeam() {
		return renderTypeLaserBeam;
	}

	public static void setRenderTypeLaserBeam(ShaderInstance renderTypeLaserBeam) {
		ESShaders.renderTypeLaserBeam = renderTypeLaserBeam;
	}

	public static ShaderInstance getRenderTypeStarlightPortal() {
		return renderTypeStarlightPortal;
	}

	public static void setRenderTypeStarlightPortal(ShaderInstance renderTypeStarlightPortal) {
		ESShaders.renderTypeStarlightPortal = renderTypeStarlightPortal;
	}

	public static ShaderInstance getRenderTypeEclipse() {
		return renderTypeEclipse;
	}

	public static void setRenderTypeEclipse(ShaderInstance renderTypeEclipse) {
		ESShaders.renderTypeEclipse = renderTypeEclipse;
	}
}
