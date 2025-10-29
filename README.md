# PROYECTO 1 - PumaBank

- **Equipo:** LasPamparas
- **Practica:** 1
- **Materia:** Modelado y Programacion
- **Institucion:** Facultad de Ciencias, UNAM

## Integrantes

- Aviles Luque Luis Diego - 318176653
- Luis Enrique Flores Juárez - 322278316
- Julio Cesar Islas Espino - 320340594

## Estado de la Entrega

La practica se encuentra **completa**. Todos los requisitos funcionales han sido implementados y validados con tests automatizados, incluyendo la funcionalidad extra de generacion de reportes en archivos `.txt`.

## Argumentacion de Patrones de Diseño

Para la solucion de este proyecto se implementaron 8 patrones de diseño clasicos con el objetivo de construir un sistema bancario robusto, flexible y mantenible, adhiriendose a los principios SOLID. A continuacion se argumenta la eleccion de cada uno:

### 1. State (Estado)
- **Razon de uso:** Se eligio para modelar los diferentes estados de una cuenta bancaria (`Activa`, `Sobregirada`, `Congelada`, `Cerrada`). Permite que el objeto `Cuenta` altere su comportamiento dinamicamente cuando su estado interno cambia (e.g., no permitir retiros si esta congelada) sin usar condicionales complejos, encapsulando la logica de cada estado en su propia clase.

### 2. Strategy (Estrategia)
- **Razon de uso:** Se utilizo para definir una familia de algoritmos intercambiables para el calculo de intereses (`InteresMensual`, `InteresAnual`, `InteresPremium`). Esto permite cambiar la politica de intereses de una cuenta en tiempo de ejecucion sin modificar la clase `Cuenta`, promoviendo el principio de Abierto/Cerrado.

### 3. Observer (Observador)
- **Razon de uso:** Se implemento para notificar a multiples clientes (observadores) sobre eventos relevantes en sus cuentas (sujeto), como balances bajos o cargos inesperados. Desacopla al emisor de las notificaciones (`GestorAlertas`) de los receptores (`ClienteObservador`), permitiendo añadir nuevos tipos de notificaciones o suscriptores facilmente.

### 4. Decorator (Decorador)
- **Razon de uso:** Se aplico para añadir funcionalidades y servicios adicionales a una cuenta (`SeguroAntifraude`, `Recompensas`) de forma dinamica y acumulativa. Evita la explosion de subclases que ocurriria si se intentara modelar todas las combinaciones de servicios, permitiendo "envolver" un objeto base con nuevas responsabilidades.

### 5. Proxy (Proxy)
- **Razon de uso:** Se empleo para controlar el acceso a las operaciones bancarias. El `AccesoRemoto` actua como un proxy que verifica el NIP del usuario y controla los intentos fallidos antes de delegar la llamada al objeto real (`OperacionesBancariasImpl`), añadiendo una capa de seguridad de forma transparente.

### 6. Factory Method (Metodo Fabrica)
- **Razon de uso:** Se utilizo para centralizar y simplificar la creacion de distintos tipos de cuentas. `FabricaCuentas` proporciona metodos (`crearCuentaMensual`, `crearCuentaPremium`) que encapsulan la logica de instanciacion y configuracion de cada tipo de cuenta, asegurando que los objetos se creen en un estado consistente.

### 7. Composite (Compuesto)
- **Razon de uso:** Se implemento para tratar de manera uniforme tanto las cuentas individuales (`Cuenta`) como las agrupaciones de cuentas (`Portafolio`). Permite a los clientes ejecutar operaciones (como `getSaldo` o `depositar`) sobre un portafolio completo como si fuera una unica cuenta, simplificando el codigo cliente.

### 8. Template Method (Metodo Plantilla)
- **Razon de uso:** Se uso para definir el esqueleto de un algoritmo en el proceso de cierre mensual (`ProcesoMensualTemplate`), permitiendo que las subclases (`ProcesoMensualStandard`, `ProcesoMensualPremium`) redefinan ciertos pasos (como el calculo de cargos) sin cambiar la estructura general del proceso.

## Como Compilar y Ejecutar

### Usando Maven
```bash
# Compilar
mvn clean compile

# Ejecutar la aplicacion principal
mvn exec:java
```

### Usando Docker
```bash
# Construir la imagen
docker build -t pumabank:1.0 .

# Ejecutar el contenedor
docker run -it --rm pumabank:1.0
```