package knn;

import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;
import java.util.Vector;

import javax.annotation.processing.FilerException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class Knn {

	private Vehicle vehicle;
	private LinkedList<Customer> customers = new LinkedList<Customer>();
	private LinkedList<Customer> customersAll = new LinkedList<Customer>();
	private Vector<LinkedList<Customer>> routes = new Vector<LinkedList<Customer>>();

	Knn() {

	}

	public File selectFile() {

		File file;
		JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));

		int returnVal = fc.showOpenDialog(fc);

		if (returnVal == JFileChooser.APPROVE_OPTION) {

			file = fc.getSelectedFile();
			System.out.println(file);

			return file;
		}

		return null;
	}

	public boolean readFile(File file) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(file));
		String linea = "";

		int cont = 0;
		int numCustomers = 26;
		int capacity = 0;

		try {

			while ((linea = br.readLine()) != null) {

				if (linea.contains("VEHICLE")) {
					linea = br.readLine();
					linea = br.readLine();

					capacity = Integer.parseInt(linea.split("\\s+")[2]);
				}

				if (linea.contains("CUST NO.")) {
					linea = br.readLine();
					for (int contCustomer = 0; contCustomer < numCustomers; contCustomer++) {

						cont++;
						linea = br.readLine();
						String[] dataAux = linea.split("\\s+");

						int[] data = new int[dataAux.length];

						for (int i = 1; i < dataAux.length; i++)
							data[i - 1] = Integer.parseInt(dataAux[i]);

						customers.add(new Customer(data[0], data[1], data[2],
								data[3], data[4], data[5], data[6]));
					}
					customersAll = (LinkedList<Customer>) customers.clone();
					vehicle = new Vehicle(customers.getFirst(), capacity);
					customers.remove();
				}
				cont++;
			}
		} catch (Exception ex) {
			System.out
					.println("Ocurrio un error en la lectura del archivo en la linea: "
							+ cont + ":\"" + linea + "\"");
		}

		System.out.println(vehicle);
		System.out.println(customers);

		br.close();

		return true;
	}

	public void start() {

		LinkedList<Customer> routhCustomers = new LinkedList<Customer>();
		routhCustomers.add(new Customer(vehicle));

		System.out.println("\nNew routh:");
		System.out.println("\t" + vehicle + "Location: " + vehicle.location);

		while (!customers.isEmpty()) {

			computeDistances(vehicle);

			Customer knnCustomer = customers.getFirst();
			//Customer knnCustomer = getRandom(3);
			System.out.println(customers);

			if (vehicle.canDeliver(knnCustomer.demand)) {

				vehicle.deliver(knnCustomer);
				vehicle.totalDistance += knnCustomer.distance;
				customers.remove(knnCustomer);
				routhCustomers.add(knnCustomer);

			} else {

				System.out.println(customers);
				
				vehicle.totalDistance += Math.sqrt(Math.pow(
						routhCustomers.getLast().x - vehicle.xinitial, 2)
						+ Math.pow(routhCustomers.getLast().y - vehicle.yinitial, 2));
				
				System.out.println("\nNew routh:");

				routes.add(routhCustomers);
				routhCustomers = new LinkedList<Customer>();

				vehicle.reset();
				vehicle.custNo = 0;

				routhCustomers.add(new Customer(vehicle));
			}
			System.out.println("\t" + vehicle + "Location: " + vehicle.custNo);
		}
		routes.add(routhCustomers);

		printRoutes();

		KnnGraphics aplicacion = new Knn.KnnGraphics();
		aplicacion.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private Customer getRandom(int firstN) {
		Random rnd = new Random();
		if (customers.size() > 3)
			return customers.get(rnd.nextInt(firstN));
		return customers.getFirst();
	}

	public void printRoutes() {

		for (LinkedList<Customer> routh : routes) {
			System.out.println("\nRuta:");
			for (Customer cust : routh)
				System.out.print(cust.custNo
						+ (routh.indexOf(cust) != routh.size() - 1 ? " -> "
								: " "));
		}
		System.out.println();
		System.out.println(vehicle.totalDistance);
	}

	public void computeDistances(Customer vehicle) {

		double[] allDistances = new double[customers.size()];

		for (Customer actualCustomer : customers) {
			allDistances[customers.indexOf(actualCustomer)] = Math.sqrt(Math.pow(
					actualCustomer.x - vehicle.x, 2)
					+ Math.pow(actualCustomer.y - vehicle.y, 2));

			actualCustomer.distance = allDistances[customers
					.indexOf(actualCustomer)];
		}

		customers = quickSortCustomersDistances(customers, 0,
				customers.size() - 1);
	}

	private LinkedList<Customer> quickSortCustomersDistances(
			LinkedList<Customer> customers, int left, int right) {

		if (left >= right)
			return customers;

		int l = left, r = right;

		if (left != right) {
			int pivote;
			Customer aux;
			pivote = left;

			while (left != right) {

				while (customers.get(right).distance >= customers.get(pivote).distance
						&& left < right)
					right--;

				while (customers.get(left).distance < customers.get(pivote).distance
						&& left < right)
					left--;

				if (left != right) {
					aux = customers.get(right);
					customers.set(right, customers.get(left));
					customers.set(left, aux);
				}
			}
			if (left == right) {
				quickSortCustomersDistances(customers, l, left - 1);
				quickSortCustomersDistances(customers, left + 1, r);
			}
		} else
			return customers;
		return customers;
	}

	@SuppressWarnings("serial")
	public class KnnGraphics extends JFrame {

		public KnnGraphics() {
			super("KnnGraphic");

			setSize(840, 720);
			setVisible(true);
		}

		public void paint(Graphics graphics) {
			super.paint(graphics);

			int esc = 4;
			int desplX = 120;
			int desplY = 120;

			int width = 7, height = 7;

			Random rand = new Random();
			int r = rand.nextInt(255);
			int g = rand.nextInt(255);
			int b = rand.nextInt(255);

			graphics.setColor(Color.RED);
			
			graphics.fillOval(vehicle.xinitial * esc + desplX, vehicle.yinitial
					* esc + desplY, width, height);

			for (Customer cust : customersAll) {
				graphics.setColor(Color.BLUE);
				graphics.fillOval((int) (cust.x * esc) + desplX,
						(int) (cust.y * esc) + desplY, width, height);
				
				graphics.setColor(Color.BLACK);
				
//				graphics.drawString(cust.demand+"", cust.x * esc + desplX,
//						cust.y * esc + desplY);
			
			}
			try {

				for (LinkedList<Customer> routh : routes) {
					for (int flash = 0; flash < 10; flash++) {

						r = rand.nextInt(255);
						g = rand.nextInt(255);
						b = rand.nextInt(255);

						graphics.setColor(new Color(r, g, b));
						graphics.fillOval(vehicle.xinitial * esc + desplX,
								vehicle.yinitial * esc + desplY, width, height);

						Thread.sleep(200);

						r = rand.nextInt(255);
						g = rand.nextInt(255);
						b = rand.nextInt(255);
						graphics.setColor(new Color(r, g, b));
						graphics.fillOval(vehicle.xinitial * esc + desplX,
								vehicle.yinitial * esc + desplY, width, height);
					}

					r = rand.nextInt(255);
					g = rand.nextInt(255);
					b = rand.nextInt(255);

					Customer temp = routh.getFirst();
					for (Customer cust : routh) {
						
						Thread.sleep(700);
						graphics.fillOval(cust.x * esc + desplX, cust.y * esc
								+ desplY, width, height);

						graphics.setColor(new Color(r, g, b));
						graphics.drawLine(cust.x * esc + desplX, cust.y * esc
								+ desplY, temp.x * esc + desplX, temp.y * esc
								+ desplY);
						temp = cust;
					}
					graphics.drawLine(vehicle.xinitial * esc + desplX,
							vehicle.yinitial * esc + desplY, temp.x * esc
									+ desplX, temp.y * esc + desplY);
				}
				graphics.setColor(Color.RED);
				graphics.fillOval(vehicle.xinitial * esc + desplX,
						vehicle.yinitial * esc + desplY, width, height);
			} catch (Exception ex) {

			}
		}
	}

	public static void main(String args[]) {
		Knn knn = new Knn();

		try {
			knn.readFile(knn.selectFile());
			knn.start();
		} catch (FilerException ex) {
			System.err.println("So sorry something wrong happened...");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}