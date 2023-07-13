package com.farmacia.uth.data.entity;

import java.util.List;

public class ResponseMedicamentos {
	private List<Medicamento> items;
	private boolean hasMore;
	private int count;
	public List<Medicamento> getItems() {
		return items;
	}
	public void setItems(List<Medicamento> items) {
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
