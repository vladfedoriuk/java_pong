package sample;

public class Position {
    private double x;
    private double y;
    Position(double x, double y){
        this.x = x;
        this.y = y;
    }
    double get_x(){
        return x;
    }
    double get_y(){
        return y;
    }

    void set_x(double x){
        this.x = x;
    }

    void set_y(double y){
        this.y = y;
    }
};
