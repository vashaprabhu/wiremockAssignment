package stubMapping;

public class PerDayDiscount implements Comparable<PerDayDiscount> {
	
	
	String vin;
	float Discount;
	
	PerDayDiscount (String vin, float Discount)
	{
		
		this.vin = vin;
		this.Discount = Discount;
	}


	public int compareTo(PerDayDiscount PerDayDiscount) {
		// TODO Auto-generated method stub
		return (int) (this.Discount - PerDayDiscount.Discount);
	}

}
