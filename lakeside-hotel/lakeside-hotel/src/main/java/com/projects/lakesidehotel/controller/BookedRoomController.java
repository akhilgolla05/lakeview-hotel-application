package com.projects.lakesidehotel.controller;

import com.projects.lakesidehotel.exception.InvalidBookingRequestException;
import com.projects.lakesidehotel.exception.ResourceNotFoundException;
import com.projects.lakesidehotel.model.BookedRoom;
import com.projects.lakesidehotel.model.Room;
import com.projects.lakesidehotel.response.BookingResponse;
import com.projects.lakesidehotel.response.RoomResponse;
import com.projects.lakesidehotel.service.BookedRoomService;
import com.projects.lakesidehotel.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookedRoomController {

    private final BookedRoomService bookingService;

    private final RoomService roomService;

    @GetMapping("/all-bookings")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<BookingResponse>> getAllBookings(){

        List<BookedRoom> bookings = bookingService.getAllBookings();
        List<BookingResponse> bookingResponses = new ArrayList<>();
        for(BookedRoom booking : bookings){
            BookingResponse bookingResponse = getBookingResponse(booking);
            bookingResponses.add(bookingResponse);
        }
        return ResponseEntity.ok(bookingResponses);
    }

    private BookingResponse getBookingResponse(BookedRoom booking) {
        Room theRoom = roomService.getRoomById(booking.getRoom().getId()).get();
        RoomResponse room = new RoomResponse(theRoom.getId(),
                theRoom.getRoomType(), theRoom.getRoomPrice());

        return new BookingResponse(booking.getBookingId(), booking.getCheckInDate(),
                booking.getCheckOutDate(),booking.getGuestFullName(),
                booking.getGuestEmail(),booking.getNumOfAdults(),
                booking.getNumOfChildren(),booking.getTotalNumberGuest(),
                booking.getBookingConfirmationCode(),
                room);


    }

    @GetMapping("/confirmation/{confirmationCode}")
    public ResponseEntity<?> getBookingByConfirmation(@PathVariable("confirmationCode") String confirmationCode){
        try{
            BookedRoom booking = bookingService.findByBookingConfirmationCode(confirmationCode);
            BookingResponse bookingResponse = getBookingResponse(booking);
            return ResponseEntity.ok(bookingResponse);
        }catch(ResourceNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());

        }
    }


    @PostMapping("/room/{roomId}/booking")
    public ResponseEntity<?> saveBooking(@PathVariable Long roomId,
                                         @RequestBody BookedRoom bookingRequest){

        try{
            String confirmationCode = bookingService.saveBooking(roomId, bookingRequest);
            return ResponseEntity.ok("Room Booked Successfully ! Your conformation code is : "+confirmationCode);

        }catch(InvalidBookingRequestException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

    }

    @DeleteMapping("/booking/{bookingId}/delete")
    public void cancelBooking(@PathVariable("bookingId") Long bookingId){
        bookingService.cancelBooking(bookingId);

    }

    @GetMapping("/user/{email}/bookings")
    public ResponseEntity<List<BookingResponse>> getBookingsByUserEmail(@PathVariable String email) {
        List<BookedRoom> bookings = bookingService.getBookingsByUserEmail(email);
        List<BookingResponse> bookingResponses = new ArrayList<>();
        for (BookedRoom booking : bookings) {
            BookingResponse bookingResponse = getBookingResponse(booking);
            bookingResponses.add(bookingResponse);
        }
        return ResponseEntity.ok(bookingResponses);
    }


}
