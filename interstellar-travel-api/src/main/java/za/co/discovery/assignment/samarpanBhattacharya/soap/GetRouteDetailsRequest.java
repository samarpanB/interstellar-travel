//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.02.24 at 05:28:54 PM IST 
//
package za.co.discovery.assignment.samarpanBhattacharya.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
	"source",
	"destination"
})
@XmlRootElement(name = "GetRouteDetailsRequest")
public class GetRouteDetailsRequest {

	protected String source;
	protected String destination;

	/**
	 * Gets the value of the source property.
	 *
	 * @return
	 */
	public String getSource() {
		return source;
	}

	/**
	 * Sets the value of the source property.
	 *
	 * @param source
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * Gets the value of the destination property.
	 *
	 * @return
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * Sets the value of the destination property.
	 *
	 * @param destination
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}
}
