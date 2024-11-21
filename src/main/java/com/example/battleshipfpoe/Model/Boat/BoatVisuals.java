package com.example.battleshipfpoe.Model.Boat;

import javafx.scene.Group;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class BoatVisuals {
    public class Aircraft {

        private double x;
        private double y;
        private boolean isHorizontal;
        private double weight;
        private double height;

        public Aircraft(double x, double y, boolean isHorizontal, double weight, double height) {
            this.x = x;
            this.y = y;
            this.isHorizontal = isHorizontal;
            this.weight = weight;
            this.height = height;

            AircraftDrawer(x, y, isHorizontal);
        }

        public double getX() {
            return x;
        }
        public double getY() {
            return y;
        }
        public boolean isHorizontal() {
            return isHorizontal;
        }

        public double getWeight() {
            return weight;
        }
        public double getHeight() {
            return height;
        }

        public void setHeight(double height) {
            this.height = height;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }

        public void setX(double x) {
            this.x = x;
        }
        public void setY(double y) {
            this.y = y;
        }
        public void setHorizontal(boolean isHorizontal) {
            this.isHorizontal = isHorizontal;
        }

        public Group AircraftDrawer (double x, double y, boolean isHorizontal) {
            Group group = new Group();

            Polygon polygon1 = new Polygon(69.4, -39, 63, 161, 49.4, -39);
            polygon1.setFill(Color.web("#8c9196"));
            polygon1.setStroke(Color.BLACK);
            polygon1.setLayoutX(289);
            polygon1.setLayoutY(154);

            Polygon polygon2 = new Polygon(70.4, -21.4, 61, 180, 55.2, -21.4);
            polygon2.setFill(Color.web("#8c9196"));
            polygon2.setStroke(Color.BLACK);
            polygon2.setLayoutX(224);
            polygon2.setLayoutY(135);

            Polygon polygon3 = new Polygon(64.4, -10, 83, -10, 83, -72.2);
            polygon3.setFill(Color.web("#8c9196"));
            polygon3.setStroke(Color.BLACK);
            polygon3.setLayoutX(214);
            polygon3.setLayoutY(125);

            Polygon polygon4 = new Polygon(-73, -10, -53.6, -10, -73, -73);
            polygon4.setFill(Color.web("#8c9196"));
            polygon4.setStroke(Color.BLACK);
            polygon4.setLayoutX(412);
            polygon4.setLayoutY(125);

            Polygon polygon5 = new Polygon(104.2, -6.2, 128, -17, 76, -17);
            polygon5.setFill(Color.web("#6b7b7b"));
            polygon5.setStroke(Color.BLACK);
            polygon5.setLayoutX(215);
            polygon5.setLayoutY(335);

            // Rectángulos
            Rectangle rectangle1 = new Rectangle(285, 115, 67, 200);
            rectangle1.setArcHeight(5);
            rectangle1.setArcWidth(5);
            rectangle1.setFill(Color.web("#69727b"));
            rectangle1.setStroke(Color.BLACK);

            Rectangle rectangle2 = new Rectangle(297, 25, 42, 90);
            rectangle2.setArcHeight(5);
            rectangle2.setArcWidth(5);
            rectangle2.setFill(Color.web("#8c9196"));
            rectangle2.setStroke(Color.BLACK);

            Rectangle rectangle3 = new Rectangle(301, 30, 34, 83);
            rectangle3.setArcHeight(5);
            rectangle3.setArcWidth(5);
            rectangle3.setFill(Color.web("#a2b5c7"));
            rectangle3.setStroke(Color.BLACK);

            Rectangle rectangle4 = new Rectangle(291, 110, 52, 200);
            rectangle4.setArcHeight(5);
            rectangle4.setArcWidth(5);
            rectangle4.setFill(Color.web("#a2b5c7"));
            rectangle4.setStroke(Color.BLACK);

            Rectangle rectangle5 = new Rectangle(291, 313, 52, 5);
            rectangle5.setArcHeight(5);
            rectangle5.setArcWidth(5);
            rectangle5.setStroke(Color.BLACK);

            Rectangle rectangle6 = new Rectangle(298, 115, 4, 177);
            rectangle6.setArcHeight(5);
            rectangle6.setArcWidth(5);
            rectangle6.setFill(Color.WHITE);
            rectangle6.setStroke(Color.BLACK);

            Rectangle rectangle7 = new Rectangle(333, 115, 4, 177);
            rectangle7.setArcHeight(5);
            rectangle7.setArcWidth(5);
            rectangle7.setFill(Color.WHITE);
            rectangle7.setStroke(Color.BLACK);

            // Rectángulos con efectos de sombra
            Rectangle shadowRectangle1 = new Rectangle(312, 133, 13, 19);
            shadowRectangle1.setArcHeight(5);
            shadowRectangle1.setArcWidth(5);
            shadowRectangle1.setFill(Color.web("#6f7b86"));
            shadowRectangle1.setStroke(Color.BLACK);
            shadowRectangle1.setEffect(new DropShadow());

            Rectangle shadowRectangle2 = new Rectangle(312, 162, 13, 19);
            shadowRectangle2.setArcHeight(5);
            shadowRectangle2.setArcWidth(5);
            shadowRectangle2.setFill(Color.web("#6f7b86"));
            shadowRectangle2.setStroke(Color.BLACK);
            shadowRectangle2.setEffect(new DropShadow());

            Rectangle shadowRectangle3 = new Rectangle(312, 191, 13, 19);
            shadowRectangle3.setArcHeight(5);
            shadowRectangle3.setArcWidth(5);
            shadowRectangle3.setFill(Color.web("#6f7b86"));
            shadowRectangle3.setStroke(Color.BLACK);
            shadowRectangle3.setEffect(new DropShadow());

            Rectangle shadowRectangle4 = new Rectangle(312, 224, 13, 19);
            shadowRectangle4.setArcHeight(5);
            shadowRectangle4.setArcWidth(5);
            shadowRectangle4.setFill(Color.web("#6f7b86"));
            shadowRectangle4.setStroke(Color.BLACK);
            shadowRectangle4.setEffect(new DropShadow());

            // Círculos
            Circle circle1 = new Circle(318, 265, 14);
            circle1.setFill(Color.web("#d2dae1"));
            circle1.setStroke(Color.BLACK);

            Circle circle2 = new Circle(318, 265, 10);
            circle2.setFill(Color.web("#a6acb1"));
            circle2.setStroke(Color.BLACK);
            circle2.setEffect(new Bloom());

            // Rectángulos adicionales
            Rectangle additionalRectangle1 = new Rectangle(326, 263, 13, 5);
            additionalRectangle1.setArcHeight(5);
            additionalRectangle1.setArcWidth(5);
            additionalRectangle1.setFill(Color.web("#6f7b86"));
            additionalRectangle1.setStroke(Color.BLACK);
            additionalRectangle1.setEffect(new DropShadow());

            Rectangle additionalRectangle2 = new Rectangle(298, 263, 13, 5);
            additionalRectangle2.setArcHeight(5);
            additionalRectangle2.setArcWidth(5);
            additionalRectangle2.setFill(Color.web("#6f7b86"));
            additionalRectangle2.setStroke(Color.BLACK);
            additionalRectangle2.setEffect(new DropShadow());

            // Rectángulos blancos
            Rectangle whiteRectangle1 = new Rectangle(315, 37, 4, 19);
            whiteRectangle1.setArcHeight(5);
            whiteRectangle1.setArcWidth(5);
            whiteRectangle1.setFill(Color.WHITE);
            whiteRectangle1.setStroke(Color.BLACK);

            Rectangle whiteRectangle2 = new Rectangle(315, 62, 4, 19);
            whiteRectangle2.setArcHeight(5);
            whiteRectangle2.setArcWidth(5);
            whiteRectangle2.setFill(Color.WHITE);
            whiteRectangle2.setStroke(Color.BLACK);

            Rectangle whiteRectangle3 = new Rectangle(315, 86, 4, 19);
            whiteRectangle3.setArcHeight(5);
            whiteRectangle3.setArcWidth(5);
            whiteRectangle3.setFill(Color.WHITE);
            whiteRectangle3.setStroke(Color.BLACK);

            // Rectángulos en la parte inferior
            Rectangle bottomRectangle1 = new Rectangle(341, 146, 20, 13);
            bottomRectangle1.setArcHeight(5);
            bottomRectangle1.setArcWidth(5);
            bottomRectangle1.setFill(Color.web("#6f7b86"));
            bottomRectangle1.setStroke(Color.BLACK);
            bottomRectangle1.setEffect(new DropShadow());

            Rectangle bottomRectangle2 = new Rectangle(342, 175, 20, 13);
            bottomRectangle2.setArcHeight(5);
            bottomRectangle2.setArcWidth(5);
            bottomRectangle2.setFill(Color.web("#6f7b86"));
            bottomRectangle2.setStroke(Color.BLACK);
            bottomRectangle2.setEffect(new DropShadow());

            Rectangle bottomRectangle3 = new Rectangle(341, 204, 20, 13);
            bottomRectangle3.setArcHeight(5);
            bottomRectangle3.setArcWidth(5);
            bottomRectangle3.setFill(Color.web("#6f7b86"));
            bottomRectangle3.setStroke(Color.BLACK);
            bottomRectangle3.setEffect(new DropShadow());

            Rectangle bottomRectangle4 = new Rectangle(341, 236, 20, 13);
            bottomRectangle4.setArcHeight(5);
            bottomRectangle4.setArcWidth(5);
            bottomRectangle4.setFill(Color.web("#6f7b86"));
            bottomRectangle4.setStroke(Color.BLACK);
            bottomRectangle4.setEffect(new DropShadow());

            Rectangle bottomRectangle5 = new Rectangle(341, 269, 20, 13);
            bottomRectangle5.setArcHeight(5);
            bottomRectangle5.setArcWidth(5);
            bottomRectangle5.setFill(Color.web("#6f7b86"));
            bottomRectangle5.setStroke(Color.BLACK);
            bottomRectangle5.setEffect(new DropShadow());
            Rectangle bottomRectangle6 = new Rectangle(274, 269, 20, 13);
            bottomRectangle6.setArcHeight(5);
            bottomRectangle6.setArcWidth(5);
            bottomRectangle6.setFill(Color.web("#6f7b86"));
            bottomRectangle6.setStroke(Color.BLACK);
            bottomRectangle6.setEffect(new DropShadow());

            Rectangle bottomRectangle7 = new Rectangle(274, 236, 20, 13);
            bottomRectangle7.setArcHeight(5);
            bottomRectangle7.setArcWidth(5);
            bottomRectangle7.setFill(Color.web("#6f7b86"));
            bottomRectangle7.setStroke(Color.BLACK);
            bottomRectangle7.setEffect(new DropShadow());

            Rectangle bottomRectangle8 = new Rectangle(274, 204, 20, 13);
            bottomRectangle8.setArcHeight(5);
            bottomRectangle8.setArcWidth(5);
            bottomRectangle8.setFill(Color.web("#6f7b86"));
            bottomRectangle8.setStroke(Color.BLACK);
            bottomRectangle8.setEffect(new DropShadow());

            Rectangle bottomRectangle9 = new Rectangle(275, 175, 20, 13);
            bottomRectangle9.setArcHeight(5);
            bottomRectangle9.setArcWidth(5);
            bottomRectangle9.setFill(Color.web("#6f7b86"));
            bottomRectangle9.setStroke(Color.BLACK);
            bottomRectangle9.setEffect(new DropShadow());

            Rectangle bottomRectangle10 = new Rectangle(274, 146, 20, 13);
            bottomRectangle10.setArcHeight(5);
            bottomRectangle10.setArcWidth(5);
            bottomRectangle10.setFill(Color.web("#6f7b86"));
            bottomRectangle10.setStroke(Color.BLACK);
            bottomRectangle10.setEffect(new DropShadow());

            // Rectángulo final
            Rectangle finalRectangle = new Rectangle(304, 292, 28, 13);
            finalRectangle.setArcHeight(5);
            finalRectangle.setArcWidth(5);
            finalRectangle.setFill(Color.web("#5e6973"));
            finalRectangle.setStroke(Color.BLACK);
            finalRectangle.setEffect(new DropShadow());

            // Agregar todos los elementos al pane
            group.getChildren().addAll(
                    polygon1, polygon2, polygon3, polygon4, polygon5,
                    rectangle1, rectangle2, rectangle3, rectangle4,
                    rectangle5, rectangle6, rectangle7,
                    shadowRectangle1, shadowRectangle2, shadowRectangle3, shadowRectangle4,
                    circle1, circle2,
                    additionalRectangle1, additionalRectangle2,
                    whiteRectangle1, whiteRectangle2, whiteRectangle3,
                    bottomRectangle1, bottomRectangle2, bottomRectangle3,
                    bottomRectangle4, bottomRectangle5, bottomRectangle6,
                    bottomRectangle7, bottomRectangle8, bottomRectangle9, bottomRectangle10,
                    finalRectangle
            );

            return group;
        }
    }

    public static class Submarine {
        private double x;
        private double y;
        private boolean isHorizontal;
        private double weight;
        private double height;

        public Submarine(double x, double y, boolean isHorizontal, double weight, double height) {
            this.x = x;
            this.y = y;
            this.isHorizontal = isHorizontal;
            this.weight = weight;
            this.height = height;

            SubmarineDrawer(x, y, isHorizontal);
        }

        public double getX() {
            return x;
        }
        public double getY() {
            return y;
        }
        public boolean isHorizontal() {
            return isHorizontal;
        }

        public double getWeight() {
            return weight;
        }
        public double getHeight() {
            return height;
        }

        public void setHeight(double height) {
            this.height = height;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }


        public void setX(double x) {
            this.x = x;
        }
        public void setY(double y) {
            this.y = y;
        }
        public void setHorizontal(boolean isHorizontal) {
            this.isHorizontal = isHorizontal;
        }

        public Group SubmarineDrawer(double x, double y, boolean isHorizontal) {
            // Primer AnchorPane
            Pane pane = new Pane();
            Group group = new Group();

            // Ellipse 1
            Ellipse ellipse1 = new Ellipse(100.0, 167.0, 24.0, 129.0);
            ellipse1.setFill(Color.web("#585b5e"));
            ellipse1.setStroke(Color.BLACK);

            // Polygon 1
            Polygon polygon1 = new Polygon(
                    -2.8, -29.0,
                    18.8, -29.0,
                    6.8, 22.8
            );
            polygon1.setLayoutX(92.0);
            polygon1.setLayoutY(302.0);
            polygon1.setFill(Color.web("#878f96"));
            polygon1.setStroke(Color.BLACK);

            // Ellipse 2
            Ellipse ellipse2 = new Ellipse(100.0, 167.0, 21.0, 124.0);
            ellipse2.setFill(Color.web("#878f96"));
            ellipse2.setStroke(Color.BLACK);

            // Rectangle 1
            Rectangle rectangle1 = new Rectangle(91.0, 280.0, 18.0, 11.0);
            rectangle1.setFill(Color.web("#595959"));
            rectangle1.setArcWidth(5.0);
            rectangle1.setArcHeight(5.0);
            rectangle1.setStroke(Color.BLACK);

            // Circle 1
            Circle circle1 = new Circle(100.0, 90.0, 11.0);
            circle1.setFill(Color.web("#969a9e"));
            circle1.setStroke(Color.BLACK);
            circle1.setEffect(new Bloom());

            // Circle 2
            Circle circle2 = new Circle(91.0, 114.0, 5.0);
            circle2.setFill(Color.web("#969a9e"));
            circle2.setStroke(Color.BLACK);
            circle2.setEffect(new Bloom());

            // Circle 3
            Circle circle3 = new Circle(110.0, 114.0, 5.0);
            circle3.setFill(Color.web("#969a9e"));
            circle3.setStroke(Color.BLACK);
            circle3.setEffect(new Bloom());

            // Rectangle 2
            Rectangle rectangle2 = new Rectangle(95.0, 129.0, 10.0, 113.0);
            rectangle2.setFill(Color.web("#616569"));
            rectangle2.setArcWidth(5.0);
            rectangle2.setArcHeight(5.0);
            rectangle2.setEffect(new DropShadow());
            rectangle2.setStroke(Color.BLACK);

            // Rectangle 3
            Rectangle rectangle3 = new Rectangle(86.0, 192.0, 29.0, 36.0);
            rectangle3.setFill(Color.web("#313336"));
            rectangle3.setArcWidth(5.0);
            rectangle3.setArcHeight(5.0);
            rectangle3.setEffect(new DropShadow());
            rectangle3.setStroke(Color.BLACK);

            // Circle 4
            Circle circle4 = new Circle(86.0, 134.0, 5.0);
            circle4.setFill(Color.web("#969a9e"));
            circle4.setStroke(Color.BLACK);
            circle4.setEffect(new Bloom());

            // Circle 5
            Circle circle5 = new Circle(114.0, 134.0, 5.0);
            circle5.setFill(Color.web("#969a9e"));
            circle5.setStroke(Color.BLACK);
            circle5.setEffect(new Bloom());

            // Polygon 2
            Polygon polygon2 = new Polygon(
                    -55.5, 21.0,
                    -21.0, 21.0,
                    -55.0, 8.0
            );
            polygon2.setLayoutX(159.0);
            polygon2.setLayoutY(284.0);
            polygon2.setFill(Color.web("#4d5359"));
            polygon2.setStroke(Color.BLACK);

            // Polygon 3
            Polygon polygon3 = new Polygon(
                    -75.8, 11.0,
                    -107.0, 11.0,
                    -78.2, -1.8
            );
            polygon3.setLayoutX(171.0);
            polygon3.setLayoutY(293.0);
            polygon3.setFill(Color.web("#4d5359"));
            polygon3.setStroke(Color.BLACK);

            // Rectangle 4
            Rectangle rectangle4 = new Rectangle(89.0, 196.0, 22.0, 28.0);
            rectangle4.setFill(Color.web("#c4cbd7"));
            rectangle4.setArcWidth(5.0);
            rectangle4.setArcHeight(5.0);
            rectangle4.setEffect(new DropShadow());
            rectangle4.setStroke(Color.BLACK);

            // Rectangle 5
            Rectangle rectangle5 = new Rectangle(91.0, 252.0, 18.0, 10.0);
            rectangle5.setFill(Color.web("#b4c6d7"));
            rectangle5.setStroke(Color.BLACK);

            // Rectangle 6
            Rectangle rectangle6 = new Rectangle(91.0, 268.0, 18.0, 10.0);
            rectangle6.setFill(Color.web("#b4c6d7"));
            rectangle6.setStroke(Color.BLACK);

            // Rectangle 7
            Rectangle rectangle7 = new Rectangle(84.0, 153.0, 5.0, 28.0);
            rectangle7.setFill(Color.web("#616569"));
            rectangle7.setArcWidth(5.0);
            rectangle7.setArcHeight(5.0);
            rectangle7.setEffect(new DropShadow());
            rectangle7.setStroke(Color.BLACK);

            // Rectangle 8
            Rectangle rectangle8 = new Rectangle(112.0, 153.0, 5.0, 28.0);
            rectangle8.setFill(Color.web("#616569"));
            rectangle8.setArcWidth(5.0);
            rectangle8.setArcHeight(5.0);
            rectangle8.setEffect(new DropShadow());
            rectangle8.setStroke(Color.BLACK);


            // Adding all elements to the group
            group.getChildren().addAll(
                    ellipse1, polygon1, ellipse2, rectangle1, circle1, circle2, circle3,
                    rectangle2, rectangle3, circle4, circle5, polygon2, polygon3, rectangle4,
                    rectangle5, rectangle6, rectangle7, rectangle8
            );

            //OE CON ESTO SE PUEDE ALARGAR EN X O Y Y TAMBIEN SE PUEDE MOVER COMO UN GRUPO
            group.setScaleX(0.5); // Escala 1.5 veces en el eje X
            group.setScaleY(0.4);
            group.setLayoutX(100); // Mover el grupo en el eje X
            group.setLayoutY(200); // Mover el grupo en el eje Y

            return group;
        }
    }
    public class Destroyer {

        private double x;
        private double y;
        private boolean isHorizontal;
        private double weight;
        private double height;

        public Destroyer(double x, double y, boolean isHorizontal, double weight, double height) {
            this.x = x;
            this.y = y;
            this.isHorizontal = isHorizontal;
            this.weight = weight;
            this.height = height;

            DestroyerDrawer(x, y, isHorizontal);
        }

        public double getX() {
            return x;
        }
        public double getY() {
            return y;
        }
        public boolean isHorizontal() {
            return isHorizontal;
        }

        public double getWeight() {
            return weight;
        }
        public double getHeight() {
            return height;
        }

        public void setHeight(double height) {
            this.height = height;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }

        public void setX(double x) {
            this.x = x;
        }
        public void setY(double y) {
            this.y = y;
        }
        public void setHorizontal(boolean isHorizontal) {
            this.isHorizontal = isHorizontal;
        }

        public Group DestroyerDrawer(double x, double y, boolean isHorizontal){
            // Polygons
            Pane pane = new Pane();
            Group group = new Group();

            // Polígono 1
            Polygon polygon1 = new Polygon();
            polygon1.getPoints().addAll(-20.199996948242188, -59.59999084472656,
                    19.000015258789062, -59.59999084472656,
                    1.3999786376953125, 122.0);
            polygon1.setFill(Color.web("#215484"));
            polygon1.setLayoutX(101.0);
            polygon1.setLayoutY(162.0);
            polygon1.setStroke(Color.BLACK);
            polygon1.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);

            // Polígono 2
            Polygon polygon2 = new Polygon();
            polygon2.getPoints().addAll(-30.199996948242188, 28.400009155273438,
                    9.000015258789062, 28.400009155273438,
                    -11.799972534179688, -66.00001525878906);
            polygon2.setFill(Color.web("#215484"));
            polygon2.setLayoutX(111.0);
            polygon2.setLayoutY(74.0);
            polygon2.setStroke(Color.BLACK);
            polygon2.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);

            // Elipse
            Ellipse ellipse = new Ellipse();
            ellipse.setFill(Color.web("#215484"));
            ellipse.setLayoutX(101.0);
            ellipse.setLayoutY(167.0);
            ellipse.setRadiusX(20.0);
            ellipse.setRadiusY(110.0);
            ellipse.setStroke(Color.BLACK);
            ellipse.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);

            // Rectángulo 1
            Rectangle rectangle1 = new Rectangle();
            rectangle1.setArcHeight(5.0);
            rectangle1.setArcWidth(5.0);
            rectangle1.setFill(Color.web("#215484"));
            rectangle1.setHeight(21.0);
            rectangle1.setLayoutX(85.0);
            rectangle1.setLayoutY(57.0);
            rectangle1.setStroke(Color.BLACK);
            rectangle1.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
            rectangle1.setWidth(31.0);

            // Polígono 3
            Polygon polygon3 = new Polygon();
            polygon3.getPoints().addAll(2.0000152587890625, 28.400009155273438,
                    22.800003051757812, 128.0,
                    11.0, -76.59999084472656);
            polygon3.setFill(Color.web("#215484"));
            polygon3.setLayoutX(70.0);
            polygon3.setLayoutY(168.0);
            polygon3.setStroke(Color.BLACK);
            polygon3.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
            polygon3.setEffect(new InnerShadow());

            // Polígono 4
            Polygon polygon4 = new Polygon();
            polygon4.getPoints().addAll(50.0, 14.199996948242188,
                    30.800003051757812, 118.19998168945312,
                    40.0, -87.40000915527344);
            polygon4.setFill(Color.web("#215484"));
            polygon4.setLayoutX(80.0);
            polygon4.setLayoutY(178.0);
            polygon4.setStroke(Color.BLACK);
            polygon4.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
            polygon4.setEffect(new InnerShadow());

            // Rectángulo 2
            Rectangle rectangle2 = new Rectangle();
            rectangle2.setArcHeight(5.0);
            rectangle2.setArcWidth(5.0);
            rectangle2.setFill(Color.web("#6798c7"));
            rectangle2.setHeight(73.0);
            rectangle2.setLayoutX(93.0);
            rectangle2.setLayoutY(91.0);
            rectangle2.setStroke(Color.BLACK);
            rectangle2.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
            rectangle2.setWidth(18.0);
            rectangle2.setEffect(new DropShadow());

            // Rectángulo 3
            Rectangle rectangle3 = new Rectangle();
            rectangle3.setArcHeight(5.0);
            rectangle3.setArcWidth(5.0);
            rectangle3.setFill(Color.web("#3f85c7"));
            rectangle3.setHeight(36.0);
            rectangle3.setLayoutX(99.0);
            rectangle3.setLayoutY(91.0);
            rectangle3.setStroke(Color.BLACK);
            rectangle3.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
            rectangle3.setWidth(5.0);

            // Rectángulo 4
            Rectangle rectangle4 = new Rectangle();
            rectangle4.setArcHeight(5.0);
            rectangle4.setArcWidth(5.0);
            rectangle4.setFill(Color.web("#6798c7"));
            rectangle4.setHeight(10.0);
            rectangle4.setLayoutX(91.0);
            rectangle4.setLayoutY(97.0);
            rectangle4.setStroke(Color.BLACK);
            rectangle4.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
            rectangle4.setWidth(21.0);

            // Rectángulo 5
            Rectangle rectangle5 = new Rectangle();
            rectangle5.setArcHeight(5.0);
            rectangle5.setArcWidth(5.0);
            rectangle5.setFill(Color.web("#6798c7"));
            rectangle5.setHeight(11.0);
            rectangle5.setLayoutX(91.0);
            rectangle5.setLayoutY(254.0);
            rectangle5.setStroke(Color.BLACK);
            rectangle5.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
            rectangle5.setWidth(22.0);
            rectangle5.setEffect(new DropShadow());

            // Círculo 1
            Circle circle1 = new Circle();
            circle1.setFill(Color.web("#b5c2cf"));
            circle1.setLayoutX(102.0);
            circle1.setLayoutY(194.0);
            circle1.setRadius(14.0);
            circle1.setStroke(Color.BLACK);
            circle1.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);

            // Círculo 2
            Circle circle2 = new Circle();
            circle2.setFill(Color.web("#6b869e"));
            circle2.setLayoutX(102.0);
            circle2.setLayoutY(194.0);
            circle2.setRadius(11.0);
            circle2.setStroke(Color.BLACK);
            circle2.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
            circle2.setEffect(new InnerShadow());

            // Rectángulo 6
            Rectangle rectangle6 = new Rectangle();
            rectangle6.setArcHeight(5.0);
            rectangle6.setArcWidth(5.0);
            rectangle6.setFill(Color.web("#234f79"));
            rectangle6.setHeight(28.0);
            rectangle6.setLayoutX(100.0);
            rectangle6.setLayoutY(270.0);
            rectangle6.setStroke(Color.BLACK);
            rectangle6.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
            rectangle6.setWidth(5.0);

            // Rectángulo 7
            Rectangle rectangle7 = new Rectangle();
            rectangle7.setArcHeight(5.0);
            rectangle7.setArcWidth(5.0);
            rectangle7.setFill(Color.web("#3f85c7"));
            rectangle7.setHeight(36.0);
            rectangle7.setLayoutX(100.0);
            rectangle7.setLayoutY(149.0);
            rectangle7.setStroke(Color.BLACK);
            rectangle7.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
            rectangle7.setWidth(5.0);

            // Rectángulo 8
            Rectangle rectangle8 = new Rectangle();
            rectangle8.setArcHeight(5.0);
            rectangle8.setArcWidth(5.0);
            rectangle8.setFill(Color.web("#6798c7"));
            rectangle8.setHeight(22.0);
            rectangle8.setLayoutX(91.0);
            rectangle8.setLayoutY(207.0);
            rectangle8.setStroke(Color.BLACK);
            rectangle8.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
            rectangle8.setWidth(22.0);
            rectangle8.setEffect(new DropShadow());

            // Rectángulo 9
            Rectangle rectangle9 = new Rectangle();
            rectangle9.setArcHeight(5.0);
            rectangle9.setArcWidth(5.0);
            rectangle9.setFill(Color.web("#3f85c7"));
            rectangle9.setHeight(36.0);
            rectangle9.setLayoutX(99.0);
            rectangle9.setLayoutY(200.0);
            rectangle9.setStroke(Color.BLACK);
            rectangle9.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
            rectangle9.setWidth(5.0);

            // Círculo 3
            Circle circle3 = new Circle();
            circle3.setFill(Color.web("#3f85c7"));
            circle3.setLayoutX(125.0);
            circle3.setLayoutY(163.0);
            circle3.setRadius(5.0);
            circle3.setStroke(Color.BLACK);
            circle3.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);

            // Rectángulo 10
            Rectangle rectangle10 = new Rectangle();
            rectangle10.setArcHeight(5.0);
            rectangle10.setArcWidth(5.0);
            rectangle10.setFill(Color.web("#234f79"));
            rectangle10.setHeight(12.0);
            rectangle10.setLayoutX(91.0);
            rectangle10.setLayoutY(258.0);
            rectangle10.setStroke(Color.BLACK);
            rectangle10.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
            rectangle10.setWidth(22.0);

            // Rectángulo 11
            Rectangle rectangle11 = new Rectangle();
            rectangle11.setArcHeight(5.0);
            rectangle11.setArcWidth(5.0);
            rectangle11.setFill(Color.web("#3f85c7"));
            rectangle11.setHeight(6.0);
            rectangle11.setLayoutX(101.0);
            rectangle11.setLayoutY(233.0);
            rectangle11.setStroke(Color.BLACK);
            rectangle11.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
            rectangle11.setWidth(5.0);

            group.getChildren().addAll(polygon1, polygon2, ellipse, rectangle1, polygon3, polygon4,
                    rectangle2, rectangle3, rectangle4, rectangle5, circle1, circle2,
                    rectangle6, rectangle7, rectangle8, rectangle9, rectangle10, rectangle11);

            return group;
        }
    }


    public class Caravel {

        private double x;
        private double y;
        private boolean isHorizontal;
        private double weight;
        private double height;

        public Caravel(double x, double y, boolean isHorizontal, double weight, double height) {
            this.x = x;
            this.y = y;
            this.isHorizontal = isHorizontal;
            this.weight = weight;
            this.height = height;

            CaravelDrawer(x, y, isHorizontal);
        }


        public double getX() {
            return x;
        }
        public double getY() {
            return y;
        }
        public boolean isHorizontal() {
            return isHorizontal;
        }

        public double getWeight() {
            return weight;
        }
        public double getHeight() {
            return height;
        }

        public void setHeight(double height) {
            this.height = height;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }


        public void setX(double x) {
            this.x = x;
        }
        public void setY(double y) {
            this.y = y;
        }
        public void setHorizontal(boolean isHorizontal) {
            this.isHorizontal = isHorizontal;
        }

        public Group CaravelDrawer(double x, double y, boolean isHorizontal){
            // Crear un Group para agregar las formas
            Group group = new Group();
            Pane pane = new Pane();

            // Elipse exterior
            Ellipse ellipse1 = new Ellipse(94.0, 176.0, 39.0, 115.0);
            ellipse1.setFill(Color.web("#906d6d"));
            ellipse1.setStroke(Color.BLACK);
            ellipse1.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);

            // Elipse interior con sombra
            Ellipse ellipse2 = new Ellipse(95.0, 176.0, 33.0, 110.0);
            ellipse2.setFill(Color.WHITE);
            ellipse2.setStroke(Color.BLACK);
            ellipse2.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
            InnerShadow innerShadow = new InnerShadow();
            ellipse2.setEffect(innerShadow);

            // Rectángulo con sombra
            Rectangle rectangle1 = new Rectangle(78.0, 88.0, 35.0, 200.0);
            rectangle1.setArcHeight(5.0);
            rectangle1.setArcWidth(5.0);
            rectangle1.setFill(Color.web("#ccd1d7"));
            rectangle1.setStroke(Color.BLACK);
            rectangle1.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
            DropShadow dropShadow1 = new DropShadow();
            rectangle1.setEffect(dropShadow1);

            // Rectángulo pequeño
            Rectangle rectangle2 = new Rectangle(87.0, 145.0, 18.0, 21.0);
            rectangle2.setArcHeight(5.0);
            rectangle2.setArcWidth(5.0);
            rectangle2.setStroke(Color.BLACK);
            rectangle2.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);

            // Rectángulo mediano con sombra
            Rectangle rectangle3 = new Rectangle(86.0, 188.0, 18.0, 65.0);
            rectangle3.setArcHeight(5.0);
            rectangle3.setArcWidth(5.0);
            rectangle3.setStroke(Color.BLACK);
            rectangle3.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
            DropShadow dropShadow2 = new DropShadow();
            rectangle3.setEffect(dropShadow2);

            // Rectángulo pequeño vertical
            Rectangle rectangle4 = new Rectangle(94.0, 270.0, 5.0, 36.0);
            rectangle4.setArcHeight(5.0);
            rectangle4.setArcWidth(5.0);
            rectangle4.setStroke(Color.BLACK);
            rectangle4.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);

            // Rectángulo blanco
            Rectangle rectangle5 = new Rectangle(75.0, 149.0, 42.0, 13.0);
            rectangle5.setArcHeight(5.0);
            rectangle5.setArcWidth(5.0);
            rectangle5.setFill(Color.WHITE);
            rectangle5.setStroke(Color.BLACK);
            rectangle5.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);

            // Círculo gris con Bloom
            Circle circle1 = new Circle(96.0, 117.0, 14.0);
            circle1.setFill(Color.web("#969a9e"));
            circle1.setStroke(Color.BLACK);
            circle1.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
            Bloom bloom1 = new Bloom();
            circle1.setEffect(bloom1);

            // Círculo más pequeño con Bloom
            Circle circle2 = new Circle(96.0, 117.0, 11.0);
            circle2.setFill(Color.web("#495663"));
            circle2.setStroke(Color.BLACK);
            circle2.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
            Bloom bloom2 = new Bloom();
            circle2.setEffect(bloom2);

            // Agregar las formas al Group
            group.getChildren().addAll(ellipse1, ellipse2, rectangle1, rectangle2, rectangle3, rectangle4, rectangle5, circle1, circle2);

            if(!isHorizontal){
                group.setRotate(-90);
            } else{
                group.setRotate(90);
            }

            group.setScaleX(0.6); // Estira el grupo hacia los lados (aumenta el ancho).
            group.setScaleY(0.3);

            group.setLayoutX(x);
            group.setLayoutY(y);

            return group;

        }
    }

}
