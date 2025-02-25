import java.util.*;

public class ElvisGraph3 {
    private int ciudades;
    private Map<String, List<Nodo>> listaAdyacente;
    private Map<String, Integer> indiceCiudades;
    private Map<Integer, String> nombreCiudades;

    static class Nodo {
        String ciudad;
        int costo;

        public Nodo(String ciudad, int costo) {
            this.ciudad = ciudad;
            this.costo = costo;
        }
    }

    public ElvisGraph3(int ciudades) {
        this.ciudades = ciudades;
        listaAdyacente = new HashMap<>();
        indiceCiudades = new HashMap<>();
        nombreCiudades = new HashMap<>();
    }

    public void agregarCiudad(String ciudad, int indice) {
        listaAdyacente.put(ciudad, new ArrayList<>());
        indiceCiudades.put(ciudad, indice);
        nombreCiudades.put(indice, ciudad);
    }

    public void agregarArista(String origen, String destino, int peso) {
        listaAdyacente.get(origen).add(new Nodo(destino, peso));
        listaAdyacente.get(destino).add(new Nodo(origen, peso));
    }

    public void tsp(String ciudadInicial) {
        System.out.println("** Ejecutando algoritmo del vendedor viajero (TSP) **");
        boolean[] visitadas = new boolean[ciudades];
        visitadas[indiceCiudades.get(ciudadInicial)] = true;
        List<String> ruta = new ArrayList<>();
        ruta.add(ciudadInicial);
        tspUtil(ciudadInicial, visitadas, ruta, 0, 0);
    }

    private void tspUtil(String ciudadActual, boolean[] visitadas, List<String> ruta, int contador, int costo) {
        if (contador == ciudades - 1) {
            System.out.println("Ruta: " + ruta + " Costo: " + costo);
            return;
        }

        for (Nodo vecino : listaAdyacente.get(ciudadActual)) {
            if (!visitadas[indiceCiudades.get(vecino.ciudad)]) {
                visitadas[indiceCiudades.get(vecino.ciudad)] = true;
                ruta.add(vecino.ciudad);
                tspUtil(vecino.ciudad, visitadas, ruta, contador + 1, costo + vecino.costo);
                ruta.remove(ruta.size() - 1);
                visitadas[indiceCiudades.get(vecino.ciudad)] = false;
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el número de ciudades: ");
        int ciudades = scanner.nextInt();
        scanner.nextLine(); // Consumir la nueva línea
        ElvisGraph3 grafo = new ElvisGraph3(ciudades);

        System.out.println("Ingrese los nombres de las ciudades:");
        for (int i = 0; i < ciudades; i++) {
            System.out.print("Ciudad " + (i + 1) + ": ");
            String nombreCiudad = scanner.nextLine();
            grafo.agregarCiudad(nombreCiudad, i);
        }

        System.out.println("Ingrese las distancias entre las ciudades (origen destino peso):");
        for (int i = 0; i < ciudades - 1; i++) {
            for (int j = i + 1; j < ciudades; j++) {
                System.out.print("Distancia entre " + grafo.nombreCiudades.get(i) + " y " + grafo.nombreCiudades.get(j) + ": ");
                int peso = scanner.nextInt();
                scanner.nextLine(); // Consumir la nueva línea
                grafo.agregarArista(grafo.nombreCiudades.get(i), grafo.nombreCiudades.get(j), peso);
            }
        }

        System.out.print("Ingrese la ciudad inicial: ");
        String ciudadInicial = scanner.nextLine();

        grafo.tsp(ciudadInicial);
        scanner.close();
    }
}
