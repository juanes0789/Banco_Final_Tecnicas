<%-- 
    Document   : registro
    Created on : 1/06/2024, 5:33:00 p. m.
    Author     : Juaan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>Registro</title>
<link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
<style>
    /* Agregar estilos personalizados si es necesario */
    .animated-button:hover {
        transform: scale(1.1); /* Escala del botón al pasar el cursor sobre él */
    }

    /* Efecto de hover para cambiar el color de fondo del botón */
    .animated-button:hover {
        background-color: #1e40af; /* Color oscuro al pasar el cursor sobre el botón */
    }
</style>
</head>
<body class="bg-gray-100">
      <!-- Encabezado con enlace al inicio -->
    <header class="text-center py-4">
        <a href="index.jsp" class="text-blue-500 hover:underline">Volver al Inicio</a>
    </header>
    <div class="max-w-md mx-auto my-10 bg-white p-6 rounded-md shadow-md">
        <h2 class="text-2xl font-bold mb-4">Registro de Cliente</h2>
        <form action="register" method="post" class="space-y-4">
            <div>
                <label for="nombre1" class="block">Primer Nombre:</label>
                <input type="text" id="nombre1" name="nombre1" required class="w-full px-4 py-2 border rounded-md focus:outline-none focus:ring focus:ring-primary-200">
            </div>
            
            <div>
                <label for="nombre2" class="block">Segundo Nombre:</label>
                <input type="text" id="nombre2" name="nombre2" class="w-full px-4 py-2 border rounded-md focus:outline-none focus:ring focus:ring-primary-200">
            </div>
            
            <div>
                <label for="apellido1" class="block">Primer Apellido:</label>
                <input type="text" id="apellido1" name="apellido1" required class="w-full px-4 py-2 border rounded-md focus:outline-none focus:ring focus:ring-primary-200">
            </div>
            
            <div>
                <label for="apellido2" class="block">Segundo Apellido:</label>
                <input type="text" id="apellido2" name="apellido2" class="w-full px-4 py-2 border rounded-md focus:outline-none focus:ring focus:ring-primary-200">
            </div>
            
            <div>
                <label for="identificacion" class="block">Identificación:</label>
                <input type="text" id="identificacion" name="identificacion" required class="w-full px-4 py-2 border rounded-md focus:outline-none focus:ring focus:ring-primary-200">
            </div>
            
            <div>
                <label for="tipoCuenta" class="block">Tipo de Cuenta:</label>
                <select id="tipoCuenta" name="tipoCuenta" required class="w-full px-4 py-2 border rounded-md focus:outline-none focus:ring focus:ring-primary-200">
                    <option value="Ahorros">Ahorros</option>
                    <option value="Corriente">Corriente</option>
                </select>
            </div>
            
         <div>
                <input type="submit" value="Registrar" class="w-full px-4 py-2 bg-primary text-white rounded-md hover:bg-primary-dark focus:outline-none focus:ring focus:ring-primary focus:ring-offset-1 animated-button transition-transform">
            </div>
        </form>
    </div>
</body>
</html>
