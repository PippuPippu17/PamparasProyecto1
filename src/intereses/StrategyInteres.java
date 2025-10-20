package intereses;

/**
 * Usa Patron Strategy para la interfaz para definir el tipo de interes de las cuentas.
 * (Mensual, ANual o premium)
 *
 */
public interface StrategyInteres {
  public String getNombre();
  public double calInteres(double saldo);
}

