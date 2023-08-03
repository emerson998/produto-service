package com.algaworks.example.resilience4j.produtos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.core.registry.EntryAddedEvent;
import io.github.resilience4j.core.registry.EntryRemovedEvent;
import io.github.resilience4j.core.registry.EntryReplacedEvent;
import io.github.resilience4j.core.registry.RegistryEventConsumer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class CircuitBreakConfig {

	@Bean 
	public RegistryEventConsumer<CircuitBreaker> cbLog(){
		return new RegistryEventConsumer<CircuitBreaker> () {

			@Override
			public void onEntryAddedEvent(EntryAddedEvent<CircuitBreaker> entryAddedEvent) {
				entryAddedEvent.getAddedEntry().getEventPublisher()
				.onStateTransition(event -> {
					log.info(event.toString());
				});
				
			}

			@Override
			public void onEntryRemovedEvent(EntryRemovedEvent<CircuitBreaker> entryRemoveEvent) {
			}

			@Override
			public void onEntryReplacedEvent(EntryReplacedEvent<CircuitBreaker> entryReplacedEvent) {
			}
			
		};
		
	}
}
