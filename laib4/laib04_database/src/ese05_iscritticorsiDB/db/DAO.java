package ese05_iscritticorsiDB.db;

public interface DAO<T> {
	public void create();
	public T read();
	public void update();
	public void delete();
}
