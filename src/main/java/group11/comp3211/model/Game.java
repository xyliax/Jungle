package group11.comp3211.model;

import group11.comp3211.common.exceptions.LogicException;
import lombok.Getter;
import lombok.Setter;

import java.io.*;

@Getter
@Setter
public final class Game implements Serializable {
	private static final int ROW_NUM = 9;
	private static final int COL_NUM = 7;
	private final Terrain[][] terrain;
	private boolean running;
	private Player playerX;
	private Player playerY;
	private Player currentPlayer;

	public Game() {
		setRunning(false);
		setPlayerX(null);
		setPlayerY(null);
		setCurrentPlayer(null);
		terrain = new Terrain[ROW_NUM][COL_NUM];
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
