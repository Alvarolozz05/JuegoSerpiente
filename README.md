# 🐍 Java Terminal Snake Game (Juego de la Serpiente en Terminal) 🚀

Un juego del clásico "Snake" implementado en **Java**, diseñado para ejecutarse directamente en la terminal (línea de comandos). Utiliza caracteres de escape **ANSI** para ofrecer una interfaz de usuario a color vibrante y un manejo de teclado en tiempo real para un control de juego dinámico.

---

## 💡 Aspectos Destacados

* **Juego Clásico y Adictivo:** Mueve la serpiente para comer la comida (`*` roja), hazla crecer y evita colisionar.
* **Interfaz de Consola a Color:** Implementación con códigos **ANSI** para una visualización colorida:
    * Serpiente: **`0` (verde)** 🟢
    * Comida: **`*` (rojo)** 🍎
    * Obstáculos: **`▓` (gris)** 🧱
* **Sistema de Multi-Nivel y Dificultad Progresiva:**
    * El juego introduce **obstáculos** y aumenta la **velocidad** (dificultad) a medida que el jugador alcanza ciertas puntuaciones (5, 10, 20 puntos).
    * Los niveles se cargan desde archivos externos (`niveles/nivelX.txt`).
* **Gestión de Récords Persistente:** Las puntuaciones más altas (ordenadas por **Nivel** y luego por **Puntos** descendentes) se guardan, leen y gestionan en un archivo local (`records/record.txt`).
* **Menú Interactivo en Terminal:** Navegación fluida en el menú principal con flechas ($\uparrow$/$\downarrow$) y selección con `ENTER`, utilizando un componente Swing invisible para una captura de teclas eficiente.
* **Controles Intuitivos:** Soporte completo para los controles direccionales **W-A-S-D**, además de Pausa (`P`) y Salir (`Q`).

---

## 🛠️ Instalación y Requisitos

Este proyecto es una aplicación de Java estándar que **debe ejecutarse en una Terminal** compatible con ANSI.

### Requisitos del Sistema

* **Java Development Kit (JDK) 8 o superior.** (Necesario para compilar y ejecutar)
* **Terminal Compatible con ANSI:** Necesario para visualizar correctamente los colores y el control del cursor.
    * *Sistemas recomendados:* **Terminal Ubuntu (WSL)**, Git Bash, Linux/macOS Terminal.

### ⚙️ Compilación y Ejecución

**¡IMPORTANTE! Asegúrate de estar en el directorio correcto en tu terminal.**

1. **Guarda el Código y Estructura de Archivos:**
    * Guarda el código fuente en un archivo llamado `Serpiente.java`.
    * **Crea la estructura de carpetas** (`records/` y `niveles/`) tal como se detalla más abajo, incluyendo al menos un `nivel1.txt` para que el primer cambio de nivel funcione correctamente con obstáculos.

2. **Navega al Directorio del Proyecto:**
    ```bash
    cd /mtd/ruta/a/tu/proyecto/JuegoSerpiente
    ```
    *(Ejemplo de tu entorno: `cd /mnt/c/Users/alvlo/OneDrive/Escritorio/JuegoSerpiente`)*

3. **Compila el Código:**
    Estando ya en la carpeta, ejecuta el compilador de Java:
    ```bash
    javac Serpiente.java
    ```

4. **Ejecuta el Juego:**
    Ejecuta la clase principal para iniciar el menú:
    ```bash
    java Serpiente
    ```

---

## 🎮 Uso del Juego

### ➡️ Menú Principal
Usa las flechas ($\uparrow$ / $\downarrow$) para moverte y `ENTER` para seleccionar una opción.

| Opción | Descripción |
| :--- | :--- |
| **JUGAR** | Inicia una nueva partida de Snake. |
| **VER INSTRUCCIONES** | Muestra los controles y reglas del juego. |
| **RECORDS** | Muestra el listado de las mejores puntuaciones guardadas, ordenadas por nivel y puntos (descendente). |
| **BORRAR RECORDS** | Elimina permanentemente todos los registros del archivo de puntuaciones. |
| **ACTIVAR/DESACTIVAR SONIDO** | Opción con funcionalidad *pendiente*. |
| **SALIR** | Cierra la aplicación. |

### 🕹️ Controles de la Partida

Utiliza estas teclas para el control dinámico una vez que la partida ha comenzado:

| Tecla | Acción |
| :--- | :--- |
| **W** | Mover Arriba |
| **A** | Mover Izquierda |
| **S** | Mover Abajo |
| **D** | Mover Derecha |
| **P** | **Pausar** / Continuar la partida |
| **Q** | **Salir** de la partida (guardando tu puntuación) |

---

## 💾 Estructura y Gestión de Datos

Para que el juego funcione correctamente, la siguiente estructura es necesaria:
```
.
├── Serpiente.java      # Archivo principal del juego
├── records/            # Directorio para guardar el ranking
│   └── record.txt      # Archivo de récords (se crea automáticamente)
└── niveles/            # Directorio para los archivos de niveles
    ├── nivel1.txt      # Obstáculos para el Nivel 1
    ├── nivel2.txt      # Obstáculos para el Nivel 2
    └── ...             # Más niveles (ejemplos a continuación)
```

### Ejemplo de Archivo de Nivel (`niveles/nivel1.txt`)

Los archivos de nivel deben tener el mismo alto (`15`) y ancho (`20`) que el tablero definido en el código.
* Los espacios (` `) son zona libre.
* El carácter **`▓`** (sombreado gris) representa un obstáculo fijo.
* El carácter **`#`** se usa para el borde (aunque el código lo gestiona automáticamente, puede usarse para muros adicionales).

```text
####################
#                  #
#                  #
#   ▓▓▓▓▓▓▓        #
#                  #
#                  #
#                  #
#                  #
#                  #
#                  #
#                  #
#                  #
#                  #
#                  #
####################
```
---

## 🗒️ Notas Técnicas

* La **captura de teclado en tiempo real** en la terminal (sin necesidad de presionar `Enter` en cada movimiento) se logra mediante la creación de una ventana invisible de **`javax.swing.JFrame`** que actúa como *Key Listener*.
* Los **colores** y el control de la posición del cursor (`\033[...`) requieren una terminal compatible con **códigos de escape ANSI** para funcionar visualmente como se espera.
* La funcionalidad de **Sonido** en el menú está marcada como *pendiente* (`placeholder`) en la implementación actual.
