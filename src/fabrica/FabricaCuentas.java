package fabrica;

import cuentas.*;
import intereses.*;
import java.util.Scanner;

/**
 * Clase para la creacion de cuenta bancaria
 */
public class FabricaCuentas {

  private static Scanner uwu = new Scanner(System.in);

  /**
   * Crea cuenta bancaria pidiendo datos al usuario.
   * @return una instancia de Cuenta 
   */
  public static Cuenta crearCuenta() {
    System.out.println("\n--- CREACIÓN DE CUENTA ---");

    System.out.print("Nombre de cliente: ");
    String cliente = uwu.nextLine();

    System.out.print("NIP de 4 dígitos: ");
    String nip = uwu.nextLine();

    System.out.print("Saldo inicial: ");
    double saldoInicial = uwu.nextDouble();
    uwu.nextLine(); // limpiar buffer
    
    // Selecciona la estrategia dependiendo el saldoInicial
    StrategyInteres estrategia;
 
    if (saldoInicial >= 1000 && saldoInicial < 50000) {
      estrategia = new InteresMensual();
    } else {
      estrategia = new InteresAnual();
    }

    EstadoCuenta estado = new EstadoActiva();
    Cuenta cuenta = new Cuenta(cliente, saldoInicial, estrategia, estado, nip);

    System.out.println("\nCuenta creada de " + cliente);
    return cuenta;
  }
}

