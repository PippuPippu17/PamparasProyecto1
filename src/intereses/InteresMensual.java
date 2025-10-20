package intereses;

/**
 * Interes mensual.
 */
public class InteresMensual implements StrategyInteres {

  @Override
  public String getNombre(){
    return "Mensual";
  }

  @Override
  public double calInteres(double saldo) {
    if (saldo >= 1000) {
      return saldo * 0.015;
    }
    return 0;
  }

}

