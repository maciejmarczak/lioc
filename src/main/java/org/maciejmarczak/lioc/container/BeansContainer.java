package org.maciejmarczak.lioc.container;

public interface BeansContainer {

    <T> T getBean(Class<T> type);

    <T> void putBean(T object);

}
