package sample;

public class Vector {
    private double x;
    private double y;

    Vector(double x, double y){
        this.x = x;
        this.y = y;
    }
    double get_x(){
        return x;
    }
    double get_y(){
        return y;
    }
    double mul(Vector v){
        return x*v.get_x() + y*v.get_y();
    }

    void mul(double c){
        x = x*c;
        y = y*c;
    }
    public static Vector sum(Vector v1, Vector v2){
        return new Vector(v1.get_x() + v2.get_x(), v1.get_y() + v2.get_y());
    }

    public static Vector mul(Vector v, double c){
        return new Vector(v.get_x()*c, v.get_y()*c);
    }

    void div(double c){
        if(c==0) throw new ArithmeticException("division by zero");
        x = x/c;
        y = y/c;
    }

    public static Vector normalize(Vector v){
        double len = Math.sqrt(v.get_x()*v.get_x() + v.get_y()*v.get_y());
        if(len==0.0) throw new ArithmeticException("normalizing a zero vector");
        return new Vector(v.get_x()/len, v.get_y()/len);
    }

}
