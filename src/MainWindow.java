import javax.swing.*;


public class MainWindow extends JFrame {

    public MainWindow() {
        setTitle("Змейка");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);    // крестик юудет прекращать работу
        setSize(500, 500);
        setLocation(800,800);
        add(new GameField());
        setVisible(true);

    }

    public static void main(String[] args) {
        MainWindow mw = new MainWindow();
    }

}
