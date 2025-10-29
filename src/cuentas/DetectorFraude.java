package cuentas;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Detector de actividad sospechosa para prevenir fraudes.
 * Analiza patrones de comportamiento y marca transacciones riesgosas.
 * @author LasPamparas
 * @version 1.0
 */
public class DetectorFraude {

    private static final double MONTO_SOSPECHOSO_DEPOSITO = 100000.0;
    private static final double MONTO_SOSPECHOSO_RETIRO = 50000.0;
    private static final int MAX_OPERACIONES_POR_SESION = 10;

    private List<Transaccion> historialReciente;
    private int contadorOperaciones;

    public DetectorFraude() {
        this.historialReciente = new ArrayList<>();
        this.contadorOperaciones = 0;
    }

    /**
     * Analiza si un deposito es sospechoso
     * @param monto cantidad a depositar
     * @param saldoActual saldo actual de la cuenta
     * @return true si se detecta actividad sospechosa
     */
    public boolean esDepositoSospechoso(double monto, double saldoActual) {
        // Regla 1: Depositos mayores a $100,000
        if (monto > MONTO_SOSPECHOSO_DEPOSITO) {
            registrarTransaccion("DEPOSITO_ALTO", monto);
            return true;
        }

        // Regla 2: Deposito muy grande comparado con saldo actual
        if (saldoActual > 0 && monto > (saldoActual * 5)) {
            registrarTransaccion("DEPOSITO_INUSUAL", monto);
            return true;
        }

        // Regla 3: Multiples depositos grandes en corto tiempo
        if (depositosRecientesAltos() >= 3) {
            registrarTransaccion("PATRON_DEPOSITOS", monto);
            return true;
        }

        registrarTransaccion("DEPOSITO_NORMAL", monto);
        return false;
    }

    /**
     * Analiza si un retiro es sospechoso
     * @param monto cantidad a retirar
     * @param saldoActual saldo actual de la cuenta
     * @return true si se detecta actividad sospechosa
     */
    public boolean esRetiroSospechoso(double monto, double saldoActual) {
        // Regla 1: Retiros mayores a $50,000
        if (monto > MONTO_SOSPECHOSO_RETIRO) {
            registrarTransaccion("RETIRO_ALTO", monto);
            return true;
        }

        // Regla 2: Retirar mas del 80% del saldo (solo para cuentas con saldo > $1000)
        // Evita falsos positivos en cuentas con saldos pequenos
        if (saldoActual > 1000 && monto > (saldoActual * 0.8)) {
            registrarTransaccion("VACIADO_CUENTA", monto);
            return true;
        }

        // Regla 3: Multiples retiros en secuencia
        if (retirosConsecutivos() >= 5) {
            registrarTransaccion("PATRON_RETIROS", monto);
            return true;
        }

        registrarTransaccion("RETIRO_NORMAL", monto);
        return false;
    }

    /**
     * Verifica si hay exceso de operaciones en poco tiempo
     * @return true si se detecta uso anormal
     */
    public boolean excesoDeOperaciones() {
        contadorOperaciones++;

        if (contadorOperaciones > MAX_OPERACIONES_POR_SESION) {
            registrarTransaccion("EXCESO_OPERACIONES", contadorOperaciones);
            return true;
        }

        return false;
    }

    /**
     * Detecta cambio brusco de patron de uso
     * @param saldoActual saldo actual
     * @param promedioHistorico promedio de saldos anteriores
     * @return true si hay comportamiento anormal
     */
    public boolean cambioBruscoDePatron(double saldoActual, double promedioHistorico) {
        if (promedioHistorico == 0) return false;

        // Si el saldo cambio mas de 10 veces el promedio historico
        double ratio = Math.abs(saldoActual - promedioHistorico) / promedioHistorico;

        if (ratio > 10) {
            registrarTransaccion("CAMBIO_PATRON", saldoActual);
            return true;
        }

        return false;
    }

    /**
     * Obtiene descripcion de la ultima actividad sospechosa detectada
     * @return descripcion del fraude detectado
     */
    public String obtenerDescripcionFraude() {
        if (historialReciente.isEmpty()) {
            return "Actividad sospechosa detectada";
        }

        Transaccion ultima = historialReciente.get(historialReciente.size() - 1);

        switch (ultima.tipo) {
            case "DEPOSITO_ALTO":
                return "Deposito inusualmente alto: $" + String.format("%.2f", ultima.monto);
            case "DEPOSITO_INUSUAL":
                return "Deposito desproporcionado al historial de la cuenta";
            case "PATRON_DEPOSITOS":
                return "Multiples depositos grandes detectados";
            case "RETIRO_ALTO":
                return "Retiro de alto riesgo: $" + String.format("%.2f", ultima.monto);
            case "VACIADO_CUENTA":
                return "Intento de vaciado completo de cuenta";
            case "PATRON_RETIROS":
                return "Patron sospechoso de retiros consecutivos";
            case "EXCESO_OPERACIONES":
                return "Numero excesivo de operaciones en corto tiempo";
            case "CAMBIO_PATRON":
                return "Cambio brusco en el patron de uso de la cuenta";
            default:
                return "Actividad sospechosa detectada";
        }
    }

    /**
     * Reinicia el contador de operaciones (se usa al inicio de sesion)
     */
    public void reiniciarContador() {
        contadorOperaciones = 0;
    }

    /**
     * Limpia el historial antiguo (mantiene solo ultimas 20 transacciones)
     */
    public void limpiarHistorialAntiguo() {
        if (historialReciente.size() > 20) {
            historialReciente = historialReciente.subList(
                historialReciente.size() - 20,
                historialReciente.size()
            );
        }
    }

    // ========== Metodos privados auxiliares ==========

    private void registrarTransaccion(String tipo, double monto) {
        historialReciente.add(new Transaccion(tipo, monto, LocalDateTime.now()));
        limpiarHistorialAntiguo();
    }

    private int depositosRecientesAltos() {
        int count = 0;
        for (int i = historialReciente.size() - 1; i >= 0 && i >= historialReciente.size() - 10; i--) {
            Transaccion t = historialReciente.get(i);
            if (t.tipo.contains("DEPOSITO") && t.monto > 50000) {
                count++;
            }
        }
        return count;
    }

    private int retirosConsecutivos() {
        int count = 0;
        for (int i = historialReciente.size() - 1; i >= 0; i--) {
            Transaccion t = historialReciente.get(i);
            if (t.tipo.contains("RETIRO")) {
                count++;
            } else {
                break; // Se rompe la secuencia
            }
        }
        return count;
    }

    /**
     * Clase interna para registrar transacciones
     */
    private static class Transaccion {
        String tipo;
        double monto;

        Transaccion(String tipo, double monto, LocalDateTime fecha) {
            this.tipo = tipo;
            this.monto = monto;
            // fecha se usa para timestamp interno pero no se accede directamente
        }
    }
}
