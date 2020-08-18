package stubMapping;

public class PerDayRent implements Comparable<PerDayRent>{
	
	String make;
	String vin;
	float rentPrice;
	
	PerDayRent(String make, String vin, float rentPrice)
	{
		this.make = make;
		this.vin = vin;
		this.rentPrice = rentPrice;
	}

	public int compareTo(PerDayRent perDayRent) {
		// TODO Auto-generated method stub
		return (int) (this.rentPrice - perDayRent.rentPrice);
	}

}
