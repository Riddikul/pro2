package app;

import model.EvidenceOsob;
import model.Osoba;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.time.LocalDate;

/**
 * Ukazka serializace (marshalling/unmarshalling) pomoci XML
 * pouzije se Java API for XML binging (JAXB)
 * Do Javy 8 bylo soucasti JDK, od verze 11 odstraneno a je nutne
 * doplnit zavislosti (knihovny) - viz pom.xml
 */
public class JAXBDemo {

    public static void main(String[] args) {
        try {
            JAXBContext ctx = JAXBContext.newInstance(EvidenceOsob.class);

            Marshaller marshaller = ctx.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);

            EvidenceOsob evidenceOsob  = naplnData();

            marshaller.marshal(evidenceOsob, System.out);   //serializace do XML do std. vystupu/na obrazovku

            marshaller.marshal(evidenceOsob, new File("people.xml")); //serializace do souboru

            Unmarshaller unmarshaller = ctx.createUnmarshaller();
            //deserializace ze souboru
            evidenceOsob = (EvidenceOsob) unmarshaller.unmarshal(new File("people.xml"));
            //vypis nacteneho na obrazovku
            evidenceOsob.getOsoby().forEach( o -> System.out.println(o.getPrijmeni()));

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    private static EvidenceOsob naplnData() {
        EvidenceOsob evid = new EvidenceOsob();
        evid.getOsoby().add(
                new Osoba("Novak","Tomas", LocalDate.of(1998, 1, 4))
        );
        evid.getOsoby().add(
                new Osoba("Svetr", "Petr",LocalDate.of(1989, 2, 12))
        );
        evid.getOsoby().add(
                new Osoba("Doulikova","Anna", LocalDate.of(1899, 12, 31))
        );
        return evid;
    }
}
