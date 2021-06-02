import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;
    //REFACTOR ALL THE REPEATED LINES OF CODE

    @BeforeEach
    public void addRestaurant(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE


        Restaurant mockRestaurant = Mockito.spy(restaurant);

        Mockito.when(mockRestaurant.getCurrentTime()).thenAnswer(new Answer<LocalTime>() {
            @Override
            public LocalTime answer(InvocationOnMock invocationOnMock) throws Throwable {
                return restaurant.closingTime.minusHours(2);
            }
        });

        assertTrue(mockRestaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE

        Restaurant mockRestaurant = Mockito.spy(restaurant);

        Mockito.when(mockRestaurant.getCurrentTime()).thenAnswer(new Answer<LocalTime>() {
            @Override
            public LocalTime answer(InvocationOnMock invocationOnMock) throws Throwable {
                return restaurant.closingTime.plusHours(2);
            }
        });

        assertFalse(mockRestaurant.isRestaurantOpen());

    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>SELECTED LIST<<<<<<<<<<<<<<<<<<<<<<

    @Test
    public void select_item_should_increase_list_of_selected_items_size_by_1(){

        int size = restaurant.getSelectedList().size();
        int totalCost = restaurant.addItemInSelectedList(restaurant.getMenu().get(0));
        int size1 = restaurant.getSelectedList().size();
        assertEquals(size+1,size1);
    }

    @Test
    public void deselect_item_should_decrease_list_of_selected_items_size_by_1(){
        int size = restaurant.getSelectedList().size();
        int totalCost = restaurant.removeItemInSelectedList(restaurant.getMenu().get(0));
        int size1 = restaurant.getSelectedList().size();
        assertEquals(size-1,size1);
    }

    //<<<<<<<<<<<<<<<<<<<<<<SELECTED LIST>>>>>>>>>>>>>>>>>>>>>>>

    //>>>>>>>>>>>>>>>>>>>>>>CALCULATION<<<<<<<<<<<<<<<<<<<<<<

    @Test
    public void select_item_should_add_cost_in_total_cost(){
        int totalCost = restaurant.getTotalCost();
        int totalCost1 = restaurant.addItemInSelectedList(restaurant.getMenu().get(0));
        assertEquals(totalCost+restaurant.getMenu().get(0).getPrice(),totalCost1);
    }

    @Test
    public void deselect_item_should_minus_cost_in_total_cost(){
        int totalCost = restaurant.getTotalCost();
        int totalCost1 = restaurant.removeItemInSelectedList(restaurant.getMenu().get(0));
        assertEquals(totalCost-restaurant.getMenu().get(0).getPrice(),totalCost1);
    }

    //<<<<<<<<<<<<<<<<<<<<<<CALCULATION>>>>>>>>>>>>>>>>>>>>>>>
}