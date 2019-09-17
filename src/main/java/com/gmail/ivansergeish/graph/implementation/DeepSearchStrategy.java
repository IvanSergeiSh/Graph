package com.gmail.ivansergeish.graph.implementation;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import com.gmail.ivansergeish.graph.api.Edge;
import com.gmail.ivansergeish.graph.api.SearchStrategy;
//TODO javadoc
public class DeepSearchStrategy<T> implements SearchStrategy<T> {

	@Override
	public List<Edge<T>> search(final T start, final T finish, 
			final Map<T, List<Edge<T>>> adjVertices) {
		if (start == null || finish == null) {
			throw new IllegalArgumentException();
		}
		boolean end = false;
		List<Edge<T>> path = new LinkedList<>();
		T prevVertex = start;
		List<Edge<T>> visitedEdges = new LinkedList<>();
		while (!end) {
			List<T> nextVertexes = findNonVisitedNextLevelVertex(
					adjVertices.get(prevVertex), visitedEdges, prevVertex);
			//0) if there is any next-level-vertex - check it for finish vertex
			if (nextVertexes.contains(finish)) {
				path.add(findEdgeToNextVertex(adjVertices.get(prevVertex), finish));
				end = true;
				return path;
			}
			//1) if there is any non-checked next-level vertex then move to it
			//and add it to previousEdges
			if(!nextVertexes.isEmpty()) {
				Edge<T> nextEdge = findEdgeToNextVertex(
						adjVertices.get(prevVertex), nextVertexes.get(0));
				visitedEdges.add(nextEdge);
				//move to nextVertex
				prevVertex = nextVertexes.get(0);
				path.add(nextEdge);
			}
			//2) if there are no non-checked next-level
			//vertex then move to previous
			//if(nextVertexes.isEmpty()) {
			else {
				prevVertex = findPreviousVertex(path, prevVertex);
				path.remove(path.size() - 1);
			}
			//3) if there are no non-checked next-level vertex and
			//current vertex is start then end = true
			if (nextVertexes.isEmpty() && prevVertex.equals(start)) {
				end = true;
				return path;
			}
		}
		return path;
	}

	/**
	 * 
	 * @param edges - all edges of graph
	 * @param visitedEdges - all the visited edges = path
	 * @param currentVertex - current vertex
	 * @return - list of neighboring vertexes which hasn't been visited yet
	 */
	List<T> findNonVisitedNextLevelVertex(final List<Edge<T>> edges, 
			final List<Edge<T>> visitedEdges, final T currentVertex) {
		if (edges == null || edges.isEmpty()) {
			return Collections.emptyList();
		}
		return  edges.stream().filter((Edge<T> edge) -> {
			return !visitedEdges.contains(edge);
		}).filter((edge) -> {
			if (edge.getV1().equals(edge.getV2())) {
				return false;
			}
			if((edge.getV1().equals(currentVertex)) 
					|| (edge.getV2().equals(currentVertex) 
							&& edge.isBiDirectional())) {
				return true;
			}
			return false;
		}).map((edge) -> {
			if (edge.getV1().equals(currentVertex)) { return edge.getV2();}
			return edge.getV1();
		}).collect(Collectors.toList());
	}
	
	/**
	 * 
	 * @param edges - edges neighboring to one current vertex
	 * @param nextVertex - target vertex 
	 * @return edge from current vertex to nextVertex or throws NoSuchElementException
	 */
	Edge<T> findEdgeToNextVertex(final List<Edge<T>> edges, final T nextVertex) {
		if (nextVertex == null || edges == null) {
			throw new IllegalArgumentException("neither vertex nor edge should be null");
		}
		return edges.stream().filter((edge) -> {
			if (edge.getV2().equals(nextVertex)) { return true;}
			return (edge.getV1().equals(nextVertex) && edge.isBiDirectional());
		}).findFirst().get();
	}

	T findPreviousVertex(final List<Edge<T>> path, final T currentVertex) {
		if (path == null || currentVertex == null) {
			throw new IllegalArgumentException(
					"Neither path nor currentVertex should be neither null.");
		}
		if (path.isEmpty()) {
			throw new NoSuchElementException("Unable to find a path");
		}
		if (path.get(path.size() - 1).getV1().equals(currentVertex) 
				&& path.get(path.size() - 1).getV2().equals(currentVertex)) {
			throw new IllegalArgumentException("last edge in path is ring");
		}
		if (!path.get(path.size() - 1).getV1().equals(currentVertex) 
				&& !path.get(path.size() - 1).getV2().equals(currentVertex)) {
			throw new IllegalArgumentException("last edge in path does not contain current vertex");
		}		
		if (path.get(path.size() - 1).getV2().equals(currentVertex)) {
			return path.get(path.size() - 1).getV1();
		}
		if (path.get(path.size()-1).getV1().equals(currentVertex) 
				&& path.get(path.size() - 1).isBiDirectional()) {
			return path.get(path.size() - 1).getV2();
		}
		throw new IllegalArgumentException("unable to find previous vertex");
	}	

}
