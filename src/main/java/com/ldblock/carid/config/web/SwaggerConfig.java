package com.ldblock.carid.config.web;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * 配置swagger
 * @author 老徐
 */
@Configuration
public class SwaggerConfig implements WebMvcConfigurer {

	@Value("#{ @environment['swagger.enable'] ?: true }")
	private boolean swaggerEnable;

	@Value("${server.port}")
	private String serverPort;

	private String baseUrl = "";

	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.OAS_30)
				.enable(swaggerEnable)
				.groupName("ldblock").select() // 选择那些路径和api会生成document
				.apis(RequestHandlerSelectors.basePackage("com.ldblock.carid.controller")).paths(PathSelectors.any()) // 对所有路径进行监控
				.build().apiInfo(apiInfo()).securitySchemes(securitySchemes())
				.securityContexts(securityContexts())
				;
	}

	private ApiInfo apiInfo() {
		ApiInfo apiInfo = new ApiInfoBuilder().title("车主身份认证系统").description("车主身份认证系统").version("1.0.0").build();
		return apiInfo;
	}
    
  //通过pathRegex获取SecurityContext对象
    private List<SecurityContext> securityContexts() {
        return Arrays.asList(SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any())
                .build());
    }
    
    //默认为全局的SecurityReference对象
    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global",
                "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("Authorization", authorizationScopes));
    }
	
	private List<SecurityScheme> securitySchemes() {
		//return Arrays.asList(HttpAuthenticationScheme.JWT_BEARER_BUILDER.build());
		return Arrays.asList(new ApiKey("Authorization", "Bearer", "header"));
	}

	@SuppressWarnings("unchecked")
	public void addInterceptors(InterceptorRegistry registry) {
		try {
			Field registrationsField = FieldUtils.getField(InterceptorRegistry.class, "registrations", true);			
			List<InterceptorRegistration> registrations = (List<InterceptorRegistration>) ReflectionUtils
					.getField(registrationsField, registry);
			if (registrations != null) {
				for (InterceptorRegistration interceptorRegistration : registrations) {
					interceptorRegistration.excludePathPatterns("/swagger**/**").excludePathPatterns("/webjars/**")
							.excludePathPatterns("/v3/**").excludePathPatterns("/doc.html");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String baseUrl = StringUtils.trimTrailingCharacter(this.baseUrl, '/');
		registry.addResourceHandler(baseUrl + "/swagger-ui/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
				.resourceChain(false);
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController(baseUrl + "/swagger-ui/")
				.setViewName("forward:" + baseUrl + "/swagger-ui/index.html");
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/api/pet").allowedOrigins("http://editor.swagger.io");
		registry.addMapping("/v2/api-docs.*").allowedOrigins("http://editor.swagger.io");
	}
}
