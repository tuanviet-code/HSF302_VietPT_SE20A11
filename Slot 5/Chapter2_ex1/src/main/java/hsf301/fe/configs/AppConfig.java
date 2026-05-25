package hsf301.fe.configs;

import hsf301.fe.aspects.LoggingAspect;
import hsf301.fe.services.StudentService;
import hsf301.fe.services.StudentServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class AppConfig {

    @Bean
    public StudentService myService() { return new StudentServiceImpl(); }

    @Bean
    public LoggingAspect loggingAspect() { return new LoggingAspect(); }
}
