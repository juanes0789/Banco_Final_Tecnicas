<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="modelo.Transaccion"%>
<%@ page import="java.util.List" %>
<%@ page import="modelo.Cliente" %>
<%@ page import="modelo.Cuenta" %>
<%@ page import="dao.ClienteDAO" %>
<%@ page import="dao.CuentaDAO" %>
<%@ page import="dao.TransaccionDAO" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.io.PrintWriter" %>

<%
    // Obtener lista de clientes y cuentas para la selección
    ClienteDAO clienteDAO = new ClienteDAO();
    List<Cliente> clientes = clienteDAO.obtenerClientesConCuentas();
    CuentaDAO cuentaDAO = new CuentaDAO();
    List<Cuenta> cuentas = cuentaDAO.obtenerTodasLasCuentas();
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Realizar Transacción</title>
    <!-- Agregar Tailwind CSS -->
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
</head>
<body class="bg-gray-100 p-4">
      <!-- Encabezado con enlace a index.jsp -->
    <header class="mb-8">
        <a href="index.jsp" class="text-blue-500 hover:underline">Volver a Inicio</a>
    </header>
  <div class="flex">
   <div class="max-w-md mx-auto my-10 bg-white p-6 rounded-md shadow-md">
    <h1 class="text-3xl font-bold mb-4">Transferir dinero</h1>
    <form action="transferencia" method="post" class="mb-8">
        <label for="cuentaOrigen" class="block mb-2">Cuenta Origen:</label>
        <select name="cuentaOrigen" id="cuentaOrigen" class="w-full mb-4" >
            <% for (Cuenta cuenta : cuentas) { %>
                <option value="<%= cuenta.getNumeroCuenta() %>"><%= cuenta.getNumeroCuenta() %></option>
            <% } %>
        </select>
        <label for="cuentaDestino" class="block mb-2">Cuenta Destino:</label>
        <select name="cuentaDestino" id="cuentaDestino" class="w-full mb-4">
            <% for (Cuenta cuenta : cuentas) { %>
                <option value="<%= cuenta.getNumeroCuenta() %>"><%= cuenta.getNumeroCuenta() %></option>
            <% } %>
        </select>
        <label for="tipoMoneda" class="block mb-2">Tipo de Moneda:</label>
        <select name="tipoMoneda" id="tipoMoneda" class="w-full mb-4">
            <option value="USD">USD</option>
            <option value="EUR">EUR</option>
            <option value="COP">COP</option>
        </select>
        <label for="monto" class="block mb-2">Monto:</label>
        <input type="number" step="0.01" name="monto" id="monto" class="w-full mb-4">
        <button type="submit" class="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-700">Transferir</button>
    </form>
   </div>
   <div class="max-w-md mx-auto my-10 bg-white p-6 rounded-md shadow-md">     
    <h1 class="text-3xl font-bold mb-4">Retirar dinero</h1>
    <form action="empanada" method="post" class="mb-8">
        <label for="cuentaOrigen" class="block mb-2">Cuenta Origen:</label>
        <select name="cuentaOrigen" id="cuentaOrigen" class="w-full mb-4">
            <% for (Cuenta cuenta : cuentas) { %>
                <option value="<%= cuenta.getNumeroCuenta() %>"><%= cuenta.getNumeroCuenta() %></option>
            <% } %>
        </select>
        <label for="tipoMoneda" class="block mb-2">Tipo de Moneda:</label>
        <select name="tipoMoneda" id="tipoMoneda" class="w-full mb-4">
            <option value="USD">USD</option>
            <option value="EUR">EUR</option>
            <option value="COP">COP</option>
        </select>
        <label for="monto" class="block mb-2">Monto:</label>
        <input type="number" step="0.01" name="monto" id="monto" class="w-full mb-4">
        <button type="submit" class="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-700">Retirar</button>
    </form>
   </div>
  </div>
        <%
    TransaccionDAO transaccionDAO = new TransaccionDAO();
    List<Transaccion> transacciones = null;
    try {
        transacciones = transaccionDAO.obtenerTodasTransacciones();
    } catch (SQLException e) {
        out.println("Error al obtener las transacciones: " + e.getMessage());
    }
%>

    <h1 class="text-3xl font-bold mb-4">Historial de Transacciones</h1>
    <table class="w-full border-collapse border border-gray-200">
        <thead>
            <tr>
                <th class="px-4 py-2 bg-gray-200 border border-gray-200">Consecutivo</th>
          
                <th class="px-4 py-2 bg-gray-200 border border-gray-200">Cuenta Origen</th>
                <th class="px-4 py-2 bg-gray-200 border border-gray-200">Cuenta Destino</th>
                <th class="px-4 py-2 bg-gray-200 border border-gray-200">Tipo Moneda</th>
                <th class="px-4 py-2 bg-gray-200 border border-gray-200">Monto</th>
                <th class="px-4 py-2 bg-gray-200 border border-gray-200">Fecha</th>
            </tr>
        </thead>
        <tbody>
            <% for (Transaccion transaccion : transacciones) { %>
                <tr>
                    <td class="px-4 py-2 border border-gray-200"><%= transaccion.getConsecutivo() %></td>
     
                    <td class="px-4 py-2 border border-gray-200"><%= transaccion.getIdCuentaOrigen() %></td>
                    <td class="px-4 py-2 border border-gray-200"><%= transaccion.getIdCuentaDestino() %></td>
                    <td class="px-4 py-2 border border-gray-200"><%= transaccion.getTipoMoneda() %></td>
                    <td class="px-4 py-2 border border-gray-200"><%= transaccion.getMonto() %></td>
                    <td class="px-4 py-2 border border-gray-200"><%=  transaccion.getFecha().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) %></td>
             
            </tr>
        <% } %>
    </table>
        </div>
    </div>
</div>
</body>
</html>
