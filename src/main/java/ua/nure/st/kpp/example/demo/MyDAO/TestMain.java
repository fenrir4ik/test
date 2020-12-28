package ua.nure.st.kpp.example.demo.MyDAO;


import ua.nure.st.kpp.example.demo.Flowers.Cactus;
import ua.nure.st.kpp.example.demo.Flowers.Plant;
import ua.nure.st.kpp.example.demo.Flowers.Rose;
import ua.nure.st.kpp.example.demo.Flowers.Tree;
import ua.nure.st.kpp.example.demo.Proxy.Status;
import ua.nure.st.kpp.example.demo.Proxy.User;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class TestMain {
	public static void main(String[] args)
	{
		User user = new User("emil", "12121212");
		user.login();
		System.out.println("Статус пользователя: " + user.getStatus());
		ArrayList<Plant> result;
		Plant plant = new Plant.Builder("Red Rose").buildAge(10).buildHeight(15).buildTemperature(23).build();
		System.out.println("Вставка записей... ");
		user.Insert_Plant(plant);
		System.out.println("Изменение записей... ");
		user.ChangeGround("Пещаный кактус", "Чернозём", "Очень родючая почва");
		System.out.println("Удаление записей... ");
		user.DeletePlantByName("Ель простая");

		System.out.println("Получение данных: ");
		result = user.Get_Plants();
		System.out.println(result.size() + " результатов");
		System.out.println("Получение данных: ");
		result = user.SelectByGroundName("Песок");
		System.out.println(result.size() + " результатов");
		System.out.println("Получение данных: ");
		result = user.Get_Plants_By_Name("Red Rose");
		System.out.println(result.size() + " результатов");
	}
}
