package model;

import model.xml.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@XmlRootElement(name = "person") //jmeno elementu odpovidajiciho Osobe
@XmlAccessorType(XmlAccessType.FIELD) //atributy tridy se stanou atributy elementu XML
public class Osoba {
    @XmlElement(name = "lastname")
    private String prijmeni;
    @XmlElement(name = "firstname")
    private String jmeno;

    @XmlElement(name = "birthday")
    @XmlJavaTypeAdapter(LocalDateAdapter.class) //jak se LocalDate transofmuje
    private LocalDate datumNarozeni;

    public Osoba() {
    }

    public Osoba(String prijmeni, String jmeno, LocalDate datumNarozeni) {
        this.prijmeni = prijmeni;
        this.jmeno = jmeno;
        this.datumNarozeni = datumNarozeni;
    }

    public String getPrijmeni() {
        return prijmeni;
    }

    public void setPrijmeni(String prijmeni) {
        this.prijmeni = prijmeni;
    }

    public String getJmeno() {
        return jmeno;
    }

    public void setJmeno(String jmeno) {
        this.jmeno = jmeno;
    }

    public LocalDate getDatumNarozeni() {
        return datumNarozeni;
    }

    public void setDatumNarozeni(LocalDate datumNarozeni) {
        this.datumNarozeni = datumNarozeni;
    }
}
