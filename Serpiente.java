import java.util.Scanner;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Serpiente {
    // Códigos de colores ANSI
    public static final String ANSI_RESET = "\u001B[0m"; // Resetear color
    public static final String ANSI_GREEN = "\u001B[32m"; // Color verde
    public static final String ANSI_RED = "\u001B[31m"; // Color rojo
    public static final String ANSI_YELLOW = "\u001B[33m"; // Color amarillo
    public static final String ANSI_GRAY = "\u001B[90m"; // Color gris

    public static void main(String[] args) {
        // Llamamos al método del menú principal
        menu();
    }

    // Menu principal
    public static void menu() {
        final String[] opciones = {
            ANSI_GREEN + "【 🎮  JUGAR 】" + ANSI_RESET,
            ANSI_YELLOW + "【 🗒️  VER INSTRUCCIONES 】" + ANSI_RESET,
            ANSI_GREEN + "【 🏆  RECORDS 】" + ANSI_RESET,
            ANSI_RED + "【 🧹  BORRAR RECORDS 】" + ANSI_RESET,
            ANSI_YELLOW + "【 🔊  ACTIVAR/DESACTIVAR SONIDO 】" + ANSI_RESET,
            ANSI_RED + "【 ❌  SALIR 】" + ANSI_RESET
        };

        final int[] seleccion= {0}; // Índice de opción seleccionada
        final boolean[] ejecutando = {true};
        final boolean[] confirmado = {false};

        // Ventana invisible para capturar teclas
        javax.swing.JFrame ventana = new javax.swing.JFrame();
        ventana.setUndecorated(true);
        ventana.setSize(0, 0);
        ventana.setFocusable(true);
        ventana.setVisible(true);
        ventana.requestFocus();


        // Añadir listener para capturar teclas de flecha y Enter
        ventana.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                int key = e.getKeyCode();
                switch (key) {
                    case java.awt.event.KeyEvent.VK_UP: // Flecha arriba
                        seleccion[0] = (seleccion[0] - 1 + opciones.length) % opciones.length;
                        break;
                    case java.awt.event.KeyEvent.VK_DOWN: // Flecha abajo
                        seleccion[0] = (seleccion[0] + 1) % opciones.length;
                        break;
                    case java.awt.event.KeyEvent.VK_ENTER: // Enter
                        confirmado[0] = true;
                        ejecutando[0] = false;
                        break;
                }
                mostrarMenu(opciones, seleccion[0]); // Redibujamos el menú
            }
        });

        // Mostrar primer menu
        mostrarMenu(opciones, seleccion[0]);

        // Esperar hasta que el usuario presione Enter
        while (ejecutando[0]) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Cerrar ventana de captura
        ventana.dispose();

        int opcion = seleccion[0] + 1;

        switch (opcion) {
            case 1:
                jugar(); // Iniciar juego
                break;
            case 2:
                verInstrucciones(); // Ver instrucciones
                break;
            case 3:
                verRecords(); // Ver records
                break;
            case 4:
                borrarRecords(); // Borrar records
                break;
            case 5:
                alternarSonido(); // Alternar sonido
                break;
            case 6:
                System.out.println("Gracias por jugar 🐍 ¡Hasta pronto!"); // Salir del juego
                System.exit(0);
                break;
        }

        // Esperar ENTER para volver al menú
        System.out.println("Presiona ENTER para volver al menú...");
        new Scanner(System.in).nextLine();

        menu(); // Volver a mostrar el menú
    }


    // Método para mostrar el menú con la opción seleccionada
    public static void mostrarMenu(String[] opciones, int seleccionada) {
        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.println(
            "---------------------------------------------------\n" +
            "           🐍  JUEGO DE LA SERPIENTE  🐍          \n" +
            "---------------------------------------------------\n"
        );

        for (int i = 0; i < opciones.length; i++) {
            if (i == seleccionada) {
                // Resaltar la opción seleccionada
                System.out.println(ANSI_YELLOW + " ➤ " + opciones[i] + ANSI_RESET);
            } else {
                // Opción normal
                System.out.println("  " + opciones[i]);
            }
        }

        System.out.println("---------------------------------------------------");
        System.out.println(
            "---------------------------------------------------\n" +
            "         🎯 CONTROLES DEL MENÚ PRINCIPAL 🎯        \n" +
            "---------------------------------------------------\n" +
            "  " + ANSI_YELLOW + "↑" + ANSI_RESET + " y " + ANSI_YELLOW + "↓" + ANSI_RESET + " para moverte entre opciones\n" +
            "  " + ANSI_GREEN + "ENTER" + ANSI_RESET + " para seleccionar\n" +
            "---------------------------------------------------"
        );
    }

    // Juego de la serpiente
    public static void jugar() {
        System.out.print("\033[?25l"); // Ocultar cursor
        System.out.flush();

        int nivel = 0;

        final int ALTO = 15;
        final int ANCHO = 20;

        String[][] matriz = new String[ALTO][ANCHO];
        String serpiente = ANSI_GREEN + "0" + ANSI_RESET;
        String comida = ANSI_RED + "*" + ANSI_RESET;
        int puntos = 0;

        int[] cuerpoFila = new int[200];
        int[] cuerpoColumna = new int[200];
        int longitud = 1;

        // Inicializar matriz vacía
        for (int fila = 0; fila < ALTO; fila++) {
            for (int col = 0; col < ANCHO; col++) {
                matriz[fila][col] = " ";
            }
        }

        // Posición inicial
        cuerpoFila[0] = ALTO / 2;
        cuerpoColumna[0] = ANCHO / 2;

        // Comida inicial
        int comidaFila = (int) (Math.random() * (ALTO - 2)) + 1;
        int comidaCol = (int) (Math.random() * (ANCHO - 2)) + 1;
        matriz[comidaFila][comidaCol] = comida;

        // Dirección inicial
        final char[] direccionHolder = new char[]{'D'};
        final boolean[] jugando = new boolean[]{true};
        final boolean[] enPausa = new boolean[]{false};

        // Ventana oculta que captura las teclas en tiempo real
        javax.swing.JFrame capturador = new javax.swing.JFrame(); // Crear ventana invisible
        capturador.setUndecorated(true); // Sin bordes
        capturador.setSize(0, 0); // Tamaño 0x0 para que no se vea
        capturador.setFocusable(true); // Puede recibir foco
        capturador.setVisible(true); // Hacerla visible para capturar teclas
        capturador.requestFocus(); // Pedir foco

        // Añadir listener para capturar teclas
        capturador.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                char tecla = Character.toUpperCase(e.getKeyChar());
                // Cambiar dirección o salir
                if ((tecla == 'W') || (tecla == 'A') || (tecla == 'S') || (tecla == 'D')) {
                    direccionHolder[0] = tecla;
                } else if (tecla == 'Q') {
                    jugando[0] = false;
                } else if (tecla == 'P') {
                    enPausa[0] = !enPausa[0];
                }
            }
        });

        // Bucle principal de juego
        while (jugando[0]) {
            // Calcular nueva posición de la cabeza
            int nuevaFila = cuerpoFila[0];
            int nuevaCol = cuerpoColumna[0];

            switch (direccionHolder[0]) {
                case 'W': nuevaFila--;
                    break;
                case 'S': nuevaFila++;
                    break;
                case 'A': nuevaCol--;
                    break;
                case 'D': nuevaCol++;
                    break;
            }

            if (enPausa[0]) {
                System.out.print("\033[H\033[2J");
                System.out.flush();
                System.out.println(
                    "---------------------------------------------------\n" +
                    "                ⏸️  JUEGO EN PAUSA  ⏸️              \n" +
                    "---------------------------------------------------\n" +
                    "  " + ANSI_YELLOW + "Pulsa P" + ANSI_RESET + " para continuar...\n" +
                    "  " + ANSI_RED + "Q" + ANSI_RESET + " para salir\n" +
                    "---------------------------------------------------"
                );
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                continue; // vuelve al inicio del bucle sin mover la serpiente
            }

            // Comprobar colisiones
            if ((nuevaFila <= 0) || (nuevaFila >= ALTO - 1) || (nuevaCol <= 0) || (nuevaCol >= ANCHO - 1)) {
                System.out.println(
                    "---------------------------------------------------\n" +
                    ANSI_RED + "     💥  ¡HAS CHOCADO CONTRA UNA PARED!  💥" + ANSI_RESET + "\n" +
                    "---------------------------------------------------\n" +
                    ANSI_YELLOW + "           Puntuación final: " + puntos + ANSI_RESET + "\n"
                );
                jugando[0] = false;
            }

            // Comprobar si se muerde
            for (int i = 0; i < longitud; i++) {
                if (cuerpoFila[i] == nuevaFila && cuerpoColumna[i] == nuevaCol) {
                    System.out.println(
                        "---------------------------------------------------\n" +
                        ANSI_RED + "     💀  ¡TE HAS MORDIDO!  💀" + ANSI_RESET + "\n" +
                        "---------------------------------------------------\n" +
                        ANSI_YELLOW + "           Puntuación final: " + puntos + ANSI_RESET + "\n"
                    );
                    jugando[0] = false;
                }
            }

            // Comprobar si choca con un obstáculo
            if (matriz[nuevaFila][nuevaCol].contains("▓")) {
                System.out.println(
                    "---------------------------------------------------\n" +
                    ANSI_RED + "     💥  ¡HAS CHOCADO CON UN OBSTÁCULO!  💥" + ANSI_RESET + "\n" +
                    "---------------------------------------------------\n" +
                    ANSI_YELLOW + "           Puntuación final: " + puntos + ANSI_RESET + "\n"
                );
                jugando[0] = false;
            }

            if (!jugando[0]) {
                break;
            }

            // Comer comida
            if ((nuevaFila == comidaFila) && (nuevaCol == comidaCol)) {
                puntos++;
                longitud++;
                // Nueva comida
                do {
                    comidaFila = (int) (Math.random() * (ALTO - 2)) + 1;
                    comidaCol = (int) (Math.random() * (ANCHO - 2)) + 1;
                } while (!matriz[comidaFila][comidaCol].equals(" "));
            } else {
                // Borrar cola
                matriz[cuerpoFila[longitud - 1]][cuerpoColumna[longitud - 1]] = " ";
            }

            // Subir de nivel automáticamente
            if (puntos >= 5 && nivel == 0) {
                nivel++;
                cambiarNivel(matriz, nivel, cuerpoFila, cuerpoColumna, longitud, comidaFila, comidaCol);
                // Reiniciar serpiente
                longitud = 1;
                cuerpoFila[0] = ALTO / 2;
                cuerpoColumna[0] = ANCHO / 2;
            } else if (puntos >= 10 && nivel == 1) {
                nivel++;
                cambiarNivel(matriz, nivel, cuerpoFila, cuerpoColumna, longitud, comidaFila, comidaCol);
                longitud = 1;
                cuerpoFila[0] = ALTO / 2;
                cuerpoColumna[0] = ANCHO / 2;
            } else if (puntos >= 20 && nivel == 2) {
                nivel++;
                cambiarNivel(matriz, nivel, cuerpoFila, cuerpoColumna, longitud, comidaFila, comidaCol);
                longitud = 1;
                cuerpoFila[0] = ALTO / 2;
                cuerpoColumna[0] = ANCHO / 2;
            }

            // Mover cuerpo
            for (int i = longitud - 1; i > 0; i--) {
                cuerpoFila[i] = cuerpoFila[i - 1];
                cuerpoColumna[i] = cuerpoColumna[i - 1];
            }
            cuerpoFila[0] = nuevaFila;
            cuerpoColumna[0] = nuevaCol;

            // Redibujar serpiente
            for (int i = 0; i < longitud; i++) {
                matriz[cuerpoFila[i]][cuerpoColumna[i]] = serpiente;
            }
            matriz[comidaFila][comidaCol] = comida;

            // Limpiar pantalla y pintar
            System.out.print("\033[H\033[2J");
            System.out.flush();
            
            pintarTablero(matriz);
            System.out.println(
                "---------------------------------------------------\n" +
                "              📊  ESTADO DE LA PARTIDA  📊          \n" +
                "---------------------------------------------------\n" +
                "  " + ANSI_YELLOW + "Nivel: " + ANSI_RESET + ANSI_GREEN + nivel + ANSI_RESET +
                "   |   " + ANSI_YELLOW + "Puntos: " + ANSI_RESET + ANSI_GREEN + puntos + ANSI_RESET +
                "   |   " + ANSI_YELLOW + "Longitud: " + ANSI_RESET + ANSI_GREEN + longitud + ANSI_RESET + "\n" +
                "---------------------------------------------------"
            );
            System.out.println(
                "---------------------------------------------------\n" +
                "                🎮  CONTROLES DEL JUEGO  🎮         \n" +
                "---------------------------------------------------\n" +
                "  " + ANSI_YELLOW + "W" + ANSI_RESET + " Arriba   " + ANSI_YELLOW + "S" + ANSI_RESET + " Abajo   " + ANSI_YELLOW + "A" + ANSI_RESET + " Izquierda   " + ANSI_YELLOW + "D" + ANSI_RESET + " Derecha\n" +
                "  " + ANSI_RED + "Q" + ANSI_RESET + " Salir    " + ANSI_YELLOW + "P" + ANSI_RESET + " Pausar/Continuar\n" +
                "---------------------------------------------------"
            );

            int velocidadBase = 200; // Velocidad base en ms
            int velocidadActual = Math.max(50, velocidadBase - (puntos * 10)); // Aumentar velocidad con puntos

            try {
                Thread.sleep(velocidadActual);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Cerrar ventana invisible
        capturador.dispose();

        System.out.print("\033[?25h"); // Mostrar cursor
        System.out.flush();

        // Pedir nombre siempre, incluso si el juego terminó por choque
        System.out.println(
            "---------------------------------------------------\n" +
            "             🏷️  GUARDAR PUNTUACIÓN  🏷️            \n" +
            "---------------------------------------------------\n" +
            "  " + ANSI_YELLOW + "Introduce tu nombre" + ANSI_RESET + " para guardar la puntuación.\n" +
            "  Deja vacío para usar " + ANSI_GREEN + "Anónimo" + ANSI_RESET + ".\n" +
            "---------------------------------------------------"
        );
        System.out.print("Nombre: ");
        String nombre;
        if (System.console() == null) {
            nombre = new java.util.Scanner(System.in).nextLine();
        } else {
            nombre = System.console().readLine();
        }
        if (nombre.isEmpty()) {
            nombre = "Anónimo";
        }
        guardarRecord(nombre, puntos, nivel);
    }

    // Instrucciones del juego
    public static void verInstrucciones() {
        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.println(
            "---------------------------------------------------\n" +
            "                🗒️  INSTRUCCIONES  🗒️              \n" +
            "---------------------------------------------------\n" +
            "  - Usa las teclas " + ANSI_YELLOW + "W" + ANSI_RESET + ", " + ANSI_YELLOW + "A" + ANSI_RESET + ", " + ANSI_YELLOW + "S" + ANSI_RESET + ", " + ANSI_YELLOW + "D" + ANSI_RESET + " para mover la serpiente.\n" +
            "  - Come la " + ANSI_RED + "*" + ANSI_RESET + " para ganar puntos y crecer.\n" +
            "  - Evita chocar contra las paredes " + ANSI_GRAY + "#" + ANSI_RESET + ", obstáculos " + ANSI_GRAY + "▓" + ANSI_RESET + " o tu propio cuerpo.\n" +
            "  - El juego termina si chocas o te muerdes.\n" +
            "  - ¡Supera niveles y consigue la mejor puntuación!\n" +
            "---------------------------------------------------"
        );
    }

    // Ver records
    public static void verRecords() {
        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.println(
            "---------------------------------------------------\n" +
            "                   🏆  RECORDS  🏆                  \n" +
            "---------------------------------------------------\n"
        );

        try {
            java.io.File recordsFile = new java.io.File("records/record.txt");
            java.io.File parent = recordsFile.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            if (!recordsFile.exists()) {
                recordsFile.createNewFile();
            }
            BufferedReader reader = new BufferedReader(new FileReader(recordsFile));
            String linea;
            ArrayList<String[]> listaRecords = new ArrayList<>();
            boolean tieneRecords = false;

            // Leer todos los records y extraer nombre, nivel y puntos
            while ((linea = reader.readLine()) != null) {
                // Formato esperado: "Nombre ---> Nivel X (Y puntos)"
                try {
                    String[] partes1 = linea.split("--->");
                    if (partes1.length == 2) {
                        String nombre = partes1[0].trim();

                        // Extraer "Nivel X (Y puntos)"
                        String resto = partes1[1].trim();
                        String nivelStr = resto.substring(resto.indexOf("Nivel") + 6, resto.indexOf("(") - 1).trim();
                        String puntosStr = resto.substring(resto.indexOf("(") + 1, resto.indexOf(" puntos)")).trim();

                        int nivel = Integer.parseInt(nivelStr);
                        int puntos = Integer.parseInt(puntosStr);

                        listaRecords.add(new String[]{nombre, String.valueOf(nivel), String.valueOf(puntos)});
                        tieneRecords = true;
                    }
                } catch (Exception e) {
                    System.out.println(
                        "---------------------------------------------------\n" +
                        "        🧾  ENTRADA DE RECORD NO VÁLIDA  🧾         \n" +
                        "---------------------------------------------------\n" +
                        "  " + ANSI_RED + "⚠️  Formato no reconocido." + ANSI_RESET + "\n" +
                        "  " + ANSI_YELLOW + "Línea: " + ANSI_RESET + linea + "\n" +
                        "---------------------------------------------------"
                    );
                }
            }

            reader.close();

            if (!tieneRecords) {
                System.out.println(
                    "---------------------------------------------------\n" +
                    "          📭  NO HAY RECORDS DISPONIBLES  📭        \n" +
                    "---------------------------------------------------\n" +
                    "  " + ANSI_RED + "🚫 No hay records guardados aún." + ANSI_RESET + "\n" +
                    "  " + ANSI_YELLOW + "Juega una partida" + ANSI_RESET + " para crear tu primer record.\n" +
                    "---------------------------------------------------"
                );
            } else {
                // Ordenar lista: primero por nivel, luego por puntos (ambos descendentes)
                Collections.sort(listaRecords, new Comparator<String[]>() {
                    @Override
                    public int compare(String[] r1, String[] r2) {
                        int nivel1 = Integer.parseInt(r1[1]);
                        int nivel2 = Integer.parseInt(r2[1]);
                        int puntos1 = Integer.parseInt(r1[2]);
                        int puntos2 = Integer.parseInt(r2[2]);
                        if (nivel2 != nivel1) {
                            return nivel2 - nivel1;
                        }
                        return puntos2 - puntos1;
                    }
                });

                int pos = 1;
                for (String[] record : listaRecords) {
                    System.out.println(
                        "  " + ANSI_GREEN + String.format("%2d", pos++) + ". " + ANSI_RESET +
                        ANSI_YELLOW + record[0] + ANSI_RESET +
                        "  --->  Nivel " + ANSI_GREEN + record[1] + ANSI_RESET +
                        " (" + ANSI_GREEN + record[2] + ANSI_RESET + " puntos)"
                    );
                }
            }
        } catch (Exception e) {
            System.out.println(
                "---------------------------------------------------\n" +
                "              🏆  LECTURA DE RECORDS  🏆            \n" +
                "---------------------------------------------------\n" +
                "  " + ANSI_RED + "⚠️  Error al leer los records." + ANSI_RESET + "\n" +
                "  " + ANSI_YELLOW + "Detalle: " + ANSI_RESET + e.getMessage() + "\n" +
                "---------------------------------------------------"
            );
        }

        System.out.println("---------------------------------------------------");
    }

    // Borrar records
    public static void borrarRecords() {
        try {
            java.io.File recordsFile = new java.io.File("records/record.txt");
            java.io.File parent = recordsFile.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(recordsFile));
            writer.write(""); // Sobrescribir con una cadena vacía
            writer.close();
            System.out.println(
                "---------------------------------------------------\n" +
                "             🧹  BORRADO DE RECORDS  🧹             \n" +
                "---------------------------------------------------\n" +
                "  " + ANSI_GREEN + "✅ Records borrados correctamente." + ANSI_RESET + "\n" +
                "---------------------------------------------------"
            );
        } catch (IOException e) {
            System.out.println(
                "---------------------------------------------------\n" +
                "             🧹  BORRADO DE RECORDS  🧹             \n" +
                "---------------------------------------------------\n" +
                "  " + ANSI_RED + "⚠️  Error al borrar los records." + ANSI_RESET + "\n" +
                "  " + ANSI_YELLOW + "Detalle: " + ANSI_RESET + e.getMessage() + "\n" +
                "---------------------------------------------------"
            );
        }
    }

    // Alternar sonido
    public static void alternarSonido() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        
        System.out.println(
            "---------------------------------------------------\n" +
            "            🔊  SONIDO DEL JUEGO  🔊              \n" +
            "---------------------------------------------------\n" +
            "\n" +
            "  " + ANSI_YELLOW + "Funcionalidad próximamente disponible." + ANSI_RESET + "\n" +
            "  Aquí podrás " + ANSI_GREEN + "activar" + ANSI_RESET + " o " + ANSI_RED + "desactivar" + ANSI_RESET + " el sonido del juego.\n" +
            "\n" +
            "---------------------------------------------------"
        );
    }
   
    // Método para guardar el record en un archivo
    public static void guardarRecord(String nombre, int puntos, int nivel) {
        try {
            java.io.File recordsFile = new java.io.File("records/record.txt");
            java.io.File parent = recordsFile.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(recordsFile, true));
            writer.write(nombre + " ---> Nivel " + nivel + " (" + puntos + " puntos)");
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            System.out.println("Error al guardar el record: " + e.getMessage());
        }

        System.out.println(ANSI_GREEN + "✅ Record guardado correctamente." + ANSI_RESET);
    }

    // Método para pintar el tablero
    public static void pintarTablero(String matriz[][]) {
        for (int fila = 0; fila < matriz.length; fila++) {
            for (int columna = 0; columna < matriz[0].length; columna++) {
                if ((fila == 0) || (fila == matriz.length - 1) || (columna == 0) || (columna == matriz[0].length - 1)) {
                    System.out.print('#');
                } else {
                    System.out.print(matriz[fila][columna]);
                }
            }
            System.out.println();
        }
    }

    // Método para cambiar de nivel (añade obstáculos según el nivel)
    public static void cambiarNivel(String[][] matriz, int nivel, int[] cuerpoFila, int[] cuerpoColumna, int longitud, int comidaFila, int comidaCol) {
        // Nombre del archivo según nivel
        String archivoNivel = "niveles/nivel" + nivel + ".txt";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(archivoNivel));
            String linea;
            int fila = 0;

            while ((linea = reader.readLine()) != null && fila < matriz.length) {
                for (int col = 0; col < Math.min(linea.length(), matriz[0].length); col++) {
                    char c = linea.charAt(col);
                    switch (c) {
                        case '#':
                            matriz[fila][col] = "#"; // Borde o muro
                            break;
                        case '▓':
                            matriz[fila][col] = ANSI_GRAY + "▓" + ANSI_RESET; // Obstáculo gris
                            break;
                        default:
                            matriz[fila][col] = " "; // Zona libre
                            break;
                    }
                }
                fila++;
            }

            reader.close();

        } catch (IOException e) {
            System.out.println("⚠️  Error al cargar el nivel " + nivel + ": " + e.getMessage());
            // En caso de error, dejar tablero vacío
            for (int f = 0; f < matriz.length; f++) {
                for (int c = 0; c < matriz[0].length; c++) {
                    matriz[f][c] = " ";
                }
            }
        }

        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println(
            "---------------------------------------------------\n" +
            ANSI_YELLOW + "           🌟  ¡HAS AVANZADO AL NIVEL " + nivel + "!  🌟" + ANSI_RESET + "\n" +
            "---------------------------------------------------\n"
        );
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}