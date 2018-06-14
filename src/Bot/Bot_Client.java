package Bot;

import AbiturKlassen.Client;
import Cards.Card;
import Protokoll.Protokoll;

public class Bot_Client extends Client {
	int credit;
	int player;
	int[] cardworths;
	int cardworth;
	int playerCount;
	int playerBet;

	public Bot_Client(String pServerIP, int pServerPort) {
		super(pServerIP, pServerPort);
		credit = 0;
		player = 0;
	}

	public void processMessage(String pMessage) {
		String[] splitMessage = pMessage.split(":");
		if (splitMessage[0].matches("-?\\d+")) {
			if (player == 0) {
				player = Integer.parseInt(splitMessage[0]);
			}
		}
		switch (splitMessage[0]) {
		case Protokoll.SC_GAMESTART:
			playerCount = Integer.parseInt(splitMessage[splitMessage.length - 2]);
			credit = Integer.parseInt(splitMessage[1]);
			cardworths = new int[playerCount];
			for(int i = 0; i < cardworths.length; i++) {
				cardworths[i] = 0;
			}
			this.set();
			break;

		case Protokoll.SC_CARD:
			int currentPlayer = Integer.parseInt(splitMessage[2]);
			cardworths[currentPlayer] = cardworths[currentPlayer] + cardworth;
			if (currentPlayer == player) {
				cardworth = getCardtype(splitMessage[1]);
				this.hit();
			}

			break;

		case Protokoll.SC_DEALERWIN:
			clearArray();

			break;

		case Protokoll.SC_LOSE:
			clearArray();

			break;

		case Protokoll.SC_WIN:
			clearArray();

			break;

		case Protokoll.SC_STAND:
			int lastPlayer = Integer.parseInt(splitMessage[1]);
			if (lastPlayer + 1 == player) {
				this.hit();
			}
			break;
		case Protokoll.SC_PAY:
			currentPlayer = Integer.parseInt(splitMessage[1]);
			playerBet++;
			if(playerBet == playerCount) {
				this.hit();
			}
		}
	}

	public void hit() {
		System.out.println("playerCount: " + playerCount);
		System.out.println("player: " + player);
		System.out.println("cardworths" + cardworths.length);
		if(cardworths[player] < 16) {
			send(Protokoll.CS_HIT);
		} else {
			this.stand();
		}
	}

	public void set() {
		int x = credit / 10;
		send(Protokoll.CS_PAY + ":" + x);
	}

	public void stand() {
		send(Protokoll.CS_STAND);
	}

	/*
	 * Die Inhalte des Arrays, der die Kartenwerte der Spieler beinhaltet, werden
	 * gel�scht.
	 */
	public void clearArray() {
		for (int j = 0; j < 5; j++) {
			cardworths[j] = 0;
		}
	}

	private int getCardtype(String card) {
		String[] cardComponent = card.split("_");
		if (cardComponent[1].equals("Koenig") || cardComponent[1].equals("Dame") || cardComponent[1].equals("Bube")) {
			return 10;
		} else if (cardComponent[1].equals("Ass")) {
			return 11;
		}
		return Integer.parseInt(cardComponent[1]);
	}
	
	private int transformCardStrings(String number) {
		int worth = 0;
		Card c = a.get(i);
		if (c.getType().equals("Koenig") || c.getType().equals("Dame") || c.getType().equals("Bube")
				|| c.getType().equals("Zehn")) {
			worth = worth + 10;
		} else if (c.getType().equals("Zwei")) {
			worth = worth + 2;
		} else if (c.getType().equals("Drei")) {
			worth = worth + 3;
		} else if (c.getType().equals("Vier")) {
			worth = worth + 4;
		} else if (c.getType().equals("Fuenf")) {
			worth = worth + 5;
		} else if (c.getType().equals("Sechs")) {
			worth = worth + 6;
		} else if (c.getType().equals("Sieben")) {
			worth = worth + 7;
		} else if (c.getType().equals("Acht")) {
			worth = worth + 8;
		} else if (c.getType().equals("Neun")) {
			worth = worth + 9;
		} else if (c.getType().equals("Ass")) {
			if (worth + 11 <= 21) {
				worth = worth + 11;
			} else {
				worth = worth + 1;
			}
	}
}
}
