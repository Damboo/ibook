package com.trs.ibook.core.dao;

import com.google.common.collect.ImmutableMap;
import com.season.common.JsonUtil;
import com.season.lock.optimistic.exception.OptimisticException;
import com.season.orm.dao.ISeasonDao;
import com.season.orm.dao.TableBuilder;
import com.trs.ibook.core.config.IBookContext;
import com.trs.ibook.core.pojo.AbstractPOJO;
import com.trs.ibook.core.pojo.extend.IsDelete;
import com.trs.ibook.core.pojo.extend.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


/**
 * Title: DAO超类
 * Description:
 * Copyright: 2017 北京拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company: 北京拓尔思信息技术股份有限公司(TRS)
 * Project: metadata
 * Author: Vincent
 * Create Time: 2017-08-24 15:13
 */
public abstract class AbstractDAO<T extends AbstractPOJO> implements ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(AbstractDAO.class);

    protected Class<T> entityClass;

    protected T entityExample;

    @Autowired
    protected ISeasonDao seasonDao;

    @Autowired
    protected ISeasonDao slaverDao;

    @Autowired
    protected IBookContext iBookContext;

    public AbstractDAO() {
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        entityClass = (Class) params[0];
        try {
            entityExample = entityClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public T save(T t) {
//        t.preInsert();
        t = seasonDao.save(t);
        return t;
    }

    public int update(T t) {
//        t.preUpdate();
        if (t instanceof Version) {
            Version version = (Version) t;
            int count = 0;
            try {
                count = seasonDao.updateByColumn(t, null, ImmutableMap.of("version", version.getVersion() - 1));
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("数据库更新操作失败，scoreBasePojo=" + JsonUtil.toJSONString(t));
            }
            if (count < 1) {
                throw new OptimisticException("乐观锁更新冲突");
            }
            if (count > 1) {
                throw new RuntimeException("系统错误");
            }
            return count;
        } else {
            return seasonDao.update(t);
        }
    }

    public int update(T t, String... columns) {
//        t.preUpdate();
        if (t instanceof Version) {
            Version version = (Version) t;
            int count = 0;
            try {
                version.setVersion(version.getVersion() - 1);
//                columns = ArrayKit.merge(columns,new String[]{"version","operateDate","operateBy"});
                count = seasonDao.update(t, columns);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("数据库更新操作失败，scoreBasePojo=" + JsonUtil.toJSONString(t));
            }
            if (count < 1) {
                throw new OptimisticException("乐观锁更新冲突");
            }
            if (count > 1) {
                throw new RuntimeException("系统错误");
            }
            return count;
        } else {
//            columns = ArrayKit.merge(columns,new String[]{"operateDate","operateBy"});
            return seasonDao.update(t, columns);
        }
    }

    /**
     * 根据id查询实体
     *
     * @param id
     * @return
     */
    public T findById(Object id) {
        String pkName = TableBuilder.getPkName(entityClass);
        String tableName = TableBuilder.getTableName(entityClass);
        String sql = "select * from " + tableName + " where " + pkName + "=?";
        if (entityExample instanceof IsDelete) {
            sql += " and isDelete=0";
        }
        return seasonDao.findFirst(entityClass, sql, id);
    }

    /**
     * 物理删除
     *
     * @param id
     * @return
     */
    public int physicalDelete(Object id) {
        return seasonDao.deleteById(entityClass, id);
    }

    /**
     * 逻辑删除
     *
     * @param id
     * @return
     */
    public int delete(Object id) {
        String pkName = TableBuilder.getPkName(entityClass);
        String tableName = TableBuilder.getTableName(entityClass);
        String sql = "update " + tableName + " set isDelete=1 where " + pkName + "=? and isDelete=0";
        return seasonDao.execute(sql, id);
    }

    /**
     * 判断主键是否存在
     *
     * @param id
     * @return
     */
    public boolean exist(Object id) {
        String pkName = TableBuilder.getPkName(entityClass);
        String tableName = TableBuilder.getTableName(entityClass);
        String sql = "select count(1) from " + tableName + " where " + pkName + "=?";
        if (entityExample instanceof IsDelete) {
            sql += " and isDelete=0";
        }
        int count = seasonDao.getJdbcTemplate().queryForObject(sql, Integer.class, id);
        return count > 0;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (applicationContext.containsBean("slaver")) {
            slaverDao = applicationContext.getBean("slaver", ISeasonDao.class);
        }
    }

}
