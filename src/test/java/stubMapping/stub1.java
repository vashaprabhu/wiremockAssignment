package stubMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.*;



import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import utilities.TestBase;

public class stub1 extends TestBase{

		@Test
		public void getBlueTesla() {
			String sHostname = "http://localhost:20001";
			String sURI = "/carList";
			String sURL = sHostname + sURI;
			
			String sMake = "Tesla";
			String sColor = "Blue";
			
			RestAssured.baseURI = sURL;
			
			Response res = RestAssured.given().contentType("application/json").get();
			System.out.println(res.asString());
			System.out.println(res.statusCode());
			
			List<String> carMake = res.jsonPath().getList("Car.make");
			int carNum = 0;
			for(int i = 0; i < carMake.size(); i++)
			{
				if(carMake.get(i).equalsIgnoreCase(sMake)) {
						carNum = i;
					String carColor = res.jsonPath().getString("Car["+carNum+"].metadata.Color");
					if(carColor.equalsIgnoreCase(sColor))
					{
						String carNotes = res.jsonPath().getString("Car["+carNum+"].metadata.Notes");
						System.out.println("car brand: "+carMake.get(i) +" car color :"+ carColor +"  Notes : "+carNotes);
					}
				}
			}
		}
		
		@Test(priority = 1)
		public void lowestRentalPerDay()
		{
			String sHostname = "http://localhost:20001";
			String sURI = "/carList";
			String sURL = sHostname + sURI;
			
			RestAssured.baseURI = sURL;
			
			Response res = RestAssured.given().contentType("application/json").get();
			System.out.println(res.asString());
			System.out.println(res.statusCode());

			List<Float> perDayRent = res.jsonPath().getList("Car.perdayrent");
			System.out.println(perDayRent.toString());
			List<PerDayRent> dayPrice = new ArrayList<PerDayRent>();
			List<PerDayDiscount> rentDiscount = new ArrayList<PerDayDiscount>();
			
			for(int i = 0; i<perDayRent.size(); i++)
			{
				String sMake = res.jsonPath().getString("Car["+i+"].make");
				String sVin = res.jsonPath().getString("Car["+i+"].vin");
				Float fPrice = res.jsonPath().getFloat("Car["+i+"].perdayrent.Price");
				Float fDiscount = res.jsonPath().getFloat("Car["+i+"].perdayrent.Discount");
				Float frentDiscount = (fPrice - (fPrice * fDiscount/100));
				
				dayPrice.add(new PerDayRent(sMake, sVin, fPrice));
				rentDiscount.add(new PerDayDiscount(sVin, frentDiscount ));
			}
			Collections.sort(dayPrice);
			
			System.out.println("Cars with Lowest price only ");
			for(PerDayRent rent : dayPrice)
			{
				System.out.println(rent.make);
				System.out.println(rent.vin);
				System.out.println(rent.rentPrice);
			}
			System.out.println("*****************************************************************");
			Collections.sort(rentDiscount);
			System.out.println("Cars with Lowest price and discount only ");
			for(PerDayDiscount rentDisc : rentDiscount)
			{
				System.out.println(rentDisc.vin);
				System.out.println(rentDisc.Discount);
			}
			
		}	
		
		@Test(priority = 2)
		public void highestProfitGeneratingCar()
		{
			String sHostname = "http://localhost:20001";
			String sURI = "/carList";
			String sURL = sHostname + sURI;
			
			RestAssured.baseURI = sURL;
			Response res = RestAssured.given().contentType("application/json").get();
			System.out.println(res.asString());
			System.out.println(res.statusCode());
			
			List<Float> carMetrics = res.jsonPath().getList("Car.metrics");
			System.out.println(carMetrics.toString());
			
			List<carProfit> carYearlyFactors = new ArrayList<carProfit>();
			
			for(int i = 0; i<carMetrics.size(); i++)
			{
				String sMake = res.jsonPath().getString("Car["+i+"].make");
				String sVin = res.jsonPath().getString("Car["+i+"].vin");
				Float fPrice = res.jsonPath().getFloat("Car["+i+"].perdayrent.Price");
				Float fDiscount = res.jsonPath().getFloat("Car["+i+"].perdayrent.Discount");
				Float frentDiscount = (fPrice - (fPrice * fDiscount/100));
				Float fYearMaintenance = res.jsonPath().getFloat("Car["+i+"].metrics.yoymaintenancecost");
				Float fDepric = res.jsonPath().getFloat("Car["+i+"].metrics.depreciation");
				Integer YearToDateRental = res.jsonPath().getInt("Car["+i+"].metrics.rentalcount.yeartodate"); 
				
				Float cProfit = frentDiscount*YearToDateRental - (fYearMaintenance + fDepric);
				
				carYearlyFactors.add(new carProfit(sVin, sMake, cProfit));
				
			}
			Collections.sort(carYearlyFactors);
			
			System.out.println("Cars with highest profit ");
			for(carProfit cProfit : carYearlyFactors)
			{
				System.out.println(cProfit.carMake); 
				System.out.println(cProfit.vin);
				System.out.println(cProfit.profitOfCar);
			}
		}	
}
