package knn2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class KnnV2 {
	private Establecimiento central;
	private LinkedList<Establecimiento> establecimientos = new LinkedList<Establecimiento>();
	private LinkedList<Vehiculo> vehiculos = new LinkedList<Vehiculo>();

	public void loadInstance(String pathFile) throws FileNotFoundException {
		BufferedReader br = new BufferedReader(new FileReader(pathFile));
		String linea = "";

		int cont = 0;
		int numCustomers = 0;
		int capacidad = 0;

		try {

			while ((linea = br.readLine()) != null) {

				if (linea.contains("VEHICLE")) {
					linea = br.readLine();
					linea = br.readLine();
					numCustomers = Integer.parseInt(linea.split("\\s+")[1]);
					capacidad = Integer.parseInt(linea.split("\\s+")[2]);

					vehiculos.add(new Vehiculo(1, capacidad));
				}

				if (linea.contains("CUST NO.")) {
					linea = br.readLine();
					for (int contEstab = 0; contEstab < numCustomers + 1; contEstab++) {

						cont++;
						linea = br.readLine();
						String[] dataAux = linea.split("\\s+");

						int[] data = new int[dataAux.length];

						for (int i = 1; i < dataAux.length; i++)
							data[i - 1] = Integer.parseInt(dataAux[i]);

						establecimientos.add(new Establecimiento(data[0],
								data[1], data[2], data[3]));
					}
					/**
					 * Retorna el primer elemento de la lista y lo asigna como
					 * base
					 **/
					central = establecimientos.remove();
				}
				cont++;
			}
			br.close();

		} catch (Exception ex) {

			System.out
					.println("Ocurrio un error en la lectura del archivo en la linea: "
							+ cont + ":\"" + linea + "\"");
		}

		try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void start() {

		LinkedList<Establecimiento> visitados = new LinkedList<Establecimiento>();
		Vehiculo vehiculo = vehiculos.getLast();
		vehiculo.moverACentral(central);

		while (establecimientos.size() > 0) {
			if (vehiculo.moverAleatoriamenteAlMasCercano(establecimientos)) {
				
			} else {
				vehiculos.add(new Vehiculo(vehiculos.size(), 200));
				vehiculo = vehiculos.getLast();
				vehiculo.moverACentral(central);
			}
		}

		imprimirRuta();
	}
	
	public void imprimirRuta(){
		
		for (Vehiculo vehiculo : vehiculos) {
			System.out.println("Vehicle ID: " + vehiculo.getId() );
			System.out.println("Distance: " + vehiculo.getDistanciaRecorrida());
			System.out.println("Route: ");
			vehiculo.imprimirRuta();
		}
	}

	public static void main(String[] args) {

		KnnV2 knnV2 = new KnnV2();
		try {
			knnV2.loadInstance(System.getProperty("user.dir")
					+ "/solomon_25/C101.txt");
			knnV2.start();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
