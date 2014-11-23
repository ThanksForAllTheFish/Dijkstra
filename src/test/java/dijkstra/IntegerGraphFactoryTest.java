package dijkstra;

import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import dijkstra.loader.IntegerGraphLoader;

public class IntegerGraphFactoryTest
{
  @Rule public JUnitRuleMockery context = new JUnitRuleMockery();
  @Rule public ExpectedException expectedException = ExpectedException.none();

  private IntegerGraphLoader loader = context.mock(IntegerGraphLoader.class);

  @Test
  public void throwsExceptionOnLoadFailure () throws Exception
  {
    IntegerGraphFactory factory = new IntegerGraphFactory(loader);
    
    context.checking(new Expectations()
    {
      {
        oneOf(loader).load(); will(throwException(new Exception()));
      }
    });
    
    expectedException.expect(RuntimeException.class);
    
    assertThat(factory.buildGraph(), not(nullValue()));
  }

}
