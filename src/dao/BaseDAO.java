package dao;

public interface BaseDAO<T> {
    boolean add(T t);
    boolean update(T t);
    boolean delete(T t);
}