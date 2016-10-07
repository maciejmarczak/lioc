package org.maciejmarczak.lioc;

import org.maciejmarczak.lioc.initializer.AnnotationBasedContainerInitializer;

public class AppRunner {
    public static void main(String[] args) {
        ContainerInitializer ci = new AnnotationBasedContainerInitializer("org.maciejmarczak.lioc");

        ci.getBeansContainer().getBean(SampleBean.class).bean.talk();
    }
}
