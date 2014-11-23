package dijkstra;

public class UndirectedEdge<VERTEX>
{
  public VERTEX source;
  public VERTEX target;
  public int    weight;
  
  public UndirectedEdge (VERTEX source, VERTEX target, int weight)
  {
    this.source = source;
    this.target = target;
    this.weight = weight;
  }
}