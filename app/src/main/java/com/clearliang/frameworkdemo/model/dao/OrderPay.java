package com.clearliang.frameworkdemo.model.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by ClearLiang on 2018/12/27
 * <p>
 * Function :
 */
@Entity
public class OrderPay {
    @Id(autoincrement = true)
    private Long id;

    private int amount;         // 支付金额
    private String body;        // 支付主体
    private String description; // 描述
    private String metadata;    // 支付金额
    private String orderNo;     // 订单号
    private String subject;     // 支付标题
    private String ticketId;    // 票证号
    @Generated(hash = 343438680)
    public OrderPay(Long id, int amount, String body, String description,
            String metadata, String orderNo, String subject, String ticketId) {
        this.id = id;
        this.amount = amount;
        this.body = body;
        this.description = description;
        this.metadata = metadata;
        this.orderNo = orderNo;
        this.subject = subject;
        this.ticketId = ticketId;
    }
    @Generated(hash = 1559674046)
    public OrderPay() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getAmount() {
        return this.amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    public String getBody() {
        return this.body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getMetadata() {
        return this.metadata;
    }
    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }
    public String getOrderNo() {
        return this.orderNo;
    }
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    public String getSubject() {
        return this.subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getTicketId() {
        return this.ticketId;
    }
    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

}
