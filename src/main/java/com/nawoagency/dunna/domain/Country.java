package com.nawoagency.dunna.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;

/**
 * A Country.
 */
@Entity
@Table(name = "country")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "country")
public class Country implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "country_name", nullable = false)
    private String countryName;

    @NotNull
    @Size(max = 3)
    @Column(name = "country_code_alpha", length = 3, nullable = false)
    private String countryCodeAlpha;

    @NotNull
    @Column(name = "country_code", nullable = false)
    private String countryCode;

    @Column(name = "country_flag")
    private String countryFlag;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @ManyToOne
    @JsonIgnoreProperties(value = "countries", allowSetters = true)
    private Region region;

    @ManyToOne
    @JsonIgnoreProperties(value = "countries", allowSetters = true)
    private Visitor visitor;

    @ManyToOne
    @JsonIgnoreProperties(value = "countries", allowSetters = true)
    private Visitor visitor;

    @ManyToOne
    @JsonIgnoreProperties(value = "countries", allowSetters = true)
    private Partner partner;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public Country countryName(String countryName) {
        this.countryName = countryName;
        return this;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCodeAlpha() {
        return countryCodeAlpha;
    }

    public Country countryCodeAlpha(String countryCodeAlpha) {
        this.countryCodeAlpha = countryCodeAlpha;
        return this;
    }

    public void setCountryCodeAlpha(String countryCodeAlpha) {
        this.countryCodeAlpha = countryCodeAlpha;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public Country countryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryFlag() {
        return countryFlag;
    }

    public Country countryFlag(String countryFlag) {
        this.countryFlag = countryFlag;
        return this;
    }

    public void setCountryFlag(String countryFlag) {
        this.countryFlag = countryFlag;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Country createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Country updatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Country createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public Country updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Region getRegion() {
        return region;
    }

    public Country region(Region region) {
        this.region = region;
        return this;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Visitor getVisitor() {
        return visitor;
    }

    public Country visitor(Visitor visitor) {
        this.visitor = visitor;
        return this;
    }

    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }

    public Visitor getVisitor() {
        return visitor;
    }

    public Country visitor(Visitor visitor) {
        this.visitor = visitor;
        return this;
    }

    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }

    public Partner getPartner() {
        return partner;
    }

    public Country partner(Partner partner) {
        this.partner = partner;
        return this;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Country)) {
            return false;
        }
        return id != null && id.equals(((Country) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Country{" +
            "id=" + getId() +
            ", countryName='" + getCountryName() + "'" +
            ", countryCodeAlpha='" + getCountryCodeAlpha() + "'" +
            ", countryCode='" + getCountryCode() + "'" +
            ", countryFlag='" + getCountryFlag() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            "}";
    }
}
