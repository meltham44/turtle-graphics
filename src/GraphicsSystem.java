import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JFrame;

import uk.ac.leedsbeckett.oop.LBUGraphics;

public class GraphicsSystem extends LBUGraphics
{
	public static GraphicsSystem gs;
	BufferedImage usersimage = null;
	public ArrayList<String> commands = new ArrayList<String>();
	boolean savecommand = true;

    public GraphicsSystem()
    {
        JFrame MainFrame = new JFrame();                //create a frame to display the turtle panel on
        MainFrame.setLayout(new FlowLayout());      //not strictly necessary
        MainFrame.add(this);                                        //"this" is this object that extends turtle graphics so we are adding a turtle graphics panel to the frame
        MainFrame.pack();                                           //set the frame to a size we can see
        MainFrame.setVisible(true);                         //now display it
        turnLeft();
    }

    
    public void processCommand(String command)      //this method must be provided because LBUGraphics will call it when it's JTextField is used
    {
    	String commandarray[] = command.split(" "); //array index 0 should be command and then 1 be parameter
    	String method = commandarray[0];
    	String parameter = null;
    	
    	System.out.println("You entered: " + command);
    	
    	if (commandarray.length == 2) { //if a parameter is specified store it
    		parameter = commandarray[1];
    	}
    	
    	method = method.toLowerCase();
    	
    	try {
	        switch (method) {
		        case "penup":
		        	penUp();
		        	break;
		        case "pendown":
		        	penDown();
		        	break;
		        case "turnleft":
		        	if (parameter == null)
		        		turnLeft();
		        	else
		        		turnLeft(parameter);
		        	break;
		        case "turnright":
		        	if (parameter == null)
		        		turnRight();
		        	else
		        		turnRight(parameter);
		        	break;
		        case "forward":
		        	if (parameter == null) {
		        		throw new NullPointerException();
		        	}
		        	if (Integer.parseInt(parameter) <= 200 && Integer.parseInt(parameter) >= 1) {
		        		forward(parameter);
		        		boundaryCheck();
		        	} else {
		        		System.out.println("You must specify a numeric parameter between and including 1 and 200!");
		        	}
		        	break;
		        case "backward":
		        	if (parameter == null) {
		        		throw new NullPointerException();
		        	}
		        	if (Integer.parseInt(parameter) <= 200 && Integer.parseInt(parameter) >= 1) {
		        		forward(-Integer.parseInt(parameter));
		        		boundaryCheck();
		        	} else {
		        		System.out.println("You must specify a numeric parameter between and including 1 and 200!");
		        	}
		        	break;
		        case "black":
		        	setPenColour(Color.black);
		        	break;
		        case "green":
		        	setPenColour(Color.green);
		        	break;
		        case "red":
		        	setPenColour(Color.red);
		        	break;
		        case "white":
		        	setPenColour(Color.white);
		        	break;
		        case "reset":
		        	reset();
		        	turnLeft();
		        	break;
		        case "clear":
		        	savecommand = false;
		        	clear();
		        	break;
		        case "about":
		        	about();
		        	break;
		        case "save":
		        	usersimage = getBufferedImage();
		        	System.out.println("Image saved!");
		        	break;
		        case "load":
		        	if (usersimage == null) {
		        		System.out.println("There isn't a saved image to load!");
		        		break;
		        	}
		        	setBufferedImage(usersimage);
		        	System.out.println("Image loaded!");
		        	break;
		        case "loadcommands":
		        	try {
		        		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("commands.txt")));
		        	    String line;
		        	    while ((line = br.readLine()) != null) {
		        	        processCommand(line);
		        	    }
		        	    br.close();
		        	    br = null;
		        	} catch(IOException e) {System.out.println("File load error. " + e);}
		        	savecommand = false; //makes sure that this command isn't saved so commands aren't stuck loading themselves when loaded
		        	break;
		        default:
		        	System.out.println("That command is invalid!");
	        }
    	}
    	catch(NullPointerException e) {System.out.println("You haven't specified a parameter!");}
    	catch(NumberFormatException e) {System.out.println("You must specify a numeric parameter!");}
    
    	if (savecommand == true) {
    		commands.add(command);
    		saveCommand();
    	}
    	
    	savecommand = true;
    }
    
    public void boundaryCheck()
    {
    	if (getxPos() >= 1000) {
			setxPos(999);
		}
		if (getxPos() <= -1) {
			setxPos(0);
		}
		if (getyPos() >= 400) {
			setyPos(399);
		}
		if (getyPos() <= -1) {
			setyPos(0);
		}
    }
    
    public void saveCommand()
    {
    	try {
	    	FileWriter writer = new FileWriter("commands.txt");
	    	
	    	for(String str: commands) {
	    		  writer.write(str + System.lineSeparator());
	    	}
	    	
	    	writer.close();
    	}catch (IOException e) {System.out.println("File write error." + e);}
    }
}