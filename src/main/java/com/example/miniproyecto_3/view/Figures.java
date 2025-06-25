package com.example.miniproyecto_3.view;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

/**
 * Class that creates the shapes for ships and other graphical elements used in the game.
 */
public class Figures {

    /**
     * Creates the shape of a carrier ship.
     *
     * @param fillColor the color used to fill the shape
     * @param borderColor the color used for the border
     * @return a group containing the carrier
     */
    public static Group aircraftCarrier(Color fillColor, Color borderColor){
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
        carrier.setFill(fillColor);
        carrier.setStroke(borderColor);
        carrier.setStrokeWidth(1);
        Path pista = new Path(
                new MoveTo(35, 40),
                new LineTo(15,191),
                new MoveTo(25,191),
                new LineTo(44,47)
        );
        pista.setStroke(borderColor);
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


    /**
     * Creates the shape of a destroyer ship.
     *
     * @param fillColor the color used to fill the shape
     * @param borderColor the color used for the border
     * @return a group containing the destroyer
     */
    public static Group destroyer(Color fillColor, Color borderColor){
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
        destroyer.setFill(fillColor);
        destroyer.setStroke(borderColor);
        destroyer.setStrokeWidth(1);
        Rectangle rect = new Rectangle(18,50,14,20);
        rect.setFill(Color.BLACK);
        Rectangle rect2 = new Rectangle(20,52,10,15);
        rect2.setFill(Color.DARKGRAY);
        Circle circle = new Circle(25,30,5);
        circle.setFill(Color.BLACK);
        return new Group(destroyer,rect,rect2,circle);
    }


    /**
     * Creates the shape of a frigate ship.
     *
     * @param fillColor the color used to fill the shape
     * @param borderColor the color used for the border
     * @return a group containing the frigate
     */
    public static Group frigate(Color fillColor, Color borderColor) {
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
        frigate.setFill(fillColor);
        frigate.setStroke(borderColor);
        frigate.setStrokeWidth(1);
        Circle circle = new Circle(25,15,4);
        circle.setFill(Color.BLACK);
        Rectangle rect = new Rectangle(17,27,16,6);
        rect.setFill(Color.BLACK);
        return new Group(frigate,circle,rect);
    }


    /**
     * Creates the shape of a submarine ship.
     *
     * @param fillColor the color used to fill the shape
     * @param borderColor the color used for the border
     * @return a group containing the submarine
     */
    public static Group Submarine(Color fillColor, Color borderColor) {
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
        submarine.setFill(fillColor);
        submarine.setStrokeWidth(1);
        submarine.setStroke(borderColor);

        Ellipse elipse = new Ellipse(25,130,3,15);
        elipse.setFill(Color.DIMGREY);
        elipse.setStroke(borderColor);

        Ellipse elipse2 = new Ellipse(25,30,5,10);
        elipse2.setFill(Color.BLACK);
        elipse2.setStroke(borderColor);
        return new Group(submarine,elipse,elipse2);
    }


    /**
     * Creates a ship figure based on its size.
     *
     * @param size the size of the ship (1 to 4)
     * @param fillColor the color used to fill the shape
     * @param borderColor the color used for the border
     * @return a group containing the correct ship shape
     */
    public static Group createShip(int size, Color fillColor, Color borderColor) {
        return switch (size) {
            case 1 -> frigate(fillColor, borderColor);
            case 2 -> destroyer(fillColor, borderColor);
            case 3 -> Submarine(fillColor, borderColor);
            case 4 -> aircraftCarrier(fillColor, borderColor);
            default -> throw new IllegalArgumentException("Tamaño de barco no válido: " + size);
        };
    }
}
