/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class Transaccion {
    private int consecutivo;
    private int clienteId;
    private int IdCuentaOrigen;
    private int IdCuentaDestino;
    private String tipoMoneda;
    private double monto;
    private LocalDateTime fecha;
   

    public Transaccion(int consecutivo, int clienteId, int IdCuentaOrigen, int IdCuentaDestino, String tipoMoneda, double monto, LocalDateTime fecha ) {
        this.consecutivo = consecutivo;
        this.clienteId = clienteId;
        this.IdCuentaOrigen = IdCuentaOrigen;
        this.IdCuentaDestino = IdCuentaDestino;
        this.tipoMoneda = tipoMoneda;
        this.monto = monto;
        this.fecha = fecha;
      
    }

    // Getters y setters

    public int getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(int consecutivo) {
        this.consecutivo = consecutivo;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public int getIdCuentaOrigen() {
        return IdCuentaOrigen;
    }

    public void setIdCuentaOrigen(int cuentaOrigen) {
        this.IdCuentaOrigen = cuentaOrigen;
    }

    public int getIdCuentaDestino() {
        return IdCuentaDestino;
    }

    public void setIdCuentaDestino(int cuentaDestino) {
        this.IdCuentaDestino = cuentaDestino;
    }

    public String getTipoMoneda() {
        return tipoMoneda;
    }

    public void setTipoMoneda(String tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }


}