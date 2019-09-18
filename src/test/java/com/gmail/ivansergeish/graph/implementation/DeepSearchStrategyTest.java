package com.gmail.ivansergeish.graph.implementation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.gmail.ivansergeish.graph.api.Edge;

public class DeepSearchStrategyTest {

	private List<String> vertexes;
	List<Edge<String>> edges;
	private List<Edge<String>> path;
	private Map<String, List<Edge<String>>> map = new HashMap<>();
	private static final DeepSearchStrategy<String> STRATEGY = new DeepSearchStrategy<String>();
		
	
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
		map = getMap();
		
	}
	
	private Map<String, List<Edge<String>>> getMap() {
		Map<String, List<Edge<String>>> map = new HashMap<>();
		map.put("1", new ArrayList<Edge<String>>(Arrays.asList(
				new SimpleEdge<String>("1", "2", true),
				new SimpleEdge<String>("1", "7", true))));
		map.put("2", new ArrayList<Edge<String>>(Arrays.asList(
				new SimpleEdge<String>("1", "2", true),
				new SimpleEdge<String>("2", "3", true),
				new SimpleEdge<String>("2", "4", true))));
		map.put("3", new ArrayList<Edge<String>>(Arrays.asList(
				new SimpleEdge<String>("3", "5", true),
				new SimpleEdge<String>("2", "3", true))));
		map.put("4", new ArrayList<Edge<String>>(Arrays.asList(
				new SimpleEdge<String>("4", "5", true),
				new SimpleEdge<String>("2", "4", true))));
		map.put("5", new ArrayList<Edge<String>>(Arrays.asList(
				new SimpleEdge<String>("5", "6", true),
				new SimpleEdge<String>("3", "5", true),
				new SimpleEdge<String>("4", "5", true))));
		map.put("6", new ArrayList<Edge<String>>((Arrays.asList(
				new SimpleEdge<String>("5", "6", true)))));
		map.put("7", new ArrayList<Edge<String>>((Arrays.asList(
				new SimpleEdge<String>("1", "7", true)))));
		return map;
	}
	
	 
	private List<Edge<String>> prepareNeighboringUniDirectionalEdges() {
		return new ArrayList<Edge<String>>(Arrays.asList(new SimpleEdge<String>("1", "2", false),
				new SimpleEdge<String>("2", "3", false)));
	}
	
	private List<Edge<String>> prepareNeighboringBiDirectionalEdges() {
		return new ArrayList<Edge<String>>(Arrays.asList(new SimpleEdge<String>("1", "2", true),
				new SimpleEdge<String>("2", "3", true)));
	}
	
	@Test
	public void testDfsBiDirectional1() {
		List<Edge<String>> path = STRATEGY.search(vertexes.get(0), vertexes.get(6), map);
		Assert.assertTrue(path.size() == 1);
		path = STRATEGY.search(vertexes.get(0), vertexes.get(5), map);
		Assert.assertTrue(path.size() == 4);
		path = STRATEGY.search("2", "7", map);
		Assert.assertEquals(2, path.size());
	}

	@Test
	public void testDfsBiDirectional2() {
		List<Edge<String>> path = STRATEGY.search(vertexes.get(0), vertexes.get(6), map);
		Assert.assertTrue(path.size() == 1);
		path = STRATEGY.search(vertexes.get(0), vertexes.get(5), map);
		Assert.assertTrue(path.size() == 4);
		path = STRATEGY.search("1", "6", map);
		Assert.assertEquals(4, path.size());
	}

	@Test
	public void testDfsBiDirectional3() {
		List<Edge<String>> path = STRATEGY.search(vertexes.get(0), vertexes.get(6), map);
		Assert.assertTrue(path.size() == 1);
		path = STRATEGY.search(vertexes.get(0), vertexes.get(5), map);
		Assert.assertTrue(path.size() == 4);
		path = STRATEGY.search("2", "4", map);
		Assert.assertEquals(1, path.size());
	}
	@Test(expected = IllegalArgumentException.class)
	public void testSearchNullStart() {
		STRATEGY.search(null, "1", map);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSearchNullFinish() {
		STRATEGY.search("1", null, map);
	}
	
    @Test
    public void testfindNonVisitedNextLevelVertexBiDirectionalCase() {
		Assert.assertTrue(STRATEGY.findNonVisitedNextLevelVertex(edges, edges, "1").size() == 0);
		Assert.assertTrue(STRATEGY.findNonVisitedNextLevelVertex(edges, path, "3").size() == 0);
		Assert.assertEquals(1, STRATEGY.findNonVisitedNextLevelVertex(edges, path, "2").size());
    }
    
    @Test
    public void testfindNonVisitedNextLevelVertexUniDirectionalCase() {
		List<Edge<String>> edges = new ArrayList<Edge<String>>(Arrays.asList(new SimpleEdge<String>("1","2",false),
				new SimpleEdge<String>("2", "3", false),
				new SimpleEdge<String>("3", "4", false),
				new SimpleEdge<String>("2", "5", false)));
		List<Edge<String>> visitedEdges = new ArrayList<Edge<String>>(Arrays.asList(new SimpleEdge<String>("1","2",false),
				new SimpleEdge<String>("2", "3", false),
				new SimpleEdge<String>("3", "4", false),
				new SimpleEdge<String>("2", "5", false)));		
		Assert.assertTrue(STRATEGY.findNonVisitedNextLevelVertex(edges, visitedEdges, "1").size() == 0);
		Assert.assertTrue(STRATEGY.findNonVisitedNextLevelVertex(edges, visitedEdges, "2").size() == 0);
		visitedEdges = new ArrayList<Edge<String>>(Arrays.asList(new SimpleEdge<String>("1","2",false)));
		Assert.assertEquals(2, STRATEGY.findNonVisitedNextLevelVertex(edges, visitedEdges, "2").size());
    }    
    
    @Test
    public void testFindEdgeToNextVertex() {
		List<Edge<String>> edges = prepareNeighboringUniDirectionalEdges();
		Assert.assertEquals(new SimpleEdge<String>("2", "3" , false), STRATEGY.findEdgeToNextVertex(edges, "3"));
    }
    
    @Test(expected=NoSuchElementException.class)
    public void testFindEdgeToNextVertexNoSuchElement() {
    	List<Edge<String>> edges = prepareNeighboringUniDirectionalEdges();
		Assert.assertEquals(new SimpleEdge<String>("2", "3" , false), STRATEGY.findEdgeToNextVertex(edges, "1"));
    } 
    
    @Test
    public void testFindPreviousVertexUniDirectionalCase() {
		List<Edge<String>> edges = prepareNeighboringUniDirectionalEdges();
		Assert.assertEquals(STRATEGY.findPreviousVertex(edges, "3"), "2");
    }
    
    @Test
    public void testFindPreviousVertexBiDirectionalCase() {
		List<Edge<String>> edges = prepareNeighboringBiDirectionalEdges();
		Assert.assertEquals(STRATEGY.findPreviousVertex(edges, "3"), "2");
    }    
    @Test(expected=IllegalArgumentException.class)
    public void testFindPreviousVertexShortRingInPath() {
		List<Edge<String>> edges = new ArrayList<Edge<String>>(Arrays.asList(new SimpleEdge<String>("1", "2", false),
				new SimpleEdge<String>("2", "3",false), new SimpleEdge<String>("3", "3",false)));
		Assert.assertEquals(STRATEGY.findPreviousVertex(edges, "3"), "2");
    }
    @Test
    public void testFindPreviousVertexLongRingInPath() {
		List<Edge<String>> edges = new ArrayList<Edge<String>>(Arrays.asList(new SimpleEdge<String>("1", "2", false),
				new SimpleEdge<String>("2", "3",false), new SimpleEdge<String>("3", "1",false)));
		Assert.assertEquals(STRATEGY.findPreviousVertex(edges, "1"), "3");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testFindPreviousVertexSeparatedVertex() {
		List<Edge<String>> edges = prepareNeighboringUniDirectionalEdges();
		Assert.assertEquals(STRATEGY.findPreviousVertex(edges, "11"), "3");
    }     

}
