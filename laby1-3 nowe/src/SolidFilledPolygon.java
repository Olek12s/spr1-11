public class SolidFilledPolygon extends Polygon
{
    private String color;

    public SolidFilledPolygon(Vec2[] points, Style style, String color)
    {
        super(points, style);
        this.color = color;
    }

    @Override
    public String toSvg(String args)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("fill=\"");
        sb.append(color);
        sb.append("\" ");
        sb.append(args);
        return super.toSvg(sb.toString());
    }
}
