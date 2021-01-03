package model.xml;
import  javax.xml.bind.annotation.*;

@XmlRootElement(name = "temperature")
@XmlAccessorType(XmlAccessType.FIELD)
public class Temperature {
    @XmlAttribute(name = "value")
    public double value;
    @XmlAttribute(name = "min")
    public double min;
    @XmlAttribute(name = "max")
    public double max;
    @XmlAttribute(name = "unit")
    public String unit;

    public Temperature() {

    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void toCelsius() {
        setUnit("celsius");
        setValue((double) Math.round((getValue() - 273.15)*100) /100);
        setMin((double) Math.round((getMin() - 273.15)*100) /100);
        setMax((double) Math.round((getMax() - 273.15)*100) /100);
    }
}
