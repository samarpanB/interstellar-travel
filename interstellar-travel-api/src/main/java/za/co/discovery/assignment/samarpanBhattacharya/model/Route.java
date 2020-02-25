package za.co.discovery.assignment.samarpanBhattacharya.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "route")
public class Route implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	private Planet source;

	@ManyToOne
	private Planet destination;

	@Column(name = "distance", nullable = true)
	private Float distance;

	@Column(name = "traffic")
	private Float traffic;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Planet getSource() {
		return source;
	}

	public void setSource(Planet source) {
		this.source = source;
	}

	public Planet getDestination() {
		return destination;
	}

	public void setDestination(Planet destination) {
		this.destination = destination;
	}

	public Float getDistance() {
		return distance;
	}

	public void setDistance(Float distance) {
		this.distance = distance;
	}

	public Float getTraffic() {
		return traffic;
	}

	public void setTraffic(Float traffic) {
		this.traffic = traffic;
	}

	@Override
	public String toString() {
		return "Route [id=" + id
			+ ", source=" + source
			+ ", destination=" + destination
			+ ", distance=" + distance
			+ ", traffic=" + traffic
			+ "]";
	}
}
