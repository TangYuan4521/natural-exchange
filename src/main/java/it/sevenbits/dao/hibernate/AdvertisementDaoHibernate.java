package it.sevenbits.dao.hibernate;

import it.sevenbits.dao.AdvertisementDao;
import it.sevenbits.entity.Advertisement;
import it.sevenbits.entity.hibernate.AdvertisementEntity;
import it.sevenbits.util.SortOrder;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static it.sevenbits.util.SortOrder.ASCENDING;

/**
 * Class, which implements AdvertisementDao for Hibernate
 */
@Repository(value = "advertisementDao")
public class AdvertisementDaoHibernate implements AdvertisementDao {

    private HibernateTemplate hibernateTemplate;

    @Autowired
    public AdvertisementDaoHibernate(final SessionFactory sessionFactory) {
        this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    @Override
    public void create(final Advertisement advertisement) {
        return;
    }

    /**
    * Creates and returns the ad. Works with no database.
    */
    @Override
    public Advertisement findById(final Long id) {
        AdvertisementEntity advertisementEntity = this.hibernateTemplate.get(AdvertisementEntity.class, id);

        return  advertisementEntity;
    }

    @Override
    public List<Advertisement> findAll() {
        return this.findAll(ASCENDING, Advertisement.CREATED_DATE_COLUMN_CODE);
    }

    @Override
    public List<Advertisement> findAll(final SortOrder sortType, final String sortBy) {

        SortOrder sortOrder = (sortType == null) ? SortOrder.UNSORTED : sortType;
        String sortingPropertyName = (sortBy == null) ? Advertisement.CREATED_DATE_COLUMN_CODE : sortBy;
        DetachedCriteria criteria = DetachedCriteria.forClass(AdvertisementEntity.class);
        switch (sortOrder) {
            case ASCENDING :
                criteria.addOrder(Order.asc(sortingPropertyName));
                break;
            case DESCENDING :
            case UNSORTED :
                criteria.addOrder(Order.desc(sortingPropertyName));
                break;
        }

        return this.convertEntityList(this.hibernateTemplate.findByCriteria(criteria));
    }

    @Override
    public void update(final Advertisement advertisement) {
        //To change body of implemented methods use File | Settings | File Templates.
        return;
    }

    @Override
    public void delete(final Advertisement advertisement) {
        //To change body of implemented methods use File | Settings | File Templates.
        return;
    }

    private List<Advertisement> convertEntityList(List entities) {

        List<Advertisement> advertisementList = new ArrayList<Advertisement>();
        if (entities != null) {
            List<AdvertisementEntity> advertisementEntityList = (List<AdvertisementEntity>)entities;
            for (AdvertisementEntity entity : advertisementEntityList) {
                advertisementList.add(entity);
            }
        }

        return advertisementList;
    }
}