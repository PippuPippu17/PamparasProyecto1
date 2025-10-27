package test.cuentas;

import cuentas.Portafolio;
import cuentas.Cuenta;
import cuentas.EstadoActiva;
import excepciones.EntradaInvalida;
import intereses.InteresMensual;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PortafolioTest {

    @Test
    public void testAgregarCuentaYCalcularTotal() {
        Portafolio p = new Portafolio();
        Cuenta cuenta = new Cuenta("Luis", 5000.0, new InteresMensual(), new EstadoActiva(), "1234");
        p.agregarCuenta(cuenta);
        
        // Convertimos el resultado de sumaSaldo() a double para la comparación
        String resultado = p.sumaSaldo();
        double total = Double.parseDouble(resultado.split("\\$")[1]);
        assertEquals(5000.0, total);
    }

    @Test
    public void testBuscarCuentaPorNombre() {
        Portafolio p = new Portafolio();
        Cuenta cuenta = new Cuenta("Luis", 5000.0, new InteresMensual(), new EstadoActiva(), "1234");
        p.agregarCuenta(cuenta);
        
        Cuenta encontrada = p.buscarCuentaPorNombre("Luis");
        assertNotNull(encontrada);
        assertEquals("Luis", encontrada.getCliente());
    }

    @Test
    public void testEliminarCuenta() {
        Portafolio p = new Portafolio();
        Cuenta cuenta = new Cuenta("Luis", 5000.0, new InteresMensual(), new EstadoActiva(), "1234");
        p.agregarCuenta(cuenta);
        p.eliminarCuenta(cuenta);
        
        // Verificar que la cuenta fue eliminada
        assertNull(p.buscarCuentaPorNombre("Luis"));
    }

    @Test
    public void testObtenerListaCuentas() {
        Portafolio p = new Portafolio();
        Cuenta cuenta1 = new Cuenta("Luis", 5000.0, new InteresMensual(), new EstadoActiva(), "1234");
        Cuenta cuenta2 = new Cuenta("Ana", 3000.0, new InteresMensual(), new EstadoActiva(), "5678");
        
        p.agregarCuenta(cuenta1);
        p.agregarCuenta(cuenta2);
        
        assertEquals(2, p.getCuentas().size());
    }
}