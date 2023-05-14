package pr2;

import java.util.*;
import org.junit.*;
import static org.junit.Assert.assertEquals;

public class Graph<V>{
//Lista de adyacencia.
private Map<V, Set<V>> adjacencyList = new HashMap<>();
/******************************************************************
* Añade el vértice ‘v‘ al grafo.
*
* @param v vértice a añadir.
* @return ‘true‘ si no estaba anteriormente y ‘false‘ en caso
* contrario.
******************************************************************/
public boolean addVertex(V v){
    if(adjacencyList.containsKey(v)){
        return false;
    }
    else  {
        adjacencyList.put(v, new HashSet<>());
        return true;
    }
}
/******************************************************************
* Añade un arco entre los vértices ‘v1‘ y ‘v2‘ al grafo. En
* caso de que no exista alguno de los vértices, lo añade
* también.
*
* @param v1 el origen del arco.
* @param v2 el destino del arco.
* @return ‘true‘ si no existía el arco y ‘false‘ en caso
contrario.
******************************************************************/
public boolean addEdge(V v1, V v2){
    boolean v1Exists = adjacencyList.containsKey(v1);
    boolean v2Exists = adjacencyList.containsKey(v2);

    if (!v1Exists){
    addVertex(v1);
    }
    if (!v2Exists){
    addVertex(v2);
    }
return true; 
}
/******************************************************************
* Obtiene el conjunto de vértices adyacentes a ‘v‘.
*
* @param v vértice del que se obtienen los adyacentes.
* @return conjunto de vértices adyacentes.
******************************************************************/
public Set<V> obtainAdjacents(V v) throws Exception{
    if (adjacencyList.containsKey(v)){
        return adjacencyList.get(v);
    }
    if (!adjacencyList.containsKey(v)){
        throw new Exception("El vertice introducido no esta en el grafo");
    }
    else{
        return null; 
    }
}
/******************************************************************
* Comprueba si el grafo contiene el vértice dado.
*
* @param v vértice para el que se realiza la comprobación.
* @return ‘true‘ si ‘v‘ es un vértice del grafo.
******************************************************************/
public boolean containsVertex(V v){
    if (!adjacencyList.containsKey(v)){
        return false;
    }
    else {
        return true; //Este código hay que modificarlo.
    }
}
/******************************************************************
* Método ‘toString()‘ reescrito para la clase ‘Grafo.java‘.
* @return una cadena de caracteres con la lista de
* adyacencia.
******************************************************************/
@Override
public String toString(){
    StringBuilder stb1 = new StringBuilder();
    for(V vertex: adjacencyList.keySet()){
        stb1.append(vertex.toString()).append(": ");
        for(V adyacente: adjacencyList.get(vertex)){
            stb1.append(adyacente.toString()).append(" ");
        }
        stb1.append("\n");
    }
    return stb1.toString();
}
/*********************************************************
* Obtiene, en caso de que exista, un camino entre ‘v1‘ y
* ‘v2‘. En caso contrario, devuelve ‘null‘.
*
* @param v1 el vértice origen.
* @param v2 el vértice destino.
* @return lista con la secuencia de vértices desde ‘v1‘ hasta
* ‘v2‘ * pasando por arcos del grafo.
*********************************************************/
public List<V> onePath(V v1, V v2){

    HashMap<V,V> traza = new HashMap<>();
        Stack<V> abierta = new Stack<>();
        List<V> path = new ArrayList<>();
        abierta.push(v1);
        traza.put(v1, null);
        boolean encontrado = false;

        while(!abierta.isEmpty() && (!encontrado)){
            V actual = abierta.pop();
            encontrado = actual.equals(v2);
            if(!encontrado){
                Set<V> adyacentes = adjacencyList.get(actual);
                if(adyacentes != null){
                    for(V adyacente: adyacentes){
                        if(!traza.containsKey(adyacente)){
                            abierta.push(adyacente);
                            traza.put(adyacente, actual);
                        }
                    }
                }
            }
        }
        if(encontrado){
            V actual = v2;
            while(actual != null){
                path.add(0, actual);
                actual = traza.get(actual);
            }
        }
        return encontrado ? path : null;

    }

    /**
* Este test comprueba que el método ‘onePath(V v1, V v2)‘
* encuentra un camino entre ‘v1‘ y ‘v2‘ cuando existe.
*/
@Test
public void onePathFindsAPath(){
System.out.println("\nTest onePathFindsAPath");
System.out.println("----------------------");
// Se construye el grafo.
Graph<Integer> g = new Graph<>();
g.addEdge(1, 2);
g.addEdge(3, 4);
g.addEdge(1, 5);
g.addEdge(5, 6);
g.addEdge(6, 4);
// Se construye el camino esperado.
List<Integer> expectedPath = new ArrayList<>();
expectedPath.add(1);
expectedPath.add(5);
expectedPath.add(6);
expectedPath.add(4);
//Se comprueba si el camino devuelto es igual al esperado.
assertEquals(expectedPath, g.onePath(1, 4));
}

}

