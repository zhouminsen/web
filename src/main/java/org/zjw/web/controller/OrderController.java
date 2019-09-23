package org.zjw.web.controller;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zjw.web.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhoum on 2018/7/26.
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private UserService userService;

    /**
     * 获取订单
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getOrder", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> getOrder(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        Order order = new Order();
        order.setOrderId("1111");
        order.setOrderTime(new Date());
        order.setRetCode("2");
        order.setTradeState(0);
        order.setRetMsg("来自web的订单是什么呢，是你的哈哈。我不晓得。");
        order.setTotalFee(new BigDecimal("1111"));
        order.setOutRefundNo("2222");
        order.setTransactionId("3333");
        order.setRemark("第一次测试");
        order.setAddress("湖北省武汉市武昌区徐东大街");
        Map<String, Object> data = new HashMap<>();
        data.put("order", order);
        map.put("data", data);
        map.put("customerName", "家伟平台vip2");
        map.put("type", 0);
        map.put("id", "d6b184d7162e493e8c7f1c0dde3a9a49");
        map.put("code", 0);
//        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return map;
    }

    /**
     * 接收订单
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/receive", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> receive(@RequestBody String s) {
        System.out.println("是什么：" + s);
        Map<String, Object> map = new HashMap<>();
        map.put("hahah", 1);
        return map;
    }

    @Data
    public static class Order implements Serializable {

        /**
         * serialVersionUID:TODO（用一句话描述这个变量表示什么）
         *
         * @since Ver 1.1
         */

        private static final long serialVersionUID = -4817602540551399217L;


        /**
         * 应答流水号
         */
        private String orderId;


        /**
         * 返回状态码(返回状态码，0表示成功，其他未定义)
         */
        private String retCode;

        /**
         * 交易状态码(0表示成功,1 交易失败, 2未支付,3 处理中,4支付成功等待买家收货)
         */
        private Integer tradeState;

        /**
         * 下单时间
         */
        private Date orderTime;

        /**
         * 返回信息
         */
        private String retMsg;


        /**
         * 总金额
         */
        private BigDecimal totalFee;


        /**
         * 商户订单号
         */
        private String outTradeNo;

        /**
         * 退款单号
         */
        private String outRefundNo;

        /**
         * 支付流水号
         */
        private String transactionId;

        /**
         * 是否对账
         */
        private Boolean isSplit;

        /**
         * 交易完成时间
         */
        private String timeEnd;

        /**
         * 物流费用
         */
        private BigDecimal transportFee;

        /**
         * 应收手续费(平台应收一级商户手续费)
         */
        private BigDecimal platCharge;

        /**
         * 应收手续费(平台应收二级商户手续费)
         */
        private BigDecimal subPlatCharge;

        /**
         *
         */
        private BigDecimal productFee;

        /**
         * 折扣价格
         */
        private BigDecimal discount;

        /**
         * 商家数据包
         */
        private String attach;

        /**
         * 订单地址
         */
        private String address;

        /**
         * 订单响应
         */
        private String remark;


    }

}
