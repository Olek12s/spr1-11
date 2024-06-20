public class Polygon implements Shape
{
    private Vec2[] points;
    private Style style;

    public Polygon(Vec2[] points, Style style)  // głęboka kopia
    {
        //super(style); zamieniamy na pole style
        this.points = new Vec2[points.length];
        for (int i = 0; i < points.length; i++)
        {
            this.points[i] = new Vec2(points[i].x, points[i].y);
        }
        if (style == null)
        {
            this.style = new Style("transparent", "black", 1D);
        }
        else
        {
            this.style = style;
        }
    }

    public static Polygon square(Segment segment, Style style)
    {
        double length = segment.getLength();
        Segment[] perpendicularSegments = Segment.getTwoPerpendicularSegments(segment, length);

        Vec2 p1 = segment.getBeggining();
        Vec2 p2 = segment.getEnd();
        Vec2 p3 = perpendicularSegments[0].getEnd();
        Vec2 p4 = perpendicularSegments[1].getEnd();

        return new Polygon(new Vec2[] { p1, p2, p3, p4 }, style);
    }

    @Override
    public String toSvg(String args)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("<Polygon points=\"");
        for (Vec2 point : points)
        {
            sb.append(point.x);
            sb.append(",");
            sb.append(point.y);
            sb.append(" ");
        }
        sb.append("\" ");
        sb.append(args);
        sb.append(" style=\"");
        sb.append(style.toSvg());

        return sb.toString();
    }
}
