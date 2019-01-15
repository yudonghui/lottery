package com.daxiang.lottery.database;


import com.daxiang.lottery.entity.LoginResultData;

import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.List;

/**
 * Created by Administrator on 2016/8/5 0005.
 */
public class UserInfoDbManager {
        private org.xutils.DbManager.DaoConfig mDaoConfig;

        public UserInfoDbManager() {
            mDaoConfig = new org.xutils.DbManager.DaoConfig();
            mDaoConfig.setDbName("search.db");
            mDaoConfig.setDbVersion(2);
            mDaoConfig.setDbUpgradeListener(new org.xutils.DbManager.DbUpgradeListener() {
                @Override
                public void onUpgrade(org.xutils.DbManager db, int oldVersion, int newVersion) {
                    db.getDatabase().execSQL("alter table  name add classroom  varchar(10) ");
                }
            });
        }

        /**
         * 插入数据库
         *
         * @param userInfoData
         * @throws DbException
         */
        public void insert(LoginResultData userInfoData) throws DbException {

            x.getDb(mDaoConfig).save(userInfoData);

        }

        /**
         * 批量插入与save公用一个方法
         *
         * @param searchDatas
         * @throws DbException
         */
        public void inserts(List<String> searchDatas) throws DbException {

            x.getDb(mDaoConfig).save(searchDatas);
        }

        /**
         * 查询所有
         *
         * @return
         * @throws DbException
         */
        public List<String> queryALl() throws DbException {

            return x.getDb(mDaoConfig).findAll(String.class);
        }

        /**
         * 按条件查询selector
         *
         * @return
         * @throws DbException
         */
        public List<LoginResultData> queryForWhere() throws DbException {

            return x.getDb(mDaoConfig).selector(LoginResultData.class)
                    .where("name", "=", "张飞")
                    .and("age", "=", "18")
                    .findAll();
        }


        /**
         * 简单的删除
         *
         * @param studentDbData
         * @throws DbException
         */
        public void delete(LoginResultData studentDbData) throws DbException {
            x.getDb(mDaoConfig).delete(studentDbData);
        }

        /**
         * 按条件删除，删除所有叫张飞的人
         *
         * @throws DbException
         */
        public void deleteWherer() throws DbException {
            x.getDb(mDaoConfig).delete(LoginResultData.class, WhereBuilder.b("name", "=", "张飞"));
        }

        /**
         * 简单的修改
         *
         * @param studentDbData
         * @throws DbException
         */
        public void update(LoginResultData studentDbData) throws DbException {

            // studentDbData.setName("刘备");
            x.getDb(mDaoConfig).update(studentDbData);
        }

        /**
         * 按条件修改，修改所有叫张飞的人
         *
         * @throws DbException
         */
        public void updateWherer() throws DbException {
            x.getDb(mDaoConfig).update(LoginResultData.class, WhereBuilder.b("name", "=", "张飞"));
        }
}
