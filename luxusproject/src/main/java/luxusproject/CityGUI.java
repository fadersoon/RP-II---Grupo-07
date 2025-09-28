package luxusproject;

import java.awt.*;
import javax.swing.*;
import java.util.List;
    

public class CityGUI extends JFrame implements Actor
{
    public static final int CITY_VIEW_WIDTH = 600;
    public static final int CITY_VIEW_HEIGHT = 600;
    private City city;
    private CityView cityView;
    

    public CityGUI(City city)
    {
        this.city = city;
        cityView = new CityView(city.getWidth(), city.getHeight());
        getContentPane().add(cityView);
        setTitle("Taxiville");
        setSize(CITY_VIEW_WIDTH, CITY_VIEW_HEIGHT);
        setVisible(true);
        cityView.preparePaint();
        cityView.repaint();
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the system default.
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                // Handle exception
            }
        }
    }

    public void act()
    {
        cityView.preparePaint();
        List<Item> items = city.getItems();
        for (Item item : items) {
            if (item instanceof DrawableItem) {
                DrawableItem drawable = (DrawableItem) item;
                Location location = drawable.getLocation();
                cityView.drawImage(location.getX(), location.getY(), drawable.getImage());
            }
        }
        cityView.repaint();
    }
    

    private class CityView extends JPanel
    {
        private final int VIEW_SCALING_FACTOR = 20;

        private int cityWidth, cityHeight;
        private int xScale, yScale;
        private Dimension size;
        private Graphics g;
        private Image cityImage;

   
        public CityView(int width, int height)
        {
            cityWidth = width;
            cityHeight = height;
            setBackground(Color.white);
            size = new Dimension(0, 0);
        }

 
        public Dimension getPreferredSize()
        {
            return new Dimension(cityWidth * VIEW_SCALING_FACTOR,
                                 cityHeight * VIEW_SCALING_FACTOR);
        }



        public void preparePaint()
        {
            if(!size.equals(getSize())) {  // if the size has changed...
                size = getSize();
                cityImage = cityView.createImage(size.width, size.height);
                g = cityImage.getGraphics();

                xScale = size.width / cityWidth;
                if(xScale < 1) {
                    xScale = VIEW_SCALING_FACTOR;
                }
                yScale = size.height / cityHeight;
                if(yScale < 1) {
                    yScale = VIEW_SCALING_FACTOR;
                }
            }
            // Fundo gradiente com tons de cinza mais neutros
            GradientPaint gp = new GradientPaint(0, 0, new Color(245, 245, 245), size.width, size.height, new Color(230, 230, 230));
            ((Graphics2D) g).setPaint(gp);
            g.fillRect(0, 0, size.width, size.height);

            // Linhas de grade ainda mais sutis
            g.setColor(new Color(210, 210, 210, 100)); // Cinza muito claro e mais transparente
            for(int i = 0, x = 0; x < size.width; i++, x = i * xScale) {
                g.drawLine(x, 0, x, size.height - 1);
            }
            for(int i = 0, y = 0; y < size.height; i++, y = i * yScale) {
                g.drawLine(0, y, size.width - 1, y);
            }

            // Borda mais suave ao redor da grade
            g.setColor(new Color(180, 180, 180)); // Cinza médio para a borda
            g.drawRect(0, 0, size.width - 1, size.height - 1);
        }



        public void drawImage(int x, int y, Image image)
        {
            g.drawImage(image, x * xScale + 1, y * yScale + 1,
                        xScale - 1, yScale - 1, this);
        }

   
        public void paintComponent(Graphics g)
        {
            if(cityImage != null) {
                Dimension currentSize = getSize();
                if(size.equals(currentSize)) {
                    g.drawImage(cityImage, 0, 0, null);
                }
                else {
                    // Rescale the previous image.
                    g.drawImage(cityImage, 0, 0, currentSize.width, currentSize.height, null);
                }
            }
        }
    }
}