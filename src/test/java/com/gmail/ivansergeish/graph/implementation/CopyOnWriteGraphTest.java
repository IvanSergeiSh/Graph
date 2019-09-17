package com.gmail.ivansergeish.graph.implementation;

public class CopyOnWriteGraphTest extends SimpleGraphTest {
	@Override
	protected AbstractGraph<String> getGraph() {
		return new CopyOnWriteGraph<String>(new DeepSearchStrategy<String>());
	}
}
