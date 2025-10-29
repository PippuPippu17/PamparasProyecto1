package intereses;

/**
 * Estrategia de interes mensual.
 * @author LasPamparas
 * @version 1.0
 */
public class InteresMensual implements StrategyInteres {

  /**
   * {@inheritDoc}
   * Devuelve el nombre de la estrategia: "Mensual".
   */
  @Override
  public String getNombre(){
    return "Mensual";
  }

  /**
   * {@inheritDoc}
   * Calcula el interes mensual. Se aplica un 1.5% si el saldo está entre 1000 y 50000.
   */
  @Override
  public double calInteres(double saldo) {
    if (saldo >= 1000 && saldo < 50000) {
      return saldo * 0.015;
    }
    return 0;
  }

}