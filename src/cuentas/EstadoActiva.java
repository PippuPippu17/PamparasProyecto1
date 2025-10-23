package cuentas;

import intereses.StrategyInteres;

/**
 * Representa el estado "Activa" de una cuenta
 * 
 * Prmite depositos, retiros e intereses.
 */
public class EstadoActiva implements EstadoCuenta {

  /** {@inheritDoc} */
  @Override
  public String getEstado() {
    return "Activa";
  }

  /** {@inheritDoc} */
  @Override
  public void depositar(Cuenta cuenta, double monto) {
    cuenta.setSaldo(cuenta.getSaldo() + monto);

    if (monto > 100000) {
      cuenta.setEstado(new EstadoCongelada());
      System.out.println("Cuenta congelada por actividad sospechosa (depósito mayor a $100,000).");
    } else {
      System.out.println("Depósito realizado. Saldo total: $" + cuenta.getSaldo());
    }
  }

  /** {@inheritDoc} */
  @Override
  public void retirar(Cuenta cuenta, double monto) {
    if (monto > cuenta.getSaldo()) {
      System.out.println("Saldo insuficiente. La cuenta será sobregirada.");
      cuenta.setEstado(new EstadoSobregirada());
      cuenta.getEstado().retirar(cuenta, monto);
      cuenta.setSaldo(cuenta.getSaldo() - monto);
      System.out.println("Saldo resultante: $" + cuenta.getSaldo());
    } else {
      cuenta.setSaldo(cuenta.getSaldo() - monto);
      System.out.println("Saldo restante: $" + cuenta.getSaldo());
    }
  }

  /** {@inheritDoc} */
  @Override
  public void calculaInteres(Cuenta cuenta, StrategyInteres estrategia) {
    double interes = estrategia.calInteres(cuenta.getSaldo());
    cuenta.setSaldo(cuenta.getSaldo() + interes);
    System.out.println("Monto de interés generado: $" + interes);
  }
}

