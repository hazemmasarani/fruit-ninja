package mainControllers;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class XmlParser {

	GameState gameState = GameState.getGameState();
	
	public XmlParser() {  }
	
	public void saveGame() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try { builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) { e.printStackTrace(); }
		 
		//Build Document
		Document document = null;
		try { document = builder.parse(new File("Database.xml"));
		} catch (SAXException | IOException e) { e.printStackTrace(); }
		document.getDocumentElement().normalize();

		Element profileE = (Element)document.getElementsByTagName("profile").item(0);
		profileE.setAttribute("gameType", gameState.gameType );
		profileE.setAttribute("livesCounter", String.valueOf(gameState.livesCounter) );
		profileE.setAttribute("scoreCounter", String.valueOf(gameState.scoreCounter) );
		profileE.setAttribute("timeCounter", String.valueOf(gameState.timeCounter) );

		// create the x m l file
		//transform the DOM Object to an XML File
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(document);
			
			StreamResult streamResult = new StreamResult(new File("Database.xml"));
			//StreamResult streamResult = new StreamResult(System.out);

			transformer.transform(domSource, streamResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loadGame() {
		System.out.println("lol");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try { builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) { e.printStackTrace(); }
		 
		//Build Document
		Document document = null;
		try { document = builder.parse(new File("Database.xml"));
		} catch (SAXException | IOException e) { e.printStackTrace(); }
		document.getDocumentElement().normalize();

		Element profileE = (Element)document.getElementsByTagName("profile").item(0);
		gameState.gameType = profileE.getAttribute("gameType");
		gameState.livesCounter = Integer.valueOf( profileE.getAttribute("livesCounter") );
		gameState.scoreCounter = Integer.valueOf( profileE.getAttribute("scoreCounter") );
		gameState.timeCounter = Integer.valueOf( profileE.getAttribute("timeCounter") );
	}
	
	public boolean isNewHighScore() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try { builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) { e.printStackTrace(); }
		 
		//Build Document
		Document document = null;
		try { document = builder.parse(new File("Database.xml"));
		} catch (SAXException | IOException e) { e.printStackTrace(); }
		document.getDocumentElement().normalize();

		Element gameTypeBoardE = (Element)document.getElementsByTagName( gameState.gameType ).item(0);
		int topScore = Integer.valueOf( gameTypeBoardE.getAttribute("s0") );

		if( gameState.scoreCounter > topScore ) return true;
		else return false;
	}
	
	public void addToLeaderboard() {
		System.out.println("pop");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try { builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) { e.printStackTrace(); }
		 
		//Build Document
		Document document = null;
		try { document = builder.parse(new File("Database.xml"));
		} catch (SAXException | IOException e) { e.printStackTrace(); }
		document.getDocumentElement().normalize();

		int[] topScores = new int[5];
		Element gameTypeBoardE = (Element)document.getElementsByTagName( gameState.gameType ).item(0);
		for(int i=0; i<5 ;i++) topScores[i] = Integer.valueOf( gameTypeBoardE.getAttribute("s"+i) );

		for(int i=0; i<5 ;i++) {
			if( gameState.scoreCounter > topScores[i] ) {
				for(int j=4; j>i ;j--) topScores[j] = topScores[j-1];
				topScores[i] = gameState.scoreCounter;
				i=6;
			}
		}
		for(int i=0; i<5 ;i++) gameTypeBoardE.setAttribute( ("s"+i), String.valueOf( topScores[i] ) );
		
		// create the x m l file
		//transform the DOM Object to an XML File
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(document);
			
			StreamResult streamResult = new StreamResult(new File("Database.xml"));
			//StreamResult streamResult = new StreamResult(System.out);

			transformer.transform(domSource, streamResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String[] getLeaderboard() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try { builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) { e.printStackTrace(); }
		 
		//Build Document
		Document document = null;
		try { document = builder.parse(new File("Database.xml"));
		} catch (SAXException | IOException e) { e.printStackTrace(); }
		document.getDocumentElement().normalize();

		int[] classicScores = new int[5];
		Element classicE = (Element)document.getElementsByTagName( "classic" ).item(0);
		for(int i=0; i<5 ;i++) classicScores[i] = Integer.valueOf( classicE.getAttribute("s"+i) );
		
		int[] arcadeScores = new int[5];
		Element arcadeE = (Element)document.getElementsByTagName( "arcade" ).item(0);
		for(int i=0; i<5 ;i++) arcadeScores[i] = Integer.valueOf( arcadeE.getAttribute("s"+i) );

		String[] leaderBoard = new String[4];
		leaderBoard[0] = "CLASSIC";
		leaderBoard[1] = (1)+". "+classicScores[0]+"\n";
		for(int i=1; i<5 ;i++) leaderBoard[1] += (i+1)+". "+classicScores[i]+"\n";
		leaderBoard[2] = "ARCADE";
		leaderBoard[3] = (1)+". "+arcadeScores[0]+"\n";
		for(int i=1; i<5 ;i++) leaderBoard[3] += (i+1)+". "+arcadeScores[i]+"\n";
		
		return leaderBoard;
	}
}
