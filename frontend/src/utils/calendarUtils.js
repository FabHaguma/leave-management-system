// Function to process a list of leave requests and return formatted events for a calendar
function processLeaveRequests(leaveRequests) {
    return leaveRequests.flatMap((request) => [
      {
        title: `${request.userName} (Start)`,
        start: new Date(request.startDate),
        end: new Date(request.startDate)
      },
      {
        title: `${request.userName} (End)`,
        start: new Date(request.endDate),
        end: new Date(request.endDate)
      },
    ]);
  }

// A list of all events on the calendar in Rwanda
const typicalEvents = [
    {
      title: "New Year's Day",
      start: new Date(new Date().getFullYear(), 0, 1),
      end: new Date(new Date().getFullYear(), 0, 1),
    },
    {
      title: "Heroes' Day",
      start: new Date(new Date().getFullYear(), 1, 1),
      end: new Date(new Date().getFullYear(), 1, 1),
    },
    {
      title: "Good Friday",
      start: new Date(new Date().getFullYear(), 3, 18),
      end: new Date(new Date().getFullYear(), 3, 18),
    },
    {
      title: "Labour Day",
      start: new Date(new Date().getFullYear(), 4, 1),
      end: new Date(new Date().getFullYear(), 4, 1),
    },
    {
      title: "Independence Day",
      start: new Date(new Date().getFullYear(), 6, 1),
      end: new Date(new Date().getFullYear(), 6, 1),
    },
    {
      title: "Liberation Day",
      start: new Date(new Date().getFullYear(), 6, 4),
      end: new Date(new Date().getFullYear(), 6, 4),
    },
    {
      title: "Assumption of Mary",
      start: new Date(new Date().getFullYear(), 7, 15),
      end: new Date(new Date().getFullYear(), 7, 15),
    },
    {
      title: "All Saints' Day",
      start: new Date(new Date().getFullYear(), 10, 1),
      end: new Date(new Date().getFullYear(), 10, 1),
    },
    {
      title: "Christmas Day",
      start: new Date(new Date().getFullYear(), 11, 25),
      end: new Date(new Date().getFullYear(), 11, 25),
    },
    {
      title: "International Women's Day",
      start: new Date(new Date().getFullYear(), 2, 8), // March 8
      end: new Date(new Date().getFullYear(), 2, 8),
    },
  ];

// Function to export public holidays by title and date
function getCompanyEvents() {
  const events = [];
  for (let i = 0; i < typicalEvents.length; i++) {
    const holiday = typicalEvents[i];
    events.push({
      name: holiday.title,
      date: holiday.start.toISOString().split('T')[0], // Extracting the date in YYYY-MM-DD format
    });
  }

  // Sort events by date
  events.sort((a, b) => new Date(a.date) - new Date(b.date));

  return events;
}

// Function to get the list of public holidays
function getNationalHolidays() {
    return typicalEvents;
}

const calendarUtils = {
  processLeaveRequests,
  getCompanyEvents,
  getNationalHolidays,
};

export default calendarUtils;
