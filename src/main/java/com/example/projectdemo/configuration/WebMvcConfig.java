package com.example.projectdemo.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 *
 **/
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
  public final Logger LOG = LoggerFactory.getLogger(WebMvcConfig.class);

  @Value("${img.win_location}")
  private String win_location;
  @Value("${img.linux_location}")
  private String linux_location;

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {

    if (!registry.hasMappingForPattern("/static/**")) {
      String property = System.getProperty("os.name");
      if (property.startsWith("Win")) {
        registry.addResourceHandler("/static/**").addResourceLocations("file:" + win_location);
      } else {
        registry.addResourceHandler("/static/**").addResourceLocations("file:" + linux_location);
      }
    }
    super.addResourceHandlers(registry);
  }

//  @Override
//  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
//    argumentResolvers.add(new ObjectConvertResolver());
//  }

}
