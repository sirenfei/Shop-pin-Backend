package cn.edu.neu.shop.pin.mapper;

import cn.edu.neu.shop.pin.PinApplication;
import cn.edu.neu.shop.pin.customer.service.ProductService;
import cn.edu.neu.shop.pin.model.PinProduct;
import java.util.List;

import com.github.pagehelper.PageHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes= PinApplication.class)
//@EnableAutoConfiguration

@RunWith(SpringRunner.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PinProductMapperTest {

    @Autowired
    private PinProductMapper pinProductMapper;

//    @Autowired
//    private ProductService productService;

    @Test
    public void testPage() {
        PageHelper.startPage(1, 10);
        List<PinProduct> list = pinProductMapper.getHotProducts();
        System.out.println("Size: " + list.size());
        for(PinProduct p : list) {
            System.out.println();
            System.out.println(p);
            System.out.println();
        }
    }
}
