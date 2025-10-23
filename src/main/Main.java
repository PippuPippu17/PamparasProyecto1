package main;

import intereses.*;
import cuentas.*;
import fabrica.*;
import serviciosextra.*;
import observer.*;
import archivo.*;
import excepciones.*;

import java.util.List;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase principal del sistema PumaBank.
 * Contiene los menús y opciones interactivas para el usuario,
 * con validaciones y manejo de excepciones personalizadas.
 */
public class Main {

  public static void main(String[] args) {
    Scanner uwu = new Scanner(System.in);
    int opcionPrincipal;
    boolean salir = false;

    List<Cuenta> cuentas = new ArrayList<>();
    Portafolio portafolio = new Portafolio();

    // Cuentas de prueba 
    cuentas.add(new Cuenta("LUIS", 3000.0, new InteresMensual(), new EstadoActiva(), "1212"));
    cuentas.add(new Cuenta("JULIO", 200.0, new InteresMensual(), new EstadoCerrada(), "7777"));

    System.out.println("\n--- Bienvenido a PUMABANK ---");

    while (!salir) {
      try {
        System.out.println("\nMENU PRINCIPAL");
        System.out.println("1. Crear cuenta");
        System.out.println("2. Ingresar NIP");
        System.out.println("3. Ver Portafolio");
        System.out.println("4. Exportar Portafolio");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opcion: ");

        opcionPrincipal = Integer.parseInt(uwu.nextLine().trim());

        switch (opcionPrincipal) {
          case 1 : {
            Cuenta nuevaCuenta = FabricaCuentas.crearCuenta();
            if (nuevaCuenta != null) {
              cuentas.add(nuevaCuenta);
              portafolio.agregarCuenta(nuevaCuenta);
              System.out.println("Cuenta creada exitosamente.");
            } else {
              System.out.println("Error al crear la cuenta.");
            }
            break;
          }

          case 2 : {
            System.out.print("\nIngrese su NIP: ");
            String pin = uwu.nextLine();

            Cuenta cuentaActual = buscarCuentaPorNIP(cuentas, pin);
            if (cuentaActual != null) {
              mostrarMenuCuenta(uwu, cuentaActual, portafolio);
            } else {
              throw new EntradaInvalida("NIP inválido. Intente nuevamente.");
            }
            break;
          }

          case 3 : {
            System.out.println("\n--- PORTAFOLIO ---");
            portafolio.mostrarCuentas();
            System.out.println("Saldo total: $" + portafolio.sumaSaldo());
            break;
          }

          case 4 : {
            GeneradorTXT.exportarPortafolio(portafolio, "portafolio.txt");
            System.out.println(" Portafolio exportado correctamente.");
            break;
          }

          case 0 : {
            System.out.println("👋 ¡Gracias por usar PUMABANK!");
            salir = true;
          }

          default : throw new EntradaInvalida("Opción fuera del rango del menú.");
        }

      } catch (EntradaInvalida e) {
        System.out.println("⚠️ Error: " + e.getMessage());
      } catch (NumberFormatException | InputMismatchException e) {
        System.out.println("⚠️ Entrada inválida. Por favor ingrese un número válido.");
        uwu.nextLine();
      } catch (Exception e) {
        System.out.println("⚠️ Error inesperado: " + e.getMessage());
      }
    }

    uwu.close();
  }

  private static Cuenta buscarCuentaPorNIP(List<Cuenta> cuentas, String pin) {
    for (Cuenta c : cuentas) {
      if (c.getNIP().equals(pin)) {
        return c;
      }
    }
    return null;
  }

  private static void mostrarMenuCuenta(Scanner uwu, Cuenta cuenta, Portafolio portafolio) {
    GestorAlertas gestor = new GestorAlertas(cuenta);
    ClienteObservador clienteObs = new ClienteObservador(cuenta.getCliente());
    gestor.agregarObservador(clienteObs);

    boolean salirCuenta = false;

    while (!salirCuenta) {
      try {
        System.out.println("\n--- MENU DE CUENTA: " + cuenta.getCliente() + " ---");
        System.out.println("1. Consultar saldo");
        System.out.println("2. Retirar");
        System.out.println("3. Depositar");
        System.out.println("4. Agregar al Portafolio");
        System.out.println("5. Exportar información a TXT");
        System.out.println("6. Contratar servicios");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");

        int opcion = Integer.parseInt(uwu.nextLine().trim());

        switch (opcion) {
          case 1 : 
            System.out.println("Saldo actual: $" + cuenta.getSaldo());
            break;

          case 2 : {
            System.out.println("Saldo actual: $" + cuenta.getSaldo());
            System.out.print("Monto a retirar: ");
            double retiro = Double.parseDouble(uwu.nextLine().trim());
            if (retiro <= 0) throw new EntradaInvalida("El monto debe ser mayor que 0.");
            cuenta.retirar(retiro);
            if (retiro > cuenta.getSaldo()) {
            gestor.generarAlerta("Cuenta sobregirada por retiro de $" + retiro);
             }
            break;
          }

          case 3 : {
            System.out.println("Saldo actual: $" + cuenta.getSaldo());
            System.out.print("Monto a depositar: ");
            double deposito = Double.parseDouble(uwu.nextLine().trim());
            if (deposito <= 0) throw new EntradaInvalida("El monto debe ser positivo.");
            cuenta.depositar(deposito);
            if (deposito > 100000) {
              gestor.generarAlerta("Deposito mayor a $100,000 .");
            }
            break;
          }

          case 4 : {
            portafolio.agregarCuenta(cuenta);
            System.out.println(" Cuenta agregada al portafolio.");
            break;
          }

          case 5 : {
            GeneradorTXT.exportarCuenta(cuenta, cuenta.getCliente() + "_info.txt");  
            System.out.println(" Informacion exportada a archivo.");
            break;
          }
          case 6:
            if (cuenta.getEstado().getEstado().equals("Activa")) {
              ServAdicional servicio = new serviciosextra.CuentaBase(cuenta);
              boolean menuServicios = true;

              while (menuServicios) {
                System.out.println("\n---SERVICIOS ADICIONALES---");
                System.out.println("1. Seguro Antifraude (+$1500)");
                System.out.println("2. Programa de Recompensas (+$0)");
                System.out.println("3. Alertas Premium (+$600)");
                System.out.println("4. Ver servicios contradaos.");
                System.out.println("0. Salir");

                int serv = uwu.nextInt();
                uwu.nextLine();
                
                switch (serv) {
                  case 1:
                    servicio = new serviciosextra.SeguroAntifraude(servicio, cuenta);
                    double precio = cuenta.getSaldo() - 1500;
                    cuenta.setSaldo(precio);
                    System.out.println("Seguro Antifraude contratado.");
                    break;
                  case 2:
                    servicio = new serviciosextra.Recompensas(servicio, cuenta);
                    double precios = cuenta.getSaldo() - 0;
                    cuenta.setSaldo(precios);
                    System.out.println("Programa de Recompensas contratado.");
                    break;
                  case 3:
                    servicio = new serviciosextra.AlertasPremium(servicio, cuenta);
                     double precioss = cuenta.getSaldo() - 600;
                    cuenta.setSaldo(precioss);
                    System.out.println("Alertas Premium contratadas.");
                    break;
                  case 4:
                    System.out.println("Descripciones: " + servicio.getDesc());
                    System.out.println("Costo total mensual: $" + servicio.getCosto());
                    break;
                  case 0:
                    menuServicios = false;
                    break;
                  default:
                    System.out.println("Opción no válida.");
                }
              } 
            } else {
              System.out.println("No puedes contratar servicios si tu cuenta no está activa.");
            }
            break;

          case 0 : salirCuenta = true;
            break;

          default : throw new EntradaInvalida("Opcion fuera del rango del menu.");
            
        }

      } catch (EntradaInvalida e) {
        System.out.println("⚠️ " + e.getMessage());
      } catch (NumberFormatException e) {
        System.out.println("⚠️ Ingrese un numero valido.");
      } catch (Exception e) {
        System.out.println("⚠️ Error inesperado: " + e.getMessage());
      }
    }
  }
}

