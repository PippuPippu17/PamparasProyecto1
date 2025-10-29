package serviciosextra;

import cuentas.Cuenta;

/**
 * Clase abstracta base para los decoradores de servicios.
 * @author LasPamparas
 * @version 1.0
 */
public abstract class ServDecorator implements ServAdicional {
  
  protected ServAdicional servicioNormal;
  protected Cuenta cuenta;

  /**
   * Constructor para el decorador de servicios.
   * @param servicioNormal el servicio base a decorar.
   * @param cuenta la cuenta asociada.
   */
  public ServDecorator(ServAdicional servicioNormal, Cuenta cuenta) {
    this.servicioNormal = servicioNormal;
    this.cuenta = cuenta;
  }

  /**
   * {@inheritDoc}
   * Devuelve la descripcion del servicio base.
   */
  @Override
  public String getDesc() {
    return servicioNormal.getDesc();
  }

  /**
   * {@inheritDoc}
   * Devuelve el costo del servicio base.
   */
  @Override
  public double getCosto() {
    return servicioNormal.getCosto();
  }
}