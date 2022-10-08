//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2022.10.08 à 12:39:10 PM CEST 
//


package gen;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour geographic complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="geographic">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="longitude" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="latitude" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "geographic", propOrder = {
    "longitude",
    "latitude"
})
public class Geographic {

    protected double longitude;
    protected double latitude;

    /**
     * Obtient la valeur de la propriété longitude.
     * 
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Définit la valeur de la propriété longitude.
     * 
     */
    public void setLongitude(double value) {
        this.longitude = value;
    }

    /**
     * Obtient la valeur de la propriété latitude.
     * 
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Définit la valeur de la propriété latitude.
     * 
     */
    public void setLatitude(double value) {
        this.latitude = value;
    }

}
