package de.guerz.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Entity
public class Chart implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer Id;
	private String name;
	private List<ChartData> data;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Chart chart = (Chart) o;

		if (name != null ? !name.equals(chart.name) : chart.name != null) return false;
		return data != null ? data.equals(chart.data) : chart.data == null;

	}

	@Override
	public int hashCode() {
		int result = name != null ? name.hashCode() : 0;
		result = 31 * result + (data != null ? data.hashCode() : 0);
		return result;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "chart_fk")
	public List<ChartData> getData() {
		return data;
	}

	public void setData(List<ChartData> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return Id + ", " + name + ", " + data;
	}

}
