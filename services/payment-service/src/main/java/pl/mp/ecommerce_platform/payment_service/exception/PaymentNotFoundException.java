package pl.mp.ecommerce_platform.payment_service.exception;

public class PaymentNotFoundException extends Exception{
    public PaymentNotFoundException(String message) {
        super(message);
    }
}
