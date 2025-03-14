<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Lista de Reservas</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <h1>📋 Lista de Reservas</h1>

        <% String error = (String) request.getAttribute("error"); %>
        <% if (error != null) { %>
            <p style="color: red;"><%= error %></p>
        <% } %>

        <table border="1">
            <thead>
                <tr>
                    <th>Nombre</th>
                    <th>Fecha</th>
                    <th>Espacio</th>
                    <th>Duración (horas)</th>
                    <th>Accion</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<String[]> reservas = (List<String[]>) request.getAttribute("reservas");
                    if (reservas != null && !reservas.isEmpty()) {
                        for (String[] reserva : reservas) {
                %>
                <tr>
                    <td><%= reserva[0] %></td>
                    <td><%= reserva[1] %></td>
                    <td><%= reserva[2] %></td>
                    <td><%= reserva[3] %></td>
                    <td><form action="EliminarReservaServlet" method="POST" style="display:inline;">
                            <input type="hidden" name="nombre" value="<%= reserva[0] %>">
                            <input type="hidden" name="fecha" value="<%= reserva[1] %>">
                            <input type="hidden" name="espacio" value="<%= reserva[2] %>">
                            <input type="hidden" name="duracion" value="<%= reserva[3] %>">
                            <button type="submit" onclick="return confirm('¿Seguro que deseas eliminar esta reserva?');">
                                🗑 Eliminar
                            </button>
                        </form>
                    </td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="5">⚠ No hay reservas registradas.</td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>

        <p><a href="index.html">⬅ Volver al inicio</a></p>
    </body>
</html>
