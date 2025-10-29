package serviciosextra;

import cuentas.*;

/**
 * Servicio adicional: Programa de recompensas.
 * @author LasPamparas
 * @version 1.0
 */
public class Recompensas extends ServDecorator {
    private static final double RECOMPENSA_BASE = 100.0;
    
    /**
     * Constructor para el decorador de Recompensas.
     * @param servicioNormal el servicio base a decorar.
     * @param cuenta la cuenta asociada.
     */
    public Recompensas(ServAdicional servicioNormal, Cuenta cuenta) {
        super(servicioNormal, cuenta);
    }

    /**
     * {@inheritDoc}
     * Agrega la descripcion del programa de recompensas a la descripcion del servicio base.
     */
    @Override
    public String getDesc() {
        return servicioNormal.getDesc() +
               " + Recompensas: Por tener monto minimo de 2,000 se recompensan " + RECOMPENSA_BASE;
    }

    /**
     * {@inheritDoc}
     * Aplica la recompensa y devuelve el costo del servicio base.
     */
    @Override
    public double getCosto() {
        aplicarRecompensa();
        return servicioNormal.getCosto() + 0.0;
    }
    
    /**
     * Aplica la recompensa si se cumplen las condiciones (saldo > 2000).
     */
    public void aplicarRecompensa() {
        if (cuenta.getSaldo() > 2000) {
            cuenta.setSaldo(cuenta.getSaldo() + RECOMPENSA_BASE);
            System.out.println("¡Has recibido una recompensa de $" + RECOMPENSA_BASE + "!");
        }
    }
}