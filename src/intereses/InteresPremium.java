package intereses;

/**
 * Implementa el plan de interés Premium con beneficios adicionales.
 * Ofrece tasas de interés escalonadas según el saldo y antigüedad.
 */
public class InteresPremium implements StrategyInteres {
    private static final double TASA_BASE = 0.08; // 8% base
    private static final double BONO_SALDO_ALTO = 0.02; // +2% para saldos altos
    private static final double BONO_ANTIGUEDAD = 0.01; // +1% por antigüedad
    private int mesesAntiguedad;
    
    public InteresPremium() {
        this.mesesAntiguedad = 0;
    }
    
    public void incrementarAntiguedad() {
        mesesAntiguedad++;
    }
    
    public int getAntiguedad() {
        return mesesAntiguedad;
    }

    @Override
    public String getNombre() {
        return "Premium";
    }

    @Override
    public double calInteres(double saldo) {
        double tasaTotal = TASA_BASE;
        
        // Bonificación por saldo alto (más de 100,000)
        if (saldo >= 100000) {
            tasaTotal += BONO_SALDO_ALTO;
        }
        
        // Bonificación por antigüedad (cada 12 meses)
        int añosAntiguedad = mesesAntiguedad / 12;
        tasaTotal += (BONO_ANTIGUEDAD * Math.min(añosAntiguedad, 5)); // Máximo 5 años de bonificación
        
        return saldo * tasaTotal;
    }
    
    /**
     * Calcula la tasa de interés efectiva actual
     * @return tasa de interés como porcentaje
     */
    public double getTasaActual(double saldo) {
        double tasaTotal = TASA_BASE;
        
        if (saldo >= 100000) {
            tasaTotal += BONO_SALDO_ALTO;
        }
        
        int añosAntiguedad = mesesAntiguedad / 12;
        tasaTotal += (BONO_ANTIGUEDAD * Math.min(añosAntiguedad, 5));
        
        return tasaTotal * 100; // Convertir a porcentaje
    }
    
    /**
     * Proyecta los intereses que se generarían con un saldo específico
     * @param saldoProyectado saldo para calcular proyección
     * @return intereses proyectados
     */
    public double proyectarIntereses(double saldoProyectado) {
        return calInteres(saldoProyectado);
    }
}
