//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.02.24 at 05:28:54 PM IST 
//
package za.co.discovery.assignment.samarpanBhattacharya.soap;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for anonymous complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RouteDetails" type="{https://www.discovery.co.za/ws/routes}RouteDetails"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
	"routeDetailsList"
})
@XmlRootElement(name = "GetRouteDetailsResponse")
public class GetRouteDetailsResponse {

	@XmlElementWrapper(name = "RouteDetailsList")
	@XmlElement(name = "RouteDetails", required = true)
	protected List<RouteDetails> routeDetailsList;

	/**
	 * Gets the value of the routeDetails property.
	 *
	 * @return possible object is {@link RouteDetailsList }
	 *
	 */
	public List<RouteDetails> getRouteDetailsList() {
		return routeDetailsList;
	}

	/**
	 * Sets the value of the routeDetails property.
	 *
	 * @param value allowed object is {@link RouteDetailsList }
	 *
	 */
	public void setRouteDetailsList(List<RouteDetails> value) {
		this.routeDetailsList = value;
	}

}
