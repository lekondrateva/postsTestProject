package requests;

public interface CrudRepository<T> {

    T create(T item);

    T update(T item, int id);

    T read(int id);

    String delete(int id);

}
