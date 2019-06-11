package cn.edu.neu.shop.pin.controller.admin;

import cn.edu.neu.shop.pin.mapper.PinProductMapper;
import cn.edu.neu.shop.pin.model.PinProduct;
import cn.edu.neu.shop.pin.model.PinUser;
import cn.edu.neu.shop.pin.service.ProductService;
import cn.edu.neu.shop.pin.service.security.UserService;
import cn.edu.neu.shop.pin.util.PinConstants;
import cn.edu.neu.shop.pin.util.ResponseWrapper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/goods")
public class AdminProductController {
    @Autowired
    UserService userService;
    @Autowired
    PinProductMapper productMapper;
    @Autowired
    ProductService productService;

    /**
     * TODO:未测试返回不同存货类型的商品
     *
     * @param req
     * @param queryType
     * @return
     */
    @GetMapping("/goods-list")
    public JSONObject getProducts(HttpServletRequest req, @RequestParam String queryType) {
        try {
            PinUser user = userService.whoAmI(req);
            String currentStoreId = req.getHeader("Current-Store");
            List<PinProduct> products = productMapper.getProductByStoreId(Integer.parseInt(currentStoreId));
            return ResponseWrapper.wrap(PinConstants.StatusCode.SUCCESS, PinConstants.ResponseMessage.SUCCESS, productService.judgeQueryType(products, queryType));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseWrapper.wrap(PinConstants.StatusCode.INTERNAL_ERROR, e.getMessage(), null);
        }
    }

    /**
     * 商铺所有者管理本店铺的商品
     * 商品分类部分 获取父级、子级分类名及一些商品信息
     * @param req
     * @return
     */
    @GetMapping("/goods-category")
    public JSONObject getProductFromSameStore(HttpServletRequest req) {
        try {
            String currentStoreId = req.getHeader("Current-Store");
            List<JSONObject> list = productService.getProductInfoFromSameStore(Integer.parseInt(currentStoreId));
            JSONObject data = new JSONObject();
            data.put("list", list);
            return ResponseWrapper.wrap(PinConstants.StatusCode.SUCCESS, PinConstants.ResponseMessage.SUCCESS, data);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseWrapper.wrap(PinConstants.StatusCode.INTERNAL_ERROR, e.getMessage(), null);
        }
    }

}
