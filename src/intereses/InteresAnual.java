package intereses;

/**
 * Estrategia de interes anual.
 * Se aplica SOLO en el mes 12 (diciembre) y si el saldo promedio fue de $50,000 o mas.
 * @author LasPamparas
 * @version 1.0
 */
public class InteresAnual implements StrategyInteres {

  /**
   * {@inheritDoc}
   * Devuelve el nombre de la estrategia: "Anual".
   */
  @Override
  public String getNombre() {
    return "Anual";
  }

  /**
   * {@inheritDoc}
   * Metodo simple - por compatibilidad, no aplica interes.
   * El interes anual solo se aplica con el metodo que recibe mes y saldo promedio.
   */
  @Override
  public double calInteres(double saldo) {
    return 0; // No aplica sin verificar mes y saldo promedio
  }

  /**
   * {@inheritDoc}
   * Calcula el interes anual SOLO en el mes 12 con un saldo promedio >= $50,000.
   * @return 5.5% del saldo actual si cumple las condiciones, 0 en caso contrario.
   */
  @Override
  public double calInteres(double saldo, int mesActual, double saldoPromedio) {
    // SOLO aplica en mes 12 (diciembre) Y si saldo promedio >= 50000
    if (mesActual == 12 && saldoPromedio >= 50000) {
      return saldo * 0.055; // 5.5%
    }
    return 0;
  }

}