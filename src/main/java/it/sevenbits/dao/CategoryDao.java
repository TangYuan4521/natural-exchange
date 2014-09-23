package it.sevenbits.dao;

import it.sevenbits.entity.Category;
import it.sevenbits.entity.hibernate.CategoryEntity;

import java.util.List;
import java.util.Set;

/**
 *Interface
 */
public interface CategoryDao {

    /**
     * Create category to DB
     * @param category  category
     */
    void create(Category category);

    /**
     *  id
     * @param id -
     * @return Category
     */
    Category findById(Long id);

    CategoryEntity findEntityBySlug(final String name);

    /**
     * Search categories by slugs.
     * @param slugs
     * @return
     */
    Set<CategoryEntity> findBySlugs(final String[] slugs);

    int categoryCount();

    /**
     * Return list of categories
     * @return  list
     */
    List<Category> findAll();

    List<Category> findThreeLastCategories();

    /**
     * Change category
     * @param category to change
     */
    void update(Category category);

    /**
     * Delete category
     * @param category to delete
     */
    void delete(Category category);
}
