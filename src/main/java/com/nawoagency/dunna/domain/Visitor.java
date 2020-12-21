package com.nawoagency.dunna.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.nawoagency.dunna.domain.enumeration.Gender;

import com.nawoagency.dunna.domain.enumeration.IdcardType;

/**
 * A Visitor.
 */
@Entity
@Table(name = "visitor")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "visitor")
public class Visitor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender_type")
    private Gender genderType;

    @Column(name = "name")
    private String name;

    @Column(name = "lastname")
    private String lastname;

    @Enumerated(EnumType.STRING)
    @Column(name = "idcard_type")
    private IdcardType idcardType;

    @Column(name = "card_expiring_date")
    private String cardExpiringDate;

    @Column(name = "birth_date")
    private String birthDate;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "card_country")
    private Integer cardCountry;

    @Column(name = "nationality")
    private Integer nationality;

    @OneToMany(mappedBy = "visitor")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Partner> partners = new HashSet<>();

    @OneToMany(mappedBy = "v")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Country> cs = new HashSet<>();

    @OneToMany(mappedBy = "v")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Country> cs = new HashSet<>();

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

    public Visitor genderType(Gender genderType) {
        this.genderType = genderType;
        return this;
    }

    public void setGenderType(Gender genderType) {
        this.genderType = genderType;
    }

    public String getName() {
        return name;
    }

    public Visitor name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public Visitor lastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public IdcardType getIdcardType() {
        return idcardType;
    }

    public Visitor idcardType(IdcardType idcardType) {
        this.idcardType = idcardType;
        return this;
    }

    public void setIdcardType(IdcardType idcardType) {
        this.idcardType = idcardType;
    }

    public String getCardExpiringDate() {
        return cardExpiringDate;
    }

    public Visitor cardExpiringDate(String cardExpiringDate) {
        this.cardExpiringDate = cardExpiringDate;
        return this;
    }

    public void setCardExpiringDate(String cardExpiringDate) {
        this.cardExpiringDate = cardExpiringDate;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public Visitor birthDate(String birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public Visitor cardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
        return this;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Integer getCardCountry() {
        return cardCountry;
    }

    public Visitor cardCountry(Integer cardCountry) {
        this.cardCountry = cardCountry;
        return this;
    }

    public void setCardCountry(Integer cardCountry) {
        this.cardCountry = cardCountry;
    }

    public Integer getNationality() {
        return nationality;
    }

    public Visitor nationality(Integer nationality) {
        this.nationality = nationality;
        return this;
    }

    public void setNationality(Integer nationality) {
        this.nationality = nationality;
    }

    public Set<Partner> getPartners() {
        return partners;
    }

    public Visitor partners(Set<Partner> partners) {
        this.partners = partners;
        return this;
    }

    public Visitor addPartner(Partner partner) {
        this.partners.add(partner);
        partner.setVisitor(this);
        return this;
    }

    public Visitor removePartner(Partner partner) {
        this.partners.remove(partner);
        partner.setVisitor(null);
        return this;
    }

    public void setPartners(Set<Partner> partners) {
        this.partners = partners;
    }

    public Set<Country> getCs() {
        return cs;
    }

    public Visitor cs(Set<Country> countries) {
        this.cs = countries;
        return this;
    }

    public Visitor addC(Country country) {
        this.cs.add(country);
        country.setV(this);
        return this;
    }

    public Visitor removeC(Country country) {
        this.cs.remove(country);
        country.setV(null);
        return this;
    }

    public void setCs(Set<Country> countries) {
        this.cs = countries;
    }

    public Set<Country> getCs() {
        return cs;
    }

    public Visitor cs(Set<Country> countries) {
        this.cs = countries;
        return this;
    }

    public Visitor addC(Country country) {
        this.cs.add(country);
        country.setV(this);
        return this;
    }

    public Visitor removeC(Country country) {
        this.cs.remove(country);
        country.setV(null);
        return this;
    }

    public void setCs(Set<Country> countries) {
        this.cs = countries;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Visitor)) {
            return false;
        }
        return id != null && id.equals(((Visitor) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Visitor{" +
            "id=" + getId() +
            ", genderType='" + getGenderType() + "'" +
            ", name='" + getName() + "'" +
            ", lastname='" + getLastname() + "'" +
            ", idcardType='" + getIdcardType() + "'" +
            ", cardExpiringDate='" + getCardExpiringDate() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            ", cardNumber='" + getCardNumber() + "'" +
            ", cardCountry=" + getCardCountry() +
            ", nationality=" + getNationality() +
            "}";
    }
}
