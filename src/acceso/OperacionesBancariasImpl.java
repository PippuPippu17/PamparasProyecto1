package acceso;

import cuentas.Cuenta;
import excepciones.EntradaInvalida;

/**
 * Implementación real de las operaciones bancarias.
 */
public class OperacionesBancariasImpl implements OperacionesBancarias {
    private Cuenta cuenta;
    
    public OperacionesBancariasImpl(Cuenta cuenta) {
        this.cuenta = cuenta;
    }
    
    @Override
    public double consultarSaldo() throws EntradaInvalida {
        return cuenta.getSaldo();
    }
    
    @Override
    public void retirar(double monto) throws EntradaInvalida {
        if (monto <= 0) {
            throw new EntradaInvalida("El monto a retirar debe ser positivo");
        }
        cuenta.retirar(monto);
    }
    
    @Override
    public void depositar(double monto) throws EntradaInvalida {
        if (monto <= 0) {
            throw new EntradaInvalida("El monto a depositar debe ser positivo");
        }
        cuenta.depositar(monto);
    }
    
    @Override
    public Cuenta getCuenta() {
        return cuenta;
    }
}