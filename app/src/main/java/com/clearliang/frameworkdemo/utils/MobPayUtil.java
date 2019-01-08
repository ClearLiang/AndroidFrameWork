package com.clearliang.frameworkdemo.utils;

import com.blankj.utilcode.util.ToastUtils;
import com.clearliang.frameworkdemo.R;
import com.mob.paysdk.AliPayAPI;
import com.mob.paysdk.MobPayAPI;
import com.mob.paysdk.OnPayListener;
import com.mob.paysdk.PayOrder;
import com.mob.paysdk.PayResult;
import com.mob.paysdk.PaySDK;
import com.mob.paysdk.UnionPayAPI;
import com.mob.paysdk.WXPayAPI;

import static com.mob.paysdk.PayResult.PAYCODE_CANCEL;
import static com.mob.paysdk.PayResult.PAYCODE_CHANNEL_ERROR;
import static com.mob.paysdk.PayResult.PAYCODE_INVALID_CHANNEL;
import static com.mob.paysdk.PayResult.PAYCODE_NETWORK_EXCEPTION;
import static com.mob.paysdk.PayResult.PAYCODE_OK;
import static com.mob.paysdk.PayResult.PAYCODE_UNSUPPORT;


/**
 * Created by ClearLiang on 2018/12/27
 * <p>
 * Function :
 */

public class MobPayUtil {
    private static MobPayUtil instance;
    public static final int ALIPAY = 1;
    public static final int WXPAY = 2;
    public static final int UNPAY = 3;
    private PayOrder order1 = new PayOrder();

    private MobPayUtil() {
    }

    public static MobPayUtil getMobPayUtil() {
        if (instance == null) {
            synchronized (MobPayUtil.class) {
                if (instance == null) {
                    instance = new MobPayUtil();
                }
            }
        }
        return instance;
    }

    public MobPayUtil initMobPay(PayOrder order) {
        order1.setOrderNo(order.getOrderNo());
        order1.setAmount(order.getAmount());
        order1.setSubject(order.getSubject());
        order1.setBody(order.getBody());
        return instance;
    }

    public void setPayWay(int payWay) {
        switch (payWay) {
            case 1:
                initAliPay();
                break;

            case 2:
                initWXPay();
                break;

            case 3:
                initUnPay();
                break;
        }
    }

    private void initAliPay() {
        AliPayAPI payApi = PaySDK.createMobPayAPI(AliPayAPI.class);
        payApi.pay(order1, new OnPayListener<PayOrder>() {
            @Override
            public boolean onWillPay(String ticketId, PayOrder payOrder, MobPayAPI mobPayAPI) {
                // 保存本次支付操作的 ticketId
                // 返回false表示不阻止本次支付
                return false;
            }

            @Override
            public void onPayEnd(PayResult payResult, PayOrder payOrder, MobPayAPI mobPayAPI) {
                // 处理支付的结果，成功或失败可以在payResult中获取
                switch (payResult.getPayCode()) {
                    case PAYCODE_CANCEL:
                        ToastUtils.showShort(R.string.string_pay_cancel);
                        break;

                    case PAYCODE_CHANNEL_ERROR:
                        ToastUtils.showShort(R.string.string_pay_channel_error);
                        break;

                    case PAYCODE_INVALID_CHANNEL:
                        ToastUtils.showShort(R.string.string_pay_invalid_channel);
                        break;

                    case PAYCODE_NETWORK_EXCEPTION:
                        ToastUtils.showShort(R.string.string_pay_network_exception);
                        break;

                    case PAYCODE_OK:
                        ToastUtils.showShort(R.string.string_pay_ok);
                        break;

                    case PAYCODE_UNSUPPORT:
                        ToastUtils.showShort(R.string.string_pay_unsupport);
                        break;
                }
            }
        });
    }

    private void initWXPay() {
        WXPayAPI payApi = PaySDK.createMobPayAPI(WXPayAPI.class);
        payApi.pay(order1, new OnPayListener<PayOrder>() {
            @Override
            public boolean onWillPay(String ticketId, PayOrder payOrder, MobPayAPI mobPayAPI) {
                // 保存本次支付操作的 ticketId
                // 返回false表示不阻止本次支付
                return false;
            }

            @Override
            public void onPayEnd(PayResult payResult, PayOrder payOrder, MobPayAPI mobPayAPI) {
                // 处理支付的结果，成功或失败可以在payResult中获取
            }
        });
    }

    private void initUnPay() {
        UnionPayAPI payApi = PaySDK.createMobPayAPI(UnionPayAPI.class);
        payApi.pay(order1, new OnPayListener<PayOrder>() {
            @Override
            public boolean onWillPay(String ticketId, PayOrder payOrder, MobPayAPI mobPayAPI) {
                // 保存本次支付操作的 ticketId
                // 返回false表示不阻止本次支付
                return false;
            }

            @Override
            public void onPayEnd(PayResult payResult, PayOrder payOrder, MobPayAPI mobPayAPI) {
                // 处理支付的结果，成功或失败可以在payResult中获取
            }
        });
    }
}

