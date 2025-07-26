# Instrucciones para Claude Code - Wedding Backend

## Entorno de desarrollo
- **Sistema**: Windows con WSL2 Ubuntu 22.04
- **Comando preferido**: Usar siempre WSL2 para ejecutar comandos de desarrollo
- **Ruta del proyecto**: `/mnt/d/GIT/wedding-backend/`
- **Puerto del servidor**: 8585

## Comandos de ejecución

### Ejecutar backend
```bash
wsl.exe --exec bash -c "cd /mnt/d/GIT/wedding-backend && ./mvnw spring-boot:run"
```

### Otros comandos útiles
```bash
# Compilar sin ejecutar
wsl.exe --exec bash -c "cd /mnt/d/GIT/wedding-backend && ./mvnw clean compile"

# Ejecutar tests
wsl.exe --exec bash -c "cd /mnt/d/GIT/wedding-backend && ./mvnw test"

# Verificar estado del proyecto
wsl.exe --exec bash -c "cd /mnt/d/GIT/wedding-backend && ls -la"
```

## Configuración de base de datos
- **Tipo**: PostgreSQL
- **Puerto**: 5432
- **Base de datos**: wedding_db
- **Usuario**: wedding_user
- **Puerto del servidor backend**: 8585

## Configuración de email (asíncrono)
- **Servicio**: Gmail SMTP
- **Procesamiento**: Asíncrono con thread pool
- **Configuración**: AsyncConfig.java para no bloquear respuestas API

## Estructura del proyecto
- **Backend**: Spring Boot 3.4.3 con Java 17
- **ORM**: JPA/Hibernate
- **Base de datos**: PostgreSQL (migrado desde H2)
- **Funcionalidades**: Gestión de invitados y grupos, envío de emails asíncronos

## Notas importantes
- **SIEMPRE** trabajar directamente en `/mnt/d/GIT/wedding-backend/` desde WSL2
- **NO** crear copias en `~/projects/` o directorios similares
- **Usar WSL2** para evitar problemas de comandos en Windows
- El proyecto frontend hermano está en `/mnt/d/GIT/wedding-frontend/`
- La configuración CORS permite conexiones desde cualquier origen para desarrollo

## URLs importantes
- **API Base**: http://localhost:8585/api
- **Endpoints principales**:
  - GET/POST `/api/guests` - Gestión de invitados
  - GET `/api/guests/group/{groupId}` - Invitados por grupo
  - PUT `/api/guests/group/{groupId}` - Actualización masiva de grupo
  - POST `/api/guests/search` - Búsqueda de invitados

## Troubleshooting
- Si hay problemas de puerto, verificar que PostgreSQL esté ejecutándose
- Si fallan comandos Windows, usar equivalentes WSL2
- Los emails se envían de forma asíncrona, no bloquean las respuestas API