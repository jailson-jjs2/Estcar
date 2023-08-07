package br.com.estcar.Estcar.servico.autenticacao;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class LogininterceptorAppConfig extends WebMvcConfigurationSupport {
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginInterceptor())
		.excludePathPatterns(
				"/login",
				"/error",
				"/logar",
				"/vendor/**",
				"/js/**",
				"/img/**",
				"/login",
				"/favicon.ico",
				"/css/**"
				);
	}
}
