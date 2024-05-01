package Medizen.Interfaces;
import java.util.List;
import java.sql.SQLException;


public interface IService <T>{
    //CRUD
    //1
    void add(T t) throws SQLException;

    void delete(T t) throws SQLException;

    void update(T t) throws SQLException;
    List<T> Readall() throws SQLException;
    //5:All
    List <T> getAll();
    //6:by criteria
    T getOne(int id);



}
