/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import com.google.gson.Gson;
import dao.ClienteDAO;
import dao.CuentaDAO;
import dao.TransaccionDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Cliente;
import modelo.Cuenta;
import modelo.Transaccion;


/**
 *
 * @author Juaan
 */
@WebServlet("/transferencia")
public class Transferencia extends HttpServlet {
    private static final Logger logger = Logger.getLogger(Transferencia.class.getName());
    
    private TransaccionDAO transaccionDAO;
    private ClienteDAO clienteDAO;

    public Transferencia() {
        this.transaccionDAO = new TransaccionDAO();
        this.clienteDAO = new ClienteDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String numeroCuentaOrigen = request.getParameter("cuentaOrigen");
            String numeroCuentaDestino = request.getParameter("cuentaDestino");
            String tipoMoneda = request.getParameter("tipoMoneda");
            double monto = Double.parseDouble(request.getParameter("monto"));
            LocalDateTime fechaRealizacion = LocalDateTime.now();

            // Obtener cuentas por número de cuenta
            Cuenta cuentaOrigen = clienteDAO.obtenerCuentaPorNumero(numeroCuentaOrigen);
            Cuenta cuentaDestino = clienteDAO.obtenerCuentaPorNumero(numeroCuentaDestino);

            // Validar que ambas cuentas existen
            if (cuentaOrigen == null || cuentaDestino == null) {
                request.setAttribute("mensaje", "Una o ambas cuentas no existen.");
                request.getRequestDispatcher("error_1.jsp").forward(request, response);
                return;
            }

            double montoConvertido = convertirMoneda(tipoMoneda, monto);

            // Verificar saldo de la cuenta de origen
            if (cuentaOrigen.getSaldo() < montoConvertido) {
                request.setAttribute("mensaje", "Fondos insuficientes en la cuenta de origen.");
                request.getRequestDispatcher("error_1.jsp").forward(request, response);
                return;
            }

            Transaccion transaccion = new Transaccion(0, 0, cuentaOrigen.getConsecutivo(), cuentaDestino.getConsecutivo(), tipoMoneda, montoConvertido, fechaRealizacion);
            
            

            transaccionDAO.realizarTransferencia(transaccion);
            response.sendRedirect("succes_1.jsp");

        } catch (NumberFormatException | SQLException e) {
            logger.log(Level.SEVERE, "Error al procesar la solicitud de transferencia", e);
            request.setAttribute("mensaje", "Error al procesar la solicitud de transferencia");
            request.getRequestDispatcher("error_1.jsp").forward(request, response);
        }
    }

    private double convertirMoneda(String tipoMoneda, double monto) {
        double tasaConversion;
        switch (tipoMoneda) {
            case "USD":
                tasaConversion = 3700.0; // Ejemplo de tasa de conversión USD a COP
                break;
            case "EUR":
                tasaConversion = 4200.0; // Ejemplo de tasa de conversión EUR a COP
                break;
            case "COP":
            default:
                tasaConversion = 1.0;
        }
        return monto * tasaConversion;
    }
}