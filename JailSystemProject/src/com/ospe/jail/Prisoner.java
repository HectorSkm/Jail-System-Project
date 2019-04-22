package com.ospe.jail;

import java.io.Serializable;
import com.google.gson.annotations.Expose;

public class Prisoner extends Person implements Serializable {
	@Expose private int num_preso;
	@Expose private int niv_amenaza;
			private Cell celda;
	@Expose private String condena;
	@Expose private String crimen;
	@Expose private String ingreso;
	@Expose private boolean visitas;
	@Expose private boolean llamadas;

	public Prisoner() {
		super();
	}

	public Prisoner(String nombre, String apellidos, String DNI, String f_nac, String nacionalidad, String sexo,
			int altura, double peso, int num_preso, int niv_amenaza, Cell celda, String condena, String crimen,
			String ingreso, boolean visitas, boolean llamadas) {
		super(nombre, apellidos, DNI, f_nac, nacionalidad, sexo, altura, peso);
		this.num_preso = num_preso;
		this.niv_amenaza = niv_amenaza;
		this.celda = celda;
		this.condena = condena;
		this.crimen = crimen;
		this.ingreso = ingreso;
		this.visitas = visitas;
		this.llamadas = llamadas;
	}

	// Getter and Setters
	public int getNum_preso() {
		return num_preso;
	}

	public void setNum_preso(int num_preso) {
		this.num_preso = num_preso;
	}

	public int getNiv_amenaza() {
		return niv_amenaza;
	}

	public void setNiv_amenaza(int niv_amenaza) {
		this.niv_amenaza = niv_amenaza;
	}

	public Cell getCelda() {
		return celda;
	}

	public void setCelda(Cell celda) {
		this.celda = celda;
	}

	public String getCondena() {
		return condena;
	}

	public void setCondena(String condena) {
		this.condena = condena;
	}

	public String getCrimen() {
		return crimen;
	}

	public void setCrimen(String crimen) {
		this.crimen = crimen;
	}

	public String getIngreso() {
		return ingreso;
	}

	public void setIngreso(String ingreso) {
		this.ingreso = ingreso;
	}

	public boolean isVisitas() {
		return visitas;
	}

	public void setVisitas(boolean visitas) {
		this.visitas = visitas;
	}

	public boolean isLlamadas() {
		return llamadas;
	}

	public void setLlamadas(boolean llamadas) {
		this.llamadas = llamadas;
	}

	public String toString() {
		String s = "\nDNI: " + DNI + "\nNombre: " + nombre + "\nApellidos: " + apellidos
				+ "\nFecha de nacimiento (d�a/mes/a�o): " + f_nac + "\nNacionalidad: " + nacionalidad + "\nSexo (h/m): "
				+ sexo + "\nAltura (cm): " + altura + "\nPeso (kg): " + peso + "\nN�mero de preso: " + num_preso
				+ "\nNivel de amenaza: " + niv_amenaza + "\nN�mero de celda: " + celda.getNum_celda() + "\nCrimen: "
				+ crimen + "\nTiempo de condena(a�os,meses,d�as): " + condena + "\nIngreso (d�a/mes/a�o): " + ingreso;
		if (visitas) {
			s = s + "\nVisitas: s�";
		} else {
			s = s + "\nVisitas: no";
		}
		if (llamadas) {
			s = s + "\nLlamadas: s�\n\n";
		} else {
			s = s + "\nLlamadas: no\n\n";
		}
		return s;
	}

	public String toCSV(char i) {
		String s = DNI + i + nombre + i + apellidos + i + f_nac + i + nacionalidad + i + sexo + i + altura + i + peso
				+ i + num_preso + i + niv_amenaza + i + celda.getNum_celda() + i + crimen + i + condena + i + ingreso;
		if (visitas) {
			s = s + i + "s�";
		} else {
			s = s + i + "no";
		}
		if (llamadas) {
			s = s + i + "s�\n";
		} else {
			s = s + i + "no\n";
		}
		return s;
	}
}