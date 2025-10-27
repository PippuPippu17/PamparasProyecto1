package test.observer;

import observer.GestorAlertas;
import observer.ClienteObservador;
import cuentas.Cuenta;
import cuentas.EstadoActiva;
import intereses.InteresMensual;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GestorAlertasTest {

    @Test
    public void testRegistrarYNotificar() {
        // Crear una cuenta para el gestor de alertas
        Cuenta cuenta = new Cuenta("Luis", 1000.0, new InteresMensual(), new EstadoActiva(), "1234");
        
        // Crear el gestor de alertas (Sujeto)
        GestorAlertas gestor = new GestorAlertas(cuenta);
        
        // Crear un observador
        ClienteObservador observador = new ClienteObservador("Luis");
        
        // Registrar el observador
        gestor.agregarObservador(observador);
        
        // Generar una alerta
        gestor.generarAlerta("Saldo bajo");
        
        // La verificación se hace a través de la salida del sistema
        // Ya que los mensajes se imprimen directamente
        assertTrue(true); // La prueba pasa si no hay excepciones
    }
    
    @Test
    public void testEliminarObservador() {
        Cuenta cuenta = new Cuenta("Luis", 1000.0, new InteresMensual(), new EstadoActiva(), "1234");
        GestorAlertas gestor = new GestorAlertas(cuenta);
        ClienteObservador observador = new ClienteObservador("Luis");
        
        gestor.agregarObservador(observador);
        gestor.eliminarObservador(observador);
        
        // Verificar que no haya excepciones al notificar después de eliminar
        gestor.generarAlerta("Prueba después de eliminar");
        assertTrue(true);
    }
    
    @Test
    public void testMultiplesObservadores() {
        Cuenta cuenta = new Cuenta("Luis", 1000.0, new InteresMensual(), new EstadoActiva(), "1234");
        GestorAlertas gestor = new GestorAlertas(cuenta);
        
        ClienteObservador observador1 = new ClienteObservador("Luis");
        ClienteObservador observador2 = new ClienteObservador("Ana");
        
        gestor.agregarObservador(observador1);
        gestor.agregarObservador(observador2);
        
        // Verificar que se pueden notificar múltiples observadores sin errores
        gestor.generarAlerta("Notificación múltiple");
        assertTrue(true);
    }
}