package com.nawoagency.dunna.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.nawoagency.dunna.domain.enumeration.PartnerType;

/**
 * A Partner.
 */
@Entity
@Table(name = "partner")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "partner")
public class Partner implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "partner_type")
    private PartnerType partnerType;

    @OneToMany(mappedBy = "partner")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Country> countries = new HashSet<>();

    @OneToMany(mappedBy = "partner")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<User> users = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "partners", allowSetters = true)
    private Visitor visitor;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Partner name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Partner createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Partner updatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Partner createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public Partner updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getEmail() {
        return email;
    }

    public Partner email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public Partner address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public Partner phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public PartnerType getPartnerType() {
        return partnerType;
    }

    public Partner partnerType(PartnerType partnerType) {
        this.partnerType = partnerType;
        return this;
    }

    public void setPartnerType(PartnerType partnerType) {
        this.partnerType = partnerType;
    }

    public Set<Country> getCountries() {
        return countries;
    }

    public Partner countries(Set<Country> countries) {
        this.countries = countries;
        return this;
    }

    public Partner addCountry(Country country) {
        this.countries.add(country);
        country.setPartner(this);
        return this;
    }

    public Partner removeCountry(Country country) {
        this.countries.remove(country);
        country.setPartner(null);
        return this;
    }

    public void setCountries(Set<Country> countries) {
        this.countries = countries;
    }

    public Set<User> getUsers() {
        return users;
    }

    public Partner users(Set<User> users) {
        this.users = users;
        return this;
    }

    public Partner addUser(User user) {
        this.users.add(user);
        user.setPartner(this);
        return this;
    }

    public Partner removeUser(User user) {
        this.users.remove(user);
        user.setPartner(null);
        return this;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Visitor getVisitor() {
        return visitor;
    }

    public Partner visitor(Visitor visitor) {
        this.visitor = visitor;
        return this;
    }

    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Partner)) {
            return false;
        }
        return id != null && id.equals(((Partner) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Partner{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", email='" + getEmail() + "'" +
            ", address='" + getAddress() + "'" +
            ", phone='" + getPhone() + "'" +
            ", partnerType='" + getPartnerType() + "'" +
            "}";
    }
}
