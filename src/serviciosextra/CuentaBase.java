package serviciosextra;

import cuentas.Cuenta;

/**
 * Clase base que adapta una cuenta para decorarla.
 */
public class CuentaBase implements ServAdicional {

  private Cuenta cuenta;

  public CuentaBase(Cuenta cuenta) {
    this.cuenta = cuenta;
  }

  @Override
  public String getDesc() {
    return "Cuenta base: " + cuenta.getCliente();
  }

  @Override
  public double getCosto(){
    return 0.0;
  }
  

  public Cuenta getCuenta() {
    return cuenta;
  }
}

