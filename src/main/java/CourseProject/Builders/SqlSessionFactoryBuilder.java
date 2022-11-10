package CourseProject.Builders;

import CourseProject.Configuration.Configuration;
import CourseProject.Configuration.Mapper;
import CourseProject.XmlParserConfig;
import CourseProject.XmlParserMapper;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

public class SqlSessionFactoryBuilder {

    public SqlSessionFactory build(String resource) {
        XmlParserConfig parser = new XmlParserConfig(resource);
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
