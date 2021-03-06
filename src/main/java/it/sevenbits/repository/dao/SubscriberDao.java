package it.sevenbits.repository.dao;

import it.sevenbits.repository.entity.Subscriber;
import java.util.List;

/**
 *Интерфейс, предоставляющий методы работы с сущностью UserEntity
 */
public interface SubscriberDao {

    /**
     * добавить подписчика
     * @param subscriber - subscriber to add in DB
     */
    void create(Subscriber subscriber);

    /**
     *
     */
    void update(final String oldEmail, final String newEmail);

    /**
     *  найти подписчика по id
     * @param id  identif. number of subscriber in DB
     * @return    subscriber
     */
    Subscriber findById(Integer id);

    boolean isExists(Subscriber subscriber);

    /**
     * возвращает  список всех подписчиков
     * @return  list of all subscribers
     */
    List<Subscriber> find();

    /**
     * удалить подписчика
     * @param subscriber to delete
     */
    void delete(Subscriber subscriber);
}
