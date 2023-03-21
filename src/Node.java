import java.awt.*;

public class Node {
    public double topLeftX;
    public double topLeftY;
    public double bottomRightX;
    public double bottomRightY;

    public double level = 0;
    public double bodies = 0;

    public int limit = 4;
    public Node[] children = new Node[limit];

    public double mass;
    public Vector2 centerofMass;


    public Rectangle self;
    public boolean subdivided = false;

    public Node(Rectangle grect, double glevel){
        level = glevel;
        centerofMass = new Vector2(bottomRightX/2, bottomRightY/2);
        self = grect;
        topLeftX = self.getMinX();
        topLeftY = self.getMinY();
        bottomRightX = self.getMaxX();
        bottomRightY = self.getMaxY();
    }

    public void subdivide(){

        int x = (int) topLeftX;
        int y = (int) topLeftY;
        int h = (int) (bottomRightY - topLeftY) / 2;
        int w = (int) (bottomRightX - topLeftX) / 2;

        children[0] = new Node(new Rectangle(x,y,w,h),level+1); //Top Left
        children[1] = new Node(new Rectangle(x+w,y,w,h),level+1); //Top Right
        children[2] = new Node(new Rectangle(x,y+h,w,h),level+1); //Bottom Left
        children[3] = new Node(new Rectangle(x+w,y+h,w,h),level+1); //Bottom Right
    }

    public void updateNode(Particle part){
        mass += part.mass;
        centerofMass = new Vector2((part.getX()*mass + centerofMass.x*mass)/mass, (part.getY()*mass + centerofMass.y*mass)/mass);
    }

    public int insertPoint(Point p){
        if(!self.contains(p)){
            return 0;
        }
        if(level < 4){
            self.add(p);
            return 1;
        }
        if(!subdivided){
            subdivide();
        }
        for(Node section: children){
            section.insertPoint(p);
        }
        return 1;
    }

    public void displayNode(Graphics g){
        Graphics2D graphics = (Graphics2D)g;

        graphics.draw(self);
        for(int i =0; i<limit; i++){
            if(children[i] != null){
                System.out.println(i);
                children[i].displayNode(g);
            }
        }
    }



}
