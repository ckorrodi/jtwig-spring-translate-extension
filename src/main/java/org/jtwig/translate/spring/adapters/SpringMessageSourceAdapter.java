package org.jtwig.translate.spring.adapters;

import com.google.common.base.Optional;
import org.jtwig.translate.message.source.MessageSource;

import java.util.Locale;

public class SpringMessageSourceAdapter implements MessageSource {
    private final org.springframework.context.MessageSource springMessageSource;

    public SpringMessageSourceAdapter(org.springframework.context.MessageSource springMessageSource) {
        this.springMessageSource = springMessageSource;
    }

    @Override
    public Optional<String> message(Locale locale, String message) {
        return Optional.fromNullable(springMessageSource.getMessage(message, new Object[]{}, null, locale));
    }
}
