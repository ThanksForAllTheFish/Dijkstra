package dijkstra;

import dijkstra.loader.IntegerFileGraphLoader;
import dijkstra.loader.IntegerGraphLoader;

public class IntegerGraphFactory
{
  private IntegerGraphLoader loader;
  
  public IntegerGraphFactory ()
  {
    this(new IntegerFileGraphLoader());
  }

  public IntegerGraphFactory (IntegerGraphLoader loader)
  {
    this.loader = loader;
  }

  public Graph<Integer> buildGraph() {
    try
    {
      return new Graph<Integer> (loader.load());
    }
    catch (Exception e)
    {
      throw new RuntimeException(e);
    }
  }
}
