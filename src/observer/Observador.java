package observer;

/**
 * Interfaz del observador en el patron Observer.
  */
public interface Observador {

  /**
   * Metodo que recibe las notificaciones del sujeto observado
   * @param mensaje Mensaje descriptivo del evento ocurrido.
   */
  void actualizar(String mensaje);
}

