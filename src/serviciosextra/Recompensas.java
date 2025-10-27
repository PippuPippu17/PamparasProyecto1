package serviciosextra;

import cuentas.*;

/**
 * Servicio adicional: Programa de recompensas.
 */
public class Recompensas extends ServDecorator {
    private static final double RECOMPENSA_BASE = 100.0;
    
    public Recompensas(ServAdicional servicioNormal, Cuenta cuenta) {
        super(servicioNormal, cuenta);
    }

    @Override
    public String getDesc() {
        return servicioNormal.getDesc() +
               " + Recompensas: Por tener monto mínimo de 2,000 se recompensan " + RECOMPENSA_BASE;
    }

    @Override
    public double getCosto() {
        aplicarRecompensa();
        return servicioNormal.getCosto() + 0.0;
    }
    
    /**
     * Aplica la recompensa si se cumplen las condiciones
     */
    public void aplicarRecompensa() {
        if (cuenta.getSaldo() > 2000) {
            cuenta.setSaldo(cuenta.getSaldo() + RECOMPENSA_BASE);
            System.out.println("¡Has recibido una recompensa de $" + RECOMPENSA_BASE + "!");
        }
    }
}

