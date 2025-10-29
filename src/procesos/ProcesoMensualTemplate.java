package procesos;

import cuentas.Cuenta;

/**
 * Clase abstracta que define el Template Method para el proceso mensual.
 * Define el esqueleto del algoritmo que se ejecuta cada mes.
 * @author LasPamparas
 * @version 1.0
 */
public abstract class ProcesoMensualTemplate {

    /**
     * TEMPLATE METHOD - Define la secuencia fija de pasos del proceso mensual.
     * Este metodo es final para garantizar que el orden no se pueda cambiar.
     *
     * @param cuenta cuenta sobre la que se ejecuta el proceso
     * @param mesActual mes actual (1-12)
     * @param saldoPromedio saldo promedio del periodo
     */
    public final void ejecutarProcesoMensual(Cuenta cuenta, int mesActual, double saldoPromedio) {
        // Secuencia FIJA de pasos (no se puede cambiar)

        // Paso 1: Actualizar antiguedad (comun para todos)
        actualizarAntiguedad(cuenta);

        // Paso 2: Revisar y aplicar cargos por sobregiro (varia segun tipo)
        revisarSobregiros(cuenta);

        // Paso 3: Calcular y aplicar intereses (varia segun tipo)
        calcularIntereses(cuenta, mesActual, saldoPromedio);

        // Paso 4: Generar reporte (hook - puede ser sobrescrito)
        generarReporte(cuenta, mesActual, saldoPromedio);
    }

    /**
     * Paso concreto: Actualiza la antiguedad de la cuenta.
     * Este paso es comun para todas las variantes del proceso.
     *
     * @param cuenta cuenta a actualizar
     */
    protected void actualizarAntiguedad(Cuenta cuenta) {
        cuenta.incrementarAntiguedad();
        System.out.println("Antiguedad actualizada: " + cuenta.getMesesAntiguedad() + " meses");
    }

    /**
     * Paso abstracto: Revisa y aplica cargos por sobregiro.
     * Cada subclase define su propia politica de cargos.
     *
     * @param cuenta cuenta a revisar
     */
    protected abstract void revisarSobregiros(Cuenta cuenta);

    /**
     * Paso abstracto: Calcula y aplica intereses segun la estrategia.
     * Cada subclase define como calcular los intereses.
     *
     * @param cuenta cuenta a la que se aplican intereses
     * @param mesActual mes actual (1-12)
     * @param saldoPromedio saldo promedio del periodo
     */
    protected abstract void calcularIntereses(Cuenta cuenta, int mesActual, double saldoPromedio);

    /**
     * Hook: Genera el reporte mensual.
     * Implementacion por defecto. Las subclases pueden sobrescribirlo
     * para generar reportes personalizados.
     *
     * @param cuenta cuenta del reporte
     * @param mesActual mes actual
     * @param saldoPromedio saldo promedio
     */
    protected void generarReporte(Cuenta cuenta, int mesActual, double saldoPromedio) {
        StringBuilder reporte = new StringBuilder();
        reporte.append("\n=== REPORTE MENSUAL - Mes ").append(mesActual).append(" ===\n");
        reporte.append("Cliente: ").append(cuenta.getCliente()).append("\n");
        reporte.append("Estado: ").append(cuenta.getEstado().getEstado()).append("\n");
        reporte.append("Saldo actual: $").append(cuenta.getSaldo()).append("\n");
        reporte.append("Saldo promedio: $").append(saldoPromedio).append("\n");
        reporte.append("Antiguedad: ").append(cuenta.getMesesAntiguedad()).append(" meses\n");
        reporte.append("=================================\n");

        System.out.println(reporte.toString());
    }
}