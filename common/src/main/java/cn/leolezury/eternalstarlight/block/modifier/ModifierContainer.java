package cn.leolezury.eternalstarlight.block.modifier;

import java.util.function.Consumer;

public record ModifierContainer<T>(T type, Consumer<T>... modifiers) {
    @SafeVarargs
    public ModifierContainer {

    }

    public void initModifiers() {
        for (Consumer<T> modifier : modifiers) {
            modifier.accept(type);
        }
    }
}
