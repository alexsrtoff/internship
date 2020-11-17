package ru.sber.internship.service.impl;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ru.sber.internship.entity.Client;
import ru.sber.internship.entity.Order;
import ru.sber.internship.entity.OrderItem;
import ru.sber.internship.entity.Product;
import ru.sber.internship.entity.dto.ClientDTO;
import ru.sber.internship.entity.dto.OrderDTO;
import ru.sber.internship.entity.dto.OrderItemDTO;
import ru.sber.internship.entity.utils.OrderStatus;
import ru.sber.internship.repository.ClientRepository;
import ru.sber.internship.repository.OrderItemRepository;
import ru.sber.internship.repository.OrderRepository;
import ru.sber.internship.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
class OrderServiceImplTest {

    @Autowired
    OrderServiceImpl orderService;

    @Autowired
    OrderItemServiceImpl orderItemService;

    @Autowired
    ClientServiceImpl clientService;

    @Autowired
    private ProductServiceImpl productService;

    @MockBean
    OrderRepository orderRepository;

    @MockBean
    OrderItemRepository orderItemRepository;

    @MockBean
    ClientRepository clientRepository;

    @MockBean
    ProductRepository productRepository;

    private List<Order> orderList = new ArrayList<>();
    private List<OrderDTO> orderDTOList = new ArrayList<>();
    private List<OrderItem> orderItemList = new ArrayList<>();

    private Client client;
    private Order order;
    private Product product;
    private OrderDTO orderDTO;
    private ClientDTO clientDTO;
    private OrderItem orderItem;
    private OrderItemDTO itemDTO;


    @BeforeEach
    void setUp() {
        client = Client.builder().firstName("Ivan").lastName("Ivanov").email("ivanov@ya.ru").id(1L).build();
        product = Product.builder().name("product").description("product").id(1L).discount(0).price(BigDecimal.ONE).orderItems(null).build();
        orderItem = OrderItem.builder().id(1L).order(order).product(product).count(1).build();
        orderDTO = OrderDTO.builder().id(1L).orderStatus(OrderStatus.UNPAYED).totalPrice(BigDecimal.ONE).clientId(1L).build();
        orderDTOList.add(orderDTO);
        orderItemList.add(orderItem);
        order = Order.builder().id(1L).totalPrice(BigDecimal.ONE).orderStatus(OrderStatus.UNPAYED).client(client).orderItems(orderItemList).build();
        orderList.add(order);
        itemDTO = OrderItemDTO.builder().id(1L).count(1).orderId(1L).productId(1L).build();

    }

    @Test
    void findAll() {
        Mockito.when(orderService.findAll()).thenReturn(orderList);
        List<Order> orderList1 = orderRepository.findAll();
        System.out.println(orderList1);
        Mockito.verify(orderRepository, Mockito.times(1)).findAll();
        Assert.assertEquals(orderList, orderList1);
    }

    @Test
    void findById() {
        Mockito.when(orderService.findById(1)).thenReturn(order);
        Order order1 = orderService.findById(1);
        Mockito.verify(orderRepository, Mockito.times(1)).findById(1);
        Assert.assertEquals(order, order1);
    }

    @Test
    void save() {
        Order order1 = Order.builder().id(null).totalPrice(BigDecimal.ONE).orderStatus(OrderStatus.UNPAYED).client(client).orderItems(orderItemList).build();
        Mockito.when(orderService.save(order1)).thenReturn(order);
        Order orderSave = orderService.save(order1);
        Mockito.verify(orderRepository, Mockito.times(1)).save(order1);
        Assert.assertEquals(order, orderSave);

        Order order2 = Order.builder().id(1L).totalPrice(BigDecimal.ONE).orderStatus(OrderStatus.PAYED).client(client).orderItems(orderItemList).build();
        Mockito.when(orderService.save(order2)).thenReturn(order);
        order.setOrderStatus(OrderStatus.PAYED);
        Order orderUpdate = orderService.save(order2);
        Mockito.verify(orderRepository, Mockito.times(1)).save(order2);
        Assert.assertEquals(order, orderUpdate);
    }

    @Test
    void findOrderByIdAndAndClientId() {
        Mockito.when(orderService.findOrderByIdAndAndClientId(1L, 1L)).thenReturn(order);
        Order order1 = orderService.findOrderByIdAndAndClientId(1L, 1L);
        Assert.assertEquals(order, order1);
        Mockito.verify(orderRepository, Mockito.times(1)).findOrderByIdAndClientId(1L, 1L);
    }

    @Test
    void deleteByIdAndClient_Id() {
        Mockito.when(orderService.findByIdAndClient_Id(1, 1)).thenReturn(order);
        Mockito.when(orderService.findByIdAndClient_Id(1, 10)).thenReturn(null);
        boolean deleteTrue = orderService.deleteByIdAndClient_Id(1, 1);
        boolean deleteFalse = orderService.deleteByIdAndClient_Id(1, 10);

        Assert.assertTrue(deleteTrue);
        Assert.assertFalse(deleteFalse);

        Mockito.verify(orderRepository, Mockito.times(1)).delete(order);
        Mockito.verify(orderRepository, Mockito.times(1)).findByIdAndClient_Id(1, 1);
        Mockito.verify(orderRepository, Mockito.times(1)).findByIdAndClient_Id(1, 10);
    }

    @Test
    void findAllByClient_Id() {
        Mockito.when(orderService.findAllByClient_Id(1)).thenReturn(orderList);
        List<Order> orderList1 = orderService.findAllByClient_Id(1);
        Assert.assertEquals(orderList, orderList1);
        Mockito.verify(orderRepository, Mockito.times(1)).findAllByClient_Id(1);
    }

    @Test
    void deleteById() {
        Mockito.when(orderService.findById(1)).thenReturn(order);
        Mockito.when(orderService.findById(10)).thenReturn(null);
        boolean deleteTrue = orderService.deleteById(1);
        boolean deleteFalse = orderService.deleteById(10);
        Assert.assertTrue(deleteTrue);
        Assert.assertFalse(deleteFalse);
    }

    @Test
    void convertOrderToOrderDTO() {
        OrderDTO orderDTO1 = orderService.convertOrderToOrderDTO(order);
        Assert.assertEquals(orderDTO, orderDTO1);
    }

    @Test
    void chekOrderByOrderItem() {
        Order order1 = Order.builder().id(1L).totalPrice(BigDecimal.ONE).orderStatus(OrderStatus.PAYED).client(client).orderItems(orderItemList).build();
        OrderItemDTO itemDTO1 = OrderItemDTO.builder().id(1L).count(1).orderId(10L).productId(1L).build();

        Mockito.when(orderService.findOrderByIdAndAndClientId(1L, 1L)).thenReturn(order);
        Mockito.when(orderService.findOrderByIdAndAndClientId(1L, 10L)).thenReturn(null);
        Mockito.when(orderService.findOrderByIdAndAndClientId(10L, 1L)).thenReturn(null);

        boolean checkTrue = orderService.chekOrderByOrderItem(itemDTO, 1L);
        boolean checkFalseClientId = orderService.chekOrderByOrderItem(itemDTO, 10L);
        boolean checkFalseItemDTO = orderService.chekOrderByOrderItem(itemDTO1, 1L);

        Mockito.when(orderService.findOrderByIdAndAndClientId(1L, 1L)).thenReturn(order1);
        boolean checkFalseOrderStatus = orderService.chekOrderByOrderItem(itemDTO1, 1L);

        Assert.assertTrue(checkTrue);
        Assert.assertFalse(checkFalseClientId);
        Assert.assertFalse(checkFalseOrderStatus);
        Assert.assertFalse(checkFalseItemDTO);
    }

    @Test
    void chekOrder() {
        OrderDTO orderDTO1 = OrderDTO.builder().id(10L).orderStatus(OrderStatus.UNPAYED).totalPrice(BigDecimal.ONE).clientId(1L).build();
        OrderDTO orderDTO2 = OrderDTO.builder().id(1L).orderStatus(OrderStatus.UNPAYED).totalPrice(BigDecimal.ONE).clientId(10L).build();
        OrderDTO orderDTO3 = OrderDTO.builder().id(1L).orderStatus(OrderStatus.PAYED).totalPrice(BigDecimal.ONE).clientId(1L).build();

        Mockito.when(orderService.findOrderByIdAndAndClientId(1L, 1L)).thenReturn(order);
        Mockito.when(orderService.findOrderByIdAndAndClientId(10L, 1L)).thenReturn(null);
        Mockito.when(orderService.findOrderByIdAndAndClientId(1L, 10L)).thenReturn(null);

        boolean checkTrue = orderService.chekOrder(orderDTO);
        boolean checkFalseOrderId = orderService.chekOrder(orderDTO1);
        boolean checkFalseClientId = orderService.chekOrder(orderDTO2);

        Assert.assertTrue(checkTrue);
        Assert.assertFalse(checkFalseClientId);
        Assert.assertFalse(checkFalseOrderId);

        order.setOrderStatus(OrderStatus.PAYED);
        Mockito.when(orderService.findOrderByIdAndAndClientId(1L, 1L)).thenReturn(order);
        boolean checkFalseOrderStatus = orderService.chekOrder(orderDTO3);
        Assert.assertFalse(checkFalseOrderStatus);
    }

    @Test
    void convertOrderDTOToOrder() {
    }

    @Test
    void convertOrderListToOrderDTOList() {
        List<OrderDTO> orderDTOS = orderService.convertOrderListToOrderDTOList(orderList);
        Assert.assertEquals(orderDTOList, orderDTOS);
    }

    @Test
    void createOrder() {
        Order order1 = Order.builder()
                .client(client)
                .orderStatus(OrderStatus.UNPAYED)
                .build();
        Mockito.when(productService.findById(1)).thenReturn(product);
        Mockito.when(clientService.findById(1)).thenReturn(client);
        Mockito.when(orderItemService.convertOrderItemDTOToOrderItem(itemDTO)).thenReturn(orderItem);
        Mockito.when(orderService.findById(1)).thenReturn(order);
        Mockito.when(orderService.save(order1)).thenReturn(order);
        Order order2 = orderService.createOrder(itemDTO, 1L);
        Assert.assertEquals(order, order2);
    }
}