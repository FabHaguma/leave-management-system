# Leave Management System: Step-by-Step Completion Plan

## 0. Prerequisites (Already Done)
- [x] Database initialized
- [x] React frontend initialized
- [x] Java Spring backend initialized

---

## 1. **Set Up Core Backend API (Spring Boot)**
### 1.1. Configure Authentication & Security (Do this first)
- Integrate Microsoft Authenticator/MSAL with Spring Security.
- Restrict login to @ist.com emails.
- Set up user roles (Staff, Manager, Admin) in the database and backend.

### 1.2. Define Main Domain Models
- User, Role, LeaveRequest, LeaveBalance, LeaveType, Notification, Document.

### 1.3. Implement Core API Endpoints
- **User**: Get user info, avatar from Microsoft profile.
- **LeaveRequest**: CRUD for leave applications.
- **LeaveBalance**: Get and update balances.
- **LeaveType**: List available leave types.
- **Approval Workflow**: Approve/reject leave, with comment.

### 1.4. Business Logic & Services
- Accrual and carry-forward policies.
- Leave entitlement enforcement.
- Notification logic (triggers for submission, approval/rejection).

### 1.5. Admin APIs
- Adjust leave balances.
- Manage leave types, accruals, and policies.
- Generate/export reports.

---

## 2. **Set Up Core Frontend (React)**
### 2.1. Configure Authentication (Do this first)
- Integrate MSAL for login.
- Retrieve user info and roles from backend.

### 2.2. Set Up Routing & State Management
- Use Context API for global state (auth, leave data, notifications).

### 2.3. Build Main Pages/Components (Start with essentials)
- **Login Page**
- **Employee Dashboard**: Show leave balance, application status, history.
- **Apply Leave**: Form for submitting new leave application.
- **Leave History**: List of past applications.
- **Approval Page (Manager/Admin)**: List, approve/reject requests.
- **Admin Panel**: Policy management, manual adjustments, reports.
- **Team Calendar**: Show who is on leave.

### 2.4. Integrate Core API Calls
- Connect frontend components to backend REST endpoints for data.

### 2.5. Implement Notifications
- In-app notification center for leave events.

---

## 3. **Integration & User Flows**
- Ensure end-to-end flows: login → dashboard → apply for leave → manager approval → notification → balance update.
- Manually test each user role (Staff, Manager, Admin).

---

## 4. **Testing**
- Write unit/integration tests in backend (JUnit for services, controllers).
- Write frontend tests (Jest for components, Cypress for user flows).
- Fix issues found during testing.

---

## 5. **Styling & UX**
- Apply Material-UI components.
- Polish layout and user experience.
- Add global styles/theme.

---

## 6. **Enhancements & Optional Features**
- Outlook sync for team calendar (if required).
- Export reports (CSV/Excel).
- Add advanced filters/search.

---

## 7. **Documentation**
- Update API docs (Swagger/OpenAPI or api.md).
- Write up setup instructions (setup.md).
- Document deployment steps (deployment.md).

---

## 8. **Deployment & CI/CD**
- Add Dockerfiles for backend and frontend if not already done.
- Create/update `docker-compose.yml` for local development.
- Set up GitHub Actions for build/test/deploy pipelines.
- Push Docker images to Docker Hub.
- Deploy to production environment.

---

## 9. **Final Checks & Launch**
- Perform user acceptance testing.
- Fix any critical bugs.
- Announce and go live!

---

## **Tip:**  
Focus on authentication, main user flows, and integration first—these are essential for a usable system. Admin/reporting, enhancements, and polish can be layered in once the core works.