package excepciones;

/**
 * Excepcion personalizada para entradas invalidas (unchecked).
 * @author LasPamparas
 * @version 1.0
 */
public class EntradaInvalida extends RuntimeException {
  /**
   * Constructor de la excepcion.
   * @param mensaje el mensaje de error.
   */
  public EntradaInvalida(String mensaje) {
    super(mensaje);
  }
}