package cuentas;

import intereses.StrategyInteres;

/**
 * Representa el estado "Cerrada" de una cuenta
 * 
 * No permite operaciones
 */
public class EstadoCerrada implements EstadoCuenta {

  /** {@inheritDoc} */
  @Override
  public String getEstado() {
    return "Cerrada";
  }

  /** {@inheritDoc} */
  @Override
  public void depositar(Cuenta cuenta, double monto) {
    System.out.println("Cuenta cerrada. No permite operaciones.");
  }

  /** {@inheritDoc} */
  @Override
  public void retirar(Cuenta cuenta, double monto) {
    System.out.println("Cuenta cerrada. No permite operaciones.");
  }

  /** {@inheritDoc} */
  @Override
  public void calculaInteres(Cuenta cuenta, StrategyInteres estrategia) {
    System.out.println("Cuenta cerrada. No permite operaciones.");
  }
}

