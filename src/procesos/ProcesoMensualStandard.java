package procesos;

import cuentas.Cuenta;
import cuentas.EstadoSobregirada;
import observer.GestorAlertas;

/**
 * Implementacion estandar del proceso mensual.
 * Define las politicas normales de cargos e intereses.
 * @author LasPamparas
 * @version 1.0
 */
public class ProcesoMensualStandard extends ProcesoMensualTemplate {

    private static final double CARGO_SOBREGIRO = 500.0;

    /**
     * Revisa si la cuenta esta en numeros rojos y aplica un cargo de $500.
     *
     * @param cuenta la cuenta a revisar.
     */
    @Override
    protected void revisarSobregiros(Cuenta cuenta) {
        if (cuenta.getSaldo() < 0) {
            cuenta.setSaldo(cuenta.getSaldo() - CARGO_SOBREGIRO);
            System.out.println("Cargo por sobregiro aplicado: $" + CARGO_SOBREGIRO);

            GestorAlertas gestor = new GestorAlertas(cuenta);
            gestor.generarAlerta("Se ha aplicado un cargo por sobregiro de $" + CARGO_SOBREGIRO);
        }
    }

    /**
     * Calcula y aplica intereses segun la estrategia de la cuenta.
     * No aplica intereses si la cuenta esta sobregirada.
     *
     * @param cuenta la cuenta a la que se aplican los intereses.
     * @param mesActual el mes actual (1-12).
     * @param saldoPromedio el saldo promedio del periodo.
     */
    @Override
    protected void calcularIntereses(Cuenta cuenta, int mesActual, double saldoPromedio) {
        if (cuenta.getEstado() instanceof EstadoSobregirada) {
            System.out.println("No se generaron intereses por estado sobregirado.");
        } else {
            // Aplicar interes con informacion del mes actual y saldo promedio
            cuenta.getEstado().calculaInteres(cuenta, cuenta.getEstrategiaInteres(), mesActual, saldoPromedio);
            System.out.println("Intereses aplicados segun plan " +
                cuenta.getEstrategiaInteres().getNombre() +
                " (mes " + mesActual + ", saldo promedio: $" + saldoPromedio + ")");
        }
    }
}