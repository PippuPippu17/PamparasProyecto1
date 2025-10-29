package cuentas;

import cuentas.*;
import intereses.InteresMensual;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EstadoCerradaTest {

    @Test
    public void testNoPermiteDeposito() {
        Cuenta cuenta = new Cuenta("Luis", 5000, new InteresMensual(), new EstadoCerrada(), "1212");
        double antes = cuenta.getSaldo();
        cuenta.depositar(1000);
        assertEquals(antes, cuenta.getSaldo(), 0.0001, "Saldo no debe cambiar al depositar en cuenta cerrada");
    }

    @Test
    public void testNoPermiteRetiro() {
        Cuenta cuenta = new Cuenta("Luis", 5000, new InteresMensual(), new EstadoCerrada(), "1212");
        double antes = cuenta.getSaldo();
        cuenta.retirar(100);
        assertEquals(antes, cuenta.getSaldo(), 0.0001, "Saldo no debe cambiar al retirar en cuenta cerrada");
    }

    @Test
    public void testReabrirCuentaPermiteOperaciones() {
        Cuenta cuenta = new Cuenta("Luis", 5000, new InteresMensual(), new EstadoCerrada(), "1212");
        double antes = cuenta.getSaldo();
        cuenta.setEstado(new EstadoActiva());
        // ahora realizar un retiro y que cambie el saldo
        cuenta.retirar(100);
        assertNotEquals(antes, cuenta.getSaldo(), "Al reabrir y retirar, el saldo debe cambiar");
        assertInstanceOf(EstadoActiva.class, cuenta.getEstado());
    }
}