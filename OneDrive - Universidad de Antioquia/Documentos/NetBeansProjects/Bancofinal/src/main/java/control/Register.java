/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;


import configuracion.Conexion;
import dao.ClienteDAO;
import dao.CuentaDAO;

import modelo.Cliente;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import modelo.Cuenta;


@WebServlet("/register")
public class Register extends HttpServlet {

    private ClienteDAO clienteDAO;

    public Register(){
        this.clienteDAO = new ClienteDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
       throws ServletException, IOException {

        String nombre1 = request.getParameter("nombre1");
        String nombre2 = request.getParameter("nombre2");
        String apellido1 = request.getParameter("apellido1");
        String apellido2 = request.getParameter("apellido2");
        String identificacion = request.getParameter("identificacion");
        String tipoCuenta = request.getParameter("tipoCuenta");

        Cliente cliente = new Cliente(0,identificacion, nombre1, nombre2, apellido1, apellido2 );
        ClienteDAO clienteDAO = new ClienteDAO();
        int clienteId = clienteDAO.registrarCliente(cliente);

        if (clienteId > 0) {
            String numeroCuenta = generateAccountNumber();
            Cuenta cuenta = new Cuenta(0, numeroCuenta, tipoCuenta, clienteId, 10.000);
            CuentaDAO cuentaDAO = new CuentaDAO();
            cuentaDAO.registrarCuenta(cuenta);
            response.sendRedirect("succes.jsp");
        } else {
            response.sendRedirect("error.jsp");
        }
    }

    private String generateAccountNumber() {
        SecureRandom random = new SecureRandom();
        String numeroCuenta;
        do {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 10; i++) {
                int digit = random.nextInt(10); // Genera un número entre 0 y 9
                sb.append(digit);
            }
            numeroCuenta = sb.toString();
        } while (existeNumeroCuenta(numeroCuenta));
        return numeroCuenta;
    }

    private boolean existeNumeroCuenta(String numeroCuenta) {
        Connection connection = Conexion.getConnection();
        String sql = "SELECT COUNT(*) FROM cuenta WHERE numero_cuenta = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, numeroCuenta);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}