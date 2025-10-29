package cuentas;

import intereses.StrategyInteres;
import excepciones.EntradaInvalida;

/**
 * Representa el estado "Activa" de una cuenta.
 * Permite depositos, retiros y calculo de intereses.
 * @author LasPamparas
 * @version 1.0
 */
public class EstadoActiva implements EstadoCuenta {

  /**
   * {@inheritDoc}
   * Devuelve el nombre del estado actual: "Activa".
   */
  @Override
  public String getEstado() {
    return "Activa";
  }

  /**
   * {@inheritDoc}
   * Realiza un deposito en la cuenta. Detecta actividad sospechosa y congela si es necesario.
   */
  @Override
  public void depositar(Cuenta cuenta, double monto) {
    if (monto <= 0) {
      throw new EntradaInvalida("Monto invalido");
    }

    double saldoAnterior = cuenta.getSaldo();
    cuenta.setSaldo(cuenta.getSaldo() + monto);

    // Detectar fraude usando el sistema especializado
    DetectorFraude detector = cuenta.getDetectorFraude();
    if (detector.esDepositoSospechoso(monto, saldoAnterior)) {
      cuenta.setEstado(new EstadoCongelada());
      System.out.println("ALERTA DE SEGURIDAD: Cuenta congelada por actividad sospechosa");
      System.out.println("Razon: " + detector.obtenerDescripcionFraude());
      System.out.println("Contacte al banco para verificar esta operacion.");
    } else {
      System.out.println("Deposito realizado correctamente. Saldo actual: $" + cuenta.getSaldo());
    }
  }

  /**
   * {@inheritDoc}
   * Realiza un retiro de la cuenta. Detecta actividad sospechosa y puede congelar la cuenta.
   */
  @Override
  public void retirar(Cuenta cuenta, double monto) {
    if (monto <= 0) {
      throw new EntradaInvalida("Monto invalido");
    }

    // Detectar fraude ANTES de procesar el retiro
    DetectorFraude detector = cuenta.getDetectorFraude();
    if (detector.esRetiroSospechoso(monto, cuenta.getSaldo())) {
      cuenta.setEstado(new EstadoCongelada());
      System.out.println("ALERTA DE SEGURIDAD: Cuenta congelada por actividad sospechosa");
      System.out.println("Razon: " + detector.obtenerDescripcionFraude());
      System.out.println("La operacion ha sido bloqueada. Contacte al banco.");
      return; // No procesar el retiro
    }

    // Si no es sospechoso, procesar normalmente
    if (monto > cuenta.getSaldo()) {
      System.out.println("Saldo insuficiente. La cuenta será sobregirada.");
      cuenta.setEstado(new EstadoSobregirada());
      cuenta.setSaldo(cuenta.getSaldo() - monto);
      System.out.println("Saldo resultante: $" + cuenta.getSaldo());
    } else {
      cuenta.setSaldo(cuenta.getSaldo() - monto);
      System.out.println("Retiro exitoso. Saldo restante: $" + cuenta.getSaldo());
    }
  }

  /**
   * {@inheritDoc}
   * Calcula y aplica los intereses a la cuenta.
   */
  @Override
  public void calculaInteres(Cuenta cuenta, StrategyInteres estrategia) {
    double interes = estrategia.calInteres(cuenta.getSaldo());
    cuenta.setSaldo(cuenta.getSaldo() + interes);
    System.out.println("Monto de interes generado: $" + interes);
  }

  /**
   * {@inheritDoc}
   * Calcula y aplica los intereses a la cuenta, considerando el mes actual y el saldo promedio.
   */
  @Override
  public void calculaInteres(Cuenta cuenta, StrategyInteres estrategia, int mesActual, double saldoPromedio) {
    double interes = estrategia.calInteres(cuenta.getSaldo(), mesActual, saldoPromedio);
    cuenta.setSaldo(cuenta.getSaldo() + interes);
    System.out.println("Monto de interes generado (mes " + mesActual + "): $" + interes);
  }
}