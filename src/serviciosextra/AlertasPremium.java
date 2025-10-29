package serviciosextra;

import cuentas.Cuenta;

/**
 * Servicio adicional: Alertas Premium.
 * @author LasPamparas
 * @version 1.0
 */
public class AlertasPremium extends ServDecorator {

  /**
   * Constructor para el decorador de Alertas Premium.
   * @param servicioNormal el servicio base a decorar.
   * @param cuenta la cuenta asociada.
   */
  public AlertasPremium(ServAdicional servicioNormal, Cuenta cuenta) {
    super(servicioNormal, cuenta);
  }

  /**
   * {@inheritDoc}
   * Agrega la descripcion de las Alertas Premium a la descripcion del servicio base.
   */
  @Override
  public String getDesc() {
    return servicioNormal.getDesc() +
    " + Alertas Premium: Se notifica cada que alguien accede a la cuenta.";
  }

  /**
   * {@inheritDoc}
   * Agrega el costo de las Alertas Premium al costo del servicio base.
   */
  @Override
  public double getCosto() {
    return servicioNormal.getCosto() + 700.0;
  }
}