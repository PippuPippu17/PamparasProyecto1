package intereses;

/**
 * Interes premium
 */
public class InteresPremium implements StrategyInteres {

  @Override
  public String getNombre(){
    return "Premium";
  }

  @Override
  public double calInteres(double saldo){
    return saldo * 0.1;

  }
}
