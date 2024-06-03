/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import configuracion.Conexion;
import static configuracion.Conexion.getConnection;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import modelo.Transaccion;



public class TransaccionDAO {
    private static final String INGRESAR_TRANSFERENCIA = "INSERT INTO transaccion (cuenta_origen, cuenta_destino, tipo_moneda, monto, fecha) VALUES(?, ?, ?, ?, ?)";
    private static final String INGRESAR_RETIRO = "INSERT INTO transaccion (cuenta_origen, cuenta_destino, tipo_moneda, monto, fecha) VALUES(?, ?, ?, ?, ?)";

    public double obtenerSaldoPorNumeroCuenta(long numeroCuenta) throws SQLException {
        String sql = "SELECT saldo FROM cuenta WHERE numero_cuenta = ?";
        try (Connection connection = Conexion.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, numeroCuenta);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("saldo");
                } else {
                    throw new SQLException("Cuenta no encontrada");
                }
            }
        }
    }

    public double obtenerSaldo(int idCuenta, Connection connection) throws SQLException {
        String sql = "SELECT saldo FROM cuenta WHERE consecutivo = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, idCuenta);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("saldo");
                } else {
                    throw new SQLException("Cuenta no encontrada");
                }
            }
        }
    }

    public void actualizarSaldoCuenta(double cantidad, int id) throws SQLException {
        String actualizarSaldoQuery = "UPDATE cuenta SET saldo = saldo + ? WHERE consecutivo = ?";
        try (Connection conexion = Conexion.getConnection();
             PreparedStatement preparedStatement = conexion.prepareStatement(actualizarSaldoQuery)) {
            preparedStatement.setDouble(1, cantidad);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error al actualizar el saldo de la cuenta: " + e.getMessage(), e);
        }
    }
    private static final String SELECT_ALL_TRANSACCIONES = "SELECT * FROM transaccion";

    // Método para obtener todas las transacciones
    public List<Transaccion> obtenerTodasTransacciones() throws SQLException {
        List<Transaccion> transacciones = new ArrayList<>();

        try (Connection conexion = getConnection();
             PreparedStatement preparedStatement = conexion.prepareStatement(SELECT_ALL_TRANSACCIONES);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int consecutivo = resultSet.getInt("consecutivo");
                int clienteId = resultSet.getInt("cliente_id");
                int cuentaOrigen = resultSet.getInt("cuenta_origen");
                int cuentaDestino = resultSet.getInt("cuenta_destino");
                String tipoMoneda = resultSet.getString("tipo_moneda");
                double monto = resultSet.getDouble("monto");
                LocalDateTime fecha = resultSet.getTimestamp("fecha").toLocalDateTime();
                

                Transaccion transaccion = new Transaccion(consecutivo, clienteId, cuentaOrigen, cuentaDestino, tipoMoneda, monto, fecha);
              

                transacciones.add(transaccion);
            }
        }

        return transacciones;
    }


    public void realizarTransferencia(Transaccion transaccion) throws SQLException {
        Connection connection = Conexion.getConnection();
        try {
            connection.setAutoCommit(false);

            // Calcular la comisión
            double monto = transaccion.getMonto();
            double comision = 0;
            if (monto < 50000) {
                comision = 100;
            } else if (monto > 50000) {
                comision = monto * 0.01;
            }
            double montoTotal = monto + comision;

            // Actualizar saldo de la cuenta de origen
            actualizarSaldoCuenta(-montoTotal, transaccion.getIdCuentaOrigen());

            // Actualizar saldo de la cuenta de destino
            actualizarSaldoCuenta(monto, transaccion.getIdCuentaDestino());

            // Registrar la transacción
            String sqlTransaccion = INGRESAR_TRANSFERENCIA;
            try (PreparedStatement pstmtTransaccion = connection.prepareStatement(sqlTransaccion)) {
                pstmtTransaccion.setLong(1, transaccion.getIdCuentaOrigen());
                pstmtTransaccion.setLong(2, transaccion.getIdCuentaDestino());
                pstmtTransaccion.setString(3, transaccion.getTipoMoneda());
                pstmtTransaccion.setDouble(4, monto);
                pstmtTransaccion.setTimestamp(5, Timestamp.valueOf(transaccion.getFecha()));
                pstmtTransaccion.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }
    
    public void realizarRetiro(Transaccion transaccion) throws SQLException {
        Connection connection = Conexion.getConnection();
        try {
            connection.setAutoCommit(false);

            // Calcular la comisión
            double monto = transaccion.getMonto();
            double comision = 0;
            if (monto < 50000) {
                comision = 100;
            } else if (monto > 50000) {
                comision = monto * 0.10;
            }
            double montoTotal = monto + comision;

            // Actualizar saldo de la cuenta de origen
            actualizarSaldoCuenta(-montoTotal, transaccion.getIdCuentaOrigen());

            
            // Registrar la transacción
            String sqlTransaccion = INGRESAR_RETIRO;
            try (PreparedStatement pstmtTransaccion = connection.prepareStatement(sqlTransaccion)) {
                pstmtTransaccion.setLong(1, transaccion.getIdCuentaOrigen());
                pstmtTransaccion.setLong(2, transaccion.getIdCuentaOrigen());
                pstmtTransaccion.setString(3, transaccion.getTipoMoneda());
                pstmtTransaccion.setDouble(4, monto);
                pstmtTransaccion.setTimestamp(5, Timestamp.valueOf(transaccion.getFecha()));
                pstmtTransaccion.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
