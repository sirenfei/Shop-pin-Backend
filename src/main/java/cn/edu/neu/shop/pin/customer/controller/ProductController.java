package cn.edu.neu.shop.pin.customer.controller;

import cn.edu.neu.shop.pin.customer.service.ProductCategoryService;
import cn.edu.neu.shop.pin.customer.service.ProductCommentService;
import cn.edu.neu.shop.pin.customer.service.ProductInfoService;
import cn.edu.neu.shop.pin.util.PinConstants;
import cn.edu.neu.shop.pin.util.ResponseWrapper;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/commons/product")
public class ProductController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private ProductCommentService productCommentService;

    @PostMapping("/category/get-all-by-layer")
    public JSONObject getCategoryByLayer(@RequestBody JSONObject requestJSON) {
        try{
            Integer layer = requestJSON.getInteger("layer");
            JSONObject data = new JSONObject();
            data.put("list", productCategoryService.getProductCategoryByLayer(layer));
            return ResponseWrapper.wrap(PinConstants.StatusCode.SUCCESS, PinConstants.ResponseMessage.SUCCESS, data);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseWrapper.wrap(PinConstants.StatusCode.INTERNAL_ERROR, e.getMessage(), null);
        }
    }

    @GetMapping("/{productId}")
    public JSONObject getProductInfoByProductId(@PathVariable Integer productId){
        try{
            return ResponseWrapper.wrap(PinConstants.StatusCode.SUCCESS, PinConstants.ResponseMessage.SUCCESS, productInfoService.getProInfoByProId(productId));
        }catch(Exception e){
            e.printStackTrace();
            return ResponseWrapper.wrap(PinConstants.StatusCode.INTERNAL_ERROR, e.getMessage(), null);
        }
    }

    @GetMapping("/{productId}/user-comment")
    public JSONObject getCommentByProductId(@PathVariable Integer productId){
        try{
            JSONObject data = new JSONObject();
            data.put("list", productCommentService.getCommentByProductId(productId));
            return ResponseWrapper.wrap(PinConstants.StatusCode.SUCCESS, PinConstants.ResponseMessage.SUCCESS, data);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseWrapper.wrap(PinConstants.StatusCode.SUCCESS, e.getMessage(), null);
        }
    }
}
