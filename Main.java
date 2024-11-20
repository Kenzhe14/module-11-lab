import java.util.*;
import java.util.stream.Collectors;

class User {
    public int Id;
    public String Username;
    public String Password;
}

class Product {
    public int Id;
    public String Name;
    public double Price;
}

class Order {
    public int Id;
    public int UserId;
    public List<Product> Products;
    public String Status;
}

interface IUserService {
    User Register(String username, String password);
    User Login(String username, String password);
}

interface IProductService {
    List<Product> GetProducts();
    Product AddProduct(Product product);
}

interface IOrderService {
    Order CreateOrder(int userId, List<Integer> productIds);
    Order GetOrderStatus(int orderId);
}

interface IPaymentService {
    boolean ProcessPayment(int orderId, double amount);
}

interface INotificationService {
    void SendNotification(int userId, String message);
}

class OrderService implements IOrderService {
    private final IProductService productService;
    private final IPaymentService paymentService;
    private final INotificationService notificationService;

    public OrderService(IProductService productService, IPaymentService paymentService, INotificationService notificationService) {
        this.productService = productService;
        this.paymentService = paymentService;
        this.notificationService = notificationService;
    }

    @Override
    public Order CreateOrder(int userId, List<Integer> productIds) {
        List<Product> products = productService.GetProducts()
                .stream()
                .filter(p -> productIds.contains(p.Id))
                .collect(Collectors.toList());

        if (products.isEmpty()) {
            throw new RuntimeException("Selected products not found.");
        }

        Order order = new Order();
        order.UserId = userId;
        order.Products = products;
        order.Status = "Created";

        double totalAmount = products.stream().mapToDouble(p -> p.Price).sum();

        if (paymentService.ProcessPayment(order.Id, totalAmount)) {
            order.Status = "Paid";
            notificationService.SendNotification(userId, "Your order has been successfully paid.");
        } else {
            order.Status = "Payment Failed";
            notificationService.SendNotification(userId, "Payment failed. Please try again.");
        }

        return order;
    }

    @Override
    public Order GetOrderStatus(int orderId) {
        Order order = new Order();
        order.Id = orderId;
        order.Status = "In Progress";
        return order;
    }
}

public class Main {
    public static void main(String[] args) {
        IProductService productService = new IProductService() {
            private List<Product> products = new ArrayList<>(Arrays.asList(
                    new Product() {{ Id = 1; Name = "Laptop"; Price = 1000.0; }},
                    new Product() {{ Id = 2; Name = "Phone"; Price = 500.0; }}
            ));

            @Override
            public List<Product> GetProducts() {
                return products;
            }

            @Override
            public Product AddProduct(Product product) {
                product.Id = products.size() + 1;
                products.add(product);
                return product;
            }
        };

        IPaymentService paymentService = (orderId, amount) -> amount > 0;
        INotificationService notificationService = (userId, message) -> System.out.println("Notification to user " + userId + ": " + message);

        OrderService orderService = new OrderService(productService, paymentService, notificationService);

        List<Integer> productIds = Arrays.asList(1, 2);
        Order order = orderService.CreateOrder(123, productIds);

        System.out.println("Order Status: " + order.Status);
    }
}
