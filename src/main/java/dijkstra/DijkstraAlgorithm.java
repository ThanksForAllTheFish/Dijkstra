package dijkstra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class DijkstraAlgorithm<VERTEX>
{
  private UndirectedEdges<VERTEX>       weights;
  private Map<Edge, Map<VERTEX, VERTEX>> path;
  private List<VERTEX>                   unvisited;
  private List<VERTEX>                   visited;
  private UndirectedEdges<VERTEX>       outEdges;
  
  public Path<VERTEX> shortestPath (Graph<VERTEX> graph, VERTEX source, VERTEX target)
  {
    initialize(source, graph);
    return shortestPath(source, source, target);
  }

  private void initialize (VERTEX source, Graph<VERTEX> graph)
  {
    this.outEdges = graph.edges();
    weights = new UndirectedEdges<>();
    weights.add(new UndirectedEdge<VERTEX>(source, source, 0));
    path = new HashMap<>();
    
    this.unvisited = new ArrayList<>(graph.vertexes());
    for (VERTEX v : this.unvisited)
    {
      if (!v.equals(source))
      {
        Edge edge = new Edge(source, v);
        weights.add(new UndirectedEdge<VERTEX>(source, v, Integer.MAX_VALUE));
        path.put(edge, new HashMap<VERTEX, VERTEX>());
      }
    }
    this.visited = new ArrayList<>();
  }

  private Path<VERTEX> shortestPath (VERTEX current, VERTEX source, VERTEX target)
  {
    analyzeNeighbors(current, source);
    VERTEX nextCurrent = findNextVertex(source);

    if (foundShortestPathTo(target) || nextCurrent == null)
    {
      return buildPath(source, target);
    }
    return shortestPath(nextCurrent, source, target);
  }

  private void analyzeNeighbors (VERTEX current, VERTEX source)
  {
    for (VERTEX n : outEdges.getTargets(current))
    {
      int fromSourceToN = weights.getEdgeWeight(source, current) + outEdges.getEdgeWeight(current, n);
      if (fromSourceToN < weights.getEdgeWeight(source, n))
      {
        weights.add(new UndirectedEdge<VERTEX>(source, n, fromSourceToN));
        addVertexToPath(source, n, current);
      }
    }
    visited.add(current);
    unvisited.remove(current);
  }

  private VERTEX findNextVertex (VERTEX source)
  {
    int nextCurrentDistance = Integer.MAX_VALUE;
    VERTEX nextCurrent = null;
    for (int i = 0; i < unvisited.size(); i++)
    {
      int distance = weights.getEdgeWeight(source, unvisited.get(i));
      if (distance < nextCurrentDistance)
      {
        nextCurrentDistance = distance;
        nextCurrent = unvisited.get(i);
      }
    }
    return nextCurrent;
  }

  private boolean foundShortestPathTo (VERTEX target)
  {
    return visited.contains(target);
  }

  private Path<VERTEX> buildPath (VERTEX source, VERTEX target)
  {
    Map<VERTEX, VERTEX> traversed = getTraversedVertexes(new Edge(source, target));
    List<VERTEX> path = buildPath(traversed, target, new ArrayList<VERTEX>());
    
    Integer shortestDuration = weights.getEdgeWeight(source, target);
    if (shortestDuration < Integer.MAX_VALUE)
    {
      if (!source.equals(path.get(0))) path.add(0, source);
      path.add(target);
      return new Path<>(path, shortestDuration);
    }
    return new NonExistentPath<VERTEX>(source, target);
  }

  private Map<VERTEX, VERTEX> getTraversedVertexes (Edge edge)
  {
    return path.get(edge);
  }

  private void addVertexToPath (VERTEX source, VERTEX target, VERTEX vertexToAdd)
  {
    Edge ends = new Edge(source, target);
    Map<VERTEX, VERTEX> traversed = getTraversedVertexes(ends);
    traversed.put(target, vertexToAdd);
    this.path.put(ends, traversed);
  }

  private List<VERTEX> buildPath (Map<VERTEX, VERTEX> previous, VERTEX current, List<VERTEX> path)
  {
    VERTEX vertex = previous.get(current);
    if (null != vertex)
    {
      path.add(0, vertex);
      return buildPath(previous, vertex, path);
    }
    return path;
  }

  private class Edge
  {
    private final VERTEX source;
    private final VERTEX target;

    public Edge (VERTEX source, VERTEX target)
    {
      this.source = source;
      this.target = target;
    }

    @Override
    public int hashCode ()
    {
      int hash = 7;
      int seed = 31;
      hash = hash * seed + source.hashCode();
      return hash * seed + target.hashCode();
    }

    @Override
    public boolean equals (Object other)
    {
      @SuppressWarnings("unchecked")
      Edge edge = (DijkstraAlgorithm<VERTEX>.Edge) other;
      return this.source.equals(edge.source) && this.target.equals(edge.target);
    }

    @Override
    public String toString ()
    {
      return source + " -> " + target;
    }
  }
}
