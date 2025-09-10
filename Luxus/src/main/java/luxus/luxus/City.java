import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
    

public class City
{
    public City(int width, int height)
    {
        if(width < 1) {
            throw new IllegalArgumentException(
                        "Width must be positive: " +
                        width);
        }
        if(height < 1) {
            throw new IllegalArgumentException(
                        "Height must be positive: " +
                        height);
        }
        this.width = width;
        this.height = height;
        items = new LinkedList<Object>();
    }
    

    public City()
    {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
    

    public Iterator getItems()
    {
        return items.iterator();
    }

  
    public void addItem(Object item)
    {
        if(items.contains(item)) {
            throw new IllegalArgumentException(
                item + " already recorded in the city.");
        }
        items.add(item);
    }


    public void removeItem(Object item)
    {
        if(!items.remove(item)) {
            throw new IllegalArgumentException(
                        item + " is not in the city.");
        }
    }

    public String toString()
    {
        return "City size " + width + " by " + height;
    }
    

    public int getWidth()
    {
        return width;
    }
    

    public int getHeight()
    {
        return height;
    }
}
