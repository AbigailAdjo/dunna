package com.nawoagency.dunna.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;

import com.nawoagency.dunna.domain.enumeration.Gender;

/**
 * A UserExtended.
 */
@Entity
@Table(name = "user_extended")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "userextended")
public class UserExtended implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender_type")
    private Gender genderType;

    @NotNull
    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "responsible")
    private Boolean responsible;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @Lob
    @Column(name = "avatar")
    private byte[] avatar;

    @Column(name = "avatar_content_type")
    private String avatarContentType;

    @Column(name = "last_login")
    private Instant lastLogin;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Gender getGenderType() {
        return genderType;
    }

    public UserExtended genderType(Gender genderType) {
        this.genderType = genderType;
        return this;
    }

    public void setGenderType(Gender genderType) {
        this.genderType = genderType;
    }

    public String getPhone() {
        return phone;
    }

    public UserExtended phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean isResponsible() {
        return responsible;
    }

    public UserExtended responsible(Boolean responsible) {
        this.responsible = responsible;
        return this;
    }

    public void setResponsible(Boolean responsible) {
        this.responsible = responsible;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public UserExtended createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public UserExtended updatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public UserExtended createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public UserExtended updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public UserExtended avatar(byte[] avatar) {
        this.avatar = avatar;
        return this;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public String getAvatarContentType() {
        return avatarContentType;
    }

    public UserExtended avatarContentType(String avatarContentType) {
        this.avatarContentType = avatarContentType;
        return this;
    }

    public void setAvatarContentType(String avatarContentType) {
        this.avatarContentType = avatarContentType;
    }

    public Instant getLastLogin() {
        return lastLogin;
    }

    public UserExtended lastLogin(Instant lastLogin) {
        this.lastLogin = lastLogin;
        return this;
    }

    public void setLastLogin(Instant lastLogin) {
        this.lastLogin = lastLogin;
    }

    public User getUser() {
        return user;
    }

    public UserExtended user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserExtended)) {
            return false;
        }
        return id != null && id.equals(((UserExtended) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserExtended{" +
            "id=" + getId() +
            ", genderType='" + getGenderType() + "'" +
            ", phone='" + getPhone() + "'" +
            ", responsible='" + isResponsible() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", avatar='" + getAvatar() + "'" +
            ", avatarContentType='" + getAvatarContentType() + "'" +
            ", lastLogin='" + getLastLogin() + "'" +
            "}";
    }
}
