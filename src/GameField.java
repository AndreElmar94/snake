import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;


// основные действия игры


public class GameField extends JPanel implements ActionListener {


    private final int SIZE = 500;
    private final int DOT_SIZE = 16; // сколько пикс будет занимать размер яблочко и 1 секция змейки (16х16)
    private final int ALL_DOTS = 800; //  сколько игровых единиц можт пометситс на поле
    private Image dot;
    private Image apple;
    private int appleX; // х - позиция яблока
    private int appleY; // y - позиция яблока
    private int[] x = new int[ALL_DOTS]; // массив, где храним все положения змейки
    private int[] y = new int[ALL_DOTS]; // массив, где храним все положения змейки
    private int dots; // размер змейки
    private Timer timer; // таймер стандарнтый свинговый
    private boolean left = false; // отвечает за текущее направление змейки
    private boolean right = true; // отвечает за текущее направление змейки
    private boolean up = false; // отвечает за текущее направление змейки
    private boolean down = false; // отвечает за текущее направление змейки
    private boolean inGame = true; // мы ещё  в игре или нет?


    public GameField() {
        setBackground(Color.black); // цвет игрового поля
        loadImages();
        inGame();
        addKeyListener(new FiledKeyListneer());
        setFocusable(true);
    }

    // метод который инициализирует начало ИГРЫ
    public void inGame() {
        dots = 1;
        for (int i = 0; i < dots; i++) {
            x[i] = 48 - i * DOT_SIZE;   // первое звено змейки
            y[i] = 48; // плашмя положется вдоль Оси (x)
        }
        timer = new Timer(200, this); // 70 млсек (будет с такой скоростью тикать змейка)
        timer.start();
        createApple();
    }

    public void createApple() {
        appleX = new Random().nextInt(20) * DOT_SIZE;
        appleY = new Random().nextInt(20) * DOT_SIZE;
    }

    //метод для загрузки картинок
    public void loadImages() {
        ImageIcon iconApple = new ImageIcon("apple.png");
        apple = iconApple.getImage();
        ImageIcon iconDot = new ImageIcon("dot.png");
        dot = iconDot.getImage();

    }

    // сгенерировала метод
    // перерисовка
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inGame) {
            g.drawImage(apple, appleX, appleY, this);
            // перерисовываю всю змейку
            for (int i = 0; i < dots; i++) {
                g.drawImage(dot, x[i], y[i], this);
            }

        } else {
            String str = "Game Over";
             // Font f = new Font("Arial", 14, Font.BOLD);
            g.setColor(Color.white);
            // g.setFont(f);
            g.drawString(str, 125, SIZE / 2);
        }
    }

    // логическая перерисовка точек (ячеек)
    public void move() {
        for (int i = dots; i > 0; i--) {
            x[i] = x[i - 1];    //туловище
            y[i] = y[i - 1];
        }
        // голова
        if (left) {
            x[0] -= DOT_SIZE;
        }
        if (right) {
            x[0] += DOT_SIZE;
        }
        if (up) {
            y[0] -= DOT_SIZE;
        }
        if (down) {
            y[0] += DOT_SIZE;
        }
    }

    // голова змейки x[0]
    // столкновение с яблоком
    public void checkApple() {
        if (x[0] == appleX && y[0] == appleY) {
            dots++;
            createApple();
        }
    }

    public void checkCollisions() {
        // проверим не врезались ли мы сами в себя
        for (int i = dots; i > 0; i--) {
            if (i > 4 && x[0] == x[i] && y[0] == y[i]) {
                inGame = false;
            }
        }
        if (x[0] > SIZE) {
            inGame = false;
        }
        if (x[0] < 0) {
            inGame = false;
        }
        if (y[0] > SIZE) {
            inGame = false;
        }
        if (y[0] < 0) {
            inGame = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
// если я в игре
        if (inGame) {
            checkApple(); // встреча с яблоком
            checkCollisions();// встеча с границами поля
            move();
        }
        repaint(); // надо перерисовывать поле каждый раз
    }

    // обработка нажатия клавиш
    class FiledKeyListneer extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();   // смотрю что было нажато какая клавиша
//в соответсвтии с тем, какая была нажата клавиша, происходят след.изменения
            if (key == KeyEvent.VK_LEFT && !right) {
                left = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_RIGHT && !left) {
                right = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_UP && !down) {
                right = false;
                up = true;
                left = false;
            }
            if (key == KeyEvent.VK_DOWN && !up) {
                right = false;
                down = true;
                left = false;
            }
        }
    }
}
