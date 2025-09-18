//Implementacion con problema de ejemplo
//Heurística A* f(n)=g(n)+h(n)
//Referencias 
//Código compartido de clase
//Nº 025.1: Código Python - Búsqueda en Anchura Link: https://youtu.be/fYRLNggYwB4?si=CODyljnvl1sq5Tar
//Nº 026.1: Código Python - Búsqueda con Coste Uniforme Link: https://youtu.be/jXVj-gQxo14?si=0TD786ffG4ODS9lL
//Nº 027.1: Código Python - Búsqueda en Profundidad Link: https://youtu.be/8qQEJCCFAPM?si=MI0tqpGSwF0UFiz-

import java.util.*;

// Clase Arista (conexión entre ciudades)
class Edge {
    String to;
    int cost;

    public Edge(String to, int cost) {
        this.to = to;
        this.cost = cost;
    }
}

// Clase Nodo para búsquedas
class Node {
    String state;
    int totalCost;
    Node parent;

    public Node(String state, int totalCost, Node parent) {
        this.state = state;
        this.totalCost = totalCost;
        this.parent = parent;
    }

    public String getState() {
        return state;
    }

    public int getTotalCost() {
        return totalCost;
    }
}

// Comparador para UCS
class NodePriorityComparator implements Comparator<Node> {
    @Override
    public int compare(Node x, Node y) {
        return Integer.compare(x.getTotalCost(), y.getTotalCost());
    }
}

// Clase Grafo
class Graph {
    private Map<String, List<Edge>> adjList = new HashMap<>();

    public void addEdge(String from, String to, int cost) {
        adjList.putIfAbsent(from, new ArrayList<>());
        adjList.putIfAbsent(to, new ArrayList<>());
        adjList.get(from).add(new Edge(to, cost));
        adjList.get(to).add(new Edge(from, cost)); // grafo no dirigido
    }

    public List<Edge> getNeighbors(String node) {
        return adjList.getOrDefault(node, new ArrayList<>());
    }
}

public class Busquedas {

    // Heurística fija (distancias estimadas a la meta "E")
    public static Map<String, Integer> heuristic = new HashMap<>();
    static {
        heuristic.put("A", 4);
        heuristic.put("B", 2);
        heuristic.put("C", 1);
        heuristic.put("D", 2);
        heuristic.put("E", 0); // meta
    }

    // ---------- BFS ----------
    public static void BFS(Graph graph, String start, String goal) {
        Queue<Node> frontier = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        frontier.add(new Node(start, 0, null));

        while (!frontier.isEmpty()) {
            Node current = frontier.poll();

            if (current.state.equals(goal)) {
                printPath(current);
                return;
            }

            if (!visited.contains(current.state)) {
                visited.add(current.state);
                for (Edge e : graph.getNeighbors(current.state)) {
                    frontier.add(new Node(e.to, 0, current));
                }
            }
        }
    }

    // ---------- DFS ----------
    public static void DFS(Graph graph, String start, String goal) {
        Stack<Node> frontier = new Stack<>();
        Set<String> visited = new HashSet<>();

        frontier.push(new Node(start, 0, null));

        while (!frontier.isEmpty()) {
            Node current = frontier.pop();

            if (current.state.equals(goal)) {
                printPath(current);
                return;
            }

            if (!visited.contains(current.state)) {
                visited.add(current.state);
                for (Edge e : graph.getNeighbors(current.state)) {
                    frontier.push(new Node(e.to, 0, current));
                }
            }
        }
    }

    // ---------- UCS ----------
    public static void UCS(Graph graph, String start, String goal) {
        PriorityQueue<Node> frontier = new PriorityQueue<>(new NodePriorityComparator());
        Set<String> visited = new HashSet<>();

        frontier.add(new Node(start, 0, null));

        while (!frontier.isEmpty()) {
            Node current = frontier.poll();

            if (current.state.equals(goal)) {
                printPath(current);
                System.out.println("Costo total: " + current.totalCost);
                return;
            }

            if (!visited.contains(current.state)) {
                visited.add(current.state);
                for (Edge e : graph.getNeighbors(current.state)) {
                    frontier.add(new Node(e.to, current.totalCost + e.cost, current));
                }
            }
        }
    }

    // ---------- A* ----------
    public static void AStar(Graph graph, String start, String goal) {
        PriorityQueue<Node> frontier = new PriorityQueue<>(
            Comparator.comparingInt(n -> n.totalCost + heuristic.get(n.state))
        );
        Set<String> visited = new HashSet<>();

        frontier.add(new Node(start, 0, null));

        while (!frontier.isEmpty()) {
            Node current = frontier.poll();

            if (current.state.equals(goal)) {
                printPath(current);
                System.out.println("Costo total: " + current.totalCost);
                return;
            }

            if (!visited.contains(current.state)) {
                visited.add(current.state);
                for (Edge e : graph.getNeighbors(current.state)) {
                    frontier.add(new Node(e.to, current.totalCost + e.cost, current));
                }
            }
        }
    }

    // ---------- Imprimir Ruta ----------
    public static void printPath(Node node) {
        List<String> path = new ArrayList<>();
        while (node != null) {
            path.add(node.state);
            node = node.parent;
        }
        Collections.reverse(path);
        System.out.println("Ruta encontrada: " + path);
    }
    public static void main(String[] args) {
        Graph graph = new Graph();

        // Grafo de ejemplo
        graph.addEdge("A", "B", 2);
        graph.addEdge("A", "C", 5);
        graph.addEdge("B", "C", 1);
        graph.addEdge("B", "D", 4);
        graph.addEdge("C", "E", 2);
        graph.addEdge("D", "E", 3);

        System.out.println("=== BFS ===");
        BFS(graph, "A", "E");

        System.out.println("\n=== DFS ===");
        DFS(graph, "A", "E");

        System.out.println("\n=== UCS ===");
        UCS(graph, "A", "E");

        System.out.println("\n=== A* ===");
        AStar(graph, "A", "E");
    }
}
