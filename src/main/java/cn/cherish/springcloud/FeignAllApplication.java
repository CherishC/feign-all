package cn.cherish.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class FeignAllApplication {

	public static void main(String[] args) {
//		new SpringApplicationBuilder(FeignAllApplication.class).web(true).run(args);
		SpringApplication.run(FeignAllApplication.class, args);
	}
}
