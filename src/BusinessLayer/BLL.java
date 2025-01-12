package BusinessLayer;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import DataAccessLayer.DataAccess;
import Entities.AppUser;
import Entities.Folio;
import Entities.Guest;
import Entities.Room;

public class BLL {

    DataAccess DAL;

    public BLL() {
        super();
        DAL = new DataAccess();
    }

    public int AddUser(String userName, String password, String name, String surname) {
        for (var user : GetAllAppUser()) {
            if (user.getUsername().equals(userName)) {
                return -1;//Kullanıcı zaten önceden eklenmiş
            }
        }
        AppUser newAppUser = new AppUser();
        newAppUser.setUsername(userName);
        newAppUser.setName(name);
        newAppUser.setSurname(surname);
        newAppUser.setPassword(password);
        return DAL.AddUser(newAppUser);

    }

    public int CheckUserForLogin(String userName, String password) {
        return DAL.CheckUserForLogin(userName, password);

    }

    public List<AppUser> GetAllAppUser() {
        return DAL.GetAllAppUser();
    }

    public int UpdateUser(String userName, String password, String newPassword, String name, String surname) {
        return DAL.UpdateUser(userName, password, newPassword, name, surname);
    }

    public List<Room> GetAllRooms() {
        return DAL.GetAllRooms();
    }

    public int CreateRoom(int roomID) {
        Room newRoom = new Room();
        newRoom.setRoomID(roomID);
        return DAL.CreateRoom(newRoom);
    }

    public Room GetRoomByID(int id) {
        return DAL.GetRoomByID(id);
    }

    public int AddGuestToRoom(String TC, String Name, String Surname, LocalDate CheckIn, LocalDate CheckOut, int RoomId) {
        Guest newGuest = new Guest();
        if (CheckTcNo(TC) == 1) {
            newGuest.setTC(TC);
            newGuest.setName(Name);
            newGuest.setSurname(Surname);
            newGuest.setCheckIn(CheckIn);
            newGuest.setCheckOut(CheckOut);

            DAL.AddGuestToRoom(newGuest, RoomId);
            return 1;
        }
        return -1;

    }

    public int RemoveGuestFromRoom(String GuestTC, int RoomId) {
        if (CheckTcNo(GuestTC) == 1) {
            DAL.RemoveGuestFromRoom(GuestTC, RoomId);
            return 1;
        }
        return -1;

    }

    public int CheckDaysOfGuest(String GuestTc, int RoomId) {
        if (CheckTcNo(GuestTc) == 1) {
            return DAL.CheckDaysOfGuest(GuestTc, RoomId);
        }
        return -1;

    }

    public int ControlCheckOutDate(String GuestTc, int RoomId) {
        if (CheckTcNo(GuestTc) == 1) {
            return DAL.ControlCheckOutDate(GuestTc, RoomId);
        }
        return -1;
    }

    public int CheckTcNo(String Tc) {
        if (Tc.length() == 11) {
            return 1;//Tc doğru kodu
        }
        return -1;//Tc yanlış kodu
    }

    public List<Guest> GetGuestListByRoom(int RoomId) {
        return DAL.GetGuestListByRoom(RoomId);

    }

    public void AddProductToFolio(int roomId, String productName, double productPrice) {
//        HashMap<String, Integer> products = new HashMap<String, Integer>();
//        products.put(productName, productPrice);
        Room room = GetRoomByID(roomId);
        Folio roomFolio = room.getFolios();
        double balance = roomFolio.getBalance();
        roomFolio.setBalance(balance + productPrice);
        DAL.AddProductToFolio(roomId, productName, productPrice);
    }

    public Folio GetAllFolio(int RoomId) {
        return DAL.GetAllFolio(RoomId);
    }

    public void DeleteProductFromFolio(int roomId, String productName) {
        Room room = GetRoomByID(roomId);
        Folio roomFolio = room.getFolios();
        double balance = roomFolio.getBalance();
        roomFolio.setBalance(balance - roomFolio.getProducts().get(productName));
        DAL.DeleteProductFromFolio(roomId, productName);
    }

}
