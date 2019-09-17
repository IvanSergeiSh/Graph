package com.gmail.ivansergeish.graph.implementation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

import com.gmail.ivansergeish.graph.api.Edge;
import com.gmail.ivansergeish.graph.api.Graph;

public abstract class AbstractGraphTest {
    List<String> vertexes;
    List<Edge<String>> edges;
    protected abstract AbstractGraph<String> getGraph();
    //protected abstract Graph<String> prepareGraph();
    
	protected List<String> getTestVertexes() {
		return new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5", "6"));
	}
	
	protected Graph<String> prepareGraph(){
		Graph<String> graph = getGraph();
		fillGraph(graph);
		return graph;
	}

//	private List<Edge<String>> getOKTestEdges() {
//		return new ArrayList<>(Arrays.asList(new SimpleEdge<>("1","2", true), new SimpleEdge<>("2", "3", true)));
//	}
	
	private List<Edge<String>> getDuplicatedTestEdges() {
		return new ArrayList<>(Arrays.asList(new SimpleEdge<>("1","2", true), new SimpleEdge<>("1", "2", true)));
	}	
	
	private List<Edge<String>> getDuplicatedReverseOrderTestEdges() {
		return new ArrayList<>(Arrays.asList(new SimpleEdge<>("1", "2", true), new SimpleEdge<>("2", "1", true)));
	}

//	private List<Edge<String>> getNonDuplicatedReverseOrderTestEdges() {
//		return new ArrayList<>(Arrays.asList(new SimpleEdge<>("1", "2", false), new SimpleEdge<>("2", "1", false)));
//	}

	protected List<Edge<String>> getUniDirectionalTestEdges() {
		return new ArrayList<>(Arrays.asList(new SimpleEdge<>("1","2", false),new SimpleEdge<>("2","3", false)));
	}
	
	protected void fillGraph(Graph<String> graph) {
		graph.addVertex(vertexes.get(0));
		graph.addVertex(vertexes.get(1));
		graph.addVertex(vertexes.get(2));
		graph.addVertex(vertexes.get(3));
		graph.addVertex(vertexes.get(4));
		graph.addVertex(vertexes.get(5));
		graph.addVertex(vertexes.get(6));
		graph.addEdge(edges.get(0));
		graph.addEdge(edges.get(1));
		graph.addEdge(edges.get(2));
		graph.addEdge(edges.get(3));
		graph.addEdge(edges.get(4));
		graph.addEdge(edges.get(5));
		graph.addEdge(edges.get(6));		
	}	
	
	private List<Edge<String>> getBiDirectionalTestEdges() {
		return new ArrayList<>(Arrays.asList(new SimpleEdge<>("1","2", true),new SimpleEdge<>("2","3", true)));
	}	
	
//	private List<Edge<String>> getNullTestEdge() {
//		return  new ArrayList<>(Arrays.asList(null));
//	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAddNullVertex() {
		getGraph().addVertex(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddNullEdge() {
		Graph<String> graph = getGraph();
		graph.addVertex(getTestVertexes().get(0));
		graph.addEdge(null);
	}
	@Test(expected = IllegalArgumentException.class)
	public void testAddDuplicatedVertex() {
		Graph<String> graph = getGraph();
		graph.addVertex(getTestVertexes().get(0));
		graph.addVertex(getTestVertexes().get(0));
	}	

	@Test(expected = IllegalArgumentException.class)
	public void testAddDuplicatedEdge() {
		Graph<String> graph = getGraph();
		List<Edge<String>> edges = getDuplicatedTestEdges();
		graph.addVertex(getTestVertexes().get(0));
		graph.addEdge(edges.get(0));
		graph.addEdge(edges.get(1));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddDuplicatedReverseOrderEdge() {
		Graph<String> graph = getGraph();
		List<String> vertexes = getTestVertexes();
		List<Edge<String>> edges = getDuplicatedReverseOrderTestEdges();
		graph.addVertex(vertexes.get(0));
		graph.addVertex(vertexes.get(1));
		graph.addVertex(vertexes.get(2));
		graph.addEdge(edges.get(0));
		graph.addEdge(edges.get(1));
	}
    //TODO 
	@Test
	public void testAddBiDirectionalEdge() {
		AbstractGraph<String> graph = getGraph();
		List<String> vertexes = getTestVertexes();
		List<Edge<String>> edges = getBiDirectionalTestEdges();
		graph.addVertex(vertexes.get(0));
		graph.addVertex(vertexes.get(1));
		graph.addVertex(vertexes.get(2));		
		graph.addEdge(edges.get(0));
		graph.addEdge(edges.get(1));

		Assert.assertTrue((graph.getAdjVertices().get(vertexes.get(0))).size() == 1);
		Assert.assertTrue((graph.getAdjVertices().get(vertexes.get(0))).contains(edges.get(0)));
		Assert.assertTrue((graph.getAdjVertices().get(vertexes.get(1))).size() == 2);
		Assert.assertTrue((graph.getAdjVertices().get(vertexes.get(1))).contains(edges.get(0)));
		Assert.assertTrue((graph.getAdjVertices().get(vertexes.get(1))).contains(edges.get(1)));
		Assert.assertTrue((graph.getAdjVertices().get(vertexes.get(2))).size() == 1);
		Assert.assertTrue((graph.getAdjVertices().get(vertexes.get(2))).contains(edges.get(1)));
	}

	@Test
	public void testAddUniDirectionalEdge() {
		AbstractGraph<String> graph = getGraph();
		List<String> vertexes = getTestVertexes();
		List<Edge<String>> edges = getUniDirectionalTestEdges();
		graph.addVertex(vertexes.get(0));
		graph.addVertex(vertexes.get(1));
		graph.addVertex(vertexes.get(2));		
		graph.addEdge(edges.get(0));
		graph.addEdge(edges.get(1));

		Assert.assertTrue((graph.getAdjVertices().get(vertexes.get(0))).size() == 1);
		Assert.assertTrue((graph.getAdjVertices().get(vertexes.get(0))).contains(edges.get(0)));
		Assert.assertTrue((graph.getAdjVertices().get(vertexes.get(1))).size() == 1);
				Assert.assertTrue((graph.getAdjVertices().get(vertexes.get(1))).contains(edges.get(1)));
		Assert.assertTrue((graph.getAdjVertices().get(vertexes.get(2))).size() == 0);
	}
	
	@Test
	public void testDfsBiDirectional() {
		Graph<String> graph = prepareGraph();
		List<Edge<String>> path = graph.getPath(vertexes.get(0), vertexes.get(6));
		Assert.assertTrue(path.size() == 1);
		path = graph.getPath(vertexes.get(0), vertexes.get(5));
		Assert.assertTrue(path.size() == 4);
		path = graph.getPath("2", "7");
		Assert.assertEquals(2, path.size());
	}	
}
