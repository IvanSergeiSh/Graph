package com.gmail.ivansergeish.graph.implementation;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;

import com.gmail.ivansergeish.graph.api.Edge;
import com.gmail.ivansergeish.graph.api.Graph;
import com.gmail.ivansergeish.graph.api.SearchStrategy;

public class CopyOnWriteGraphCuncurrentTest {
	
	@Test
	public void CopyOnWriteCuncurrentExecutionTest() throws InterruptedException, ExecutionException {
		ExecutorService service = Executors.newFixedThreadPool(100);
		CountDownLatch latch = new CountDownLatch(100);
	    Graph<String> graph = new CopyOnWriteGraph<String>(new TestStrategy<>());
	    List<CompletableFuture<Void>> list = IntStream.range(0, 100).boxed().map(i -> {
	    	 return CompletableFuture.runAsync(getTask(latch, graph), service);
	    }).collect(Collectors.toList());
	    CompletableFuture.allOf(list.toArray(new CompletableFuture[0])).join();
	    service.shutdown();
	}

	private Runnable getTask(CountDownLatch latch, Graph<String> graph) {
		Random r = new Random();
		if (r.nextInt() % 2 > 0) {
			return new PathFinder(latch, graph);
		} else {
			return new Pusher(latch, graph);
		}
	}

	private static class PathFinder implements Runnable{
        private CountDownLatch latch;
        private Graph<String> graph;
        public PathFinder(CountDownLatch latch, Graph<String> graph) {
        	this.latch = latch;
        	this.graph = graph;
        }
		@Override
		public void run() {
			latch.countDown();
			try {
				latch.await();
				graph.getPath("1", "2");
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			
		}
		
	}
	
	
	private static class Pusher implements Runnable{
        private CountDownLatch latch;
        private Graph<String> graph;
        public Pusher(CountDownLatch latch, Graph<String> graph) {
        	this.latch = latch;
        	this.graph = graph;
        }
		@Override
		public void run() {
			latch.countDown();
			try {
				latch.await();
				String v1 = Integer.toString(this.hashCode());
				String v2 = Integer.toString(this.hashCode()-1);
				graph.addVertex(v1);
				graph.addVertex(v2);
				graph.addEdge(new SimpleEdge<>(v1,v2, false));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	/**
	 * This strategy is intended to check if the behavior of graph is according to,
	 * CopyOnWrite pattern.
	 * @author ivan
	 */
	private static class TestStrategy<T> implements SearchStrategy<T>{

		@Override
		public List<Edge<T>> search(T start, T finish, Map<T, List<Edge<T>>> adjVertices) {
			int count = adjVertices.size();
			try {
				Thread.sleep(100);
				System.out.println(Thread.currentThread());
			} catch(InterruptedException e) {
				System.out.println(e);
			}
			if (count != adjVertices.size()) {
				throw new RuntimeException("Internal state of graph was changed somehow");
			}
			return Collections.emptyList();
		}
		
	}
}
