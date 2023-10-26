package passoffTests.myTests;

import exceptions.BadRequestException;
import exceptions.DataAccessException;
import exceptions.ForbiddenException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import passoffTests.TestFactory;
import responses.RegisterUserResponse;
import static org.junit.jupiter.api.Assertions.*;

class RegisterUserServiceTest {

    @BeforeEach
    void setUp() throws DataAccessException {
        // Get a fresh start
        TestFactory.clearApplication();
    }

    @Test
    void registerUserPositive() throws ForbiddenException, BadRequestException, DataAccessException {

        assertDoesNotThrow(() -> {
            RegisterUserResponse response = TestFactory.createTestUser();
            assertEquals(TestFactory.getTestUsername(), response.getUsername(), "Username did not return expected value");
            assertNotNull(response.getAuthToken(), "Authtoken was not returned");
        });
    }

    @Test
    void registerUserNegative() throws ForbiddenException, BadRequestException, DataAccessException {
        TestFactory.createTestUser();

        assertThrowsExactly(ForbiddenException.class , TestFactory::createTestUser, "Registering the same user twice should have thrown an error");
    }
}