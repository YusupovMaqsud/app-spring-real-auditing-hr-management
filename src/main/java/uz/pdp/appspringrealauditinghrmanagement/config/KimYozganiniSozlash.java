package uz.pdp.appspringrealauditinghrmanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.UUID;

@Configuration
@EnableJpaAuditing
public class KimYozganiniSozlash {
    @Bean
    AuditorAware<UUID> auditorAware() {
        return new KimYozganiniBilish();
    }
}
