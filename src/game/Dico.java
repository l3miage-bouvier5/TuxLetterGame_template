/**
 *
 * @author bouvier et delagrange
 */
package game;

import java.util.ArrayList;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


/* Classe pour gérer le dictionnaire */
public class Dico extends DefaultHandler{
    // Vont être utile pour le parser SAX
    private StringBuffer buffer; 
    private boolean inDictionnaire = false, inNiveau = false, inMot = false; 
    private int numero;

    
    
    private ArrayList<String> listeNiveau1;
    private ArrayList<String> listeNiveau2;
    private ArrayList<String> listeNiveau3;
    private ArrayList<String> listeNiveau4;
    private ArrayList<String> listeNiveau5;

    private String cheminFichierDico;

    public Dico(String cheminFichierDico) {
        super();                                // On appelle le constructeur de DefaultHandler
        listeNiveau1 = new ArrayList<String>();
        listeNiveau2 = new ArrayList<String>();
        listeNiveau3 = new ArrayList<String>();
        listeNiveau4 = new ArrayList<String>();
        listeNiveau5 = new ArrayList<String>();
        this.cheminFichierDico = cheminFichierDico;
    }

    
    /* Renvoie un mot aléatoire correspondant au niveau niveau */
    public String getMotDepuisListeNiveaux(int niveau) {
        String s = "";

        switch (verifieNiveau(niveau)) {
            case 1:
                s = getMotDepuisListe(listeNiveau1);
                break;
            case 2:
                s = getMotDepuisListe(listeNiveau2);
                break;
            case 3:
                s = getMotDepuisListe(listeNiveau3);
                break;
            case 4:
                s = getMotDepuisListe(listeNiveau4);
                break;
            case 5:
                s = getMotDepuisListe(listeNiveau5);
                break;
            default:
                break;
        }
        return s;
    }

    
    private int verifieNiveau(int niveau) {
        int res = niveau;

        if (niveau > 5) {
            res = 5;
        }
        if (niveau < 1) {
            res = 1;
        }
        return res;
    }

    
    /* Renvoie un mot aléatoire depuis la liste list*/
    private String getMotDepuisListe(ArrayList<String> list) {
        if (list.isEmpty()) {
            return "vide";
        } else {
            int nombreAleatoire = (int) (Math.random() * (list.size()));
            return list.get(nombreAleatoire);
        }

    }

    public void ajoutMotDico(int niveau, String mot) {
        switch (verifieNiveau(niveau)) {
            case 1:
                listeNiveau1.add(mot);
                break;
            case 2:
                listeNiveau2.add(mot);
                break;
            case 3:
                listeNiveau3.add(mot);
                break;
            case 4:
                listeNiveau4.add(mot);
                break;
            case 5:
                listeNiveau5.add(mot);
                break;
        }
    }
    
    
    /* Parsing DOM du dictionnaire */
    public void lireDictionnaireDOM() {
        try {
            
      
           
            
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
            FileInputStream in = new FileInputStream(new File(this.cheminFichierDico));
            Document doc = dbBuilder.parse(in, "UTF-8");
            

            NodeList nListeNiveau = doc.getElementsByTagName("niveau");
            int niveau = 1;
            for (int i = 0; i < nListeNiveau.getLength(); i++) {
                Element tempListeNiveau = (Element) nListeNiveau.item(i);
                NodeList nListeMot = tempListeNiveau.getElementsByTagName("mot");
                for (int j = 0; j < nListeMot.getLength(); j++) {
                    Node mot = nListeMot.item(j);
                    Element elt = (Element) mot;
                    this.ajoutMotDico(niveau, elt.getTextContent());
                }
                niveau++;
            }

        } catch (Exception e) {
            e.toString();
        }

    }
    
    
    /* Parsing SAX DU dictionnaire. On override toutes les fonctions qui nous seront necessaires : startElement, endElement, startDocument, 
    endDocument et characters
    */
    
        
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if("dictionnaire".equals(qName)){
            inDictionnaire = true;
        }
        else if ("niveau".equals(qName)){
            try{
                // On récupère le niveau
                numero = Integer.parseInt(attributes.getValue("numero")); 
            }catch(Exception e){
                throw new SAXException(e);
            }
            inNiveau = true;
        }else{
            // on récupere le mot
            buffer = new StringBuffer();
            if("mot".equals(qName)){
                inMot = true;
            }else{
                throw new SAXException("Balise "+qName+" inconnue.");
            }
        }
    }
    
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if("dictionnaire".equals(qName)){
            inDictionnaire = false;
        }else if("niveau".equals(qName)){
            inNiveau = false;
        }else if("mot".equals(qName)){
            ajoutMotDico(numero, buffer.toString());
            buffer = null;
            inMot = false;
        }else{
            throw new SAXException("Balise "+ qName + " inconnue");
        }
    }
        
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String lecture = new String(ch,start,length);
        if(buffer!=null) buffer.append(lecture);
    }
    
    @Override
    public void startDocument() throws SAXException {
        System.out.println("Début du parsing");
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println("Fin du parsing");
        System.out.println("Résultat du parsing");
        System.out.println("Liste mots niveau 1 : ");
    } 
    
    /*
    Initialise le parsing SAX.
    */
    public void lireDictionnaire() throws ParserConfigurationException, org.xml.sax.SAXException, IOException{
        SAXParserFactory fabrique = SAXParserFactory.newInstance(); 
        SAXParser parseur = fabrique.newSAXParser(); 
  
        File fichier = new File("src/data/xml/dico.xml");
        parseur.parse(fichier, this);
    }
    
    
    /*
    Affiche tous les mots du dictionnaire.
    */
    public void afficherDico(){
        System.out.println("Mot de la liste 1 : \n");
        for(int i = 0; i < listeNiveau1.size();i++){
            System.out.println("Mot "+(i+1)+" : " + listeNiveau1.get(i));
        }
        System.out.println("Mot de la liste 2 : \n");
        for(int i = 0; i < listeNiveau2.size();i++){
            System.out.println("Mot "+(i+1)+" : " + listeNiveau2.get(i));
        }
         System.out.println("Mot de la liste 3 : \n");
        for(int i = 0; i < listeNiveau3.size();i++){
            System.out.println("Mot "+(i+1)+" : " + listeNiveau3.get(i));
        }
         System.out.println("Mot de la liste 4 : \n");
        for(int i = 0; i < listeNiveau4.size();i++){
            System.out.println("Mot "+(i+1)+" : " + listeNiveau4.get(i));
        }
         System.out.println("Mot de la liste 5 : \n");
        for(int i = 0; i < listeNiveau5.size();i++){
            System.out.println("Mot "+(i+1)+" : " + listeNiveau5.get(i));
        }
    }
}
