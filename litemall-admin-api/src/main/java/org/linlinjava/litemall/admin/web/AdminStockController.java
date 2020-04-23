package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.linlinjava.litemall.admin.annotation.RequiresPermissionsDesc;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.core.validator.Order;
import org.linlinjava.litemall.core.validator.Sort;
import org.linlinjava.litemall.db.domain.LitemallBrand;
import org.linlinjava.litemall.db.domain.LitemallStock;
import org.linlinjava.litemall.db.domain.extend.LitemallStockExtend;
import org.linlinjava.litemall.db.domain.extend.LitemallStockGoodsExtend;
import org.linlinjava.litemall.db.service.LitemallBrandService;
import org.linlinjava.litemall.db.service.LitemallStockGoodsService;
import org.linlinjava.litemall.db.service.LitemallStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/admin/stock")
@Validated
public class AdminStockController {
    private final Log logger = LogFactory.getLog(AdminStockController.class);

    @Autowired
    private LitemallStockService stockService;
    @Autowired
    private LitemallStockGoodsService stockGoodsService;

    @RequiresPermissions("admin:stock:list")
    @RequiresPermissionsDesc(menu = {"商场管理", "入库管理"}, button = "查询")
    @GetMapping("/list")
    public Object list() {
        List<LitemallStock> list = stockService.all();

        return ResponseUtil.ok(list);
    }

    @RequiresPermissions("admin:stock:pagelist")
    @RequiresPermissionsDesc(menu = {"商场管理", "入库管理"}, button = "查询")
    @GetMapping("/pagelist")
    public Object list(String id, String name, String address,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        List<LitemallStock> stockList = stockService.querySelective(id, name, address, page, limit, sort, order);
        

        return ResponseUtil.okList(stockList);
    }

    private Object validate(LitemallStock stock) {
        String stockSn = stock.getStockSn();
        if (StringUtils.isEmpty(stockSn)) {
            return ResponseUtil.badArgument();
        }

        String warehouseId = stock.getWarehouseId().toString();
        if (StringUtils.isEmpty(warehouseId)) {
            return ResponseUtil.badArgument();
        }
        
        return null;
    }

    @RequiresPermissions("admin:stock:create")
    @RequiresPermissionsDesc(menu = {"商场管理", "入库管理"}, button = "添加")
    @PostMapping("/create")
    public Object create(@RequestBody LitemallStockExtend stock) {
        Object error = validate(stock);
        if (error != null) {
            return error;
        }
        stockService.add(stock);
        return ResponseUtil.ok(stock);
    }

    @RequiresPermissions("admin:stock:read")
    @RequiresPermissionsDesc(menu = {"商场管理", "入库管理"}, button = "详情")
    @GetMapping("/read")
    public Object read(@NotNull Integer id) {
        LitemallStock Stock = stockService.findById(id);
        return ResponseUtil.ok(Stock);
    }

    @RequiresPermissions("admin:stock:update")
    @RequiresPermissionsDesc(menu = {"商场管理", "入库管理"}, button = "编辑")
    @PostMapping("/update")
    public Object update(@RequestBody LitemallStock stock) {
        Object error = validate(stock);
        if (error != null) {
            return error;
        }
        if (stockService.updateById(stock) == 0) {
            return ResponseUtil.updatedDataFailed();
        }
        return ResponseUtil.ok(stock);
    }

    @RequiresPermissions("admin:stock:delete")
    @RequiresPermissionsDesc(menu = {"商场管理", "入库管理"}, button = "删除")
    @PostMapping("/delete")
    public Object delete(@RequestBody LitemallStock stock) {
        Integer id = stock.getId();
        if (id == null) {
            return ResponseUtil.badArgument();
        }
        stockService.deleteById(id);
        return ResponseUtil.ok();
    }

    @GetMapping("/listGoodsByStock")
    public Object listBySupplier(@NotNull Integer stockId) {
        List<LitemallStockGoodsExtend> goodsList = stockGoodsService.goodsListByStockId(stockId);
        return ResponseUtil.ok(goodsList);
    }

}
