package model.xml;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "temperature")
@XmlAccessorType(XmlAccessType.FIELD)
public class Direction {
    @XmlAttribute(name = "value")
    public int value;
    @XmlAttribute(name = "code")
    public String code;
    @XmlAttribute(name = "name")
    public String name;

    public Direction() {

    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
