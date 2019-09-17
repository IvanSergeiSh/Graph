package com.gmail.ivansergeish.graph.implementation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gmail.ivansergeish.graph.api.Edge;
import com.gmail.ivansergeish.graph.api.SearchStrategy;

/**
 *
 *  Simple non-thread-safe implementation of Graph.
 * @author ivan
 *
 * @param <T>
 */
public class SimpleGraph<T> extends AbstractGraph<T> {

	private static final String NULL_STRATEGY = "SearchStrategy shouldn't be null";

	private Map<T, List<Edge<T>>> adjVertices;
	private SearchStrategy<T> strategy;

	public SimpleGraph(final SearchStrategy<T> strategy) {
		if (strategy == null) {
			throw new IllegalArgumentException(NULL_STRATEGY);
		}
		adjVertices = new HashMap<>();
		this.strategy = strategy;
	}

	@Override
	public List<Edge<T>> getPath(final T start, final T finish) {
		return strategy.search(start, finish, getAdjVertices());
	}

	@Override
	protected Map<T, List<Edge<T>>> getAdjVertices() {
		return this.adjVertices;
	}
}
