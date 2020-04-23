package org.linlinjava.litemall.db.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.linlinjava.litemall.db.domain.LitemallStockGoods;
import org.linlinjava.litemall.db.domain.LitemallStockGoodsExample;
import org.linlinjava.litemall.db.domain.extend.LitemallStockGoodsExtend;

public interface LitemallStockGoodsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_stock_goods
     *
     * @mbg.generated
     */
    long countByExample(LitemallStockGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_stock_goods
     *
     * @mbg.generated
     */
    int deleteByExample(LitemallStockGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_stock_goods
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_stock_goods
     *
     * @mbg.generated
     */
    int insert(LitemallStockGoods record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_stock_goods
     *
     * @mbg.generated
     */
    int insertSelective(LitemallStockGoods record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_stock_goods
     *
     * @mbg.generated
     */
    LitemallStockGoods selectOneByExample(LitemallStockGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_stock_goods
     *
     * @mbg.generated
     */
    LitemallStockGoods selectOneByExampleSelective(@Param("example") LitemallStockGoodsExample example, @Param("selective") LitemallStockGoods.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_stock_goods
     *
     * @mbg.generated
     */
    List<LitemallStockGoods> selectByExampleSelective(@Param("example") LitemallStockGoodsExample example, @Param("selective") LitemallStockGoods.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_stock_goods
     *
     * @mbg.generated
     */
    List<LitemallStockGoods> selectByExample(LitemallStockGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_stock_goods
     *
     * @mbg.generated
     */
    LitemallStockGoods selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") LitemallStockGoods.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_stock_goods
     *
     * @mbg.generated
     */
    LitemallStockGoods selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_stock_goods
     *
     * @mbg.generated
     */
    LitemallStockGoods selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_stock_goods
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") LitemallStockGoods record, @Param("example") LitemallStockGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_stock_goods
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") LitemallStockGoods record, @Param("example") LitemallStockGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_stock_goods
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(LitemallStockGoods record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_stock_goods
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(LitemallStockGoods record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_stock_goods
     *
     * @mbg.generated
     */
    int logicalDeleteByExample(@Param("example") LitemallStockGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_stock_goods
     *
     * @mbg.generated
     */
    int logicalDeleteByPrimaryKey(Integer id);

    List<LitemallStockGoodsExtend> selectGoodsByStockId(@Param("stockId") Integer stockId);
}