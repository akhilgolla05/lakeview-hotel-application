package com.projects.lakesidehotel.service;

import com.projects.lakesidehotel.model.BookedRoom;

import java.util.List;

public interface BookedRoomService {
    List<BookedRoom> getAllBookingsByRoomId(Long roomId);

    void cancelBooking(Long bookingId);

    String saveBooking(Long roomId, BookedRoom bookingRequest);

    BookedRoom findByBookingConfirmationCode(String conformationCode);

    List<BookedRoom> getAllBookings();
}
