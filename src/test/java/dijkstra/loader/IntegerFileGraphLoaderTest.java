package dijkstra.loader;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.Test;

import dijkstra.UndirectedEdge;
import dijkstra.UndirectedEdges;
import dijkstra.loader.IntegerFileGraphLoader;

public class IntegerFileGraphLoaderTest
{
  private static final IntegerFileGraphLoader LOADER = new IntegerFileGraphLoader("test-dijkstraData.txt");

  @Test
  public void load () throws Exception
  {
    UndirectedEdges<Integer> edges = LOADER.load();
    
    assertThat(edges, equalTo(expectedEdges()));
  }

  private UndirectedEdges<Integer> expectedEdges ()
  {
    UndirectedEdges<Integer> edges = new UndirectedEdges<>();
    edges.addAll(addEdges(new UndirectedEdge<Integer>(1, 2, 1), new UndirectedEdge<Integer>(1, 3, 4)));
    edges.addAll(addEdges(new UndirectedEdge<Integer>(2, 1, 1), new UndirectedEdge<Integer>(2, 3, 2), new UndirectedEdge<Integer>(2, 4, 6)));
    edges.addAll(addEdges(new UndirectedEdge<Integer>(3, 1, 4), new UndirectedEdge<Integer>(3, 2, 2), new UndirectedEdge<Integer>(3, 4, 3)));
    edges.addAll(addEdges(new UndirectedEdge<Integer>(4, 2, 6), new UndirectedEdge<Integer>(4, 3, 3)));
    return edges;
  }

  @SafeVarargs
  private final HashSet<UndirectedEdge<Integer>> addEdges (UndirectedEdge<Integer>... group)
  {
    HashSet<UndirectedEdge<Integer>> edges = new HashSet<>();
    for(UndirectedEdge<Integer> e : group)
      edges.add(e);
    return edges;
  }

}
