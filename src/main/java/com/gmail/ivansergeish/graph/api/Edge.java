package com.gmail.ivansergeish.graph.api;
/**
 * in case of bidirectional edge, 
 * equals should return the same values regardless of order of v1,v2.
 * @author ivan
 *
 * @param <T> - implements immutable interface.
 */
public interface Edge<T> {
	public T getV1();
	public T getV2();
	public boolean isBiDirectional();
}
