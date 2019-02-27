package notSwing;

import java.util.Scanner;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Menu {

	boolean exit;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Menu menu = new Menu();
		menu.runMenu();

	}

	private void log_menu_eng() {

		System.out.println("+-----------------------------------+");
		System.out.println("|       Welcome,please log in       |");
		System.out.println("|          into the system          |");
		System.out.println("+-----------------------------------+");

	}

	private void log_menu_esp() {
		Scanner scesp = new Scanner(System.in);
		System.out.println("+-----------------------------------+");
		System.out.println("|  Bienvenido,por favor introduzca  |");
		System.out.println("|  sus credenciales en el sistema   |");
		System.out.println("+-----------------------------------+");
		System.out.println("\nIntroduzca el usuario: ");
		String us = scesp.nextLine();
		System.out.println("\nInserte la contrase�a: ");
		String passw = scesp.nextLine();
	}

	private void printHeadereng() {
		System.out.println("+-----------------------------------+");
		System.out.println("|       Welcome to Jail System      |");
		System.out.println("|        Security Project App       |");
		System.out.println("+-----------------------------------+");
	}

	private void printHeaderesp() {
		System.out.println("+-----------------------------------+");
		System.out.println("|       Bienvenido a Jail System    |");
		System.out.println("|        Security Project App       |");
		System.out.println("+-----------------------------------+");
	}

	private void printMenu() {
		System.out.println("\nPlease make a selection: ");
		System.out.println("\n1) Register Management");
		System.out.println("\n0) Exit");
	}

	private void printMenuPower() {
		System.out.println("\nPlease make a selection: ");
		System.out.println("\n1) Register Management");
		System.out.println("\n2) Stats");
		System.out.println("\n3) Export");
		System.out.println("\n4) Import");
		System.out.println("\n5) Report by email");
		System.out.println("\n0) Exit");
	}

//Ejecucion principal programa
	public void runMenu() {
		log_menu_eng();
		int ret = -1;
		while (ret == -1) {
			ret = login();
			if (ret == 1) {
				printHeadereng();
				while (!exit) {
					printMenuPower();
					int choice = getInput();
					performAction(choice);
				}

			} else if (ret == 0) {
				printHeadereng();
				while (!exit) {
					printMenu();
					int choice = getInput();
					performAction(choice);
				}
			}
		}
	}

	public int login() {
		JSONParser parser = new JSONParser();

		try {

			Object obj = parser.parse(new FileReader("src/notSwing/users.json"));

			JSONObject jsonObject = (JSONObject) obj;
			Scanner sceng = new Scanner(System.in);
			System.out.println("\nInsert user: ");
			String us = sceng.nextLine();
			if (jsonObject.containsKey(us)) {
				JSONObject obj2 = (JSONObject) jsonObject.get(us);
				System.out.println("\nInsert password: ");
				String passw = sceng.nextLine();
				String passtest = (String) obj2.get("Password");
				String power = (String) obj2.get("PowerUser");
				if (passtest.equals(passw) && power.equals("True")) {
					System.out.println("\nCorrect Login");

					return 1;
				} else if (passtest.equals(passw) && power.equals("False")) {
					System.out.println("\nCorrect Login");

					return 0;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("\nError login try again");
		return -1;

	}

	private int getInput() {
		Scanner sc = new Scanner(System.in);
		int choice = -1;
		while (choice < 0 || choice > 5) {
			try {
				System.out.println("\nEnter your choice:  ");
				choice = Integer.parseInt(sc.nextLine());

			} catch (NumberFormatException e) {
				System.out.println("Invalid selection. Please try again.");

			}
		}
		return choice;
	}

	private void performAction(int choice) {
		switch (choice) {

		case 0:
			exit = true;
			System.out.println("Thank you for using our application.");
			break;

		case 1:
			System.out.println("Option 1 Selected");
			break;

		case 2:
			System.out.println("Option 2 Selected");
			break;

		case 3:
			System.out.println("Option 3 Selected");
			break;

		case 4:
			System.out.println("Option 4 Selected");
			break;

		case 5:
			System.out.println("Option 5 Selected");
			break;

		default:
			System.out.println("An unknown error has ocurred.");
			break;
		}

	}
}