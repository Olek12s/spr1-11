public class Main {
    public static void main(String[] args) {
        Vec2 p1 = new Vec2(10, 10);
        Vec2 p2 = new Vec2(100, 10);
        Segment segment = new Segment(p1, p2);

        Style style = new Style("none", "black", 2.0);
        Polygon square = Polygon.square(segment, style);

        SvgScene scene = new SvgScene();
        scene.addShape(square);
        scene.save("output.html");

        System.out.println("Square: " + square.toSvg(""));

        Shape myPolygon = new Polygon(new Vec2[] { new Vec2(50, 80), new Vec2(110, 110), new Vec2(80, 80) }, null);
        Shape myEllipse = new Ellipse(new Vec2(50, 50), 20, 10, null);

        Shape filledPolygon = new SolidFillShapeDecorator(myPolygon, "red");
        Shape filledEllipse = new SolidFillShapeDecorator(myEllipse, "blue");

        System.out.println("polygon: " + myPolygon.toSvg(""));
        System.out.println("ellipse: " + myEllipse.toSvg(""));

        Shape myStrokedPolygon = new StrokeShapeDecorator(filledPolygon, "black", 2.0);
        Shape myStrokedEllipse = new StrokeShapeDecorator(filledEllipse, "black", 2.0);

        System.out.println("stroked polygon: " + myStrokedPolygon.toSvg(""));
        System.out.println("stroked ellipse: " + myStrokedEllipse.toSvg(""));

        Shape myTransformedPolygon = new TransformationDecorator.Builder(myStrokedPolygon)
                .translate(new Vec2(10, 20))
                .rotate(45, new Vec2(0, 0))
                .scale(new Vec2(1.5, 1.5))
                .build();
        System.out.println("transformed stroked polygon: " +  myTransformedPolygon.toSvg(""));

        Shape myShadowedPolygon = new DropShadowDecorator(myTransformedPolygon);

        System.out.println("transformed and shadowed stroked polygon: " +  myShadowedPolygon.toSvg(""));
    }
}