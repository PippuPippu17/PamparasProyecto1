package test.cuentas;

import cuentas.EstadoSobregirada;
import cuentas.Cuenta;
import cuentas.EstadoActiva;
import intereses.InteresMensual;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EstadoSobregiradaTest {

    @Test
    public void testDepositarSuficienteParaVolverActiva() {
        Cuenta c = new Cuenta("Luis", 0.0, new InteresMensual(), new EstadoActiva(), "1234");
        c.depositar(100);
        c.retirar(150); // entra en sobregiro
        c.depositar(200);
        assertTrue(c.getEstado() instanceof EstadoActiva);
    }
}