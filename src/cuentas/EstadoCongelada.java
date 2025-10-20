package cuentas;
import intereses.StrategyInteres;

/**
 * Estado Congelada. Se bloquean todas las operaciones.
 */
public class EstadoCongelada implements EstadoCuenta {

  @Override
  public String getEstado() {
    return "Congelada";
  }
  
  @Override
  public void depositar(Cuenta cuenta, double monto) {
    System.out.println("Operacion bloqueada, cuenta congelada.");
  }

  @Override
  public void retirar(Cuenta cuenta, double monto) {
    System.out.println("Operacion bloqueada, cuenta congelada.");
  }

  @Override
  public void calculaInteres(Cuenta cuenta, StrategyInteres estrategia) {
    System.out.println("Operacion bloqueada, cuenta congelada.");
  }

}

