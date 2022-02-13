package com.area.domain;

import com.area.domain.enumeration.Sexe;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The Employee entity.
 */
@Schema(description = "The Employee entity.")
@Entity
@Table(name = "participant")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Participant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * The firstname attribute.
     */
    @Schema(description = "The firstname attribute.")
    @Column(name = "prenom")
    private String prenom;

    @Column(name = "nom")
    private String nom;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "date_de_naissance")
    private Instant dateDeNaissance;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexe")
    private Sexe sexe;

    @Column(name = "cin")
    private String cin;

    @OneToOne
    @JoinColumn(unique = true)
    private Inscription inscription;

    @OneToOne
    @JoinColumn(unique = true)
    private Faculte faculte;

    @ManyToOne
    @JsonIgnoreProperties(value = { "participants" }, allowSetters = true)
    private Groupe groupe;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Participant id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public Participant prenom(String prenom) {
        this.setPrenom(prenom);
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return this.nom;
    }

    public Participant nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return this.email;
    }

    public Participant email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public Participant password(String password) {
        this.setPassword(password);
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public Participant telephone(String telephone) {
        this.setTelephone(telephone);
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Instant getDateDeNaissance() {
        return this.dateDeNaissance;
    }

    public Participant dateDeNaissance(Instant dateDeNaissance) {
        this.setDateDeNaissance(dateDeNaissance);
        return this;
    }

    public void setDateDeNaissance(Instant dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    public Sexe getSexe() {
        return this.sexe;
    }

    public Participant sexe(Sexe sexe) {
        this.setSexe(sexe);
        return this;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public String getCin() {
        return this.cin;
    }

    public Participant cin(String cin) {
        this.setCin(cin);
        return this;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public Inscription getInscription() {
        return this.inscription;
    }

    public void setInscription(Inscription inscription) {
        this.inscription = inscription;
    }

    public Participant inscription(Inscription inscription) {
        this.setInscription(inscription);
        return this;
    }

    public Faculte getFaculte() {
        return this.faculte;
    }

    public void setFaculte(Faculte faculte) {
        this.faculte = faculte;
    }

    public Participant faculte(Faculte faculte) {
        this.setFaculte(faculte);
        return this;
    }

    public Groupe getGroupe() {
        return this.groupe;
    }

    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }

    public Participant groupe(Groupe groupe) {
        this.setGroupe(groupe);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Participant)) {
            return false;
        }
        return id != null && id.equals(((Participant) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Participant{" +
            "id=" + getId() +
            ", prenom='" + getPrenom() + "'" +
            ", nom='" + getNom() + "'" +
            ", email='" + getEmail() + "'" +
            ", password='" + getPassword() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", dateDeNaissance='" + getDateDeNaissance() + "'" +
            ", sexe='" + getSexe() + "'" +
            ", cin='" + getCin() + "'" +
            "}";
    }
}
