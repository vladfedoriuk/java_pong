package sample;

public enum Points {
    P0(5.0, 200.0),
    P1(265.0, 5.0),
    P2(530.0, 5.0),
    P3(795.0, 200.0),
    P4(795.0, 400.0),
    P5(530.0, 595.0),
    P6(265.0, 595.0),
    P7(5.0, 400.0);
    private double x;
    private double y;
    Points(double x, double y){
        this.x = x;
        this.y = y;
    }
    double get_x(){
        return x;
    }
    double get_y(){
        return y;
    }
    public static double distance(Position p, Position q){
        return Math.sqrt((p.get_x()-q.get_x())*(p.get_x()-q.get_x()) + (p.get_y() - q.get_y())* (p.get_y() - q.get_y()));
    }
}
