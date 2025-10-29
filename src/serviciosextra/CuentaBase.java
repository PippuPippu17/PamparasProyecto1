package serviciosextra;

import cuentas.Cuenta;

/**
 * Clase base que adapta una cuenta para poder decorarla con servicios adicionales.
 * @author LasPamparas
 * @version 1.0
 */
public class CuentaBase implements ServAdicional {

  private Cuenta cuenta;

  /**
   * Constructor de la clase CuentaBase.
   * @param cuenta la cuenta a adaptar.
   */
  public CuentaBase(Cuenta cuenta) {
    this.cuenta = cuenta;
  }

  /**
   * {@inheritDoc}
   * Devuelve la descripcion de la cuenta base.
   */
  @Override
  public String getDesc() {
    return "Cuenta base: " + cuenta.getCliente();
  }

  /**
   * {@inheritDoc}
   * Devuelve el costo de la cuenta base, que es 0.
   */
  @Override
  public double getCosto(){
    return 0.0;
  }
  
  /**
   * Devuelve la cuenta asociada.
   * @return la cuenta asociada.
   */
  public Cuenta getCuenta() {
    return cuenta;
  }
}