package org.maciejmarczak.lioc.initializer;

public final class AnnotationBasedContainerInitializer extends AbstractContainerInitializer {

    public AnnotationBasedContainerInitializer(String... basePackages) {
        BeansLoader.loadFromMultiplePackages(beansContainer, basePackages);
    }

}
