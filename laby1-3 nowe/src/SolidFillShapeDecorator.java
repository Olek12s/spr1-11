public class SolidFillShapeDecorator extends ShapeDecorator
{
    private final String color;

    public SolidFillShapeDecorator(Shape decoratedShape, String color)
    {
        super(decoratedShape);
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
        return decoratedShape.toSvg(sb.toString());
    }
}
