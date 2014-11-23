package dijkstra;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UndirectedEdges<VERTEX>
{
  private final Map<VERTEX, Map<VERTEX, Integer>> outEdges;

  public UndirectedEdges ()
  {
    this.outEdges = new HashMap<>();
  }

  public void add (UndirectedEdge<VERTEX> edge)
  {
    addOutEdge(edge.source, edge.target, edge.weight);
    addOutEdge(edge.target, edge.source, edge.weight);
  }

  private void addOutEdge (VERTEX source, VERTEX target, int weight)
  {
    Map<VERTEX, Integer> out = outEdges.get(source);
    if (null == out)
    {
      out = new HashMap<>();
    }
    out.put(target, weight);
    outEdges.put(source, out);
  }

  public Integer getEdgeWeight (VERTEX source, VERTEX target)
  {
    Map<VERTEX, Integer> neighbors = outEdges.get(source);
    int weight = Integer.MAX_VALUE;
    if (null != neighbors && neighbors.containsKey(target))
    {
      weight = neighbors.get(target);
    }
    return weight;
  }

  public Set<VERTEX> getTargets (VERTEX source)
  {
    return outEdges.containsKey(source) ? outEdges.get(source).keySet() : new HashSet<VERTEX>();
  }

  public Set<VERTEX> getSources ()
  {
    return outEdges.keySet();
  }

  public void addAll (Set<UndirectedEdge<VERTEX>> edges)
  {
    edges.parallelStream().forEach(edge -> add(edge));
  }
  
  public void combine(UndirectedEdges<VERTEX> others) {
    outEdges.putAll(others.outEdges);
  }

  @Override
  public int hashCode ()
  {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((outEdges == null) ? 0 : outEdges.hashCode());
    return result;
  }

  @Override
  public boolean equals (Object obj)
  {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    @SuppressWarnings("unchecked")
    UndirectedEdges<VERTEX> other = (UndirectedEdges<VERTEX>) obj;
    if (outEdges == null)
    {
      if (other.outEdges != null) return false;
    }
    else if (!outEdges.equals(other.outEdges)) return false;
    return true;
  }

  @Override
  public String toString ()
  {
    return "UndirectedEdges [outEdges=" + outEdges + "]";
  }
}
