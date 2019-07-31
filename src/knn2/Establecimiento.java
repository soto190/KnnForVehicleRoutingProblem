package knn2;

public class Establecimiento implements Comparable<Establecimiento>{

	private int id;
	private int posicionX;
	private int posicionY;
	private int demanda;

	private double distancia;

	public Establecimiento(int id, int posicionX, int posicionY, int demanda) {
		this.id = id;
		this.posicionX = posicionX;
		this.posicionY = posicionY;
		this.demanda = demanda;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the posicionX
	 */
	public int getPosicionX() {
		return posicionX;
	}

	/**
	 * @return the posicionY
	 */
	public int getPosicionY() {
		return posicionY;
	}

	/**
	 * @return the demanda
	 */
	public int getDemanda() {
		return demanda;
	}

	public double getDistancia() {
		return distancia;
	}

	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}

	public int compareTo(Establecimiento est) {
		if (this.getDistancia() == est.getDistancia())
			return 0;
		else if (this.getDistancia() > est.getDistancia())
			return 1;
		else if (this.getDistancia() < est.getDistancia())
			return -1;
		return 0;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Establecimiento [id=" + id + ", posicionX=" + posicionX
				+ ", posicionY=" + posicionY + ", demanda=" + demanda
				+ ", toString()=" + super.toString() + "]";
	}

}
