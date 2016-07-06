package com.stocks.StockTracker.application;

import java.util.List;

public class Test {
	private List<String> auto;

	public List<String> getAuto() {
		return auto;
	}

	public void setAuto(List<String> auto) {
		this.auto = auto;
	}

	@Override
	public String toString() {
		return "Test [auto=" + auto + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((auto == null) ? 0 : auto.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Test other = (Test) obj;
		if (auto == null) {
			if (other.auto != null)
				return false;
		} else if (!auto.equals(other.auto))
			return false;
		return true;
	}
}
