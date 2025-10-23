package cuentas;

import intereses.StrategyInteres;

/**
 * Interfaz de operaciones de lacuenta
 * según su estado 
 */
public interface EstadoCuenta {

  /**
   * Devuelve el estado
   * 
   * @return estado como cadena
   */
  public String getEstado();

  /**
   * Permite depositar dinero en la cuenta segun su estado.
   * 
   * @param cuenta cuenta sobre la que se realiza la operacion
   * @param monto cantidad a depositar
   */
  public void depositar(Cuenta cuenta, double monto);

  /**
   * Permite retirar dinero de la cuenta segun su estado.
   * 
   * @param cuenta cuenta sobre la que se realiza la operacion
   * @param monto cantidad a retirar
   */
  public void retirar(Cuenta cuenta, double monto);

  /**
   * Calcula y aplica los intereses a la cuenta segun su estado.
   * 
   * @param cuenta cuenta sobre la que se calcula el interes
   * @param estrategia estrategia de calculo de interes
   */
  public void calculaInteres(Cuenta cuenta, StrategyInteres estrategia);
}

