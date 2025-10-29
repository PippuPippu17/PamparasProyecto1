package acceso;

import cuentas.Cuenta;
import excepciones.EntradaInvalida;

/**
 * Implementacion real de las operaciones bancarias. Esta es la clase "RealSubject"
 * en el patron Proxy. Delega las llamadas directamente al objeto Cuenta.
 * @author julio
 * @version 1.0
 */
public class OperacionesBancariasImpl implements OperacionesBancarias {
    private Cuenta cuenta;

    /**
     * Constructor que asocia la implementacion con una cuenta especifica.
     * @param cuenta La cuenta sobre la que se realizaran las operaciones.
     */
    public OperacionesBancariasImpl(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    /**
     * {@inheritDoc}
     * Consulta el saldo de la cuenta.
     */
    @Override
    public double consultarSaldo() throws EntradaInvalida {
        return cuenta.getSaldo();
    }

    /**
     * {@inheritDoc}
     * Retira un monto de la cuenta.
     */
    @Override
    public void retirar(double monto) throws EntradaInvalida {
        if (monto <= 0) {
            throw new EntradaInvalida("El monto a retirar debe ser positivo");
        }
        cuenta.retirar(monto);
    }

    /**
     * {@inheritDoc}
     * Deposita un monto en la cuenta.
     */
    @Override
    public void depositar(double monto) throws EntradaInvalida {
        if (monto <= 0) {
            throw new EntradaInvalida("El monto a depositar debe ser positivo");
        }
        cuenta.depositar(monto);
    }

    /**
     * {@inheritDoc}
     * Obtiene la cuenta asociada.
     */
    @Override
    public Cuenta getCuenta() {
        return cuenta;
    }
}