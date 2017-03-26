import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import web.entity.Customer;
import web.service.CustomerAccountService;
import web.service.CustomerService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/context-web.xml")
@WebAppConfiguration
public class CustomerAccountServiceTest {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerAccountService customerAccountService;

    @Test
    public void someTest() {
        String pass = customerAccountService.findPass("me@me.me");
        Customer customer = customerService.find("me@me.me");
        System.out.println(pass);
        System.out.println(customer);
    }
}