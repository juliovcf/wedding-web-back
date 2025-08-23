package com.yourwedding.wedding_backend.config;

import java.net.URI;
import java.net.URISyntaxException;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class DatabaseConfig {

    @Bean
    @Primary
    public DataSource dataSource() {
        String databaseUrl = System.getenv("DATABASE_URL");
        
        if (databaseUrl != null) {
            log.info("DATABASE_URL encontrado: {}", databaseUrl.replaceAll(":[^:@]+@", ":***@"));
            
            try {
                // Si ya tiene jdbc: al principio, quitárselo para parsearlo
                String cleanUrl = databaseUrl.replace("jdbc:", "");
                URI dbUri = new URI(cleanUrl);
                
                String username = dbUri.getUserInfo().split(":")[0];
                String password = dbUri.getUserInfo().split(":")[1];
                String host = dbUri.getHost();
                int port = dbUri.getPort() != -1 ? dbUri.getPort() : 5432; // Default PostgreSQL port
                String database = dbUri.getPath().substring(1); // Remove leading slash
                
                String jdbcUrl = String.format("jdbc:postgresql://%s:%d/%s", host, port, database);
                
                log.info("Configurando conexión JDBC: {}", jdbcUrl);
                log.info("Usuario: {}", username);
                
                return DataSourceBuilder
                        .create()
                        .url(jdbcUrl)
                        .username(username)
                        .password(password)
                        .driverClassName("org.postgresql.Driver")
                        .build();
                        
            } catch (URISyntaxException e) {
                log.error("Error parsing DATABASE_URL: {}", e.getMessage());
                throw new RuntimeException("Error parsing DATABASE_URL", e);
            }
        } else {
            log.info("DATABASE_URL no encontrado, usando configuración por defecto");
            // Fallback para desarrollo local
            return DataSourceBuilder
                    .create()
                    .url("jdbc:postgresql://localhost:5432/wedding_db")
                    .username("wedding_user")
                    .password("wedding_pass")
                    .driverClassName("org.postgresql.Driver")
                    .build();
        }
    }
}