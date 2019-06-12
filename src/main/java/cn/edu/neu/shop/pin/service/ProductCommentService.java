package cn.edu.neu.shop.pin.service;

import cn.edu.neu.shop.pin.mapper.PinUserProductCommentMapper;
import cn.edu.neu.shop.pin.model.PinUserProductComment;
import cn.edu.neu.shop.pin.util.base.AbstractService;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ProductCommentService extends AbstractService<PinUserProductComment> {

    public static final int STATUS_ADD_COMMENT_SUCCESS = 0;
    public static final int STATUS_ADD_COMMENT_FAILED = -1;

    @Autowired
    private PinUserProductCommentMapper pinUserProductCommentMapper;

    @Autowired
    private ProductCommentService productCommentService;

    /**
     * @author flyhero
     * 为商品添加评论
     * @param userId 用户ID
     * @param orderIndividualId 订单ID
     * @param productId 产品ID
     * @param skuId skuID
     * @param grade 0-好评，1-中评，2-差评
     * @param productScore 产品评分（1～5）
     * @param serviceScore 服务评分（1～5）
     * @param content 评论内容
     * @param imagesUrls 评论图片
     * @return
     */
    public Integer addComment(Integer userId, Integer orderIndividualId, Integer productId, Integer skuId, Integer grade,
                           Integer productScore, Integer serviceScore, String content, String imagesUrls) {
        PinUserProductComment commentSample = new PinUserProductComment();
        commentSample.setOrderIndividualId(orderIndividualId);
        commentSample.setSkuId(skuId);
        List<PinUserProductComment> list = pinUserProductCommentMapper.select(commentSample);
        if(list != null) {
            // 用户已对此订单中的此商品做出过评论了，不能再评论一次
            return STATUS_ADD_COMMENT_FAILED;
        }
        PinUserProductComment comment = new PinUserProductComment();
        comment.setUserId(userId);
        comment.setOrderIndividualId(orderIndividualId);
        comment.setProductId(productId);
        comment.setSkuId(skuId);
        comment.setGrade(grade);
        comment.setProductScore(productScore);
        comment.setServiceScore(serviceScore);
        comment.setContent(content);
        comment.setImagesUrls(imagesUrls);
        pinUserProductCommentMapper.insert(comment);
        return STATUS_ADD_COMMENT_SUCCESS;
    }

    /**
     * 根据商品ID 获取该商品评论信息
     * @param productId 商品 ID
     * @return List
     */
    public PageInfo<PinUserProductComment> getCommentByProductIdByPage(Integer productId, Integer pageNum, Integer pageSize) {
        return PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> {
            PinUserProductComment pinUserProductComment = new PinUserProductComment();
            pinUserProductComment.setProductId(productId);
            pinUserProductCommentMapper.select(pinUserProductComment);
        });
    }

    /**
     * 从当前时间算起，获取之前7天内每天的评论数
     * @param storeId 店铺ID
     * @return
     */
    public Integer[] getComments(Integer storeId) {
        Integer comment[] = new Integer[7];
        Date date = new Date();
        date = getDateByOffset(date, 1);
        for(int i = 0; i < 7; i++) {
            Date toDate = date;
            date = getDateByOffset(date, -1);
            Date fromDate = date;
//            System.out.println("fromDate: " + fromDate + " --- toDate: " + toDate);
            comment[i] = pinUserProductCommentMapper.getNumberOfComment(fromDate, toDate, storeId);
//            System.out.println("comment[i]: " + comment[i]);
        }
        return comment;
    }

    /**
     * 管理端
     * 分页获取商品评论信息 包括评论用户昵称和头像 购买商品类型
     */
    public List<JSONObject> getCommentAndUserInfoByPage(Integer productId) {
        return pinUserProductCommentMapper.getCommentAndUserInfo(productId);
    }

    /**
     *
     * @param list
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public List<?> getCommentsByPageNumAndSize(List<?> list, Integer pageNumber, Integer pageSize) {
        if (pageNumber * pageSize < list.size()) {
            return list.subList((pageNumber - 1) * pageSize, pageNumber * pageSize);
        } else {
            return list.subList((pageNumber - 1) * pageSize, list.size());
        }
    }

    /**
     * 获取该店铺商家未评论总数
     * @param storeId
     * @return
     */
    public Integer getMerchantNotComment(Integer storeId) {
        return pinUserProductCommentMapper.getNumberOfMerchantNotComment(storeId);
    }

    /**
     * 指定偏移的天数，计算某天的日期
     * @param today 当前时间
     * @param delta 偏移量
     * @return
     */
    private java.util.Date getDateByOffset(java.util.Date today, Integer delta) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + delta);
        return calendar.getTime();
    }

    public List<JSONObject> getProductWithComment(Integer storeId) {
        return pinUserProductCommentMapper.getAllProductWithComment(storeId);
    }

    public void updateMerchantCommentContent(Integer commentId, String commentContent, Date commentTime){
        pinUserProductCommentMapper.updateMerchantComment(commentId, commentContent, commentTime);
    }
}
