/**
 *
 * @author bouvier et delagrange
 */
package game;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/*
Classe permettant de définir les règles de Jeu
*/
public class JeuDevineLeMotOrdre extends Jeu {

    Chronometre chrono;

    public JeuDevineLeMotOrdre() throws SAXException, IOException, ParserConfigurationException {
        super();   // Appelle le constructeur de Jeu
    }

    @Override
    
    // Lance le chrono et initialise le nombre de lettres
    protected void démarrePartie(Partie partie) {
        nbLettresRestantes = lettres.size();
        chrono = new Chronometre(60);   
        chrono.start();
        }
    

    
    // Applique les règles de jeu
    @Override
    protected boolean appliqueRegles(Partie partie) {
        boolean res = true;
        if(chrono.remainsTime()){
            if (tuxTrouveLettre()) {
                if (nbLettresRestantes == 0) {
                    chrono.stop();
                }
            }
        }else{
            partie.setTrouve((partie.getMot().length() - nbLettresRestantes/partie.getMot().length()) * 100);
            res = false;
            
        }
        return res;
    }
    // Termine la partie, mets le temps de partie et le nombre de lettres trouvées
    @Override
    protected void terminePartie(Partie partie) {
        if (!chrono.remainsTime()) {
            partie.setTemps(0);
        } else {
            partie.setTemps(chrono.getSeconds());
        }
        partie.setTrouve(nbLettresRestantes);
        lettres.clear();
    }

    
    // Renvoie true si tux entre en collision avec la bonne lettre
    private boolean tuxTrouveLettre() {
        boolean res = false;
        if (nbLettresRestantes == 0) {
            res = true;
        } else {

            if (collision(lettres.get(lettres.size() - nbLettresRestantes))) {
                env.removeObject(lettres.get(lettres.size() - nbLettresRestantes));
                res = true;
                nbLettresRestantes--;
            }
        }

        return res;
    }

}
