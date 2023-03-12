/**
 *
 * @author bouvier et delagrange
 */
package game;

import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/*
Classe qui réprésente une partie.
*/
public class Partie {
    private String date;
    private String mot;
    private int niveau;
    private int trouvé;
    private int temps;
    
    // Constructeur pour un nouveau joueur
    public Partie(String date, String mot, int niveau){
        this.date = date;
        this.niveau = niveau;
        this.mot = mot;
    }
    
    
    // Constructeur pour une partie déjà existante
    public Partie(Element partieElt){
        this.date = partieElt.getAttribute("date");
        
        this.temps = Integer.parseInt(partieElt.getElementsByTagName("temps").item(0).getTextContent());
        
        this.niveau = Integer.parseInt(partieElt.getElementsByTagName("niveau").item(0).getAttributes().item(0).getTextContent());
        
        this.mot = partieElt.getElementsByTagName("mot").item(0).getTextContent();
        
        if(partieElt.hasAttribute("trouvé")){
            this.trouvé =Integer.parseInt(partieElt.getAttribute("trouvé"));
        }else{
            this.trouvé = 100;
        }
        
    }
    
    public String getDate(){
        return this.date;
    }
    
    public int getTrouvé(){
        return trouvé;
    }
    
    public String getMot(){
        return mot;
    }
    
    public void setTrouve(int nbLettresRestantes){
        trouvé = (int)((mot.length()-nbLettresRestantes)/mot.length())*100;
    }
    public void setTemps(int temps){
        this.temps = temps;
    }
    
    public int getNiveau(){
        return niveau;
    }
    
    public void setNiveau(int niveau){
        this.niveau = niveau;
    }
    
    
    public int getTemps(){
        return this.temps;
    }
    
    @Override
    public String toString(){
        String res = "";
        res += "Partie terminée :\n";
        
        if(temps == 0){
            res += "Malheurement vous avez mit plus de 60 secondes à trouver le mot";
            res += "\n Vous avez trouvé "+trouvé+"% du mot.\n votre partie est sauvegardée et vous pourrez réessayer le meme mot dans une prochaine partie";
        }else{
            res += "Vous avez trouvé "+ trouvé +"% du mot : " + mot;
            res += " en "+ temps + " secondes";
        }
        return res;
    }
    
    
    // Renvoie un Element partie, à partir du Document doc, qui représente la partie jouée.
    public Element getPartie(Document doc){
        Element res = doc.createElement("partie");
        res.setAttribute("date", ""+LocalDate.now());
      
        if(trouvé != 100){
            res.setAttribute("trouvé", ""+trouvé);
        }
        
        Element temps = doc.createElement("temps");
        temps.setTextContent(""+this.temps);
        
        Element niveau = doc.createElement("niveau");
        niveau.setAttribute("numero", ""+this.niveau);
        
        Element mot = doc.createElement("mot");
        mot.setTextContent(this.mot);
        
        niveau.appendChild(mot);
        res.appendChild(temps);
        res.appendChild(niveau);
        
        return res;
        
    }
    
    
    // Calcule le score en fonction du temps passé à trouver et du niveau du mot trouvé
    private int score(){
        int res = 0;
        switch(niveau){
            case 1:
                res =60 -temps;
                break;
            case 2 : 
                res = (60-temps)*2;
                break;
            case 3: 
                res =(60- temps)*4;
                break;
            case 4: 
                res = (60-temps)*5;
                break;
            case 5 :
                res = (60-temps)*7;
                break;      
        }
        if(60 - temps < 0){
            res = 0;
        }
        return res;
    }
    
    // Permet de créer un fichier xml à partir du Document doc
    private void toXML(String nomFichier, Document doc) {
        try {
            XMLUtil.DocumentTransform.writeDoc(doc, nomFichier);
        } catch (Exception ex) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // renvoie le doc tiré du fichier nomFichier
    private Document fromXML(String nomFichier) {
        try {
            return XMLUtil.DocumentFactory.fromFile(nomFichier);
        } catch (Exception ex) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    // Fonction qui permet d'enregistrer la partie dans le tableau des meilleurs scores
    // si elle fait partie des 10 meilleures parties (ça fait beaucoup de "partie")
    // Le tri pour l'affichage est géré par le XSLT. 
    public void enregistrerHiscores(Profil profil){
        if(this.trouvé == 100){
            try {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document _doc = db.newDocument();
                // Si le document n'existe pas on le créer et on met la partie jouée dedans 
                //(étant donné que c'est la première elle est forcément dans les meilleures)
                if(fromXML("src/data/xml/hiscores.xml")==null){


                        Element root = _doc.createElement("hiscores");
                        _doc.setXmlVersion("1.0");

                        root.setAttribute("xmlns", "http://myGame/tux");
                        root.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
                        root.setAttribute("xsi:schemaLocation", "http://myGame/tux ../xsd/hiscores.xsd");



                        Element nNom = _doc.createElement("nom");
                        nNom.setTextContent(profil.getNom());

                        Element nAvatar = _doc.createElement("avatar");
                        nAvatar.setTextContent(profil.getAvatar());

                        Element nMot = _doc.createElement("mot");
                        nMot.setTextContent(this.getMot());

                        Element nPartie = _doc.createElement("partie");
                        nPartie.setAttribute("score", ""+this.score());
                        nPartie.setAttribute("date", this.getDate());

                        nPartie.appendChild(nNom);
                        nPartie.appendChild(nAvatar);
                        nPartie.appendChild(nMot);

                        root.appendChild(nPartie);

                        _doc.appendChild(root);

                        toXML("src/data/xml/hiscores.xml", _doc);
                        
                // Si le document existe on le récupère
                }else{
                    _doc = fromXML("src/data/xml/hiscores.xml");

                    NodeList parties = _doc.getElementsByTagName("partie");
                    
                    
                    // Si il y a moins de 10 parties dedans, on ajoute la partie jouée.
                    if(parties.getLength()<10){
                        Element root =(Element) _doc.getElementsByTagName("hiscores").item(0);

                        Element nPartie = _doc.createElement("partie");
                        nPartie.setAttribute("score", ""+score());
                        nPartie.setAttribute("date", this.getDate());



                        Element nNom = _doc.createElement("nom");
                        nNom.setTextContent(profil.getNom());

                        Element nMot = _doc.createElement("mot");
                        nMot.setTextContent(this.getMot());

                        Element nAvatar = _doc.createElement("avatar");
                        nAvatar.setTextContent(profil.getAvatar());

                        nPartie.appendChild(nNom);
                        nPartie.appendChild(nMot);
                        nPartie.appendChild(nAvatar);


                        root.appendChild(nPartie);
                        // Si il y plus de 10 parties, on cherche la partie avec le score le plus bas
                        // on compare ce score avec celui de la partie jouée.
                        // Si la partie jouée a un score plus élevé, on l'ajoute en enlevant la partie qui avait le plus bas score
                        // sinon, on ne fait rien
                    }else{
                        int indexMin = 0;
                        Element partie0 = (Element) parties.item(0);
                        int min =Integer.parseInt(partie0.getAttribute("score"));
                        for(int i = 0; i < parties.getLength(); i++){
                            Element ePartie = (Element) parties.item(i);
                            if(min > Integer.parseInt(ePartie.getAttribute("score"))){
                                min = Integer.parseInt(ePartie.getAttribute("score"));
                                indexMin = i;
                            }
                        }
                        if(this.score() > min){
                            Element partieAEnlever = (Element) parties.item(indexMin);
                            Element root =(Element) _doc.getElementsByTagName("hiscores").item(0);

                            Element nPartie = _doc.createElement("partie");
                            nPartie.setAttribute("score", ""+score());
                            nPartie.setAttribute("date", this.getDate());



                            Element nNom = _doc.createElement("nom");
                            nNom.setTextContent(profil.getNom());

                            Element nMot = _doc.createElement("mot");
                            nMot.setTextContent(this.getMot());

                            Element nAvatar = _doc.createElement("avatar");
                            nAvatar.setTextContent(profil.getAvatar());

                            nPartie.appendChild(nNom);
                            nPartie.appendChild(nMot);
                            nPartie.appendChild(nAvatar);


                            root.appendChild(nPartie);
                            root.removeChild(partieAEnlever);
                        }
                    }



                    toXML("src/data/xml/hiscores.xml", _doc);

                }
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(Partie.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
