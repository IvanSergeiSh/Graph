package com.gmail.ivansergeish.graph.api;

import java.util.List;

public interface Graph<T> {
	/**
	 * The method is intended to add a single vertex to graph
	 * @param vertex
	 */
	 public void addVertex(T vertex);
	 /**
	  * the method is intended to add edge,
	  *  which contains two vertexes to the graph.
	  * each of vertexes should be already added to the graph
	  * @param edge
	  */
     public void addEdge(Edge<T> edge);
/**
 * 
 * @param start
 * @param finish
 * @return path as a list of Edge or throws NoSuchElementException,
      * if it's impossible to find a path.
 */
     public List<Edge<T>> getPath(T start, T finish);

}
