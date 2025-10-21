package serviciosextra;

import cuentas.*;

/**
 * Servicio adicional: Programa de recompensas.
 */
public class Recompensas extends ServDecorator{
  public Recompensas(ServAdicional servicioNormal, Cuenta cuenta) {
    super(servicioNormal, cuenta);
  }

  @Override
  public String getDesc() {
    return servicioNormal.getDesc() +
    " + Recompensas: Por tener monto minimo de 2,000 se recompensan 100";
  }

  @Override
  public double getCosto() {
    if (cuenta.getSaldo() > 2000){
      double recompensa = 100;
      cuenta.setSaldo(cuenta.getSaldo() + recompensa);
      System.out.println("¡Has recibido una recompensa de $100!");
    }
    return servicioNormal.getCosto() + 0.0;  
    
  }
}

