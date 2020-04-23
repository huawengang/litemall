package org.linlinjava.litemall.db.domain.extend;

import lombok.Data;
import org.linlinjava.litemall.db.domain.LitemallStock;
import org.linlinjava.litemall.db.domain.LitemallStockGoods;

import java.util.List;

/**
 * Created by hwg on 2020/4/22
 */
@Data
public class LitemallStockExtend extends LitemallStock {
    private List<LitemallStockGoods> stockGoods;
}
