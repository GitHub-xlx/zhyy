package com.zhyy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *@author Administrator
 *Date 2020/2/13
 */
@SpringBootApplication
@RestController
public class MySpringbootApplication implements WebMvcConfigurer
{
	public static void main(String[] args)

	{
		SpringApplication.run(MySpringbootApplication.class);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
		WebMvcConfigurer.super.addResourceHandlers(registry);
	}

	private static final Logger LOG = LogManager.getLogger(MySpringbootApplication.class);

	@GetMapping("/test")
	public String test() {
		LOG.debug("debug 级别日志 ...");
		LOG.info("info 级别日志 ...");
		LOG.warn("warn 级别日志 ...");
		LOG.error("error 级别日志 ...");
		LOG.fatal("fatal 级别日志 ...");
		return "Hello Log4j2 !";
	}
}
