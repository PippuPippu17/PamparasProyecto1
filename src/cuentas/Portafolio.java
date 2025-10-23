package cuentas;

import java.util.List;
import java.util.ArrayList;

/**
 * Representa Portafolio de cuentas
 *  
 * Administrar varias cuentas, consultarlas, eliminarlas y calcular saldo total
 */
public class Portafolio {

  private List<Cuenta> cuentas;

  public Portafolio() {
    cuentas = new ArrayList<>();
  }

  /**
   * Agrega cuenta al portafolio.
   * 
   * @param cuenta cuenta a agregar
   */
  public void agregarCuenta(Cuenta cuenta) {
    cuentas.add(cuenta);
  }

  /**
   * Elimina cuenta del portafolio 
   * @param cuenta cuenta a eliminar
   */
  public void eliminarCuenta(Cuenta cuenta) {
    cuentas.remove(cuenta);
  }

  /**
   * Busca una cuenta en el portafolio por nombre  
   * @param nombreCliente nombre del cliente
   * @return cuenta o null si no existe
   */
  public Cuenta buscarCuentaPorNombre(String nombreCliente) {
    for (Cuenta c : cuentas) {
      if (c.getCliente().equalsIgnoreCase(nombreCliente)) {
        return c;
      }
    }
    return null;
  }

  /**
   * Muestra la info de todas las cuentas del portafolio
   */
  public void mostrarCuentas() {
    System.out.println("--- Portafolio ---");
    for (Cuenta c : cuentas) {
      System.out.println(c.desc());
    }
  }

  /**
   * Suma los saldos de TODAS las cuentas del portafolio
   * 
   *@return cadena con saldo total
   */
  public String sumaSaldo() {
    double total = 0.0;
    for (Cuenta c : cuentas) {
      total += c.getSaldo();
    }
    return "Saldo total del portafolio: $" + total;
  }

  /**
   * Devuelve la lista completa de cuentas en el portafolio
   * 
   * @return lista de cuentas
   */
  public List<Cuenta> getCuentas() {
    return cuentas;
  }
}

