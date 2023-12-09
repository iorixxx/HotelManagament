package DataAccessLayer;
import java.security.Guard;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.lang.model.util.ElementScanner14;

import Entities.AppUser;
import Entities.Folio;
import Entities.Guest;
import Entities.Room;

public class DataAccess {
    List<AppUser> appUsers = new ArrayList<AppUser>();//Kullanıcı listesini oluşturur
    List<Room> rooms = new ArrayList<Room>();//Room listesi oluşturur


    //Kullanıcı listesine kullanıcı ekler
    public int AddUser(AppUser appUserAdd){
        boolean checkAdd = appUsers.add(appUserAdd);
        if(checkAdd == true)
        {
            return 1;
        }
        else 
        return -1;
    }

    //Kullanıcı listesindeki kullanıcıları kontrol eder
    public int CheckUserForLogin(AppUser appUserCheck){
        for (AppUser appUser : appUsers) {
            if(appUserCheck.getUsername() == appUser.getUsername() && appUserCheck.getPassword()==appUser.getPassword()){
                return 1;//Kullanıcı listesindeki kullanıcıları parametre olarak gelen kullanıcıya göre kontrol eder ve bulursa başarılı değer olan 1 döner
            }
        }
        return -1;
    }

    //Tüm kullancıların listesini getirir
    public List<AppUser> GetAllAppUser(){
        return appUsers;
    }

    //Kullanıcıyı günceller
    public int UpdateUser(AppUser appUserUpdate){
        for (AppUser appUser : appUsers) {
            if(appUser.equals(appUserUpdate)){//Kullanıcı listesi içinde parametre olarak gelen kullanıcıyı arar
                if(appUser.getPassword()==appUserUpdate.getPassword())//Güncellenecek kullanıcının şifresini kontrol eder
                {
                    appUser.setName(appUserUpdate.getName());
                    appUser.setSurname(appUserUpdate.getSurname());
                    appUser.setPassword(appUserUpdate.getPassword());
                    return 1;//Başarılı kodunu döndürür
                }
                else return -2;//Şifre yanlış hata kodunu döndürür
                
                
            }
        }
        return -1;//Kullanıcı bulunamadı hata kodunu döndürür
    }

    //Tüm odaların listesini getirir
    public List<Room> GetAllRooms(){
        return rooms;
    }

    //Yeni oda oluşturur
    public int CreateRoom(Room roomCreate){
        
        boolean checkRoom = rooms.add(roomCreate);
        if(checkRoom == true) return 1;
        return -1;
    }

    //ID bilgisine göre oda bilgisi getirir
    public Room GetRoomByID(int id){
        for (Room room : rooms) {
            if(room.getRoomID() == id){
                return room;
            }            
        }
        return null;
    }

    //Odaya misafir ekler(CheckIn)
    public int AddGuestToRoom(Guest guestAdd,int roomId){ 
        Room room = GetRoomByID(roomId);
        
        List<Guest> listRoomGuest = room.getGuests();
        
        boolean checkResult = listRoomGuest.add(guestAdd);
        
        if(checkResult==true){
        return 1;
        }  
        else{
        return -1;
        }

    }
    
    //Misafir çıkışı sağlar(CheckOut)
    public int RemoveGuestFromRoom(Guest removeGuest,int roomId){
        Room room = GetRoomByID(roomId);
        List<Guest> roomGuestsList = room.getGuests();

        if( roomGuestsList.remove(removeGuest)){
            return 1;//Başarılı sonuç kodu
        }
        return -1;//Başarsızın sonuç kodu
    }

    //Misafirin kaç gün kaldığını kontrol eder
    public int CheckDaysOfGuest(Guest checkGuest,int roomId){
        Room room  = GetRoomByID(roomId);
        List<Guest> guestsList = room.getGuests();
        for (Guest guest : guestsList) {
            if(checkGuest.getTC()==guest.getTC())//Mevcut misafir listesi içinde döner ve Tc değerine göre misafiri getirir
            {
                LocalDate checkInDate = guest.getCheckIn();//Müşteri giirş tarihini alır
                LocalDate now = LocalDate.now();//Şu anki zamanın gün ay yıl şeklinde alır
                long StayedDays = ChronoUnit.DAYS.between(now, checkInDate);//Şu anki zamanla giriş zamanı arasındaki farkı gün olarak alır ve gün değerini döndürür
                return (int)StayedDays;
            }
        }
        return -1;//Başarısız hata kodu döndürür

    }

    //Misafirin çıkış tarihini kontrol eder
    public int ControlCheckOutDate(Guest checkGuest,int RoomId){
        List<Guest> guests = GetGuestListByRoom(RoomId);
        LocalDate guestCheckOutDate = LocalDate.now();
        for (Guest guest : guests) {
            if(guest.getTC()==checkGuest.getTC()){
                guestCheckOutDate = guest.getCheckOut();
            }
        }
        if(guestCheckOutDate.isAfter(LocalDate.now()))//Çıkış tarihi bugünden sonra mı diye kontrol eder
        {
            return 1;//Çıkış tarihi geçmemiş kodu
        }
        else if(guestCheckOutDate.isBefore(LocalDate.now()))//Çıkış tarihi bugünden çnce mi diye kontrol eder
        {
            return -1;//Çıkış tarihi geçmiş kodu
        }
        else{
            return 0;//Çıkış tarihi bugün kodu
        }
    }

    //Odaya göre misafir listesini getirir
    public List<Guest> GetGuestListByRoom(int roomId){
        Room room = GetRoomByID(roomId);
        return room.getGuests();
    }

    //Folyoya ürün ekler
    public void AddProductToFolio(int roomId,HashMap<String,Integer> product){
        Room room = GetRoomByID(roomId);
        
        Folio roomFolio = room.getFolios();
        HashMap<String,Integer> productsMap = roomFolio.getProducts();
        for (var p : product.entrySet()) {
            productsMap.put(p.getKey(), p.getValue());
        }
    }

    //Folyoyu getirir
    public Folio GetAllFolio(int roomId){
        Room room = GetRoomByID(roomId);
        return room.getFolios();
    }

    //Folyodan ürün silme
    public void DeleteProductFromFolio(int roomId,String productName){
        Room room = GetRoomByID(roomId);
        Folio roomFolio = room.getFolios();
        
        HashMap<String,Integer> products = roomFolio.getProducts();
        products.remove(productName);

        }








}