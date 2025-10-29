package serviciosextra;

import cuentas.*;
import observer.*;
import intereses.InteresMensual;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GestorAlertasTest {

    @Test
    public void testAgregarYNotificarObservador() {
        Cuenta c = new Cuenta("Luis", 5000, new InteresMensual(), new EstadoActiva(), "1212");
        GestorAlertas gestor = new GestorAlertas(c);
        ClienteObservador cliente = new ClienteObservador("Luis");
        gestor.agregarObservador(cliente);

        assertDoesNotThrow(() -> gestor.generarAlerta("Prueba de alerta"));
    }

    @Test
    public void testMultiplesAlertas() {
        Cuenta c = new Cuenta("Luis", 5000, new InteresMensual(), new EstadoActiva(), "1212");
        GestorAlertas gestor = new GestorAlertas(c);
        gestor.agregarObservador(new ClienteObservador("Luis"));
        gestor.generarAlerta("Primera alerta");
        gestor.generarAlerta("Segunda alerta");
        assertTrue(true, "No debe lanzar excepciones con multiples alertas");
    }
}