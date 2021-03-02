package sample;

import javafx.scene.input.KeyCode;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Player implements Runnable{

    static final int height = 50;
    static final int width = 15;
    private Monitor monitor;

    Player(Monitor monitor){
        this.monitor = monitor;
    }
    @Override
    public void run() {

        while (true) {
            synchronized (monitor) {

                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (Main.choice.equals("Y")) {

                    if (Monitor.code == KeyCode.W) {
                        if (monitor.get_pos("player0").get_y() - 20 > Points.P0.get_y()) {
                            double y = monitor.get_pos("player0").get_y() - 15;
                            monitor.get_pos("player0").set_y(y);
                        }
                        Monitor.code = null;
                    } else if (Monitor.code == KeyCode.S) {
                        if (monitor.get_pos("player0").get_y() + 55 < Points.P7.get_y()) {
                            double y = monitor.get_pos("player0").get_y() + 15;
                            monitor.get_pos("player0").set_y(y);
                        }
                        Monitor.code = null;
                    }

                } else if (Main.choice.equals("N")) {

                    if (Monitor.code == KeyCode.UP) {
                        if (monitor.get_pos("player1").get_y() - 20 > Points.P3.get_y()) {
                            double y = monitor.get_pos("player1").get_y() - 15;
                            monitor.get_pos("player1").set_y(y);
                        }
                        Monitor.code = null;
                    } else if (Monitor.code == KeyCode.DOWN) {
                        if (monitor.get_pos("player1").get_y() + 55 < Points.P4.get_y()) {
                            double y = monitor.get_pos("player1").get_y() + 15;
                            monitor.get_pos("player1").set_y(y);
                        }
                        Monitor.code = null;

                    }
                }
            }
        }
    }
}

