package cuentas;

import intereses.StrategyInteres;

/**
 * Estado Activa.
 * Permite realizar compras, depositos y generar intereses con normalidad
 */
public class EstadoActiva implements EstadoCuenta {


  @Override
  public String getEstado() {
    return "Activa";
  }
  
  @Override
  public void depositar(Cuenta cuenta, double monto) {
    cuenta.setSaldo(cuenta.getSaldo() + monto);
    
    //Esta es la actividad sospechosa para pasar a estado congelada porque no se que poner
    if (monto > 100000){
      cuenta.setEstado(new EstadoCongelada());
      System.out.println("Cuenta congelada por actividad sospechosa (deposito mayor a $100,000).");
    } else {
    System.out.println("Se ha realizado el deposito. Saldo total: $" + cuenta.getSaldo());
    }
  }


  @Override
  public void retirar(Cuenta cuenta, double monto) {
    if (monto > cuenta.getSaldo()) {
      System.out.println("Saldo insuficiente. La cuenta sera sobregirada.");
      cuenta.setEstado(new EstadoSobregirada());
      cuenta.getEstado().retirar(cuenta, monto);
      cuenta.setSaldo(cuenta.getSaldo() - monto); 
      System.out.println("Saldo resultante: $" + cuenta.getSaldo());
    } else {
      cuenta.setSaldo(cuenta.getSaldo() - monto);
      System.out.println("Saldo restante: $" + cuenta.getSaldo());
    }
  }

  @Override
  public void calculaInteres(Cuenta cuenta, StrategyInteres estrategia) {
    double interes = estrategia.calInteres(cuenta.getSaldo());
    cuenta.setSaldo(cuenta.getSaldo() + interes);
    System.out.println("Monto de interes: $" + interes);
  }
}

