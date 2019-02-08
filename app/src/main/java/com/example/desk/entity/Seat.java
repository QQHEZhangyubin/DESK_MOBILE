package com.example.desk.entity;

public class Seat {
    private String roomid;
    private String total_seat;
    private String avail_seat;

    public Seat(String total_seat, String avail_seat,String roomid) {
        this.total_seat = total_seat;
        this.avail_seat = avail_seat;
        this.roomid = roomid;
    }

    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }

    public String getTotal_seat() {
        return total_seat;
    }

    public void setTotal_seat(String total_seat) {
        this.total_seat = total_seat;
    }

    public String getAvail_seat() {
        return avail_seat;
    }

    public void setAvail_seat(String avail_seat) {
        this.avail_seat = avail_seat;
    }
}
