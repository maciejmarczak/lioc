package org.maciejmarczak.lioc.initializer;

import org.maciejmarczak.lioc.annotation.Bean;
import org.maciejmarczak.lioc.annotation.Inject;
import org.maciejmarczak.lioc.container.BeansContainer;
import org.maciejmarczak.lioc.exception.ContainerInitializationException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.stream.Collectors;

class BeansLoader {

    private static int checkAndAddClass(BeansContainer beansContainer, String name, String basePackage) {
        int loadedBeans = 0;

        try {
            Class<?> loadedClass = Class.forName(basePackage + "." + name);

            if (loadedClass.isAnnotationPresent(Bean.class)) {
                Constructor<?>[] constructors =
                        Arrays.stream(loadedClass.getConstructors())
                                .filter((c) -> c.isAnnotationPresent(Inject.class))
                                .toArray(Constructor<?>[]::new);

                if (constructors.length > 1) {
                    throw new ContainerInitializationException();
                }

                if (constructors.length == 0) {
                    beansContainer.putBean(loadedClass.newInstance());
                    return ++loadedBeans;
                }

                Class<?>[] paramTypes = constructors[0].getParameterTypes();
                Object[] finalParams = new Object[paramTypes.length];

                for (int i = 0; i < paramTypes.length; i++) {
                    Object bean = beansContainer.getBean(paramTypes[i]);

                    if (bean != null) {
                        finalParams[i] = bean;
                    } else {
                        loadedBeans += checkAndAddClass(beansContainer, paramTypes[i].getSimpleName(),
                                paramTypes[i].getPackage().getName());
                    }
                }

                beansContainer.putBean(constructors[0].newInstance(finalParams));
                loadedBeans++;
            }

        } catch (ClassNotFoundException | IllegalAccessException |
                InstantiationException | InvocationTargetException e) {
            throw new ContainerInitializationException();
        }

        return loadedBeans;
    }

    private static int fillContainerFromPath(BeansContainer beansContainer, String path, String basePackage) {
        File root = new File(path);
        File[] rootFiles = root.listFiles();

        if (rootFiles == null) {
            return 0;
        }

        int loadedBeans = 0;

        for (File file : rootFiles) {
            if (file.isDirectory()) {
                loadedBeans += fillContainerFromPath(beansContainer, file.getPath(),
                        basePackage + "." + file.getName());
                continue;
            }

            if (file.getName().endsWith(".class")) {
                loadedBeans += checkAndAddClass(beansContainer, file.getName().replaceAll("\\.class", ""), basePackage);
            }
        }

        return loadedBeans;
    }

    private static int loadFromPackage(BeansContainer beansContainer, String basePackage) {
        Enumeration<URL> roots;
        int loadedBeans = 0;

        try {
            roots = ClassLoader.getSystemClassLoader().getResources("");

            while (roots.hasMoreElements()) {
                String path = roots.nextElement().getPath();
                path += basePackage.replaceAll("\\.", File.separator);

                loadedBeans += fillContainerFromPath(beansContainer, path, basePackage);
            }

        } catch (IOException ioe) {
            throw new ContainerInitializationException();
        }

        return loadedBeans;
    }

    static int loadFromMultiplePackages(final BeansContainer beansContainer, String[] basePackages) {
        return Arrays.stream(basePackages)
                .collect(Collectors.summingInt((pkg) -> loadFromPackage(beansContainer, pkg)));
    }

}
