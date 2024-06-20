public class StrokeShapeDecorator extends ShapeDecorator
{
    private final String color;
    private final double width;

    public StrokeShapeDecorator(Shape decoratedShape, String color, double width)
    {
        super(decoratedShape);
        this.color = color;
        this.width = width;
    }

    @Override
    public String toSvg(String params)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("stroke=\"");
        sb.append(color);
        sb.append("\" ");
        sb.append("stroke-width=\"");
        sb.append(width);
        sb.append("\" ");
        sb.append(params);

        return decoratedShape.toSvg(sb.toString());
    }
}
