package cuentas;

import intereses.InteresMensual;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PortafolioTest {

    @Test
    public void testAgregarCuentaYCalcularTotal() {
        Portafolio p = new Portafolio("Portafolio de prueba");
        Cuenta cuenta = new Cuenta("Luis", 5000.0, new InteresMensual(), new EstadoActiva(), "1234");
        p.agregar(cuenta);

        // El nuevo API devuelve el saldo directamente
        assertEquals(5000.0, p.getSaldo());
    }

    @Test
    public void testBuscarCuentaPorNombre() {
        Portafolio p = new Portafolio("Portafolio de prueba");
        Cuenta cuenta = new Cuenta("Luis", 5000.0, new InteresMensual(), new EstadoActiva(), "1234");
        p.agregar(cuenta);

        // Buscar en los componentes del portafolio
        Cuenta encontrada = null;
        for (ComponenteBancario comp : p.getComponentes()) {
            if (comp instanceof Cuenta && ((Cuenta) comp).getCliente().equals("Luis")) {
                encontrada = (Cuenta) comp;
                break;
            }
        }
        assertNotNull(encontrada);
        assertEquals("Luis", encontrada.getCliente());
    }

    @Test
    public void testEliminarCuenta() {
        Portafolio p = new Portafolio("Portafolio de prueba");
        Cuenta cuenta = new Cuenta("Luis", 5000.0, new InteresMensual(), new EstadoActiva(), "1234");
        p.agregar(cuenta);
        p.remover(cuenta);

        // Verificar que la cuenta fue eliminada
        boolean encontrada = false;
        for (ComponenteBancario comp : p.getComponentes()) {
            if (comp instanceof Cuenta && ((Cuenta) comp).getCliente().equals("Luis")) {
                encontrada = true;
                break;
            }
        }
        assertFalse(encontrada);
    }

    @Test
    public void testObtenerListaCuentas() {
        Portafolio p = new Portafolio("Portafolio de prueba");
        Cuenta cuenta1 = new Cuenta("Luis", 5000.0, new InteresMensual(), new EstadoActiva(), "1234");
        Cuenta cuenta2 = new Cuenta("Ana", 3000.0, new InteresMensual(), new EstadoActiva(), "5678");

        p.agregar(cuenta1);
        p.agregar(cuenta2);

        assertEquals(2, p.getComponentes().size());
    }
}