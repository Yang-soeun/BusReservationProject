package bus.busReservation.dto;

import bus.busReservation.domain.Bus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusDto {
    private Long id;
    private String busName;//버스 이름 100, 200 ...
    private String busNum;//차량번호

    public BusDto(Bus bus){
        id = bus.getId();
        busName = bus.getName();
        busNum = bus.getNum();
    }
}
