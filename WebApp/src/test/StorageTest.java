import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import web.entity.Customer;
import web.entity.CustomerAccount;
import web.entity.Product;
import web.entity.Storage;
import web.service.ProductService;
import web.service.StorageService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/context-web.xml")
@WebAppConfiguration
public class StorageTest {
    @Autowired
    private StorageService storageService;
    @Autowired
    private ProductService productService;

    @Test
    public void someTest() {
        Product product = productService.read(1L);
        Storage storage = storageService.read(product);
    }
}
