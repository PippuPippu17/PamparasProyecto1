package cuentas;

import intereses.StrategyInteres;

/**
 * Estado de cuenta. Para esto se usa Patron State.
 */
public interface EstadoCuenta {
  public String getEstado();
  public void depositar(Cuenta cuenta, double monto);
  public void retirar(Cuenta cuenta, double monto);
  public void calculaInteres(Cuenta cuenta, StrategyInteres estrategia);
}

