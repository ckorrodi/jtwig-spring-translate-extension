package org.jtwig.translate.spring.adapters;

import com.google.common.base.Supplier;
import org.jtwig.web.servlet.ServletRequestHolder;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;

public class CurrentLocaleResolverSpringAdapter implements Supplier<Locale> {
    private final LocaleResolver localeResolver;

    public CurrentLocaleResolverSpringAdapter(LocaleResolver localeResolver) {
        this.localeResolver = localeResolver;
    }

    @Override
    public Locale get() {
        return localeResolver.resolveLocale(ServletRequestHolder.get());
    }
}
