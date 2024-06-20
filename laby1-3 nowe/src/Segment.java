public class Segment
{
    private Vec2 beggining;
    private Vec2 end;

    public Vec2 getBeggining() {return beggining;}
    public Vec2 getEnd() {return end;}

    public Segment(Vec2 beggining, Vec2 end)
    {
        this.beggining = beggining;
        this.end = end;
    }

    public double getLength()
    {
        return Math.sqrt(Math.pow(end.x - beggining.x, 2) + Math.pow(end.y - beggining.y, 2));
    }

    public static Segment[] getTwoPerpendicularSegments (Segment segment, Vec2 point)
    {
        double dx = segment.getEnd().x - segment.getBeggining().x;
        double dy = segment.getEnd().y - segment.getBeggining().y;
        double length = segment.getLength();
        double nx = dx / length;
        double ny = dy / length;

        double px1 = -ny;
        double py1 = nx;
        double px2 = ny;
        double py2 = -nx;

        Vec2 end1 = new Vec2(point.x + px1 * length, point.y + py1 * length);
        Vec2 end2 = new Vec2(point.x + px2 * length, point.y + py2 * length);

        return new Segment[] {new Segment(point, end1), new Segment(point, end2)};
    }

    public static Segment[] getTwoPerpendicularSegments (Segment segment, double length)
    {
        double dx = segment.getEnd().x - segment.getBeggining().x;
        double dy = segment.getEnd().y - segment.getBeggining().y;
        double segmentLength = segment.getLength();
        double nx = dx / segmentLength;
        double ny = dy / segmentLength;

        double px = -ny;
        double py = nx;

        Vec2 point = new Vec2(segment.getBeggining().x, segment.getBeggining().y);
        Vec2 end1 = new Vec2(point.x + px * length, point.y + py * length);
        Vec2 end2 = new Vec2(point.x - px * length, point.y - py * length);

        return new Segment[] { new Segment(point, end1), new Segment(point, end2) };
    }

    public String toSvg()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("<line x1=\"");
        sb.append(beggining.x);
        sb.append("\"");
        sb.append(" y1=\"");
        sb.append(beggining.y);
        sb.append("\" x2=\"");
        sb.append(end.x);
        sb.append("\" y2=\"");
        sb.append(end.y);
        sb.append("\" />");
        return sb.toString();
    }
}
