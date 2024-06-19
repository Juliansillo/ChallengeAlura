import java.io.IOException;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        Scanner lectura = new Scanner(System.in);
        ConsultaTasaCambio consulta = new ConsultaTasaCambio();

        System.out.println("Sea bienvenido al conversor de moneda!");

        try {
            TasaCambio tasaCambio = consulta.obtenerTasasDeCambio();

            int opcion = 0;
            while (opcion != 7) {
                System.out.println("""
                    *****************************************
                    Elija una opción para convertir moneda:
                    
                    1) Dolar =>> Peso argentino               
                    2) Peso argentino =>> Dolar
                    3) Dolar =>> Real brasileño
                    4) Real brasileño =>> Dolar
                    5) Dolar =>> Peso colombiano
                    6) Peso colombiano =>> Dolar
                    7) Salir
                    *****************************************
                    """);
                System.out.print("Por favor, elija una opción: ");

                try {
                    opcion = Integer.parseInt(lectura.nextLine());

                    if (opcion >= 1 && opcion <= 6) {
                        System.out.print("Ingrese la cantidad en moneda de origen: ");
                        double monto = Double.parseDouble(lectura.nextLine());
                        double resultado = realizarConversion(opcion, monto, tasaCambio);
                        System.out.println("Monto convertido: " + resultado);
                    } else if (opcion == 7) {
                        System.out.println("Finalizando el programa. Muchas gracias por usar nuestros servicios.");
                    } else {
                        System.out.println("Opción inválida. Por favor, intente de nuevo.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Entrada inválida. Por favor, ingrese un número válido.");
                }
            }
        } catch (IOException e) {
            System.out.println("Error al obtener las tasas de cambio: " + e.getMessage());
        } finally {
            lectura.close();
        }
    }

    private static double realizarConversion(int opcion, double monto, TasaCambio tasaCambio) {
        return switch (opcion) {
            case 1 -> monto * tasaCambio.tasaDolarAPesoArgentino();
            case 2 -> monto / tasaCambio.tasaDolarAPesoArgentino();
            case 3 -> monto * tasaCambio.tasaDolarARealBrasileno();
            case 4 -> monto / tasaCambio.tasaDolarARealBrasileno();
            case 5 -> monto * tasaCambio.tasaDolarAPesoColombiano();
            case 6 -> monto / tasaCambio.tasaDolarAPesoColombiano();
            default -> 0;
        };
    }
}
