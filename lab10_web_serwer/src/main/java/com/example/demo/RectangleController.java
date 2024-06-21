package com.example.demo;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RectangleController
{
    List<Rectangle> rectangles = new ArrayList<>();

    @GetMapping("/rectangle")
    public Rectangle getRectangle()
    {
        Rectangle rectangle = new Rectangle(25, 25, 100, 100, "red");
        return rectangle;
    }

    //dodaje prostokąt o określonych w kodzie parametrach
    @PostMapping("/addRectangle")
    public int addRectangle() {
        Rectangle rectangle = new Rectangle(20,10, 100,213, "red");
        rectangles.add(rectangle);

        return rectangles.size();
    }

    //zwróci listę prostokątów zmapowaną na JSON.
    @GetMapping("rectangles")
    public List<Rectangle> getRectangles()
    {
        return rectangles;
    }

    @GetMapping("/svg")
    public String getSvg()
    {
        StringBuilder svgBuilder = new StringBuilder();
        svgBuilder.append("<svg width=\"800\" height=\"600\" xmlns=\"http://www.w3.org/2000/svg\">");

        for (Rectangle rect : rectangles) {
            svgBuilder.append(String.format(
                    "<rect x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" fill=\"%s\" />",
                    rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight(), rect.getColor()
            ));
        }

        svgBuilder.append("</svg>");
        return svgBuilder.toString();
    }

    // Metoda zwracająca prostokąt z listy na podanym indeksie
    @GetMapping("/{index}")
    public Rectangle GET(@PathVariable int index)
    {
        if (index > 0)
        {
            return rectangles.get(index);
        }
        else
        {
            throw new IndexOutOfBoundsException(index + "out of bounds");
        }
    }

    // Metoda mdyfikującą istniejący na liście pod tym indeksem prostokąt na prostokąt przekazany argumentem
    @PutMapping("/{index}")
    public Rectangle PUT(@PathVariable int index, @RequestBody Rectangle updatedRectangle)
    {
        if (index > 0)
        {
            rectangles.set(index, updatedRectangle);
            return updatedRectangle;
        }
        else
        {
            throw new IndexOutOfBoundsException(index + "out of bounds");
        }
    }

    // Metoda usuwająca prostokąt z listy na podanym indeksie
    @DeleteMapping("/{index}")
    public Rectangle DELETE(@PathVariable int index)
    {
        if (index > 0)
        {
           return rectangles.remove(index);
        }
        else
        {
            throw new IndexOutOfBoundsException(index + "out of bounds");
        }
    }

}
