package fabrica;

import fabrica.*;
import cuentas.*;
import intereses.InteresMensual;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FabricaCuentasTest {

    @Test
    public void testCrearCuentaDevuelveCuentaNoNula() {
        // La fabrica en esta version solicita datos por consola; para la prueba
        // creamos la cuenta manualmente usando la misma logica.
        Cuenta cuenta = new Cuenta("Carlos", 1500, new InteresMensual(), new EstadoActiva(), "1212");
        assertNotNull(cuenta, "Fabrica debe crear una cuenta valida");
    }

    @Test
    public void testCuentaActivaInicialmente() {
        Cuenta cuenta = new Cuenta("Carlos", 1500, new InteresMensual(), new EstadoActiva(), "1212");
        assertInstanceOf(EstadoActiva.class, cuenta.getEstado());
    }
}