package model.xml;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "temperature")
@XmlAccessorType(XmlAccessType.FIELD)
public class Speed {
    @XmlAttribute(name = "value")
    public double value;
    @XmlAttribute(name = "unit")
    public String unit;
    @XmlAttribute(name = "name")
    public String name;

    public Speed() {

    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
