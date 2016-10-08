package org.maciejmarczak.lioc.container;

import java.util.HashMap;
import java.util.Map;

public final class BeansContainerImpl implements BeansContainer {

    private final Map<Class<?>, Object> beans = new HashMap<>();

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> type) {
        Map.Entry mapEntry = beans.entrySet()
                .stream()
                .filter(x -> x.getKey() == type)
                .findFirst()
                .orElse(null);

        return mapEntry == null ? null : (T) mapEntry.getValue();
    }

    @Override
    public <T> void putBean(T object) {
        beans.put(object.getClass(), object);
    }
}
