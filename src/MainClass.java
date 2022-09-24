
public class MainClass {
	
	public static void main(String[] args)
    {
        GraphicsSystem.gs = new GraphicsSystem(); //create instance of class that extends LBUGraphics (could be separate class without main), gets out of static context
    }

}
