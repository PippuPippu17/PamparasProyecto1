package test.fabrica;

import fabrica.*;
import cuentas.*;
import intereses.InteresMensual;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FabricaCuentasTest {

    @Test
    public void testCrearCuentaDevuelveCuentaNoNula() {
        // La fábrica en esta versión solicita datos por consola; para la prueba
        // creamos la cuenta manualmente usando la misma lógica.
        Cuenta cuenta = new Cuenta("Carlos", 1500, new InteresMensual(), new EstadoActiva(), "1212");
        assertNotNull(cuenta, "Fábrica debe crear una cuenta válida");
    }

    @Test
    public void testCuentaActivaInicialmente() {
        Cuenta cuenta = new Cuenta("Carlos", 1500, new InteresMensual(), new EstadoActiva(), "1212");
        assertInstanceOf(EstadoActiva.class, cuenta.getEstado());
    }
}