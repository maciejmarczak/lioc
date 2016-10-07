package org.maciejmarczak.lioc;

import org.maciejmarczak.lioc.annotation.Bean;
import org.maciejmarczak.lioc.annotation.Inject;

@Bean()
public class SampleBean {

    AnotherBean bean;

    @Inject()
    public SampleBean(AnotherBean bean) {
        this.bean = bean;
    }

}
