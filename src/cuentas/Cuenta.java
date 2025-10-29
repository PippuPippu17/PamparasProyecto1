package cuentas;

import intereses.StrategyInteres;
import java.time.LocalDate;

/**
 * Representa una cuenta bancaria que utiliza los patrones State y Strategy para el estado y calcular intereses.
 * @author LasPamparas
 * @version 1.0
 */
public class Cuenta implements ComponenteBancario {

  private String cliente;
  private double saldo;
  private EstadoCuenta estado;
  private StrategyInteres interes;
  private String nip;
  private LocalDate fechaCreacion;
  private int mesesAntiguedad;
  private DetectorFraude detectorFraude;

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
    this.fechaCreacion = LocalDate.now();
    this.mesesAntiguedad = 0;
    this.detectorFraude = new DetectorFraude();
  }

  /**
   * Retira un monto de la cuenta dependiendo del estado.
   * @param monto cantidad a retirar
   */
  public void retirar(double monto) {
    estado.retirar(this, monto);
  }

  /**
   * Deposita un monto a la cuenta dependiendo del estado.
   * @param monto cantidad a depositar
   */
  public void depositar(double monto) {
    estado.depositar(this, monto);
  }

  /**
   * Aplica la estrategia de interes definida para la cuenta.
   */
  public void aplicarInteres() {
    estado.calculaInteres(this, interes);
  }

  /**
   * Devuelve el nombre del cliente.
   * @return el nombre del cliente.
   */
  public String getCliente() { return cliente; }

  /**
   * Devuelve el saldo actual.
   * @return el saldo actual.
   */
  public double getSaldo() { return saldo; }

  /**
   * Establece el saldo de la cuenta.
   * @param saldo el nuevo saldo.
   */
  public void setSaldo(double saldo) { this.saldo = saldo; }

  /**
   * Devuelve el estado de la cuenta.
   * @return el estado de la cuenta.
   */
  public EstadoCuenta getEstado() { return estado; }

  /**
   * Establece el estado de la cuenta.
   * @param estado el nuevo estado de la cuenta.
   */
  public void setEstado(EstadoCuenta estado) { this.estado = estado; }

  /**
   * Devuelve la estrategia de calculo de interes.
   * @return la estrategia de interes.
   */
  public StrategyInteres getEstrategiaInteres() { return interes; }

  /**
   * Establece la estrategia de interes.
   * @param interes la nueva estrategia de interes.
   */
  public void setEstrategiaInteres(StrategyInteres interes) { this.interes = interes; }

  /**
   * Devuelve el NIP de acceso.
   * @return el NIP de acceso.
   */
  public String getNIP() { return nip; }

  /**
   * Establece el NIP de acceso.
   * @param nip el nuevo NIP.
   */
  public void setNIP(String nip) { this.nip = nip; }

  /**
   * Devuelve la fecha de creacion de la cuenta.
   * @return la fecha de creacion.
   */
  public LocalDate getFechaCreacion() { return fechaCreacion; }

  /**
   * Devuelve los meses de antiguedad de la cuenta.
   * @return los meses de antiguedad.
   */
  public int getMesesAntiguedad() { return mesesAntiguedad; }

  /**
   * Establece los meses de antiguedad de la cuenta.
   * @param meses los nuevos meses de antiguedad.
   */
  public void setMesesAntiguedad(int meses) { this.mesesAntiguedad = meses; }

  /** @return detector de fraude de la cuenta */
  public DetectorFraude getDetectorFraude() { return detectorFraude; }

  /**
   * Incrementa la antiguedad de la cuenta en un mes.
   */
  public void incrementarAntiguedad() {
    this.mesesAntiguedad++;
  }

  /**
   * Reabre una cuenta cerrada conservando su antiguedad.
   * @return true si se reabrio exitosamente, false si no estaba cerrada.
   */
  public boolean reabrirCuenta() {
    if (estado instanceof EstadoCerrada) {
      estado = new EstadoActiva();
      System.out.println("Cuenta de " + cliente + " reabierta exitosamente.");
      System.out.println("Antiguedad conservada: " + mesesAntiguedad + " meses (desde " + fechaCreacion + ")");
      return true;
    } else {
      System.out.println("La cuenta no está cerrada. Estado actual: " + estado.getEstado());
      return false;
    }
  }

  /**
   * Cierra la cuenta pero conserva la antiguedad para posible reapertura.
   */
  public void cerrarCuenta() {
    estado = new EstadoCerrada();
    System.out.println("Cuenta de " + cliente + " cerrada.");
    System.out.println("La antiguedad de " + mesesAntiguedad + " meses se conservará si decide reabrirla.");
  }

  /**
   * Devuelve una descripcion de la cuenta.
   *
   * @return una cadena con la informacion de la cuenta (cliente, saldo, estado y antiguedad).
   */
  public String desc() {
    return "Cuenta de " + cliente +
      " ,  Saldo disponible: $" + saldo +
      " , Estado de la cuenta: " + estado.getEstado() +
      " , Antiguedad: " + mesesAntiguedad + " meses";
  }
}