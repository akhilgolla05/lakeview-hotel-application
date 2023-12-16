package com.projects.lakesidehotel.service;

import com.projects.lakesidehotel.exception.InvalidBookingRequestException;
import com.projects.lakesidehotel.exception.ResourceNotFoundException;
import com.projects.lakesidehotel.model.BookedRoom;
import com.projects.lakesidehotel.model.Room;
import com.projects.lakesidehotel.repository.BookedRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookedRoomServiceImpl implements BookedRoomService{

    private final BookedRoomRepository bookingRepository;

    private final RoomService roomService;



    @Override
    public List<BookedRoom> getAllBookingsByRoomId(Long roomId) {
        return bookingRepository.findByRoomId(roomId);
    }

    @Override
    public void cancelBooking(Long bookingId) {
        bookingRepository.deleteById(bookingId);

    }

    @Override
    public String saveBooking(Long roomId, BookedRoom bookingRequest) {
        if(bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())){
            throw new InvalidBookingRequestException("Check-in date must come before checkout");

        }
        Room room = roomService.getRoomById(roomId).get();
        List<BookedRoom> existingBookings = room.getBookings();
        boolean roomIsAvailable = roomIsAvailable(bookingRequest,existingBookings);
        if(roomIsAvailable){
            room.addBooking(bookingRequest);
            bookingRepository.save(bookingRequest);
        }else{
            throw new InvalidBookingRequestException("Sorry, This room is not available for selected dates.");

        }
        return bookingRequest.getBookingConfirmationCode();
    }

    private boolean roomIsAvailable(BookedRoom bookingRequest, List<BookedRoom> existingBookings) {

        return existingBookings.stream()
                .noneMatch(existingBooking ->
                        bookingRequest.getCheckInDate().equals(existingBooking.getCheckInDate())
                                || bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckOutDate())
                                || (bookingRequest.getCheckInDate().isAfter(existingBooking.getCheckInDate())
                                && bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate()))
                                || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

                                && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckOutDate()))
                                || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

                                && bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckOutDate()))

                                || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                                && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckInDate()))

                                || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                                && bookingRequest.getCheckOutDate().equals(bookingRequest.getCheckInDate()))


                );

    }

    @Override
    public BookedRoom findByBookingConfirmationCode(String conformationCode) {
        return bookingRepository.findByBookingConfirmationCode(conformationCode)
                .orElseThrow(()->new ResourceNotFoundException("No Booking Found with booking code : " + conformationCode));
    }

    @Override
    public List<BookedRoom> getAllBookings() {
        return bookingRepository.findAll();
    }
}
