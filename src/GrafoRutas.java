import java.util.*;

public class GrafoRutas {
    private int totalCiudades;
    private Map<String, List<Vertice>> mapaAdyacente;
    private Map<String, Integer> idCiudades;
    private Map<Integer, String> nombreCiudades;

    static class Vertice {
        String nombre;
        int costo;

        public Vertice(String nombre, int costo) {
            this.nombre = nombre;
            this.costo = costo;
        }
    } 

    public GrafoRutas(int totalCiudades) {
        this.totalCiudades = totalCiudades;
        mapaAdyacente = new HashMap<>();
        idCiudades = new HashMap<>();
        nombreCiudades = new HashMap<>();
    }

    public void agregarCiudad(String nombre, int id) {
        mapaAdyacente.put(nombre, new ArrayList<>());
        idCiudades.put(nombre, id);
        nombreCiudades.put(id, nombre);
    }

    public void agregarConexion(String origen, String destino, int peso) {
        mapaAdyacente.get(origen).add(new Vertice(destino, peso));
        mapaAdyacente.get(destino).add(new Vertice(origen, peso));
    }

    public void ejecutarFloydWarshall() {
        System.out.println("Ejecutando algoritmo de Floyd-Warshall ");
        int[][] distancias = new int[totalCiudades][totalCiudades];

        for (int i = 0; i < totalCiudades; i++) {
            for (int j = 0; j < totalCiudades; j++) {
                if (i == j) {
                    distancias[i][j] = 0;
                } else {
                    distancias[i][j] = Integer.MAX_VALUE;
                }
            }
        }

        for (String nombre : mapaAdyacente.keySet()) {
            int i = idCiudades.get(nombre);
            for (Vertice vecino : mapaAdyacente.get(nombre)) {
                int j = idCiudades.get(vecino.nombre);
                distancias[i][j] = vecino.costo;
            }
        }

        for (int k = 0; k < totalCiudades; k++) {
            for (int i = 0; i < totalCiudades; i++) {
                for (int j = 0; j < totalCiudades; j++) {
                    if (distancias[i][k] != Integer.MAX_VALUE && distancias[k][j] != Integer.MAX_VALUE) {
                        distancias[i][j] = Math.min(distancias[i][j], distancias[i][k] + distancias[k][j]);
                    }
                }
            }
        }

        System.out.println("Matriz de distancias mas cortas:");
        for (int i = 0; i < totalCiudades; i++) {
            for (int j = 0; j < totalCiudades; j++) {
                if (distancias[i][j] == Integer.MAX_VALUE) {
                    System.out.print("INF ");
                } else {
                    System.out.print(distancias[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el numero de ciudades: ");
        int totalCiudades = scanner.nextInt();
        scanner.nextLine(); 
        GrafoRutas grafo = new GrafoRutas(totalCiudades);

        System.out.println("Ingrese los nombres de las ciudades:");
        for (int i = 0; i < totalCiudades; i++) {
            System.out.print("Ciudad " + (i + 1) + ": ");
            String nombreCiudad = scanner.nextLine();
            grafo.agregarCiudad(nombreCiudad, i);
        }

        System.out.println("Ingrese las distancias entre las ciudades :");
        for (int i = 0; i < totalCiudades - 1; i++) {
            for (int j = i + 1; j < totalCiudades; j++) {
                System.out.print("Distancia entre " + grafo.nombreCiudades.get(i) + " y " + grafo.nombreCiudades.get(j) + ": ");
                int peso = scanner.nextInt();
                scanner.nextLine(); 
                grafo.agregarConexion(grafo.nombreCiudades.get(i), grafo.nombreCiudades.get(j), peso);
            }
        }

        grafo.ejecutarFloydWarshall();
        scanner.close();
    }
}
