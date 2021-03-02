package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public enum Lines implements Drowable {
    A(Points.P0, Points.P1) {
        @Override
        public void draw(GraphicsContext gc) {
            gc.setStroke(Color.WHITE);
            gc.strokeLine(p1.get_x(), p1.get_y(), p2.get_x(), p2.get_y());
        }
    },
    B(Points.P1, Points.P2) {
        @Override
        public void draw(GraphicsContext gc) {
            gc.setStroke(Color.YELLOW);
            gc.strokeLine(p1.get_x(), p1.get_y(), p2.get_x(), p2.get_y());
        }
    },
    C(Points.P2, Points.P3) {
        @Override
        public void draw(GraphicsContext gc) {
            gc.setStroke(Color.BLUE);
            gc.strokeLine(p1.get_x(), p1.get_y(), p2.get_x(), p2.get_y());
        }
    },
    D(Points.P3, Points.P4) {
        @Override
        public void draw(GraphicsContext gc) {
            gc.setStroke(Color.MAGENTA);
            gc.strokeLine(p1.get_x(), p1.get_y(), p2.get_x(), p2.get_y());
        }
    },
    E(Points.P4, Points.P5) {
        @Override
        public void draw(GraphicsContext gc) {
            gc.setStroke(Color.AZURE);
            gc.strokeLine(p1.get_x(), p1.get_y(), p2.get_x(), p2.get_y());
        }
    },
    F(Points.P5, Points.P6) {
        @Override
        public void draw(GraphicsContext gc) {
            gc.setStroke(Color.GREEN);
            gc.strokeLine(p1.get_x(), p1.get_y(), p2.get_x(), p2.get_y());
        }
    },
    G(Points.P6, Points.P7) {
        @Override
        public void draw(GraphicsContext gc) {
            gc.setStroke(Color.ORANGE);
            gc.strokeLine(p1.get_x(), p1.get_y(), p2.get_x(), p2.get_y());
        }
    },
    H(Points.P7, Points.P0) {
        @Override
        public void draw(GraphicsContext gc) {
            gc.setStroke(Color.PURPLE);
            gc.strokeLine(p1.get_x(), p1.get_y(), p2.get_x(), p2.get_y());
        }
    };


    Lines(Points p1, Points p2) {
        this.p1 = p1;
        this.p2 = p2;
        if(p1.get_x() == p2.get_x()){
            double ran_x =  (Math.random()*100);
            Vector temp = new Vector(ran_x- p1.get_x(), 0.0);
            v = Vector.normalize(temp);
        } else
        if(p1.get_y() == p2.get_y()){
            double ran_y =  (Math.random()*100);
            Vector temp = new Vector( 0.0, ran_y- p1.get_y());
            v = Vector.normalize(temp);
        } else {
            a = (p2.get_y() - p1.get_y()) / (p2.get_x() - p1.get_x());
            b = p1.get_y() - (p1.get_x() / (p2.get_x() - p1.get_x())) * (p2.get_y() - p1.get_y());
            if (a == 0) throw new ArithmeticException("division by zero");
            double c = -1.0 / a;
            double d = (a - c) * p1.get_x() + b;
            double ran_x = (Math.random() * 100);
            double ran_y = c * ran_x + d;
            Vector temp = new Vector(ran_x - p1.get_x(), ran_y - p1.get_y());
            v = Vector.normalize(temp);
        }
    }

    Points p1;
    Points p2;

    double a;
    double b;

    public final Vector v;

    public double distance(Position pos){
        if(p1.get_x() == p2.get_x()) return Math.abs(pos.get_x() - p1.get_x());
        if(p1.get_y() == p2.get_y()) return Math.abs(pos.get_y() - p1.get_y());
        double x = pos.get_x();
        double y = pos.get_y();
        double x1 = x;
        double y1 = a*x+b;
        double y2 = y;
        double x2 = (y2 - b)/a;
        double l1 = Math.sqrt((x-x1)*(x-x1) + (y-y1)*(y-y1));
        double l2 = Math.sqrt((x-x2)*(x-x2) + (y-y2)*(y-y2));
        double l3 = Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
        double p = (l1+l2+l3)/2;
        double S = Math.sqrt(p*(p-l1)*(p-l2)*(p-l3));
        return (2*S)/l3;
    }
    public Vector reflect(Vector v0){
        Vector proj = Vector.mul(v, (v.mul(v0))/(v.mul(v))*(-1));
        proj.mul(2.0);
        return Vector.sum(proj, v0);
    }
};
