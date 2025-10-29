package observer;

/**
 * Representa a un cliente que recibe notificaciones.
 * Implementa la interfaz Observador para ser notificado de los cambios.
 * @author LasPamparas
 * @version 1.0
 */
public class ClienteObservador implements Observador {

  private String nombre;

  /**
   * Crea un cliente observador con el nombre especificado.
   * @param nombre Nombre del cliente que observará los eventos.
   */
  public ClienteObservador(String nombre) {
    this.nombre = nombre;
  }

  /**
   * {@inheritDoc}
   * Muestra un mensaje de notificacion para el cliente.
   */
  @Override
  public void actualizar(String mensaje) {
    System.out.println("Notificacion para " + nombre + ": " + mensaje);

  }
}