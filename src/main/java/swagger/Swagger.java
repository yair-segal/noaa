package noaa;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.*;
import java.io.*;

@Configuration
@EnableSwagger2
public class Swagger extends WebMvcConfigurationSupport {
    @Bean
    public Docket productApi() {

	// get host address
        String host_addr;
	try {
	    // assume we are running on the web
            URL url = new URL("http://checkip.amazonaws.com");
	    BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            host_addr = in.readLine();
	} catch (java.io.IOException ex) {
	    Logger logger = LoggerFactory.getLogger(Application.class);
            logger.error("Failed to get public host ip. Swagger will fail to execute HTTP requets when browsing to external ip. Browsing to localhost will work");
	    host_addr = "127.0.0.1:8080";
	}

        return new Docket(DocumentationType.SWAGGER_2)
            .host(host_addr)
            .select()
            .apis(RequestHandlerSelectors.basePackage("noaa"))
            .build()
            .apiInfo(metaData());
    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder()
            .title("NOAA")
            .version("1.0.0")
            .build();
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
            .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
            .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
