import React, { useState } from 'react';
import './styles/HolidaysComp.css'; // Import the external CSS file

const HolidaysComp = ({ holidayList, accessLevel }) => {
  const [holidays, setHolidays] = useState(holidayList);
  const [newHoliday, setNewHoliday] = useState({ name: '', date: '' });

  const handleAddHoliday = () => {
    if (newHoliday.name && newHoliday.date) {
      setHolidays([...holidays, newHoliday]);
      setNewHoliday({ name: '', date: '' });
    }
  };

  return (
    <div>
      <div className="holiday-list">
        <h2>Holidays</h2>
        <ul>
          {holidays.map((holiday, index) => (
            <li key={index}>
              {holiday.name} - {holiday.date}
            </li>
          ))}
        </ul>
      </div>

      {accessLevel === 3 && (
        <div className="add-holiday-form">
          <h3>Add a New Holiday</h3>
          <input
            type="text"
            placeholder="Holiday Name"
            value={newHoliday.name}
            onChange={(e) => setNewHoliday({ ...newHoliday, name: e.target.value })}
          />
          <input
            type="date"
            value={newHoliday.date}
            onChange={(e) => setNewHoliday({ ...newHoliday, date: e.target.value })}
          />
          <button onClick={handleAddHoliday}>Add Holiday</button>
        </div>
      )}
    </div>
  );
};

export default HolidaysComp;