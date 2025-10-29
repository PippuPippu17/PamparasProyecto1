package procesos;

import cuentas.Cuenta;
import cuentas.EstadoSobregirada;
import observer.GestorAlertas;

/**
 * Implementacion Premium del proceso mensual.
 * Politicas mas flexibles: NO cobra sobregiro y genera reportes detallados.
 *
 * Esta clase demuestra como el Template Method permite diferentes implementaciones
 * sin cambiar la secuencia de pasos definida en la clase abstracta.
 * @author LasPamparas
 * @version 1.0
 */
public class ProcesoMensualPremium extends ProcesoMensualTemplate {

    /**
     * Implementacion Premium: NO cobra sobregiro, solo notifica.
     * Los clientes premium tienen este beneficio.
     *
     * @param cuenta cuenta a revisar
     */
    @Override
    protected void revisarSobregiros(Cuenta cuenta) {
        if (cuenta.getSaldo() < 0) {
            System.out.println("*** Cliente Premium - Cargo por sobregiro PERDONADO ***");
            System.out.println("Saldo negativo: $" + cuenta.getSaldo());

            GestorAlertas gestor = new GestorAlertas(cuenta);
            gestor.generarAlerta("Cuenta Premium en numeros rojos - cargo perdonado");
        }
    }

    /**
     * Calcula intereses con bonificacion para clientes premium.
     * Aplica intereses incluso si esta sobregirada (beneficio premium).
     *
     * @param cuenta cuenta a la que se aplican intereses
     * @param mesActual mes actual (1-12)
     * @param saldoPromedio saldo promedio del periodo
     */
    @Override
    protected void calcularIntereses(Cuenta cuenta, int mesActual, double saldoPromedio) {
        // Premium: Aplica interes INCLUSO si esta sobregirada
        if (cuenta.getEstado() instanceof EstadoSobregirada) {
            System.out.println("*** Beneficio Premium: generando intereses a pesar de sobregiro ***");
        }

        cuenta.getEstado().calculaInteres(cuenta, cuenta.getEstrategiaInteres(), mesActual, saldoPromedio);
        System.out.println("Intereses Premium aplicados segun plan " +
            cuenta.getEstrategiaInteres().getNombre() +
            " (mes " + mesActual + ", saldo promedio: $" + saldoPromedio + ")");
    }

    /**
     * Override del hook para generar reporte premium mas detallado.
     *
     * @param cuenta cuenta del reporte
     * @param mesActual mes actual
     * @param saldoPromedio saldo promedio
     */
    @Override
    protected void generarReporte(Cuenta cuenta, int mesActual, double saldoPromedio) {
        StringBuilder reporte = new StringBuilder();
        reporte.append("\n");
        reporte.append("╔════════════════════════════════════════════╗\n");
        reporte.append("║      REPORTE MENSUAL PREMIUM              ║\n");
        reporte.append("╚════════════════════════════════════════════╝\n\n");
        reporte.append("Mes: ").append(mesActual).append("\n");
        reporte.append("Cliente VIP: ").append(cuenta.getCliente()).append("\n");
        reporte.append("Estado: ").append(cuenta.getEstado().getEstado()).append("\n");
        reporte.append("Saldo actual: $").append(String.format("%.2f", cuenta.getSaldo())).append("\n");
        reporte.append("Saldo promedio: $").append(String.format("%.2f", saldoPromedio)).append("\n");
        reporte.append("Antiguedad: ").append(cuenta.getMesesAntiguedad()).append(" meses\n");
        reporte.append("\nBeneficios Premium activos:\n");
        reporte.append("  ✓ Sin cargos por sobregiro\n");
        reporte.append("  ✓ Intereses en cualquier estado\n");
        reporte.append("  ✓ Atencion prioritaria 24/7\n");
        reporte.append("  ✓ Recompensas duplicadas\n");
        reporte.append("\n════════════════════════════════════════════\n");

        System.out.println(reporte.toString());
    }
}