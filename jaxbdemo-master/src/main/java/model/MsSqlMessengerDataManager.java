package model;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
public class MsSqlMessengerDataManager implements MessengerDataManager
{
    Connection connection;

    public MsSqlMessengerDataManager()
    {
        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection("jdbc:sqlserver://database-1.cwnuam1akix6.us-east-2.rds.amazonaws.com:1433;DatabaseName=PRO2_Messenger", "PRO2Students", "Pro2StudentsPassword");
        }
        catch (ClassNotFoundException | SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void AddPerson(MessengerPerson person)
    {
        try(Statement statement = connection.createStatement())
        {
            statement.execute("insert into Person (name,login) values ('"+person.name+"','"+person.login+"')");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void AddMessage(MessengerMessage message)
    {
        try(Statement statement = connection.createStatement())
        {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");
            String dateTimeString = LocalDateTime.now().format(dateTimeFormatter);
            String sql="insert into Message(FromPersonID,ToPersonID,Text,Time) values('"+message.fromPersonId+"','"+message.toPersonId+"','"+message.text+"', '"+dateTimeString+"')";
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public MessengerPerson GetPersonByLogin(String login)
    {
        try(Statement statement = connection.createStatement())
        {
            ResultSet resultSet= statement.executeQuery("select * from Person where login='"+login+"'");
            if(resultSet.next())
            {
                MessengerPerson messengerPerson = new MessengerPerson(
                        resultSet.getString("Login"),
                        resultSet.getString("Name"),
                        resultSet.getLong("ID")
                );
                return messengerPerson;
            }
            else
            {
                return null;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public MessengerPerson GetPersonById(Long id)
    {
        try(Statement statement = connection.createStatement())
        {
            ResultSet resultSet= statement.executeQuery("select * from Person where id='"+id+"'");
            if(resultSet.next())
            {
                MessengerPerson messengerPerson = new MessengerPerson(
                        resultSet.getString("Login"),
                        resultSet.getString("Name"),
                        resultSet.getLong("ID")
                );
                return messengerPerson;
            }
            else
            {
                return null;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<MessengerMessage> GetMessagesByToPerson(MessengerPerson toPerson)
    {
        ArrayList<MessengerMessage> result = new ArrayList<>();
        try(Statement statement = connection.createStatement())
        {
            ResultSet resultSet= statement.executeQuery("select * from Message where ToPersonId='"+toPerson.id+"'");
            while(resultSet.next())
            {
                MessengerMessage messengerMessage = new MessengerMessage(
                        resultSet.getString("Text"),
                        resultSet.getTimestamp("Time").toLocalDateTime(),
                        resultSet.getLong("FromPersonID"),
                        resultSet.getLong("ToPersonID"),
                        resultSet.getLong("Id"));
                result.add(messengerMessage);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    @Override
    public List<MessengerPerson> GetAllPersons() {
        ArrayList<MessengerPerson> result = new ArrayList<>();
        try(Statement statement = connection.createStatement())
        {
            ResultSet resultSet= statement.executeQuery("select * from Person");
            while(resultSet.next())
            {
                MessengerPerson messengerPerson = new MessengerPerson(
                        resultSet.getString("Login"),
                        resultSet.getString("Name"),
                        resultSet.getLong("Id"));
                result.add(messengerPerson);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
        return result;
    }
}
