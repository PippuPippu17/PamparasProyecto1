# PROYECTO 1 - PumaBank

**Materia:** Modelado y Programacion  
**Practica:** 1  
**Equipo:** LasPamparas

---

## Integrantes

- **Aviles Luque Luis Diego:** 318176653
- **Luis Enrique Flores Juárez:** 322278316
- **Julio Cesar Islas Espino:** 320340594

---

## Formato de Entrega

El formato de entrega consistira en un archivo `.zip` que incluira la siguiente estructura:

- `README.pdf`: El presente documento en formato PDF.
- `[diagramas]`: Archivos de imagen de los diagramas solicitados, a la misma altura que el PDF.
- `src/`: Una carpeta que contendra unicamente los archivos fuente `.java` de la implementacion.

**Nota:** Es requisito respetar este formato de entrega para la evaluacion del proyecto.

---

## Compilacion y Ejecucion

Este proyecto utiliza **Apache Maven** para gestionar su ciclo de vida y dependencias. El uso de Maven asegura que el entorno de compilacion y ejecucion sea consistente y automatizado.

### Requisitos

- **Java Development Kit (JDK):** Version 21 o superior.
- **Apache Maven:** Version 3.9 o superior.

### Dependencias Externas

La principal dependencia externa del proyecto es **JavaFX**, utilizada para la interfaz grafica de usuario (GUI). Maven se encarga de descargar y enlazar esta biblioteca automaticamente durante el proceso de compilacion.

### Comandos de Ejecucion

Los siguientes comandos deben ejecutarse desde la raiz del proyecto (la carpeta que contiene el archivo `pom.xml`). Estos comandos son compatibles con Windows, Linux y macOS.

**1. Para compilar el proyecto:**

Este comando compila todo el codigo fuente.

```bash
mvn compile
```

**2. Para ejecutar la aplicacion:**

Este comando inicia la aplicacion y muestra la interfaz grafica.

```bash
mvn exec:java
```

---

**Nota para el evaluador:** Se ha optado por usar Maven para la gestion del proyecto, ya que este utiliza la biblioteca externa JavaFX. Maven proporciona una forma estandarizada y multiplataforma de compilar y ejecutar el codigo, cumpliendo con el requisito de indicar como manejar las bibliotecas adicionales.
