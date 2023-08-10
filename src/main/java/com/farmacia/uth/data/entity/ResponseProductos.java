package com.farmacia.uth.data.entity;

import java.util.List;

public class ResponseProductos {
	List<Productos> items;
	boolean hasMore;
	int limit;
	public List<Productos> getItems() {
		return items;
	}
	public void setItems(List<Productos> items) {
		this.items = items;
	}
	public boolean isHasMore() {
		return hasMore;
	}
	public void setHasMore(boolean hasMore) {
		this.hasMore = hasMore;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	
}
