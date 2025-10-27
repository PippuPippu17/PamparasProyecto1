package archivo;

import java.io.FileWriter;
import java.io.IOException;
import cuentas.Cuenta;
import cuentas.Portafolio;

/**
 * Clase encargada de exportar información de cuentas o portafolios a un archivo .txt.
 */
public class GeneradorTXT {

  /**
   * Exporta contenido arbitrario a un archivo txt
   * @param nombreArchivo Nombre del archivo a generar
   * @param contenido Contenido a escribir en el archivo
   */
  public static void exportarContenido(String nombreArchivo, String contenido) {
    try (FileWriter writer = new FileWriter(nombreArchivo)) {
      writer.write(contenido);
      System.out.println("Archivo " + nombreArchivo + " generado con éxito.");
    } catch (IOException e) {
      System.out.println("Error al generar el archivo: " + e.getMessage());
    }
  }

  /**
   * Exporta registro mensual de la cuenta a un archivo txt
   * @param cuenta Cuenta de los datos que se exportann.
   * @param nombreArchivo Nombre del archivo .txt
   */
  public static void exportarCuenta(Cuenta cuenta, String nombreArchivo) {
    try (FileWriter writer = new FileWriter(nombreArchivo)) {
      writer.write("Informacion de cuenta\n");
      writer.write(cuenta.desc() + "\n");
      System.out.println("Archivo " + nombreArchivo + " generado con exito.");
    } catch (IOException e) {
      System.out.println("Error al generar el archivo: " + e.getMessage());
    }
  }

  /**
   * Exporta la info de un portafolio 
   * @param portafolio Portafolio a exportar.
   * @param nombreArchivo Nombre del archivo .txt
   */
  public static void exportarPortafolio(Portafolio portafolio, String nombreArchivo) {
    try (FileWriter writer = new FileWriter(nombreArchivo)) {
      writer.write("Portafolio de Cuentas\n\n");
      for (Cuenta c : portafolio.getCuentas()) {
        writer.write(c.desc() + "\n");
      }
      writer.write("\n" + portafolio.sumaSaldo());
      System.out.println("Archivo " + nombreArchivo + " generado con exito.");
    } catch (IOException e) {
      System.out.println("Error al generar el archivo: " + e.getMessage());
    }
  }
}

