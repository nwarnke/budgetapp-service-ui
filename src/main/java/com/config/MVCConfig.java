package com.config;

import com.controller.MainController;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

@ComponentScan(basePackageClasses = {MainController.class})
@Import({JdbcConfig.class, DaoConfig.class, I18NConfig.class})
@Configuration
public class MVCConfig extends WebMvcConfigurationSupport 
{

	@Override
	public void addInterceptors(final InterceptorRegistry registry)
	{
		registry.addInterceptor(buildWebContentInterceptor());

		super.addInterceptors(registry);
	}


	private HandlerInterceptor buildWebContentInterceptor()
	{
		final WebContentInterceptor interceptor = new WebContentInterceptor();
		interceptor.setCacheSeconds(0);
		interceptor.setUseExpiresHeader(Boolean.TRUE);
		interceptor.setUseCacheControlHeader(Boolean.TRUE);
		interceptor.setUseCacheControlNoStore(Boolean.TRUE);

		return interceptor;
	}
}
