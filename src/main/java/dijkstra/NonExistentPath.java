package dijkstra;

import java.util.ArrayList;
import java.util.List;

public class NonExistentPath<VERTEX> extends Path<VERTEX>
{
  private VERTEX source;
  private VERTEX target;

  public NonExistentPath (VERTEX source, VERTEX target)
  {
    this(new ArrayList<VERTEX>(), Integer.MAX_VALUE);
    this.source = source;
    this.target = target;
  }

  private NonExistentPath (List<VERTEX> visited, int duration)
  {
    super(visited, duration);
  }
  
  @Override
  public boolean exists ()
  {
    return false;
  }
  
  @Override
  public String toString ()
  {
    return target + " is unreachable from " + source;
  }

}
