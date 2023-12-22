// Подумать над структурой класса Ноутбук для магазина техники - выделить поля и методы. Реализовать в java.
// Создать множество ноутбуков.
// Написать метод, который будет запрашивать у пользователя критерий (или критерии) фильтрации и выведет ноутбуки, отвечающие фильтру.
// Критерии фильтрации можно хранить в Map.
// Например:
// “Введите цифру, соответствующую необходимому критерию:
// 1 - ОЗУ
// 2 - Объем ЖД
// 3 - Операционная система
// 4 - Цвет …
// Далее нужно запросить минимальные значения для указанных критериев - сохранить параметры фильтрации можно также в Map.
// Отфильтровать ноутбуки из первоначального множества и вывести проходящие по условиям.

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class Program {
    private static Scanner sc = new Scanner(System.in);


    public static void main(String[] args) {
        HashSet<Laptop> catalog = StartCatalog();
        HashSet<Laptop> select;  // Каталог с фильтрами
        Laptop filter = new Laptop();

        String option;
        LinkedList<String> options = new LinkedList<>(Arrays.asList("1", "2", "3", "4", "5", "6"));
        boolean escape = false;
        while (!escape) {
            MainMenu();
            option = sc.nextLine();
            System.out.print("\033[H\033[J");
            if (options.contains(option)) {
                switch (option) {
                    case "1":  // Весь каталог
                        ShowCatalog(catalog);
                        break;
                    case "2":  // Показать фильтры
                        catalog = AddProduct(catalog);
                        break;
                    case "3":  // Каталог с фильтрами
                        select = Selection(catalog, filter);
                        ShowFilter(filter);
                        if (select.size() > 0){
                            System.out.println("Подходящие варианты:");
                            ShowCatalog(select);
                        }
                        else System.out.println("Нет подходящих вариантов.");
                        break;
                    case "4":  // Показать фильтры
                        ShowFilter(filter);
                        break;
                    case "5":  // Настройка фильтров
                        filter = UpdateFilter(filter);
                        break;
                    case "6":  // Выход
                        escape = true;
                        break;
                }
            }
            else{
                System.out.println("Некорректный ввод команды. Повторите попытку.\n");
            }
        }
    }

    private static HashSet<Laptop> CreateRandomSet(int maxAmount){
        ArrayList<String> manufactur = new ArrayList<>(Arrays.asList("ASUS", "HP", "LENOVO", "MSI", "APPLE"));
        ArrayList<Integer> ram = new ArrayList<>(Arrays.asList(8, 16, 32));
        ArrayList<Integer> hdCap = new ArrayList<>(Arrays.asList(256, 512, 1024, 2048));
        ArrayList<String> os = new ArrayList<>(Arrays.asList("LINUX", "WINDOWS"));
        ArrayList<String> color = new ArrayList<>(Arrays.asList("BLACK", "WHITE", "GOLD", "PINK", "GRAY"));

        Random rnd = new Random();
        HashSet<Laptop> laptops = new HashSet<>();
        for (int i = 0; i < maxAmount; i++) {
            Laptop current = new Laptop();
            current.setManufacturer(manufactur.get(rnd.nextInt(manufactur.size())));
            current.setRAM(ram.get(rnd.nextInt(ram.size())).toString());
            current.setHDCap(hdCap.get(rnd.nextInt(hdCap.size())).toString());
            if(current.getManufacturer().equals("APPLE"))
                current.setOS("IOS");
            else
                current.setOS(os.get(rnd.nextInt(os.size())));
            current.setColor(color.get(rnd.nextInt(color.size())));
            laptops.add(current);
        }

        return laptops;
    }

    private static HashSet<Laptop> AddProduct(HashSet<Laptop> catalog){
        Laptop newLaptop = new Laptop();
        String temp;

        System.out.println("Введите наименование производителя");
        System.out.print("-> ");
        temp = sc.nextLine().toUpperCase();
        if (!temp.equals(""))
            newLaptop.setManufacturer(temp);

        System.out.println("Введите объем ОЗУ");
        System.out.print("-> ");
        temp = sc.nextLine();
        if (TryParseInt(temp) != null)
            newLaptop.setRAM(temp);

        System.out.println("Введите объем ЖД");
        System.out.print("-> ");
        temp = sc.nextLine();
        if (TryParseInt(temp) != null)
            newLaptop.setHDCap(temp);

        if (!newLaptop.getManufacturer().equals("APPLE")){
            System.out.println("Введите наименование ОС");
            System.out.print("-> ");
            temp = sc.nextLine().toUpperCase();
            if (!temp.equals(""))
                newLaptop.setOS(temp);
        }
        else newLaptop.setOS("IOS");


        System.out.println("Введите цвет");
        System.out.print("-> ");
        temp = sc.nextLine().toUpperCase();
        if (!temp.equals(""))
            newLaptop.setColor(temp);

        if (!catalog.contains(newLaptop) && !newLaptop.isEmpty()){
            catalog.add(newLaptop);
            System.out.println("Успешно добавлено.\n");
        }
        else System.out.println("Продукт с указанными характеристиками уже имеется.\n");

        return catalog;
    }
