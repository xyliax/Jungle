package group11.comp3211.model;

import group11.comp3211.common.exceptions.LogicException;
import lombok.Getter;
import lombok.Setter;

import java.io.*;

@Getter
@Setter
public final class Game implements Serializable {
	private final PlayBoard playboard;
	private boolean running;
	private Player playerX;
	private Player playerY;
	private Player currentPlayer;

	public Game() {
		this.playboard = new PlayBoard();
		this.running = false;
		this.playerX = null;
		this.playerY = null;
		this.currentPlayer = null;
	}

	public void runTurn() throws LogicException {

	}

	public void saveToFile(String fileName) throws IOException {
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName));
		objectOutputStream.writeObject(this);
		objectOutputStream.close();
	}

	public Game loadFromFile(String fileName) throws IOException {
		ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(fileName));
		try {
			return (Game) objectInputStream.readObject();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}
