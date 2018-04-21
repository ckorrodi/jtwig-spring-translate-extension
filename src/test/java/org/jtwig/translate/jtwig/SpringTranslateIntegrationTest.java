package org.jtwig.translate.jtwig;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;

import java.nio.charset.Charset;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SpringTranslateIntegrationTest {

    @Test
    public void integrationTest() throws Exception {
        AnnotationConfigServletWebServerApplicationContext run = (AnnotationConfigServletWebServerApplicationContext) SpringApplication.run(ExampleController.class, "--server.port=0");

        try (CloseableHttpResponse response = HttpClients.createDefault().execute(new HttpGet(String.format("http://localhost:%d/?lang=pt", run.getWebServer().getPort())))) {
            String content = IOUtils.toString(response.getEntity().getContent(), Charset.defaultCharset());

            assertThat(content, is("Ola - Ola - Ciao"));
        }

        try (CloseableHttpResponse response = HttpClients.createDefault().execute(new HttpGet(String.format("http://localhost:%d/", run.getWebServer().getPort())))) {
            String content = IOUtils.toString(response.getEntity().getContent(), Charset.defaultCharset());

            assertThat(content, is("Hello - Ola - Ciao"));
        }

        run.stop();
    }

}
