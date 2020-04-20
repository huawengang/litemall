package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.linlinjava.litemall.admin.annotation.RequiresPermissionsDesc;
import org.linlinjava.litemall.admin.vo.SupplierVo;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.core.validator.Order;
import org.linlinjava.litemall.core.validator.Sort;
import org.linlinjava.litemall.db.domain.LitemallSupplier;
import org.linlinjava.litemall.db.service.LitemallBrandService;
import org.linlinjava.litemall.db.service.LitemallSupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin/supplier")
@Validated
public class AdminSupplierController {
    private final Log logger = LogFactory.getLog(AdminSupplierController.class);

    @Autowired
    private LitemallSupplierService supplierService;

    @Autowired
    LitemallBrandService brandService;

    @RequiresPermissions("admin:supplier:list")
    @RequiresPermissionsDesc(menu = {"商场管理", "供应商管理"}, button = "查询")
    @GetMapping("/list")
    public Object list() {
        List<LitemallSupplier> list = supplierService.all();

        return ResponseUtil.ok(list);
    }

    @RequiresPermissions("admin:supplier:pagelist")
    @RequiresPermissionsDesc(menu = {"商场管理", "供应商管理"}, button = "查询")
    @GetMapping("/pagelist")
    public Object list(String id, String name, String contact,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        List<LitemallSupplier> supplierList = supplierService.querySelective(id, name, contact, page, limit, sort, order);

        List<SupplierVo> list = new ArrayList<>();
        for (LitemallSupplier litemallSupplier : supplierList) {
            long brandCount = brandService.countBySupplierId(litemallSupplier.getId());
            SupplierVo supplierVo = new SupplierVo(litemallSupplier);
            supplierVo.setBrandCount(brandCount);
            list.add(supplierVo);
        }

        return ResponseUtil.okList(list);
    }

    private Object validate(LitemallSupplier supplier) {
        String name = supplier.getName();
        if (StringUtils.isEmpty(name)) {
            return ResponseUtil.badArgument();
        }

        String contact = supplier.getContact();
        if (StringUtils.isEmpty(contact)) {
            return ResponseUtil.badArgument();
        }

        String mobile = supplier.getMobile();
        if (StringUtils.isEmpty(mobile)) {
            return ResponseUtil.badArgument();
        }

        String address = supplier.getAddress();
        if (StringUtils.isEmpty(address)) {
            return ResponseUtil.badArgument();
        }
        
        return null;
    }

    @RequiresPermissions("admin:supplier:create")
    @RequiresPermissionsDesc(menu = {"商场管理", "供应商管理"}, button = "添加")
    @PostMapping("/create")
    public Object create(@RequestBody LitemallSupplier supplier) {
        Object error = validate(supplier);
        if (error != null) {
            return error;
        }
        supplierService.add(supplier);
        return ResponseUtil.ok(supplier);
    }

    @RequiresPermissions("admin:supplier:read")
    @RequiresPermissionsDesc(menu = {"商场管理", "供应商管理"}, button = "详情")
    @GetMapping("/read")
    public Object read(@NotNull Integer id) {
        LitemallSupplier Supplier = supplierService.findById(id);
        return ResponseUtil.ok(Supplier);
    }

    @RequiresPermissions("admin:supplier:update")
    @RequiresPermissionsDesc(menu = {"商场管理", "供应商管理"}, button = "编辑")
    @PostMapping("/update")
    public Object update(@RequestBody LitemallSupplier supplier) {
        Object error = validate(supplier);
        if (error != null) {
            return error;
        }
        if (supplierService.updateById(supplier) == 0) {
            return ResponseUtil.updatedDataFailed();
        }
        return ResponseUtil.ok(supplier);
    }

    @RequiresPermissions("admin:supplier:delete")
    @RequiresPermissionsDesc(menu = {"商场管理", "供应商管理"}, button = "删除")
    @PostMapping("/delete")
    public Object delete(@RequestBody LitemallSupplier supplier) {
        Integer id = supplier.getId();
        if (id == null) {
            return ResponseUtil.badArgument();
        }
        supplierService.deleteById(id);
        return ResponseUtil.ok();
    }

}
