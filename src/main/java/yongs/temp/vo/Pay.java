package yongs.temp.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor 
@NoArgsConstructor
@Data
public class Pay {
	private String id;
	private long no;
	private String account;
	private long total;
	private long orderNo;
}
