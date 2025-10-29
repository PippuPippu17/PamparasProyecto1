package intereses;

/**
 * Implementa el plan de interes Premium con beneficios adicionales.
 * Ofrece tasas de interes escalonadas segun el saldo y antiguedad.
 * @author LasPamparas
 * @version 1.0
 */
public class InteresPremium implements StrategyInteres {
    private static final double TASA_BASE = 0.08; // 8% base
    private static final double BONO_SALDO_ALTO = 0.02; // +2% para saldos altos
    private static final double BONO_ANTIGUEDAD = 0.01; // +1% por antiguedad
    private int mesesAntiguedad;
    
    /**
     * Constructor de la estrategia de interes Premium.
     */
    public InteresPremium() {
        this.mesesAntiguedad = 0;
    }
    
    /**
     * Incrementa la antiguedad en meses.
     */
    public void incrementarAntiguedad() {
        mesesAntiguedad++;
    }
    
    /**
     * Devuelve la antiguedad en meses.
     * @return la antiguedad en meses.
     */
    public int getAntiguedad() {
        return mesesAntiguedad;
    }

    /**
     * {@inheritDoc}
     * Devuelve el nombre de la estrategia: "Premium".
     */
    @Override
    public String getNombre() {
        return "Premium";
    }

    /**
     * {@inheritDoc}
     * Calcula el interes Premium, que incluye bonificaciones por saldo alto y antiguedad.
     */
    @Override
    public double calInteres(double saldo) {
        double tasaTotal = TASA_BASE;
        
        // Bonificacion por saldo alto (mas de 100,000)
        if (saldo >= 100000) {
            tasaTotal += BONO_SALDO_ALTO;
        }
        
        // Bonificacion por antiguedad (cada 12 meses)
        int anosAntiguedad = mesesAntiguedad / 12;
        tasaTotal += (BONO_ANTIGUEDAD * Math.min(anosAntiguedad, 5)); // Maximo 5 anos de bonificacion
        
        return saldo * tasaTotal;
    }
    
    /**
     * Calcula la tasa de interes efectiva actual.
     * @param saldo el saldo actual de la cuenta.
     * @return la tasa de interes como un porcentaje.
     */
    public double getTasaActual(double saldo) {
        double tasaTotal = TASA_BASE;
        
        if (saldo >= 100000) {
            tasaTotal += BONO_SALDO_ALTO;
        }
        
        int anosAntiguedad = mesesAntiguedad / 12;
        tasaTotal += (BONO_ANTIGUEDAD * Math.min(anosAntiguedad, 5));
        
        return tasaTotal * 100; // Convertir a porcentaje
    }
    
    /**
     * Proyecta los intereses que se generarian con un saldo especifico.
     * @param saldoProyectado el saldo para calcular la proyeccion.
     * @return los intereses proyectados.
     */
    public double proyectarIntereses(double saldoProyectado) {
        return calInteres(saldoProyectado);
    }
}