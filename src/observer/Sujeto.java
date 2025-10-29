package observer;

/**
 * Interfaz Sujeto del patron Observer.
 * Define metodos para registrar, eliminar y notificar observadores.
 * @author LasPamparas
 * @version 1.0
 */
public interface Sujeto {

  /**
   * Registra un nuevo observador.
   * @param obs El observador a agregar.
   */
  void agregarObservador(Observador obs);

  /**
   * Elimina un observador.
   * @param obs El observador a eliminar.
   */
  void eliminarObservador(Observador obs);

  /**
   * Notifica a todos los observadores registrados.
   * @param mensaje El mensaje con el evento a notificar.
   */
  void notificarObservadores(String mensaje);
}