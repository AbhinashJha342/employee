package com.example.employee.web;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

//@RunWith(SpringRunner.class)
//@SpringBootTest
//@ContextConfiguration(classes = AppConfig.class)
//@DataJpaTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles({"test"})
@TestPropertySource(value = {
        "classpath:application-test.properties"
})
public abstract class AbstractControllerIntegrationTest {
}
