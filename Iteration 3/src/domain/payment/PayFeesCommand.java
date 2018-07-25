package domain.payment;

abstract public interface PayFeesCommand {
	public int pay(double chargeAmount) throws InterruptedException;
}
