package org.jtwig.translate.spring;

import org.jtwig.translate.TranslateExtension;
import org.jtwig.translate.configuration.TranslateConfiguration;
import org.jtwig.translate.configuration.TranslateConfigurationBuilder;
import org.jtwig.translate.message.source.cache.CachedMessageSourceFactory;
import org.jtwig.translate.spring.adapters.CurrentLocaleResolverSpringAdapter;
import org.jtwig.translate.spring.adapters.SpringMessageSourceFactory;

public class SpringTranslateExtension extends TranslateExtension {
    public SpringTranslateExtension(SpringTranslateExtensionConfiguration configuration) {
        super(createConfiguration(configuration));
    }

    private static TranslateConfiguration createConfiguration(SpringTranslateExtensionConfiguration configuration) {
        TranslateConfigurationBuilder configurationBuilder = TranslateConfigurationBuilder.translateConfiguration();

        if (configuration.getCache().isPresent()) {
            configurationBuilder.withMessageSourceFactory(CachedMessageSourceFactory.cachedWith(
                    configuration.getCache().get(), SpringMessageSourceFactory.create(configuration.getMessageSource())
            ));
        } else {
            configurationBuilder.withMessageSourceFactory(SpringMessageSourceFactory.create(configuration.getMessageSource()));
        }

        if (configuration.getLocaleResolver().isPresent()) {
            configurationBuilder.withCurrentLocaleSupplier(new CurrentLocaleResolverSpringAdapter(configuration.getLocaleResolver().get()));
        }

        return configurationBuilder.build();
    }
}
