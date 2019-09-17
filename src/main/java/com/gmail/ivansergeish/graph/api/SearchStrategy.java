package com.gmail.ivansergeish.graph.api;

import java.util.List;
import java.util.Map;

public interface SearchStrategy<T> {
/**
 *
 * @param start
 * @param finish
 * @param adjVertices
 * @return list of Edges as a path from start to finish
 * if unable to find path throws NoSuchElementException
 * if path is empty => start = finish
 */
    public List<Edge<T>> search(T start, T finish, Map<T,
    		List<Edge<T>>> adjVertices);
}
