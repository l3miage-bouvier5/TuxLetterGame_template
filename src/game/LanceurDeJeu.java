/**
 *
 * @author bouvier et delagrange
 */
package game;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;

/*
Main de la classe Jeu. On lance le jeu d'ici.
*/
public class LanceurDeJeu {
    public static void main(String args[]) throws ParserConfigurationException, TransformerException, SAXException, IOException {
        // Declare un Jeu
        Jeu jeu;
            //Instancie un nouveau jeu
        jeu = new JeuDevineLeMotOrdre();
       
        //Execute le jeu
        jeu.execute();
    }
}
