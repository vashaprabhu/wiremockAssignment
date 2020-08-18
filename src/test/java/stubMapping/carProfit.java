package stubMapping;

public class carProfit implements Comparable<carProfit> {
	
	String carMake;
	String vin;
	float profitOfCar;


	
	public carProfit(String carMake, String vin, float profitOfCar)
	{
		this.carMake = carMake;
		this.vin = vin;
		this.profitOfCar = profitOfCar;	
	}

public int compareTo(carProfit profit) {
	// TODO Auto-generated method stub
	return (int) (profit.profitOfCar - this.profitOfCar);
}
	
	

	

}
