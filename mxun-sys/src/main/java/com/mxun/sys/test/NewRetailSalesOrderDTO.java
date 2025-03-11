package com.mxun.sys.test;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 新零售公司订单表 kafka接收对象
 * @author zhilinliu
 * @since 2025-02-21
 */
@Data
public class NewRetailSalesOrderDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 交易编号(tid)
     */
    private String trade_id;

    /**
     * 订单编号(cTradeCode)
     */
    private String ord_code;

    /**
     * WMS出库单号(wmsDeliveryCode)
     */
    private String wms_delivery_code;

    /**
     * 订单类型(orderType)
     */
    private String ord_type;

    /**
     * 收货人姓名(receiverName)
     */
    private String consignee_name;

    /**
     * 手机号(receiverMobile)
     */
    private String consignee_phone;

    /**
     * 省(还没做,本次任务追加)
     */
    private String receiver_state;

    /**
     * 市(还没做,本次任务追加)
     */
    private String receiver_city;

    /**
     * 区(receiverDistrict)
     */
    private String consignee_district;

    /**
     * 详细地址(receiverAddress)
     */
    private String consignee_address;

    /**
     * 店铺编码(shopCode)
     */
    private String shop_code;

    /**
     * 店铺名称(shopName)
     */
    private String shop_name;

    /**
     * 商品编码(goodsCode)
     */
    private String goods_code;

    /**
     * 发货单号(deliveryOrderNum)
     */
    private String sent_code;

    /**
     * 商品名称(goodsName)
     */
    private String goods_name;

    /**
     * SAP物料编码(materialCode)
     */
    private String mat_code;

    /**
     * SAP物料名称(matName)
     */
    private String mat_name;

    /**
     * 发货数量(iquantity)
     */
    private BigDecimal sent_qty;

    /**
     * 销售单位(mainUnitName)
     */
    private String sale_unit_name;

    /**
     * 发货时间(deliveryTime)
     */
    @JsonFormat(pattern = "yyyyMMddHHmmss")
    private Date sent_time;

    /**
     * 仓库编码(warehouseCode)
     */
    private String warehouse_code;

    /**
     * 仓库名称(warehouseName)
     */
    private String warehouse_name;

    /**
     * 序列码(箱码/盒码)(snCode)
     */
    private String sn_code;

    /**
     * 首次入仓日期
     */
    private String etl_create_time;

    /**
     * 最近入仓日期
     */
    private String etl_update_time;
}
