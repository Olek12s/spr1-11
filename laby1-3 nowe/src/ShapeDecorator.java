public class ShapeDecorator implements Shape
{
    protected final Shape decoratedShape;

    public ShapeDecorator(Shape decoratedShape)
    {
        this.decoratedShape = decoratedShape;
    }

    @Override
    public String toSvg(String args)
    {
        return decoratedShape.toSvg(args);
    }
}
