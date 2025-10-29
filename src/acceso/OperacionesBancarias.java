package acceso;

import cuentas.Cuenta;
import excepciones.EntradaInvalida;

/**
 * Interfaz que define las operaciones bancarias disponibles.
 * Parte del patron Proxy para el control de acceso.
 * @author LasPamparas
 * @version 1.0
 */
public interface OperacionesBancarias {
    /**
     * Consulta el saldo de la cuenta.
     * @return el saldo actual.
     * @throws EntradaInvalida si no esta autenticado.
     */
    double consultarSaldo() throws EntradaInvalida;
    
    /**
     * Realiza un retiro de la cuenta.
     * @param monto la cantidad a retirar.
     * @throws EntradaInvalida si no esta autenticado o el monto es invalido.
     */
    void retirar(double monto) throws EntradaInvalida;
    
    /**
     * Realiza un deposito en la cuenta.
     * @param monto la cantidad a depositar.
     * @throws EntradaInvalida si no esta autenticado o el monto es invalido.
     */
    void depositar(double monto) throws EntradaInvalida;
    
    /**
     * Obtiene la cuenta asociada.
     * @return la cuenta bancaria.
     */
    Cuenta getCuenta();
}