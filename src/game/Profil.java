/**
 *
 * @author bouvier et delagrange
 */
package game;

import game.XMLUtil.DocumentTransform;
import java.awt.Desktop;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


/*
Classe qui représente un profil d'un  joueur
*/
public class Profil {

    private String nom;
    private String avatar;
    private String dateNaissance;
    private ArrayList<Partie> parties;
    public Document _doc;

    
    // Initialise un profil par défaut.
    // En théorie ce constructeur n'est jamais appelé.
    public Profil() {
        this.nom = "Ragnar";
        this.avatar = "src/data/images/voldemort.png";
        this.dateNaissance = "24/08/2001";
    }
    
    
    // Constructeur pour un nouveau profil
    public Profil(String nomJoueur, String dateNaissance) throws ParserConfigurationException {
        this.nom = nomJoueur;
        this.dateNaissance = dateNaissance;
        parties = new ArrayList<>();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        _doc = db.newDocument();
    }

    // Constructeur pour un profil déjà existant à partir du fichier nomFichier
    Profil(String nomFichier) throws ParserConfigurationException {
        _doc = fromXML(nomFichier);
        

        //  test si le document existe ou pas
            if(_doc != null){
            
            // Récupère les infos du profil : nom, avatar et date de naissance.
            Element profil = (Element) _doc.getElementsByTagName("profil").item(0);
            this.nom = profil.getElementsByTagName("nom").item(0).getTextContent();
            this.avatar = profil.getElementsByTagName("avatar").item(0).getTextContent();
            this.dateNaissance = xmlDateToProfileDate(profil.getElementsByTagName("anniversaire").item(0).getTextContent());

            
            // Récupère les parties déjà jouées du profil
            parties = new ArrayList<>();
            NodeList nParties = _doc.getElementsByTagName("partie");
            for (int i = 0; i < nParties.getLength(); i++) {
                Element nPartie = (Element) nParties.item(i);
                Partie p = new Partie(nPartie);
                parties.add(p);
            }
            // Si le document n'existe pas, on créer un profil aléatoire et on commence à jouer avec
        }else{
                this.nom = "aleatoire";
                this.avatar = "src/data/images/voldemort.png";
                this.dateNaissance="24/08/2001";
                parties = new ArrayList<>();
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                _doc = db.newDocument();
            }
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setDate(String date) {
        this.dateNaissance = date;
    }

    public void setParties(ArrayList<Partie> parties) {
        this.parties = parties;
    }

    public String getNom() {
        return nom;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getDate() {
        return dateNaissance;
    }

    public ArrayList<Partie> getParties() {
        return parties;
    }

    public void ajoutePartie(Partie partie) {
        parties.add(partie);
    }

    
    // renvoie si nomJoueur est egale au nom du profil
    public boolean charge(String nomJoueur) {
        return (nomJoueur.equals(this.nom));
    }

    // Renvoie le document tiré du fichier nomFichier
    private Document fromXML(String nomFichier) {
        try {
            return XMLUtil.DocumentFactory.fromFile(nomFichier);
        } catch (Exception ex) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /// Takes a date in XML format (i.e. ????-??-??) and returns a date
    /// in profile format: dd/mm/yyyy
    private static String xmlDateToProfileDate(String xmlDate) {
        String date;
        // récupérer le jour
        date = xmlDate.substring(xmlDate.lastIndexOf("-") + 1, xmlDate.length());
        date += "/";
        // récupérer le mois
        date += xmlDate.substring(xmlDate.indexOf("-") + 1, xmlDate.lastIndexOf("-"));
        date += "/";
        // récupérer l'année
        date += xmlDate.substring(0, xmlDate.indexOf("-"));

        return date;
    }
    /// Takes a date in profile format: dd/mm/yyyy and returns a date
    /// in XML format (i.e. ????-??-??)

    private static String profileDateToXmlDate(String profileDate) {
        String date;
        // Récupérer l'année
        date = profileDate.substring(profileDate.lastIndexOf("/") + 1, profileDate.length());
        date += "-";
        // Récupérer  le mois
        date += profileDate.substring(profileDate.indexOf("/") + 1, profileDate.lastIndexOf("/"));
        date += "-";
        // Récupérer le jour
        date += profileDate.substring(0, profileDate.indexOf("/"));

        return date;
    }
    
    
    // Sauvegarde un DOM en XML
    private void toXML(String nomFichier) {
        try {
            XMLUtil.DocumentTransform.writeDoc(_doc, nomFichier);
        } catch (Exception ex) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    // Pdermet de sauvegarder le profil dans le fichier nomFichier
    public void sauvegarder(String nomFichier) throws TransformerException {
        
        // Si il existe pas on le créer
        if (fromXML(nomFichier) == null) {
            Element root = _doc.createElement("profil");
            _doc.setXmlVersion("1.0");
           
            root.setAttribute("xmlns", "http://myGame/tux");
            root.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            root.setAttribute("xsi:schemaLocation", "http://myGame/tux ../xsd/profil.xsd");

            Element nNom = _doc.createElement("nom");
            nNom.setTextContent(nom);

            root.appendChild(nNom);

            Element nAvatar = _doc.createElement("avatar");
            nAvatar.setTextContent(avatar);

            root.appendChild(nAvatar);

            Element nAnniversaire = _doc.createElement("anniversaire");
            nAnniversaire.setTextContent(profileDateToXmlDate(dateNaissance));

            root.appendChild(nAnniversaire);

            Element nParties = _doc.createElement("parties");

            for (Partie p : parties) {
                Element nPartie = p.getPartie(_doc);
                nParties.appendChild(nPartie);
            }
            root.appendChild(nParties);
            _doc.appendChild(root);
            
            toXML(nomFichier);
         
            
        // Si il existe on ajoute juste la dernière partie au profil
        }else{
            _doc = fromXML(nomFichier);
            Element parties = (Element) _doc.getElementsByTagName("parties").item(0);
            
            Partie p = this.parties.get(this.parties.size()-1);
            Element partie = p.getPartie(_doc);
            
                  
            parties.appendChild(partie);
            
            toXML(nomFichier);
        }

    }

    @Override
    public String toString() {
        String res = "Bonjour  : " + nom + "\n";

        res += "Voici les parties jouées de ce profil : ";

        for (Partie partie : parties) {
            res += "\n" + partie.toString();
        }
        return res;
    }
   
    // Renvoie la première partie non terminée du joueur et la supprime
    // Elle sera remplacée par la nouvelle version de la partie
    public Partie chargePartie() {
        
        int i = 0;
        while(i < parties.size() && parties.get(i).getTrouvé() == 100){
            i++;
        }
        if(i < parties.size()){
            supprimerPartie(parties.get(i).getMot());
            return parties.get(i);
            
        }else{
            return null;
        }
        
    }
    
    // Supprime des parties celles qui correspondent au mot mot
    private void supprimerPartie(String mot) {
        Element parties = (Element)_doc.getElementsByTagName("parties").item(0);
        NodeList nParties = _doc.getElementsByTagName("partie");
        for(int j = 0; j < nParties.getLength(); j++){
            System.out.println(nParties.item(j).getTextContent());
        }
        for(int i = 0; i < nParties.getLength(); i++){
            Element ePartie = (Element) nParties.item(i);
            if(mot.equals(ePartie.getElementsByTagName("mot").item(0).getTextContent())){
                parties.removeChild(ePartie);
            }
        }
        // On met à jour le fichier après avoir supprimer les parties qu'il faut
        toXML("src/data/xml/profil-"+this.getNom()+".xml");
    }
    
    // Affiche le profil dans une page html en lui appliquant le xslt correspondant au profil
    public void afficheProfil(String xslStreamSource){
        Document docTemp = fromXML("src/data/xml/profil-"+this.getNom()+".xml");
        String res = "";
        try {   
            res = DocumentTransform.fromXSLTransformation(xslStreamSource, docTemp);
            FileUtil.stringToFile(res, "src/data/html/profil-"+this.nom+".html");
            File htmlFile = new File("src/data/html/profil-"+this.nom+".html");
            Desktop.getDesktop().browse(htmlFile.toURI());
        }catch(Exception e){
            e.toString();
        }
    }


}
