package cuentas;

import cuentas.EstadoActiva;
import cuentas.Cuenta;
import intereses.InteresMensual;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EstadoActivaTest {

    @Test
    public void testTransicionASobregiradaCuandoSaldoNegativo() {
        Cuenta c = new Cuenta("Luis", 0.0, new InteresMensual(), new EstadoActiva(), "1234");
        c.depositar(100);
        c.retirar(200);
        assertTrue(c.getEstado() instanceof cuentas.EstadoSobregirada);
    }
}