package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.linlinjava.litemall.admin.annotation.RequiresPermissionsDesc;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.core.validator.Order;
import org.linlinjava.litemall.core.validator.Sort;
import org.linlinjava.litemall.db.domain.LitemallWarehouse;
import org.linlinjava.litemall.db.service.LitemallBrandService;
import org.linlinjava.litemall.db.service.LitemallWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/admin/warehouse")
@Validated
public class AdminWarehouseController {
    private final Log logger = LogFactory.getLog(AdminWarehouseController.class);

    @Autowired
    private LitemallWarehouseService warehouseService;

    @Autowired
    LitemallBrandService brandService;

    @RequiresPermissions("admin:warehouse:list")
    @RequiresPermissionsDesc(menu = {"商场管理", "仓库管理"}, button = "查询")
    @GetMapping("/list")
    public Object list() {
        List<LitemallWarehouse> list = warehouseService.all();

        return ResponseUtil.ok(list);
    }

    @RequiresPermissions("admin:warehouse:pagelist")
    @RequiresPermissionsDesc(menu = {"商场管理", "仓库管理"}, button = "查询")
    @GetMapping("/pagelist")
    public Object list(String id, String name, String address,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        List<LitemallWarehouse> warehouseList = warehouseService.querySelective(id, name, address, page, limit, sort, order);
        

        return ResponseUtil.okList(warehouseList);
    }

    private Object validate(LitemallWarehouse warehouse) {
        String name = warehouse.getName();
        if (StringUtils.isEmpty(name)) {
            return ResponseUtil.badArgument();
        }

        String address = warehouse.getAddress();
        if (StringUtils.isEmpty(address)) {
            return ResponseUtil.badArgument();
        }
        
        return null;
    }

    @RequiresPermissions("admin:warehouse:create")
    @RequiresPermissionsDesc(menu = {"商场管理", "仓库管理"}, button = "添加")
    @PostMapping("/create")
    public Object create(@RequestBody LitemallWarehouse warehouse) {
        Object error = validate(warehouse);
        if (error != null) {
            return error;
        }
        warehouseService.add(warehouse);
        return ResponseUtil.ok(warehouse);
    }

    @RequiresPermissions("admin:warehouse:read")
    @RequiresPermissionsDesc(menu = {"商场管理", "仓库管理"}, button = "详情")
    @GetMapping("/read")
    public Object read(@NotNull Integer id) {
        LitemallWarehouse Warehouse = warehouseService.findById(id);
        return ResponseUtil.ok(Warehouse);
    }

    @RequiresPermissions("admin:warehouse:update")
    @RequiresPermissionsDesc(menu = {"商场管理", "仓库管理"}, button = "编辑")
    @PostMapping("/update")
    public Object update(@RequestBody LitemallWarehouse warehouse) {
        Object error = validate(warehouse);
        if (error != null) {
            return error;
        }
        if (warehouseService.updateById(warehouse) == 0) {
            return ResponseUtil.updatedDataFailed();
        }
        return ResponseUtil.ok(warehouse);
    }

    @RequiresPermissions("admin:warehouse:delete")
    @RequiresPermissionsDesc(menu = {"商场管理", "仓库管理"}, button = "删除")
    @PostMapping("/delete")
    public Object delete(@RequestBody LitemallWarehouse warehouse) {
        Integer id = warehouse.getId();
        if (id == null) {
            return ResponseUtil.badArgument();
        }
        warehouseService.deleteById(id);
        return ResponseUtil.ok();
    }

}
