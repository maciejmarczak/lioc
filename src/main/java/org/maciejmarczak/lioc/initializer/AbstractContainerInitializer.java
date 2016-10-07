package org.maciejmarczak.lioc.initializer;

import org.maciejmarczak.lioc.ContainerInitializer;
import org.maciejmarczak.lioc.container.BeansContainer;
import org.maciejmarczak.lioc.container.BeansContainerImpl;

public abstract class AbstractContainerInitializer implements ContainerInitializer {

    final BeansContainer beansContainer = new BeansContainerImpl();

    @Override
    public BeansContainer getBeansContainer() {
        return beansContainer;
    }

}
