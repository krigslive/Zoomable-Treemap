import java.util.ArrayList;

public class JsonObject {
	private String name;
	private ArrayList<JsonObject> children = new ArrayList<JsonObject>(); 
	private double color;
    private double value; 
    private JsonObject Parent;
    
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    
    public ArrayList<JsonObject> getChildren() {
    	if(children.size() != 0)
    	{return children;}
    	else
    	{return null;}
    }
    public void addChildren(JsonObject child) { children.add(child);}  
    
    
    public double getColor() {return color;}
    public void setColor(double color) {this.color = color;}
    
    
    public double getValue() {return value;}
    public void setValue(double value) {this.value = value;}
    
    public JsonObject getParent() {return Parent;}
    public void setParent(JsonObject _Parent) {this.Parent = _Parent;}
}
