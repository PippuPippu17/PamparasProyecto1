package serviciosextra;

import cuentas.Cuenta;

/**
 * Servicio adicional: Alertas Premium.
 */
public class AlertasPremium extends ServDecorator {

  public AlertasPremium(ServAdicional servicioNormal, Cuenta cuenta) {
    super(servicioNormal, cuenta);
  }

  @Override
  public String getDesc() {
    return servicioNormal.getDesc() +
    " + Alertas Premium: Se notifica cada que alguien accede a la cuenta.";
  }

  @Override
  public double getCosto() {
    return servicioNormal.getCosto() + 700.0;
  }
}

