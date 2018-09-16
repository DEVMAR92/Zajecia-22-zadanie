import java.sql.*;

public class BookDao {

    private static final String URL = "jdbc:mysql://localhost:3306/library?characterEncoding=utf8&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "root";
    private Connection connection;

    public BookDao() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            System.out.println("NIE ZNALEZIONO STEROWNIKA");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("BRAK POLONCZENIA Z BAZA DANYCH");
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void save(Book book) {
        final String sql = "insert into books(title, author, year, isbn) values(?, ?, ?, ?)";
        try {
            PreparedStatement prepStmt = connection.prepareStatement(sql);
            prepStmt.setString(1, book.getTitle());
            prepStmt.setString(2, book.getAuthor());
            prepStmt.setInt(3, book.getYear());
            prepStmt.setInt(4, book.getIsbn());
            prepStmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("NIE ZAPISANO KSIAZKI");
            e.printStackTrace();
        }
    }

    public Book read(long id) throws ElementNotFoundException {
        final String sql = "select idbooks, title, author, year, isbn from books where idbooks = ?";
        try {
            PreparedStatement prepStmt = connection.prepareStatement(sql);
            prepStmt.setLong(1, id);
            ResultSet result = prepStmt.executeQuery();
            if (result.next()) {
                Book book = new Book();
                book.setId(result.getLong(1));
                book.setTitle(result.getString(2));
                book.setAuthor(result.getString(3));
                book.setYear(result.getInt(4));
                book.setIsbn(result.getInt(5));
                return book;
            }
        } catch (SQLException e) {
            System.err.println("KSIAZKA O TAKIM ID NIE ISTNIEJE");
            e.printStackTrace();
        }
        throw new ElementNotFoundException();
    }

    public void update(Book book) {
        final String sql = "update books set title=?, author=?, year=?, isbn=? where idbooks = ?";
        try {
            PreparedStatement prepStmt = connection.prepareStatement(sql);
            prepStmt.setString(1, book.getTitle());
            prepStmt.setString(2, book.getAuthor());
            prepStmt.setInt(3, book.getYear());
            prepStmt.setInt(4, book.getIsbn());
            prepStmt.setLong(5, book.getId());
            prepStmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("NIE ZAKTUALIZOWANO KSIAZKI");
            e.printStackTrace();
        }
    }

    public void delete(long id) {
        final String sql = "delete from books where idbooks = ?";
        try {
            PreparedStatement prepStmt = connection.prepareStatement(sql);
            prepStmt.setLong(1, id);
            prepStmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("NIE USUNIETO REKORDU");
            e.printStackTrace();
        }
    }


}
