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
import ru.sber.internship.entity.dto.OrderItemDTO;
import ru.sber.internship.entity.utils.OrderStatus;
import ru.sber.internship.repository.OrderItemRepository;
import ru.sber.internship.repository.OrderRepository;
import ru.sber.internship.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
class OrderItemServiceImplTest {

    @Autowired
    private OrderItemServiceImpl itemService;

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private ProductServiceImpl productService;

    @MockBean
    private OrderItemRepository itemRepository;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private ProductRepository productRepository;

    private final List<OrderItem> orderItemList = new ArrayList<>();
    private final List<OrderItemDTO> orderItemDTOList = new ArrayList<>();
    private OrderItem orderItem;
    private OrderItemDTO orderItemDTO;
    private Order order;
    private Product product;

    @BeforeEach
    void setUp() {
        Client client = Client.builder().firstName("Ivan").lastName("Ivanov").email("ivanov@ya.ru").id(1L).build();
        product = Product.builder().name("product").description("product").id(1L).discount(0).price(BigDecimal.ONE).orderItems(null).build();
        order = Order.builder().id(1L).totalPrice(BigDecimal.ONE).orderStatus(OrderStatus.UNPAYED).client(client).build();
        orderItem = OrderItem.builder().id(1L).order(order).product(product).count(1).build();
        orderItemDTO = OrderItemDTO.builder().id(1L).productId(1L).orderId(1L).count(1).build();

        orderItemList.add(orderItem);
        orderItemDTOList.add(orderItemDTO);
    }

    @Test
    void findAll() {
        Mockito.when(itemService.findAll()).thenReturn(orderItemList);
        List<OrderItem> items = itemService.findAll();
        Mockito.verify(itemRepository, Mockito.times(1)).findAll();
        Assert.assertEquals(orderItemList, items);
    }

    @Test
    void findById() {
        Mockito.when(itemService.findById(1L)).thenReturn(orderItem);
        Mockito.when(itemService.findById(10L)).thenReturn(null);
        OrderItem orderItemNotNull = itemService.findById(1L);
        Assert.assertEquals(orderItem, orderItemNotNull);
        OrderItem orderItemNull = itemService.findById(10L);
        Assert.assertNull(orderItemNull);
        Mockito.verify(itemRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(itemRepository, Mockito.times(1)).findById(10L);
    }

    @Test
    void save() {
        OrderItem orderItem1 = OrderItem.builder().id(null).order(order).count(1).product(product).build();
        Mockito.when(itemService.save(orderItem1)).thenReturn(orderItem);
        OrderItem save = itemService.save(orderItem1);
        Assert.assertEquals(orderItem, save);

        OrderItem orderItem2 = OrderItem.builder().id(1L).order(order).count(2).product(product).build();
        Mockito.when(itemService.save(orderItem2)).thenReturn(orderItem2);
        orderItem.setCount(2);
        OrderItem update = itemService.save(orderItem2);
        Assert.assertEquals(orderItem, update);

        Mockito.verify(itemRepository, Mockito.times(1)).save(orderItem1);
        Mockito.verify(itemRepository, Mockito.times(1)).save(orderItem2);
    }

    @Test
    void findAllByOrderClientId() {
        Mockito.when(itemService.findAllByOrderClientId(1L)).thenReturn(orderItemList);
        List<OrderItem> itemListByClientId = itemService.findAllByOrderClientId(1L);

        Mockito.verify(itemRepository, Mockito.times(1)).findAllByOrderClientId(1L);
        Assert.assertEquals(orderItemList, itemListByClientId);
    }

    @Test
    void findAllByOrder_Id() {
        Mockito.when(itemService.findAllByOrderId(1L)).thenReturn(orderItemList);
        List<OrderItem> allByOrderid = itemService.findAllByOrderId(1);
        Assert.assertEquals(orderItemList, allByOrderid);
        Mockito.verify(itemRepository, Mockito.times(1)).findAllByOrderId(1);
    }

    @Test
    void findAllByProductId() {
        Mockito.when(itemService.findAllByProductId(1L)).thenReturn(orderItemList);
        List<OrderItem> allByProductId = itemService.findAllByProductId(1);
        Assert.assertEquals(orderItemList, allByProductId);
        Mockito.verify(itemRepository, Mockito.times(1)).findAllByProductId(1);

    }

    @Test
    void findOrderItemByIdAndOrderClientId() {
        Mockito.when(itemService.findOrderItemByIdAndOrderClientId(1L, 1L)).thenReturn(orderItem);
        OrderItem orderItemByIdAndOrderClientId = itemService.findOrderItemByIdAndOrderClientId(1L, 1L);
        Assert.assertEquals(orderItem, orderItemByIdAndOrderClientId);
        Mockito.verify(itemRepository, Mockito.times(1)).findOrderItemByIdAndOrderClientId(1L, 1L);
    }

    @Test
    void deleteById() {
        Mockito.when(itemService.findOrderItemByIdAndOrderClientId(1L, 1L)).thenReturn(orderItem);
        Mockito.when(itemService.findOrderItemByIdAndOrderClientId(10L, 1L)).thenReturn(null);

        boolean deleteByIdTrue = itemService.deleteById(1, 1);
        boolean deleteByIdFalse = itemService.deleteById(10, 1);
        Assert.assertTrue(deleteByIdTrue);
        Assert.assertFalse(deleteByIdFalse);

        Mockito.verify(itemRepository, Mockito.times(1)).findOrderItemByIdAndOrderClientId(1L, 1L);
        Mockito.verify(itemRepository, Mockito.times(1)).findOrderItemByIdAndOrderClientId(10L, 1L);
    }

    @Test
    void delete() {
        Mockito.when(itemService.findById(1)).thenReturn(orderItem);
        Mockito.when(itemService.findById(10)).thenReturn(null);
        boolean deleteByIdTrue = itemService.delete(1L);
        boolean deleteByIdFalse = itemService.delete(10L);

        Assert.assertTrue(deleteByIdTrue);
        Assert.assertFalse(deleteByIdFalse);

        Mockito.verify(itemRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(itemRepository, Mockito.times(1)).findById(10L);

    }


    @Test
    void convertOrderItemToOrderItemDTO() {
        OrderItemDTO convertedOrderItemDTO = itemService.convertOrderItemToOrderItemDTO(orderItem);
        Assert.assertEquals(orderItemDTO, convertedOrderItemDTO);
    }

    @Test
    void convertListOrderItemToListOrderItemDTO() {
        List<OrderItemDTO> convertedOrderItemDTOLiist = itemService.convertListOrderItemToListOrderItemDTO(orderItemList);
        Assert.assertEquals(orderItemDTOList, convertedOrderItemDTOLiist);
    }

    @Test
    void convertOrderItemDTOToOrderItem() {
        Mockito.when(itemService.save(orderItem)).thenReturn(orderItem);
        Mockito.when(orderService.findById(1)).thenReturn(order);
        Mockito.when(productService.findById(1)).thenReturn(product);
        OrderItem convertedOrderItem = itemService.convertOrderItemDTOToOrderItem(orderItemDTO);
        Assert.assertEquals(orderItem, convertedOrderItem);
        Mockito.verify(itemRepository, Mockito.times(1)).save(orderItem);
        Mockito.verify(productRepository, Mockito.times(1)).findById(1);
        Mockito.verify(orderRepository, Mockito.times(1)).findById(1);
    }


}