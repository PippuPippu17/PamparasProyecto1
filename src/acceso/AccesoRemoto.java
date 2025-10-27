package acceso;

import cuentas.Cuenta;
import excepciones.EntradaInvalida;
import observer.GestorAlertas;

/**
 * Implementa el patrón Proxy para controlar el acceso remoto a las operaciones bancarias.
 * Verifica el NIP antes de permitir cualquier operación.
 */
public class AccesoRemoto implements OperacionesBancarias {
    private OperacionesBancarias operaciones;
    private String nipAlmacenado;
    private boolean autenticado;
    private int intentosFallidos;
    private GestorAlertas gestor;
    private static final int MAX_INTENTOS = 3;
    
    public AccesoRemoto(Cuenta cuenta) {
        this.operaciones = new OperacionesBancariasImpl(cuenta);
        this.nipAlmacenado = cuenta.getNIP();
        this.autenticado = false;
        this.intentosFallidos = 0;
        this.gestor = new GestorAlertas(cuenta);
    }
    
    /**
     * Verifica el NIP proporcionado
     * @param nip NIP ingresado por el usuario
     * @return true si la autenticación es exitosa
     * @throws EntradaInvalida si hay demasiados intentos fallidos o el NIP es inválido
     */
    public boolean verificarNIP(String nip) throws EntradaInvalida {
        if (intentosFallidos >= MAX_INTENTOS) {
            gestor.generarAlerta("Cuenta bloqueada por múltiples intentos fallidos de acceso");
            throw new EntradaInvalida("Cuenta bloqueada por seguridad. Contacte a soporte.");
        }
        
        if (nip == null || nip.length() != 4) {
            intentosFallidos++;
            throw new EntradaInvalida("El NIP debe tener 4 dígitos.");
        }
        
        if (nip.equals(nipAlmacenado)) {
            autenticado = true;
            intentosFallidos = 0;
            gestor.generarAlerta("Acceso exitoso a la cuenta");
            return true;
        } else {
            intentosFallidos++;
            gestor.generarAlerta("Intento fallido de acceso a la cuenta");
            throw new EntradaInvalida("NIP incorrecto. Intentos restantes: " + (MAX_INTENTOS - intentosFallidos));
        }
    }
    
    private void verificarAutenticacion() throws EntradaInvalida {
        if (!autenticado) {
            throw new EntradaInvalida("Debe autenticarse primero con su NIP.");
        }
    }
    
    @Override
    public double consultarSaldo() throws EntradaInvalida {
        verificarAutenticacion();
        return operaciones.consultarSaldo();
    }
    
    @Override
    public void retirar(double monto) throws EntradaInvalida {
        verificarAutenticacion();
        operaciones.retirar(monto);
    }
    
    @Override
    public void depositar(double monto) throws EntradaInvalida {
        verificarAutenticacion();
        operaciones.depositar(monto);
    }
    
    @Override
    public Cuenta getCuenta() {
        return operaciones.getCuenta();
    }
    
    /**
     * Cierra la sesión actual
     */
    public void cerrarSesion() {
        autenticado = false;
        gestor.generarAlerta("Sesión cerrada");
    }
    
    /**
     * Verifica si hay una sesión activa
     * @return true si el usuario está autenticado
     */
    public boolean estaAutenticado() {
        return autenticado;
    }
}