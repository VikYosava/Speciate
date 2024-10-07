package model;

public class Configuracion {
	int Nmutaciones, CantEInicial, Niteraciones, frecC;
	String txt, direc;
	
	public Configuracion(int fCantEInicial, int fNmutaciones, int nIt, String mtxt, String dir, int frec) {
		this.Nmutaciones=fNmutaciones;
		this.CantEInicial=fCantEInicial;
		this.Niteraciones=nIt;
		this.txt=mtxt;
		this.direc=dir;
		this.frecC=frec;
	}
	public void setCantEInicial(int cantEInicial2) {
		// TODO Auto-generated method stub
		this.CantEInicial=cantEInicial2;
	}
	public void setNMutaciones(int nMutaciones) {
		// TODO Auto-generated method stub
		this.Nmutaciones=nMutaciones;
		
	}
	public void setNIteraciones(int nIteraciones2) {
		// TODO Auto-generated method stub
		this.Niteraciones=nIteraciones2;
		
	}
	
	public void setMatrixTxt(String matrixtxt) {
		// TODO Auto-generated method stub
		this.txt=matrixtxt;
	}
	public void setDir(String direct) {
		// TODO Auto-generated method stub
		this.direc=direct;
	}
	public void setFrecuenciaCatastrofes(int catastrof) {
		// TODO Auto-generated method stub
		this.frecC=catastrof;
	}
	public int getCantEInicial() {
		// TODO Auto-generated method stub
		return this.CantEInicial;
	}
	public int getNIteraciones() {
		// TODO Auto-generated method stub
		return this.Niteraciones;
	}
	public int getNMutaciones() {
		// TODO Auto-generated method stub
		return this.Nmutaciones;
	}
	public String getMatrixtxt() {
		// TODO Auto-generated method stub
		return this.txt;
	}
	public String getDirect() {
		// TODO Auto-generated method stub
		return this.direc;
	}
	public int getCatastrof() {
		// TODO Auto-generated method stub
		return this.frecC;
	}
	
	
}
