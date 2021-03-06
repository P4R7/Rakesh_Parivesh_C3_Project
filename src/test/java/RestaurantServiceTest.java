import org.junit.jupiter.api.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class RestaurantServiceTest {

    RestaurantService service = new RestaurantService();
    Restaurant restaurant;
    //REFACTOR ALL THE REPEATED LINES OF CODE
    @BeforeEach
    void setup_before_each_test()
    {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = service.addRestaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    //>>>>>>>>>>>>>>>>>>>>>>SEARCHING<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void searching_for_existing_restaurant_should_return_expected_restaurant_object() throws restaurantNotFoundException {

        service.addRestaurant("ABC","Aplha road, shop-1,Rampur", LocalTime.of(12,00,00),LocalTime.of(23,00,00) );
        service.addRestaurant("DCE","Aplha road, shop-2,Rampur", LocalTime.of(8,00,00),LocalTime.of(20,00,00) );
        assertEquals("ABC",service.findRestaurantByName("ABC").getName());
    }

    //You may watch the video by Muthukumaran on how to write exceptions in Course 3: Testing and Version control: Optional content
    @Test
    public void searching_for_non_existing_restaurant_should_throw_exception() throws restaurantNotFoundException {
        service.addRestaurant("DCE","Aplha road, shop-2,Rampur", LocalTime.of(8,00,00),LocalTime.of(20,00,00) );
        assertThrows(restaurantNotFoundException.class,()->service.findRestaurantByName("ABC"));
    }
    //<<<<<<<<<<<<<<<<<<<<SEARCHING>>>>>>>>>>>>>>>>>>>>>>>>>>




    //>>>>>>>>>>>>>>>>>>>>>>ADMIN: ADDING & REMOVING RESTAURANTS<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void remove_restaurant_should_reduce_list_of_restaurants_size_by_1() throws restaurantNotFoundException {

        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.removeRestaurant("Amelie's cafe");
        assertEquals(initialNumberOfRestaurants-1, service.getRestaurants().size());
    }

    @Test
    public void removing_restaurant_that_does_not_exist_should_throw_exception() throws restaurantNotFoundException {
        assertThrows(restaurantNotFoundException.class,()->service.removeRestaurant("Pantry d'or"));
    }

    @Test
    public void add_restaurant_should_increase_list_of_restaurants_size_by_1(){
        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.addRestaurant("Pumpkin Tales","Chennai",LocalTime.parse("12:00:00"),LocalTime.parse("23:00:00"));
        assertEquals(initialNumberOfRestaurants + 1,service.getRestaurants().size());
    }
    //<<<<<<<<<<<<<<<<<<<<ADMIN: ADDING & REMOVING RESTAURANTS>>>>>>>>>>>>>>>>>>>>>>>>>>
    //>>>>>>>>>>>>>>>>>>>>USER : ADDING ORDERS & SHOWING TOTAL<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void add_order_item_to_list_and_show_total_cost() {
        Item item1 = new Item("Sweet corn soup",119);
        Item item2 = new Item("Vegetable lasagne", 269);
        List<Item> order = new ArrayList<>();
        order.add(item1);
        order.add(item2);
        assertEquals(388, service.showOrderTotal(order));
    }
    //<<<<<<<<<<<<<<<<<<<<USER : ADDING ORDERS & SHOWING TOTAL>>>>>>>>>>>>>>>>>>>>>>>>>>
}