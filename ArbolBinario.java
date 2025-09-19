public class ArbolBinario {
    
    // Clase interna para el Nodo
    private static class Nodo {
        String producto;
        double precio;
        Nodo izq;
        Nodo der;
        
        // Constructor
        public Nodo(String producto, double precio) {
            this.producto = producto;
            this.precio = precio;
            this.izq = null;
            this.der = null;
        }
    }
    
    // Atributos de la clase ArbolBinario
    private Nodo raiz;
    
    // Constructor
    public ArbolBinario() {
        this.raiz = null;
    }
    
    // Método vacio(): boolean
    public boolean vacio() {
        return raiz == null;
    }
    
    // Método buscarNodo(producto): Nodo - Implementa búsqueda in-order
    public Nodo buscarNodo(String producto) {
        return buscarNodoRecursivo(raiz, producto);
    }
    
    private Nodo buscarNodoRecursivo(Nodo nodo, String producto) {
        // Caso base: nodo es null o encontramos el producto
        if (nodo == null || nodo.producto.equals(producto)) {
            return nodo;
        }
        
        // Si el producto es menor, buscar en el subárbol izquierdo
        if (producto.compareTo(nodo.producto) < 0) {
            return buscarNodoRecursivo(nodo.izq, producto);
        }
        
        // Si el producto es mayor, buscar en el subárbol derecho
        return buscarNodoRecursivo(nodo.der, producto);
    }
    
    // Método insertar
    public void insertar(String producto, double precio) {
        raiz = insertarRecursivo(raiz, producto, precio);
    }
    
    private Nodo insertarRecursivo(Nodo nodo, String producto, double precio) {
        // Si el árbol está vacío, crear un nuevo nodo
        if (nodo == null) {
            return new Nodo(producto, precio);
        }
        
        // Si el producto es menor, insertar en el subárbol izquierdo
        if (producto.compareTo(nodo.producto) < 0) {
            nodo.izq = insertarRecursivo(nodo.izq, producto, precio);
        }
        // Si el producto es mayor, insertar en el subárbol derecho
        else if (producto.compareTo(nodo.producto) > 0) {
            nodo.der = insertarRecursivo(nodo.der, producto, precio);
        }
        // Si el producto ya existe, no insertar (no duplicados)
        
        return nodo;
    }
    
    // Método imprimirArbol - Implementa recorrido in-order
    public void imprimirArbol() {
        if (vacio()) {
            System.out.println("El árbol está vacío");
        } else {
            System.out.println("Contenido del árbol (in-order):");
            imprimirInOrder(raiz);
            System.out.println();
        }
    }
    
    private void imprimirInOrder(Nodo nodo) {
        if (nodo != null) {
            imprimirInOrder(nodo.izq);
            System.out.print(nodo.producto + " ($" + String.format("%.2f", nodo.precio) + ") ");
            imprimirInOrder(nodo.der);
        }
    }
    
    // Métodos adicionales para diferentes tipos de recorrido
    public void imprimirPreOrder() {
        System.out.println("Recorrido Pre-order:");
        imprimirPreOrderRecursivo(raiz);
        System.out.println();
    }
    
    private void imprimirPreOrderRecursivo(Nodo nodo) {
        if (nodo != null) {
            System.out.print(nodo.producto + " ($" + String.format("%.2f", nodo.precio) + ") ");
            imprimirPreOrderRecursivo(nodo.izq);
            imprimirPreOrderRecursivo(nodo.der);
        }
    }
    
    public void imprimirPostOrder() {
        System.out.println("Recorrido Post-order:");
        imprimirPostOrderRecursivo(raiz);
        System.out.println();
    }
    
    private void imprimirPostOrderRecursivo(Nodo nodo) {
        if (nodo != null) {
            imprimirPostOrderRecursivo(nodo.izq);
            imprimirPostOrderRecursivo(nodo.der);
            System.out.print(nodo.producto + " ($" + String.format("%.2f", nodo.precio) + ") ");
        }
    }
    
    // Método main para probar la implementación
    public static void main(String[] args) {
        System.out.println("=== CATÁLOGO DE PRODUCTOS - ÁRBOL BINARIO ===");
        
        ArbolBinario catalogo = new ArbolBinario();
        
        // Probar si el catálogo está vacío
        System.out.println("¿Catálogo vacío? " + catalogo.vacio());
        
        // Insertar productos con sus precios
        System.out.println("\n--- Agregando productos al catálogo ---");
        catalogo.insertar("Laptop", 1299.99);
        catalogo.insertar("Mouse", 25.50);
        catalogo.insertar("Teclado", 89.99);
        catalogo.insertar("Monitor", 299.99);
        catalogo.insertar("Auriculares", 149.99);
        catalogo.insertar("Webcam", 79.99);
        catalogo.insertar("Tablet", 399.99);
        
        System.out.println("¿Catálogo vacío después de agregar productos? " + catalogo.vacio());
        
        // Imprimir el catálogo ordenado alfabéticamente
        System.out.println("\n--- Catálogo ordenado alfabéticamente ---");
        catalogo.imprimirArbol();
        
        // Buscar productos específicos
        System.out.println("\n--- Búsqueda de productos ---");
        Nodo encontrado = catalogo.buscarNodo("Laptop");
        if (encontrado != null) {
            System.out.println("✓ Producto encontrado: " + encontrado.producto + " - Precio: $" + String.format("%.2f", encontrado.precio));
        } else {
            System.out.println("✗ Producto 'Laptop' no encontrado");
        }
        
        encontrado = catalogo.buscarNodo("Smartphone");
        if (encontrado != null) {
            System.out.println("✓ Producto encontrado: " + encontrado.producto + " - Precio: $" + String.format("%.2f", encontrado.precio));
        } else {
            System.out.println("✗ Producto 'Smartphone' no encontrado");
        }
        
        // Mostrar diferentes tipos de recorrido
        System.out.println("\n--- Diferentes formas de recorrer el catálogo ---");
        catalogo.imprimirPreOrder();
        catalogo.imprimirPostOrder();
        
        System.out.println("\n=== FIN DEL CATÁLOGO ===");
    }
}