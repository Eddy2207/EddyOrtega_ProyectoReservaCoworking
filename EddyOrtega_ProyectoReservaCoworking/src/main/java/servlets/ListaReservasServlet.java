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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet que maneja la consulta de reservas almacenadas en la base de datos.
 */
@WebServlet(name = "ListaReservasServlet", urlPatterns = {"/ListaReservasServlet"})
public class ListaReservasServlet extends HttpServlet {

    // Datos de conexión a la base de datos MySQL
    private static final String URL = "jdbc:mysql://localhost:3306/coworking";  // Nombre de tu BD
    private static final String USER = "root";  // Usuario de MySQL
    private static final String PASSWORD = "";  // Contraseña de MySQL 
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        List<String[]> reservas = new ArrayList<>(); // Lista para almacenar las reservas
        
         // Conectar a la base de datos y obtener las reservas
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("SELECT nombre, fecha, espacio, duracion FROM reservas");
             ResultSet rs = stmt.executeQuery()){
            
            // Recorrer los resultados de la consulta y almacenarlos en la lista
            while (rs.next()){
                String[] reserva = {
                rs.getString("nombre"),
                rs.getString("fecha"),
                rs.getString("espacio"),
                String.valueOf(rs.getInt("duracion"))
                };
                reservas.add(reserva);// Agregar la reserva a la lista
            }
        } catch (SQLException ex) {
            ex.printStackTrace();// Imprimir error en la consola del servidor
            request.setAttribute("error","Error al obtener las reservas" + ex.getMessage());
        }
        // Enviar la lista de reservas como atributo a la página JSP
        request.setAttribute("reservas", reservas);
        
        // Redirigir la solicitud a la página JSP que mostrará la lista
        request.getRequestDispatcher("reservas.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
