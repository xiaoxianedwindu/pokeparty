//HW3_104403016
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Formatter;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class PokeMaster {

	public static void main(String[] args) {
		//Main GUI
		PokeGUI gameFrame = new PokeGUI();
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setSize(450,650);
		gameFrame.setVisible(true);
	}
	
	public static class PokeGUI extends JFrame{
		//Parts of the GUI
		private final JLabel Charmander;
		private final JTextField Nickname;
		private final JButton GiveCandyButton;
		private final JButton SaveButton;
		private final JButton OpenGameButton;
		private JLabel EvoCount;
		private static int CandyCount = 0;
		private static int SaveCandyCount = 0;
		private int Evo;
		private int Evo1;
		private static Scanner input;
		public String monsterName = "monster";
		
		public PokeGUI(){
			//Title
			super("Gotta Catch Them All!");
			//sets layout format
			setLayout(new FlowLayout());
			//Pokemon Picture
			Icon CharPic = new ImageIcon(getClass().getResource("small.png"));
			
			Charmander = new JLabel();
			Charmander.setText("Charmander");
			Charmander.setIcon(CharPic);
			Charmander.setHorizontalTextPosition(SwingConstants.CENTER);
			Charmander.setVerticalTextPosition(SwingConstants.BOTTOM);
			Charmander.setToolTipText("Char Char!");
			add(Charmander);
			//Nickname for pokemon
			Nickname = new JTextField("Enter your Pokemon's nickname here");
			add(Nickname);
			TextFieldHandler handler = new TextFieldHandler();
			Nickname.addActionListener(handler);
			Nickname.setCaretPosition(SwingConstants.BOTTOM);
			//Give Candy Button
			Icon charCandy = new ImageIcon(getClass().getResource("firecandy.png"));
			GiveCandyButton = new JButton("Give Candy", charCandy);
			add(GiveCandyButton);
			ButtonHandler handler1 = new ButtonHandler();
			GiveCandyButton.addActionListener(handler1);
			GiveCandyButton.setActionCommand("1");
			//Evolution Counter
			EvoCount = new JLabel();
			String CandyCountText = Integer.toString(CandyCount); 
			EvoCount.setText(CandyCountText);
			add(EvoCount);
			//Save Button
			SaveButton = new JButton("Save");
			add(SaveButton);
			SaveButton.addActionListener(handler1);
			SaveButton.setActionCommand("2");
			OpenGameButton = new JButton("Open Save");
			add(OpenGameButton);
			OpenGameButton.addActionListener(handler1);
			OpenGameButton.setActionCommand("3");
			
			openData();
			readData();
			closeData();
			
			Evo = CharEv;
			System.out.println(CharEv);
			System.out.println(CharmPic);
			
			if (CandyCount != SaveCandyCount){
				CandyCount = SaveCandyCount;
				repaint();
			}
		}
		private String Pokename;
		public class TextFieldHandler implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent event){
				String name = "";
				if (event.getSource() == Nickname)
					name = java.lang.String.format("Nickname: %s", event.getActionCommand());
				System.out.println(event.getActionCommand());
				JOptionPane.showMessageDialog(null, name);
				Pokename = event.getActionCommand();
			}
		}
		public class ButtonHandler implements ActionListener{
			  @Override
			  public void actionPerformed (ActionEvent event){
				  int action = Integer.parseInt(event.getActionCommand());
				  switch(action){
				  case 1:
					  JOptionPane.showMessageDialog(PokeGUI.this, ("You gave candy!"));
					  CandyCount++;
					  //Updates Evolution Counter
					  System.out.println(CandyCount);
					  String CandyCountText = Integer.toString(CandyCount); 
					  EvoCount.setText(CandyCountText);
					  Icon CharmPic1 = new ImageIcon(getClass().getResource(CharmPic));
					  Icon CharzPic1 = new ImageIcon(getClass().getResource(CharzPic));
					  if (CandyCount>=Evo){
						  if (CandyCount == Evo){
						  JOptionPane.showMessageDialog(null,"What? Charmander is evolving!");
						  JOptionPane.showMessageDialog(null,"Congratulations! Your Charmander evolved into a Charmeleon.");
						  Charmander.setText("Charmeleon");
						  Charmander.setIcon(CharmPic1);
						  Charmander.setHorizontalTextPosition(SwingConstants.CENTER);
						  Charmander.setVerticalTextPosition(SwingConstants.BOTTOM);
						  Charmander.setToolTipText("Charm Charm!");
						  }
						  Evo1 = CharmEv;
						  
						if (CandyCount>=Evo1){
							if (CandyCount == Evo1){
							JOptionPane.showMessageDialog(null,"What? Charmeleon is evolving!");
							JOptionPane.showMessageDialog(null,"Congratulations! Your Charmeleon evolved into a Charizard!.");
							Charmander.setText("Charizard");
							Charmander.setIcon(CharzPic1);
							Charmander.setHorizontalTextPosition(SwingConstants.CENTER);
							Charmander.setVerticalTextPosition(SwingConstants.BOTTOM);
							Charmander.setToolTipText("Charizard!");
							Evo = CharmEv;
							JOptionPane.showMessageDialog(null,"You win!");
							}
						}
					  }  
					  break;
				  case 2:
					openSaveFile();
					addSaveFile();
					  break;
				  case 3:
					openSaveFileInput(); 
					readSaveFileInput();
					  break;
				  default:
					  break;
				  }
				  
			  }
		}
		public static void openData(){
			try {
				input = new Scanner(Paths.get("pokemon.txt"));
			} catch (IOException e) {
				System.err.println("Error opening Save. Terminating.");
				System.exit(1);
			}
		}
		
		private static String CharPic;
		private static int CharEv;
		private static String CharmPic;
		private static int CharmEv;
		private static String CharzPic;
		
		public static void readData(){
			while(input.hasNext()){
				CharPic = input.next();
				CharEv = Integer.parseInt(input.next());
				CharmPic = input.next();
				CharmEv = Integer.parseInt(input.next());
				CharzPic = input.next();	
			}
		}
		public static void closeData(){
			if (input != null)
				input.close();
		}
		
		public static ObjectOutputStream output;
		public static void openSaveFile(){
		      try{
		         output = new ObjectOutputStream(Files.newOutputStream(Paths.get("PokeSave.ser")));
		         System.out.println("Test: openSaveFile");
		      }
		      catch (IOException ioException){
		         System.err.println("Error opening file. Terminating.");
		         System.exit(1);
		      }		      
		} 

		public void addSaveFile(){
		     // Scanner input = new Scanner(System.in);
		      System.out.println("Test: openSaveFile0");
		         try{
		        	 System.out.println("Test: addSaveFile" + Pokename);
		        	 PokeSerializable save = new PokeSerializable(Pokename, monsterName , CandyCount );
		            // serialize record object into file
		            output.writeObject(save); 
		            System.out.println("Test: openSaveFile2");
		            output.close();
		         } 
		         catch (IOException ioException){
		            System.err.println("Error writing to file. Terminating.");
		         } 
		  }
		  
		 private static ObjectInputStream input1;
		 public static void openSaveFileInput()
		   {
		      try // open file
		      {
		         input1 = new ObjectInputStream(          
		            Files.newInputStream(Paths.get("PokeSave.ser")));
		      } 
		      catch (IOException ioException)
		      {
		         System.err.println("Error opening file.");
		         System.exit(1);
		      } 
		   }
		 public static void readSaveFileInput(){
		      System.out.println("readSaveFile test");

		      try{
		         while (true){
		        	PokeSerializable save = (PokeSerializable) input1.readObject();

		            // display record contents
		            System.out.printf("%-15s%-12s%-12d",  
		               save.getNickname(),save.getMonster(), save.getCandy());
		            SaveCandyCount = save.getCandy();
		         } 
		      }
		      catch (EOFException endOfFileException)
		      {
		         System.out.printf("%nNo more records%n");
		         input.close();
		      } 
		      catch (ClassNotFoundException classNotFoundException)
		      {
		         System.err.println("Invalid object type. Terminating.");
		      } 
		      catch (IOException ioException)
		      {
		         System.err.println("Error reading from file. Terminating.");
		      } 
		   }
	}
}
