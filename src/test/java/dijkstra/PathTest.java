package dijkstra;

import static dijkstra.fixture.TestHelper.buildPath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import dijkstra.Path;

public class PathTest
{
  private Path<Character> path = buildPath(9, 'X', 'Y');
  
  @Test
  public void areIdentical ()
  {
    assertThat(path.equals(path), equalTo(true));
  }
  
  @Test
  public void areDifferentObject ()
  {
    assertThat(path.equals(new Object()), equalTo(false));
  }
  
  @Test
  public void haveDifferentWeight ()
  {
    assertThat(path.equals(buildPath(10, 'X', 'Y')), equalTo(false));
  }
  
  @Test
  public void haveDifferentVertexes ()
  {
    assertThat(path.equals(buildPath(9, 'X', 'Z')), equalTo(false));
  }
  
  @Test
  public void areEquals ()
  {
    assertThat(path.equals(buildPath(9, 'X', 'Y')), equalTo(true));
  }
  
  @Test
  public void setPathTargetGeneratesNewPath ()
  {
    Path<Character> newPath = path.setTarget('Z', 5);
    
    assertThat(newPath, not(equalTo(path)));
    assertThat(newPath, equalTo(buildPath(14, 'X', 'Y', 'Z')));
  }
  
  @Test
  public void hashCodeValue ()
  {
    assertThat(path.hashCode(), equalTo(10784));
  }
}
