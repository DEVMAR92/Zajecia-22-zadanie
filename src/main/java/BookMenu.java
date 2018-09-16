import java.util.InputMismatchException;
import java.util.Scanner;

public class BookMenu {

    private final static int CREATE = 1;
    private final static int READ = 2;
    private final static int UPDATE = 3;
    private final static int DELETE = 4;
    private final static int EXIT = 0;

    static void start() {

        Scanner input = new Scanner(System.in);
        BookDao bookDao = new BookDao();
        int result = 0;
        try {
            System.out.println("\nWITAJ W BIBLIOTECE");
            System.out.println("------------------");
            do {
                System.out.println(CREATE + " - DODAJ KSIĄŻKĘ");
                System.out.println(READ + " - WCZYTAJ KSIĄŻKĘ");
                System.out.println(UPDATE + " - AKTUALIZUJ KSIĄŻKĘ");
                System.out.println(DELETE + " - USUŃ KSIĄŻKĘ");
                System.out.println(EXIT + " - KONIEC");
                result = input.nextInt();
                if (result > 4 || result < 0){
                    System.err.println("WPROWADZ LICZBE OD 0 DO 4");
                }
                input.nextLine();
                switch (result) {
                    case CREATE:
                        System.out.println("Podaj tytuł:");
                        String titleCreate = input.nextLine();
                        System.out.println("Podaj autora:");
                        String authorCreate = input.nextLine();
                        System.out.println("Podaj rok wyddania:");
                        int yearCreate = input.nextInt();
                        System.out.println("Podaj numer isbn:");
                        int isbnCreate = input.nextInt();
                        input.nextLine();
                        Book bookCreate = new Book(titleCreate, authorCreate, yearCreate, isbnCreate);
                        bookDao.save(bookCreate);
                        break;
                    case READ:
                        System.out.println("Podaj nr ID");
                        long idRead = input.nextLong();
                        Book bookRead = bookDao.read(idRead);
                        System.out.println(bookRead);
                        break;
                    case UPDATE:
                        System.out.println("Podaj ID ksiazki do aktualizacji");
                        long idUpdate = input.nextLong();
                        input.nextLine();
                        System.out.println("Podaj nowy tytuł");
                        String titleUpdate = input.nextLine();
                        System.out.println("Podaj nowego autora");
                        String authorUpdate = input.nextLine();
                        System.out.println("Podaj nowy rok wydania");
                        int yearUpdate = input.nextInt();
                        System.out.println("Podaj nowy nr isbn");
                        int isbnUpdate = input.nextInt();
                        input.nextLine();
                        Book bookUpdate = new Book(idUpdate, titleUpdate, authorUpdate, yearUpdate, isbnUpdate);
                        bookDao.update(bookUpdate);
                        break;
                    case DELETE:
                        System.out.println("Podaj ID ksiazki do skasowania");
                        long idDelete = input.nextLong();
                        input.nextLine();
                        bookDao.delete(idDelete);
                        break;
                }
            } while (result != EXIT);
        } catch (InputMismatchException e) {
            System.err.println("NIEPOPRAWNE DANE WPROWADZ LICZBE");
            e.printStackTrace();
        } catch (ElementNotFoundException e) {
            e.printStackTrace();
        }
    }
}
