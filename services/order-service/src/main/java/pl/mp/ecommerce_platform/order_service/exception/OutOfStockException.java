package pl.mp.ecommerce_platform.order_service.exception;

public class OutOfStockException extends Exception{
    public OutOfStockException(String message) {
        super(message);
    }
}
