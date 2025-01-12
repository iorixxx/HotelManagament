package BusinessLayer;

import Entities.AppUser;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        BLL bll = new BLL();

        bll.AddUser("furkan56", "deneme123", "Furkan", "sahin");
        System.out.println(bll.AddUser("furkan56", "deneme123", "Furkan", "sahin"));
        List<AppUser> test = bll.GetAllAppUser();
        System.out.println(bll.GetAllAppUser());
        System.out.println(bll.UpdateUser("furkan56", "deneme123", "test", "furkan", "sahin"));

        for (int i = 1; i <= 10; i++) {
            bll.CreateRoom(i);
        }
        System.out.println(bll.GetRoomByID(5).isClean());
        ;
        bll.AddGuestToRoom("12346578911", "bilal", "şahin", LocalDate.of(2022, 12, 7), LocalDate.of(2023, 12, 9), 5);
        System.out.println(bll.GetRoomByID(5).getGuests());
        System.out.println(bll.ControlCheckOutDate("12346578911", 5));
        bll.AddGuestToRoom("11111111111", "furkan", "sajim", LocalDate.of(2022, 12, 7), LocalDate.of(2023, 12, 9), 5);
        System.out.println(bll.GetGuestListByRoom(5));
        bll.AddProductToFolio(5, "Doritos", 15);
        var c = bll.GetRoomByID(5).getFolios();
        System.out.println(c);
        bll.AddProductToFolio(5, "dondurma", 159);
//
        System.out.println(bll.GetAllFolio(5));

        bll.DeleteProductFromFolio(5, "Doritos");
        System.out.println(bll.GetAllFolio(5));

    }
}
