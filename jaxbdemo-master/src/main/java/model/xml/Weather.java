package model.xml;

import javax.xml.bind.annotation.*;


@XmlRootElement(name = "weather")
@XmlAccessorType(XmlAccessType.FIELD)
public class Weather {
    @XmlElement(name = "temperature")
    public Temperature temperature;
    @XmlElement(name = "wind")
    public Wind wind;

    public Weather() {

    }

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }
}
