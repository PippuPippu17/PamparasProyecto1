package cuentas;

import intereses.StrategyInteres;

/**
 * Clase de la cuenta bancaria 
 * (constructor y metodos como depositar, retirar o aplicar intereses)
 * Depende de los Patrones State y Strategy
 */
public class Cuenta {

  //Atributos de cuenta
  private String cliente;
  private double saldo;
  private EstadoCuenta estado;
  private StrategyInteres interes;
  private String nip;

  /**
   *Constructor de una cuenta.
   * @param cliente propietario de cuenta
   * @param saldoInicial Monto inicial de la cuenta
   * @param estrategia Estrategia de calculo de interes.
   * @param estado Muestra el estado de la cuenta
   * @param nip nip de acceso del cliente.
   */
  public Cuenta(String cliente, double saldoInicial, StrategyInteres estrategia, EstadoCuenta estado, String nip) {
    this.cliente = cliente;
    this.saldo = saldoInicial;
    this.interes = estrategia;
    this.estado = estado;
    this.nip = nip;
  }
  
  public void retirar(double monto) {
    estado.retirar(this, monto);
  }

  public void depositar(double monto) {
    estado.depositar(this, monto);
  }

  public void aplicarInteres() {
    estado.calculaInteres(this, interes);
  }

  // Getters, setters
  public String getCliente() { 
    return cliente; 
  }

  public double getSaldo() {
    return saldo; 
  }
  public void setSaldo(double saldo) { 
    this.saldo = saldo; 
  }

  public EstadoCuenta getEstado() { 
    return estado; 
  }
  public void setEstado(EstadoCuenta estado) { 
    this.estado = estado; 
  }

  public StrategyInteres getEstrategiaInteres() { 
    return interes; 
  }
  public void setEstrategiaInteres(StrategyInteres interes) { 
    this.interes = interes; 
  }

  public String getNIP(){
    return nip;
  }
  public void setNIP(String nip){
    this.nip = nip;
  }

  public String desc() {
    return "Cuenta de " + cliente +
    " ,  Saldo disponible: $" + saldo + 
    " , Estado de la cuenta: " + estado;
  }
}

