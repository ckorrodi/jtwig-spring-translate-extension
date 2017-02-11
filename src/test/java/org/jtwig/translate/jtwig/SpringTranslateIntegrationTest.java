package org.jtwig.translate.jtwig;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.jtwig.environment.EnvironmentConfigurationBuilder;
import org.jtwig.spring.JtwigViewResolver;
import org.jtwig.spring.boot.config.JtwigViewResolverConfigurer;
import org.jtwig.translate.spring.SpringTranslateExtension;
import org.jtwig.translate.spring.SpringTranslateExtensionConfiguration;
import org.jtwig.web.servlet.JtwigRenderer;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.LocaleContextResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.nio.charset.Charset;
import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class SpringTranslateIntegrationTest {

    @Test
    public void integrationTest() throws Exception {
        AnnotationConfigEmbeddedWebApplicationContext configurableApplicationContext = (AnnotationConfigEmbeddedWebApplicationContext) SpringApplication.run(ExampleController.class, "--server.port=0");

        try (CloseableHttpResponse response = HttpClients.createDefault().execute(new HttpGet(String.format("http://localhost:%d/?lang=pt", configurableApplicationContext.getEmbeddedServletContainer().getPort())))) {
            String content = IOUtils.toString(response.getEntity().getContent(), Charset.defaultCharset());

            assertThat(content, is("Ola - Ola - Ciao"));
        }

        try (CloseableHttpResponse response = HttpClients.createDefault().execute(new HttpGet(String.format("http://localhost:%d/", configurableApplicationContext.getEmbeddedServletContainer().getPort())))) {
            String content = IOUtils.toString(response.getEntity().getContent(), Charset.defaultCharset());

            assertThat(content, is("Hello - Ola - Ciao"));
        }

        configurableApplicationContext.stop();
    }

    @EnableWebMvc
    @EnableAutoConfiguration
    @Controller
    public static class ExampleController extends WebMvcConfigurerAdapter implements JtwigViewResolverConfigurer {
        @Bean
        public ResourceBundleMessageSource resourceBundleMessageSource () {
            ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
            messageSource.setBasename("translation/example");
            return messageSource;
        }

        @RequestMapping
        public String indexAction () {
            return "index";
        }

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(getLocaleChangeInterceptor());
        }

        @Bean
        public LocaleChangeInterceptor getLocaleChangeInterceptor() {
            final LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
            interceptor.setParamName("lang");
            return interceptor;
        }

        @Bean(name="localeResolver")
        public LocaleContextResolver getLocaleContextResolver() {
            CookieLocaleResolver localeResolver = new CookieLocaleResolver();
            localeResolver.setDefaultLocale(Locale.US);
            return localeResolver;
        }

        @Override
        public void configure(JtwigViewResolver viewResolver) {
            viewResolver.setRenderer(new JtwigRenderer(EnvironmentConfigurationBuilder.configuration()
                    .extensions().add(new SpringTranslateExtension(SpringTranslateExtensionConfiguration
                            .builder(resourceBundleMessageSource())
                                .withLocaleResolver(getLocaleContextResolver())
                            .build())).and()
                    .build()));
        }
    }
}
