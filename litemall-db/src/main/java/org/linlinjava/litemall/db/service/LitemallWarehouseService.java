package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.LitemallWarehouseMapper;
import org.linlinjava.litemall.db.domain.LitemallWarehouse;
import org.linlinjava.litemall.db.domain.LitemallWarehouse.Column;
import org.linlinjava.litemall.db.domain.LitemallWarehouseExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LitemallWarehouseService {
    @Resource
    private LitemallWarehouseMapper warehouseMapper;
    private Column[] columns = new Column[]{Column.id, Column.name, Column.address};

    public List<LitemallWarehouse> query(Integer page, Integer limit, String sort, String order) {
        LitemallWarehouseExample example = new LitemallWarehouseExample();
        example.or().andDeletedEqualTo(false);
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        PageHelper.startPage(page, limit);
        return warehouseMapper.selectByExampleSelective(example, columns);
    }

    public List<LitemallWarehouse> query(Integer page, Integer limit) {
        return query(page, limit, null, null);
    }

    public LitemallWarehouse findById(Integer id) {
        return warehouseMapper.selectByPrimaryKey(id);
    }

    public List<LitemallWarehouse> querySelective(String id, String name, String address, Integer page, Integer size, String sort, String order) {
        LitemallWarehouseExample example = new LitemallWarehouseExample();
        LitemallWarehouseExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(id)) {
            criteria.andIdEqualTo(Integer.valueOf(id));
        }
        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        if (!StringUtils.isEmpty(address)) {
            criteria.andAddressLike("%" + address + "%");
        }

        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return warehouseMapper.selectByExample(example);
    }

    public int updateById(LitemallWarehouse warehouse) {
        warehouse.setUpdateTime(LocalDateTime.now());
        return warehouseMapper.updateByPrimaryKeySelective(warehouse);
    }

    public void deleteById(Integer id) {
        warehouseMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(LitemallWarehouse warehouse) {
        warehouse.setAddTime(LocalDateTime.now());
        warehouse.setUpdateTime(LocalDateTime.now());
        warehouseMapper.insertSelective(warehouse);
    }

    public List<LitemallWarehouse> all() {
        LitemallWarehouseExample example = new LitemallWarehouseExample();
        example.or().andDeletedEqualTo(false);
        return warehouseMapper.selectByExample(example);
    }
}
