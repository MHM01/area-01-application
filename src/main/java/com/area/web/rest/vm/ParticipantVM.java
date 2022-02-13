package com.area.web.rest.vm;

import com.area.domain.enumeration.Sexe;
import com.area.domain.enumeration.Type;
import com.area.validators.BirthDate;
import java.util.Date;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ParticipantVM {

    public static final int PASSWORD_MIN_LENGTH = 4;

    public static final int PASSWORD_MAX_LENGTH = 100;

    private Long id;

    @Pattern(regexp = "[\\d]{8}")
    @NotBlank
    private String cin;

    /**
     * The firstname attribute.
     */
    @Size(max = 50)
    private String prenom;

    @Size(max = 50)
    private String nom;

    @Email
    @Size(min = 5, max = 254)
    private String email;

    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;

    @Pattern(regexp = "[\\d]{8}")
    @NotBlank
    private String telephone;

    @BirthDate
    private Date dateDeNaissance;

    @Enumerated(EnumType.STRING)
    private Sexe sexe;

    @NotBlank
    private String nomFaculte;

    private Type typeDeparticipation;

    private String participantDuGroupe;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Date getDateDeNaissance() {
        return dateDeNaissance;
    }

    public void setDateDeNaissance(Date dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    public Sexe getSexe() {
        return sexe;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public String getNomFaculte() {
        return nomFaculte;
    }

    public void setNomFaculte(String nomFaculte) {
        this.nomFaculte = nomFaculte;
    }

    public String getParticipantOfGroupe() {
        return participantDuGroupe;
    }

    public void setParticipantOfGroupe(String participantDuGroupe) {
        this.participantDuGroupe = participantDuGroupe;
    }

    public Type getTypeDeparticipation() {
        return typeDeparticipation;
    }

    public void setTypeDeparticipation(Type typeDeparticipation) {
        this.typeDeparticipation = typeDeparticipation;
    }
}
