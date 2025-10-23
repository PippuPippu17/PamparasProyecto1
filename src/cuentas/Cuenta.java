package cuentas;

import intereses.StrategyInteres;

/**
 * Representa una cuenta bancaria que utiliza los patrones
 * State y <b>Strategy para el estado y calcular intereses 
 */
public class Cuenta {

  private String cliente;
  private double saldo;
  private EstadoCuenta estado;
  private StrategyInteres interes;
  private String nip;

  /**
   * Constructor de cuenta bancaria.
   * 
   * @param cliente propietario de la cuenta
   * @param saldoInicial monto inicial 
   * @param estrategia estrategia para el calculo de intereses
   * @param estado estado de la cuenta
   * @param nip NIP de acceso
   */
  public Cuenta(String cliente, double saldoInicial, StrategyInteres estrategia, EstadoCuenta estado, String nip) {
    this.cliente = cliente;
    this.saldo = saldoInicial;
    this.interes = estrategia;
    this.estado = estado;
    this.nip = nip;
  }

  /**
   * Retira un monto de la cuenta dependiendo del estado  
   * @param monto cantidad a retirar
   */
  public void retirar(double monto) {
    estado.retirar(this, monto);
  }

  /**
   * Deposita un monto a la cuenta dependiendo del estado 
   * @param monto cantidad a depositar
   */
  public void depositar(double monto) {
    estado.depositar(this, monto);
  }

  /**
   * Aplica strategia de interes definida para la cuenta.
   */
  public void aplicarInteres() {
    estado.calculaInteres(this, interes);
  }

  
  public String getCliente() { return cliente; }

  /** @return saldo actual. */
  public double getSaldo() { return saldo; }

  /** @param saldo nuevo saldo */
  public void setSaldo(double saldo) { this.saldo = saldo; }

  /** @return estado de la cuenta. */
  public EstadoCuenta getEstado() { return estado; }

  /** @param estado nuevo estado de la cuenta. */
  public void setEstado(EstadoCuenta estado) { this.estado = estado; }

  /** @return estrategia de calculo de interes. */
  public StrategyInteres getEstrategiaInteres() { return interes; }

  /** @param interes nueva estrategia de interes. */
  public void setEstrategiaInteres(StrategyInteres interes) { this.interes = interes; }

  /** @return NIP de acceso. */
  public String getNIP() { return nip; }

  /** @param nip nuevo NIP. */
  public void setNIP(String nip) { this.nip = nip; }

  /**
   * Devuelve descripcion de la cuenta.
   * 
   * @return informacion (cliente, saldo y estado).
   */
  public String desc() {
    return "Cuenta de " + cliente +
      " ,  Saldo disponible: $" + saldo +
      " , Estado de la cuenta: " + estado;
  }
}

