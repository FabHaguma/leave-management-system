package com.ist.leavemanagementsystem.dto;

public class LeaveApprovalDto {

    private Long leaveRequestId;
    private Long approverId;
    private String approvalStatus;
    private String approvalDate;
    private String comments;

    public LeaveApprovalDto() {}

    public LeaveApprovalDto(Long leaveRequestId, Long approverId, String approvalStatus, String approvalDate, String comments) {
        this.leaveRequestId = leaveRequestId;
        this.approverId = approverId;
        this.approvalStatus = approvalStatus;
        this.approvalDate = approvalDate;
        this.comments = comments;
    }

    public Long getLeaveRequestId() {
        return leaveRequestId;
    }

    public void setLeaveRequestId(Long leaveRequestId) {
        this.leaveRequestId = leaveRequestId;
    }

    public Long getApproverId() {
        return approverId;
    }

    public void setApproverId(Long approverId) {
        this.approverId = approverId;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(String approvalDate) {
        this.approvalDate = approvalDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
