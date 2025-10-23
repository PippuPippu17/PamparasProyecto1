 package cuentas;

import intereses.StrategyInteres;

/**
 * Representa el estado "Congelada" de una cuenta
 * Bloquea operaciones por actividad sospechosa. 
 * */
public class EstadoCongelada implements EstadoCuenta {

  /** {@inheritDoc} */
  @Override
  public String getEstado() {
    return "Congelada";
  }

  /** {@inheritDoc} */
  @Override
  public void depositar(Cuenta cuenta, double monto) {
    System.out.println("Operación bloqueada: la cuenta está congelada.");
  }

  /** {@inheritDoc} */
  @Override
  public void retirar(Cuenta cuenta, double monto) {
    System.out.println("Operación bloqueada: la cuenta está congelada.");
  }

  /** {@inheritDoc} */
  @Override
  public void calculaInteres(Cuenta cuenta, StrategyInteres estrategia) {
    System.out.println("Operación bloqueada: la cuenta está congelada.");
  }
}


