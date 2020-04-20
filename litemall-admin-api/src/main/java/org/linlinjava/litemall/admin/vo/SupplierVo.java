package org.linlinjava.litemall.admin.vo;

import lombok.Data;
import org.linlinjava.litemall.db.domain.LitemallSupplier;

@Data
public class SupplierVo {
    private Integer id;
    private String name;
    private String webSite;
    private String contact;
    private String telephone;
    private String mobile;
    private String wechat;
    private String qq;
    private String email;
    private String codezip;
    private String address;
    private Long brandCount;

    public SupplierVo(LitemallSupplier supplier) {
        this.id = supplier.getId();
        this.name = supplier.getName();
        this.webSite = supplier.getWebSite();
        this.contact = supplier.getContact();
        this.telephone = supplier.getTelephone();
        this.mobile = supplier.getMobile();
        this.wechat = supplier.getWechat();
        this.qq = supplier.getQq();
        this.email = supplier.getEmail();
        this.codezip = supplier.getCodezip();
        this.address = supplier.getAddress();
    }

}
