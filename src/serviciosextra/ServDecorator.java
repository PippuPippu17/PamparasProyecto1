package serviciosextra;

import cuentas.Cuenta;

/**
 * Clase abstracta de base para los servicios
 */
public abstract class ServDecorator implements ServAdicional {
  
  protected ServAdicional servicioNormal;
  protected Cuenta cuenta;

  public ServDecorator(ServAdicional servicioNormal, Cuenta cuenta) {
    this.servicioNormal = servicioNormal;
    this.cuenta = cuenta;
  }

  @Override
  public String getDesc() {
    return servicioNormal.getDesc();
  }

  @Override
  public double getCosto() {
    return servicioNormal.getCosto();
  }
}

