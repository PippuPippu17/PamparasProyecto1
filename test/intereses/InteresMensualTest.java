package test.intereses;

import intereses.*;
import cuentas.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InteresMensualTest {

    @Test
    public void testCalculoInteresSobreSaldoMayorA1000() {
        InteresMensual interes = new InteresMensual();
        Cuenta c = new Cuenta("Ana", 2000, interes, new EstadoActiva(), "2222");
        // la estrategia define el cálculo a partir del saldo
        double interesCalculado = interes.calInteres(c.getSaldo());
        assertTrue(interesCalculado > 0, "Debe generar interés positivo");
    }

    @Test
    public void testNoGeneraInteresSiSaldoMenorA1000() {
        InteresMensual interes = new InteresMensual();
        Cuenta c = new Cuenta("Ana", 800, interes, new EstadoActiva(), "2222");
        double interesCalculado = interes.calInteres(c.getSaldo());
        assertEquals(0.0, interesCalculado, 0.0001, "No debe generar interés si el saldo < 1000");
    }
}