package dijkstra.internal;

import static dijkstra.fixture.TestHelper.buildPath;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import dijkstra.NonExistentPath;
import dijkstra.Path;

public class DebugMethodTests
{
  @Test
  public void aNonExistentPathPrintsCorrectly ()
  {
    Path<Character> path = new NonExistentPath<>('C', 'Z');
    
    assertThat(path.toString(), equalTo("Z is unreachable from C"));
  }
  
  @Test
  public void aPathPrintsCorrectly ()
  {
    assertThat(buildPath(10, 'X', 'Y').toString().trim(), equalTo("X -> Y ->  in 10"));
  }
}
