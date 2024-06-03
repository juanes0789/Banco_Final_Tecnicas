/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import configuracion.Conexion;
import static configuracion.Conexion.getConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Cuenta;

public class CuentaDAO {

   
      public boolean registrarCuenta(Cuenta cuenta) {
        Connection connection = Conexion.getConnection();
        String sql = "INSERT INTO cuenta (numero_cuenta, tipo_cuenta, cliente_id, saldo) VALUES(?, ?, ?, ?);";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, cuenta.getNumeroCuenta());
            ps.setString(2, cuenta.getTipoCuenta());
            ps.setInt(3, cuenta.getCliente());
            ps.setDouble(4, cuenta.getSaldo());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean existeNumeroCuenta(String numeroCuenta) {
        Connection connection = Conexion.getConnection();
        String sql = "SELECT 1 FROM cuenta WHERE numero_cuenta = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, numeroCuenta);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    
    private static final Logger logger = Logger.getLogger(CuentaDAO.class.getName());

 public Cuenta obtenerCuentaPorNumero(String numeroCuenta) throws SQLException {
        Connection connection = Conexion.getConnection();
        String query = "SELECT * FROM cuenta WHERE numero_cuenta = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, numeroCuenta);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int consecutivo = resultSet.getInt("consecutivo");
                    String numeroCuentaDb = resultSet.getString("numero_cuenta");
                    String tipoCuenta = resultSet.getString("tipo_cuenta");
                    int cliente = resultSet.getInt("cliente");
                    double saldo = resultSet.getDouble("saldo");
                    return new Cuenta(consecutivo, numeroCuentaDb, tipoCuenta, cliente, saldo);
                } else {
                    return null; // No se encontró la cuenta
                }
            }
        }
 }
 private static final String SELECCIONAR_CUENTA = "SELECT * FROM cuenta WHERE numero_cuenta = ?";
 public Cuenta seleccionarCuenta(String numeroCuenta) {
        Cuenta cuentaEncontrada = null;
        try (Connection conexion = getConnection();
             PreparedStatement preparedStatement = conexion.prepareStatement(SELECCIONAR_CUENTA)) {
            preparedStatement.setString(1, numeroCuenta);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                cuentaEncontrada = new Cuenta();
                cuentaEncontrada.setSaldo(resultSet.getDouble("saldo"));
                cuentaEncontrada.setTipoCuenta(resultSet.getString("tipoCuenta"));
                cuentaEncontrada.setNumeroCuenta(resultSet.getString("numeroCuenta"));
                cuentaEncontrada.setCliente(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            System.out.println("Error al seleccionar una cuenta: " + e.getMessage());
        }
        return cuentaEncontrada;
    }
 public void actualizarSaldoCuenta(double cantidad, int id) {
        String actualizarSaldoQuery = "UPDATE cuenta SET saldo = saldo + ? WHERE id= ?";

        try (Connection conexion = getConnection();
             PreparedStatement preparedStatement = conexion.prepareStatement(actualizarSaldoQuery)) {
            preparedStatement.setDouble(1, cantidad);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al actualizar el saldo de la cuenta: " + e.getMessage());
        }
    }
 
    private static final String ACTUALIZAR_SALDO_CUENTA = "UPDATE cuenta SET saldo = ? WHERE consecutivo = ?";

    public void actualizarSaldoCuenta2(double saldo, int consecutivoCuenta) throws SQLException {
        try (Connection connection = Conexion.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(ACTUALIZAR_SALDO_CUENTA)) {
            pstmt.setDouble(1, saldo);
            pstmt.setInt(2, consecutivoCuenta);
            pstmt.executeUpdate();
        }
    }


     public List<Cuenta> obtenerTodasLasCuentas() {
        List<Cuenta> cuentas = new ArrayList<>();
        String sql = "SELECT * FROM cuenta";
        try (Connection connection = Conexion.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Cuenta cuenta = new Cuenta();
                cuenta.setConsecutivo(resultSet.getInt("consecutivo"));
                cuenta.setNumeroCuenta(resultSet.getString("numero_cuenta"));
                cuenta.setTipoCuenta(resultSet.getString("tipo_cuenta"));
                cuenta.setSaldo(resultSet.getDouble("saldo"));
                cuentas.add(cuenta);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al obtener todas las cuentas", e);
        }
        return cuentas;
    }

    public void actualizarCuenta(Cuenta cuentaOrigen) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}