package ua.nure.st.kpp.example.demo.Flowers;

public interface TW_list extends Iterable
{
    void add(Plant data);
    void clear();
    boolean remove(Plant element);
    Plant[] toArray();
    int size();
    boolean contains(Plant element);
    void print();
    void rprint();
}