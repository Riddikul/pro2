package model.xml;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Adapter - trida, ktera, provadi transformaci "nepodporovane" tridy
 * LocalDate na Sting a zpet - automaticky se pouzije pri serializaci
 */
public class LocalDateAdapter extends XmlAdapter<String,LocalDate> {

    @Override
    public LocalDate unmarshal(String v) throws Exception {
        return LocalDate.parse(v, DateTimeFormatter.ISO_DATE);
    }

    @Override
    public String marshal(LocalDate v) throws Exception {
        return v.format(DateTimeFormatter.ISO_DATE);
    }
}
