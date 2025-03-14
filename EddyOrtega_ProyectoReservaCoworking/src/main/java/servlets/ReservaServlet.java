package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ReservaServlet", urlPatterns = {"/ReservaServlet"})
public class ReservaServlet extends HttpServlet {

    // Datos de conexión a MySQL
    private static final String URL = "jdbc:mysql://localhost:3306/coworking";  // Nombre de tu BD
    private static final String USER = "root";  // Usuario de MySQL
    private static final String PASSWORD = "";  // Contraseña de MySQL (deja vacío si no tiene)

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener parámetros del formulario
        String nombre = request.getParameter("nombre");
        String fecha = request.getParameter("fecha");
        String espacio = request.getParameter("espacio");
        String duracionStr = request.getParameter("duracion");

        // Imprimir los valores en la consola para depuración
        System.out.println("🔹 Nombre recibido: " + nombre);
        System.out.println("🔹 Fecha recibida: " + fecha);
        System.out.println("🔹 Espacio recibido: " + espacio);
        System.out.println("🔹 Duración recibida: " + duracionStr);

        // Validar que todos los campos están completos
        if (nombre == null || nombre.isEmpty() ||
            fecha == null || fecha.isEmpty() ||
            espacio == null || espacio.isEmpty() ||
            duracionStr == null || duracionStr.isEmpty()) {

            System.out.println("⚠ Error: Faltan campos obligatorios.");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Todos los campos son obligatorios");
            return;
        }
        
        // Obtener la fecha de reserva
        String fechaStr = request.getParameter("fecha");

        try {
            LocalDate fechaReserva = LocalDate.parse(fechaStr);
            LocalDate fechaHoy = LocalDate.now();

            if (fechaReserva.isBefore(fechaHoy)) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "⚠ No puedes reservar para una fecha pasada.");
                return;
            }
        } catch (DateTimeParseException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "⚠ Fecha no válida.");
            return;
        }

        // Convertir duración a número entero
        int duracion;
        try {
            duracion = Integer.parseInt(duracionStr);
            if (duracion <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            System.out.println("⚠ Error: La duración debe ser un número mayor a 0.");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "La duración debe ser un número mayor a 0");
            return;
        }
        
                try {
            Class.forName("com.mysql.cj.jdbc.Driver");  // 🔹 Cargar el driver manualmente
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("⚠ Error: No se encontró el driver de MySQL.");
        }

        // Guardar la reserva en la base de datos
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO reservas (nombre, fecha, espacio, duracion) VALUES (?, ?, ?, ?)")) {

            stmt.setString(1, nombre);
            stmt.setString(2, fecha);
            stmt.setString(3, espacio);
            stmt.setInt(4, duracion);
            stmt.executeUpdate();

            System.out.println("✅ Reserva guardada correctamente en la base de datos.");

            // Redirigir a la página de confirmación
            response.sendRedirect("confirmacion.jsp");

        } catch (SQLException ex) {
            ex.printStackTrace();  // 🔹 Muestra el error en la consola de NetBeans
            System.out.println("⚠ Error en la base de datos: " + ex.getMessage());

            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<h2>⚠ Error en la base de datos</h2>");
            out.println("<p>Mensaje: " + ex.getMessage() + "</p>");
            out.println("<p>Causa: " + (ex.getCause() != null ? ex.getCause() : "No disponible") + "</p>");
            out.println("<pre>");
            ex.printStackTrace(out);
            out.println("</pre>");
        }
    }
}
