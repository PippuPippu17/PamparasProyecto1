package intereses;

/**
 * interes anual.
 */
public class InteresAnual implements StrategyInteres {

  @Override
  public String getNombre() {
    return "Anual";
  }

  @Override
  public double calInteres(double saldo) {
    if (saldo >= 50000) {
      return saldo * 0.055;
    }
    return 0;
  }

}

