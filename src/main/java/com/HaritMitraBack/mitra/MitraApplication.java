package com.HaritMitraBack.mitra;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@EnableRabbit
@SpringBootApplication(scanBasePackages = "com.HaritMitraBack")

public class MitraApplication {

	public static void main(String[] args) {
		SpringApplication.run(MitraApplication.class, args);
	}

}
