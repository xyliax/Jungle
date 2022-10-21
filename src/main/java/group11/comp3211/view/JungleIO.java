package group11.comp3211.view;

import lombok.Getter;
import lombok.Setter;

import java.io.Console;

enum Color {
	BLACK(0), RED(1), GREEN(2), YELLOW(3), BLUE(4), MAGENTA(5), CYAN(6), GREY(7);
	final int value;

	Color(int value) {
		this.value = value;
	}
}

enum JCString {
	WELCOME_BANNER("""
			        888888     888     888     888b    888      .d8888b.      888          8888888888
			            "88b     888     888     8888b   888     d88P  Y88b     888          888
			             888     888     888     88888b  888     888    888     888          888
			             888     888     888     888Y88b 888     888            888          8888888
			             888     888     888     888 Y88b888     888  88888     888          888
			             888     888     888     888  Y88888     888    888     888          888
			             88P     Y88b. .d88P     888   Y8888     Y88b  d88P     888          888
			             888      "Y88888P"      888    Y888      "Y8888P88     88888888     8888888888
			           .d88P
			         .d88P"
			        888P"
			"""),
	START_MENU("""
					===================================================================================
					#                       MM    MM  EEEEEEE  NN   NN  UU   UU                       #
			   		#                       MMM  MMM  EE       NNN  NN  UU   UU                       #
			   		#                       MM MM MM  EEEEE    NN N NN  UU   UU                       #
			   		#                       MM    MM  EE       NN  NNN  UU   UU                       #
			   		#                       MM    MM  EEEEEEE  NN   NN   UUUUU                        #
					#==================================================================================
					#                                                                                 #
					#                                                                                 #
					#                                                                                 #
					#                                                                                 #
					#                                                                                 #
					#                                                                                 #
					===================================================================================
			""");
	final String string;

	JCString(String string) {
		this.string = string;
	}
}

@Getter
@Setter
public final class JungleIO extends Thread {
	private Console console;
	private String promptStr = ">>> ";

	private JungleIO() {
		this.console = System.console();
	}

	public static JungleIO getInstance() {
		return JunglePrinterHolder.JUNGLE_IO;
	}

	public void clearScreen() {
		if (console != null) System.out.print("\033c");
	}

	public void reset() {
		System.out.print("\033[0m");
	}

	public void setFront(Color color) {
		System.out.print("\033[3" + color.value + "m");
	}

	public void setBack(Color color) {
		System.out.print("\033[4" + color.value + "m");
	}

	public void showWelcomeAnimation() {
		clearScreen();
		setFront(Color.GREEN);
		setBack(Color.GREY);
		int chCount = 0;
		for (char character : JCString.WELCOME_BANNER.string.toCharArray()) {
			chCount++;
			if (character == '\n') {
				while (chCount++ < 100) System.out.print(" ");
				chCount = 0;
				try {
					sleep(20);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
			System.out.print(character);
		}
		reset();
	}

	public void showStartMenu() {

	}

	public String readLine() {
		return null;
	}

	private static final class JunglePrinterHolder {
		private static final JungleIO JUNGLE_IO = new JungleIO();
	}
}