import org.springframework.context.ApplicationContext;
import service.CustomerService;
import service.UserSessionService;
import util.ApplicationContextFactory;

/**
 * Created by eriol4ik on 17.02.2017.
 */
public class Test2 {
    public static void main(String[] args) {
        ApplicationContext context = ApplicationContextFactory.getApplicationContext();
        CustomerService service = context.getBean(CustomerService.class);
        UserSessionService service2 = context.getBean(UserSessionService.class);
        System.out.println(service.read(2L));
        System.out.println(service2.read(1L));

        System.out.println("1:" + service2.restoreSession());
        System.out.println("2:" + service2.writeToResource(1L));
        System.out.println("3:" + service2.restoreSession());
        System.out.println("4:" + service2.writeToResource(null));
        System.out.println("5:" + service2.restoreSession());
    }
}
