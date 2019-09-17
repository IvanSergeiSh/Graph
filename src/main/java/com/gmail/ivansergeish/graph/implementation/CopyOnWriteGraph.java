package com.gmail.ivansergeish.graph.implementation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import com.gmail.ivansergeish.graph.api.Edge;
import com.gmail.ivansergeish.graph.api.SearchStrategy;

import java.util.concurrent.atomic.AtomicReference;
/**
 * This is a copy-on-write implementation of graph.
 * @author ivan
 * @param <T>
 */
public class CopyOnWriteGraph<T> extends AbstractGraph<T> {
    private SearchStrategy<T> strategy;
    private AtomicReference<Map<T, List<Edge<T>>>> adjVertices;
    private Map<T, List<Edge<T>>> tempMap;
    private ReentrantLock lock;

    public CopyOnWriteGraph(final SearchStrategy<T> strategy) {
    	if (strategy == null) {
    		throw new IllegalArgumentException("Strategy shouldn't be null");
    	}
    	this.strategy = strategy;
    	this.adjVertices = new AtomicReference<>(new HashMap<>());
    	lock = new ReentrantLock();
    }

    @Override
    public void addVertex(T vertex) {
    	lock.lock();
    	super.addVertex(vertex);
    	this.adjVertices.set(tempMap);
    	lock.unlock();
    }

    @Override
    public void addEdge(Edge<T> edge) {
    	lock.lock();
    	super.addEdge(edge);
    	this.adjVertices.set(tempMap);
    	lock.unlock();
    }

	@Override
	public List<Edge<T>> getPath(T start, T finish) {
		return strategy.search(start, finish, adjVertices.get());
	}

	@Override
	protected Map<T, List<Edge<T>>> getAdjVertices() {
		Map<T, List<Edge<T>>> map = adjVertices.get();
		tempMap = new HashMap<>(map.size());
		map.forEach((key, value) -> {
			tempMap.put(key, copyList(value));
		});
		return tempMap;
	}

	private List<Edge<T>> copyList(List<Edge<T>> list) {
		return list.stream().map(e -> new SimpleEdge<>(e))
				.collect(Collectors.toList());
	}

}
