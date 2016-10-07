package org.maciejmarczak.lioc;

import org.maciejmarczak.lioc.annotation.Bean;

@Bean
public class AnotherBean {

    void talk() {
        System.out.println("hello!");
    }
}
