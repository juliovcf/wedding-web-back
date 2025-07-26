package com.yourwedding.wedding_backend.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableAsync
@Slf4j
public class AsyncConfig {

    @Bean(name = "emailTaskExecutor")
    public Executor emailTaskExecutor() {
        log.info("Configurando thread pool para envío asíncrono de emails");
        
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2); // Número mínimo de hilos
        executor.setMaxPoolSize(5);  // Número máximo de hilos
        executor.setQueueCapacity(100); // Capacidad de la cola
        executor.setThreadNamePrefix("Email-"); // Prefijo para nombres de hilos
        executor.setKeepAliveSeconds(60); // Tiempo que los hilos inactivos permanecen vivos
        executor.setWaitForTasksToCompleteOnShutdown(true); // Esperar tareas al cerrar
        executor.setAwaitTerminationSeconds(60); // Tiempo máximo de espera al cerrar
        
        // Inicializar el executor
        executor.initialize();
        
        log.info("Thread pool configurado: corePoolSize={}, maxPoolSize={}, queueCapacity={}", 
                executor.getCorePoolSize(), executor.getMaxPoolSize(), executor.getQueueCapacity());
        
        return executor;
    }
}