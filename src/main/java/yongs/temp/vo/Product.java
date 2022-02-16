package yongs.temp.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor 
@NoArgsConstructor
@Data
public class Product {
	private String id;
	private String category;
    private String name;
    private int madein;
    private int shippingfee;
    private long price;    
    private String imageName;
    private String imageUrl;
}
