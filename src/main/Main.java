package main;

import intereses.*;
import cuentas.*;
import java.util.List;
import java.util.ArrayList;

import java.util.Scanner;

/**
 * Clase main con menu interactivo de PumaBank.
 */
public class Main {

  public static void main(String[] args) {
    Scanner uwu = new Scanner(System.in);
    int opcionPrincipal;
    boolean salir = false;

    // Lista dinamica para almacenar las cuentas
    List<Cuenta> cuentas = new ArrayList<>();
    
    cuentas.add(new Cuenta("LUIS", 3000.0, new InteresMensual(), new EstadoActiva(), "1212"));
    cuentas.add(new Cuenta("JULIO", 200, new InteresMensual(), new cuentas.EstadoCerrada(), "7777"));
    while (!salir) {
      System.out.println("  ---Bienvenido a PUMABANK---");
      System.out.print("Seleccione una opcion: ");
      System.out.println("\n1. Crear cuenta");
      System.out.println("2. Ingresar PIN");
      System.out.println("0. Salir");

      opcionPrincipal = uwu.nextInt();
      uwu.nextLine(); // limpiar buffer

      switch (opcionPrincipal) {
      case 1:
        System.out.println("\nCreacion de cuenta");
        break;

      case 2:
        System.out.print("\nIngrese su PIN: ");
        String pin = uwu.nextLine();

        Cuenta cuentaActual = null;
        for (Cuenta c : cuentas) {
          if (c.getNIP().equals(pin)) {
            cuentaActual = c;
            break;
          }
        }
        if (cuentaActual != null) {
          mostrarMenuCuenta(uwu, cuentaActual);
        } else {
          System.out.println("PIN invalido. Regresando al menu principal...\n");
        }
      case 0:
        System.out.println("Hasta pronto!");
        salir = true;
        break;

      default:
        System.out.println("Opcion no valida. Intente nuevamente.\n");
      }
    }
    uwu.close();
  }



  /**
   * Menu secundario tras ingresar PIN correcto.
   */
  private static void mostrarMenuCuenta(Scanner uwu, Cuenta cuenta) {
    int opcionCuenta;
    boolean salirCuenta = false;

    while (!salirCuenta) {
      if (cuenta.getEstado().getEstado().equals("Cerrada")){
        System.out.println("Deseas reactivar la cuenta? SI/NO");
        String activacion = uwu.nextLine();
        if (activacion.equalsIgnoreCase("SI")){
          cuenta.setEstado(new EstadoActiva());
        } 
      }
    

      if (cuenta.getEstado().getEstado().equals("Activa")) {
        System.out.println("\n---MENU DE CUENTA DE " + cuenta.getCliente() + "---");
        System.out.println("1. Retirar");
        System.out.println("2. Depositar");
        System.out.println("3. Contratar servicio adicional");
        System.out.println("4. Obtener informacion de cuenta");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opcion: ");

        opcionCuenta = uwu.nextInt();
        uwu.nextLine(); // limpiar buffer

        switch (opcionCuenta) {
          case 1:
            System.out.println("Saldo actual: $" + cuenta.getSaldo());
            System.out.println("Cuanto dinero deseas retirar");
            double retiro = uwu.nextDouble();
            cuenta.retirar(retiro);
            break;
          case 2:
            System.out.println("Saldo actual: $" + cuenta.getSaldo());
            System.out.print("Cuanto dinero deseas depositar: ");
            double deposito = uwu.nextDouble();
            uwu.nextLine();
            cuenta.depositar(deposito);
            break;
          case 3:
            System.out.println("[Opcion: Contratar servicio adicional]");
            break;
          case 4:
            System.out.println(cuenta.desc());
            break;
          case 0:
            System.out.println("Saliendo del menu de cuenta...\n");
            salirCuenta = true;
            break;
          default:
            System.out.println("Opcion no valida. Intente nuevamente.\n");
  
        }
      
      } else {
        System.out.println("La cuenta esta cerrada, no se puede acceder al menu.");

      }
    }
  }
}
