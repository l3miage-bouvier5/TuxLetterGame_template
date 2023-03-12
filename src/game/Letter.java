/**
 *
 * @author bouvier et delagrange
 */
package game;

import env3d.advanced.EnvNode;

/*
Classe qui permet de gérer une lettre. Avec sa "valeur" et ses coordonnées.
Permet aussi d'associer une texture a chaque lettre.
*/
public class Letter extends EnvNode {

    private char letter;
    

    public Letter(char letter, double x, double y) {
        
        this.letter = letter;
        setScale(5.0);
        setX(x);// positionnement au milieu de la largeur de la room
        setY(getScale() * 1.1); // positionnement en hauteur basé sur la taille de Tux
        setZ(y); // positionnement au milieu de la profondeur de la room

        if (this.letter == ' ') {
            setTexture("models/letter/cube.png");
        } else {
            setTexture("models/letter/" + this.letter + ".png");
        }
        setModel("models/letter/cube.obj");
        
    }

    
    
}
