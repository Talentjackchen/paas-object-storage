package com.wondersgroup.cloud.paas.storage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.context.ApplicationContext;


/**
 * @author chenlong
 */
@SpringBootApplication(exclude = { FreeMarkerAutoConfiguration.class })
@MapperScan(value = "com.wondersgroup.cloud.paas.storage.mapper")
public class PaasObjectStorage {
    public static void main(String[] args){
        ApplicationContext applicationContext = SpringApplication.run(PaasObjectStorage.class);
    }
}
