package domain.payment;

public class CashRegisterAPI implements PayFeesCommand {
	
	@Override
	public int pay(double chargeAmount) throws InterruptedException {
		Thread.sleep(1000); //just an API; activates physical machine to conduct payment process
		//sleep represents payment process going on at cashier
		// rest of programming in API is unknown
		return 0; 
	}
}
