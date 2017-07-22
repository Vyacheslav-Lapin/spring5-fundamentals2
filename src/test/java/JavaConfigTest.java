import lab.config.Config;
import lab.model.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Config.class})
public class JavaConfigTest {

    @Autowired
    Person person;

    @Test
    void testRunningAppByJavaConfig() {
        assertThat(person, is(HelloWorldTest.getExpectedPerson()));
    }
}
