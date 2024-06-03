<%-- 
    Document   : error
    Created on : 1/06/2024, 12:47:49 a. m.
    Author     : Juaan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error en el Registro</title>
    <link rel="stylesheet" href="build/css/tailwind.css">
</head>
<body class="bg-red-100 text-center">
    <div class="min-h-screen flex flex-col justify-center items-center">
        <div class="bg-white p-8 rounded-md shadow-md w-96">
            <h1 class="text-2xl font-semibold text-red-700">¡Error en la transferencia!</h1>
            <p class="mt-4 text-gray-600">${mensaje}$</p>
            <a href="Transaccion.jsp" class="mt-6 inline-block bg-red-500 text-white px-4 py-2 rounded-md hover:bg-red-600">
                Volver al inicio
            </a>
        </div>
    </div>
</body>
</html>