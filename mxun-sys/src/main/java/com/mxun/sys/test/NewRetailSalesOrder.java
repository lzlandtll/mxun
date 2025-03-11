package com.mxun.sys.test;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 新零售公司订单表 实体类。
 * @author zhilinliu
 * @since 2025-02-21
 */
@Data
public class NewRetailSalesOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 交易编号(tid)
     */
    private String tradeId;

    /**
     * 订单编号(cTradeCode)
     */
    private String ordCode;

    /**
     * WMS出库单号(wmsDeliveryCode)
     */
    private String wmsDeliveryCode;

    /**
     * 订单类型(orderType)
     */
    private String ordType;

    /**
     * 收货人姓名(receiverName)
     */
    private String consigneeName;

    /**
     * 手机号(receiverMobile)
     */
    private String consigneePhone;

    /**
     * 省(还没做,本次任务追加)
     */
    private String receiverState;

    /**
     * 市(还没做,本次任务追加)
     */
    private String receiverCity;

    /**
     * 区(receiverDistrict)
     */
    private String consigneeDistrict;

    /**
     * 详细地址(receiverAddress)
     */
    private String consigneeAddress;

    /**
     * 店铺编码(shopCode)
     */
    private String shopCode;

    /**
     * 店铺名称(shopName)
     */
    private String shopName;

    /**
     * 商品编码(goodsCode)
     */
    private String goodsCode;

    /**
     * 发货单号(deliveryOrderNum)
     */
    private String sentCode;

    /**
     * 商品名称(goodsName)
     */
    private String goodsName;

    /**
     * SAP物料编码(materialCode)
     */
    private String matCode;

    /**
     * SAP物料名称(matName)
     */
    private String matName;

    /**
     * 发货数量(iquantity)
     */
    private BigDecimal sentQty;

    /**
     * 销售单位(mainUnitName)
     */
    private String saleUnitName;

    /**
     * 发货时间(deliveryTime)
     */
    private Timestamp sentTime;

    /**
     * 仓库编码(warehouseCode)
     */
    private String warehouseCode;

    /**
     * 仓库名称(warehouseName)
     */
    private String warehouseName;

    /**
     * 序列码(箱码/盒码)(snCode)
     */
    private String snCode;

}
