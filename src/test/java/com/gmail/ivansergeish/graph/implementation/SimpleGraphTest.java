package com.gmail.ivansergeish.graph.implementation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.gmail.ivansergeish.graph.api.Edge;
import com.gmail.ivansergeish.graph.api.Graph;



public class SimpleGraphTest extends AbstractGraphTest{

	//private List<String> vertexes;
	//List<Edge<String>> edges;
	@SuppressWarnings("unused")
	private List<Edge<String>> path;
	
	@Override
	protected AbstractGraph<String> getGraph() {
		return new SimpleGraph<String>(new DeepSearchStrategy<String>());
	}
	
	
	/**
	 *          Test graph
	 *          
	 *               1 - 7
	 *               |
	 *               2
	 *              / \
	 *             3   4
	 *              \ /
	 *               5
	 *               |
	 *               6
	 */
	@Before
	public void init() {
		vertexes = new ArrayList<String>(Arrays.asList("1", "2", "3", "4", "5", "6", "7"));
		edges = new ArrayList<Edge<String>>(Arrays.asList(
				new SimpleEdge<String>("1","2",true),
				new SimpleEdge<String>("2","3",true),
				new SimpleEdge<String>("2","4",true),
				new SimpleEdge<String>("3","5",true),
				new SimpleEdge<String>("4","5",true),
				new SimpleEdge<String>("5","6",true),
				new SimpleEdge<String>("1","7",true)));
		path = new ArrayList<Edge<String>>(Arrays.asList(
				new SimpleEdge<String>("1","2",true),
				new SimpleEdge<String>("2","3",true),
				new SimpleEdge<String>("3","5",true)));		
	}
	
	
	 
//	@Test
//	public void testDfsBiDirectional() {
//		Graph<String> graph = prepareGraph();
//		List<Edge<String>> path = graph.getPath(vertexes.get(0), vertexes.get(6));
//		Assert.assertTrue(path.size() == 1);
//		path = graph.getPath(vertexes.get(0), vertexes.get(5));
//		Assert.assertTrue(path.size() == 4);
//		path = graph.getPath("2", "7");
//		Assert.assertEquals(2, path.size());
//	}
}