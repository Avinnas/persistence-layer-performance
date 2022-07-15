package ab.persistencelayer.dto;

import java.time.LocalDateTime;

public class OrderEmployeeNameDto {

    private LocalDateTime creationDate;
    private LocalDateTime deliveryDate;
    private String employeeName;

    public OrderEmployeeNameDto(){
    }

    public OrderEmployeeNameDto(LocalDateTime creationDate, LocalDateTime deliveryDate, String employeeName) {
        this.creationDate = creationDate;
        this.deliveryDate = deliveryDate;
        this.employeeName = employeeName;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
}
