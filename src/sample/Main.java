package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class Main extends Application {
    static final int width = 800;
    static final int height = 600;
    private Monitor monitor = new Monitor();
    private Ball b = new Ball(monitor);
    private Player p = new Player(monitor);
    private Thread ball = new Thread(b);
    private Thread player = new Thread(p);
    static Label score1 = new Label("0");
    static Label score2 = new Label("0");
    public static String choice;
    ServerSocket ss = null;
    Socket s = null;
    Message m = new Message();
    ObjectInputStream in = null;
    ObjectOutputStream out = null;
    InetAddress host = null;


    void prepare_screen(Stage primaryStage){
        Main main = new Main();
        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        primaryStage.setTitle("PONG");
        GridPane layout = new GridPane();
        layout.setHgap(128);
        layout.setVgap(150);
        layout.add(canvas, 0,0,6,4);
        score1.setTextFill(Color.WHITE);
        score2.setTextFill(Color.WHITE);
        final double MAX_FONT_SIZE = 30.0;
        score1.setFont(new Font(MAX_FONT_SIZE));
        score2.setFont(new Font(MAX_FONT_SIZE));
        layout.add(score1,2,0);
        layout.add(score2,4,0);
        Scene scene = new Scene(layout, 800, 600);
        scene.setOnKeyPressed(main::handle);
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Monitor.playing = true;
                if(Main.choice.equals("Y")){
                    ball.resume();
                    player.resume();
                } else{
                    player.resume();
                }
            }
        });
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                if(Main.choice.equals("Y")) {
                    ball.stop();
                    player.stop();
                } else{
                    player.stop();
                }
            }
        });
        monitor.gc = gc;
        Timeline t = new Timeline(new KeyFrame(Duration.millis(10), e -> game_on(monitor)));
        t.setCycleCount(Timeline.INDEFINITE);
        primaryStage.show();
        initial_state(monitor);
        t.play();

    }

    @Override
    public void start(Stage primaryStage) throws InterruptedException {
        System.out.println("Do you want to create a server?[Y/N]");
        Scanner scanner = new Scanner(System.in);
        String ch = scanner.nextLine();
        String ip;
        if(ch.equals("N")){
            player.start();
        } else if(ch.equals("Y")){
            ball.start();
            player.start();
        }
        choice = ch;
        if(Main.choice.equals("Y")){
            try {
                ss = new ServerSocket(6666);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                host = InetAddress.getLocalHost();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        prepare_screen(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);


    }

    public void handle(KeyEvent keyEvent){
        synchronized (monitor) {
            Monitor.code = keyEvent.getCode();
        }
    }

        void initial_state(Monitor monitor){
            Monitor.playing = false;
            monitor.set_pos("ball", new Position(width/2-Ball.r/2, height/2+Ball.r/2));
            monitor.set_pos("player0", new Position(0, height / 2));
            monitor.set_pos("player1", new Position(width - Player.width, height / 2));
            int ran = (int) (Math.random()*100);
            int ran1 = (int) (Math.random()*100);
            monitor.set_speed(new Vector(ran%2 == 0 ? 5.0 : -5.0, ran1%2 == 0 ? 5.0 : -5.0));
            monitor.set_ball_v(monitor.get_speed());
            monitor.set_score(0,0);
            monitor.set_score(1,0);
            m.reset();
            monitor.clear();
        }

        private void game_on(Monitor monitor){
            if(Main.choice.equals("Y")) {
                try {
                    System.out.println("Waiting to request");
                    s = ss.accept();
                    System.out.println("ok");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    in = new ObjectInputStream(s.getInputStream());
                    System.out.println("in_ok");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    out = new ObjectOutputStream(s.getOutputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    m.readObject(in);
                    m.print();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                monitor.set_pos("player1", new Position(m.player1_x, m.player1_y));
                m.player0_x = monitor.get_pos("player0").get_x();
                m.player0_y = monitor.get_pos("player0").get_y();

                m.ball_x = monitor.get_pos("ball").get_x();
                m.ball_y = monitor.get_pos("ball").get_y();

                m.playing = Monitor.playing;
                m.print();

                try {
                    m.writeObject(out);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            if(Main.choice.equals("N")){
                System.out.println("ok_client");
                try {
                    s = new Socket(host,6666);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("ok_client1");
                try {
                    in = new ObjectInputStream(s.getInputStream());
                    System.out.println("ok_in_client");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    out = new ObjectOutputStream(s.getOutputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                m.player1_x = monitor.get_pos("player1").get_x();
                m.player1_y = monitor.get_pos("player1").get_y();

                monitor.set_pos("player0", new Position(m.player0_x, m.player0_y));
                monitor.set_pos("ball", new Position(m.ball_x, m.ball_y));
                monitor.set_score(0, m.score0);
                monitor.set_score(1, m.score1);
                Monitor.playing = m.playing;

                try {
                    m.writeObject(out);
                    m.print();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    m.readObject(in);
                    m.print();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }

                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            synchronized (monitor) {
                if(Monitor.playing) monitor.notifyAll();
                else {
                    initial_state(monitor);
                   if(Main.choice.equals("Y")){
                       ball.suspend();
                       player.suspend();
                   } else{
                       player.suspend();
                   }
                }
                monitor.clear();
            }
        }
    }


