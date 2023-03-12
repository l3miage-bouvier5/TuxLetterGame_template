/**
 *
 * @author bouvier et delagrange
 */
package game;

import env3d.Env;
import env3d.advanced.EnvNode;
import org.lwjgl.input.Keyboard;



/*
Cette classe représente un petit tux
*/
public class Tux extends EnvNode {
    private Env env;
    private Room room;
    
    // Instancie un tuc dans un environnement env et dans une room room
    public Tux(Env env, Room room){
        this.env = env;
        this.room = room;
        
        setScale(5.0); // Taille de Tux
        setX( room.getWidth()/2 );// positionnement au milieu de la largeur de la room
        setY(getScale() * 1.1); // positionnement en hauteur basé sur la taille de Tux
        setZ( room.getDepth()/2 ); // positionnement au milieu de la profondeur de la room
        setTexture("models/tux/tux_special.png");
        setModel("models/tux/tux.obj"); 
    }
    
    
    // Gère les déplacements en fonctions des touches pressées
    public void déplace(){
        if (env.getKeyDown(Keyboard.KEY_Z) || env.getKeyDown(Keyboard.KEY_UP)) { // Fleche 'haut' ou Z
       // Haut
            if(!testeRoomCollision(this.getX(), this.getZ()-1.0)){
                this.setRotateY(180);
                this.setZ(this.getZ() - 1.0);
            }
   
       }
        if (env.getKeyDown(Keyboard.KEY_Q) || env.getKeyDown(Keyboard.KEY_LEFT)) { // Fleche 'gauche' ou Q
            // Gauche
            if(!testeRoomCollision(this.getX()-1.0, this.getZ())){
                this.setRotateY(270);
                this.setX(this.getX() - 1.0);
            }
        }
        if (env.getKeyDown(Keyboard.KEY_S) || env.getKeyDown(Keyboard.KEY_DOWN)) { // Fleche 'bas' ou S
            // Bas
            if(!testeRoomCollision(this.getX(), this.getZ()+1.0)){
                this.setRotateY(0);
                this.setZ(this.getZ() + 1.0);
            }
        }
        if (env.getKeyDown(Keyboard.KEY_D) || env.getKeyDown(Keyboard.KEY_RIGHT)) { // Fleche 'droite' ou D
            // Droite
            if(!testeRoomCollision(this.getX()+1.0, this.getZ())){
                this.setRotateY(90);
                this.setX(this.getX() + 1.0);
            }
        }
    }
    
    
    // Renvoie true si Tux se "prend" un mur de la room
    public boolean testeRoomCollision(double x, double z){
        boolean collision = false;
        
        if((z == room.getDepth() - this.getScale()) || (z == 0 + this.getScale()) || (x == room.getWidth() - this.getScale()) || (x == 0 + this.getScale()) ){
            collision = true;
        }
        
        return collision;
    }
    
    
    
  
}
