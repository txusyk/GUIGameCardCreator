
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;



/**
 *
 * @author Josu
 */
public class TextFileManager {

    private static TextFileManager myTextFileManager;

    public static TextFileManager getFileManager() {
        if (myTextFileManager == null) {
            myTextFileManager = new TextFileManager();
        }
        return myTextFileManager;
    }

    public void readCardFiles() {
        try {
            /*JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            int result = fileChooser.showOpenDialog(null);
            URL url = null;
            File selectedFile = null;
            if (result == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
                System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                url = getClass().getResource(selectedFile.getPath().replaceAll("%20"," "));
            }*/

            URL url = getClass().getResource("cardList.xml");
            File auxXML = new File(url.getPath().replaceAll("%20", " "));

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
            Document doc = dbBuilder.parse(auxXML);

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("card");

            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element auxElement = (Element) nNode;

                    String auxHardness = auxElement.getElementsByTagName("hardness").item(0).getTextContent();
                    String auxSex_dev = auxElement.getElementsByTagName("sexual_dev").item(0).getTextContent();
                    ArrayList<String> auxOptions = new ArrayList<>();
                    for (int j = 0; j < auxElement.getElementsByTagName("options").getLength(); j++) {
                        auxOptions.add(auxElement.getElementsByTagName("options").item(j).getTextContent());
                    }
                    String auxText = auxElement.getElementsByTagName("text").item(0).getTextContent();

                    Card card = new Card(auxHardness, auxSex_dev, auxOptions, auxText);

                    CardList.getMyCardList().addCard(card);

                    card.showOptions();
                }
            }
        } catch (Exception e) {
            System.out.println("No file found");
        }
    }
    
   public void writeXMLFile() {
        DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder icBuilder;
        
        DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy");
        Date date = new Date();

        try {
            icBuilder = icFactory.newDocumentBuilder();
            Document doc = icBuilder.newDocument();
            Element mainRootElement= doc.createElement("cardList") ;//= doc.createElementNS("cardOutput.xml","cardList");
            doc.appendChild(mainRootElement);
 
            Card auxC; Iterator<Card> itr = CardList.getMyCardList().getIterator();
            while (itr.hasNext()){
                auxC = itr.next();
                // append child elements to root element
                mainRootElement.appendChild(getCard(doc, auxC.getHardness(), auxC.getSex_dev(), auxC.getOptions(), auxC.getText()));
            }
         
            // output DOM XML to console 
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
            DOMSource source = new DOMSource(doc);
            StreamResult console = new StreamResult(System.out);
            transformer.transform(source, console);

            String directorio = System.getProperty("user.dir");//cogemos variable entorno
            String directorio2 = System.getProperty("user.home");

            Path path = Paths.get(directorio+"/"+dateFormat.format(date));
            Files.createDirectories(path);

            File fichero1 = new File("/Users/Josu/IdeaProjects/GenTarjetas/src/cardList.xml");
            File fichero2 = new File(directorio+"/cardList.xml");
            File fichero3 = new File(directorio2+"/cardList.xml");

            writeDocumentToFile(fichero1,source);
            writeDocumentToFile(fichero2,source);
            writeDocumentToFile(fichero3,source);

            JOptionPane.showMessageDialog(null,"XML DOM Created Succesfully..,","INFORMATION_MESSAGES", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("\nXML DOM Created Successfully..");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void writeDocumentToFile(File file, DOMSource source) throws TransformerException {

        try {
            Transformer tr = TransformerFactory.newInstance().newTransformer();
            tr.setOutputProperty(OutputKeys.INDENT, "yes");
            tr.setOutputProperty(OutputKeys.METHOD, "xml");
            tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            //tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "roles.dtd");
            tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            // send DOM to file
            tr.transform(source,new StreamResult(new FileOutputStream(file)));

        } catch (TransformerException te) {
            System.out.println(te.getMessage());
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    
    private static Node getCard(Document doc,String pHardness, String pSexual_dev, ArrayList<String> pOptions,String pText){
        Element card = doc.createElement("card");
        card.appendChild(getCardElements(doc, card, "hardness",pHardness));
        card.appendChild(getCardElements(doc, card, "sexual_dev",pSexual_dev));
        
        Iterator<String> itr = pOptions.iterator();
        while (itr.hasNext()){
            String auxOption = itr.next();
            card.appendChild(getCardElements(doc, card, "options",auxOption));
        }
        card.appendChild(getCardElements(doc, card, "text",pText));
        return card;
    }
    
    // utility method to create text node
    private static Node getCardElements(Document doc, Element element, String name, String value){
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }
}
