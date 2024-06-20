public class DropShadowDecorator extends ShapeDecorator
{
    private static int filterIndex = 0;
    private final int index;

    public DropShadowDecorator(Shape decoratedShape)
    {
        super(decoratedShape);
        this.index = filterIndex++;
        SvgScene.getSvgSceneInstance().addDef(String.format(
                "\t<filter id=\"f%d\" x=\"-100%%\" y=\"-100%%\" width=\"300%%\" height=\"300%%\">\n" +
                        "\t\t<feOffset result=\"offOut\" in=\"SourceAlpha\" dx=\"5\" dy=\"5\" />\n" +
                        "\t\t<feGaussianBlur result=\"blurOut\" in=\"offOut\" stdDeviation=\"5\" />\n" +
                        "\t\t<feBlend in=\"SourceGraphic\" in2=\"blurOut\" mode=\"normal\" />\n" +
                        "\t</filter>", index));
    }

    @Override
    public String toSvg(String args)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("filter=\"url(#f");
        sb.append(index);
        sb.append(")\" ");
        sb.append(args);

        return decoratedShape.toSvg(sb.toString());
    }
}
