package dijkstra;

import java.util.ArrayList;
import java.util.List;

public class Path<VERTEX>
{
  private final List<VERTEX> path;
  private final int          weight;

  public Path (List<VERTEX> path, int weight)
  {
    this.path = path;
    this.weight = weight;
  }

  public int getWeight ()
  {
    return weight;
  }

  public Path<VERTEX> setTarget (VERTEX target, int deltaWeight)
  {
    List<VERTEX> path = new ArrayList<>(this.path);
    path.add(target);
    return new Path<VERTEX>(path, this.weight + deltaWeight);
  }

  @Override
  public int hashCode ()
  {
    int hash = 7;
    int seed = 31;
    hash = hash * seed + weight;
    return hash * seed + path.hashCode();
  }

  @Override
  public boolean equals (Object other)
  {
    if (!(other instanceof Path<?>)) return false;
    if (this == other) return true;

    Path<?> path = (Path<?>) other;
    return this.weight == path.weight && this.path.equals(path.path);
  }

  @Override
  public String toString ()
  {
    return path.stream().collect(StringBuilder::new, (sb, v) -> sb.append(v).append(" -> "), StringBuilder::append)
        .append(" in ").append(weight).append("\n").toString();
  }

  public boolean exists ()
  {
    return true;
  }
}
