package observer;

import java.util.ArrayList;
import java.util.List;
import cuentas.Cuenta;

/**
 * Gestiona alertas y notifica  a los observadores
 * Sobre eventos relacionados con la cuenta.
 */
public class GestorAlertas implements Sujeto {

  private List<Observador> observadores;
  private Cuenta cuenta;

  /**
   * Crea un gestor de alertas 
   * @param cuenta Cuenta que será monitoreada.
   */
  public GestorAlertas(Cuenta cuenta) {
    this.cuenta = cuenta;
    this.observadores = new ArrayList<>();
  }


  /** {@inheritDoc} */
  @Override
  public void agregarObservador(Observador obs) {
    observadores.add(obs);
  }

  /** {@inheritDoc} */
  @Override
  public void eliminarObservador(Observador obs) {
    observadores.remove(obs);
  }

  /** {@inheritDoc} */
  @Override
  public void notificarObservadores(String mensaje) {
    for (Observador obs : observadores) {
      obs.actualizar(mensaje);
    }
  }

  /**
   * Genera una alerta y notifica a los observadores
   * @param evento Descripcion de evento 
   */
  public void generarAlerta(String evento) {
    String mensaje = "Cuenta de " + cuenta.getCliente() + ": " + evento;
    notificarObservadores(mensaje);
  }
}

