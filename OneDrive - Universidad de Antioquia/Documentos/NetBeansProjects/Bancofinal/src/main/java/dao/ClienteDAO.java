/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import configuracion.Conexion;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Cliente;
import modelo.Cuenta;


public class ClienteDAO {
  public int registrarCliente(Cliente cliente) {
    Connection connection = Conexion.getConnection();
    String sql = "INSERT INTO cliente (identificacion, nombre1, nombre2, apellido1, apellido2) VALUES (?, ?, ?, ?, ?);";
    try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
        ps.setString(1, cliente.getIdentificacion());
        ps.setString(2, cliente.getNombre1());
        ps.setString(3, cliente.getNombre2());
        ps.setString(4, cliente.getApellido1());
        ps.setString(5, cliente.getApellido2());
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1); // Devuelve el ID generado
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return -1; // Devuelve -1 si hubo un error
}
    // Método para registrar cuenta bancaria
    public boolean registrarCuenta(Cuenta cuenta) {
        Connection connection = Conexion.getConnection();
        String sql = "INSERT INTO cuenta (numero_cuenta, cliente_id, saldo) VALUES(?, ?, ?);";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, cuenta.getNumeroCuenta());
            ps.setInt(2, cuenta.getCliente());
            ps.setDouble(3, cuenta.getSaldo());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para generar número de cuenta único
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

    // Método para verificar si el número de cuenta ya existe
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
    public List<Cliente> obtenerClientesConCuentas() {
    List<Cliente> clientes = new ArrayList<>();
    try {
        Connection connection = Conexion.getConnection();
        String sql = "SELECT c.*, cu.numero_cuenta, cu.tipo_cuenta, cu.saldo FROM cliente c INNER JOIN cuenta cu ON c.consecutivo = cu.cliente_id";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Cliente cliente = new Cliente();
            cliente.setConsecutivo(resultSet.getInt("consecutivo"));
            cliente.setNombre1(resultSet.getString("nombre1"));
            cliente.setNombre2(resultSet.getString("nombre2"));
            cliente.setApellido1(resultSet.getString("apellido1"));
            cliente.setApellido2(resultSet.getString("apellido2"));
            cliente.setIdentificacion(resultSet.getString("identificacion"));

            // Crear una instancia de Cuenta y establecer sus atributos
            Cuenta cuenta = new Cuenta();
            cuenta.setNumeroCuenta(resultSet.getString("numero_cuenta"));
            cuenta.setTipoCuenta(resultSet.getString("tipo_cuenta"));
            cuenta.setSaldo(resultSet.getDouble("saldo"));

            // Establecer la cuenta en el cliente
            cliente.setCuenta(cuenta);

            // Agregar el cliente a la lista
            clientes.add(cliente);
        }
        resultSet.close();
        statement.close();
        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return clientes;
}
       public Cuenta obtenerCuentaPorNumero(String numeroCuenta) throws SQLException {
        String sql = "SELECT consecutivo, numero_cuenta, tipo_cuenta, cliente_id, saldo FROM cuenta WHERE numero_cuenta = ?";
        try (Connection connection = Conexion.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, numeroCuenta);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int consecutivo = rs.getInt("consecutivo");
                    String tipoCuenta = rs.getString("tipo_cuenta");
                    int clienteId = rs.getInt("cliente_id");
                    double saldo = rs.getDouble("saldo");
                    return new Cuenta(consecutivo, numeroCuenta, tipoCuenta, clienteId, saldo);
                } else {
                    throw new SQLException("Cuenta no encontrada");
                }
            }
        }
    }
}