package cn.leolezury.eternalstarlight.common.client.book;

import cn.leolezury.eternalstarlight.common.client.book.component.BookComponentDefinition;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.List;

@Environment(EnvType.CLIENT)
public interface BookAccess {
	int getRelativePage();

	boolean isLeftPage();

	void setPage(int page);

	List<BookComponentDefinition> getComponents();
}
