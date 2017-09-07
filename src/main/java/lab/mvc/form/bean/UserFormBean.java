package lab.mvc.form.bean;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserFormBean {

	@NotNull//(message="{NotNull.userFormBean.firstName}")
	@Size(min = 2, max = 20)
	private String firstName;
	
	@NotNull
	@Size(min = 2, max = 30)
	private String lastName;
}
