# Implementacion con problema de ejemplo
# Referencias
# Dijkstra's Algorithm - A step by step analysis, with sample Python code Link:https://youtu.be/_B5cx-WD5EA?si=96HFpYjiqaWSBl_L
# EL PROBLEMA DE LA RUTA MÁS CORTA (ALGORITMO DE DIJKSTRA) EJERCICIO RESUELTO Link:https://youtu.be/gocE-gooeBQ?si=2rRMq0ftEBeyg3G7
import itertools
from heapq import heappush, heappop


class Graph:
    def __init__(self, adjacency_list):
        self.adjacency_list = adjacency_list


class Vertex:
    def __init__(self, value):
        self.value = value


class Edge:
    def __init__(self, distance, vertex):
        self.distance = distance
        self.vertex = vertex


def dijkstra(graph, start, end):
    previous = {v: None for v in graph.adjacency_list.keys()}
    visited = {v: False for v in graph.adjacency_list.keys()}
    distances = {v: float("inf") for v in graph.adjacency_list.keys()}
    distances[start] = 0
    queue = PriorityQueue()
    queue.add_task(0, start)
    path = []
    while queue:
        removed_distance, removed = queue.pop_task()
        visited[removed] = True

        if removed is end:
            while previous[removed]:
                path.append(removed.value)
                removed = previous[removed]
            path.append(start.value)
            print(f"shortest distance to {end.value}: ", distances[end])
            print(f"path to {end.value}: ", path[::-1])
            return

        for edge in graph.adjacency_list[removed]:
            if visited[edge.vertex]:
                continue
            new_distance = removed_distance + edge.distance
            if new_distance < distances[edge.vertex]:
                distances[edge.vertex] = new_distance
                previous[edge.vertex] = removed
                queue.add_task(new_distance, edge.vertex)
    return


# slightly modified heapq implementation from https://docs.python.org/3/library/heapq.html
class PriorityQueue:
    def __init__(self):
        self.pq = []  # list of entries arranged in a heap
        self.entry_finder = {}  # mapping of tasks to entries
        self.counter = itertools.count()  # unique sequence count

    def __len__(self):
        return len(self.pq)

    def add_task(self, priority, task):
        'Add a new task or update the priority of an existing task'
        if task in self.entry_finder:
            self.update_priority(priority, task)
            return self
        count = next(self.counter)
        entry = [priority, count, task]
        self.entry_finder[task] = entry
        heappush(self.pq, entry)

    def update_priority(self, priority, task):
        'Update the priority of a task in place'
        entry = self.entry_finder[task]
        count = next(self.counter)
        entry[0], entry[1] = priority, count

    def pop_task(self):
        'Remove and return the lowest priority task. Raise KeyError if empty.'
        while self.pq:
            priority, count, task = heappop(self.pq)
            del self.entry_finder[task]
            return priority, task
        raise KeyError('pop from an empty priority queue')


# testing the algorithm with the rural roads problem
# Grafo basado en la imagen del problema de caminos rurales
vertices = [Vertex(1), Vertex(2), Vertex(3), Vertex(4), Vertex(5), Vertex(6), Vertex(7), Vertex(8)]
v1, v2, v3, v4, v5, v6, v7, v8 = vertices

# Lista de adyacencia basada en el grafo
adj_list = {
    v1: [Edge(2, v2), Edge(1, v3)],
    v2: [Edge(2, v1), Edge(1, v3), Edge(5, v4)],
    v3: [Edge(1, v1), Edge(1, v2), Edge(2, v4), Edge(1, v5), Edge(4, v6)],
    v4: [Edge(5, v2), Edge(2, v3), Edge(3, v5)],
    v5: [Edge(1, v3), Edge(3, v4), Edge(6, v6), Edge(7, v7)],
    v6: [Edge(4, v3), Edge(6, v5), Edge(3, v7), Edge(5, v8), Edge(2, v8)],
    v7: [Edge(7, v5), Edge(3, v6), Edge(8, v8)],
    v8: [Edge(5, v6), Edge(2, v6), Edge(8, v7), Edge(6, v8)]
}

my_graph = Graph(adj_list)

print("=== SOLUCIÓN DEL PROBLEMA DE CAMINOS RURALES ===\n")

# a) Ruta más corta entre localidades 1 y 8
print("a) Ruta más corta entre localidades 1 y 8:")
dijkstra(my_graph, start=v1, end=v8)
print()

# b) Ruta más corta entre localidades 1 y 6
print("b) Ruta más corta entre localidades 1 y 6:")
dijkstra(my_graph, start=v1, end=v6)
print()

# c) Ruta más corta entre localidades 4 y 8
print("c) Ruta más corta entre localidades 4 y 8:")
dijkstra(my_graph, start=v4, end=v8)
print()
 
# d) Ruta más corta entre localidades 2 y 6
print("d) Ruta más corta entre localidades 2 y 6:")
dijkstra(my_graph, start=v2, end=v6)
print()