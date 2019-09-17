package com.gmail.ivansergeish.graph.implementation;

import com.gmail.ivansergeish.graph.api.Edge;

public class SimpleEdge<T> implements Edge<T> {
    private T v1;
    private T v2;
    private boolean isBiDirectional;

    public SimpleEdge(final Edge<T> e) {
    	this(e.getV1(), e.getV2(), e.isBiDirectional());
    }

    public SimpleEdge(final T v1, final T v2, final boolean isBiDirectional) {
    	if (v1 == null || v2 == null) {
    		throw new IllegalArgumentException("vertexes shouldnt be null");
    	}
    	this.v1 = v1;
    	this.v2 = v2;
    	this.isBiDirectional = isBiDirectional;
    }

	@Override
	public final T getV1() {
		return v1;
	}

	@Override
	public final T getV2() {
		return v2;
	}

	@Override
	public boolean isBiDirectional() {
		return isBiDirectional;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isBiDirectional ? 1231 : 1237);
		result = prime * result + ((v1 == null) ? 0 : v1.hashCode());
		result = prime * result + ((v2 == null) ? 0 : v2.hashCode());
		return result;
	}
	/**
	 * In addition to usual equals behavior the method
	 *  should return true in the following cases:
	 * 1) this(1, 2, true) other(2, 1, true)
	 * 2) this(1, 2, true) other(2, 1, false)
	 * 3) this(1, 2, false) other(2, 1, true)
	 * And it should return false if, for example
	 * 1) this(1, 2, false) other(2, 1, false)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("rawtypes")
		SimpleEdge other = (SimpleEdge) obj;
		if (v1 == null || v2 == null || other.v1 == null || other.v2 == null) {
				return false;
		}
		if (v1.equals(other.v1) && (v2.equals(other.v2))) {
			return true;
		}
		if (v1.equals(other.v2) && v2.equals(other.v1) && (isBiDirectional || other.isBiDirectional)) {
			return true;
		}
		return false;
	}

}
