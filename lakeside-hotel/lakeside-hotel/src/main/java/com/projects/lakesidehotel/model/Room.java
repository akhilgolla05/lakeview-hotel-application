package com.projects.lakesidehotel.model;

import ch.qos.logback.core.testUtil.RandomUtil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;



@Entity
@Setter
@Getter

@AllArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomType;

    private BigDecimal roomPrice;

    private boolean isBooked = false;

    @Lob
    private Blob photo;


    @OneToMany(mappedBy = "room",fetch = FetchType.LAZY, cascade = CascadeType.ALL )
    private List<BookedRoom> bookings;

    public Room()
    {
        this.bookings = new ArrayList<>();
    }

    public static String generateRandomNumericString(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10)); // Appending random digits (0-9)
        }
        return sb.toString();
    }

    public void addBooking(BookedRoom booking)
    {
        if(bookings == null)
        {
            bookings = new ArrayList<>();
        }
        bookings.add(booking);
        booking.setRoom(this);
        isBooked = true;

        String bookingCode = generateRandomNumericString(10);
        booking.setBookingConfirmationCode(bookingCode);
    }
}
