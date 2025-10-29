package acceso;

import cuentas.Cuenta;
import excepciones.EntradaInvalida;
import observer.GestorAlertas;

/**
 * Implementa el patron Proxy para controlar el acceso remoto a las operaciones bancarias.
 * Verifica el NIP antes de permitir cualquier operacion, anadiendo una capa de seguridad.
 * Tambien gestiona los intentos de acceso fallidos.
 * @author LasPamparas
 * @version 1.0
 */
public class AccesoRemoto implements OperacionesBancarias {
    private OperacionesBancarias operaciones;
    private String nipAlmacenado;
    private boolean autenticado;
    private int intentosFallidos;
    private GestorAlertas gestor;
    private static final int MAX_INTENTOS = 3;

    /**
     * Constructor del Proxy de Acceso Remoto.
     * @param cuenta La cuenta real a la que se controlara el acceso.
     */
    public AccesoRemoto(Cuenta cuenta) {
        this.operaciones = new OperacionesBancariasImpl(cuenta);
        this.nipAlmacenado = cuenta.getNIP();
        this.autenticado = false;
        this.intentosFallidos = 0;
        this.gestor = new GestorAlertas(cuenta);
    }

    /**
     * Verifica si el NIP proporcionado por el usuario es correcto.
     * Si el NIP es correcto, el proxy se marca como autenticado.
     * Si es incorrecto, incrementa el contador de intentos fallidos.
     * @param nip NIP ingresado por el usuario.
     * @return true si la autenticacion es exitosa.
     * @throws EntradaInvalida si hay demasiados intentos fallidos o el NIP es invalido.
     */
    public boolean verificarNIP(String nip) throws EntradaInvalida {
        if (intentosFallidos >= MAX_INTENTOS) {
            gestor.generarAlerta("Cuenta bloqueada por multiples intentos fallidos de acceso");
            throw new EntradaInvalida(
                "CUENTA BLOQUEADA POR SEGURIDAD\n\n" +
                "Has excedido el numero maximo de intentos (3).\n\n" +
                "Por tu seguridad, esta cuenta ha sido temporalmente bloqueada.\n" +
                "Para desbloquearla:\n" +
                "- Contacta a soporte: 55-PUMABANK (55-7862-2265)\n" +
                "- Acude a una sucursal con identificacion oficial\n" +
                "- Horario: Lun-Vie 9:00-18:00"
            );
        }

        if (nip == null || nip.length() != 4) {
            intentosFallidos++;
            throw new EntradaInvalida("El NIP debe tener 4 digitos.");
        }

        if (nip.equals(nipAlmacenado)) {
            autenticado = true;
            intentosFallidos = 0;
            gestor.generarAlerta("Acceso exitoso a la cuenta");
            return true;
        } else {
            intentosFallidos++;
            int intentosRestantes = MAX_INTENTOS - intentosFallidos;
            gestor.generarAlerta("Intento fallido de acceso (#" + intentosFallidos + ")");

            if (intentosRestantes == 0) {
                gestor.generarAlerta("Cuenta bloqueada por multiples intentos fallidos");
                throw new EntradaInvalida(
                    "CUENTA BLOQUEADA\n\n" +
                    "NIP incorrecto. Has agotado todos los intentos.\n\n" +
                    "Contacta a soporte: 55-PUMABANK (55-7862-2265)"
                );
            } else {
                throw new EntradaInvalida(
                    "NIP incorrecto\n\n" +
                    "Intentos restantes: " + intentosRestantes + "\n" +
                    (intentosRestantes == 1 ? "ULTIMO INTENTO - Ten cuidado" : "")
                );
            }
        }
    }

    /**
     * Metodo privado que comprueba si el usuario ha sido autenticado.
     * @throws EntradaInvalida si el usuario no esta autenticado.
     */
    private void verificarAutenticacion() throws EntradaInvalida {
        if (!autenticado) {
            throw new EntradaInvalida("Debe autenticarse primero con su NIP.");
        }
    }

    /**
     * {@inheritDoc}
     * Primero verifica la autenticacion, luego delega la llamada al objeto real.
     */
    @Override
    public double consultarSaldo() throws EntradaInvalida {
        verificarAutenticacion();
        return operaciones.consultarSaldo();
    }

    /**
     * {@inheritDoc}
     * Primero verifica la autenticacion, luego delega la llamada al objeto real.
     */
    @Override
    public void retirar(double monto) throws EntradaInvalida {
        verificarAutenticacion();
        operaciones.retirar(monto);
    }

    /**
     * {@inheritDoc}
     * Primero verifica la autenticacion, luego delega la llamada al objeto real.
     */
    @Override
    public void depositar(double monto) throws EntradaInvalida {
        verificarAutenticacion();
        operaciones.depositar(monto);
    }

    /**
     * {@inheritDoc}
     * Obtiene la cuenta asociada.
     */
    @Override
    public Cuenta getCuenta() {
        return operaciones.getCuenta();
    }

    /**
     * Cierra la sesion actual, revocando el estado de autenticacion.
     */
    public void cerrarSesion() {
        autenticado = false;
        gestor.generarAlerta("Sesion cerrada");
    }

    /**
     * Verifica si hay una sesion activa.
     * @return true si el usuario esta autenticado, false en caso contrario.
     */
    public boolean estaAutenticado() {
        return autenticado;
    }
}
