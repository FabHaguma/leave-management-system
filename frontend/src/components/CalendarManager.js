import React, { useState } from 'react';
import Navbar from './Navbar';
import '../styles/CalendarManager.css';
import { Calendar, dateFnsLocalizer } from 'react-big-calendar';
import 'react-big-calendar/lib/css/react-big-calendar.css';
import { format, parse, startOfWeek, getDay } from 'date-fns';
import enUS from 'date-fns/locale/en-US';

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

const events = [
  {
    title: "Team Meeting",
    start: new Date(2025, 3, 25, 10, 0),
    end: new Date(2025, 3, 25, 11, 0),
  },
  {
    title: "Project Deadline",
    start: new Date(2025, 3, 30, 23, 59),
    end: new Date(2025, 3, 30, 23, 59),
  },
];

const CalendarManager = () => {
  const [holidays, setHolidays] = useState([
    { name: "New Year's Day", date: 'January 1' },
    { name: 'Independence Day', date: 'July 4' },
  ]);
  const [newHoliday, setNewHoliday] = useState({ name: '', date: '' });

  const handleAddHoliday = () => {
    if (newHoliday.name && newHoliday.date) {
      setHolidays([...holidays, newHoliday]);
      setNewHoliday({ name: '', date: '' });
    }
  };

  return (
    <div className="LMS-general-page-container">
      <Navbar />
      <div className="calendar-content">
        <div className="calendar-view">
          <h2>Calendar</h2>
          <Calendar
            localizer={localizer}
            events={events}
            startAccessor="start"
            endAccessor="end"
            style={{ height: 400, margin: '30px' }}
            views={['month']} // Restrict to only the 'month' view
            toolbar={false} // Remove the toolbar containing 'Today', 'Month', 'Week', etc.
          />
        </div>
        <aside className="public-holidays">
          <h3>Public Holidays</h3>
          <ul>
            {holidays.map((holiday, index) => (
              <li key={index}>{`${holiday.name} - ${holiday.date}`}</li>
            ))}
          </ul>
          <div className="add-holiday-form">
            <h4>Add New Holiday</h4>
            <input
              type="text"
              placeholder="Holiday Name"
              value={newHoliday.name}
              onChange={(e) => setNewHoliday({ ...newHoliday, name: e.target.value })}
            />
            <input
              type="text"
              placeholder="Holiday Date"
              value={newHoliday.date}
              onChange={(e) => setNewHoliday({ ...newHoliday, date: e.target.value })}
            />
            <button onClick={handleAddHoliday}>Add Holiday</button>
          </div>
        </aside>
      </div>
    </div>
  );
};

export default CalendarManager;