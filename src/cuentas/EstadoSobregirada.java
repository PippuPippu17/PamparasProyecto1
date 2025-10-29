package cuentas;

import intereses.StrategyInteres;

/**
 * Representa el estado "Sobregirada" de una cuenta.
 * En este estado, se aplican cargos y restricciones hasta tener saldo positivo.
 * @author LasPamparas
 * @version 1.0
 */
public class EstadoSobregirada implements EstadoCuenta {

  /**
   * {@inheritDoc}
   * Devuelve el nombre del estado actual: "Sobregirada".
   */
  @Override
  public String getEstado() {
    return "Sobregirada";
  }

  /**
   * {@inheritDoc}
   * Realiza un deposito en la cuenta, aplicando un cargo por sobregiro.
   * Si el saldo resultante es positivo, la cuenta vuelve al estado "Activa".
   * Detecta actividad sospechosa y congela si es necesario.
   */
  @Override
  public void depositar(Cuenta cuenta, double monto) {
    // Detectar fraude antes de procesar
    DetectorFraude detector = cuenta.getDetectorFraude();
    double saldoAnterior = cuenta.getSaldo();

    if (detector.esDepositoSospechoso(monto, saldoAnterior)) {
      cuenta.setEstado(new EstadoCongelada());
      System.out.println("ALERTA DE SEGURIDAD: Cuenta congelada por actividad sospechosa");
      System.out.println("Razon: " + detector.obtenerDescripcionFraude());
      System.out.println("Contacte al banco para verificar esta operacion.");
      return; // No procesar el deposito
    }

    // Si no es sospechoso, procesar normalmente
    double cargo = 50.0;
    System.out.println("Cargo por sobregiro de cuenta: $" + cargo);
    cuenta.setSaldo(cuenta.getSaldo() - cargo + monto);

    if (cuenta.getSaldo() >= 0) {
      cuenta.setEstado(new EstadoActiva());
      System.out.println("La cuenta vuelve a estar activa. Saldo actual: $" + cuenta.getSaldo());
    } else {
      System.out.println("Cuenta sigue sobregirada. Saldo actual: $" + cuenta.getSaldo());
    }
  }

  /**
   * {@inheritDoc}
   * No permite retiros, ya que la cuenta está sobregirada.
   */
  @Override
  public void retirar(Cuenta cuenta, double monto) {
    System.out.println("La cuenta no tiene saldo suficiente para realizar retiros.");
  }

  /**
   * {@inheritDoc}
   * No calcula intereses, ya que la cuenta está sobregirada.
   */
  @Override
  public void calculaInteres(Cuenta cuenta, StrategyInteres estrategia) {
    System.out.println("No se genera interes mientras la cuenta esté sobregirada.");
  }

  /**
   * {@inheritDoc}
   * No calcula intereses, ya que la cuenta está sobregirada.
   */
  @Override
  public void calculaInteres(Cuenta cuenta, StrategyInteres estrategia, int mesActual, double saldoPromedio) {
    System.out.println("No se genera interes mientras la cuenta esté sobregirada.");
  }
}