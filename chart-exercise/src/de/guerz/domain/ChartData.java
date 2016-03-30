package de.guerz.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ChartData implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private double weight;

	public ChartData(String name, double weight) {
		this.name = name;
		this.weight = weight;
	}

	public ChartData() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return name + ", " + weight;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ChartData chartData = (ChartData) o;

		if (Double.compare(chartData.weight, weight) != 0) return false;
		return name != null ? name.equals(chartData.name) : chartData.name == null;

	}

	@Override
	public int hashCode() {
		int result;
		long temp;
		result = name != null ? name.hashCode() : 0;
		temp = Double.doubleToLongBits(weight);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
}
