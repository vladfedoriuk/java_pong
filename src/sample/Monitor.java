package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Monitor {
    public static boolean playing;
    public GraphicsContext gc;
    private Vector ball_v;
    private Vector speed;
    private Map<String, Position> positions = new HashMap<>();
    private ArrayList<Integer> score = new ArrayList<>();
    private String s;
    public static KeyCode code;

    Vector get_speed(){
        return speed;
    }

    void set_speed(Vector v){
        speed = v;
    }

    Position get_pos(String name){
        return positions.get(name);
    }
    void set_pos(String name, Position p){
        positions.put(name, p);
    }
    Vector get_ball_v(){
        return ball_v;
    }
    void set_ball_v(Vector v){
        ball_v = v;
    }

    int get_score(int i){
        return score.get(i);
    }
    void set_score(int i, int scr){
        score.add(i, scr);
    }

    public void clear() {

            gc.setFill(Color.BLACK);
            gc.setGlobalBlendMode(BlendMode.SRC_OVER);
            gc.fillRect(0, 0, Main.width, Main.height);

            for(Lines line: Lines.values()){
                line.draw(gc);
            }

            gc.setFill(Color.WHITE);
            gc.fillRect((int)get_pos("player0").get_x(),(int) get_pos("player0").get_y(), Player.width, Player.height);
            gc.fillRect((int)get_pos("player1").get_x(), (int)get_pos("player1").get_y(), Player.width, Player.height);

            gc.setFill(Color.YELLOW);
            gc.fillOval((int)get_pos("ball").get_x(), (int)get_pos("ball").get_y(), Ball.r, Ball.r);

            gc.setFill(Color.YELLOW);
            //gc.setStroke(Color.YELLOW);
            gc.setFont(Font.font("Droid Sans", FontWeight.BOLD, 40));
            if(playing) s = null; else s = "TAP TO PLAY";
            //gc.strokeText(s, (int)(Main.width/2)-45, (int)(Main.height/2) - 40,100 );
            gc.fillText(s, (int)(Main.width/2)-45, (int)(Main.height/2) - 40,100);
    }

}
