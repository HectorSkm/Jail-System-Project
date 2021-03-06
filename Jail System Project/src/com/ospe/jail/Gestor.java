package com.ospe.jail;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Scanner;
import java.util.Vector;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ospe.jail.IO.*;
import com.ospe.jail.Swing.MessageWindow;

public class Gestor extends Thread {
	private HashMap<Integer, Prisoner> prisoners = new HashMap<Integer, Prisoner>();
	private HashMap<Integer, Cell> cells = new HashMap<Integer, Cell>();
	private HashMap<Integer, Pavilion> pavilions = new HashMap<Integer, Pavilion>();
	private HashMap<String, CivilServant> civilServants = new HashMap<String, CivilServant>();
	private HashMap<String, Visitor> visitors = new HashMap<String, Visitor>();
	private int contFallos = 0;
	private int stop = 9;
	private boolean back = false;
	private static final String mainPath = "src/com/ospe/jail/";
	private static ArrayList<String> bc = new ArrayList<String>();
	public boolean importError=false;

	public HashMap<Integer, Prisoner> getPrisoners() {
		return prisoners;
	}

	public void setPrisoners(HashMap<Integer, Prisoner> prisoners) {
		this.prisoners = prisoners;
	}

	public HashMap<Integer, Cell> getCells() {
		return cells;
	}

	public void setCells(HashMap<Integer, Cell> cells) {
		this.cells = cells;
	}

	public HashMap<Integer, Pavilion> getPavilions() {
		return pavilions;
	}

	public void setPavilions(HashMap<Integer, Pavilion> pavilions) {
		this.pavilions = pavilions;
	}

	public HashMap<String, CivilServant> getCivilServants() {
		return civilServants;
	}

	public void setCivilServants(HashMap<String, CivilServant> civilServants) {
		this.civilServants = civilServants;
	}

	public HashMap<String, Visitor> getVisitors() {
		return visitors;
	}

	public void setVisitors(HashMap<String, Visitor> visitors) {
		this.visitors = visitors;
	}

	public boolean isBack() {
		return back;
	}

	public void setBack(boolean back) {
		this.back = back;
	}
	public void run() {
		Prisoner_IO pIO = Prisoner_IO.getInstance();
		Cell_IO cIO = new Cell_IO();
		Pavilion_IO paIO = new Pavilion_IO();
		CivilServant_IO csIO = new CivilServant_IO();
		Visitor_IO vIO = new Visitor_IO();
		Menu menu = new Menu();
		Gestor m = new Gestor();
		Scanner read = new Scanner(System.in);
		int loaded = 0;
		int ret = -1; // Rematar login con if else power , duplicar todo el codigo de la opcion 1 en
						// el else
		while (ret == -1) {
			ret = m.login();
			if (ret == 1) {
				while (loaded == 0) {
					System.out.println("Do you want to load the main database(1) or the backup files(2)?");
					int load = read.nextInt();
					if (load == 1) {
						m.JsonToHashPab(mainPath + "database/" + "prisondb" + ".json");
						m.JsonToHashCS(mainPath + "database/" + "civilservants" + ".json");
						m.JsonToHashV(mainPath + "database/" + "visitors" + ".json");
						loaded = 1;
					} else if (load == 2) {
						m.JsonToHashPab(mainPath + "database/backup/" + "prisondbBackup" + ".json");
						m.JsonToHashCS(mainPath + "database/backup/" + "civilservantsBackup" + ".json");
						m.JsonToHashV(mainPath + "database/backup/" + "visitorsBackup" + ".json");
						loaded = 1;
					} else {
						System.out.println("That is not a valid option.");
					}
				}
				do {
					m.setBack(false);
					menu.menuPrincipal();
					bc.add("Location: >>Login, >>Main Menu");
					System.out.println("\n" + bc);
					int choice = read.nextInt();
					switch (choice) {
					case 0:
						System.out.println("Do you want to save changes?(1=yes, 0=no)");
						int save = read.nextInt();
						if (save == 1) {
							m.HashToJson(0, "prisondb", "database/");
							m.HashToJson(1, "civilservants", "database/");
							m.HashToJson(2, "visitors", "database/");
							System.out.println("Changes saved.");
						}
						System.out.println("Do you want to update the backup copy?(1=yes, 0=no)");
						int backup = read.nextInt();
						if (backup == 1) {
							m.HashToJson(0, "prisondbBackup", "database/backup/");
							m.HashToJson(1, "civilservantsBackup", "database/backup/");
							m.HashToJson(2, "visitorsBackup", "database/backup/");
							System.out.println("Changes saved.");
						}
						System.out.println("\n\nThank you for using our application");
						break;
					case 1:
						boolean volver = false;
						do {
							volver = false;
							menu.seleccionTipoDato();
							bc.add(">>Register Management");
							System.out.println("\n" + bc);
							int dato = read.nextInt();
							do {
								switch (dato) {
								case 1:
									menu.gestorPresos();
									bc.add(">>Prisoners");
									System.out.println("\n" + bc);
									int opcionP = read.nextInt();
									switch (opcionP) {
									case 1:
										pIO.leerListado(m.getPrisoners());
										break;
									case 2:
										pIO.leer(m.getPrisoners());
										break;
									case 3:
										pIO.crear(m.getPrisoners(), m.getCells());
										break;
									case 4:
										pIO.editar(m.getPrisoners(), m.getCells());
										break;
									case 5:
										pIO.borrar(m.getPrisoners(), m.getCells());
										break;
									case 6:
										pIO.realizarConsulta(m.getPrisoners(), mainPath);
										break;
									case 7:
										System.out.println(
												"Do you want to visualize(1) or visualize and export as text file(2)?");
										int a = read.nextInt();
										read.nextLine();
										if (a == 1) {
											System.out.println(pIO.stats(m.getPrisoners()));
										} else if (a == 2) {
											System.out.println(pIO.stats(m.getPrisoners()));
											System.out.println("Insert the name of the file: ");
											String n = read.nextLine();
											m.saveTxt(n, pIO.stats(m.getPrisoners()));
										} else {
											System.out.println("That is not a valid option.");
										}
										break;
									case 0:
										volver = true;
										bc.remove((bc.size() - 1));
										break;

									}
									bc.remove((bc.size() - 1));
									break;

								case 2:
									menu.gestorFuncionarios();
									bc.add(">>Civil Servants");
									System.out.println("\n" + bc);
									int opcionF = read.nextInt();
									switch (opcionF) {
									case 1:
										csIO.leerListado(m.getCivilServants());
										break;
									case 2:
										csIO.leer(m.getCivilServants());
										break;
									case 3:
										csIO.crear(m.getCivilServants());
										break;
									case 4:
										csIO.editar(m.getCivilServants());
										break;
									case 5:
										csIO.borrar(m.getCivilServants());
										break;
									case 6:
										csIO.realizarConsulta(m.getCivilServants(), mainPath);
										break;
									case 7:
										System.out.println(
												"Do you want to visualize(1) or visualize and export as text file(2)?");
										int a = read.nextInt();
										read.nextLine();
										if (a == 1) {
											System.out.println(csIO.stats(m.getCivilServants()));
										} else if (a == 2) {
											System.out.println(csIO.stats(m.getCivilServants()));
											System.out.println("Insert the name of the file: ");
											String n = read.nextLine();
											m.saveTxt(n, csIO.stats(m.getCivilServants()));
										} else {
											System.out.println("That is not a valid option.");
										}
										break;
									case 0:
										volver = true;
										bc.remove((bc.size() - 1));
										break;
									}
									bc.remove((bc.size() - 1));
									break;

								case 3:
									menu.gestorVisitantes();
									bc.add(">>Visitors");
									System.out.println("\n" + bc);
									int opcionV = read.nextInt();
									switch (opcionV) {
									case 1:
										vIO.leerListado(m.getVisitors());
										break;
									case 2:
										vIO.leer(m.getVisitors());
										break;
									case 3:
										vIO.crear(m.getVisitors());
										break;
									case 4:
										vIO.editar(m.getVisitors());
										break;
									case 5:
										vIO.borrar(m.getVisitors());
										break;
									case 6:
										vIO.realizarConsulta(m.getVisitors(), mainPath);
										break;
									case 7:
										System.out.println(
												"Do you want to visualize(1) or visualize and export as text file(2)?");
										int a = read.nextInt();
										read.nextLine();
										if (a == 1) {
											System.out.println(vIO.stats(m.getVisitors()));
										} else if (a == 2) {
											System.out.println(vIO.stats(m.getVisitors()));
											System.out.println("Insert the name of the file: ");
											String n = read.nextLine();
											m.saveTxt(n, vIO.stats(m.getVisitors()));
										} else {
											System.out.println("That is not a valid option.");
										}
										break;
									case 0:
										volver = true;
										bc.remove((bc.size() - 1));
										break;
									}
									bc.remove((bc.size() - 1));
									break;
								case 4:
									menu.gestorCeldas();
									bc.add(">>Cells");
									System.out.println("\n" + bc);
									int opcionC = read.nextInt();
									switch (opcionC) {
									case 1:
										cIO.leerListado(m.getCells());
										break;
									case 2:
										cIO.leer(m.getCells());
										break;
									case 3:
										cIO.crear(m.getCells(), m.getPavilions());
										break;
									case 4:
										cIO.editar(m.getCells(), m.getPavilions());
										break;
									case 5:
										cIO.borrar(m.getCells(), m.getPavilions());
										break;
									case 6:
										cIO.realizarConsulta(m.getCells(), mainPath);
										break;
									case 7:
										System.out.println(
												"Do you want to visualize(1) or visualize and export as text file(2)?");
										int a = read.nextInt();
										read.nextLine();
										if (a == 1) {
											System.out.println(cIO.stats(m.getCells()));
										} else if (a == 2) {
											System.out.println(cIO.stats(m.getCells()));
											System.out.println("Insert the name of the file: ");
											String n = read.nextLine();
											m.saveTxt(n, cIO.stats(m.getCells()));
										} else {
											System.out.println("That is not a valid option.");
										}
										break;
									case 0:
										volver = true;
										bc.remove((bc.size() - 1));
										break;
									}
									bc.remove((bc.size() - 1));
									break;
								case 5:
									menu.gestorPabellones();
									bc.add(">>Pavilions");
									System.out.println("\n" + bc);
									int opcionPa = read.nextInt();
									switch (opcionPa) {
									case 1:
										paIO.leerListado(m.getPavilions());
										break;
									case 2:
										paIO.leer(m.getPavilions());
										break;
									case 3:
										paIO.crear(m.getPavilions());
										break;
									case 4:
										paIO.editar(m.getPavilions());
										break;
									case 5:
										paIO.borrar(m.getPavilions());
										break;
									case 6:
										System.out.println(
												"Do you want to visualize(1) or visualize and export as text file(2)?");
										int a = read.nextInt();
										read.nextLine();
										if (a == 1) {
											System.out.println(paIO.stats(m.getPavilions()));
										} else if (a == 2) {
											System.out.println(paIO.stats(m.getPavilions()));
											System.out.println("Insert the name of the file: ");
											String n = read.nextLine();
											m.saveTxt(n, paIO.stats(m.getPavilions()));
										} else {
											System.out.println("That is not a valid option.");
										}
										break;
									case 0:
										volver = true;
										bc.remove((bc.size() - 1));
										break;
									}
									bc.remove((bc.size() - 1));
									break;
								case 0:
									m.setBack(true);
									volver = true;
									bc.remove((bc.size() - 1));
									bc.remove((bc.size() - 1));
									break;
								}
							} while (!volver);
						} while (volver && !m.isBack());
						break;
					case 2:
						bc.add(">>Import JSON");
						System.out.println("\n" + bc);
						System.out.println("\nDo you want to merge(0) the files or generate a new database(1)?");
						int form = read.nextInt();
						System.out.println(
								"\nInsert the filetype of the JSON you want to import:\n\n0)Pavilion\n1)CivilServant\n2)Visitor");
						int fltp = read.nextInt();
						read.nextLine();
						System.out.println("\nIntroduce the name of the file:");
						String name = read.nextLine();
						m.importJson(form, fltp, mainPath+"imports/"+name+".json");
						m.setBack(true);
						bc.remove((bc.size() - 1));
						bc.remove((bc.size() - 1));
						break;
					case 3:
						System.out.println("\nSelect the data type you want to export: ");
						System.out.println("\n1) Prisoners");
						System.out.println("2) Civil Servants");
						System.out.println("3) Visitors");
						System.out.println("4) Cells");
						System.out.println("5) Pavilions");
						bc.add(">>Export CSV");
						System.out.println("\n" + bc);
						int opt = read.nextInt();
						read.nextLine();
						switch (opt) {
						case 1:
							pIO.exportCSV(m.getPrisoners(), mainPath);
							break;
						case 2:
							csIO.exportCSV(m.getCivilServants(), mainPath);
							break;
						case 3:
							vIO.exportCSV(m.getVisitors(), mainPath);
							break;
						case 4:
							cIO.exportCSV(m.getCells(), mainPath);
							break;
						case 5:
							paIO.exportCSV(m.getPavilions(), mainPath);
							break;
						default:
							System.out.println("That is not a valid option.");
							break;
						}
						m.setBack(true);
						bc.remove((bc.size() - 1));
						bc.remove((bc.size() - 1));
						break;
					case 4:
						bc.add(">>Send Email");
						System.out.println("\n" + bc);
						read.nextLine();
						System.out.println("Insert your email adress: ");
						String adress = read.nextLine();
						System.out.println("Write the subject of the email: ");
						String subj = read.nextLine();
						System.out.println(
								"Insert the name of the file you want to send (must be in the export folder): ");
						String flname = read.nextLine();
						System.out.println("Insert the filetype(.json, .CSV, etc): ");
						String fltype = read.nextLine();
						m.sendEmail(adress, subj, flname, fltype);
						m.setBack(true);
						bc.remove((bc.size() - 1));
						bc.remove((bc.size() - 1));
						break;
					case 5:
						bc.add(">>Save Changes");
						System.out.println("\n" + bc);
						System.out.println("Are you sure you want to save changes?(1=yes, 0=no)");
						int saf = read.nextInt();
						if (saf == 1) {
							m.HashToJson(0, "prisondb", "database/");
							m.HashToJson(1, "civilservants", "database/");
							m.HashToJson(2, "visitors", "database/");
							System.out.println("Changes saved.");
						}
						m.setBack(true);
						bc.remove((bc.size() - 1));
						bc.remove((bc.size() - 1));
						break;
					default:
						System.out.println("That is not a valid option");
						m.setBack(true);
						bc.remove((bc.size() - 1));
						break;
					}
				} while (m.isBack());
			} else if (ret == 0) {
				while (loaded == 0) {
					System.out.println("Do you want to load the main database(1) or the backup files(2)?");
					int load = read.nextInt();
					if (load == 1) {
						m.JsonToHashPab(mainPath + "database/" + "prisondb" + ".json");
						m.JsonToHashCS(mainPath + "database/" + "civilservants" + ".json");
						m.JsonToHashV(mainPath + "database/" + "visitors" + ".json");
						loaded = 1;
					} else if (load == 2) {
						m.JsonToHashPab(mainPath + "database/backup/" + "prisondbBackup" + ".json");
						m.JsonToHashCS(mainPath + "database/backup/" + "civilservantsBackup" + ".json");
						m.JsonToHashV(mainPath + "database/backup/" + "visitorsBackup" + ".json");
						loaded = 1;
					} else {
						System.out.println("That is not a valid option.");
					}
				}
				do {
					m.setBack(false);
					menu.menuBasic();
					bc.add("Location: >>Login, >>Basic Menu");
					System.out.println("\n" + bc);
					int choice = read.nextInt();
					switch (choice) {
					case 0:
						System.out.println("Do you want to save changes?(1=yes, 0=no)");
						int save = read.nextInt();
						if (save == 1) {
							m.HashToJson(0, "prisondb", "database/");
							m.HashToJson(1, "civilservants", "database/");
							m.HashToJson(2, "visitors", "database/");
							System.out.println("Changes saved.");
						}
						System.out.println("Do you want to update the backup copy?(1=yes, 0=no)");
						int backup = read.nextInt();
						if (backup == 1) {
							m.HashToJson(0, "prisondbBackup", "database/backup/");
							m.HashToJson(1, "civilservantsBackup", "database/backup/");
							m.HashToJson(2, "visitorsBackup", "database/backup/");
							System.out.println("Changes saved.");
						}
						System.out.println("\n\nThank you for using our application");
						break;
					case 1:
						boolean volver = false;
						do {
							volver = false;
							menu.seleccionTipoDato();
							bc.add(">>Register Management");
							System.out.println("\n" + bc);
							int dato = read.nextInt();
							do {
								switch (dato) {
								case 1:
									menu.gestorPresos();
									bc.add(">>Prisoners");
									System.out.println("\n" + bc);
									int opcionP = read.nextInt();
									switch (opcionP) {
									case 1:
										pIO.leerListado(m.getPrisoners());
										break;
									case 2:
										pIO.leer(m.getPrisoners());
										break;
									case 3:
										pIO.crear(m.getPrisoners(), m.getCells());
										break;
									case 4:
										pIO.editar(m.getPrisoners(), m.getCells());
										break;
									case 5:
										pIO.borrar(m.getPrisoners(), m.getCells());
										break;
									case 6:
										pIO.realizarConsulta(m.getPrisoners(), mainPath);
										break;
									case 7:
										System.out.println(
												"Do you want to visualize(1) or visualize and export as text file(2)?");
										int a = read.nextInt();
										read.nextLine();
										if (a == 1) {
											System.out.println(pIO.stats(m.getPrisoners()));
										} else if (a == 2) {
											System.out.println(pIO.stats(m.getPrisoners()));
											System.out.println("Insert the name of the file: ");
											String n = read.nextLine();
											m.saveTxt(n, pIO.stats(m.getPrisoners()));
										} else {
											System.out.println("That is not a valid option.");
										}
										break;
									case 0:
										volver = true;
										bc.remove((bc.size() - 1));
										break;
									}
									bc.remove((bc.size() - 1));
									break;

								case 2:
									menu.gestorFuncionarios();
									bc.add(">>Civil Servants");
									System.out.println("\n" + bc);
									int opcionF = read.nextInt();
									switch (opcionF) {
									case 1:
										csIO.leerListado(m.getCivilServants());
										break;
									case 2:
										csIO.leer(m.getCivilServants());
										break;
									case 3:
										csIO.crear(m.getCivilServants());
										break;
									case 4:
										csIO.editar(m.getCivilServants());
										break;
									case 5:
										csIO.borrar(m.getCivilServants());
										break;
									case 6:
										csIO.realizarConsulta(m.getCivilServants(), mainPath);
										break;
									case 7:
										System.out.println(
												"Do you want to visualize(1) or visualize and export as text file(2)?");
										int a = read.nextInt();
										read.nextLine();
										if (a == 1) {
											System.out.println(csIO.stats(m.getCivilServants()));
										} else if (a == 2) {
											System.out.println(csIO.stats(m.getCivilServants()));
											System.out.println("Insert the name of the file: ");
											String n = read.nextLine();
											m.saveTxt(n, csIO.stats(m.getCivilServants()));
										} else {
											System.out.println("That is not a valid option.");
										}
										break;
									case 0:
										volver = true;
										bc.remove((bc.size() - 1));
										break;
									}
									bc.remove((bc.size() - 1));

									break;

								case 3:
									menu.gestorVisitantes();
									bc.add(">>Visitors");
									System.out.println("\n" + bc);
									int opcionV = read.nextInt();
									switch (opcionV) {
									case 1:
										vIO.leerListado(m.getVisitors());
										break;
									case 2:
										vIO.leer(m.getVisitors());
										break;
									case 3:
										vIO.crear(m.getVisitors());
										break;
									case 4:
										vIO.editar(m.getVisitors());
										break;
									case 5:
										vIO.borrar(m.getVisitors());
										break;
									case 6:
										vIO.realizarConsulta(m.getVisitors(), mainPath);
										break;
									case 7:
										System.out.println(
												"Do you want to visualize(1) or visualize and export as text file(2)?");
										int a = read.nextInt();
										read.nextLine();
										if (a == 1) {
											System.out.println(vIO.stats(m.getVisitors()));
										} else if (a == 2) {
											System.out.println(vIO.stats(m.getVisitors()));
											System.out.println("Insert the name of the file: ");
											String n = read.nextLine();
											m.saveTxt(n, vIO.stats(m.getVisitors()));
										} else {
											System.out.println("That is not a valid option.");
										}
										break;
									case 0:
										volver = true;
										bc.remove((bc.size() - 1));
										break;
									}
									bc.remove((bc.size() - 1));
									break;
								case 4:
									menu.gestorCeldas();
									bc.add(">>Cells");
									System.out.println("\n" + bc);
									int opcionC = read.nextInt();
									switch (opcionC) {
									case 1:
										cIO.leerListado(m.getCells());
										break;
									case 2:
										cIO.leer(m.getCells());
										break;
									case 3:
										cIO.crear(m.getCells(), m.getPavilions());
										break;
									case 4:
										cIO.editar(m.getCells(), m.getPavilions());
										break;
									case 5:
										cIO.borrar(m.getCells(), m.getPavilions());
										break;
									case 6:
										cIO.realizarConsulta(m.getCells(), mainPath);
										break;
									case 7:
										System.out.println(
												"Do you want to visualize(1) or visualize and export as text file(2)?");
										int a = read.nextInt();
										read.nextLine();
										if (a == 1) {
											System.out.println(cIO.stats(m.getCells()));
										} else if (a == 2) {
											System.out.println(cIO.stats(m.getCells()));
											System.out.println("Insert the name of the file: ");
											String n = read.nextLine();
											m.saveTxt(n, cIO.stats(m.getCells()));
										} else {
											System.out.println("That is not a valid option.");
										}
										break;
									case 0:
										volver = true;
										bc.remove((bc.size() - 1));
										break;
									}
									bc.remove((bc.size() - 1));
									break;
								case 5:
									menu.gestorPabellones();
									bc.add(">>Pavilions");
									System.out.println("\n" + bc);
									int opcionPa = read.nextInt();
									switch (opcionPa) {
									case 1:
										paIO.leerListado(m.getPavilions());
										break;
									case 2:
										paIO.leer(m.getPavilions());
										break;
									case 3:
										paIO.crear(m.getPavilions());
										break;
									case 4:
										paIO.editar(m.getPavilions());
										break;
									case 5:
										paIO.borrar(m.getPavilions());
										break;
									case 6:
										System.out.println(
												"Do you want to visualize(1) or visualize and export as text file(2)?");
										int a = read.nextInt();
										read.nextLine();
										if (a == 1) {
											System.out.println(paIO.stats(m.getPavilions()));
										} else if (a == 2) {
											System.out.println(paIO.stats(m.getPavilions()));
											System.out.println("Insert the name of the file: ");
											String n = read.nextLine();
											m.saveTxt(n, paIO.stats(m.getPavilions()));
										} else {
											System.out.println("That is not a valid option.");
										}
										break;
									case 0:
										volver = true;
										bc.remove((bc.size() - 1));
										break;
									}
									bc.remove((bc.size() - 1));
									break;
								case 0:
									m.setBack(true);
									volver = true;
									bc.remove((bc.size() - 1));
									bc.remove((bc.size() - 1));
									break;
								}
							} while (!volver);
						} while (volver && !m.isBack());
						break;
					case 2:
						bc.add(">>Save Changes");
						System.out.println("\n" + bc);
						System.out.println("Are you sure you want to save changes?(1=yes, 0=no)");
						int saf = read.nextInt();
						if (saf == 1) {
							m.HashToJson(0, "prisondb", "database/");
							m.HashToJson(1, "civilservants", "database/");
							m.HashToJson(2, "visitors", "database/");
							System.out.println("Changes saved.");
						}
						m.setBack(true);
						break;
					default:
						System.out.println("That is not a valid option");
						m.setBack(true);
						bc.remove((bc.size() - 1));
						bc.remove((bc.size() - 1));
						break;
					}
				} while (m.isBack());
			}
		}
		read.close();
	}

	public void JsonToHashPab(String path) {
		// definicion de variables necesarias
		JSONParser parser = new JSONParser();
		Vector<String> llaves = new Vector<String>();
		Gson gson = new Gson();
		Pavilion pa = new Pavilion();
		// cargar el json al HashMap pavilions
		try {
			JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(path));
			for (Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				llaves.addElement(key);
			}
			for (int i = 0; i < llaves.size(); i++) {
				JSONObject jsonObject2 = (JSONObject) jsonObject.get(llaves.elementAt(i));
				pa = (Pavilion) gson.fromJson(jsonObject2.toString(), Pavilion.class);
				getPavilions().put(pa.getNum_pabellon(), pa);
			}
		} catch (Exception e) {
			MessageWindow mg2 = new MessageWindow("File error", "Verify you have selected the correct file");
			mg2.frame.setVisible(true);
			importError=true;
		}
		// cargar pabellones dentro de celdas y celdas dentro de presos
		for (Pavilion pab : getPavilions().values()) {

			for (int j = 0; j < pab.getCeldas().size(); j++) {

				pab.getCeldas().get(j).setPabellon(pab);
				for (int k = 0; k < pab.getCeldas().get(j).getPresos().size(); k++) {

					pab.getCeldas().get(j).getPresos().get(k).setCelda(pab.getCeldas().get(j));
				}
				pab.getCeldas().get(j).setCap_actual();
				pab.getCeldas().get(j).setLlena();
			}
			pab.setNum_celdas();
			pab.setNum_presos();
		}
		// cargar los HashMap cells y prisoners
		for (Pavilion pab : getPavilions().values()) {

			for (int j = 0; j < pab.getCeldas().size(); j++) {
				getCells().put(pab.getCeldas().get(j).getNum_celda(), pab.getCeldas().get(j));
			}
		}
		for (Pavilion pab : getPavilions().values()) {
			for (int j = 0; j < pab.getCeldas().size(); j++) {
				for (int k = 0; k < pab.getCeldas().get(j).getPresos().size(); k++) {
					getPrisoners().put(pab.getCeldas().get(j).getPresos().get(k).getNum_preso(),
							pab.getCeldas().get(j).getPresos().get(k));
				}
			}
		}
	}

	public void JsonToHashCS(String path) {
		// definicion de variables necesarias
		JSONParser parser = new JSONParser();
		Vector<String> llaves = new Vector<String>();
		Gson gson = new Gson();
		CivilServant cs = new CivilServant();
		// cargar el json al HashMap CivilServant
		try {
			JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(path));
			for (Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				llaves.addElement(key);
			}
			for (int i = 0; i < llaves.size(); i++) {
				JSONObject jsonObject2 = (JSONObject) jsonObject.get(llaves.elementAt(i));
				cs = (CivilServant) gson.fromJson(jsonObject2.toString(), CivilServant.class);
				getCivilServants().put(cs.getId_funcionario(), cs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void JsonToHashV(String path) {
		// definicion de variables necesarias
		JSONParser parser = new JSONParser();
		Vector<String> llaves = new Vector<String>();
		Gson gson = new Gson();
		Visitor v = new Visitor();
		// cargar el json al HashMap CivilServant
		try {
			JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(path));
			for (Iterator iterator = jsonObject.keySet().iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				llaves.addElement(key);
			}
			for (int i = 0; i < llaves.size(); i++) {
				JSONObject jsonObject2 = (JSONObject) jsonObject.get(llaves.elementAt(i));
				v = (Visitor) gson.fromJson(jsonObject2.toString(), Visitor.class);
				getVisitors().put(v.getId_visitor(), v);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void HashToJson(int type, String filename, String path) {// type 0=pavilions, type 1=civilServants, type
																	// 2=visitors
		try (FileWriter file = new FileWriter(mainPath + path + filename + ".json")) {
			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
			if (type == 0) {
				file.write(gson.toJson(pavilions));
			} else if (type == 1) {
				file.write(gson.toJson(civilServants));
			} else if (type == 2) {
				file.write(gson.toJson(visitors));
			}
			file.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void importJson(int type, int filetype, String path) {// type=0 merge, type=1 new; filetype
																					// 0=pavilions,
		// 1=civilServants, 2=visitors
		// MERGE:
		switch (filetype) {
		case 0:
			if (type == 1) {
				getPavilions().clear();
				getCells().clear();
				getPrisoners().clear();
			}
			JsonToHashPab(path);
			break;
		case 1:
			if (type == 1) {
				getCivilServants().clear();
			}
			JsonToHashCS(path);
			break;
		case 2:
			if (type == 1) {
				getVisitors().clear();
			}
			JsonToHashV(path);
			break;
		}
	}

	public int login() {
		JSONParser parser = new JSONParser();
		Scanner sceng = new Scanner(System.in);

		try {
			Object obj = parser.parse(new FileReader(mainPath + "database/users.json"));

			JSONObject jsonObject = (JSONObject) obj;
			System.out.println("+-----------------------------------+");
			System.out.println("|       Please introduce your       |");
			System.out.println("|       username and password       |");
			System.out.println("|             to log in             |");
			System.out.println("+-----------------------------------+");
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
					System.out.println("\n\n_____________________________________________________\n\n");
					return 1;
				} else if (passtest.equals(passw) && power.equals("False")) {
					System.out.println("\nCorrect Login");
					System.out.println("\n\n_____________________________________________________\n\n");
					return 0;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("\nError,the name or the password you have introduced might be wrong. Please try again");
		System.out.println("\n\n_____________________________________________________\n\n");
		contFallos = contFallos + 1;
		if (contFallos >= 3) {
			System.out.println("You have wasted 3 attempts. Wait " + stop + " seconds to continue.");
			for (int i = 1; i < 5; i++) {
				try {
					Thread.sleep((stop * 1000) / 4);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(
						"You have wasted 3 attempts. Wait " + (stop - (i * (stop / 4))) + " seconds to continue.");
			}
			contFallos = 0;
			System.out.println("If paused, press enter to continue");
			sceng.nextLine();

		}
		return -1;

	}

	public void sendEmail(String adress, String subject, String filename, String filetype) {
		final String username = "jailsystemproject@gmail.com";
		final String password = "averchavales";

		Properties props = new Properties();
		props.put("mail.smtp.auth", true);
		props.put("mail.smtp.starttls.enable", true);
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("jailsystemproject@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(adress));
			message.setSubject(subject);
			message.setText("");

			MimeBodyPart messageBodyPart = new MimeBodyPart();

			Multipart multipart = new MimeMultipart();

			messageBodyPart = new MimeBodyPart();
			String file = mainPath + "exports/" + filename + filetype;
			String fileName = filename + filetype;
			DataSource source = new FileDataSource(file);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(fileName);
			multipart.addBodyPart(messageBodyPart);

			message.setContent(multipart);

			System.out.println("Sending");

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public void saveTxt(String name, String text) {
		try {
			FileWriter file = new FileWriter(mainPath + "exports/" + name + ".txt");
			file.write(text);
			file.flush();
			file.close();
			System.out.println("\nYour file have been saved.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		Gestor g = new Gestor();
		g.start();
	}
}
