package cuentas;

import intereses.StrategyInteres;

/**
 * Interfaz para el patron State, que define las operaciones de la cuenta
 * segun su estado.
 * @author LasPamparas
 * @version 1.0
 */
public interface EstadoCuenta {

  /**
   * Devuelve el nombre del estado actual.
   * 
   * @return el estado como una cadena.
   */
  public String getEstado();

  /**
   * Permite depositar dinero en la cuenta segun su estado.
   * 
   * @param cuenta la cuenta sobre la que se realiza la operacion.
   * @param monto la cantidad a depositar.
   */
  public void depositar(Cuenta cuenta, double monto);

  /**
   * Permite retirar dinero de la cuenta segun su estado.
   * 
   * @param cuenta la cuenta sobre la que se realiza la operacion.
   * @param monto la cantidad a retirar.
   */
  public void retirar(Cuenta cuenta, double monto);

  /**
   * Calcula y aplica los intereses a la cuenta segun su estado.
   *
   * @param cuenta la cuenta sobre la que se calcula el interes.
   * @param estrategia la estrategia de calculo de interes.
   */
  public void calculaInteres(Cuenta cuenta, StrategyInteres estrategia);

  /**
   * Calcula y aplica los intereses con informacion del proceso mensual.
   * @param cuenta la cuenta sobre la que se calcula el interes.
   * @param estrategia la estrategia de calculo de interes.
   * @param mesActual el mes actual (1-12).
   * @param saldoPromedio el saldo promedio del periodo.
   */
  default void calculaInteres(Cuenta cuenta, StrategyInteres estrategia, int mesActual, double saldoPromedio) {
    calculaInteres(cuenta, estrategia); // Por defecto usa el metodo simple
  }
}