package org.example.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class WebApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}

	@Bean
    public CommonsRequestLoggingFilter commonsRequestLoggingFilter() {
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        return filter;
    }

    /**
     *
     * {@link org.springframework.boot.context.event.ApplicationStartedEvent}
     * {@link org.springframework.boot.context.event.ApplicationReadyEvent}
     * {@link org.springframework.boot.SpringApplication#run(String...)}
     */
    @Bean
    public ApplicationListener<ApplicationEvent> applicationEventListener() {

        return event -> log.info("Event: {}", event.getClass().getSimpleName());
    }
}
