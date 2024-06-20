public class Style
{
    public final String fillColor;
    public final String strokeColor;
    public final Double strokeWidth;

    public Style(String fillColor, String strokeColor, Double strokeWidth)
    {
        this.fillColor = fillColor;
        this.strokeColor = strokeColor;
        this.strokeWidth = strokeWidth;
    }

    public String toSvg()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("style = \"fill:");
        sb.append(fillColor);
        sb.append(";");
        sb.append("stroke:");
        sb.append(strokeColor);
        sb.append(";");
        sb.append("stroke-width:");
        sb.append(strokeWidth);
        sb.append("\" />");

        return sb.toString();
    }

}
