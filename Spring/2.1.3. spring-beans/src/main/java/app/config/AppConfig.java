package app.config;

import app.model.Cat;
import app.model.Dog;
import app.model.Timer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan(basePackages = "app")
public class AppConfig {
    @Bean
    public Dog getDog(){return new Dog();}
    @Bean
    public Cat getCat(){return new Cat();}
    @Bean
    public Timer getTimer(){return new Timer();}
}
