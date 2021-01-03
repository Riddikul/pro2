package model;
import java.time.LocalDateTime;

public class MessengerMessage
{
    public MessengerMessage(String text, LocalDateTime time, long fromPersonId, long toPersonId, long id) {
        this.text = text;
        this.time = time;
        this.fromPersonId = fromPersonId;
        this.toPersonId = toPersonId;
        this.id = id;
    }

    public String text;
    public LocalDateTime time;
    public long fromPersonId;
    public long toPersonId;
    public long id;
}
