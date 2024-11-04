package dat.daos;

import java.util.List;

public interface IDAO<T, I>
{
    T create(T t);
    T getById(I i);
    List<T> getAll();
    T update(I i, T t);
    void delete(I i);
}
