package cuentas;

import cuentas.EstadoActiva;
import excepciones.EntradaInvalida;
import intereses.InteresMensual;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CuentaTest {

    @Test
    public void testDepositarValido() {
        Cuenta c = new Cuenta("Luis", 0.0, new InteresMensual(), new EstadoActiva(), "1234");
        c.depositar(500);
        assertEquals(500, c.getSaldo());
    }

    @Test
    public void testDepositarNegativoLanzaExcepcion() {
        Cuenta c = new Cuenta("Luis", 0.0, new InteresMensual(), new EstadoActiva(), "1234");
        assertThrows(EntradaInvalida.class, () -> c.depositar(-100));
    }

    @Test
    public void testRetirarValido() {
        Cuenta c = new Cuenta("Luis", 0.0, new InteresMensual(), new EstadoActiva(), "1234");
        c.depositar(400);
        c.retirar(100);
        assertEquals(300, c.getSaldo());
    }

    @Test
    public void testValidarNipCorrecto() {
        Cuenta c = new Cuenta("Luis", 0.0, new InteresMensual(), new EstadoActiva(), "9999");
        assertEquals("9999", c.getNIP());
    }

    @Test
    public void testValidarNipIncorrecto() {
        Cuenta c = new Cuenta("Luis", 0.0, new InteresMensual(), new EstadoActiva(), "9999");
        assertNotEquals("1234", c.getNIP());
    }
}