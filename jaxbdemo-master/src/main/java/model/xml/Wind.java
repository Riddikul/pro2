package model.xml;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "wind")
@XmlAccessorType(XmlAccessType.FIELD)
public class Wind {
    @XmlElement(name = "speed")
    public Speed speed;
    @XmlElement(name = "direction")
    public Direction direction;

    public Wind() {

    }

    public Speed getSpeed() {
        return speed;
    }

    public void setSpeed(Speed speed) {
        this.speed = speed;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void toKmh () {
        getSpeed().setValue((double) Math.round((getSpeed().getValue() * 3.6)*100)/100);
        getSpeed().setUnit("Km/h");
    }
}
