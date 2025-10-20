package cuentas;

import intereses.StrategyInteres;

/**
 * Estado Sobregirada. Se aplican cargos antes de operar de nuevo.
 */
public class EstadoSobregirada implements EstadoCuenta {

  @Override
  public String getEstado(){
    return "Sobregirada";
  }

  @Override
  public void depositar(Cuenta cuenta, double monto) {
    double cargo = 500.0;
    System.out.println("Cargo por sobregiro de cuenta $" + cargo);
    cuenta.setSaldo(cuenta.getSaldo() - cargo + monto);
    if (cuenta.getSaldo() >= 0) {
      cuenta.setEstado(new EstadoActiva());
      System.out.println("La Cuenta pasa a activa");
      System.out.println("Saldo Resultante: $" + cuenta.getSaldo());
    } else {
      System.out.println("Cuenta sigue sobregirada");
      System.out.println("Saldo Resultante: $" + cuenta.getSaldo());
    }
  }

  @Override
  public void retirar(Cuenta cuenta, double monto) {
  System.out.println("La cuenta no tiene saldo");
  }

  @Override
  public void calculaInteres(Cuenta cuenta, StrategyInteres estrategia) {
    System.out.println("No se genera interes.");
  }

}

