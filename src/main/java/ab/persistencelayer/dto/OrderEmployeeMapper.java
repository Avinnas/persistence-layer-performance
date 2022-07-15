package ab.persistencelayer.dto;

import ab.persistencelayer.model.Order;

public class OrderEmployeeMapper {

    public static OrderEmployeeNameDto mapToDto(Order order){
        return new OrderEmployeeNameDto(order.getCreationDate(), order.getDeliveryDate(),
                order.getEmployee().getName() + order.getEmployee().getSurname());
    }
}
