package th.mfu;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
;

@RestController
public class OrderController {

    @Autowired
    OrderRepository orderRepo;

    @Autowired
    CustomerRepository customerRepo;

    //create order

    @PostMapping("/customers/{customerId}/orders")
    public ResponseEntity<String> createOrder(@PathVariable Integer customerId, @RequestBody Order order) {
        Optional<Customer> cust = customerRepo.findById(customerId);
        if(!cust.isPresent()){
            return new ResponseEntity<String>("Customer not found", HttpStatus.NOT_FOUND);
        } 
        order.setCustomer(cust.get());
        orderRepo.save(order);
        return new ResponseEntity<String>("Customer created", HttpStatus.CREATED);
    }

    
    //get order by customerId
    @GetMapping("/customers/{customerId}/orders")
    public ResponseEntity<List<Order>> getOrderByCustomerId(@PathVariable Integer customerId ){
        return new ResponseEntity<List<Order>>(orderRepo.findByCustomerId(customerId), HttpStatus.OK);
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable int id) {
        orderRepo.deleteById(id);
        return new ResponseEntity<String>("Order deleted!", HttpStatus.NO_CONTENT);
}
}