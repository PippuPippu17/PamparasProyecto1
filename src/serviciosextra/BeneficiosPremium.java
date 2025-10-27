package serviciosextra;

import cuentas.Cuenta;
import intereses.InteresPremium;
import observer.GestorAlertas;

/**
 * Implementa beneficios especiales para clientes premium.
 * Combina servicios adicionales y ofrece descuentos.
 */
public class BeneficiosPremium extends ServDecorator {
    private static final double DESCUENTO_SERVICIOS = 0.25; // 25% de descuento en servicios
    private GestorAlertas gestor;
    
    public BeneficiosPremium(ServAdicional servicioNormal, Cuenta cuenta) {
        super(servicioNormal, cuenta);
        this.gestor = new GestorAlertas(cuenta);
        aplicarBeneficiosPremium();
    }
    
    private void aplicarBeneficiosPremium() {
        // Activar alertas premium automáticamente
        servicioNormal = new AlertasPremium(servicioNormal, cuenta);
        
        // Activar seguro antifraude con descuento
        servicioNormal = new SeguroAntifraude(servicioNormal, cuenta);
        
        // Activar programa de recompensas mejorado
        servicioNormal = new Recompensas(servicioNormal, cuenta) {
            @Override
            public double getCosto() {
                // Las recompensas son gratuitas para clientes premium
                return 0.0;
            }
            
            @Override
            public void aplicarRecompensa() {
                if (cuenta.getSaldo() > 2000) {
                    // Doble recompensa para clientes premium
                    cuenta.setSaldo(cuenta.getSaldo() + 200);
                    gestor.generarAlerta("¡Recompensa premium aplicada: $200!");
                }
            }
        };
    }

    @Override
    public String getDesc() {
        return servicioNormal.getDesc() + "\n" +
               "** Cliente Premium **\n" +
               "- 25% de descuento en servicios adicionales\n" +
               "- Recompensas duplicadas\n" +
               "- Atención prioritaria\n" +
               "- Tasas de interés preferenciales";
    }

    @Override
    public double getCosto() {
        // Aplica descuento del 25% al costo total de servicios
        double costoTotal = servicioNormal.getCosto();
        double descuento = costoTotal * DESCUENTO_SERVICIOS;
        
        gestor.generarAlerta(String.format(
            "Descuento premium aplicado: $%.2f (25%% sobre $%.2f)",
            descuento, costoTotal
        ));
        
        return costoTotal - descuento;
    }
    
    /**
     * Proyecta los beneficios financieros del plan premium
     * @return resumen de beneficios proyectados
     */
    public String proyectarBeneficios() {
        InteresPremium interesPremium = (InteresPremium) cuenta.getEstrategiaInteres();
        double saldoActual = cuenta.getSaldo();
        
        StringBuilder proyeccion = new StringBuilder();
        proyeccion.append("\n=== Proyección de Beneficios Premium ===\n");
        proyeccion.append(String.format("Tasa de interés actual: %.2f%%\n", 
            interesPremium.getTasaActual(saldoActual)));
        proyeccion.append(String.format("Intereses proyectados: $%.2f\n", 
            interesPremium.proyectarIntereses(saldoActual)));
        proyeccion.append(String.format("Ahorro en servicios: $%.2f\n", 
            servicioNormal.getCosto() * DESCUENTO_SERVICIOS));
        proyeccion.append("Beneficios adicionales:\n");
        proyeccion.append("- Recompensas duplicadas ($200 por mes)\n");
        proyeccion.append("- Atención prioritaria\n");
        proyeccion.append("- Servicios premium incluidos\n");
        
        return proyeccion.toString();
    }
}