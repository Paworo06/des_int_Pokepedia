## CRUD Pokepedia (Java Swing, MVC) — Estilo Pokédex

Aplicación de escritorio en Java Swing siguiendo MVC para gestionar usuarios y pokémon conectados a MySQL. Incluye login con roles, tabla con búsqueda en vivo y formularios de alta/edición/borrado, con aspecto rojo/blanco tipo Pokédex.

### Requisitos previos
- JDK 8+.
- MySQL en ejecución con la base `pokepedia` creada con el script proporcionado.
- Driver MySQL JDBC (por ejemplo `mysql-connector-j-8.x.x.jar`). Colócalo en `lib/mysql-connector-j.jar` o ajusta la ruta en los comandos.

### Estructura (nombres en español)
- `src/com/pokepedia/config/DatabaseConfig.java` — conexión JDBC.
- `src/com/pokepedia/modelo/Usuario.java`, `modelo/Pokemon.java` — modelos.
- `src/com/pokepedia/dao/UsuarioDao.java`, `dao/PokemonDao.java` — acceso a datos.
- `src/com/pokepedia/controlador/ControladorAutenticacion.java`, `controlador/ControladorPokemon.java` — coordinan vistas/DAOs.
- Vistas Swing en `src/com/pokepedia/vista/`:
  - `VistaLogin.java` (login)
  - `VistaPrincipal.java` (tabla + acciones)
  - `VistaFormularioPokemon.java` (alta/edición)
  - `VistaDetallePokemon.java` (detalle)
- `src/com/pokepedia/Main.java` — punto de entrada.

### Configurar la conexión
Edita `src/com/pokepedia/config/DatabaseConfig.java` con tu host, puerto, usuario y contraseña de MySQL. Ejemplo:
```java
private static final String URL = "jdbc:mysql://localhost:3306/pokepedia?useSSL=false&serverTimezone=UTC";
private static final String USER = "root";
private static final String PASSWORD = "";
```

### Compilar (Windows PowerShell)
```powershell
Set-Location "c:\laragon\www\Desarrollo de Interfaces\Crud"
mkdir out -Force | Out-Null
javac -cp "lib/mysql-connector-j.jar" -d out (Get-ChildItem -Recurse src/com/pokepedia/*.java)
```

### Ejecutar
```powershell
java -cp "out;lib/mysql-connector-j.jar" com.pokepedia.Main
```

### Credenciales de ejemplo (según script de BD)
- Admin: `admin@pokepedia.com` / `admin123` (rol `ADMIN`, puede crear/editar/eliminar)
- Usuario: `ash@pokemon.com` / `pikachu` (rol `USER`, solo consulta/detalle)

### Funcionalidades clave
- Login con roles `ADMIN`/`USER`.
- Vista principal tipo Pokédex (rojo/blanco) con:
  - Tabla de pokémon y búsqueda inmediata por nombre.
  - Detalle en diálogo aparte.
  - Alta/edición/borrado restringidos a `ADMIN`.
- Formularios modales para insertar/actualizar y diálogo de detalle.

### Notas y buenas prácticas
- Las contraseñas del script están en texto plano; para producción usa hashing (BCrypt/Argon2) y validación segura.
- Asegúrate de que el driver MySQL esté accesible en `lib/` o ajusta el classpath.

### Pruebas rápidas manuales
1) Inicia la app y entra como admin (`admin@pokepedia.com` / `admin123`).  
2) Crea un nuevo pokémon, edítalo y elimínalo; verifica que la tabla se refresca.  
3) Busca por nombre parcial en el cuadro de búsqueda y confirma que filtra.  
4) Entra como usuario (`ash@pokemon.com` / `pikachu`) y confirma que los botones de alta/edición/borrado están deshabilitados pero el detalle funciona.  

