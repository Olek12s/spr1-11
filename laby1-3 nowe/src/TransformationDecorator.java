public class TransformationDecorator implements Shape
{
    private final Shape decoratedShape;
    private final boolean translate;
    private final Vec2 translateVector;
    private final boolean rotate;
    private final double rotateAngle;
    private final Vec2 rotateCenter;
    private final boolean scale;
    private final Vec2 scaleVector;

    private TransformationDecorator(Builder builder)
    {
        this.decoratedShape = builder.shape;
        this.translate = builder.translate;
        this.translateVector = builder.translateVector;
        this.rotate = builder.rotate;
        this.rotateAngle = builder.rotateAngle;
        this.rotateCenter = builder.rotateCenter;
        this.scale = builder.scale;
        this.scaleVector = builder.scaleVector;
    }

    @Override
    public String toSvg(String params)
    {
        StringBuilder transform = new StringBuilder();
        if (translate)
        {
            transform.append(String.format("translate(%f %f) ", translateVector.x, translateVector.y));
        }
        if (rotate)
        {
            transform.append(String.format("rotate(%f %f %f) ", rotateAngle, rotateCenter.x, rotateCenter.y));
        }
        if (scale)
        {
            transform.append(String.format("scale(%f %f) ", scaleVector.x, scaleVector.y));
        }
        String transformParams = String.format("transform=\"%s\" %s", transform.toString(), params);
        return decoratedShape.toSvg(transformParams);
    }

    public static class Builder
    {
        private Shape shape;
        private boolean translate = false;
        private Vec2 translateVector = new Vec2(0, 0);
        private boolean rotate = false;
        private double rotateAngle = 0;
        private Vec2 rotateCenter = new Vec2(0, 0);
        private boolean scale = false;
        private Vec2 scaleVector = new Vec2(1, 1);

        public Builder(Shape shape)
        {
            this.shape = shape;
        }

        public Builder translate(Vec2 translateVector)
        {
            this.translate = true;
            this.translateVector = translateVector;
            return this;
        }

        public Builder rotate(double rotateAngle, Vec2 rotateCenter)
        {
            this.rotate = true;
            this.rotateAngle = rotateAngle;
            this.rotateCenter = rotateCenter;
            return this;
        }

        public Builder scale(Vec2 scaleVector)
        {
            this.scale = true;
            this.scaleVector = scaleVector;
            return this;
        }

        public TransformationDecorator build()
        {
            return new TransformationDecorator(this);
        }
    }
}
