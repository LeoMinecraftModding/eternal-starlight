package cn.leolezury.eternalstarlight.common.client.book;

import cn.leolezury.eternalstarlight.common.client.book.component.BookComponentDefinition;

import java.util.List;

public interface BookAccess {
    int getRelativePage();
    boolean isLeftPage();
    void setPage(int page);
    List<BookComponentDefinition> getComponents();
}
