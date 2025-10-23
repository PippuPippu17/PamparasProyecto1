package excepciones;

/**
 * Excepcion personalizada para entradas invalidas 
 */
public class EntradaInvalida extends Exception {
  public EntradaInvalida(String mensaje) {
    super(mensaje);
  }
}

