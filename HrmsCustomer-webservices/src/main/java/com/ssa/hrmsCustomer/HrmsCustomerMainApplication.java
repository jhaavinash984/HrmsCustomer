
package com.ssa.hrmsCustomer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.ssa.hrmsCustomer.dao")
@EnableMongoRepositories(basePackages = {"com.ssa.hrmsCustomer.dao.mongodb"})
//@EnableEurekaClient
@EnableSwagger2
public class HrmsCustomerMainApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(HrmsCustomerMainApplication.class,args);

	}

}
