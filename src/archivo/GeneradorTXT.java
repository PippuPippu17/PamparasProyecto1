package archivo;

import java.io.FileWriter;
import java.io.IOException;
import cuentas.Cuenta;
import cuentas.Portafolio;
import cuentas.ComponenteBancario;

/**
 * Clase encargada de exportar informacion de componentes bancarios a un archivo .txt.
 * @author LasPamparas
 * @version 1.0
 */
public class GeneradorTXT {

  /**
   * Exporta contenido arbitrario a un archivo txt.
   * Este es un metodo de utilidad general.
   * @param nombreArchivo Nombre del archivo a generar.
   * @param contenido Contenido a escribir en el archivo.
   */
  public static void exportarContenido(String nombreArchivo, String contenido) {
    try (FileWriter writer = new FileWriter(nombreArchivo)) {
      writer.write(contenido);
      System.out.println("Archivo " + nombreArchivo + " generado con exito.");
    } catch (IOException e) {
      System.out.println("Error al generar el archivo: " + e.getMessage());
    }
  }

  /**
   * Exporta la descripcion de cualquier componente bancario a un archivo.
   * @param componente El componente (Cuenta o Portafolio) a exportar.
   * @param nombreArchivo Nombre del archivo a generar.
   */
  private static void exportarComponente(ComponenteBancario componente, String nombreArchivo) {
    try (FileWriter writer = new FileWriter(nombreArchivo)) {
      writer.write(componente.desc());
      System.out.println("Archivo " + nombreArchivo + " generado con exito.");
    } catch (IOException e) {
      System.out.println("Error al generar el archivo: " + e.getMessage());
    }
  }

  /**
   * Exporta el registro de una cuenta a un archivo txt.
   * @param cuenta La cuenta cuyos datos se exportan.
   * @param nombreArchivo Nombre del archivo .txt.
   */
  public static void exportarCuenta(Cuenta cuenta, String nombreArchivo) {
    exportarComponente(cuenta, nombreArchivo);
  }

  /**
   * Exporta la informacion de un portafolio a un archivo txt.
   * @param portafolio El portafolio a exportar.
   * @param nombreArchivo Nombre del archivo .txt.
   */
  public static void exportarPortafolio(Portafolio portafolio, String nombreArchivo) {
    exportarComponente(portafolio, nombreArchivo);
  }
}