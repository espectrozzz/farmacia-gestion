package com.farmacia.uth.data.entity;

import java.util.List;

public class ResponseMovimientos {
	private List<Movimiento> items;
	private boolean hasMore;
	private int count;
	public List<Movimiento> getItems() {
		return items;
	}
	public void setItems(List<Movimiento> items) {
		this.items = items;
	}
	public boolean isHasMore() {
		return hasMore;
	}
	public void setHasMore(boolean hasMore) {
		this.hasMore = hasMore;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
}
