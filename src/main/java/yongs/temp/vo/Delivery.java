package yongs.temp.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor 
@NoArgsConstructor
@Data
public class Delivery {
	private String id;
	private long no;
	private String address;
	private long orderNo;
}
