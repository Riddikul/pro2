package model;

import java.util.List;

public interface MessengerDataManager {

    void AddPerson(MessengerPerson person);

    void AddMessage(MessengerMessage message);

    MessengerPerson GetPersonByLogin(String login);

    MessengerPerson GetPersonById(Long id);

    List<MessengerMessage> GetMessagesByToPerson(MessengerPerson toPerson);

    List<MessengerPerson> GetAllPersons();
}
