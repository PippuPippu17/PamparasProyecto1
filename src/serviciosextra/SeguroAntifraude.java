package serviciosextra;

import cuentas.Cuenta;

/**
 * Servicio adicional: Seguro antifraude.
 * @author LasPamparas
 * @version 1.0
 */
public class SeguroAntifraude extends ServDecorator{

  /**
   * Constructor para el decorador de Seguro Antifraude.
   * @param servicioNormal el servicio base a decorar.
   * @param cuenta la cuenta asociada.
   */
  public SeguroAntifraude(ServAdicional servicioNormal, Cuenta cuenta) {
    super(servicioNormal, cuenta);
  }

  /**
   * {@inheritDoc}
   * Agrega la descripcion del seguro antifraude a la descripcion del servicio base.
   */
  @Override
  public String getDesc() {
    return servicioNormal.getDesc() +
    " + Seguro Antifraude: La cuenta se asegura con doble verificacion del banco. ";
  }

  /**
   * {@inheritDoc}
   * Agrega el costo del seguro antifraude al costo del servicio base.
   */
  @Override
  public double getCosto() {
    return servicioNormal.getCosto() + 1500.0;
  }
}