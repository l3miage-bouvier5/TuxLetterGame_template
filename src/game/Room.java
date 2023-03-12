/**
 *
 * @author bouvier et delagrange
 */
package game;

import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/*
Cette classe représente un terrain de jeu.
*/
public class Room {
    private double depth;
    private double height;
    private double width;
    private String textureBottom;
    private String textureNorth;
    private String textureEast;
    private String textureWest;
    private String textureSouth;
    private String textureTop;
    
    
    // Instancie une room avec les infos de plateau.xml en le parsant avec DOM
    public Room() throws SAXException, IOException, ParserConfigurationException{
        DocumentBuilderFactory dbF = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbF.newDocumentBuilder();
        Document doc = db.parse("src/data/xml/plateau.xml");
        
        Element dimension = (Element) doc.getElementsByTagName("ns1:dimensions").item(0);
        depth =Double.parseDouble(dimension.getElementsByTagName("ns1:depth").item(0).getTextContent());
        height = Double.parseDouble(dimension.getElementsByTagName("ns1:height").item(0).getTextContent());
        width = Double.parseDouble(dimension.getElementsByTagName("ns1:width").item(0).getTextContent());
        
        
        Element mapping = (Element) doc.getElementsByTagName("ns1:mapping").item(0);
        
        textureBottom = mapping.getElementsByTagName("ns1:textureBottom").item(0).getTextContent();
        textureEast = mapping.getElementsByTagName("ns1:textureEast").item(0).getTextContent();
        textureNorth = mapping.getElementsByTagName("ns1:textureNorth").item(0).getTextContent();
        textureWest = mapping.getElementsByTagName("ns1:textureWest").item(0).getTextContent();
    }

    public double getDepth() {
        return this.depth;
    }

    public double getHeight() {
        return this.height;
    }

    public double getWidth() {
        return this.width;
    }

    public String getTextureBottom() {
        return textureBottom;
    }

    public String getTextureNorth() {
        return textureNorth;
    }

    public String getTextureEast() {
        return textureEast;
    }

    public String getTextureWest() {
        return textureWest;
    }

    public String getTextureSouth() {
        return textureSouth;
    }

    public String getTextureTop() {
        return textureTop;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setTextureBottom(String textureBottom) {
        this.textureBottom = textureBottom;
    }

    public void setTextureNorth(String textureNorth) {
        this.textureNorth = textureNorth;
    }

    public void setTextureEast(String textureEast) {
        this.textureEast = textureEast;
    }

    public void setTextureWest(String textureWest) {
        this.textureWest = textureWest;
    }

    public void setTextureSouth(String textureSouth) {
        this.textureSouth = textureSouth;
    }

    public void setTextureTop(String textureTop) {
        this.textureTop = textureTop;
    }
    
}
