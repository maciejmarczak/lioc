package org.maciejmarczak.lioc;

import org.maciejmarczak.lioc.container.BeansContainer;

/**
 * Interface describing operations which every <b>IoC container</b> initializer
 * should support.
 *
 * Currently, only one action is required, which is fetching
 * a beans container via {@link ContainerInitializer#getBeansContainer()} method.
 */
public interface ContainerInitializer {

    /**
     * Returns an instance of ready to use, initialized {@link BeansContainer}.
     *
     * @return initialized {@link BeansContainer}
     */
    BeansContainer getBeansContainer();
}
