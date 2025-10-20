package cuentas;

import intereses.StrategyInteres;

/**
 * Estado Cerrada: conserva la antiguedad, no permite ninugna operacion.
 */
public class EstadoCerrada implements EstadoCuenta {

  @Override
  public String getEstado() {
    return "Cerrada";
  }

  @Override
  public void depositar(Cuenta cuenta, double monto) {
    System.out.println("Cuenta cerrada. No permite operaciones.");
  }

  @Override
  public void retirar(Cuenta cuenta, double monto) {
    System.out.println("Cuenta cerrada. No permite operaciones.");
  }

  @Override
  public void calculaInteres(Cuenta cuenta, StrategyInteres estrategia) {
    System.out.println("Cuenta cerrada. No permite operaciones.");
  }
}

