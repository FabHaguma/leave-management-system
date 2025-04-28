import React, { useState } from 'react';
import { Calendar, dateFnsLocalizer } from 'react-big-calendar';
import 'react-big-calendar/lib/css/react-big-calendar.css';
import { format, parse, startOfWeek, getDay, addMonths, subMonths } from 'date-fns';
import enUS from 'date-fns/locale/en-US';
import './styles/CalendarComp.css'; // Import the external CSS file

const locales = {
  'en-US': enUS,
};

const localizer = dateFnsLocalizer({
  format,
  parse,
  startOfWeek,
  getDay,
  locales,
});

const previous = "<<";
const next = ">>";

const CalendarComp = ({ events }) => {
  const [currentDate, setCurrentDate] = useState(new Date());

  const handleNextMonth = () => {
    setCurrentDate(addMonths(currentDate, 1)); // Move to the next month
  };

  const handlePreviousMonth = () => {
    setCurrentDate(subMonths(currentDate, 1)); // Move to the previous month
  };

  return (
    <div className="calendar-view">
      <div className="calendar-roof">
        <div className="calendar-title"><h1>Calendar</h1></div>
        <div className="calendar-change-month">
          <button onClick={handlePreviousMonth}>{previous}</button>
          <button onClick={handleNextMonth}>{next}</button>
        </div>
      </div>
      <Calendar
        localizer={localizer}
        events={events}
        startAccessor="start"
        endAccessor="end"
        date={currentDate} // Set the current date for the calendar
        onNavigate={(date) => setCurrentDate(date)} // Sync navigation with state
        style={{ height: 450, width: '100%' }}
        views={['month']} // Restrict to only the 'month' view
        toolbar={true} // Keep the toolbar disabled
      />
    </div>
  );
};

export default CalendarComp;