package demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static aop.TestUtils.fromSystemOut;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:demo.xml")
class TerminatorQuoterTest {

    @Autowired
    Quoter quoter;

    @Test
    void sayQuote() {
        assertThat(fromSystemOut(quoter::sayQuote), is("message = I`ll be back\n"));
    }
}