package utilities;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;


public class TestBase 
{
	public static final int port = 20001;
	public static final String host = "localhost";
	
	public static WireMockServer wireMockServer = new WireMockServer(port);
    
	@BeforeSuite
	public void startServer()
	{
		WireMock.configureFor(host, port);
		wireMockServer.start();
		
		ResponseDefinitionBuilder mockResponse = new ResponseDefinitionBuilder();
		mockResponse.withStatus(200);
		mockResponse.withBodyFile("rentalCars.json");
		mockResponse.withHeader("content-type", "application/json");
		
		

		WireMock.stubFor(WireMock.get("/carList").willReturn(mockResponse));
//		WireMock.stubFor(
//				WireMock.get("/carList")
//				.willReturn(WireMock.aResponse()
//				.withBodyFile("rentalCars.json")
//				.withStatus(200)));	

	}

	@AfterSuite
	public void stopServer()
	{
		wireMockServer.stop();
		System.out.println("server stopped");
	}
	
}
