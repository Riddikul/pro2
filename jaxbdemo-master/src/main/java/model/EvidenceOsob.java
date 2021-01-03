package model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "people")
@XmlAccessorType(XmlAccessType.FIELD)
public class EvidenceOsob {

    @XmlElement(name = "person")
   private List<Osoba> osoby;

    public EvidenceOsob() {
        osoby = new ArrayList<>();
    }

    public EvidenceOsob(List<Osoba> osoby) {
        this.osoby = osoby;
    }

    public List<Osoba> getOsoby() {
        return osoby;
    }

    public void setOsoby(List<Osoba> osoby) {
        this.osoby = osoby;
    }
}
