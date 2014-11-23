package dijkstra.loader;

import dijkstra.UndirectedEdges;

public interface IntegerGraphLoader
{

  UndirectedEdges<Integer> load() throws Exception;
}
