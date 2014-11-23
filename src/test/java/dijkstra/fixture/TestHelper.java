package dijkstra.fixture;

import java.util.ArrayList;
import java.util.List;

import dijkstra.Path;

public abstract class TestHelper
{
  
  public static Path<Character> buildPath (int weight, char... vertexes)
  {
    List<Character> path = new ArrayList<Character>(vertexes.length*100/75+1);
    for(char c : vertexes)
      path.add(c);
    return new Path<>(path, weight);
  }
}
