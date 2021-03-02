package sample;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Message implements Serializable{
   public double player0_x;
   public double player0_y;
   public double player1_x;
   public double player1_y;
   public double ball_x;
   public double ball_y;
   public boolean playing;

   public int score0;
   public int score1;

   public void print(){
       System.out.println(player0_x+" "+player0_y);
       System.out.println(player1_x+" "+player1_y);
       System.out.println(ball_x+" "+ball_y);
       System.out.println(score0+" "+score1);
       System.out.println(playing);
   }

   void reset(){
       ball_x = Main.width/2-Ball.r/2;
       ball_y = Main.height/2+Ball.r/2;

       player0_x = 0;
       player0_y = Main.height/2;

       player1_x = Main.width - Player.width;
       player1_y = Main.height/2;

       playing = Monitor.playing;
   }
   Message(){
       ball_x = Main.width/2-Ball.r/2;
       ball_y = Main.height/2+Ball.r/2;

       player0_x = 0;
       player0_y = Main.height/2;

       player1_x = Main.width - Player.width;
       player1_y = Main.height/2;

       score0 = 0;
       score1 = 0;
       playing = true;
   }
    void readObject(ObjectInputStream in)throws IOException, ClassNotFoundException {
        player0_x = in.readDouble();
        player0_y = in.readDouble();

        player1_x = in.readDouble();
        player1_y = in.readDouble();

        ball_x = in.readDouble();
        ball_y = in.readDouble();

        score0 = in.readInt();
        score1 = in.readInt();

        playing = in.readBoolean();

    }
    void writeObject(ObjectOutputStream out)throws IOException {
        out.writeDouble(player0_x);
        out.writeDouble(player0_y);

        out.writeDouble(player1_x);
        out.writeDouble(player1_y);

        out.writeDouble(ball_x);
        out.writeDouble(ball_y);

        out.writeInt(score0);
        out.writeInt(score1);

        out.writeBoolean(playing);

    }
}
