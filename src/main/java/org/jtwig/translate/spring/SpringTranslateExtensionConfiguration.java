package org.jtwig.translate.spring;

import com.google.common.base.Optional;
import org.jtwig.translate.message.source.cache.MessageSourceCache;
import org.jtwig.translate.message.source.cache.PersistentMessageSourceCache;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.LocaleResolver;


public class SpringTranslateExtensionConfiguration {
    public static Builder builder (MessageSource messageSource) {
        return new Builder(messageSource);
    }

    private final MessageSource messageSource;
    private final Optional<LocaleResolver> localeResolver;
    private final Optional<MessageSourceCache> cache;

    SpringTranslateExtensionConfiguration(MessageSource messageSource, Optional<LocaleResolver> localeResolver, Optional<MessageSourceCache> cache) {
        this.messageSource = messageSource;
        this.localeResolver = localeResolver;
        this.cache = cache;
    }

    public MessageSource getMessageSource() {
        return messageSource;
    }

    public Optional<LocaleResolver> getLocaleResolver() {
        return localeResolver;
    }

    public Optional<MessageSourceCache> getCache() {
        return cache;
    }

    public static class Builder implements org.apache.commons.lang3.builder.Builder<SpringTranslateExtensionConfiguration> {
        private final MessageSource messageSource;
        private Optional<LocaleResolver> localeResolver = Optional.absent();
        private Optional<MessageSourceCache> cache = Optional.<MessageSourceCache>of(PersistentMessageSourceCache.persistentCache());

        Builder(MessageSource messageSource) {
            this.messageSource = messageSource;
        }

        public Builder withLocaleResolver(LocaleResolver localeResolver) {
            this.localeResolver = Optional.fromNullable(localeResolver);
            return this;
        }

        public Builder withCache(MessageSourceCache cache) {
            this.cache = Optional.fromNullable(cache);
            return this;
        }

        public Builder withoutCache () {
            this.cache = Optional.absent();
            return this;
        }

        @Override
        public SpringTranslateExtensionConfiguration build() {
            return new SpringTranslateExtensionConfiguration(messageSource, localeResolver, cache);
        }
    }
}
