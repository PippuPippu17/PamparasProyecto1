package test.excepciones;

import excepciones.EntradaInvalida;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ExcepcionEntradaInvalidaTest {

    @Test
    public void testMensajePersonalizado() {
        EntradaInvalida e = new EntradaInvalida("Monto inválido");
        assertEquals("Monto inválido", e.getMessage());
    }

    @Test
    public void testMensajeVacio() {
        EntradaInvalida e = new EntradaInvalida("");
        assertEquals("", e.getMessage());
    }

    @Test
    public void testMensajeNulo() {
        EntradaInvalida e = new EntradaInvalida(null);
        assertNull(e.getMessage());
    }
}