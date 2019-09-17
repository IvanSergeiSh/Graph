package com.gmail.ivansergeish.graph.implementation;

import org.junit.Assert;
import org.junit.Test;


public class SimpleEdgeTest {
    private SimpleEdge<String> biDirectEdge1 = new SimpleEdge<>("1", "2", true);
    private SimpleEdge<String> biDirectEdge2 = new SimpleEdge<>("2", "1", true);

    private SimpleEdge<String> uniDirectEdge1 = new SimpleEdge<>("1", "2", false);
    private SimpleEdge<String> uniDirectEdge2 = new SimpleEdge<>("2", "1", false);
    @Test
    public void testEquals() {
    	Assert.assertNotEquals(biDirectEdge1, null);
    	Assert.assertEquals(biDirectEdge1, biDirectEdge1);
    	Assert.assertEquals(biDirectEdge1, biDirectEdge2);
    	Assert.assertEquals(biDirectEdge1, uniDirectEdge2);
    	Assert.assertEquals(uniDirectEdge1, biDirectEdge2);
    	Assert.assertNotEquals(uniDirectEdge1, uniDirectEdge2);
    }
}
