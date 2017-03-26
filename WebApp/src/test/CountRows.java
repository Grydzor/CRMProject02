import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import web.service.ProductService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/context-web.xml")
@WebAppConfiguration
public class CountRows {
    @Autowired
    private ProductService productService;

    @Test
    public void countTest() {
        System.out.println(productService.getNumberOfRows());
    }
}
