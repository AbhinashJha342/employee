package com.example.employee.web;

import com.example.employee.container.TestPostgresContainer;
import org.junit.ClassRule;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

//@ActiveProfiles({"test"})
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@ContextConfiguration(classes = AppConfig.class)
@ExtendWith(SpringExtension.class)
//@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestPropertySource(value = {
        "classpath:application-test.properties"
})
public abstract class AbstractControllerIntegrationTest {

    @ClassRule
    public static TestPostgresContainer postgres = TestPostgresContainer
            .getInstance();
}
