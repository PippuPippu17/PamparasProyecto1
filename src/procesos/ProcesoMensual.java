package procesos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import archivo.GeneradorTXT;
import cuentas.Cuenta;

/**
 * Clase que maneja los procesos mensuales de las cuentas bancarias.
 * Incluye revision de sobregiros, calculo de intereses y generacion de reportes.
 * @author LasPamparas
 * @version 1.0
 */
public class ProcesoMensual {
    private List<Cuenta> cuentas;
    private Map<Cuenta, List<Double>> saldosHistoricos;
    private Map<Cuenta, List<String>> registroOperaciones;
    private LocalDate fechaUltimoCorte;
    
    /**
     * Constructor de la clase ProcesoMensual.
     * Inicializa las listas y mapas necesarios.
     */
    public ProcesoMensual() {
        this.cuentas = new ArrayList<>();
        this.saldosHistoricos = new HashMap<>();
        this.registroOperaciones = new HashMap<>();
        this.fechaUltimoCorte = LocalDate.now();
    }
    
    /**
     * Agrega una cuenta al proceso mensual.
     * @param cuenta Cuenta a agregar.
     */
    public void agregarCuenta(Cuenta cuenta) {
        cuentas.add(cuenta);
        saldosHistoricos.putIfAbsent(cuenta, new ArrayList<>());
        registroOperaciones.putIfAbsent(cuenta, new ArrayList<>());
        registrarOperacion(cuenta, "Cuenta agregada al proceso mensual");
    }
    
    /**
     * Registra una operacion en el historial de la cuenta.
     * @param cuenta La cuenta en la que se registra la operacion.
     * @param operacion La descripcion de la operacion.
     */
    public void registrarOperacion(Cuenta cuenta, String operacion) {
        List<String> operaciones = registroOperaciones.get(cuenta);
        if (operaciones != null) {
            operaciones.add(LocalDate.now() + ": " + operacion);
        }
    }
    
    /**
     * Registra el saldo diario de la cuenta.
     * @param cuenta La cuenta cuyo saldo se registra.
     */
    public void registrarSaldoDiario(Cuenta cuenta) {
        List<Double> saldos = saldosHistoricos.get(cuenta);
        if (saldos != null) {
            saldos.add(cuenta.getSaldo());
        }
    }
    
    /**
     * Calcula el saldo promedio del mes.
     * @param cuenta La cuenta para la que se calcula el saldo promedio.
     * @return El saldo promedio del mes.
     */
    private double calcularSaldoPromedio(Cuenta cuenta) {
        List<Double> saldos = saldosHistoricos.get(cuenta);
        if (saldos == null || saldos.isEmpty()) {
            return 0.0;
        }
        return saldos.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }
    
    /**
     * Ejecuta el proceso mensual para todas las cuentas registradas.
     * Utiliza el patron Template Method mediante ProcesoMensualStandard.
     */
    public void ejecutarProcesoMensual() {
        LocalDate fechaActual = LocalDate.now();
        if (fechaActual.getMonth() == fechaUltimoCorte.getMonth()) {
            return; // Ya se ejecuto el proceso este mes
        }

        // Crear instancia del Template Method con implementacion estandar
        ProcesoMensualTemplate procesoTemplate = new ProcesoMensualStandard();
        int mesActual = fechaActual.getMonthValue(); // 1-12

        for (Cuenta cuenta : cuentas) {
            // Calcular saldo promedio antes de ejecutar el template
            double saldoPromedio = calcularSaldoPromedio(cuenta);
            registrarOperacion(cuenta, "Saldo promedio mensual: $" + saldoPromedio);

            // EJECUTAR TEMPLATE METHOD - define la secuencia de pasos
            procesoTemplate.ejecutarProcesoMensual(cuenta, mesActual, saldoPromedio);

            // Registrar en el historial
            registrarOperacion(cuenta, "Proceso mensual ejecutado correctamente");

            // Generar reporte completo con historial
            generarReporteMensual(cuenta);

            // Limpiar historicos del mes
            saldosHistoricos.get(cuenta).clear();
        }

        fechaUltimoCorte = fechaActual;
    }
    
    /**
     * Genera el reporte mensual de una cuenta.
     * @param cuenta La cuenta para la que se genera el reporte.
     */
    private void generarReporteMensual(Cuenta cuenta) {
        StringBuilder reporte = new StringBuilder();
        reporte.append("REPORTE MENSUAL - ").append(LocalDate.now()).append("\n\n");
        reporte.append("Cliente: ").append(cuenta.getCliente()).append("\n");
        reporte.append("Estado de cuenta: ").append(cuenta.getEstado().getEstado()).append("\n");
        reporte.append("Saldo actual: $").append(cuenta.getSaldo()).append("\n");
        reporte.append("Saldo promedio: $").append(calcularSaldoPromedio(cuenta)).append("\n\n");
        reporte.append("REGISTRO DE OPERACIONES:\n");
        
        List<String> operaciones = registroOperaciones.get(cuenta);
        if (operaciones != null) {
            operaciones.forEach(op -> reporte.append(op).append("\n"));
        }
        
        String nombreArchivo = cuenta.getCliente() + "_reporte_" + 
            fechaUltimoCorte.getMonth() + "_" + fechaUltimoCorte.getYear() + ".txt";
        
        try {
            GeneradorTXT.exportarContenido(nombreArchivo, reporte.toString());
            registroOperaciones.get(cuenta).clear(); // Limpiar registro despues de generar reporte
        } catch (Exception e) {
            System.err.println("Error al generar reporte mensual: " + e.getMessage());
        }
    }
}
