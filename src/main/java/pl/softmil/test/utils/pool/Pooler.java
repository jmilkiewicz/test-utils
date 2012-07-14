package pl.softmil.test.utils.pool;

import java.util.*;

public class Pooler<T> {
    private final Random random = new Random();
    private final List<T> toPoolFrom;

    public Pooler(Collection<T> poolFrom) {
        toPoolFrom = new LinkedList<T>(poolFrom);
    }

    public List<T> poolXElements(int x) {
        if (x > toPoolFrom.size()) {
            throw new IllegalArgumentException("can not pool " + x
                    + " elements from collection of size" + toPoolFrom.size());
        }
        if (x == toPoolFrom.size()) {
            return new LinkedList<T>(toPoolFrom);
        }
        Set<Integer> indexes = poolXIndexes(x);
        return getElementsByIndex(indexes);
    }

    private List<T> getElementsByIndex(Set<Integer> indexes) {
        List<T> result = new LinkedList<T>();
        for (Integer index : indexes) {
            result.add(toPoolFrom.get(index));
        }
        return result;
    }

    private Set<Integer> poolXIndexes(int x) {
        Set<Integer> result = new HashSet<Integer>();
        while (result.size() < x) {
            result.add(poolIndex(toPoolFrom));
        }
        return result;
    }

    private int poolIndex(List<T> collectionAsList) {
        return random.nextInt(collectionAsList.size());
    }

}
