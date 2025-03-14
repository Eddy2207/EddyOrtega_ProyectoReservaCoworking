# EddyOrtega_ProyectoReservaCoworking
Parcial Corte 1 Back End

Sistema de reservas para un espacio de coworking, desarrollado con Java EE, Servlets y JSP.

## 📌 **Requisitos Previos**
Antes de ejecutar el proyecto, asegúrate de tener instalado:  
- **Java 17** o superior  
- **Apache Tomcat 9** o superior  
- **MySQL 8.0.17** o superior  
- **Apache Maven 3.6** o superior  

**Instalación y Configuración**
**1️⃣ Clonar el Repositorio**
Si no tienes el proyecto aún, clónalo con:  

git clone https://github.com/Eddy2207/EddyOrtega_ProyectoReservaCoworking.git
cd EddyOrtega_ProyectoReservaCoworking


**2️⃣ Configurar la Base de Datos**
1. Inicia MySQL y crea la base de datos:
   
   CREATE DATABASE coworking;
   
3. Crea la tabla de reservas (si no existe):  

   CREATE TABLE reservas (
       id INT AUTO_INCREMENT PRIMARY KEY,
       nombre VARCHAR(100) NOT NULL,
       fecha DATE NOT NULL,
       espacio VARCHAR(50) NOT NULL,
       duracion INT NOT NULL
   );
   
4. Verifica que el usuario de MySQL (`root`) tiene acceso sin contraseña o actualiza los datos en `ReservaServlet.java`:  
   
   private static final String URL = "jdbc:mysql://localhost:3306/coworking";
   private static final String USER = "root";
   private static final String PASSWORD = "";


**3️⃣ Compilar el Proyecto**
Desde la raíz del proyecto, ejecuta:  

mvn clean package

Esto generará un archivo `.war` en la carpeta `target/`.

**4️⃣ Desplegar en Tomcat**
1. Copia el archivo `.war` generado en `target/` a la carpeta de despliegue de Tomcat (`webapps/`).
2. Inicia Tomcat y accede a la aplicación en:  
   **🔗 URL:** [http://localhost:8084/EddyOrtega_ProyectoReservaCoworking/index.html](http://localhost:8084/EddyOrtega_ProyectoReservaCoworking/index.html)

---

## 🚀 **Uso del Sistema**
1. **Reservar un espacio:**  
   - Rellena el formulario en `index.html` con los datos de la reserva.  
   - Presiona **"Reservar"** para enviarlo.  

2. **Ver la lista de reservas:**  
   - Haz clic en **"Lista de Reservas"** para ver todas las reservas almacenadas en MySQL.  

3. **Eliminar una reserva:**  
   - En la lista de reservas, cada reserva tiene un botón **"Eliminar"** que la borra de la base de datos.  

---

## 📜 **Tecnologías Utilizadas**
- **Java EE 7** (Servlets, JSP)  
- **MySQL 8.0.17** (Base de datos)  
- **JDBC** (Conexión a MySQL)  
- **Apache Tomcat 9** (Servidor de aplicaciones)  
- **Maven** (Gestión de dependencias)  
- **HTML, CSS, JavaScript** (Interfaz web)  

---

## 👤 **Autor**
Proyecto desarrollado por **Eddy Ortega**.  
🔗 **Repositorio GitHub:** [Eddy2207/EddyOrtega_ProyectoReservaCoworking](https://github.com/Eddy2207/EddyOrtega_ProyectoReservaCoworking)
