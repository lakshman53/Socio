package tds.socio;

import android.util.Log;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
/**
 * Created by laks on 12-03-2015.
 */
public class addOffersByParseXML {


    addOffersByParseXML() {

    }

        public Integer addOffers(String xmlRecords) throws Exception{

            Integer nodeLength = 0;

            if (!xmlRecords.isEmpty()) {
                DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(xmlRecords));

                Document doc = db.parse(is);
                NodeList nodes = doc.getElementsByTagName("Offers");
                nodeLength = nodes.getLength();

                for (int i = 0; i < nodeLength; i++) {
                    Element element = (Element) nodes.item(i);

                    NodeList name = element.getElementsByTagName("Subject");
                    Element line = (Element) name.item(0);
                    String Subject = getCharacterDataFromElement(line);

                    NodeList title = element.getElementsByTagName("SentAt");
                    line = (Element) title.item(0);
                    String SentAt = getCharacterDataFromElement(line);
                    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy h:mm:ss aaa");

                    NodeList messages = element.getElementsByTagName("message");
                    line = (Element) messages.item(0);
                    String message = getCharacterDataFromElement(line);

                    Random randomGenerator = new Random();
                    Offers offers;

                    offers = new Offers(Integer.toString(randomGenerator.nextInt(1000)), R.drawable.ic_launcher, "Lakshman", Subject, message, formatter.parse(SentAt), false, false);
                    offers.save();
                }

                if (nodeLength > 0) {
                    Element element = (Element) nodes.item(0);

                    NodeList name = element.getElementsByTagName("OfferId");
                    Element line = (Element) name.item(0);
                    String LastOfferId = getCharacterDataFromElement(line);

                    Log.d("LastOfferId", LastOfferId.toString());

                    Employee emp = Employee.findById(Employee.class, 1L);
                    emp.setLastOrderId(Integer.valueOf(LastOfferId));
                    emp.save();
                }
            }
            return nodeLength;
        }

        public static String getCharacterDataFromElement(Element e) {
            Node child = e.getFirstChild();
            if (child instanceof CharacterData) {
                CharacterData cd = (CharacterData) child;
                return cd.getData();
            }
            return "";
        }
    }

