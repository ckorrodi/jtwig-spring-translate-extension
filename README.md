# Jtwig Translate Extension

**Build Stats**

[![Build Status](https://travis-ci.org/jtwig/jtwig-spring-translate-extension.svg?branch=master)](https://travis-ci.org/jtwig/jtwig-spring-translate-extension)
[![codecov](https://codecov.io/gh/jtwig/jtwig-spring-translate-extension/branch/master/graph/badge.svg)](https://codecov.io/gh/jtwig/jtwig-spring-translate-extension)
[![Download](https://api.bintray.com/packages/jtwig/maven/jtwig-spring-translate-extension/images/download.svg) ](https://bintray.com/jtwig/maven/jtwig-spring-translate-extension/_latestVersion)

**How to use?**

With spring boot.

```java
import org.jtwig.environment.EnvironmentConfigurationBuilder;
import org.jtwig.spring.JtwigViewResolver;
import org.jtwig.spring.boot.config.JtwigViewResolverConfigurer;
import org.jtwig.translate.spring.SpringTranslateExtension;
import org.jtwig.translate.spring.SpringTranslateExtensionConfiguration;
import org.jtwig.web.servlet.JtwigRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;

@Configuration
public class JtwigMvcConfiguration implements JtwigViewResolverConfigurer {
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private LocaleResolver localeResolver;

    @Override
    public void configure(JtwigViewResolver viewResolver) {
        viewResolver.setRenderer(new JtwigRenderer(EnvironmentConfigurationBuilder.configuration()
                .extensions().add(new SpringTranslateExtension(SpringTranslateExtensionConfiguration
                        .builder(messageSource)
                        .withLocaleResolver(localeResolver)
                        .build())).and()
                .build()));
    }
}

```

**Licensing**

[![Apache License](https://img.shields.io/hexpm/l/plug.svg?maxAge=2592000)]()

**Requirements**

- Java 7
