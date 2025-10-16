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

Este proyecto es una aplicación de Java estándar que **debe ejecutarse en una Terminal Ubuntu** compatible con ANSI.

### Requisitos del Sistema

* **Java Development Kit (JDK) 8 o superior.** (Necesario para compilar y ejecutar)
* **Terminal Ubuntu (WSL)**: Es el entorno recomendado y necesario para la correcta ejecución y el manejo de rutas.
* Una **Terminal** o **Consola** compatible con códigos de escape **ANSI** (colores y control de cursor).
    * *Sistemas compatibles:* **Terminal Ubuntu (WSL)**, Git Bash, Linux/macOS Terminal.

### ⚙️ Compilación y Ejecución

**¡IMPORTANTE! Todos los comandos deben ejecutarse en la Terminal Ubuntu.**

1.  **Guarda el Código y Estructura de Archivos:**
    * Guarda el código fuente en un archivo llamado `Serpiente.java`.
    * **Crea la carpeta de niveles:** El código espera encontrar niveles en el directorio `niveles/`. Si solo ejecutas el juego sin crear los archivos de nivel (`nivel1.txt`, `nivel2.txt`, etc.), los niveles se cargarán vacíos.
        > *Recomendación:* Crea la carpeta `niveles` en el mismo directorio que `Serpiente.java`.

2.  **Navega al Directorio del Proyecto:**
    Usa el siguiente comando para acceder a la carpeta donde se encuentra `Serpiente.java`:
    ```bash
    cd /mnt/c/Users/alvlo/OneDrive/Escritorio/2º\ DAW/Programacion/1º\ Trimestre/TEMA\ 3/Ejercicios/JuegoSerpiente
    ```

3.  **Compila el Código:**
    Estando ya en la carpeta, ejecuta el compilador de Java:
    ```bash
    javac Serpiente.java
    ```

4.  **Ejecuta el Juego:**
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
| **RECORDS** | Muestra el listado de las mejores puntuaciones guardadas en `records/record.txt`, ordenadas por nivel y puntos (descendente). |
| **BORRAR RECORDS** | Elimina permanentemente todos los registros del archivo de puntuaciones. |
| **ACTIVAR/DESACTIVAR SONIDO** | Opción con funcionalidad *pendiente* (placeholder). |
| **SALIR** | Cierra la aplicación de la terminal. |

### 🕹️ Controles de la Partida

Utiliza estas teclas para el control dinámico una vez que la partida ha comenzado:

| Tecla | Acción |
| :--- | :--- |
| **W** | Mover Arriba |
| **A** | Mover Izquierda |
| **S** | Mover Abajo |
| **D** | Mover Derecha |
| **P** | **Pausar** / Continuar la partida (muestra pantalla de pausa) |
| **Q** | **Salir** de la partida (guardando tu puntuación) |

---

## 💾 Estructura y Gestión de Datos

El juego crea una carpeta `records` en el directorio de ejecución para almacenar el historial de puntuaciones.

* **Archivo de Récords:** `records/record.txt`
* **Formato de Guardado:** Cada línea del archivo sigue el formato:
    `[Nombre del jugador] ---> Nivel [X] ([Y] puntos)`
* **Archivos de Nivel:** El juego espera que los diseños de los niveles estén en `niveles/nivelX.txt`, donde `X` es el número de nivel. El carácter **`▓`** define un obstáculo.