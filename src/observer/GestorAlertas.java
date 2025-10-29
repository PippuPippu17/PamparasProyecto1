package observer;

import java.util.ArrayList;
import java.util.List;
import cuentas.Cuenta;

/**
 * Gestiona alertas y notifica a los observadores sobre eventos relacionados con la cuenta.
 * Implementa la interfaz Sujeto para el patron Observer.
 * @author LasPamparas
 * @version 1.0
 */
public class GestorAlertas implements Sujeto {

  private List<Observador> observadores;
  private Cuenta cuenta;

  /**
   * Crea un gestor de alertas para una cuenta especifica.
   * @param cuenta La cuenta que será monitoreada.
   */
  public GestorAlertas(Cuenta cuenta) {
    this.cuenta = cuenta;
    this.observadores = new ArrayList<>();
  }


  /**
   * {@inheritDoc}
   * Agrega un observador a la lista de observadores.
   */
  @Override
  public void agregarObservador(Observador obs) {
    observadores.add(obs);
  }

  /**
   * {@inheritDoc}
   * Elimina un observador de la lista de observadores.
   */
  @Override
  public void eliminarObservador(Observador obs) {
    observadores.remove(obs);
  }

  /**
   * {@inheritDoc}
   * Notifica a todos los observadores con un mensaje.
   */
  @Override
  public void notificarObservadores(String mensaje) {
    for (Observador obs : observadores) {
      obs.actualizar(mensaje);
    }
  }

  /**
   * Genera una alerta y notifica a los observadores.
   * @param evento La descripcion del evento que genera la alerta.
   */
  public void generarAlerta(String evento) {
    String mensaje = "Cuenta de " + cuenta.getCliente() + ": " + evento;
    notificarObservadores(mensaje);
  }
}