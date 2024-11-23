package dev.kauanmocelin.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {
    "dev.kauanmocelin.customer",
    "dev.kauanmocelin.amqp",
})
@EnableEurekaClient
@EnableFeignClients(basePackages = "dev.kauanmocelin.clients")
public class CustomerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class, args);
    }
}
