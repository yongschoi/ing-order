package yongs.temp.config;

import java.util.concurrent.TimeUnit;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Logger;
import feign.Request;

@Configuration
@EnableFeignClients(basePackages="yongs.temp.httpclient")
public class FeignConfig {
	@Bean
	public Request.Options options() {
		// connectTimeout, readTimeout
		return new Request.Options(5000, TimeUnit.MILLISECONDS, 10000, TimeUnit.MILLISECONDS, false);
	}
	@Bean
	Logger.Level feignLoggerLevel() {
		return Logger.Level.BASIC;
	}
}
