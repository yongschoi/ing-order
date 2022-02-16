package yongs.temp.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor 
@NoArgsConstructor
@Data
public class User {
	private int id;
	private String email;
	private String name;
	private int score;
	private String photo;
	private String photoUrl;
}
