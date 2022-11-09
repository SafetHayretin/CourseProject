package CourseProject;

import CourseProject.Configuration.Configuration;
import CourseProject.Configuration.Mapper;
import CourseProject.Mappers.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class XmlParserMapper {
    private final Configuration config;

    private Mapper mapper;

    public XmlParserMapper(Configuration configuration) {
        this.config = configuration;
    }


    public void getMapper(String resource, int index) throws ParserConfigurationException, IOException, SAXException {
        File xmlFile = new File(resource);
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = docFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);
        Element root = doc.getDocumentElement();
        if (!root.getNodeName().equals("mapper"))
            throw new ParserConfigurationException("Missing mapper element");
        mapper = config.mappers.getMapper(index);
        mapper.nameSpace = root.getNodeName();

        createMapper(root);
    }

    private void createMapper(Element root) {
        NodeList list = root.getChildNodes();

        for (int i = 0; i < list.getLength(); i++) {
            Node child = list.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                String name = child.getNodeName();
                switch (name) {
                    case "resultMap" -> {
                        String id = child.getAttributes().item(0).getNodeValue();
                        String type = child.getAttributes().item(1).getNodeValue();
                        mapper.resultMap = createResultMap(child, id, type);
                    }
                    case "insert" -> {
                        String id = child.getAttributes().item(0).getNodeValue();
                        String parameterType = child.getAttributes().item(1).getNodeValue();
                        String query = child.getTextContent();
                        mapper.insert = new Insert(id, parameterType, query);
                    }
                    case "delete" -> {
                        String id = child.getAttributes().item(0).getNodeValue();
                        String parameterType = child.getAttributes().item(1).getNodeValue();
                        String query = child.getTextContent();
                        mapper.delete = new Delete(id, parameterType, query);
                    }
                    case "select" -> {
                        String id = child.getAttributes().item(0).getNodeValue();
                        String parameterType = child.getAttributes().item(1).getNodeValue();
                        String query = child.getTextContent();
                        mapper.select = new Select(id, parameterType, query);
                    }
                    case "update" -> {
                        String id = child.getAttributes().item(0).getNodeValue();
                        String parameterType = child.getAttributes().item(1).getNodeValue();
                        String query = child.getTextContent();
                        mapper.update = new Update(id, parameterType, query);
                    }
                }
            }
        }
    }

    private ResultMap createResultMap(Node root, String id, String type) {
        ResultMap map = new ResultMap(id, type);
        NodeList list = root.getChildNodes();

        for (int i = 0; i < list.getLength(); i++) {
            Node child = list.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                String name = child.getNodeName();
                if (name.equals("result")) {
                    String property = child.getAttributes().item(0).getNodeValue();
                    String column = child.getAttributes().item(1).getNodeValue();
                    map.add(property, column);
                }
            }
        }

        return map;
    }
}
