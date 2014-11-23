package dijkstra;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Field;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import dijkstra.Graph;
import dijkstra.IntegerGraphFactory;
import dijkstra.loader.IntegerFileGraphLoader;

public class GraphTest
{
  private Graph<Integer> graph;
  private static final IntegerGraphFactory factory = new IntegerGraphFactory(new IntegerFileGraphLoader("test-dijkstraData.txt"));
  private DijkstraAlgorithm<Integer> algorithm;
  
  @Before
  public void init() {
    graph = factory.buildGraph();
    algorithm = new DijkstraAlgorithm<>();
  }
  
  @Test
  public void getVertexes () throws Exception
  {
    Field vertexesField = graph.getClass().getDeclaredField("vertexes");
    vertexesField.setAccessible(true);
    @SuppressWarnings("unchecked")
    Set<Integer> vertexes = (Set<Integer>) vertexesField.get(graph);
    assertThat(vertexes, containsInAnyOrder(new Integer[]{1,2,3,4}));
  }
  
  @Test
  public void testShortestPaths () throws Exception
  {
    graph = new IntegerGraphFactory(new IntegerFileGraphLoader("test-dijkstraData.txt")).buildGraph();
    Integer[] targets = new Integer[]{2,3,4};
    Integer[] weights = new Integer[]{1,3,6};
    
    for(int i = 0; i < targets.length; i++) {
      int w = graph.findShortestPath(algorithm, 1, targets[i]).getWeight();
      assertThat( w, equalTo(weights[i]));      
    }
  }
  
  @Test
  public void shortestPaths () throws Exception
  {
    graph = new IntegerGraphFactory(new IntegerFileGraphLoader()).buildGraph();
    Integer[] targets = new Integer[]{ 1, 7,37,59,82,99,115,133,165,188,197};
    Integer[] weights = new Integer[]{ 1016, 2599,2610,2947,2052,2367,2399,2029,2442,2505,3068};
    for(int i = 0; i < targets.length; i++) {
      assertThat( graph.findShortestPath(algorithm, 1, targets[i]).getWeight(), equalTo(weights[i]));      
    }
  }
}