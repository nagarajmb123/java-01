package io.aiven.spring.mysql.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer
                .defaultContentType(MediaType.APPLICATION_JSON)
                .favorParameter(false);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJackson2HttpMessageConverter());
    }
}
/*
This configuration class serves two main purposes in simple terms:

1. `configureContentNegotiation`:
   - Sets JSON as the default response format for your API
   - Disables content type selection through URL parameters

2. `configureMessageConverters`:
   - Adds Jackson converter to automatically convert Java objects to JSON and vice versa
   - Ensures proper serialization/deserialization of your API requests and responses

 **Serialization**: Converting Java objects to JSON (when sending responses)
 **Deserialization**: Converting JSON to Java objects (when receiving requests)

In essence, it's telling your Spring application
"Always use JSON for communication, and here's how to convert between Java objects and JSON."
 */