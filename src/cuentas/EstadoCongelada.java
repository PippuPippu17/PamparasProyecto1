package cuentas;

import intereses.StrategyInteres;

/**
 * Representa el estado "Congelada" de una cuenta.
 * Bloquea operaciones por actividad sospechosa.
 * @author LasPamparas
 * @version 1.0
 */
public class EstadoCongelada implements EstadoCuenta {

  /**
   * {@inheritDoc}
   * Devuelve el nombre del estado actual: "Congelada".
   */
  @Override
  public String getEstado() {
    return "Congelada";
  }

  /**
   * {@inheritDoc}
   * No permite depositos, ya que la cuenta está congelada.
   * @throws IllegalStateException siempre, porque la cuenta está congelada.
   */
  @Override
  public void depositar(Cuenta cuenta, double monto) {
    throw new IllegalStateException("Operacion bloqueada: la cuenta está congelada.");
  }

  /**
   * {@inheritDoc}
   * No permite retiros, ya que la cuenta está congelada.
   * @throws IllegalStateException siempre, porque la cuenta está congelada.
   */
  @Override
  public void retirar(Cuenta cuenta, double monto) {
    throw new IllegalStateException("Operacion bloqueada: la cuenta está congelada.");
  }

  /**
   * {@inheritDoc}
   * No calcula intereses, ya que la cuenta está congelada.
   */
  @Override
  public void calculaInteres(Cuenta cuenta, StrategyInteres estrategia) {
    System.out.println("Operacion bloqueada: la cuenta está congelada.");
  }
}