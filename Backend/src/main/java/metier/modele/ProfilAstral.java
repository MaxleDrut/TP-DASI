/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.awt.Color;

/**
 *
 * @author onyr
 */
public class ProfilAstral {
    String signeZodiac;
    String signeChinois;
    Color couleur;
    String animalTotem;
    
    ProfilAstral(String signeZodiac, String signeChinois, Color couleur, String AnimalTotem) {
        this.signeZodiac = signeZodiac;
        this.signeChinois = signeChinois;
        this.couleur = couleur;
        this.animalTotem = animalTotem;
    }
    
}
