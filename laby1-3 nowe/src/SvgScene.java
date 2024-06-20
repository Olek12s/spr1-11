import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SvgScene
{
    private List<Shape> shapes = new ArrayList<>();
    private List<String> defs = new ArrayList<>();
    private static SvgScene svgSceneInstance = null;

    public static SvgScene getSvgSceneInstance()
    {
        if (svgSceneInstance == null)
        {
            svgSceneInstance = new SvgScene();
        }
            return svgSceneInstance;
    }

    public void addShape(Polygon poly) {shapes.add(poly);}
    public void addDef(String def) {defs.add(def);}

    public void save(String filePath)
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath)))
        {
            writer.write("<html><body><svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\">");
            writer.write("<defs>");
            for (String def : defs)
            {
                writer.write(def);
            }
            writer.write("</defs>");
            for (Shape shape : shapes)
            {
                writer.write(shape.toSvg(""));
            }
            writer.write("</svg></body></html>");
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
