package cuentas;

import intereses.StrategyInteres;

/**
 * Representa el estado "Sobregirada" de una cuenta
 *  
 * En este estado, se aplican cargos y restricciones hasta tener saldo positivo
 */
public class EstadoSobregirada implements EstadoCuenta {

  /** {@inheritDoc} */
  @Override
  public String getEstado() {
    return "Sobregirada";
  }

  /** {@inheritDoc} */
  @Override
  public void depositar(Cuenta cuenta, double monto) {
    double cargo = 500.0;
    System.out.println("Cargo por sobregiro de cuenta: $" + cargo);
    cuenta.setSaldo(cuenta.getSaldo() - cargo + monto);

    if (cuenta.getSaldo() >= 0) {
      cuenta.setEstado(new EstadoActiva());
      System.out.println("La cuenta vuelve a estar activa. Saldo actual: $" + cuenta.getSaldo());
    } else {
      System.out.println("Cuenta sigue sobregirada. Saldo actual: $" + cuenta.getSaldo());
    }
  }

  /** {@inheritDoc} */
  @Override
  public void retirar(Cuenta cuenta, double monto) {
    System.out.println("La cuenta no tiene saldo suficiente para realizar retiros.");
  }

  /** {@inheritDoc} */
  @Override
  public void calculaInteres(Cuenta cuenta, StrategyInteres estrategia) {
    System.out.println("No se genera interés mientras la cuenta esté sobregirada.");
  }
}

