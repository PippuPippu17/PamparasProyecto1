package observer;

/**
 * Representa a un cliente que recibe notificaciones
 */
public class ClienteObservador implements Observador {

  private String nombre;

  /**
   * Crea un cliente observador con el nombre 
   * @param nombre Nombre del cliente que observará los eventos.
   */
  public ClienteObservador(String nombre) {
    this.nombre = nombre;
  }

  @Override
  public void actualizar(String mensaje) {
    System.out.println("Notificacion para " + nombre + ": " + mensaje);

  }
}

