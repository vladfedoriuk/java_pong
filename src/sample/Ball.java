package sample;

import javafx.application.Platform;

public class Ball implements Runnable {
    static final int r = 15;
    private Monitor monitor;

    Ball(Monitor monitor) {
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

                Position pos = monitor.get_pos("ball");
                Position p1 = monitor.get_pos("player0");
                Position p2 = monitor.get_pos("player1");
                Vector v = monitor.get_ball_v();
                if (Points.distance(pos, p1) > Player.height && Lines.H.distance(pos) < r) {
                    monitor.set_score(1, monitor.get_score(1)+1);
                    Platform.runLater(() -> Main.score2.setText(Integer.toString(monitor.get_score(1))));
                    Monitor.playing = false;
                }
                if (Points.distance(pos, p2) > Player.height && Lines.D.distance(pos) < r) {
                    monitor.set_score(0, monitor.get_score(0)+1);
                    Platform.runLater(() -> Main.score2.setText(Integer.toString(monitor.get_score(0))));
                    Monitor.playing = false;
                }
                for(Lines line: Lines.values()){
                    if(line.distance(pos) < r){
                        v = line.reflect(v);
                    }
                }
                monitor.set_speed(v);
                monitor.set_ball_v(v);
                pos.set_x(pos.get_x()+v.get_x());
                pos.set_y(pos.get_y()+v.get_y());
                monitor.set_pos("ball", pos);
            }
        }
    }
}
