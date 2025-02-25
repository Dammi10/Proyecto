import java.util.*;

public class ElvisGraph {
    private int ciudades;
    private Map<String, List<ListNodo>> listaAdyacente;
    private Map<String, Integer> indiceCiudades;
    private Map<Integer, String> nombreCiudades;

    static class ListNodo {
        String ciudad;
        int costo;

        public ListNodo(String ciudad, int costo) {
            this.ciudad = ciudad;
            this.costo = costo;
        }
    }

    public ElvisGraph(int ciudades) {
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
        listaAdyacente.get(origen).add(new ListNodo(destino, peso));
        listaAdyacente.get(destino).add(new ListNodo(origen, peso));
    }

    public void dijkstra(String ciudadInicial) {
        System.out.println("** Ejecutando el algoritmo de Dijkstra **");
        PriorityQueue<ListNodo> colaPrioridad = new PriorityQueue<>(Comparator.comparingInt(a -> a.costo));
        Map<String, Integer> distanciasMinimas = new HashMap<>();
        
        for (String ciudad : listaAdyacente.keySet()) {
            distanciasMinimas.put(ciudad, Integer.MAX_VALUE);
        }
        distanciasMinimas.put(ciudadInicial, 0);
        colaPrioridad.add(new ListNodo(ciudadInicial, 0));

        while (!colaPrioridad.isEmpty()) {
            ListNodo nodoActual = colaPrioridad.poll();
            String ciudadActual = nodoActual.ciudad;

            for (ListNodo vecino : listaAdyacente.get(ciudadActual)) {
                String ciudadVecina = vecino.ciudad;
                int pesoArista = vecino.costo;
                int nuevaDistancia = distanciasMinimas.get(ciudadActual) + pesoArista;

                if (nuevaDistancia < distanciasMinimas.get(ciudadVecina)) {
                    distanciasMinimas.put(ciudadVecina, nuevaDistancia);
                    colaPrioridad.add(new ListNodo(ciudadVecina, nuevaDistancia));
                }
            }
        }

        System.out.println("Distancias mínimas desde la ciudad " + ciudadInicial + ":");
        for (Map.Entry<String, Integer> entry : distanciasMinimas.entrySet()) {
            System.out.println("A la ciudad " + entry.getKey() + ": " + entry.getValue());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el número de ciudades: ");
        int ciudades = scanner.nextInt();
        scanner.nextLine(); // Consumir la nueva línea
        ElvisGraph grafo = new ElvisGraph(ciudades);

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

        grafo.dijkstra(ciudadInicial);
        scanner.close();
    }
}
