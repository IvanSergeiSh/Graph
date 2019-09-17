package com.gmail.ivansergeish.graph.implementation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.gmail.ivansergeish.graph.api.Edge;

public class DeepSearchStrategyUniDirectionalTest {

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
				new SimpleEdge<String>("1","2",false),
				new SimpleEdge<String>("1","7",false))));
		map.put("2", new ArrayList<Edge<String>>(Arrays.asList(
				new SimpleEdge<String>("2","3",false),
				new SimpleEdge<String>("2","4",false))));
		map.put("3", new ArrayList<Edge<String>>(Arrays.asList(
				new SimpleEdge<String>("3","5",false))));
		map.put("4", new ArrayList<Edge<String>>(Arrays.asList(
				new SimpleEdge<String>("4","5",false))));
		map.put("5", new ArrayList<Edge<String>>(Arrays.asList(
				new SimpleEdge<String>("5","6",false))));
		map.put("6", new ArrayList<Edge<String>>((Collections.emptyList())));
		map.put("7", new ArrayList<Edge<String>>((Collections.emptyList())));
		return map;
	}
	
	
	@Test
	public void testDfsBiDirectional1() {
		List<Edge<String>> path = STRATEGY.search(vertexes.get(0), vertexes.get(6), map);
		Assert.assertTrue(path.size() == 1);
		path = STRATEGY.search("1", "2", map);
		Assert.assertTrue(path.size() == 1);
		path = STRATEGY.search("1", "7", map);
		Assert.assertEquals(1, path.size());
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testUnableToFindPath() {
		path = STRATEGY.search("7", "1", map);
	}
	
	 

}
