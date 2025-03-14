/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet encargado de eliminar una reserva específica.
 * Recibe los datos de la reserva desde el formulario y los elimina de la base de datos.
 */
@WebServlet(name = "EliminarReservaServlet", urlPatterns = {"/EliminarReservaServlet"})
public class EliminarReservaServlet extends HttpServlet {
    
     // Datos de conexión a la base de datos MySQL
    private static final String URL = "jdbc:mysql://localhost:3306/coworking";  // Nombre de tu BD
    private static final String USER = "root";  // Usuario de MySQL
    private static final String PASSWORD = ""; // Contraseña de MySQL

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Validar que los parámetros no sean nulos o vacíos
        String nombre = request.getParameter("nombre");
        String fecha = request.getParameter("fecha");
        String espacio = request.getParameter("espacio");
        String duracion = request.getParameter("duracion");
        
        if(nombre == null || fecha == null || espacio == null || duracion == null){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Datos de reserva no validos");
            return;
        }
        
        // Conectar a la base de datos y eliminar la reserva
        try(Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            
            // Sentencia SQL para eliminar la reserva específica
            String sql = "DELETE FROM reservas WHERE nombre = ? AND fecha = ? AND espacio = ? AND duracion = ?";
            
            try (PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setString(1, nombre);
                stmt.setString(2, fecha);
                stmt.setString(3, espacio);
                stmt.setString(4, duracion);
                
                // Ejecutar la eliminación y verificar si se eliminó correctamente
                int filasEliminadas = stmt.executeUpdate();
                
                if(filasEliminadas > 0){
                    System.out.println("Reserva eliminada correctamente");
                }else{
                    System.out.println("No se encontro la reserva a eliminar");
                }
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace(); // Imprime el error en la consola del servidor
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "⚠ Error al eliminar la reserva.");
            return;
        }
        // Redirigir de nuevo a la lista de reservas después de eliminar
        response.sendRedirect("ListaReservasServlet");
    }

    
    @Override
    public String getServletInfo() {
        return "Servlet para eliminar reservas de la base de datos.";
    }// </editor-fold>

}
