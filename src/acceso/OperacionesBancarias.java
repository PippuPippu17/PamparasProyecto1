package acceso;

import cuentas.Cuenta;
import excepciones.EntradaInvalida;

/**
 * Interfaz que define las operaciones bancarias disponibles.
 * Parte del patrón Proxy para el control de acceso.
 */
public interface OperacionesBancarias {
    /**
     * Consulta el saldo de la cuenta
     * @return saldo actual
     * @throws EntradaInvalida si no está autenticado
     */
    double consultarSaldo() throws EntradaInvalida;
    
    /**
     * Realiza un retiro de la cuenta
     * @param monto cantidad a retirar
     * @throws EntradaInvalida si no está autenticado o el monto es inválido
     */
    void retirar(double monto) throws EntradaInvalida;
    
    /**
     * Realiza un depósito en la cuenta
     * @param monto cantidad a depositar
     * @throws EntradaInvalida si no está autenticado o el monto es inválido
     */
    void depositar(double monto) throws EntradaInvalida;
    
    /**
     * Obtiene la cuenta asociada
     * @return la cuenta bancaria
     */
    Cuenta getCuenta();
}