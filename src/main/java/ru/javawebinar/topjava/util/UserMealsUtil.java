package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
       List<UserMealWithExceed> test= getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
        for (UserMealWithExceed meal :test) {
            System.out.println(meal);
        }
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        HashMap<LocalDate,Integer> dayCalories = new HashMap<LocalDate, Integer>();
        ArrayList<UserMeal> mealsInPeriod = new ArrayList<>();
        for (UserMeal meal:mealList){
            LocalDate date = meal.getDateTime().toLocalDate();
            dayCalories.put(date, dayCalories.getOrDefault(date,0) + meal.getCalories());

            LocalTime time = meal.getDateTime().toLocalTime();
            if (TimeUtil.isBetween(time,startTime,endTime)){
                mealsInPeriod.add(meal);
            }
        }

        List<UserMealWithExceed> result = new ArrayList<>();
        for (UserMeal meal : mealsInPeriod){
            if(dayCalories.get(meal.getDateTime().toLocalDate()) > caloriesPerDay){
                result.add(new UserMealWithExceed(meal,true));
            }
            else{
                result.add(new UserMealWithExceed(meal,false));
            }
        }
        return result;
    }
}
