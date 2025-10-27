package test.cuentas;

import cuentas.*;
import intereses.InteresMensual;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EstadoCongeladaTest {

    @Test
    public void testNoPermiteRetiro() {
        Cuenta cuenta = new Cuenta("Julio", 3000, new InteresMensual(), new EstadoCongelada(), "8888");
        assertThrows(IllegalStateException.class, () -> cuenta.retirar(100));
    }

    @Test
    public void testNoPermiteDeposito() {
        Cuenta cuenta = new Cuenta("Julio", 3000, new InteresMensual(), new EstadoCongelada(), "8888");
        assertThrows(IllegalStateException.class, () -> cuenta.depositar(200));
    }

    @Test
    public void testDescongelarCuenta() {
        Cuenta cuenta = new Cuenta("Julio", 3000, new InteresMensual(), new EstadoCongelada(), "8888");
        cuenta.setEstado(new EstadoActiva());
        assertInstanceOf(EstadoActiva.class, cuenta.getEstado());
    }
}