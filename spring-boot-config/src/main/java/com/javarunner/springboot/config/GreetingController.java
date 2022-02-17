package com.javarunner.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RefreshScope
public class GreetingController {

    @Value("${my.greeting:default message!}")
    private String greetingMessage;

    @Value("This is static message!")
    private String staticMessage;

    @Value("${my.list.values}")
    private List<String> listValues;

    @Autowired
    private DbSettings dbSettings;

    @Autowired
    private Environment environment;

    //@Value("#{${dbValues}}")
    //private Map<String,String> dbValues;


    @GetMapping("/greeting")
    public String greeting(){
        return greetingMessage + staticMessage + listValues +dbSettings.getHost();
    }
    @GetMapping("/envdetails")
    public String getEnvironmentDetails(){
        return environment.getProperty("db.host");
    }

}
