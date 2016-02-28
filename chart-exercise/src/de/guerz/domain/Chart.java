package de.guerz.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Chart implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer Id;
	private String name;
	private List<ChartData> data;
	
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
		return Id + ", " + name;
	}

}
