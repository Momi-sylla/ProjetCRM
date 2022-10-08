//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2022.10.08 à 12:39:10 PM CEST 
//


package gen;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour anonymous complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="lowAnnualRevenue" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="highAnnualRevenue" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="State" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "lowAnnualRevenue",
    "highAnnualRevenue",
    "state"
})
@XmlRootElement(name = "getLeadsRequest")
public class GetLeadsRequest {

    protected double lowAnnualRevenue;
    protected double highAnnualRevenue;
    @XmlElement(name = "State", required = true)
    protected String state;

    /**
     * Obtient la valeur de la propriété lowAnnualRevenue.
     * 
     */
    public double getLowAnnualRevenue() {
        return lowAnnualRevenue;
    }

    /**
     * Définit la valeur de la propriété lowAnnualRevenue.
     * 
     */
    public void setLowAnnualRevenue(double value) {
        this.lowAnnualRevenue = value;
    }

    /**
     * Obtient la valeur de la propriété highAnnualRevenue.
     * 
     */
    public double getHighAnnualRevenue() {
        return highAnnualRevenue;
    }

    /**
     * Définit la valeur de la propriété highAnnualRevenue.
     * 
     */
    public void setHighAnnualRevenue(double value) {
        this.highAnnualRevenue = value;
    }

    /**
     * Obtient la valeur de la propriété state.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getState() {
        return state;
    }

    /**
     * Définit la valeur de la propriété state.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setState(String value) {
        this.state = value;
    }

}
