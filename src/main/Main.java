package main;

import intereses.*;
import cuentas.*;
import fabrica.*;
import serviciosextra.*;
import observer.*;
import archivo.*;
import excepciones.*;
import acceso.*;
import procesos.*;

import java.util.List;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase principal del sistema PumaBank.
 * Contiene los menus y opciones interactivas para el usuario,
 * con validaciones y manejo de excepciones personalizadas.
 * @author julio
 * @version 1.0
 * 
 */
public class Main {

  /**
   * Metodo principal que inicia la aplicacion PumaBank.
   * Presenta un menu interactivo para que los usuarios administren sus cuentas.
   * 
   * @param args Los argumentos de la linea de comandos (no se utilizan).
   */
  public static void main(String[] args) {
    Scanner uwu = new Scanner(System.in);
    int opcionPrincipal;
    boolean salir = false;

    List<Cuenta> cuentas = new ArrayList<>();
    // El Portafolio ahora requiere un nombre en su constructor.
    Portafolio portafolio = new Portafolio("Portafolio Principal");
    ProcesoMensual procesoMensual = new ProcesoMensual();

    // Cuentas de prueba 
    Cuenta cuentaLuis = new Cuenta("LUIS", 3000.0, new InteresMensual(), new EstadoActiva(), "2847");
    Cuenta cuentaJulio = new Cuenta("JULIO", 200.0, new InteresMensual(), new EstadoCerrada(), "9531");
    
    cuentas.add(cuentaLuis);
    cuentas.add(cuentaJulio);

    // Agregamos las cuentas al portafolio usando el nuevo metodo 'agregar'.
    portafolio.agregar(cuentaLuis);
    portafolio.agregar(cuentaJulio);
    
    // Agregar cuentas al proceso mensual
    procesoMensual.agregarCuenta(cuentaLuis);
    procesoMensual.agregarCuenta(cuentaJulio);

    System.out.println("\n--- Bienvenido a PUMABANK ---");

    while (!salir) {
      try {
        System.out.println("\nMENU PRINCIPAL");
        System.out.println("1. Crear cuenta");
        System.out.println("2. Ingresar NIP");
        System.out.println("3. Ver Portafolio");
        System.out.println("4. Exportar Portafolio");
        System.out.println("5. Ejecutar Proceso Mensual");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opcion: ");

        opcionPrincipal = Integer.parseInt(uwu.nextLine().trim());

        switch (opcionPrincipal) {
          case 1 : {
            Cuenta nuevaCuenta = FabricaCuentas.crearCuenta();
            if (nuevaCuenta != null) {
              cuentas.add(nuevaCuenta);
              // Usamos el nuevo metodo 'agregar'
              portafolio.agregar(nuevaCuenta);
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
              mostrarMenuCuenta(uwu, cuentaActual, portafolio, procesoMensual);
            } else {
              throw new EntradaInvalida("NIP invalido. Intente nuevamente.");
            }
            break;
          }

          case 3 : {
            // Usamos el nuevo metodo 'desc' que devuelve toda la informacion formateada.
            System.out.println(portafolio.desc());
            break;
          }

          case 4 : {
            GeneradorTXT.exportarPortafolio(portafolio, "portafolio.txt");
            System.out.println(" Portafolio exportado correctamente.");
            break;
          }

          case 5 : {
            System.out.println("\n--- EJECUTANDO PROCESO MENSUAL ---");
            procesoMensual.ejecutarProcesoMensual();
            System.out.println("Proceso mensual completado. Se han generado los reportes correspondientes.");
            break;
          }

          case 0 : {
            System.out.println("👋 ¡Gracias por usar PUMABANK!");
            salir = true;
          }

          default : throw new EntradaInvalida("Opcion fuera del rango del menu.");
        }

      } catch (EntradaInvalida e) {
        System.out.println("⚠️ Error: " + e.getMessage());
      } catch (NumberFormatException | InputMismatchException e) {
        System.out.println("⚠️ Entrada invalida. Por favor ingrese un numero valido.");
        uwu.nextLine();
      } catch (Exception e) {
        System.out.println("⚠️ Error inesperado: " + e.getMessage());
      }
    }

    uwu.close();
  }

  /**
   * Busca una cuenta en la lista de cuentas por su NIP.
   * 
   * @param cuentas La lista de cuentas en la que buscar.
   * @param pin El NIP de la cuenta a buscar.
   * @return La cuenta si se encuentra, o null si no.
   */
  private static Cuenta buscarCuentaPorNIP(List<Cuenta> cuentas, String pin) {
    for (Cuenta c : cuentas) {
      if (c.getNIP().equals(pin)) {
        return c;
      }
    }
    return null;
  }

  /**
   * Muestra el menu de operaciones para una cuenta especifica.
   * 
   * @param uwu El objeto Scanner para la entrada del usuario.
   * @param cuenta La cuenta sobre la que se realizaran las operaciones.
   * @param portafolio El portafolio de cuentas.
   * @param procesoMensual El proceso mensual a ejecutar.
   */
  private static void mostrarMenuCuenta(Scanner uwu, Cuenta cuenta, Portafolio portafolio, ProcesoMensual procesoMensual) {
    GestorAlertas gestor = new GestorAlertas(cuenta);
    ClienteObservador clienteObs = new ClienteObservador(cuenta.getCliente());
    gestor.agregarObservador(clienteObs);
    
    AccesoRemoto acceso = new AccesoRemoto(cuenta);
    try {
        System.out.print("\nPor favor, confirme su NIP para acceder a las operaciones: ");
        String nip = uwu.nextLine();
        acceso.verificarNIP(nip);
    } catch (EntradaInvalida e) {
        System.out.println("⚠️ Error de autenticacion: " + e.getMessage());
        return;
    }

    boolean salirCuenta = false;

    while (!salirCuenta) {
      try {
        System.out.println("\n--- MENU DE CUENTA: " + cuenta.getCliente() + " ---");
        System.out.println("1. Consultar saldo");
        System.out.println("2. Retirar");
        System.out.println("3. Depositar");
        System.out.println("4. Agregar al Portafolio");
        System.out.println("5. Exportar informacion a TXT");
        System.out.println("6. Contratar servicios");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opcion: ");

        int opcion = Integer.parseInt(uwu.nextLine().trim());

        switch (opcion) {
          case 1 : 
            try {
                System.out.println("Saldo actual: $" + acceso.consultarSaldo());
            } catch (EntradaInvalida e) {
                System.out.println("⚠️ Error: " + e.getMessage());
            }
            break;

          case 2 : {
            try {
                System.out.println("Saldo actual: $" + acceso.consultarSaldo());
                System.out.print("Monto a retirar: ");
                double retiro = Double.parseDouble(uwu.nextLine().trim());
                acceso.retirar(retiro);
                if (retiro > cuenta.getSaldo()) {
                    gestor.generarAlerta("Cuenta sobregirada por retiro de $" + retiro);
                }
                procesoMensual.registrarSaldoDiario(cuenta);
                procesoMensual.registrarOperacion(cuenta, "Retiro por $" + retiro);
            } catch (EntradaInvalida e) {
                System.out.println("⚠️ Error: " + e.getMessage());
            }
            break;
          }

          case 3 : {
            try {
                System.out.println("Saldo actual: $" + acceso.consultarSaldo());
                System.out.print("Monto a depositar: ");
                double deposito = Double.parseDouble(uwu.nextLine().trim());
                acceso.depositar(deposito);
                if (deposito > 100000) {
                    gestor.generarAlerta("Deposito mayor a $100,000");
                }
                procesoMensual.registrarSaldoDiario(cuenta);
                procesoMensual.registrarOperacion(cuenta, "Deposito por $" + deposito);
            } catch (EntradaInvalida e) {
                System.out.println("⚠️ Error: " + e.getMessage());
            }
            break;
          }

          case 4 : {
            portafolio.agregar(cuenta);
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
                System.out.println("4. Plan Premium Todo Incluido (25% descuento)");
                System.out.println("5. Ver servicios contratados");
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
                    if (cuenta.getSaldo() >= 50000) {
                        // Cambiar a estrategia Premium
                        cuenta.setEstrategiaInteres(new intereses.InteresPremium());
                        servicio = new serviciosextra.BeneficiosPremium(servicio, cuenta);
                        System.out.println("Plan Premium activado exitosamente.");
                        BeneficiosPremium premium = (BeneficiosPremium) servicio;
                        System.out.println(premium.proyectarBeneficios());
                    } else {
                        System.out.println("El Plan Premium requiere un saldo minimo de $50,000");
                    }
                    break;
                  case 5:
                    System.out.println("Descripciones: " + servicio.getDesc());
                    System.out.println("Costo total mensual: $" + servicio.getCosto());
                    break;
                  case 0:
                    menuServicios = false;
                    break;
                  default:
                    System.out.println("Opcion no valida.");
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