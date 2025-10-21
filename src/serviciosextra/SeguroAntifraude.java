package serviciosextra;

import cuentas.Cuenta;

/**
 * Servicio adicional: Seguro antifraude.
 */
public class SeguroAntifraude extends ServDecorator{

  public SeguroAntifraude(ServAdicional servicioNormal, Cuenta cuenta) {
    super(servicioNormal, cuenta);
  }

  @Override
  public String getDesc() {
    return servicioNormal.getDesc() +
    " + Seguro Antifraude: La cuenta se asegura con doble verificacion del banco. ";
  }

  @Override
  public double getCosto() {
    return servicioNormal.getCosto() + 1500.0;
  }
}

