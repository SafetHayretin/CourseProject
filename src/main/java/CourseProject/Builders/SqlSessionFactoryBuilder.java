package CourseProject.Builders;

import CourseProject.Configuration.Configuration;
import CourseProject.Configuration.Mapper;
import Homework53.Homework53;
import Parsers.XmlParserMapper;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

public class SqlSessionFactoryBuilder {

    public SqlSessionFactory build(String resource) {
        Homework53.XmlParserConfig parser = new Homework53.Homework53.XmlParserConfig(resource);
        Configuration config = null;

        try {
            config = parser.getConfiguration();
        } catch (ParserConfigurationException e) {
            throw new IllegalArgumentException("Resource was not found!");
        } catch (SAXException e) {
            throw new RuntimeException("Wrong xml format");
        } catch (IOException e) {
            e.printStackTrace();
        }


        XmlParserMapper mapperParser = new XmlParserMapper(config);
        List<Mapper> mappers = config.mappers.getAllMappers();

        for (int i = 0; i < mappers.size(); i++) {
            String mapperResource = mappers.get(i).resource;

            try {
                mapperParser.getMapper(mapperResource, i);
            } catch (ParserConfigurationException e) {
                throw new IllegalArgumentException("Resource was not found!");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                throw new RuntimeException("Wrong input/output !");
            }
        }

        return new SqlSessionFactory(config);
    }
}
