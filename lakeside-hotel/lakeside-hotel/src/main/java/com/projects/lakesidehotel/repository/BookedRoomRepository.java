package com.projects.lakesidehotel.repository;

import com.projects.lakesidehotel.model.BookedRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookedRoomRepository extends JpaRepository<BookedRoom, Long> {

    List<BookedRoom> findByRoomId(Long roomId);

    Optional<BookedRoom>  findByBookingConfirmationCode(String conformationCode);
}
