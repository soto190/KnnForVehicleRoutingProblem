package knn;
public class Vehicle extends Customer{
	
	int xinitial;
	int yinitial;
	
	int fill = 200;
	int capacity = 200;
	int location;
	public int totalDistance = 0;
	
	public Vehicle(Customer customer, int capacity){
		super(customer);
		this.capacity = capacity;
		this.fill = capacity;
		this.custNo = customer.custNo;
		this.location = this.custNo;
		this.xinitial = customer.x;
		this.yinitial = customer.y;
	}
	
	public Vehicle(int capacity, int xcoord, int ycoord,
			int demand, int readyTime, int dueDate, int service){
		this.capacity = capacity;

		this.x = xcoord;
		this.xinitial = xcoord;

		this.y = ycoord;
		this.yinitial = ycoord;
	}

	public boolean moveTo(int xcoord, int ycoord) {

		this.x = xcoord;
		this.y = ycoord;

		return true;
	}
	public boolean reset(){
		
		this.x = this.xinitial;
		this.y = this.yinitial;
		this.capacity = this.fill;
		this.custNo = 0;
		
		return true;
	}
	
	public boolean deliver(Customer customer) {
		
		moveTo(customer.x, customer.y);
		this.capacity -= customer.demand;
		this.custNo = customer.custNo;
		return true;
	}

	public boolean canDeliver(int demand) {
		if (capacity >= demand)
			return true;
		return false;
	}
	
	public String toString() {
		return "Vehicle: " + x + "x " + y + "y " + capacity
				+ "Capacity ";
	}
}
