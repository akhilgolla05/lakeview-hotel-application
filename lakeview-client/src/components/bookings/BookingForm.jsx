import React, { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import moment from 'moment'
import { bookRoom } from '../utils/ApiFunctions'
import { Form, FormControl } from 'react-bootstrap'
import BookingSummary from './BookingSummary'
import { getRoomById } from '../utils/ApiFunctions'

const BookingForm = () => {
  
    const [isValidated, setIsValidated] = useState(false)

    const [isSubmitted, setIsSubmitted] = useState(false)

    const [errorMessage, setErrorMessage] = useState("")

    const [roomPrice, setRoomPrice] = useState(0)

    const {roomId} = useParams()

    const navigate = useNavigate()

    const currentUser = localStorage.getItem("userId")

    const [booking, setBooking] = useState({
        guestFullName : "",
        guestEmail : currentUser,
        checkInDate : "",
        checkOutDate : "",
        numOfAdults : "",
        numOfChildren : ""

    })

    const handleInputChange = (e)=>{
        const {name,value} = e.target
        setBooking({...booking, [name]:value})
        setErrorMessage("")
    }

    const [roomInfo, setRoomInfo] = useState({
        photo : "",
        roomType : "",
        roomPrice : ""
    })

    const getRoomPriceById = async (roomId) =>{
        try{
            const response = await getRoomById(roomId)
            
            setRoomPrice(response.roomPrice)
            // console.log(roomPrice)
        }catch(error){
            throw new Error(error)

        }
    }

    useEffect(()=>{
        getRoomPriceById(roomId)

    },[roomId])

    /*
    function to calculate price based on no.of days
    */

    const calculatePayment = ()=>{
        const checkInDate = moment(booking.checkInDate)
        const checkOutDate = moment(booking.checkOutDate)
        const diffInDays = checkOutDate.diff(checkInDate, "days")
        console.log(diffInDays)
        //price per day
        const price = roomPrice ? roomPrice : 0
        console.log(diffInDays * price)
        return diffInDays * price
    }

    const isGuestCountValid = ()=>{

        const adultCount = parseInt(booking.numOfAdults)
        const childrenCount = parseInt(booking.numOfChildren)

        const totalCount = adultCount + childrenCount

        return totalCount >= 1 && adultCount >=1
    }

    /*
    to check guest has entered the valid checkin and checkout date
    */

    const isCheckOutDateValid = ()=>{

        if(!moment(booking.checkOutDate).isSameOrAfter(moment(booking.checkInDate))){

            setErrorMessage("check-out date must come before check-in date")
            return false
        }else{
            setErrorMessage("")
            return true
        }
    }

    const handleSubmit = (e)=>{
        e.preventDefault()
        const form = e.currentTarget
        if(form.checkValidity() === false || !isGuestCountValid() || !isCheckOutDateValid()){
            e.stopPropagation()
        }else{
            setIsSubmitted(true)
            
        }
        setIsValidated(true)

    }


    const handleBooking = async()=>{
        try{
            const confirmationCode = await bookRoom(roomId, booking)
            setIsSubmitted(true)
            navigate("/booking-success",{state : {message : confirmationCode}})
        }catch(error){
            const errorMessage = error.message
            setErrorMessage(errorMessage)
            navigate("/booking-success",{state : {error : errorMessage}})
        }
    }
    


  
    return (
    <>
      <div className='container mb-5'>
        <div className='row'>
            <div className='col-md-6'>
                <div className='card card-body mt-5'>
                    <h4 className='card card-title'> Reserve Room</h4>
                    <Form noValidate validated={isValidated} onSubmit={handleSubmit}>
                        <Form.Group>
                            <Form.Label htmlFor='guestName'>
                                Full Name : 
                            </Form.Label>

                        </Form.Group>
                        <FormControl required type='text'
                        id="guestName"
                        name="guestFullName"
                        value={booking.guestFullName}
                        placeholder='Enter Your Full Name'
                        onChange={handleInputChange} />

                        <Form.Control.Feedback
                        type="invalid">
                            Please Enter Your Full Name
                        </Form.Control.Feedback>






                        <Form.Group>
                            <Form.Label htmlFor='guestEmail'>
                                Email : 
                            </Form.Label>

                        </Form.Group>
                        <FormControl required type='email'
                        id="guestEmail"
                        name="guestEmail"
                        value={booking.guestEmail}
                        placeholder='Enter Your Email'
                        onChange={handleInputChange} />

                        <Form.Control.Feedback
                        type="invalid">
                            Please Enter Your Email
                        </Form.Control.Feedback>

                        <fieldset style={{border : "2px"}}>
                            <legend>
                                Lodging Period
                            </legend>
                            <div className='row'>
                                <div className='col-6'>

                                    <Form.Label htmlFor='checkInDate'>
                                        Check-In Date : 
                                    </Form.Label>
                                    <FormControl required 
                                    type='date'
                                    id="checkInDate"
                                    name="checkInDate"
                                    value={booking.checkInDate}
                                    placeholder='Enter Your check-in Date'
                                    onChange={handleInputChange} />

                                    <Form.Control.Feedback
                                    type="invalid">
                                        Please Select Your check-in Date
                                    </Form.Control.Feedback>

                                </div>

                                <div className='col-6'>

                                    <Form.Label htmlFor='checkOutDate'>
                                        Check-out Date : 
                                    </Form.Label>
                                    <FormControl required 
                                    type='date'
                                    id="checkOutDate"
                                    name="checkOutDate"
                                    value={booking.checkOutDate}
                                    placeholder='Enter Your check-out Date'
                                    onChange={handleInputChange} />

                                    <Form.Control.Feedback
                                    type="invalid">
                                        Please Select Your check-out Date
                                    </Form.Control.Feedback>

                                </div>
                                {
                                    errorMessage && <p className='error-message text-danger'>{errorMessage}</p>
                                }

                            </div>
                        </fieldset>

                        <fieldset>
                            <legend>Number Of Guests</legend>

                            <div className='row'>
                                <div className='col-6'>

                                    <Form.Label htmlFor='numOfAdults'>
                                        Adults : 
                                    </Form.Label>
                                    <FormControl required 
                                    type='number'
                                    id="numOfAdults"
                                    name="numOfAdults"
                                    value={booking.numOfAdults}
                                    
                                    min={1}
                                    placeholder='0'
                                    onChange={handleInputChange} />

                                    <Form.Control.Feedback
                                    type="invalid">
                                        Please select atleast one adult.
                                    </Form.Control.Feedback>

                                </div>



                                <div className='col-6'>

                                    <Form.Label htmlFor='numOfChildren'>
                                        Children : 
                                    </Form.Label>
                                    <FormControl required 
                                    type='number'
                                    id="numOfChildren"
                                    name="numOfChildren"
                                    value={booking.numOfChildren}

                                
                                    placeholder='0'
                                    onChange={handleInputChange} />
                                    </div>
                                </div>
                        </fieldset>

                        <div className='form-group mt-2 mb-2'>
                            <button type="submit" className='btn btn-hotel'>Continue</button>
                            
                        </div>


                    </Form>

                </div>

            </div>
            <div className='col-md-6'>
                {isSubmitted && (
                    <BookingSummary 
                    booking={booking}
                    payment={calculatePayment()}
                    isFormValid={isValidated}
                    onConfirm={handleBooking}/>
                )}

            </div>

        </div>


      </div>
    </>
  )
}

export default BookingForm
