package observer;

/**
 * Interfaz del observador en el patron Observer.
 * @author LasPamparas
 * @version 1.0
 */
public interface Observador {

  /**
   * Metodo que recibe las notificaciones del sujeto observado.
   * @param mensaje Mensaje descriptivo del evento ocurrido.
   */
  void actualizar(String mensaje);
}