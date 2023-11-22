package com.projects.lakesidehotel.service;

import com.projects.lakesidehotel.model.BookedRoom;

import java.util.List;

public interface BookedRoomService {
    List<BookedRoom> getAllBookingsByRoomId(Long roomId);
}
