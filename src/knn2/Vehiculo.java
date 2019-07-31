package knn2;

import java.util.Collections;
import java.util.LinkedList;

public class Vehiculo {
	private int id;
	private int capacidad;
	private double distanciaRecorrida = 0.0;

	private int posicionActual = -1;
	/** -1 quiere decir que se ubica en la bodega **/

	private int posicionX;
	private int posicionY;

	private LinkedList<Establecimiento> ruta = new LinkedList<Establecimiento>();

	public Vehiculo(int id, int capacidad) {
		this.id = id;
		this.capacidad = capacidad;
	}

	/**
	 * @return the posicionActual
	 */
	public int getPosicionActual() {
		return posicionActual;
	}

	/**
	 * @param posicionActual
	 *            the posicionActual to set
	 */
	public void setPosicionActual(int posicionActual) {
		this.posicionActual = posicionActual;
	}

	/**
	 * @return the posicionX
	 */
	public int getPosicionX() {
		return posicionX;
	}

	/**
	 * @param posicionX
	 *            the posicionX to set
	 */
	public void setPosicionX(int posicionX) {
		this.posicionX = posicionX;
	}

	/**
	 * @return the posicionY
	 */
	public int getPosicionY() {
		return posicionY;
	}

	/**
	 * @param posicionY
	 *            the posicionY to set
	 */
	public void setPosicionY(int posicionY) {
		this.posicionY = posicionY;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the capacidad
	 */
	public int getCapacidad() {
		return capacidad;
	}

	public double getDistanciaRecorrida() {
		return distanciaRecorrida;
	}

	/**
	 * @return the ruta
	 */
	public LinkedList<Establecimiento> getRuta() {
		return ruta;
	}

	public void moverACentral(Establecimiento central) {
		this.moverA(central);
	}

	public void moverA(Establecimiento est) {
		this.ruta.add(est);
		this.distanciaRecorrida += est.getDistancia();
		this.capacidad -= est.getDemanda();
		this.posicionX = est.getPosicionX();
		this.posicionY = est.getPosicionY();
		this.posicionActual = est.getId();
	}

	public boolean moverAleatoriamenteAlMasCercano(
			LinkedList<Establecimiento> establecimientos) {

		for (Establecimiento establecimiento : establecimientos)
			establecimiento.setDistancia(this.distanciaA(establecimiento));

		Collections.sort(establecimientos);
		
		Establecimiento est = establecimientos.remove();

		if(this.getCapacidad() >= est.getDemanda()){
			this.moverA(est);
			return true;
		}
		return false;

	}

	public double distanciaA(Establecimiento est) {
		return Math.pow((this.getPosicionX() - est.getPosicionX()), 2)
				+ Math.pow((this.getPosicionY() - est.getPosicionY()), 2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Vehiculo [id=" + id + ", capacidad=" + capacidad
				+ ", posicionActual=" + posicionActual + ", posicionX="
				+ posicionX + ", posicionY=" + posicionY + ", ruta=" + ruta
				+ "]";
	}

	public void imprimirRuta() {
		for (Establecimiento est : ruta) {
			System.out.print(est.getId() + " ");
		}
		System.out.println();
	}

}
