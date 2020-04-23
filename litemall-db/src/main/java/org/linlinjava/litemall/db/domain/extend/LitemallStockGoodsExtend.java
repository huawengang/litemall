package org.linlinjava.litemall.db.domain.extend;

import lombok.Data;
import org.linlinjava.litemall.db.domain.LitemallStockGoods;

/**
 * Created by hwg on 2020/4/22
 */
@Data
public class LitemallStockGoodsExtend extends LitemallStockGoods {
    private String stockSn;
    private String goodsName;
}
