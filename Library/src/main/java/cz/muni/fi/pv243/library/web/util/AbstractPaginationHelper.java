package cz.muni.fi.pv243.library.web.util;

import javax.faces.model.DataModel;

public abstract class AbstractPaginationHelper {
	public static final int DEFAULT_SIZE = 10;
	private transient int page;
	private transient int pageSize;

	public AbstractPaginationHelper(int pageSize) {
		this.pageSize = pageSize;
	}

	public abstract int getItemsCount();

	public abstract DataModel createPageDataModel();

	public int getPageFirstItem() {
		return this.page * this.pageSize;
	}

	public int getPageLastItem() {
		int i = (getPageFirstItem() + this.pageSize) - 1;
		int count = getItemsCount() - 1;

		if (i > count) {
			i = count;
		}

		if (i < 0) {
			i = 0;
		}

		return i;
	}

	public boolean isHasNextPage() {
		return (((this.page + 1) * this.pageSize) + 1) <= getItemsCount();
	}

	public void nextPage() {
		if (isHasNextPage()) {
			this.page++;
		}
	}

	public boolean isHasPreviousPage() {
		return this.page > 0;
	}

	public void previousPage() {
		if (isHasPreviousPage()) {
			this.page--;
		}
	}

	public int getPageSize() {
		return this.pageSize;
	}

}