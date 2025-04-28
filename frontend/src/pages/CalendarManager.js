import React, { useState, useEffect } from 'react';
import Navbar from '../components/Navbar';
import CalendarComp from '../components/CalendarComp';
import HolidaysComp from '../components/HolidaysComp';
import '../styles/CalendarManager.css';
import { AuthContext } from '../context/AuthContext'; // Import AuthContext
import calendarUtil from '../utils/calendarUtils'; // Import calendar utility functions
const { sendRequest } = require('../utils/urlBuilder');

const CalendarManager = () => {
  const { user } = React.useContext(AuthContext); // Get user info from AuthContext
  const [events, setEvents] = useState(calendarUtil.getCompanyEvents()); // State to manage events
  const [holidays, setHolidays] = useState(
    calendarUtil.getNationalHolidays().map(event => ({
      name: event.title,
      date: event.start.toISOString().split('T')[0], // Format date as YYYY-MM-DD
    }))
  );

  // pull events and holidays from maybe the backend
  const fetchEventsAndHolidays = async () => {
    // const data = await sendRequest('events-and-holidays', 'GET', { });
    // setEvents(data.events);
    // setHolidays(data.holidays);
  };

  useEffect(() => {
    fetchEventsAndHolidays();
  }, []);

  return (
    <div className="LMS-general-page-container">
      <Navbar />
      <div className="calendar-content">
        <div className="calendar-component">
          <CalendarComp events={events} />
        </div>
        <div className="public-holidays">
          <HolidaysComp holidayList={holidays} accessLevel={user.level} />
        </div>
      </div>
    </div>
  );
};

export default CalendarManager;