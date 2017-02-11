package org.jtwig.translate.spring.adapters;

import org.jtwig.environment.Environment;
import org.jtwig.translate.message.source.MessageSource;
import org.jtwig.translate.message.source.factory.MessageSourceFactory;

public class SpringMessageSourceFactory implements MessageSourceFactory {
    public static SpringMessageSourceFactory create(org.springframework.context.MessageSource messageSource) {
        return new SpringMessageSourceFactory(messageSource);
    }

    private final org.springframework.context.MessageSource messageSource;

    private SpringMessageSourceFactory(org.springframework.context.MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public MessageSource create(Environment environment) {
        return new SpringMessageSourceAdapter(messageSource);
    }
}
