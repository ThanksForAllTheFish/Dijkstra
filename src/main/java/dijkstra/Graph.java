package dijkstra;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Graph<VERTEX>
{
  private final Set<VERTEX>               vertexes;
  private final UndirectedEdges<VERTEX>   edges;

  public Graph (UndirectedEdges<VERTEX> edges)
  {
    this.edges = edges;
    this.vertexes = edges.getSources();
  }

  public Set<VERTEX> vertexes ()
  {
    return vertexes;
  }

  public final UndirectedEdges<VERTEX> edges ()
  {
    return edges;
  }

  public final Path<VERTEX> findShortestPath (DijkstraAlgorithm<VERTEX> algorithm, VERTEX source, VERTEX target)
  {
    if (!source.equals(target))
    {
      return algorithm.shortestPath(this, source, target);
    }
    else
    {
      return computeShortestPath(source, target, algorithm);
    }
  }

  private Path<VERTEX> computeShortestPath (VERTEX source, VERTEX target, DijkstraAlgorithm<VERTEX> algorithm)
  {
    Map<VERTEX, Path<VERTEX>> targetInEdges = computeShortestPathToTargetInEdges(source, target, algorithm);

    return addShortestLastStep(target, targetInEdges).orElse(new NonExistentPath<VERTEX>(source, target));
  }

  private Optional<Path<VERTEX>> addShortestLastStep (VERTEX target, Map<VERTEX, Path<VERTEX>> targetInEdges)
  {
    return targetInEdges.keySet().stream().map(previous -> {
      return computePathFromPreviousToTarget(target, targetInEdges, previous);
    }).min(new Comparator<Path<VERTEX>>()
    {
      @Override
      public int compare (Path<VERTEX> path1, Path<VERTEX> path2)
      {
        return path1.getWeight() - path2.getWeight();
      }
    });
  }

  private Path<VERTEX> computePathFromPreviousToTarget (VERTEX target, Map<VERTEX, Path<VERTEX>> targetInEdges,
      VERTEX previous)
  {
    return edges.getTargets(previous).stream().filter(v -> v.equals(target)).map(t -> {
      int lastStepWeight = edges.getEdgeWeight(previous, t);
      return targetInEdges.get(previous).setTarget(t, lastStepWeight);
    }).findAny().get();
  }

  private Map<VERTEX, Path<VERTEX>> computeShortestPathToTargetInEdges (final VERTEX source, final VERTEX target, DijkstraAlgorithm<VERTEX> algorithm)
  {
    return edges.getTargets(target).stream().collect(Collectors.toMap(Function.identity(), previousToTarget -> {
      return algorithm.shortestPath(this, source, previousToTarget);
    }));
  }
}
