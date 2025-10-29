package cuentas;

import intereses.StrategyInteres;

/**
 * Representa el estado "Cerrada" de una cuenta.
 * No permite operaciones.
 * @author LasPamparas
 * @version 1.0
 */
public class EstadoCerrada implements EstadoCuenta {

  /**
   * {@inheritDoc}
   * Devuelve el nombre del estado actual: "Cerrada".
   */
  @Override
  public String getEstado() {
    return "Cerrada";
  }

  /**
   * {@inheritDoc}
   * No permite depositos, ya que la cuenta está cerrada.
   */
  @Override
  public void depositar(Cuenta cuenta, double monto) {
    System.out.println("Cuenta cerrada. No permite operaciones.");
  }

  /**
   * {@inheritDoc}
   * No permite retiros, ya que la cuenta está cerrada.
   */
  @Override
  public void retirar(Cuenta cuenta, double monto) {
    System.out.println("Cuenta cerrada. No permite operaciones.");
  }

  /**
   * {@inheritDoc}
   * No calcula intereses, ya que la cuenta está cerrada.
   */
  @Override
  public void calculaInteres(Cuenta cuenta, StrategyInteres estrategia) {
    System.out.println("Cuenta cerrada. No permite operaciones.");
  }
}