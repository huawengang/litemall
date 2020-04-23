package org.linlinjava.litemall.db.domain.extend;

import lombok.Data;
import org.linlinjava.litemall.db.domain.LitemallGoods;

/**
 * Created by hwg on 2020/4/22
 */
@Data
public class LitemallGoodsExtend extends LitemallGoods {
    private String supplierName;
    private String brandName;
}
