package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.LitemallStockGoodsMapper;
import org.linlinjava.litemall.db.domain.LitemallStockGoods;
import org.linlinjava.litemall.db.domain.LitemallStockGoods.Column;
import org.linlinjava.litemall.db.domain.LitemallStockGoodsExample;
import org.linlinjava.litemall.db.domain.extend.LitemallStockGoodsExtend;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LitemallStockGoodsService {
    @Resource
    private LitemallStockGoodsMapper stockGoodsMapper;
    private Column[] columns = new Column[]{Column.id, Column.stockId, Column.stockNum, Column.goodsId, Column.expDate};

    public List<LitemallStockGoods> query(Integer page, Integer limit, String sort, String order) {
        LitemallStockGoodsExample example = new LitemallStockGoodsExample();
        example.or().andDeletedEqualTo(false);
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        PageHelper.startPage(page, limit);
        return stockGoodsMapper.selectByExampleSelective(example, columns);
    }

    public List<LitemallStockGoods> query(Integer page, Integer limit) {
        return query(page, limit, null, null);
    }

    public LitemallStockGoods findById(Integer id) {
        return stockGoodsMapper.selectByPrimaryKey(id);
    }

    public List<LitemallStockGoods> querySelective(String id, String name, String contact, Integer page, Integer size, String sort, String order) {
        LitemallStockGoodsExample example = new LitemallStockGoodsExample();
        LitemallStockGoodsExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(id)) {
            criteria.andIdEqualTo(Integer.valueOf(id));
        }

        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return stockGoodsMapper.selectByExample(example);
    }

    public int updateById(LitemallStockGoods stockGoods) {
        stockGoods.setUpdateTime(LocalDateTime.now());
        return stockGoodsMapper.updateByPrimaryKeySelective(stockGoods);
    }

    public void deleteById(Integer id) {
        stockGoodsMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(LitemallStockGoods stockGoods) {
        stockGoods.setAddTime(LocalDateTime.now());
        stockGoods.setUpdateTime(LocalDateTime.now());
        stockGoodsMapper.insertSelective(stockGoods);
    }

    public List<LitemallStockGoods> all() {
        LitemallStockGoodsExample example = new LitemallStockGoodsExample();
        example.or().andDeletedEqualTo(false);
        return stockGoodsMapper.selectByExample(example);
    }

    public List<LitemallStockGoodsExtend> goodsListByStockId(Integer stockId) {
        return stockGoodsMapper.selectGoodsByStockId(stockId);
    }
}
