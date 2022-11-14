package CourseProject.DAO;

import CourseProject.Anotations.Cache;
import CourseProject.Anotations.Select;
import CourseProject.Anotations.SelectAll;

import java.util.List;

@Cache(eviction = "FIFO", flushInterval = 60000, size = 512)
public interface DAO {

    int update(String sql, Object parameter) throws Exception;

    int delete(String sql, Object parameter) throws Exception;

    int insert(String sql, Object parameter) throws Exception;

    @Select(query = "SELECT FROM employee where id=#{id}", useCache = true)
    <T> T selectObject(Class<T> c, Object parameter) throws Exception;

    @SelectAll(query = "SELECT * FROM employee", useCache = true)
    <T> List<T> selectList(Class<T> c, Object parameter) throws Exception;
}
