package com.ospe.jail;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Visitor_IO {

	static Scanner read = new Scanner(System.in);
	public static final int YEAR = 2019;
	
	public void leer(HashMap<String, Visitor> Visitors) {
		System.out.println("\nInsert visitor Id: ");
		String id = read.nextLine();
		if (Visitors.containsKey(id)) {
			System.out.println(Visitors.get(id));

		} else {
			System.out.println("There is no visitor registered with that Id.");
		}

	}
	
	public void leerListado(HashMap<String, Visitor> Visitors) {
        for (Visitor v : Visitors.values()) {
            System.out.print(v.getId_visitor() + ", " + v.getNombre() + " " + v.getApellidos() + "\n");
        }
    }
	
	public void crear(HashMap<String, Visitor> Visitors) {
		Visitor v = new Visitor();
		System.out.println("\nInsert new visitor DNI: ");
		v.setDNI(read.nextLine());

		System.out.println("\nInsert new visitor name: ");
		v.setNombre(read.nextLine());

		System.out.println("\nInsert new visitor last names: ");
		v.setApellidos(read.nextLine());

		System.out.println("\nInsert new visitor birth date (d�a/mes/a�o): ");
		v.setF_nac(read.nextLine());

		System.out.println("\nInsert new visitor nationality: ");
		v.setNacionalidad(read.nextLine());

		System.out.println("\nInsert new visitor sex (h/m): ");
		v.setSexo(read.nextLine());

		System.out.println("\nInsert new visitor height (cm): ");
		v.setAltura(read.nextInt());

		System.out.println("\nInsert new visitor weight (kg): ");
		v.setPeso(read.nextDouble());

		System.out.println("\nInsert new visitor Id: ");
		v.setId_visitor(read.nextLine());;

		System.out.println("\nInsert new visitor's prisoner: ");
		v.setPreso_visitado(read.nextInt());

		System.out.println("\nInsert new visitor date (year/month/day): ");
		v.setFecha_visita(read.nextLine());

		System.out.println("\nInsert new visitor's visit hour : ");
		v.setHora_visita(read.nextLine());

		System.out.println("\nInsert new visitor's relation with the prisoner: ");
		v.setRelacion_preso(read.nextLine());
		
		System.out.println("\nInsert new number of visitors: ");
		v.setNumero_visitantes(read.nextInt());
		
		System.out.println("\nInsert new visitor applicant: ");
		v.setSolicitante_visita(read.nextLine());

		Visitors.put(v.getId_visitor(), v);
	}
	
	public void editar(HashMap<String, Visitor> Visitors) {
		Visitor v = new Visitor();
		System.out.println("\nInsert Id of the visitor you want to edit: ");
		String id = read.nextLine();
		if (Visitors.containsKey(id)) {
			v = Visitors.get(id);
			System.out.println("\nWhich field would you like to change?" + "\nPlease make a selection:\n" + "\n1.DNI"
					+ "\n2.Nombre" + "\n3.Apellidos" + "\n4.Fecha de nacimiento" + "\n5.Nacionalidad" + "\n6.Sexo"
					+ "\n7.Altura" + "\n8.Peso" + "\n9.Id visitante" + "\n10.Preso visitado"
					+ "\n11.Fecha de visita" + "\n12.Hora de visita" + "\n13.Relacion con el preso" + "\n14.Numero de visitantes"
					+ "\n15.Solicitante de la visita" + "\n0.Exit");
			int choice = read.nextInt();
			read.nextLine();
			switch (choice) {
			case 0:
				System.out.println("End of editor.");
				break;
			case 1:
				System.out.println("\nInsert visitor's new DNI: ");
				v.setDNI(read.nextLine());
				break;
			case 2:
				System.out.println("\nInsert visitor's new name: ");
				v.setNombre(read.nextLine());
				break;
			case 3:
				System.out.println("\nInsert visitor's new last names: ");
				v.setApellidos(read.nextLine());
				break;
			case 4:
				System.out.println("\nInsert visitor's new birth date (d�a/mes/a�o): ");
				v.setF_nac(read.nextLine());
				break;
			case 5:
				System.out.println("\nInsert visitor's new nationality: ");
				v.setNacionalidad(read.nextLine());
				break;
			case 6:
				System.out.println("\nInsert visitor's new sex (h/m): ");
				v.setSexo(read.nextLine());
				break;
			case 7:
				System.out.println("\nInsert visitor's new height (cm): ");
				v.setAltura(read.nextInt());
				break;
			case 8:
				System.out.println("\nInsert visitor's new weight (kg): ");
				v.setPeso(read.nextDouble());
				break;
			case 9:
				System.out.println("\nInsert visitor's new Id: ");
				v.setId_visitor(read.nextLine());
				break;
			case 10:
				System.out.println("\nInsert visitor's new prisoner visited: ");
				v.setPreso_visitado(read.nextInt());
				break;
			case 11:
				System.out.println("\nInsert visitor's new date (year/month/day): ");
				v.setFecha_visita(read.nextLine());
				break;
			case 12:
				System.out.println("\nInsert visitor's new visit hour: ");
				v.setHora_visita(read.nextLine());
				break;
			case 13:
				System.out.println("\nInsert visitor's new relation with the prisoner: ");
				v.setRelacion_preso(read.nextLine());
				break;
			case 14:
				System.out.println("\nInsert visitor's new number of visitors: ");
				v.setNumero_visitantes(read.nextInt());
				break;
			case 15:
				System.out.println("\nInsert visitor's new applicant: ");
				v.setSolicitante_visita(read.nextLine());
				break;
			default:
				System.out.println("That is not a valid selection.");
				break;
			}
			Visitors.remove(id);// aunque de normal se reescribe, si cambia el id de funcionario quedarian los dos, asi
									// que hay que borrar el antiguo
			Visitors.put(v.getId_visitor(), v);
		} else {
			System.out.println("There is no visitor registered with that Id.");
		}

	}
	
	public static int[] getTheDate(String s) {
		int[] date = new int[3];
		String dayS = Character.toString(s.charAt(0)) + Character.toString(s.charAt(1));
		String monthS = Character.toString(s.charAt(3)) + Character.toString(s.charAt(4));
		String yearS = Character.toString(s.charAt(6)) + Character.toString(s.charAt(7))
				+ Character.toString(s.charAt(8)) + Character.toString(s.charAt(9));
		try {
			date[0] = Integer.parseInt(dayS);
			date[1] = Integer.parseInt(monthS);
			date[2] = Integer.parseInt(yearS);
		} catch (NumberFormatException e) {
			System.out.println("Invalid date format");
		}

		return date;
	}
	
	public void borrar(HashMap<String, Visitor> Visitors) {
		System.out.println("\nInsert id of the visitor you want to delete: ");
		String id = read.nextLine();
		if (Visitors.containsKey(id)) {
			Visitors.remove(id);
			System.out.println("Visitor deleted");
		} else {
			System.out.println("There is no visitor with that Id");
		}
	}
	
	public static void realizarConsulta(HashMap<String, Visitor> Visitors) {

		HashMap<String, Visitor> Query = new HashMap<String, Visitor>(Visitors);
		ArrayList<String> deleteos = new ArrayList<String>();
		int exit = 1;
		while (exit == 1) {
			try {
				System.out.println("\nIn which field would you like to put the restriction?"
						+ "\nPlease make a selection:\n" + "\n1.Search by DNI" + "\n2.Search by name"
						+ "\n3.Search by age" + "\n4.Search by nationality" + "\n5.Search by height"
						+ "\n6.Search by weight" + "\n7.Search by visitor Id" + "\n8.Search by prisoner visited"
						+ "\n9.Search by visit date" + "\n10.Search by visit hour" + "\n11.Search by relation with the prisoner" + "\n12.Search by number of visitors"
						+ "\n13.Search by visit applicant" + "\n0.Exit");
				int choice = read.nextInt();
				read.nextLine();
				switch (choice) {

				case 0:
					System.out.println("End of query.");
					break;
				case 1:
					System.out.println("\nInsert DNI: ");
					String dni = read.nextLine();
					for (Visitor v : Query.values()) {
						if (!v.getDNI().equals(dni)) {
							deleteos.add(v.getId_visitor());
						}
					}
					break;
				case 2:
					System.out.println("\nInsert name: ");
					String name = read.nextLine();
					for (Visitor v : Query.values()) {
						if (!v.getNombre().equals(name)) {
							deleteos.add(v.getId_visitor());
						}
					}
					break;
				case 3:
					System.out.println("\nInsert minimum age(years): ");
					int minAge = read.nextInt();
					System.out.println("\nInsert maximum age(years): ");
					int maxAge = read.nextInt();
					for (Visitor v : Query.values()) {
						int age = (YEAR - ((getTheDate(v.getF_nac()))[2]));
						if ((age < minAge) || (age > maxAge)) {
							deleteos.add(v.getId_visitor());
						}
					}
					break;
				case 4:
					System.out.println("\nInsert nationality: ");
					String nat = read.nextLine();
					for (Visitor v : Query.values()) {
						if (!v.getNacionalidad().equals(nat)) {
							deleteos.add(v.getId_visitor());
						}
					}
					break;
				case 5:
					System.out.println("\nInsert minimum height(cm): ");
					int minHeight = read.nextInt();
					System.out.println("\nInsert maximum height(cm): ");
					int maxHeight = read.nextInt();
					for (Visitor v : Query.values()) {
						if ((v.getAltura() < minHeight) || (v.getAltura() > maxHeight)) {
							deleteos.add(v.getId_visitor());
						}
					}
					break;
				case 6:
					System.out.println("\nInsert minimum weight(kg): ");
					String minW = read.nextLine();
					System.out.println("\nInsert maximum weight(kg): ");
					String maxW = read.nextLine();
					double minWeight = Double.parseDouble(minW);
					double maxWeight = Double.parseDouble(maxW);
					for (Visitor v : Query.values()) {
						if ((v.getPeso() < minWeight) || (v.getPeso() > maxWeight)) {
							deleteos.add(v.getId_visitor());
						}
					}
					break;
				case 7:
					System.out.println("\nInsert Id visitor: ");
					String idv = read.nextLine();
					for (Visitor v : Query.values()) {
						if (!v.getId_visitor().equals(idv)) {
							deleteos.add(v.getId_visitor());
						}
					}
					break;
				case 8:
					System.out.println("\nInsert prisoner visited: ");
					int prv = read.nextInt();
					for (Visitor v : Query.values()) {
						if (v.getPreso_visitado() != prv) {
							deleteos.add(v.getId_visitor());
						}
					}
					break;
				case 9:
					System.out.println("\nInsert minimum visit month: ");
					int minMonth = read.nextInt();
					System.out.println("\nInsert maximum visit month: ");
					int maxMonth = read.nextInt();
					for (Visitor v : Query.values()) {
						String s = v.getFecha_visita();
						int month = Integer.parseInt(Character.toString(s.charAt(3)) + Character.toString(s.charAt(4)));
						if ((month < minMonth) || (month >= maxMonth)) {
							deleteos.add(v.getId_visitor());
						}
					}
					break;
				case 10:
					System.out.println("\nInsert minimum visit hour: ");
					int minHour = read.nextInt();
					System.out.println("\nInsert maximum visit hour: ");
					int maxHour = read.nextInt();
					for (Visitor v : Query.values()) {
						String s = v.getHora_visita();
						int hour = Integer.parseInt(Character.toString(s.charAt(0)) + Character.toString(s.charAt(1)));
						if ((hour < minHour) || (hour >= maxHour)) {
							deleteos.add(v.getId_visitor());
						}
					}
					break;
				case 11:
					System.out.println("\nInsert relation with the prisoner: ");
					String rep = read.nextLine();
					for (Visitor v : Query.values()) {
						if (!v.getRelacion_preso().equals(rep)) {
							deleteos.add(v.getId_visitor());
						}
					}
					break;
				case 12:
					System.out.println("\nInsert number of visitors: ");
					int numv = read.nextInt();
					for (Visitor v : Query.values()) {
						if (v.getNumero_visitantes() != numv) {
							deleteos.add(v.getId_visitor());
						}
					}
					break;
				case 13:
					System.out.println("\nInsert visitor applicant: ");
					String vapp = read.nextLine();
					for (Visitor v : Query.values()) {
						if (!v.getSolicitante_visita().equals(vapp)) {
							deleteos.add(v.getId_visitor());
						}
					}
					break;
				default:
					System.out.println("That is not a valid selection.");
					break;
				}
				for (int i = 0; i < deleteos.size(); i++) {
					Query.remove(deleteos.get(i));
				}
				deleteos.clear();
				System.out.println("Do you like to add another restriction?(yes(1)/ no(0))");
				exit = read.nextInt();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		System.out.println("Do you like to visulize(1) or export it to CSV(2)?");
		int opt = read.nextInt();
		if (opt == 1) {
			System.out.println("\n");
			System.out.println(Query);
		}else if (opt == 2) {
			exportCSV(Query);System.out.println("\nConsulta guardada.");
		}
	}
	
	public static void exportCSV(HashMap<String, Visitor> query) {
		read.nextLine();
		System.out.println("Name your query: ");
		String nameCSV = read.nextLine();
        String archCSV = "src/com/ospe/jail/" + nameCSV + ".csv";
        try {
            FileWriter writer = new FileWriter(archCSV);
            writer.write("DNI;NOMBRE;APELLIDOS;F_NAC;NACIONALIDAD;SEXO;ALTURA(cm);PESO(kg);ID_VISIT;PRESO_VISIT;FECHA_VISIT(dd,mm,aaa);HORA_VISIT(hh,mm,ss);RELACION_PRESO;NUM_VISIT;SOLICITANTE_VISITA\n");
            for (Visitor v : query.values()) {
                writer.write(v.toCSV(';'));
            }
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
	
	
	
}