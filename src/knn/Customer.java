package knn;

public class Customer {
	public int custNo;
	public int x;
	public int y;
	public int demand;
	public int readyTime;
	public int dueDate;
	public int service;
	
	public double distance;
	
	Customer(){
		
	}
	
	Customer(Vehicle vehicle){
		this.custNo = vehicle.custNo;
		this.x = vehicle.xinitial;
		this.y = vehicle.yinitial;
		this.demand = vehicle.demand;
		this.readyTime = vehicle.readyTime;
		this.dueDate = vehicle.dueDate;
		this.service = vehicle.service;
		
	}
	
	Customer(Customer customer){
		this.custNo = customer.custNo;
		this.x = customer.x;
		this.y = customer.y;
		this.demand = customer.demand;
		this.readyTime = customer.readyTime;
		this.dueDate = customer.dueDate;
		this.service = customer.service;
		
	}
	Customer(int custNo, int xcoord, int ycoord, 
			int demand, int readyTime, int dueDate, int service){
		
		this.custNo = custNo;
		this.x = xcoord;
		this.y = ycoord;
		this.demand = demand;
		this.readyTime = readyTime;
		this.dueDate = dueDate;
		this.service = service;
	}
	
	public String toString(){
		
		return 	"["+custNo +": "+ x +"x "+y +"y "+ demand 
				+"D "+readyTime +"RT "+dueDate+"DD "+ service + "S "+ distance +"dist]\n";
	}
}
