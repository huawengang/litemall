package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.LitemallStockMapper;
import org.linlinjava.litemall.db.domain.LitemallStock;
import org.linlinjava.litemall.db.domain.LitemallStock.Column;
import org.linlinjava.litemall.db.domain.LitemallStockExample;
import org.linlinjava.litemall.db.domain.LitemallStockGoods;
import org.linlinjava.litemall.db.domain.extend.LitemallStockExtend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LitemallStockService {
    @Autowired
    private LitemallStockGoodsService stockGoodsService;

    @Resource
    private LitemallStockMapper stockMapper;
    private Column[] columns = new Column[]{Column.id, Column.stockSn, Column.stockTime};

    public List<LitemallStock> query(Integer page, Integer limit, String sort, String order) {
        LitemallStockExample example = new LitemallStockExample();
        example.or().andDeletedEqualTo(false);
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        PageHelper.startPage(page, limit);
        return stockMapper.selectByExampleSelective(example, columns);
    }

    public List<LitemallStock> query(Integer page, Integer limit) {
        return query(page, limit, null, null);
    }

    public LitemallStock findById(Integer id) {
        return stockMapper.selectByPrimaryKey(id);
    }

    public List<LitemallStock> querySelective(String id, String stockSn, String warehouseId, Integer page, Integer size, String sort, String order) {
        LitemallStockExample example = new LitemallStockExample();
        LitemallStockExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(id)) {
            criteria.andIdEqualTo(Integer.valueOf(id));
        }
        if (!StringUtils.isEmpty(stockSn)) {
            criteria.andStockSnLike("%" + stockSn + "%");
        }
        if (!StringUtils.isEmpty(warehouseId)) {
            criteria.andWarehouseIdEqualTo(Integer.valueOf(warehouseId));
        }

        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return stockMapper.selectByExample(example);
    }

    public int updateById(LitemallStock stock) {
        stock.setUpdateTime(LocalDateTime.now());
        return stockMapper.updateByPrimaryKeySelective(stock);
    }

    public void deleteById(Integer id) {
        stockMapper.logicalDeleteByPrimaryKey(id);
    }

    @Transactional
    public void add(LitemallStockExtend stock) {
        stock.setAddTime(LocalDateTime.now());
        stock.setUpdateTime(LocalDateTime.now());
        stockMapper.insertSelective(stock);

        for (LitemallStockGoods stockGood : stock.getStockGoods()) {
            stockGood.setStockId(stock.getId());
            stockGoodsService.add(stockGood);
        }
    }

    public List<LitemallStock> all() {
        LitemallStockExample example = new LitemallStockExample();
        example.or().andDeletedEqualTo(false);
        return stockMapper.selectByExample(example);
    }

}
