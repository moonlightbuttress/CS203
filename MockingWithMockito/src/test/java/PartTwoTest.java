import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class PartTwoTest {

    PartTwo partTwo;
    Database2 databaseMock;

    @BeforeEach
    public void setUp() throws Exception {
        databaseMock = Mockito.mock(Database2.class);
        partTwo = new PartTwo(databaseMock);
    }

    @Test
    public void login() throws Exception {
        Mockito.when(databaseMock.getUserId(Mockito.anyString(), Mockito.anyString())).thenReturn("ABC-123");
        Mockito.when(databaseMock.getUserId("Trinity","not-here")).thenThrow(SQLException.class);


        Assertions.assertEquals( "ABC-123", partTwo.login("Gale", "exists"));
        Assertions.assertEquals( "", partTwo.login("Trinity", "not-here"));
    }

    @Test
    public void register() throws Exception{
        Mockito.when(databaseMock.addUser(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenThrow(SQLException.class);

        assertThrows(SQLException.class, () -> {
           partTwo.register("Trinity", "cantGuessIt", "mail@mail.com", "Student");
        });

    }

    @Test
    public void logAccountUse() {
        Mockito.when(databaseMock.getAccountType("ABC-123")).thenReturn("Admin");
        Mockito.when(databaseMock.getAccountType("BIG-CHUNG")).thenReturn("Student");
        Mockito.when(databaseMock.getAccountType("A-UUID")).thenReturn("Staff");
        Mockito.when(databaseMock.getAccountType("NOT_A_UUID")).thenReturn("");

        partTwo.logAccountUse("ABC-123");
        partTwo.logAccountUse("BIG-CHUNG");
        partTwo.logAccountUse("A-UUID");
        partTwo.logAccountUse("NOT_A_UUID");
        Mockito.verify(databaseMock).getAccountType("ABC-123");
        Mockito.verify(databaseMock).getAccountType("BIG-CHUNG");
        Mockito.verify(databaseMock).getAccountType("A-UUID");
        Mockito.verify(databaseMock).getAccountType("NOT_A_UUID");
        Mockito.verify(databaseMock, Mockito.times(1)).logAdminUse("ABC-123");
        Mockito.verify(databaseMock, Mockito.times(1)).logStudentUse("BIG-CHUNG");
        Mockito.verify(databaseMock, Mockito.times(1)).logStaffUse("A-UUID");
    }


    @Test
    void deleteUserAccount() throws SQLException {
        Mockito.when(databaseMock.deleteUserAccount(Mockito.anyString())).thenReturn(false);
        Mockito.when(databaseMock.getUserId("Trinity", "not-here")).thenThrow(SQLException.class);

        partTwo.deleteUserAccount("john", "password");
        Mockito.verify(databaseMock).logErrorMessage("Deletion attempt: User was not deleted");
        partTwo.deleteUserAccount("Trinity", "not-here");
        Mockito.verify(databaseMock).logErrorMessage("SQL Error during Deletion");


    }
}