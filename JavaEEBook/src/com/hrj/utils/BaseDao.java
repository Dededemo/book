package com.hrj.utils;

import org.apache.commons.dbutils.*;


/**
 * BaseDao的目的就是去优化dao实现类
 * @param <T>
 */
public abstract class BaseDao<T> {
    public QueryRunner queryRunner;
    public Integer pageSize = 4;
    public BaseDao(){
        queryRunner = new QueryRunner(MyDataSource.getDataSource());
    }


    public RowProcessor getProcessor(){
        //开启下划线->驼峰转换 - start
        BeanProcessor bean = new GenerousBeanProcessor();
        RowProcessor processor = new BasicRowProcessor(bean);
        //开启下划线->驼峰转换 - end
        return processor;
    }

}
