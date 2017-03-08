package TestService;

import entity.Product;
import entity.Storage;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.ProductService;
import service.StorageService;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class StorageServiceTest {
    private ApplicationContext context;
    private ProductService productService;
    private StorageService storageService;

    @Before
    public void init(){
        context = new ClassPathXmlApplicationContext(
                "/spring/spring-config.xml");
        productService= context.getBean("productService", ProductService.class);
        storageService = context.getBean("storageService", StorageService.class);
    }

    @Test
    public void testRead(){
        List<Storage> storageList = storageService.findAll();
        Storage storage = storageService.read(storageList.get(0).getProduct().getId());
        System.out.println(storage.toString());

        assertNotNull("Проверка чтения первого продукта в складе", storage);
    }

    @Test
    public void createUpdateDelete(){
        Product product = context.getBean(Product.class);
        product.setName("Banana");
        product.setPrice(new BigDecimal("9.99"));

        productService.create(product);

        Storage storage = context.getBean(Storage.class);
        storage.setProduct(product);
        storage.setAmount(20);

        storageService.create(storage);

        Storage storageReturned = storageService.read(product.getId());
        assertEquals("Проверка корректности продукта в склад",
                storageReturned.toString(),
                storage.toString());

        Storage storage1 = context.getBean(Storage.class);
        storage1.setAmount(15);
        storage1.setProduct(product);

        storageService.update(storage1);
        Storage storageReturned1 = storageService.read(product.getId());

        assertEquals("Проверка корректности обновления информации о продукта в складе",
                storageReturned1.toString(),
                storage1.toString());

        storageService.delete(storageReturned1);
        Storage storageReturned2 = storageService.read(product.getId());
        productService.delete(product);
        assertNull("Проверка корректности удаления информации о продукта в складе", storageReturned2);
    }
}
