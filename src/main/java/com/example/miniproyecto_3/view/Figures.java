package com.example.miniproyecto_3.view;
import javafx.beans.binding.Bindings;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;

public class Figures {

    public static Group crearEquis(Color color, double grosor) {
        Path equis = new Path(
                new MoveTo(0, 0),
                new LineTo(40, 40),
                new MoveTo(0, 40),
                new LineTo(40, 0)
        );
        equis.setStroke(color);
        equis.setStrokeWidth(grosor);
        return new Group(equis);
    }

    public static Group Portaaviones(Color colorRelleno, Color colorBorde){
        Path carrier = new Path();
        carrier.getElements().addAll(
                new MoveTo(20,10),
                new LineTo(30,10),
                new LineTo(35,40),
                new LineTo(46,47),
                new LineTo(46,67),
                new LineTo(44,75),
                new LineTo(44,125),
                new LineTo(46,133),
                new LineTo(46,153),
                new LineTo(44,161),
                new LineTo(44, 176),
                new LineTo(35,176),
                new LineTo(35,191),
                new LineTo(15,191),
                new LineTo(15, 176),
                new LineTo(6,176),
                new LineTo(6,47),
                new LineTo(15,40),
                new ClosePath()
        );
        carrier.setFill(colorRelleno);
        carrier.setStroke(colorBorde);
        carrier.setStrokeWidth(1);
        Path pista = new Path(
                new MoveTo(35, 40),
                new LineTo(15,191),
                new MoveTo(25,191),
                new LineTo(44,47)
        );
        pista.setStroke(colorBorde);
        pista.setStrokeWidth(1);
        Ellipse plane1 = new Ellipse(12,50,3,2);
        plane1.setFill(Color.BLACK);
        Ellipse plane2 = new Ellipse(12,65,3,2);
        plane2.setFill(Color.BLACK);
        Ellipse plane3 = new Ellipse(12,80,3,2);
        plane3.setFill(Color.BLACK);
        Ellipse plane4 = new Ellipse(12,95,3,2);
        plane4.setFill(Color.BLACK);
        Ellipse plane5 = new Ellipse(12,110,3,2);
        plane5.setFill(Color.BLACK);
        Ellipse plane6 = new Ellipse(12,165,3,2);
        plane6.setFill(Color.BLACK);
        Rectangle rect = new Rectangle(8,125,10,25);
        rect.setFill(Color.BLACK);
        return new Group(carrier, pista, plane1,plane2, plane3, plane4,plane5,rect,plane6);
    }

    public static Group Destructor(Color colorRelleno, Color colorBorde){
        Path destroyer = new Path();
        destroyer.getElements().addAll(
                new MoveTo(25,10),
                new LineTo(40,30),
                new LineTo(40,80),
                new LineTo(35,80),
                new LineTo(35,90),
                new LineTo(15,90),
                new LineTo(15,80),
                new LineTo(10,80),
                new LineTo(10,30),
                new ClosePath(),
                new MoveTo(25,14),
                new LineTo(37,30),
                new LineTo(37,77),
                new LineTo(32,77),
                new LineTo(32,87),
                new LineTo(18,87),
                new LineTo(18,77),
                new LineTo(13,77),
                new LineTo(13,30),
                new ClosePath()
        );
        destroyer.setFill(colorRelleno);
        destroyer.setStroke(colorBorde);
        destroyer.setStrokeWidth(1);
        Rectangle rect = new Rectangle(18,50,14,20);
        rect.setFill(Color.BLACK);
        Rectangle rect2 = new Rectangle(20,52,10,15);
        rect2.setFill(Color.DARKGRAY);
        Circle circle = new Circle(25,30,5);
        circle.setFill(Color.BLACK);
        return new Group(destroyer,rect,rect2,circle);
    }

    public static Group Fragata(Color colorRelleno, Color colorBorde) {
        Path frigate = new Path();
        frigate.getElements().addAll(
                new MoveTo(25,5),
                new LineTo(40,20),
                new LineTo(40,40),
                new QuadCurveTo(25,50,10,40),
                new LineTo(10,20),
                new ClosePath(),

                new MoveTo(15,25),
                new HLineTo(35),
                new VLineTo(35),
                new HLineTo(30),
                new VLineTo(40),
                new HLineTo(20),
                new VLineTo(35),
                new HLineTo(15),
                new ClosePath()
        );
        frigate.setFill(colorRelleno);
        frigate.setStroke(colorBorde);
        frigate.setStrokeWidth(1);
        Circle circle = new Circle(25,15,4);
        circle.setFill(Color.BLACK);
        Rectangle rect = new Rectangle(17,27,16,6);
        rect.setFill(Color.BLACK);
        return new Group(frigate,circle,rect);
    }

    public static Group Submarine(Color colorRelleno, Color colorBorde) {
        Path submarine = new Path();
        submarine.getElements().addAll(
                new MoveTo(20,10),
                new QuadCurveTo(25,5,30,10),
                new MoveTo(30,10),
                new QuadCurveTo(63,65,25,130),
                new QuadCurveTo(-10,65,20,10),

                new MoveTo(18,118),
                new LineTo(10,130),
                new LineTo(10,140),
                new LineTo(25,134),
                new LineTo(40,140),
                new LineTo(40,130),
                new LineTo(32,118)
        );
        submarine.setFill(colorRelleno);
        submarine.setStrokeWidth(1);
        submarine.setStroke(colorBorde);

        Ellipse elipse = new Ellipse(25,130,3,15);
        elipse.setFill(Color.DIMGREY);
        elipse.setStroke(colorBorde);

        Ellipse elipse2 = new Ellipse(25,30,5,10);
        elipse2.setFill(Color.BLACK);
        elipse2.setStroke(colorBorde);
        return new Group(submarine,elipse,elipse2);
    }

    public static void prepareFigures(Group figure, Pane contenedor, double baseWidth, double baseHeight, int isHorizontal) {
        // Escala
        double finalBaseWidth = (isHorizontal == 0) ? baseHeight : baseWidth;
        double finalBaseHeight = (isHorizontal == 0) ? baseWidth : baseHeight;

        Scale escala = new Scale();
        escala.xProperty().bind(Bindings.createDoubleBinding(
                () -> contenedor.getWidth() / finalBaseWidth,
                contenedor.widthProperty()
        ));
        escala.yProperty().bind(Bindings.createDoubleBinding(
                () -> contenedor.getHeight() / finalBaseHeight,
                contenedor.heightProperty()
        ));
        figure.getTransforms().add(escala);

        // Rotación (después de escalar)
        if (isHorizontal == 0) {
            Bounds bounds = figure.getBoundsInParent(); // importante: usar getBoundsInParent tras escalar
            double centerX = bounds.getMinX() + bounds.getWidth() / 2;
            double centerY = bounds.getMinY() + bounds.getHeight() / 2;
            figure.getTransforms().add(new Rotate(90, centerX, centerY));
        }
    }


    public static Group createShip(int size, Color colorRelleno, Color colorBorde) {
        return switch (size) {
            case 1 -> Fragata(colorRelleno, colorBorde);
            case 2 -> Destructor(colorRelleno, colorBorde);
            case 3 -> Submarine(colorRelleno, colorBorde);
            case 4 -> Portaaviones(colorRelleno, colorBorde);
            default -> throw new IllegalArgumentException("Tamaño de barco no válido: " + size);
        };
    }
}
