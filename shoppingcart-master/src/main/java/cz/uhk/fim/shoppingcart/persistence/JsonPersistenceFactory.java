package cz.uhk.fim.shoppingcart.persistence;

public class JsonPersistenceFactory implements PersistenceFactory
{
    String path;
    public JsonPersistenceFactory(String path)
    {
        this.path = path;
    }

    @Override
    public Persistence GetPersistence()
    {
        return new JsonPersistence(path);
    }
}
