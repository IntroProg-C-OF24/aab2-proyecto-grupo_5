package javaapplication49;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final String ARCHIVO_CSV = "presos.csv";
    private static Map<String, String> usuarios = new HashMap<>();

    public static void main(String[] args) {
        cargarUsuarios();
        iniciarSesion();
    }

    private static void cargarUsuarios() {
        try (BufferedReader br = new BufferedReader(new FileReader("usuarios.csv"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                usuarios.put(datos[0], datos[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void iniciarSesion() {
        Scanner scanner = new Scanner(System.in);
        boolean inicioSesionExitoso = false;
        while (!inicioSesionExitoso) {
            System.out.print("Ingrese el usuario: ");
            String usuario = scanner.nextLine();
            System.out.print("Ingrese la contraseña: ");
            String contraseña = scanner.nextLine();

            if (usuarios.containsKey(usuario) && usuarios.get(usuario).equals(contraseña)) {
                System.out.println("Inicio de sesión exitoso.");
                inicioSesionExitoso = true;
                mostrarMenu(scanner); // Pasa el Scanner como parámetro
            } else {
                System.out.println("Usuario o contraseña incorrectos. Por favor, inténtelo de nuevo.");
            }
        }
    }

    private static void mostrarMenu(Scanner scanner) { // Recibe el Scanner como parámetro
        boolean salir = false;
        while (!salir) {
            System.out.println("\nMenú:");
            System.out.println("1. Mostrar presos y sus datos");
            System.out.println("2. Ingresar nuevo preso");
            System.out.println("3. Asignación de agravantes");
            System.out.println("4. Salir");

            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer del scanner

            switch (opcion) {
                case 1:
                    mostrarPresos(scanner); // Pasa el Scanner como parámetro
                    break;
                case 2:
                    ingresarNuevoPreso(scanner); // Pasa el Scanner como parámetro
                    break;
                case 3:
                    asignarAgravantes();
                    break;
                case 4:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, seleccione una opción válida.");
            }
        }
        scanner.close(); // Cierra el Scanner al salir del bucle
    }

    private static void mostrarPresos(Scanner scanner) { // Recibe el Scanner como parámetro
        boolean salir = false;
        while (!salir) {
            System.out.println("\nMenú de Presentación de Presos:");
            System.out.println("1. Presentar presos de la cárcel");
            System.out.println("2. Presentar pabellones");

            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer del scanner

            switch (opcion) {
                case 1:
                    presentarPresosGenerales();
                    break;
                case 2:
                    presentarPabellones(scanner);
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, seleccione una opción válida.");
            }
            System.out.println("\n¿Desea volver al menú principal? (s/n)");
            String respuesta = scanner.nextLine();
            if (!respuesta.equalsIgnoreCase("s")) {
                salir = true;
            }
        }
    }

    private static void presentarPresosGenerales() {
        System.out.println("\nPresos de la cárcel:");
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_CSV))) {
            String linea;
            System.out.printf("%-15s %-5s %-15s %-20s %-20s %-15s %-15s %-15s%n", "Preso", "Edad", "Delito", "Fecha de ingreso", "Gravedad del delito", "Tiempo de condena", "Días de visita", "Castigos");
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length >= 8) {
                    System.out.printf("%-15s %-5s %-15s %-20s %-20s %-15s %-15s %-15s%n", datos[0], datos[1], datos[2], datos[3], datos[4], datos[5], datos[6], datos[7]);
                } else {
                    System.out.println("Error: formato incorrecto en el archivo de presos.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void presentarPabellones(Scanner scanner) {
        boolean salir = false;
        while (!salir) {
            System.out.println("\nMenú de Presentación de Pabellones:");
            System.out.println("A. Alta gravedad");
            System.out.println("B. Media gravedad");
            System.out.println("C. Baja gravedad");

            System.out.print("Seleccione una opción: ");
            String opcion = scanner.nextLine();

            switch (opcion.toUpperCase()) {
                case "A":
                    presentarPabellon("Alta");
                    break;
                case "B":
                    presentarPabellon("Media");
                    break;
                case "C":
                    presentarPabellon("Baja");
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, seleccione una opción válida.");
                    continue;
            }
            System.out.println("\n¿Desea volver al menú de presentación de pabellones? (s/n)");
            String respuesta = scanner.nextLine();
            if (!respuesta.equalsIgnoreCase("s")) {
                salir = true;
            }
        }
    }

    private static void presentarPabellon(String gravedad) {
        System.out.printf("\nPresos con gravedad '%s':%n", gravedad);
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_CSV))) {
            String linea;
            System.out.printf("%-15s %-5s %-15s %-20s %-20s %-15s%n", "Preso", "Edad", "Delito", "Fecha de ingreso", "Gravedad del delito", "Tiempo de condena");
                while ((linea = br.readLine()) != null) {
                    String[] datos = linea.split(",");
                    if (datos[4].equalsIgnoreCase(gravedad)) {
                        System.out.printf("%-15s %-5s %-15s %-20s %-20s %-15s%n", datos[0], datos[1], datos[2], datos[3], datos[4], datos[5]);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private static void ingresarNuevoPreso(Scanner scanner) {
            System.out.println("\nIngresar nuevo preso:");
            try (FileWriter fw = new FileWriter(ARCHIVO_CSV, true)) {
                System.out.print("Ingrese el nombre del preso: ");
                String nombre = scanner.nextLine();
                System.out.print("Ingrese la edad del preso: ");
                String edad = scanner.nextLine();
                System.out.print("Ingrese el delito cometido: ");
                String delito = scanner.nextLine();
                LocalDate fechaIngreso = LocalDate.now(); // Obtenemos la fecha actual del sistema
                System.out.print("Ingrese la gravedad del delito: ");
                String gravedadDelito = scanner.nextLine();
                System.out.print("Ingrese el tiempo de condena del preso: ");
                String tiempoCondena = scanner.nextLine();
                System.out.print("Ingrese los días de visita del preso: ");
                String diasVisita = scanner.nextLine();
                System.out.print("Ingrese los castigos del preso: ");
                String castigos = scanner.nextLine();

                String nuevoPreso = String.format("%s,%s,%s,%s,%s,%s,%s,%s", nombre, edad, delito, fechaIngreso, gravedadDelito, tiempoCondena, diasVisita, castigos);
                fw.write(nuevoPreso + "\n");
                System.out.println("Preso ingresado correctamente.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private static void asignarAgravantes() {
            System.out.println("\nAsignación de agravantes:");
            try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_CSV))) {
                String linea;
                System.out.printf("%-15s %-5s %-15s %-20s %-20s %-15s %-15s %-15s%n", "Preso", "Edad", "Delito", "Fecha de ingreso", "Gravedad del delito", "Tiempo de condena", "Días de visita", "Castigos");
                while ((linea = br.readLine()) != null) {
                    String[] datos = linea.split(",");
                    if (datos.length >= 8) { // Aseguramos que haya suficientes campos
                        String nombrePreso = datos[0];
                        String edad = datos[1];
                        String delito = datos[2];
                        String fechaIngreso = datos[3];
                        String gravedadDelito = datos[4];
                        String tiempoCondena = datos[5];
                        String diasVisita = datos[6];
                        String castigos = datos[7];
                        System.out.printf("%-15s %-5s %-15s %-20s %-20s %-15s %-15s %-15s%n", nombrePreso, edad, delito, fechaIngreso, gravedadDelito, tiempoCondena, diasVisita, castigos);
                    } else {
                        System.out.println("Error: formato incorrecto en el archivo de presos.");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
