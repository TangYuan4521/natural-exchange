package it.sevenbits.entity.hibernate;

import javax.persistence.*;

/**
 *Class, which presents Advertisement entity for Hibernate
 */
@Entity
@Table(name = "advertisement")
@NamedQueries({
        @NamedQuery (
                name = "findAllAdvertisements",
                query = "select a from AdvertisementEntity a"
        )
})
public class AdvertisementEntity extends it.sevenbits.entity.Advertisement {
    //private Long userId;
    private Long id;
    private CategoryEntity categoryEntity;
    private UserEntity userEntity;

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

    @Column(name = "title",length = 200, nullable = false)
    @Override
    public String getTitle() {
        return super.getTitle();
    }

    @Column(name = "text",length = 1000, nullable = false)
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
    public Boolean getIsDeleted() {
        return super.getIsDeleted();
    }

    public void setCategoryEntity(final CategoryEntity categoryEntity) {
        this.categoryEntity = categoryEntity;
        super.setCategory(categoryEntity);
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
        super.setUser(userEntity);
    }

    public void setId(Long id) {
        this.id = id;
    }
}
