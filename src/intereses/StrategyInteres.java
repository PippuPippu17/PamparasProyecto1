package intereses;

/**
 * Interfaz para el patron Strategy, que define el tipo de interes de las cuentas.
 * (Mensual, Anual o Premium)
 * @author LasPamparas
 * @version 1.0
 */
public interface StrategyInteres {
  /**
   * Devuelve el nombre de la estrategia de interes.
   * @return el nombre de la estrategia.
   */
  public String getNombre();

  /**
   * Calcula el interes basado en el saldo.
   * @param saldo el saldo actual de la cuenta.
   * @return el monto de interes calculado.
   */
  public double calInteres(double saldo);

  /**
   * Calcula el interes con informacion adicional del proceso mensual.
   * @param saldo el saldo actual de la cuenta.
   * @param mesActual el mes actual (1-12).
   * @param saldoPromedio el saldo promedio del periodo.
   * @return el monto de interes calculado.
   */
  default double calInteres(double saldo, int mesActual, double saldoPromedio) {
    return calInteres(saldo); // Por defecto usa el metodo simple
  }
}