//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.11.26 at 12:22:54 AM EET 
//


package ds.health.soa.soa_endpoints;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for medicationIntakeList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="medicationIntakeList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="medicationIntake" type="{http://health.ds/soa/soa-endpoints}medicationIntake"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "medicationIntakeList", propOrder = {
    "medicationIntake"
})
public class MedicationIntakeList {

    protected List<MedicationIntake> medicationIntake;

    /**
     * Gets the value of the medicationIntake property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the medicationIntake property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMedicationIntake().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MedicationIntake }
     * 
     * 
     */
    public List<MedicationIntake> getMedicationIntake() {
        if (medicationIntake == null) {
            medicationIntake = new ArrayList<MedicationIntake>();
        }
        return this.medicationIntake;
    }

}