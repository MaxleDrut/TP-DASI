/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import javax.persistence.Embeddable;

/**
 *
 * @author romai
 */
@Embeddable
public class ProfilAstral 
{
    private String signeZodiac;
    private String signeChinois;
    private String couleur;
    private String animalTotem;

    protected ProfilAstral() {}
    
    public ProfilAstral(String signeZodiac, String signeChinois, String couleur, String animalTotem) {
        this.signeZodiac = signeZodiac;
        this.signeChinois = signeChinois;
        this.couleur = couleur;
        this.animalTotem = animalTotem;
    }

    public String getSigneZodiac() {
        return signeZodiac;
    }

    public void setSigneZodiac(String signeZodiac) {
        this.signeZodiac = signeZodiac;
    }

    public String getSigneChinois() {
        return signeChinois;
    }

    public void setSigneChinois(String signeChinois) {
        this.signeChinois = signeChinois;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public String getAnimalTotem() {
        return animalTotem;
    }

    public void setAnimalTotem(String animalTotem) {
        this.animalTotem = animalTotem;
    }

    @Override
    public String toString() {
        return "ProfilAstral{" + "signeZodiac=" + signeZodiac + ", signeChinois=" + signeChinois + ", couleur=" + couleur + ", animalTotem=" + animalTotem + '}';
    }
}
