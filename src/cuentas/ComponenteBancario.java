package cuentas;

/**
 * Interfaz Componente para el patron Composite.
 * Define las operaciones comunes para Cuentas (hojas) y Portafolios (composites).
 * @author LasPamparas
 * @version 1.0
 */
public interface ComponenteBancario {

  /**
   * Obtiene el saldo del componente.
   * Para una cuenta, es su saldo.
   * Para un portafolio, es la suma de los saldos de sus componentes.
   * @return el saldo.
   */
  double getSaldo();

  /**
   * Deposita un monto en el componente.
   * @param monto el monto a depositar.
   */
  void depositar(double monto);

  /**
   * Retira un monto del componente.
   * @param monto el monto a retirar.
   */
  void retirar(double monto);

  /**
   * Obtiene el nombre del cliente o del portafolio.
   * @return el nombre identificador.
   */
  String getCliente();

  /**
   * Devuelve una descripcion del componente.
   * @return la descripcion.
   */
  String desc();
}