package observer;

/**
 * Interfaz Sujeto del patron Observer.
 * Metodos pra registrar, eliminar y notificar observadores.
 */
public interface Sujeto {

  /**
   * Registra un nuevo observador
   * @param obs Observador a agregar
   */
  void agregarObservador(Observador obs);

  /**
   * Elimina un observador 
   * @param obs Observador a eliminar
   */
  void eliminarObservador(Observador obs);

  /**
   * Notifica a todos los observadores registrados 
   * @param mensaje Mensaje con el evento a notificar
   */
  void notificarObservadores(String mensaje);
}

