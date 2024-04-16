package dk.sdu.deviceservice.util;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class ConfigLogger {
    @Bean
    public ApplicationRunner applicationRunner(AbstractEnvironment environment) {
        return args -> {
            environment.getPropertySources()
                    .stream()
                    .filter(p -> p instanceof OriginTrackedMapPropertySource)
                    .map(p -> (OriginTrackedMapPropertySource)p)
                    .flatMap(p -> Arrays.stream(p.getPropertyNames())).forEach(x -> System.out.printf("\n%s = %s\n", x, environment.getProperty(x)));
        };
    }
}
