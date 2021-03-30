import com.bridgelabzz.addressbooksystem.Contact;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

import static com.bridgelabzz.addressbooksystem.AddressBookDemo.*;

public class AddressBookUnitTest {
    public static final String jdbcURL = "jdbc:mysql://localhost:3306/Address_book_service?allowPublicKeyRetrieval=true&useSSL=false";
    public static final String userName = "tejas";
    public static final String password = "Password@123";
    public static Connection connection;
    @BeforeEach
    void setUp() {

    }

    @Test
    public void dataBaseConnectionTest() {
        Assertions.assertDoesNotThrow(()->{
            connection = DriverManager.getConnection(jdbcURL, userName, password);
        });
    }

    @Test
    public void LoadDataTest() {
        init();
        Assertions.assertEquals(3, addressBooks.get("default").contactBook.size());
    }
    @Test
    public void addNewTest(){
        init();
        HashMap<Contact.fields,String> c = new HashMap<>();
        c.put(Contact.fields.firstName, "testname");
        c.put(Contact.fields.lastName, "testLastName");
        c.put(Contact.fields.address, "testAddress");
        c.put(Contact.fields.email, "testEmail");
        c.put(Contact.fields.zipCode, "testCity");
        c.put(Contact.fields.city, "testZip");
        c.put(Contact.fields.state, "testState");
        c.put(Contact.fields.phoneNumber, "testPhone");
        currAddressBook = "newBook";
        addEntryToDB(c);
        Assertions.assertEquals(1, addressBooks.get("newBook").contactBook.size());

    }
    @Test
    public void deleteContact(){
        init();
        deleteEntryFromDB("testname");
    }
}
