package test.archivos;

import archivo.GeneradorTXT;
import cuentas.Cuenta;
import cuentas.EstadoActiva;
import intereses.InteresMensual;
import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

public class GeneradorTxtTest {

    @Test
    public void testGeneraArchivoTxt() {
        // Crear una cuenta con los parámetros necesarios
        Cuenta c = new Cuenta("Luis", 0.0, new InteresMensual(), new EstadoActiva(), "1111");
        c.depositar(500);
        
        // Generar el archivo usando el nombre del cliente
        String nombreArchivo = c.getCliente() + "_info.txt";
        GeneradorTXT.exportarCuenta(c, nombreArchivo);

        // Verificar que el archivo existe y tiene contenido
        File f = new File(nombreArchivo);
        assertTrue(f.exists());
        assertTrue(f.length() > 0);

        // Limpiar después de la prueba
        f.delete();
    }
    
    @Test
    public void testGenerarPortafolioTxt() {
        // Crear un portafolio con algunas cuentas
        Portafolio p = new Portafolio();
        
        Cuenta c1 = new Cuenta("Luis", 500.0, new InteresMensual(), new EstadoActiva(), "1111");
        Cuenta c2 = new Cuenta("Ana", 1000.0, new InteresMensual(), new EstadoActiva(), "2222");
        
        p.agregarCuenta(c1);
        p.agregarCuenta(c2);
        
        // Generar el archivo de portafolio
        String nombreArchivo = "portafolio_test.txt";
        GeneradorTXT.exportarPortafolio(p, nombreArchivo);
        
        // Verificar que el archivo existe y tiene contenido
        File f = new File(nombreArchivo);
        assertTrue(f.exists());
        assertTrue(f.length() > 0);
        
        // Limpiar después de la prueba
        f.delete();
    }
}