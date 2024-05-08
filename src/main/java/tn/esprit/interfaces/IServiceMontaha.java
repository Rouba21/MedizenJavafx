package tn.esprit.interfaces;

import java.sql.SQLException;
import java.util.List;

public interface IServiceMontaha<T> {
    //CRUD
    public void add(T t ) ;
    public void update(T t ) throws SQLException;;
    public void delete(int id) throws SQLException;
    //4:all
    List<T> getAll()throws SQLException;
    //5 : one
    T getOne(int id) ;
    //6: by criteria


}
