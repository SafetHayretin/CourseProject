package Homework53;


import CourseProject.Builders.*;
import CourseProject.Configuration.*;
import CourseProject.Model.Employee;
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
import java.util.Properties;

public class Homework53 {
    public static void main(String[] args) throws IOException {
        String resource = "src/main/resources/mybatis-config.xml";

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resource);
        SqlSession session = sqlSessionFactory.openSession();

        Employee emp = session.select("selectById", 5);
        System.out.println(emp);
    }

    public static class XmlParserConfig {
        private final String resource;

        public XmlParserConfig(String resource) {
            this.resource = resource;
        }

        public Configuration getConfiguration() throws ParserConfigurationException, SAXException, IOException {
            File xmlFile = new File(resource);
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = docFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            Element root = doc.getDocumentElement();
            if (!root.getNodeName().equals("configuration"))
                throw new ParserConfigurationException("Missing configuration element");

            return createConfig(root);
        }

        private Configuration createConfig(Element root) throws ParserConfigurationException {
            Configuration configuration = new Configuration();
            NodeList list = root.getChildNodes();

            for (int i = 0; i < list.getLength(); i++) {
                Node child = list.item(i);
                String name = child.getNodeName();
                switch (name) {
                    case "environments" -> configuration.environments = createEnvironments(child);
                    case "typeAliases" -> configuration.aliases = createAliases(child);
                    case "mappers" -> configuration.mappers = createMappers(child);
                }
            }

            return configuration;
        }

        private Mappers createMappers(Node root) throws ParserConfigurationException {
            Mappers mappers = new Mappers();
            NodeList list = root.getChildNodes();

            for (int i = 0; i < list.getLength(); i++) {
                Node child = list.item(i);
                if (child.getNodeType() == Node.ELEMENT_NODE) {
                    String name = child.getNodeName();
                    if (!name.equals("mapper"))
                        throw new ParserConfigurationException("Wrong child name in" + name);
                    String resource = child.getAttributes().item(0).getNodeValue();
                    Mapper mapper = new Mapper(resource);

                    mappers.addMapper(mapper);
                }
            }

            return mappers;
        }

        private TypeAliases createAliases(Node root) throws ParserConfigurationException {
            TypeAliases aliases = new TypeAliases();
            NodeList list = root.getChildNodes();


            for (int i = 0; i < list.getLength(); i++) {
                Node child = list.item(i);
                if (child.getNodeType() == Node.ELEMENT_NODE) {
                    String name = child.getNodeName();
                    if (!name.equals("typeAlias"))
                        throw new ParserConfigurationException("Wrong child name in " + name);

                    String alias = child.getAttributes().item(0).getNodeValue();
                    String type = child.getAttributes().item(1).getNodeValue();

                    aliases.addTypeAlias(new TypeAlias(alias, type));
                }
            }

            return aliases;
        }

        private Environments createEnvironments(Node root) throws ParserConfigurationException {
            Environments environments = new Environments();
            NodeList list = root.getChildNodes();

            for (int i = 0; i < list.getLength(); i++) {
                Node child = list.item(i);
                if (child.getNodeType() == Node.ELEMENT_NODE) {
                    String name = child.getNodeName();
                    if (!name.equals("environment"))
                        throw new ParserConfigurationException("Wrong child name in" + name);
                    String id = child.getAttributes().item(0).getNodeValue();

                    environments.addEnvironments(createEnvironment(child, id));
                }
            }

            return environments;
        }

        private Environment createEnvironment(Node root, String id) {
            NodeList list = root.getChildNodes();
            DataSource source = null;
            String manager = null;
            for (int i = 0; i < list.getLength(); i++) {
                Node child = list.item(i);
                if (child.getNodeType() == Node.ELEMENT_NODE) {
                    String name = child.getNodeName();

                    if (name.equals("transactionManager")) {
                        manager = child.getAttributes().item(0).getNodeValue();
                    }
                    if (name.equals("dataSource")) {
                        String type = child.getAttributes().item(0).getNodeValue();
                        source = createSource(child, type);
                    }

                }
            }
            return new Environment(id, manager, source);
        }

        private DataSource createSource(Node root, String type) {
            NodeList list = root.getChildNodes();
            Properties properties = new Properties();

            for (int i = 0; i < list.getLength(); i++) {
                Node child = list.item(i);
                if (child.getNodeType() == Node.ELEMENT_NODE) {
                    String name = child.getAttributes().item(0).getNodeValue();
                    String value = child.getAttributes().item(1).getNodeValue();
                    properties.put(name, value);
                }
            }

            return new DataSource(type, properties);
        }
    }
}
