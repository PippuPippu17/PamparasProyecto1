package procesos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import archivo.GeneradorTXT;
import cuentas.Cuenta;
import cuentas.EstadoSobregirada;
import observer.GestorAlertas;

/**
 * Clase que maneja los procesos mensuales de las cuentas bancarias.
 * Incluye revisión de sobregiros, cálculo de intereses y generación de reportes.
 */
public class ProcesoMensual {
    private List<Cuenta> cuentas;
    private Map<Cuenta, List<Double>> saldosHistoricos;
    private Map<Cuenta, List<String>> registroOperaciones;
    private LocalDate fechaUltimoCorte;
    
    public ProcesoMensual() {
        this.cuentas = new ArrayList<>();
        this.saldosHistoricos = new HashMap<>();
        this.registroOperaciones = new HashMap<>();
        this.fechaUltimoCorte = LocalDate.now();
    }
    
    /**
     * Agrega una cuenta al proceso mensual
     * @param cuenta Cuenta a agregar
     */
    public void agregarCuenta(Cuenta cuenta) {
        cuentas.add(cuenta);
        saldosHistoricos.putIfAbsent(cuenta, new ArrayList<>());
        registroOperaciones.putIfAbsent(cuenta, new ArrayList<>());
        registrarOperacion(cuenta, "Cuenta agregada al proceso mensual");
    }
    
    /**
     * Registra una operación en el historial de la cuenta
     */
    public void registrarOperacion(Cuenta cuenta, String operacion) {
        List<String> operaciones = registroOperaciones.get(cuenta);
        if (operaciones != null) {
            operaciones.add(LocalDate.now() + ": " + operacion);
        }
    }
    
    /**
     * Registra el saldo diario de la cuenta
     */
    public void registrarSaldoDiario(Cuenta cuenta) {
        List<Double> saldos = saldosHistoricos.get(cuenta);
        if (saldos != null) {
            saldos.add(cuenta.getSaldo());
        }
    }
    
    /**
     * Calcula el saldo promedio del mes
     */
    private double calcularSaldoPromedio(Cuenta cuenta) {
        List<Double> saldos = saldosHistoricos.get(cuenta);
        if (saldos == null || saldos.isEmpty()) {
            return 0.0;
        }
        return saldos.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }
    
    /**
     * Ejecuta el proceso mensual para todas las cuentas registradas
     */
    public void ejecutarProcesoMensual() {
        LocalDate fechaActual = LocalDate.now();
        if (fechaActual.getMonth() == fechaUltimoCorte.getMonth()) {
            return; // Ya se ejecutó el proceso este mes
        }
        
        for (Cuenta cuenta : cuentas) {
            GestorAlertas gestor = new GestorAlertas(cuenta);
            
            // 1. Revisión de sobregiros y aplicación de cargos
            if (cuenta.getSaldo() < 0) {
                double cargoSobregiro = 500.0;
                cuenta.setSaldo(cuenta.getSaldo() - cargoSobregiro);
                registrarOperacion(cuenta, "Cargo por sobregiro aplicado: $" + cargoSobregiro);
                gestor.generarAlerta("Se ha aplicado un cargo por sobregiro de $" + cargoSobregiro);
            }
            
            // 2. Cálculo y aplicación de intereses
            double saldoPromedio = calcularSaldoPromedio(cuenta);
            registrarOperacion(cuenta, "Saldo promedio mensual: $" + saldoPromedio);
            
            if (cuenta.getEstado() instanceof EstadoSobregirada) {
                registrarOperacion(cuenta, "No se generaron intereses por estado sobregirado");
            } else {
                cuenta.aplicarInteres();
                registrarOperacion(cuenta, "Intereses aplicados según plan " + 
                    cuenta.getEstrategiaInteres().getNombre());
            }
            
            // 3. Generación de reporte mensual
            generarReporteMensual(cuenta);
            
            // Limpiar históricos del mes
            saldosHistoricos.get(cuenta).clear();
        }
        
        fechaUltimoCorte = fechaActual;
    }
    
    /**
     * Genera el reporte mensual de una cuenta
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
            registroOperaciones.get(cuenta).clear(); // Limpiar registro después de generar reporte
        } catch (Exception e) {
            System.err.println("Error al generar reporte mensual: " + e.getMessage());
        }
    }
}