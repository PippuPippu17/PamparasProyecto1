package cuentas;

import java.util.List;
import java.util.ArrayList;

/**
 * Representa un Portafolio de cuentas que actúa como un Composite en el patron de diseño.
 * Puede contener tanto Cuentas individuales (hojas) como otros Portafolios (composites).
 * @author LasPamparas
 * @version 1.0
 */
public class Portafolio implements ComponenteBancario {

  private String nombrePortafolio;
  private List<ComponenteBancario> componentes = new ArrayList<>();

  /**
   * Constructor del portafolio.
   * @param nombrePortafolio El nombre para identificar este portafolio.
   */
  public Portafolio(String nombrePortafolio) {
    this.nombrePortafolio = nombrePortafolio;
  }

  /**
   * Agrega un componente bancario (puede ser una Cuenta u otro Portafolio).
   * @param componente El componente a agregar.
   */
  public void agregar(ComponenteBancario componente) {
    componentes.add(componente);
  }

  /**
   * Remueve un componente bancario.
   * @param componente El componente a remover.
   */
  public void remover(ComponenteBancario componente) {
    componentes.remove(componente);
  }

  /**
   * Devuelve el nombre del portafolio.
   * @return El nombre del portafolio.
   */
  @Override
  public String getCliente() {
    return this.nombrePortafolio;
  }

  /**
   * Devuelve el saldo total del portafolio, sumando los saldos de todos sus componentes.
   * @return El saldo total del portafolio.
   */
  @Override
  public double getSaldo() {
    double saldoTotal = 0;
    for (ComponenteBancario comp : componentes) {
      saldoTotal += comp.getSaldo();
    }
    return saldoTotal;
  }

  /**
   * Deposita un monto en cada uno de los componentes del portafolio.
   * @param monto El monto a depositar.
   */
  @Override
  public void depositar(double monto) {
    System.out.println("--- Depositando " + monto + " en cada componente del portafolio: " + nombrePortafolio + " ---");
    for (ComponenteBancario comp : componentes) {
      comp.depositar(monto);
    }
  }

  /**
   * Retira un monto de cada uno de los componentes del portafolio.
   * @param monto El monto a retirar.
   */
  @Override
  public void retirar(double monto) {
    System.out.println("--- Retirando " + monto + " de cada componente del portafolio: " + nombrePortafolio + " ---");
    for (ComponenteBancario comp : componentes) {
      comp.retirar(monto);
    }
  }

  /**
   * Devuelve una descripcion detallada del portafolio y sus componentes.
   * @return Una cadena con la descripcion del portafolio.
   */
  @Override
  public String desc() {
    StringBuilder sb = new StringBuilder();
    sb.append("\n========== Portafolio: ").append(nombrePortafolio).append(" ==========\n");
    for (ComponenteBancario comp : componentes) {
      sb.append("  -> ").append(comp.desc()).append("\n");
    }
    sb.append("Saldo Total del Portafolio '" + nombrePortafolio + "': $").append(getSaldo());
    sb.append("\n==============================================\n");
    return sb.toString();
  }

  /**
   * Devuelve la lista de componentes para operaciones especificas del cliente.
   * @return La lista de componentes bancarios.
   */
  public List<ComponenteBancario> getComponentes() {
    return componentes;
  }
}
