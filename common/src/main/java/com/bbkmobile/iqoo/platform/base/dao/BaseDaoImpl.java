package com.bbkmobile.iqoo.platform.base.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.Work;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.bbkmobile.iqoo.common.util.ReflectUtil;
import com.bbkmobile.iqoo.platform.annotation.field.FieldConfig;
import com.bbkmobile.iqoo.platform.annotation.field.type.MatchModeType;
import com.bbkmobile.iqoo.platform.annotation.field.type.OrderType;
import com.bbkmobile.iqoo.platform.annotation.field.type.ProjectionType;

/**
 * @author wwf
 * 
 * @param <T>
 * @param <ID>
 */
public class BaseDaoImpl<T, ID extends Serializable> extends
        HibernateDaoSupport implements BaseDao<T, ID> {
    @Autowired
    public void init(SessionFactory factory) {
        setSessionFactory(factory);
    }

    private Class<T> entityClass;

    @SuppressWarnings("unchecked")
    protected Class<T> getEntityClass() {
        if (entityClass == null) {
            entityClass = (Class<T>) ((ParameterizedType) getClass()
                    .getGenericSuperclass()).getActualTypeArguments()[0];
        }
        return entityClass;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.bbkmobile.iqoo.platform.base.dao.BaseDao#save(java.lang.Object)
     */
    public void save(T t) {
        getHibernateTemplate().save(t);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.bbkmobile.iqoo.platform.base.dao.BaseDao#saveOrUpdate(java.lang.Object
     * )
     */
    public void saveOrUpdate(T t) {
        getHibernateTemplate().saveOrUpdate(t);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.bbkmobile.iqoo.platform.base.dao.BaseDao#load(java.io.Serializable)
     */
    public T load(ID id) {
        return getHibernateTemplate().load(getEntityClass(), id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.bbkmobile.iqoo.platform.base.dao.BaseDao#get(java.io.Serializable)
     */
    public T get(ID id) {
        return getHibernateTemplate().get(getEntityClass(), id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.bbkmobile.iqoo.platform.base.dao.BaseDao#contains(java.lang.Object)
     */
    public boolean contains(T t) {
        return getHibernateTemplate().contains(t);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.bbkmobile.iqoo.platform.base.dao.BaseDao#delete(java.lang.Object)
     */
    public void delete(T t) {
        getHibernateTemplate().delete(t);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.bbkmobile.iqoo.platform.base.dao.BaseDao#deleteById(java.io.Serializable
     * )
     */
    public boolean deleteById(ID Id) {
        T t = get(Id);
        if (t == null) {
            return false;
        }
        delete(t);
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.bbkmobile.iqoo.platform.base.dao.BaseDao#deleteAll(java.util.Collection
     * )
     */
    public void deleteAll(Collection<T> entities) {
        getHibernateTemplate().deleteAll(entities);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.bbkmobile.iqoo.platform.base.dao.BaseDao#refresh(java.lang.Object)
     */
    public void refresh(T t) {
        getHibernateTemplate().refresh(t);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.bbkmobile.iqoo.platform.base.dao.BaseDao#update(java.lang.Object)
     */
    public void update(T t) {
        getHibernateTemplate().update(t);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.bbkmobile.iqoo.platform.base.dao.BaseDao#update(java.io.Serializable,
     * java.lang.Object)
     */
    public void update(ID id, Object bean) {
        T t = load(id);
        ReflectUtil.mappingNotNullPropertyValuesByAnnotation(bean, t,
                FieldConfig.class);
        update(t);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.bbkmobile.iqoo.platform.base.dao.BaseDao#listByProperty(java.lang
     * .String, java.lang.Object)
     */
    public List<T> listByProperty(final String pName, final Object pValue) {
        return listByProperties(new HashMap<String, Object>() {
            private static final long serialVersionUID = 1L;
            {
                put(pName, pValue);
            }
        });
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.bbkmobile.iqoo.platform.base.dao.BaseDao#listByProperties(java.util
     * .Map)
     */
    public List<T> listByProperties(Map<String, Object> properties) {
        List<Criterion> criterions = new ArrayList<Criterion>();
        for (Map.Entry<String, Object> map : properties.entrySet()) {
            criterions.add(Restrictions.eq(map.getKey(), map.getValue()));
        }
        return findPageByCriteria(null, null, null, null, criterions);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.bbkmobile.iqoo.platform.base.dao.BaseDao#listInProperty(java.lang
     * .String, java.util.List)
     */
    public List<T> listInProperty(String property, List<Object> inValues) {
        List<Criterion> criterions = new ArrayList<Criterion>();
        criterions.add(Restrictions.in(property, inValues));
        return findPageByCriteria(null, null, null, null, criterions);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.bbkmobile.iqoo.platform.base.dao.BaseDao#listByEqBeanNotNullProperty
     * (java.lang.Object)
     */
    public List<T> listByBean(T t) {
        return listByBean(null, null, t);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.bbkmobile.iqoo.platform.base.dao.BaseDao#listByBean(java.lang.Integer
     * , java.lang.Integer, java.lang.Object)
     */
    public List<T> listByBean(Integer pageNo, Integer pageSize, T t) {

        Class<FieldConfig> aclazz = FieldConfig.class;
        List<Criterion> criterions = new ArrayList<Criterion>();
        List<Projection> projections = new ArrayList<Projection>();
        List<Order> orders = new ArrayList<Order>();
        if (t != null) {
            Map<String, Field> fields = ReflectUtil.getClassFields(
                    t.getClass(), aclazz);
            for (Map.Entry<String, Field> map : fields.entrySet()) {
                String key = map.getKey();
                String entityKey = getEntityClass().getSimpleName() + "." + key;
                Field field = map.getValue();
                FieldConfig config = field.getAnnotation(aclazz);
                ProjectionType projectionType = config.criterionProjection();
                if (projectionType.isDefault()) {
                    projections.add(Projections.property(entityKey).as(key));
                }
                OrderType orderType = config.criterionOrder();
                if (!orderType.isDefault()) {
                    if (orderType.equals(OrderType.ASC)) {
                        orders.add(Order.asc(entityKey));
                    } else {
                        orders.add(Order.desc(entityKey));
                    }
                }
                Object value = ReflectUtil.getObjectFieldValue(t, field);
                if (value == null) {
                    continue;
                }
                MatchModeType modeType = config.criterionMatchMode();
                if (modeType.isDefault()) {
                    criterions.add(Restrictions.eq(entityKey, value));
                } else {
                    criterions.add(Restrictions.like(entityKey,
                            ObjectUtils.toString(value),
                            modeType.getMatchMode()));
                }
            }
        }
        return findPageByCriteria(pageNo, pageSize, orders, projections,
                criterions);

    };

    /**
     * 通过hql查询
     * 
     * @param hql
     * @param values
     * @return
     */
    @SuppressWarnings("unchecked")
    protected T getByHQL(final String hql, final Object... values) {
        return this.getHibernateTemplate().execute(new HibernateCallback<T>() {
            public T doInHibernate(Session session) throws HibernateException {
                Query query = createHqlQuery(session, hql, values);
                return (T) query.uniqueResult();
            }
        });
    }

    /**
     * 通过sql查询
     * 
     * @param sql
     * @param values
     * @return
     */
    @SuppressWarnings("unchecked")
    protected T getBySQL(final String sql, final Object... values) {
        return this.getHibernateTemplate().execute(new HibernateCallback<T>() {
            public T doInHibernate(Session session) throws HibernateException {
                Query query = createSqlQuery(session, sql, values);
                return (T) query.uniqueResult();
            }
        });
    }

    /**
     * 通过hql查询
     * 
     * @param hql
     * @param values
     * @return
     */
    @SuppressWarnings("unchecked")
    protected List<T> getListByHQL(final String hql, final Object... values) {
        return this.getHibernateTemplate().execute(
                new HibernateCallback<List<T>>() {
                    public List<T> doInHibernate(Session session)
                            throws HibernateException {
                        Query query = createHqlQuery(session, hql, values);
                        return query.list();
                    }
                });
    }

    /**
     * 通过sql查询
     * 
     * @param sql
     * @param values
     * @return
     */
    @SuppressWarnings("unchecked")
    protected List<T> getListBySQL(final String sql, final Object... values) {
        return this.getHibernateTemplate().execute(
                new HibernateCallback<List<T>>() {
                    public List<T> doInHibernate(Session session)
                            throws HibernateException {
                        Query query = createSqlQuery(session, sql, values);
                        return query.list();
                    }
                });
    }

    /**
     * 通过sql查询
     * 
     * @param sql
     * @param map
     * @param values
     * @return
     */
    protected List<T> findListBySql(final String sql, final RowMapper<T> map,
            final Object... values) {
        return this.getHibernateTemplate().execute(
                new HibernateCallback<List<T>>() {
                    public List<T> doInHibernate(Session session)
                            throws HibernateException {
                        final List<T> list = new ArrayList<T>();
                        Work jdbcWork = new Work() {
                            public void execute(Connection connection)
                                    throws SQLException {
                                PreparedStatement ps = null;
                                ResultSet rs = null;
                                try {
                                    ps = connection.prepareStatement(sql);
                                    for (int i = 0; i < values.length; i++) {
                                        setParameter(ps, i, values[i]);
                                    }
                                    rs = ps.executeQuery();
                                    int index = 0;
                                    while (rs.next()) {
                                        T obj = map.mapRow(rs, index++);
                                        list.add(obj);
                                    }
                                } finally {
                                    if (rs != null) {
                                        rs.close();
                                    }
                                    if (ps != null) {
                                        ps.close();
                                    }
                                }
                            }
                        };
                        session.doWork(jdbcWork);
                        return list;
                    }
                });

    }

    private void setParameter(PreparedStatement ps, int pos, Object data)
            throws SQLException {
        if (data == null) {
            ps.setNull(pos + 1, Types.VARCHAR);
            return;
        }
        Class<?> dataCls = data.getClass();
        if (String.class.equals(dataCls)) {
            ps.setString(pos + 1, (String) data);
        } else if (boolean.class.equals(dataCls)) {
            ps.setBoolean(pos + 1, ((Boolean) data));
        } else if (int.class.equals(dataCls)) {
            ps.setInt(pos + 1, (Integer) data);
        } else if (double.class.equals(dataCls)) {
            ps.setDouble(pos + 1, (Double) data);
        } else if (Date.class.equals(dataCls)) {
            Date val = (Date) data;
            ps.setTimestamp(pos + 1, new Timestamp(val.getTime()));
        } else if (BigDecimal.class.equals(dataCls)) {
            ps.setBigDecimal(pos + 1, (BigDecimal) data);
        } else {
            ps.setObject(pos + 1, data);
        }
    }

    private Query createHqlQuery(Session session, String hql, Object... values) {
        Query query = session.createQuery(hql);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return query;
    }

    private Query createSqlQuery(Session session, String sql, Object... values) {
        Query query = session.createSQLQuery(sql);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return query;
    }

    protected Long countByCriteria(final Criterion[] criterions) {
        return this.getHibernateTemplate().execute(
                new HibernateCallback<Long>() {
                    public Long doInHibernate(Session session)
                            throws HibernateException {
                        Criteria criteria = session
                                .createCriteria(getEntityClass());
                        if (criterions != null) {
                            for (Criterion criterion : criterions) {
                                if (criterion == null) {
                                    continue;
                                }
                                criteria.add(criterion);
                            }
                        }
                        Object re = criteria.setProjection(
                                Projections.rowCount()).uniqueResult();
                        return (Long) re;
                    }
                });
    }

    /**
     * 分页查询
     * 
     * @param pageNo
     * @param pageSize
     * @param order
     * @param projections
     * @param criterions
     * @return
     */
    protected List<T> findPageByCriteria(final Integer pageNo,
            final Integer pageSize, final Collection<Order> orders,
            final Collection<Projection> projections,
            final Collection<Criterion> criterions) {
        return this.getHibernateTemplate().execute(
                new HibernateCallback<List<T>>() {
                    public List<T> doInHibernate(Session session)
                            throws HibernateException {
                        Criteria criteria = session.createCriteria(
                                getEntityClass(), getEntityClass()
                                        .getSimpleName());
                        if (projections != null) {
                            ProjectionList projectionList = Projections
                                    .projectionList();
                            for (Projection projection : projections) {
                                if (projection == null) {
                                    continue;
                                }
                                projectionList.add(projection);
                            }
                            criteria.setProjection(projectionList);
                            criteria.setResultTransformer(new AliasToBeanResultTransformer(
                                    getEntityClass()));
                        }
                        if (criterions != null) {
                            for (Criterion criterion : criterions) {
                                if (criterion == null) {
                                    continue;
                                }
                                criteria.add(criterion);
                            }
                        }
                        if (orders != null) {
                            for (Order order : orders) {
                                criteria.addOrder(order);
                            }
                        }
                        if (pageNo != null && pageSize != null) {
                            criteria.setFirstResult((pageNo - 1) * pageSize);
                            criteria.setMaxResults(pageSize);
                        }
                        @SuppressWarnings("unchecked")
                        List<T> itemList = criteria.list();
                        if (itemList == null) {
                            itemList = new ArrayList<T>();
                        }
                        return itemList;
                    }
                });
    }
}
