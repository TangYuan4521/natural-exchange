package it.sevenbits.repository.entity.hibernate;


import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.util.Set;

/**
 *Class, which presents Advertisement entity for Hibernate
 */
@Entity
@Table(name = "advertisement")
@NamedQueries({
        @NamedQuery(
            name = "findAllAdvertisements",
            query = "select a from AdvertisementEntity a"
        ),
        @NamedQuery (
            name = "findAllAdvertisementsWithCategoryAndOrderByTitleAsc",
            query = "select a from AdvertisementEntity a where a.categoryEntity.slug = :categoryParam " +
                "order by a.title asc "
        ),
        @NamedQuery (
            name = "findAllAdvertisementsWithCategoryAndOrderByTitleDesc",
            query = "select a from AdvertisementEntity a where a.categoryEntity.slug = :categoryParam " +
                "order by a.title desc "
        ),
        @NamedQuery (
           name = "findAllAdvertisementsWithCategoryAndOrderByDateAsc",
           query = "select a from AdvertisementEntity a where a.categoryEntity.slug = :categoryParam " +
               "order by a.createdDate asc "
        ),
        @NamedQuery (
            name = "findAllAdvertisementsWithCategoryAndOrderByDateDesc",
            query = "select a from AdvertisementEntity a where a.categoryEntity.slug = :categoryParam " +
                "order by a.createdDate desc"
        )
})
public class AdvertisementEntity extends it.sevenbits.repository.entity.Advertisement {
    private Long id;
    private CategoryEntity categoryEntity;
    private UserEntity userEntity;


    private Set<TagEntity> tags;

    public AdvertisementEntity() {
        super();
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    public UserEntity getUserEntity() {
        return userEntity;
    }

    @ManyToOne
    @JoinColumn(name = "category_id")
    public CategoryEntity getCategoryEntity() {
        return categoryEntity;
    }

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    @Column(name = "title", length = 200, nullable = false)
    @Override
    public String getTitle() {
        return super.getTitle();
    }

    @Column(name = "text", length = 1000, nullable = false)
    @Override
    public String getText() {
        return super.getText();
    }

    @Column(length = 255, name = "photo_file", nullable = false)
    @Override
    public String getPhotoFile() {
        return super.getPhotoFile();
    }

    @Column(name = "created_date", nullable = false)
    @Override
    public Long getCreatedDate() {
        return super.getCreatedDate();
    }

    @Column(name = "updated_date", nullable = false)
    @Override
    public Long getUpdatedDate() {
        return super.getUpdatedDate();
    }

    @Column(name = "is_deleted", nullable = false)
    @Override
    public Boolean getIs_deleted() {
        return super.getIs_deleted();
    }

    public void setCategoryEntity(final CategoryEntity categoryEntity) {
        this.categoryEntity = categoryEntity;
        super.setCategory(categoryEntity);
    }

    public void setUserEntity(final UserEntity userEntity) {
        this.userEntity = userEntity;
        super.setUser(userEntity);
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @OneToMany(mappedBy = "advertisement")
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
    @Fetch(FetchMode.JOIN)
    public Set<TagEntity> getTags() {
        return tags;
    }

    public void setTags(Set<TagEntity> tags) {
        this.tags = tags;
    }
}
