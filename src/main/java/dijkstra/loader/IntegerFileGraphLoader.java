package dijkstra.loader;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import dijkstra.UndirectedEdge;
import dijkstra.UndirectedEdges;

public class IntegerFileGraphLoader implements IntegerGraphLoader
{
  private final static String FILE_PATH = "dijkstraData.txt";
  private final String filePath;
  
  public IntegerFileGraphLoader ()
  {
    this(FILE_PATH);
  }

  public IntegerFileGraphLoader (String filePath)
  {
    this.filePath = filePath;
  }

  @Override
  public UndirectedEdges<Integer> load () throws Exception
  {
    URL loc = this.getClass().getClassLoader().getResource(filePath);
    Path path = Paths.get(loc.toURI());
    return Files.lines(path).parallel().map(line -> {
      String[] map = line.split("\\s+");
      final Integer source = Integer.valueOf(map[0]);
      Set<UndirectedEdge<Integer>> edges = new HashSet<>();
      for(int i = 1; i < map.length; i++) {
        String[] tarLen = map[i].split(",");
        Integer target = Integer.valueOf(tarLen[0]);
        Integer lenght = Integer.valueOf(tarLen[1]);
        edges.add(new UndirectedEdge<>(source, target, lenght));
      }
      return edges;
    }).parallel().collect(UndirectedEdges::new, UndirectedEdges::addAll, UndirectedEdges::combine);
  }

}
