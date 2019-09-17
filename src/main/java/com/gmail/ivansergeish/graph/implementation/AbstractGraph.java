package com.gmail.ivansergeish.graph.implementation;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.gmail.ivansergeish.graph.api.Edge;
import com.gmail.ivansergeish.graph.api.Graph;

/**
 * This class contains a common implementation of basic add-methods.
 * Shch as assVertexe and addEdge.
 * @author ivan
 *
 * @param <T>
 */
public abstract class AbstractGraph<T> implements Graph<T> {

/**
 * The method is intended to add vertex to graph.
	 * The vertex shouldn't be null.
	 * The vertex shouldn't be already added to graph.
	 * @param vertex
	 */
	public void addVertex(final T vertex) {
		Map<T, List<Edge<T>>> adjVertices = getAdjVertices();
		if (vertex == null || adjVertices.containsKey(vertex)) {
			throw new IllegalArgumentException(
					"Vertex shouldn't be null or shouldn't be allready added to graph");
		}
		adjVertices.put(vertex, new LinkedList<>());
	}
	/**
	  * If the edge is biDirectional then current
	  *  edge should be added to both vertexes.
	 */
	public void addEdge(Edge<T> edge) {
		Map<T, List<Edge<T>>> adjVertices = getAdjVertices();
		if (edge == null) {
			throw new IllegalArgumentException("Edge shouldn't be null");
		}
		if (!adjVertices.containsKey(edge.getV1())
				|| !adjVertices.containsKey(edge.getV2())) {
			throw new IllegalArgumentException("No such vertex");
		}
		if (adjVertices.get(edge.getV1()).contains(edge)) {
			throw new IllegalArgumentException("The edge allready exists");
		}
		adjVertices.get(edge.getV1()).add(edge);
		if (edge.isBiDirectional()) {
			adjVertices.get(edge.getV2()).add(edge);
		}
	}

	protected abstract Map<T, List<Edge<T>>> getAdjVertices();

}
