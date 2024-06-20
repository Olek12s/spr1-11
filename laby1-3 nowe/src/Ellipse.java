public class Ellipse implements Shape
{
    private Vec2 centerOfEllipse;
    private double radiusX;
    private double radiusY;
    private Style style;

    public Ellipse(Vec2 centerOfEllipse, double radiusX, double radiusY, Style style)
    {
        //super(style); zamieniamy na pole style
        this.centerOfEllipse = centerOfEllipse;
        this.radiusX = radiusX;
        this.radiusY = radiusY;
        if (style == null)
        {
            this.style = new Style("transparent", "black", 1D);
        }
        else
        {
            this.style = style;
        }
    }

    @Override
    public String toSvg(String args)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("<ellipse rx=\"");
        sb.append(radiusX);
        sb.append("\" ");
        sb.append("ry=\"");
        sb.append(radiusY);
        sb.append("\" ");
        sb.append("cx=\"");
        sb.append(centerOfEllipse.x);
        sb.append("\" ");
        sb.append("cy=\"");
        sb.append(centerOfEllipse.y);
        sb.append("\" ");
        sb.append(args);
        sb.append(" style=\"");
        sb.append(style.toSvg());
        //sb.append("\" />");   klasa Style już to realizuje

        return sb.toString();
    }

}
