package serviciosextra;

/**
 * Interfaz base del patron Decorator.
 * Representa un servicio adicional o una cuenta decorable.
 * @author LasPamparas
 * @version 1.0
 */
public interface ServAdicional {
  /**
   * Devuelve la descripcion del servicio.
   * @return la descripcion del servicio.
   */
  String getDesc();

  /**
   * Devuelve el costo del servicio.
   * @return el costo del servicio.
   */
  double getCosto();
}