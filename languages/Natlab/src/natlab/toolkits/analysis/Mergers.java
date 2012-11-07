package natlab.toolkits.analysis;

import java.util.Set;


import com.google.common.collect.Sets;

/**
 * Some useful mergers.
 * @author isbadawi
 */
public class Mergers {
  /**
   * Returns a merger on sets that performs set union.
   */
  public static <T> Merger<Set<T>> union() {
    return new Merger<Set<T>>() {
      @Override public Set<T> merge(Set<T> o1, Set<T> o2) {
        Set<T> result = Sets.newHashSet(o1);
        result.addAll(o2);
        return result;
      }
    };
  }

  /**
   * Returns a merger on sets that performs set intersection.
   */
  public static <T> Merger<Set<T>> intersection() {
    return new Merger<Set<T>>() {
      @Override public Set<T> merge(Set<T> o1, Set<T> o2) {
        Set<T> result = Sets.newHashSet(o1);
        result.retainAll(o2);
        return result;
      }
    };
  }
  private Mergers() {}
}
